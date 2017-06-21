package edu.eezo.tafl;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Created by Eezo on 19.06.2017.
 */
public class Requests extends JFrame {
    private JRadioButton jRB1_queryResult;
    private JRadioButton jRB2_tableOfTokens;
    private JCheckBox jChB3_IncludeSemantic;
    private JCheckBox jChB2_IncludeParser;
    private JCheckBox jChB1_Regexp;
    private JComboBox jComboBox1;
    private JButton jB1_Send;
    private JTable jTable1;
    private JLabel jLabel2;
    private JPanel rootPanel;

    // ! программа настроенна на базу данных по адресу F:\\CARS.sqlite, но сама БД прилагается в папке программы

    Connection con = null;
    //PreparedStatement ps = null;
    ResultSet rs = null;
    Statement st = null;
    private DefaultTableModel model;
    ButtonGroup bg1 = new ButtonGroup();
    private int selectedCB = 0;
    static boolean connectedDB = false, connectedSyntax = false;
    File syntax;

    /**
     * Creates new form Requests
     */
    Requests() {
        super("Аналіз запиту до бази даних CARS");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        Connect cnct = new Connect();
        con = cnct.getCon();
        jTable1.setVisible(false);
        bg1.add(jRB1_queryResult);
        bg1.add(jRB2_tableOfTokens);
        if (connectedDB) {
            bg1.setSelected(jRB1_queryResult.getModel(), true);
        } else {
            bg1.setSelected(jRB2_tableOfTokens.getModel(), true);
        }
        syntax = new File("d:/syntaxis.txt");
        jChB2_IncludeParser.setSelected(true);
        jChB3_IncludeSemantic.setSelected(true);
        jB1_Send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPressed();
            }
        });
        jComboBox1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonPressed();
                }
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    getHelp();
                }
            }
        });
        jComboBox1.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                try {
                    if (e.getWheelRotation() < 0) {
                        if (jComboBox1.getSelectedIndex() == 0) {
                            jComboBox1.setSelectedIndex(jComboBox1.getItemCount() - 1);
                        } else {
                            jComboBox1.setSelectedIndex(jComboBox1.getSelectedIndex() - 1);
                        }
                    } else {
                        if (jComboBox1.getSelectedIndex() == (jComboBox1.getItemCount() - 1)) {
                            jComboBox1.setSelectedIndex(0);
                        } else {
                            jComboBox1.setSelectedIndex(jComboBox1.getSelectedIndex() + 1);
                        }
                    }
                    selectedCB = jComboBox1.getSelectedIndex();
                } catch (IllegalArgumentException e1) {
                    jComboBox1.setSelectedIndex(selectedCB);
                }
            }
        });
        jTable1.addKeyListener(new KeyAdapter() {
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
                        System.out.println("Connection closed.");
                    } catch (SQLException ex) {
                        System.err.println("SQLException in formWindowClosing: " + ex);
                    }
                }
            }
        });
    }

    private void buttonPressed() {
        model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        jTable1.setVisible(true);
        if (jChB2_IncludeParser.isSelected()) {
            if (!preParser(syntax)) {
                jChB2_IncludeParser.setSelected(false);
                jChB2_IncludeParser.setEnabled(false);
                return;
            }
        }
        if (jChB3_IncludeSemantic.isSelected()) {
            semantic(jComboBox1.getSelectedItem().toString());
        }
        if (bg1.getSelection() == jRB1_queryResult.getModel()) {
            if (!connectedDB) {
                AdditionMethods.showMessageDialog(rootPane, "Ви не під'єднані до бази даних. Виконання запитів неможливе.", "Помилка з'єднання", "warning");
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

    private void getHelp() {
        JOptionPane.showMessageDialog(rootPane, "Правила по керуванню БД:\n"
                + " - ім’я таблиці складається із сукупності літер і цифр, що починається з літери. Довжина не більше 8 символів\n"
                + " - ім’я атрибута складається із сукупності літер, цифр, символів «_» і «-», що починається з літери або символа «_». Довжина не більше 16 символів", "Спрощення та обмеження", JOptionPane.INFORMATION_MESSAGE);
    }

    public String whichToken(String word) {
        if (jChB1_Regexp.isSelected()) { // регулярки
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
            if (AdditionMethods.mathOpFSM(word)) {
                return "math_op";
            } else if (AdditionMethods.keywordFSM(word)) {
                return "keyword";
            } else if (AdditionMethods.tablenameFSM(word)) {
                return "tablename(id2)";
            } else if (AdditionMethods.fieldFSM(word)) {
                return "field(id3)";
            } else if (AdditionMethods.relationFSM(word)) {
                return "relation";
            } else if (AdditionMethods.numberFSM(word)) {
                return "num";
            } else if (AdditionMethods.valueFSM(word)) {
                return "value";
            }
        }
        return "!invalid lexem";
    }

    public boolean preParser(File file) {
        String line, readLine = "";
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            connectedSyntax = true;
            while ((line = br.readLine()) != null) {
                readLine += line;
            }
        } catch (FileNotFoundException ex) {
            int op = JOptionPane.showConfirmDialog(rootPane, "Файлу з заданим синтаксисом не знайдено.\nВиберіть потрібний файл.");
            if (op == 0) {
                try {
                    syntax = AdditionMethods.setNewFile();
                } catch (FileNotFoundException ex1) {
                    AdditionMethods.showMessageDialog(rootPane, "Синтаксичний аналізатор не працює без заданого синтаксису.", "Синтаксичний аналізатор", "error");
                    return false;
                }
                connectedSyntax = true;
            } else {
                AdditionMethods.showMessageDialog(rootPane, "Синтаксичний аналізатор не працює без заданого синтаксису.", "Синтаксичний аналізатор", "error");
                return false;
            }
        } catch (IOException ex) {
            AdditionMethods.showMessageDialog(rootPane, ex.toString());
            return false;
        }
        if (connectedSyntax) {
            return parcer(readLine);
        }
        return false;
    }

    private boolean parcer(String text) {
        StringTokenizer token = new StringTokenizer(text, " \n\t");
        ArrayList<String> al = new ArrayList<>();
        while (token.hasMoreTokens()) {
            al.add(token.nextToken());
        }
        int pos = 0, tokenCounter = 0;
        String prev = "";
        token = new StringTokenizer(jComboBox1.getSelectedItem().toString(), " \t\n\r,=*/><();+", true);
        while (token.hasMoreTokens()) {
            String s = token.nextToken();
            if (!(s.equals(" ") | s.equals("\n") | s.equals("\t") | s.equals(";"))) {
                if (al.get(tokenCounter).equals("fields")) {
                    while (!s.equalsIgnoreCase("FROM")) {
                        if (" ".equals(s)) {
                            if (!" ".equals(prev) & !",".equals(prev) & !"field(id3)".equals(whichToken(prev)) & !"*".equals(prev)) {
                                AdditionMethods.showParcerMessage("','", "' '", pos);
                                return false;
                            }
                        } else if (",".equals(s)) {
                            if (" ".equals(prev) & ",".equals(prev)) {
                                AdditionMethods.showParcerMessage("<next token>", "','", pos);
                                return false;
                            }
                        } else if (!s.equals("*") & !AdditionMethods.fieldFSM(s.toUpperCase())) {
                            AdditionMethods.showParcerMessage("fieldname", s, pos);
                            return false;
                        }
                        pos += s.length();
                        prev = s;
                        s = token.nextToken();
                    }
                    tokenCounter++;
                } else if (al.get(tokenCounter).equals("field") & !AdditionMethods.fieldFSM(s.toUpperCase())) {
                    AdditionMethods.showParcerMessage("fieldname", s, pos);
                    return false;
                } else if (al.get(tokenCounter).equals("table") & !AdditionMethods.tablenameFSM(s.toUpperCase())) {
                    AdditionMethods.showParcerMessage("tablename", s, pos);
                    return false;
                } else if (!(al.get(tokenCounter).equals("field") & AdditionMethods.fieldFSM(s.toUpperCase()))
                        & !(al.get(tokenCounter).equals("table") & AdditionMethods.tablenameFSM(s.toUpperCase()))) {
                    if (!al.get(tokenCounter).equalsIgnoreCase(s)) {
                        AdditionMethods.showParcerMessage(al.get(tokenCounter), s, pos);
                        return false;
                    }
                }
                tokenCounter++;
            }
            prev = s;
            pos += s.length();
        }
        return true;
    }

    public boolean semantic(String request) {
        StringTokenizer stk = new StringTokenizer(request, " \t\n\r,=*/><();+", true);
        String prev = "", current;
        int pos = 0;
        while (stk.hasMoreTokens()) {
            current = stk.nextToken();
            // = num or field
            if (!(" ".equals(current) | "\t".equals(current) | ";".equals(current) | "\r".equals(current))) {
                if (AdditionMethods.relationFSM(prev)) {
                    if (!AdditionMethods.numberFSM(current)) {
                        if (!AdditionMethods.fieldFSM(current)) {
                            AdditionMethods.showMessageDialog(rootPane, "Після знаків порівняння повинно йти тільки число або поле.", "Семантичний аналізатор", "error");
                            return false;
                        }
                    }
                }
                if (AdditionMethods.mathOpFSM(prev)) {
                    if (!AdditionMethods.numberFSM(current)) {
                        if (!AdditionMethods.fieldFSM(current)) {
                            AdditionMethods.showMessageDialog(rootPane, "Після знака математичної операції повинно йти тільки число або поле.", "Семантичний аналізатор", "error");
                            return false;
                        }
                    }
                }
                prev = current;
            }
            pos += current.length();
        }
        return true;
    }

    private void requestResult() {
        try {
            model.setColumnCount(0);
            st = con.createStatement();
            rs = st.executeQuery(jComboBox1.getSelectedItem().toString());
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
                    jTable1.setValueAt(rs.getObject(j + 1), i, j);
                }
                i++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
            System.err.println(ex);
        } finally {
            try {
                rs.close();
                System.out.println("ResultSet closed.");
                st.close();
                System.out.println("Statement closed.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, ex);
                System.err.println(ex);
            }
        }
    }

    private void tokenTable() {
        model.setColumnCount(5);
        Object[] colIdent = {"Токен", "Лексема", "Початок", "Довжина", "Адреса"};
        model.setColumnIdentifiers(colIdent);
        String line = jComboBox1.getSelectedItem().toString();
        StringTokenizer stk = new StringTokenizer(line, " \t\n\r,=*/><();+", true);
        String s;
        int row = 0, pos = 1;
        while (stk.hasMoreTokens()) {
            s = stk.nextToken();
            if (!(s.equals(" ") | s.equals(",") | s.equals("(") | s.equals(")") | s.equals(";"))) {
                model.setRowCount(model.getRowCount() + 1);
                if (row > 0) {
                    if (s.equals("*") & "keyword".equalsIgnoreCase(jTable1.getValueAt(row - 1, 0).toString())) {
                        jTable1.setValueAt("field(id3)", row, 0);
                        jTable1.setValueAt(s, row, 1);
                        jTable1.setValueAt(pos, row, 2);
                        jTable1.setValueAt(s.length(), row, 3);
                    } else {
                        if ("tablename(id2)".equals(whichToken(s)) & !jTable1.getValueAt(row - 1, 1).toString().toUpperCase().matches("FROM|JOIN|TABLE")) {
                            jTable1.setValueAt("field(id3)", row, 0);
                        } else {
                            jTable1.setValueAt(whichToken(s), row, 0);
                        }
                        jTable1.setValueAt(s, row, 1);
                        jTable1.setValueAt(pos, row, 2);
                        jTable1.setValueAt(s.length(), row, 3);
                        if (s.equals("=") & ("<".equalsIgnoreCase(jTable1.getValueAt(row - 1, 1).toString()) | ">".equalsIgnoreCase(jTable1.getValueAt(row - 1, 1).toString()))) {
                            jTable1.setValueAt(jTable1.getValueAt(row - 1, 1) + "=", row - 1, 1);
                            jTable1.setValueAt(s.length() + (int) jTable1.getValueAt(row - 1, 3), row - 1, 3);
                            model.setRowCount(model.getRowCount() - 1);
                            row -= 1;
                        } else if (s.equals(">") & ("<".equalsIgnoreCase(jTable1.getValueAt(row - 1, 1).toString()))) {
                            jTable1.setValueAt(jTable1.getValueAt(row - 1, 1) + ">", row - 1, 1);
                            jTable1.setValueAt(s.length() + (int) jTable1.getValueAt(row - 1, 3), row - 1, 3);
                            model.setRowCount(model.getRowCount() - 1);
                            row -= 1;
                        }
                    }
                } else {
                    jTable1.setValueAt(whichToken(s), row, 0);
                    jTable1.setValueAt(s, row, 1);
                    jTable1.setValueAt(pos, row, 2);
                    jTable1.setValueAt(s.length(), row, 3);
                }
                if (jTable1.getValueAt(row, 0).toString().matches("field\\(id3\\)|tablename\\(id2\\)")) {
                    jTable1.setValueAt(Math.round(Math.random() * 10000), row, 4);
                } else {
                    jTable1.setValueAt("", row, 4);
                }
                row += 1;
            }// equals " ,"
            pos += s.length();
        } // while
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            if (jTable1.getValueAt(i, 0) == "!invalid lexem") {
                JOptionPane.showMessageDialog(rootPane, "Увага! Запит містить невизначені лексеми", "Лексичний аналізатор", JOptionPane.WARNING_MESSAGE);
                break;
            }
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Requests r = new Requests();
//                r.setVisible(true);
//                r.setTitle("Аналіз запиту до бази даних CARS");
            }
        });
    }
}
