package edu.eezo.tafl;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import static edu.eezo.tafl.AdditionMethods.*;

/**
 * Created by Eezo on 19.06.2017.
 */
public class Requests extends JFrame {
    private JRadioButton radioQueryResult;
    private JRadioButton radioTableOfTokens;
    private JCheckBox checkboxIncludeSemantic;
    private JCheckBox checkboxIncludeParser;
    private JCheckBox checkboxRegexp;
    private JComboBox comboboxQery;
    private JButton buttonSend;
    private JTable tableResults;
    private JPanel rootPanel;

    // ! программа настроенна на базу данных по адресу F:\\CARS.sqlite, но сама БД прилагается в папке программы

    private Connection con = null;
    private ResultSet rs = null;
    private Statement st = null;
    private DefaultTableModel model;
    private ButtonGroup bg1;
    private int selectedCB = 0;
    static boolean connectedDB = false, connectedSyntax = false;
    private File syntax;

    /**
     * Creates new form Requests
     */
    Requests() {
        super("Eezo Parser -- Reborn");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        con = new Connect().getCon();
        syntax = new File("d:/syntaxis.txt");
        bg1 = new ButtonGroup();

        bg1.add(radioQueryResult);
        bg1.add(radioTableOfTokens);
        if (connectedDB) {
            bg1.setSelected(radioQueryResult.getModel(), true);
        } else {
            bg1.setSelected(radioTableOfTokens.getModel(), true);
        }

        tableResults.setVisible(false);
        checkboxIncludeParser.setSelected(true);
        checkboxIncludeSemantic.setSelected(true);

        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });

        comboboxQery.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    run();
                }
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    getHelp();
                }
            }
        });

        comboboxQery.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                try {
                    if (e.getWheelRotation() < 0) {
                        if (comboboxQery.getSelectedIndex() == 0) {
                            comboboxQery.setSelectedIndex(comboboxQery.getItemCount() - 1);
                        } else {
                            comboboxQery.setSelectedIndex(comboboxQery.getSelectedIndex() - 1);
                        }
                    } else {
                        if (comboboxQery.getSelectedIndex() == (comboboxQery.getItemCount() - 1)) {
                            comboboxQery.setSelectedIndex(0);
                        } else {
                            comboboxQery.setSelectedIndex(comboboxQery.getSelectedIndex() + 1);
                        }
                    }
                    selectedCB = comboboxQery.getSelectedIndex();
                } catch (IllegalArgumentException e1) {
                    comboboxQery.setSelectedIndex(selectedCB);
                }
            }
        });

        tableResults.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    getHelp();
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (connectedDB) {
                    try {
                        con.close();
                        System.out.println(Data.DB_CONNECTION_CLOSED);
                    } catch (SQLException ex) {
                        System.err.println("SQLException in windowClosing: " + ex);
                    }
                }
            }
        });
    }

    private void run() {
        model = (DefaultTableModel) tableResults.getModel();
        model.setRowCount(0);
        tableResults.setVisible(true);

        if (checkboxIncludeParser.isSelected()) {
            if (!preParser(syntax)) {
                checkboxIncludeParser.setSelected(false);
                checkboxIncludeParser.setEnabled(false);
                return;
            }
        }

        if (checkboxIncludeSemantic.isSelected()) {
            semantic(comboboxQery.getSelectedItem().toString());
        }

        if (bg1.getSelection() == radioQueryResult.getModel()) {
            if (!connectedDB) {
                showMessageDialog(rootPane, Data.DB_NO_CONNECTION, Data.DB_CONNECTION_ERROR, Data.WARNING);
                return;
            }
            requestResult();
        } else {
            tokenTable();
        }

        if ((200 + model.getRowCount() * 16) < 860) {
            setBounds(getX(), getY(), 600 + model.getColumnCount() * 20, 200 + model.getRowCount() * 16);
        } else {
            setBounds(getX(), 0, 600 + model.getColumnCount() * 20, 860);
        }
    }

    public String whichToken(String word) {
        if (checkboxRegexp.isSelected()) {
            word = word.toUpperCase();
            // fieldname - [([a-zA-Z]{1}[a-zA-Z0-9]{,7}\\.)?[a-zA-Z\\_]{1}[a-zA-Z0-9\\_\\-]{,15}]
            // tablename - [[a-zA-Z]{1}[a-zA-Z0-9]{,7}]
            if (word.matches("\\=|\\*|\\/")) {
                return "math_op";
            } else if (word.matches("SELECT|FROM|WHERE|JOIN|INNER|LEFT|RIGHT|FULL|ON|IN|AS|CREATE|DROP|TABLE|INSERT|INTO|DELETE|ORDER|BY|HAVING|GROUP|INDEX|IF|EXISTS|ALTER")) {
                return "keyword";
            } else if (word.matches("[A-Z]{1}[A-Z0-9]{0,7}")) {
                return "tablename(id2)";
            } else if (word.matches("\\*|([A-Z]{1}[A-Z0-9]{0,7}\\.)?[A-Z\\_]{1}[A-Z0-9\\_-]{0,15}")) {
                return "field(id3)";
            } else if (word.matches("\\>|\\<")) {
                return "relation";
            } else if (word.matches("\\-?0?\\d+(\\.\\d{0,})?")) {
                return "num";
            } else if (word.matches("\"[A-Z0-9]*\"|'[A-Z0-9]*'")) {
                return "value";
            }
        } else { // автоматы
            if (mathOpFSM(word)) {
                return "math_op";
            } else if (keywordFSM(word)) {
                return "keyword";
            } else if (tablenameFSM(word)) {
                return "tablename(id2)";
            } else if (fieldFSM(word)) {
                return "field(id3)";
            } else if (relationFSM(word)) {
                return "relation";
            } else if (numberFSM(word)) {
                return "num";
            } else if (valueFSM(word)) {
                return "value";
            }
        }
        return "!invalid lexem";
    }

    private boolean preParser(File file) {
        String line, readLine = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            connectedSyntax = true;

            while ((line = br.readLine()) != null) {
                readLine += line;
            }
        } catch (FileNotFoundException ex) {
            int op = JOptionPane.showConfirmDialog(rootPane, Data.SYNTAX_FILE_NOT_FOUND);

            if (op == 0) {
                try {
                    syntax = setNewFile();
                } catch (FileNotFoundException ex1) {
                    showMessageDialog(rootPane, Data.SYNTAX_NO_SYNTAX, Data.SYNTAX_ANALIZATOR, Data.ERROR);
                    return false;
                }
                connectedSyntax = true;
            } else {
                showMessageDialog(rootPane, Data.SYNTAX_NO_SYNTAX, Data.SYNTAX_ANALIZATOR, Data.ERROR);
                return false;
            }
        } catch (IOException ex) {
            showMessageDialog(rootPane, ex.toString());
            return false;
        }
        if (connectedSyntax) {
            return parser(readLine);
        }
        return false;
    }

    private boolean parser(String text) {
        StringTokenizer token = new StringTokenizer(text, " \n\t");
        ArrayList<String> tokenList = new ArrayList<>();

        while (token.hasMoreTokens()) {
            tokenList.add(token.nextToken());
        }

        int pos = 0, tokenCounter = 0;
        String prev = "";
        token = new StringTokenizer(comboboxQery.getSelectedItem().toString(), " \t\n\r,=*/><();+", true);

        while (token.hasMoreTokens()) {
            String aToken = token.nextToken();
            if (aToken.equals(" ") || aToken.equals("\n") || aToken.equals("\t") || aToken.equals(";")) {
                continue;
            }

            if (tokenList.get(tokenCounter).equals("fields")) {
                while (!aToken.equalsIgnoreCase("FROM")) {
                    if (" ".equals(aToken)) {
                        if (!" ".equals(prev) & !",".equals(prev) & !"field(id3)".equals(whichToken(prev)) & !"*".equals(prev)) {
                            showParserMessage("','", "' '", pos);
                            return false;
                        }
                    } else if (",".equals(aToken)) {
                        if (" ".equals(prev) & ",".equals(prev)) {
                            showParserMessage("<next token>", "','", pos);
                            return false;
                        }
                    } else if (!aToken.equals("*") & !fieldFSM(aToken.toUpperCase())) {
                        showParserMessage("fieldname", aToken, pos);
                        return false;
                    }

                    pos += aToken.length();
                    prev = aToken;
                    aToken = token.nextToken();
                }

                tokenCounter++;
            } else if (tokenList.get(tokenCounter).equals("field") && !fieldFSM(aToken.toUpperCase())) {
                showParserMessage("fieldname", aToken, pos);
                return false;
            } else if (tokenList.get(tokenCounter).equals("table") && !tablenameFSM(aToken.toUpperCase())) {
                showParserMessage("tablename", aToken, pos);
                return false;
            } else if (!(tokenList.get(tokenCounter).equals("field") && fieldFSM(aToken.toUpperCase()))
                    && !(tokenList.get(tokenCounter).equals("table") && tablenameFSM(aToken.toUpperCase()))) {
                if (!tokenList.get(tokenCounter).equalsIgnoreCase(aToken)) {
                    showParserMessage(tokenList.get(tokenCounter), aToken, pos);
                    return false;
                }
            }

            tokenCounter++;
            prev = aToken;
            pos += aToken.length();
        }

        return true;
    }

    public boolean semantic(String request) {
        StringTokenizer stk = new StringTokenizer(request, " \t\n\r,=*/><();+", true);
        String prev = "", current;

        while (stk.hasMoreTokens()) {
            current = stk.nextToken();
            // = num or field
            if (" ".equals(current) || "\t".equals(current) || ";".equals(current) || "\r".equals(current)) {
                continue;
            }

            if (relationFSM(prev) && !numberFSM(current) && !fieldFSM(current)) {
                showMessageDialog(rootPane, Data.SEMANTIC_AFTER_COMPARATIONS_SYMBOLS, Data.SEMANTIC_ANALIZATOR, Data.ERROR);
                return false;
            }

            if (mathOpFSM(prev) && !numberFSM(current) && !fieldFSM(current)) {
                showMessageDialog(rootPane, Data.SEMANTIC_AFTER_MATH_OPS, Data.SEMANTIC_ANALIZATOR, Data.ERROR);
                return false;
            }

            prev = current;
        }

        return true;
    }

    private void requestResult() {
        try {
            model.setColumnCount(0);
            st = con.createStatement();
            rs = st.executeQuery(comboboxQery.getSelectedItem().toString());

            int colCount = rs.getMetaData().getColumnCount();
            Object[] colIdent = new Object[colCount];

            for (int i = 0; i < colCount; i++) {
                colIdent[i] = rs.getMetaData().getColumnLabel(i + 1);
            }

            model.setColumnCount(colCount);
            model.setColumnIdentifiers(colIdent);
            int i = 0;

            while (rs.next()) {
                model.setRowCount(i + 1);
                for (int j = 0; j < colCount; j++) {
                    tableResults.setValueAt(rs.getObject(j + 1), i, j);
                }
                i++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            System.err.println(ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    System.out.println(Data.DB_RESULT_SET_CLOSED);
                }

                if (st != null) {
                    st.close();
                    System.out.println(Data.DB_STATEMENT_CLOSED);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                System.err.println(ex.getMessage());
            }
        }
    }

    private void tokenTable() {
        model.setColumnCount(5);
        Object[] colIdent = {"Токен", "Лексема", "Початок", "Довжина", "Адреса"};
        model.setColumnIdentifiers(colIdent);
        String line = comboboxQery.getSelectedItem().toString();
        StringTokenizer tokens = new StringTokenizer(line, " \t\n\r,=*/><();+", true);
        String aToken;
        int row = 0, pos = 1;

        while (tokens.hasMoreTokens()) {
            aToken = tokens.nextToken();
            if (aToken.equals(" ") || aToken.equals(",") || aToken.equals("(") || aToken.equals(")") || aToken.equals(";")) {
                continue;
            }

            model.setRowCount(model.getRowCount() + 1);
            if (row > 0) {
                if (aToken.equals("*") && "keyword".equalsIgnoreCase(tableResults.getValueAt(row - 1, 0).toString())) {
                    tableResults.setValueAt("field(id3)", row, 0);
                    tableResults.setValueAt(aToken, row, 1);
                    tableResults.setValueAt(pos, row, 2);
                    tableResults.setValueAt(aToken.length(), row, 3);
                } else {
                    if ("tablename(id2)".equals(whichToken(aToken)) && !tableResults.getValueAt(row - 1, 1).toString().toUpperCase().matches("FROM|JOIN|TABLE")) {
                        tableResults.setValueAt("field(id3)", row, 0);
                    } else {
                        tableResults.setValueAt(whichToken(aToken), row, 0);
                    }

                    tableResults.setValueAt(aToken, row, 1);
                    tableResults.setValueAt(pos, row, 2);
                    tableResults.setValueAt(aToken.length(), row, 3);

                    if (aToken.equals("=") && ("<".equalsIgnoreCase(tableResults.getValueAt(row - 1, 1).toString()) || ">".equalsIgnoreCase(tableResults.getValueAt(row - 1, 1).toString()))) {
                        tableResults.setValueAt(tableResults.getValueAt(row - 1, 1) + "=", row - 1, 1);
                        tableResults.setValueAt(aToken.length() + (int) tableResults.getValueAt(row - 1, 3), row - 1, 3);
                        model.setRowCount(model.getRowCount() - 1);
                        row -= 1;
                    } else if (aToken.equals(">") && ("<".equalsIgnoreCase(tableResults.getValueAt(row - 1, 1).toString()))) {
                        tableResults.setValueAt(tableResults.getValueAt(row - 1, 1) + ">", row - 1, 1);
                        tableResults.setValueAt(aToken.length() + (int) tableResults.getValueAt(row - 1, 3), row - 1, 3);
                        model.setRowCount(model.getRowCount() - 1);
                        row -= 1;
                    }
                }
            } else {
                tableResults.setValueAt(whichToken(aToken), row, 0);
                tableResults.setValueAt(aToken, row, 1);
                tableResults.setValueAt(pos, row, 2);
                tableResults.setValueAt(aToken.length(), row, 3);
            }

            if (tableResults.getValueAt(row, 0).toString().matches("field\\(id3\\)|tablename\\(id2\\)")) {
                tableResults.setValueAt(Math.round(Math.random() * 10000), row, 4);
            } else {
                tableResults.setValueAt("", row, 4);
            }

            row += 1;
            pos += aToken.length();
        }

        for (int i = 0; i < tableResults.getRowCount(); i++) {
            if (tableResults.getValueAt(i, 0) == "!invalid lexem") {
                JOptionPane.showMessageDialog(rootPane, Data.LEXICAL_UNKOWN_LEXEMS, Data.LEXICAL_ANALIZATOR, JOptionPane.WARNING_MESSAGE);
                break;
            }
        }
    }

    private void getHelp() {
        JOptionPane.showMessageDialog(rootPane, Data.DB_HELP, Data.DB_HELP_TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Requests r = new Requests();
            }
        });
    }
}
