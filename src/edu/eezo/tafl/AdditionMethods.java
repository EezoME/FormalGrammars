package edu.eezo.tafl;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class AdditionMethods {
    private static JFileChooser fch = new JFileChooser();
    // This class contains helps methods for Requests

    // A-Z : 65-90
    // 0-9 : 48-57
    // '_':95   // '-':45  // '(':40  // ')':41 // '%':37  // '\':39  // '"':34
    // A-Я : 1040-1071   Ё : 1025   І : 1030

    /**
     * Checks if given word is a mathematician operation symbol (using Finite-State Machine).
     *
     * @param word given word
     * @return <b>true</b> if it is, <b>false</b> - otherwise
     */
    public static boolean mathOpFSM(String word) {
        int i = 0, state = 0;

        while (true) {
            try {
                char c = word.charAt(i);
                switch (state) {
                    case -1:
                        return false;
                    case 0:
                        if (c == '=' || c == '*' || c == '/' || c == '+' || c == '-') {
                            state = -1;
                            break;
                        }
                }
            } catch (StringIndexOutOfBoundsException e) {
                return state == -1;
            }

            i++;
        }
    }

    /**
     * Checks if given word is a keyword (using Finite-State Machine).
     *
     * @param word given word
     * @return <b>true</b> if it is, <b>false</b> - otherwise
     */
    public static boolean keywordFSM(String word) {
        word = word.toUpperCase();
        int i = 0, state = 0;

        while (true) {
            try {
                char c = word.charAt(i);
                switch (state) {
                    case -1:
                        return false;
                    case 0:
                        if (c == 'S') {
                            state = 1;
                            break;
                        }
                        if (c == 'F') {
                            state = 11;
                            break;
                        }
                        if (c == 'W') {
                            state = 21;
                            break;
                        }
                        if (c == 'J') {
                            state = 31;
                            break;
                        }
                        if (c == 'I') {
                            state = 41;
                            break;
                        }
                        if (c == 'O') {
                            state = 51;
                            break;
                        }
                        if (c == 'A') {
                            state = 61;
                            break;
                        }
                        return false;
                    case 1:
                        if (c == 'E') {
                            state = 2;
                            break;
                        }
                        return false;
                    case 2:
                        if (c == 'L') {
                            state = 3;
                            break;
                        }
                        return false;
                    case 3:
                        if (c == 'E') {
                            state = 4;
                            break;
                        }
                        return false;
                    case 4:
                        if (c == 'C') {
                            state = 5;
                            break;
                        }
                        return false;
                    case 5:
                        if (c == 'T') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 11:
                        if (c == 'R') {
                            state = 12;
                            break;
                        }
                        return false;
                    case 12:
                        if (c == 'O') {
                            state = 13;
                            break;
                        }
                        return false;
                    case 13:
                        if (c == 'M') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 21:
                        if (c == 'H') {
                            state = 22;
                            break;
                        }
                        return false;
                    case 22:
                        if (c == 'E') {
                            state = 23;
                            break;
                        }
                        return false;
                    case 23:
                        if (c == 'R') {
                            state = 24;
                            break;
                        }
                        return false;
                    case 24:
                        if (c == 'E') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 31:
                        if (c == 'O') {
                            state = 32;
                            break;
                        }
                        return false;
                    case 32:
                        if (c == 'I') {
                            state = 33;
                            break;
                        }
                        return false;
                    case 33:
                        if (c == 'N') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 41:
                        if (c == 'N') {
                            state = 42;
                            break;
                        }
                        return false;
                    case 42:
                        if (c == 'N') {
                            state = 43;
                            break;
                        }
                        return false;
                    case 43:
                        if (c == 'E') {
                            state = 44;
                            break;
                        }
                        return false;
                    case 44:
                        if (c == 'R') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 51:
                        if (c == 'N') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 61:
                        if (c == 'S') {
                            state = -1;
                            break;
                        }
                        return false;
                }
            } catch (StringIndexOutOfBoundsException e) {
                return state == -1;
            }

            i++;
        }
    }

    /**
     * Checks if given word is a table name (using Finite-State Machine).
     *
     * @param word given word
     * @return <b>true</b> if it is, <b>false</b> - otherwise
     */
    public static boolean tablenameFSM(String word) {
        word = word.toUpperCase();
//        word.matches("[A-Z]{1}[A-Z0-9]{1,7}");
        int i = 0, state = 0;

        while (true) {
            try {
                char c = word.charAt(i);
                switch (state) {
                    case -1:
                        return false;
                    case 0:
                        if (isAZ(c)) {
                            state = 1;
                            break;
                        }
                        return false;
                    case 1:
                        if (is09(c) || isAZ(c)) {
                            state = 2;
                            break;
                        }
                        return false;
                    case 2:
                        if (is09(c) || isAZ(c)) {
                            state = 3;
                            break;
                        }
                        return false;
                    case 3:
                        if (is09(c) || isAZ(c)) {
                            state = 4;
                            break;
                        }
                        return false;
                    case 4:
                        if (is09(c) || isAZ(c)) {
                            state = 5;
                            break;
                        }
                        return false;
                    case 5:
                        if (is09(c) || isAZ(c)) {
                            state = 6;
                            break;
                        }
                        return false;
                    case 6:
                        if (is09(c) || isAZ(c)) {
                            state = 7;
                            break;
                        }
                        return false;
                    case 7:
                        if (is09(c) || isAZ(c)) {
                            state = -1;
                            break;
                        }
                        return false;
                }
            } catch (StringIndexOutOfBoundsException e) {
                return true;
            }

            i++;
        }
    }

    /**
     * Checks if given word is a field name (using Finite-State Machine).
     *
     * @param word given word
     * @return <b>true</b> if it is, <b>false</b> - otherwise
     */
    public static boolean fieldFSM(String word) {
        word = word.toUpperCase();
// word.matches("\\*|([A-Z]{1}[A-Z0-9]{0,7}\\.)?[A-Z\\_]{1}[A-Z0-9\\_-]{0,15}");
        int i = 0, state = 0;

        while (true) {
            try {
                char c = word.charAt(i);
                switch (state) {
                    case -1:
                        return false;
                    case 0:
                        if (c == '*') {
                            state = -1;
                            break;
                        }
                        if (isAZ(c)) {
                            state = 1;
                            break;
                        }
                        if (c == '_') {
                            state = 10;
                            break;
                        }
                        return false;
                    case 1:
                        if (is09(c) || isAZ(c)) {
                            state = 2;
                            break;
                        }
                        if (c == '_' || c == '-') {
                            state = 11;
                            break;
                        }
                        if (c == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 2:
                        if (is09(c) || isAZ(c)) {
                            state = 3;
                            break;
                        }
                        if (c == '_' || c == '-') {
                            state = 12;
                            break;
                        }
                        if (c == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 3:
                        if (is09(c) || isAZ(c)) {
                            state = 4;
                            break;
                        }
                        if (c == '_' || c == '-') {
                            state = 13;
                            break;
                        }
                        if (c == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 4:
                        if (is09(c) || isAZ(c)) {
                            state = 5;
                            break;
                        }
                        if (c == '_' || c == '-') {
                            state = 14;
                            break;
                        }
                        if (c == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 5:
                        if (is09(c) || isAZ(c)) {
                            state = 6;
                            break;
                        }
                        if (c == '_' || c == '-') {
                            state = 15;
                            break;
                        }
                        if (c == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 6:
                        if (is09(c) || isAZ(c)) {
                            state = 7;
                            break;
                        }
                        if (c == '_' || c == '-') {
                            state = 16;
                            break;
                        }
                        if (c == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 7:
                        if (is09(c) || isAZ(c)) {
                            state = 8;
                            break;
                        }
                        if (c == '_' || c == '-') {
                            state = 17;
                            break;
                        }
                        if (c == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 8:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 18;
                            break;
                        }
                        if (c == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 9:
                        if (is09(c) || isAZ(c) || c == '_') {
                            state = 10;
                            break;
                        }
                        return false;
                    case 10:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 11;
                            break;
                        }
                        return false;
                    case 11:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 12;
                            break;
                        }
                        return false;
                    case 12:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 13;
                            break;
                        }
                        return false;
                    case 13:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 14;
                            break;
                        }
                        return false;
                    case 14:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 15;
                            break;
                        }
                        return false;
                    case 15:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 16;
                            break;
                        }
                        return false;
                    case 16:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 17;
                            break;
                        }
                        return false;
                    case 17:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 18;
                            break;
                        }
                        return false;
                    case 18:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 19;
                            break;
                        }
                        return false;
                    case 19:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 20;
                            break;
                        }
                        return false;
                    case 20:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 21;
                            break;
                        }
                        return false;
                    case 21:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 22;
                            break;
                        }
                        return false;
                    case 22:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 23;
                            break;
                        }
                        return false;
                    case 23:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = 24;
                            break;
                        }
                        return false;
                    case 24:
                        if (is09(c) || isAZ(c) || c == '_' || c == '-') {
                            state = -1;
                            break;
                        }
                        return false;
                }
            } catch (StringIndexOutOfBoundsException e) {
                return true;
            }

            i++;
        }
    }

    /**
     * Checks if given word is a relation symbol (using Finite-State Machine).
     *
     * @param word given word
     * @return <b>true</b> if it is, <b>false</b> - otherwise
     */
    public static boolean relationFSM(String word) {
        int i = 0, state = 0;

        while (true) {
            try {
                char c = word.charAt(i);
                switch (state) {
                    case -1:
                        return false;
                    case 0:
                        if (c == '>' | c == '<') {
                            state = -1;
                            break;
                        }
                }
            } catch (StringIndexOutOfBoundsException e) {
                return state == -1;
            }

            i++;
        }
    }

    /**
     * Checks if given word is a number (using Finite-State Machine).
     *
     * @param word given word
     * @return <b>true</b> if it is, <b>false</b> - otherwise
     */
    public static boolean numberFSM(String word) {
        int i = 0, state = 0;

        while (true) {
            try {
                char c = word.charAt(i);
                switch (state) {
                    case 0:
                        if (c == '-') {
                            state = 1;
                            break;
                        }
                        if (c == '0') {
                            state = 2;
                            break;
                        }
                        if (c > '0' && c <= '9') {
                            state = 3;
                            break;
                        }
                        return false;
                    case 1:
                        if (c == '0') {
                            state = 2;
                            break;
                        }
                        if (c > '0' && c <= '9') {
                            state = 3;
                            break;
                        }
                        return false;
                    case 2:
                        if (c == '.') {
                            state = 4;
                            break;
                        }
                        return false;
                    case 3:
                        if (is09(c)) {
                            break;
                        }
                        if (c == '.') {
                            state = 4;
                            break;
                        }
                        return false;
                    case 4:
                        if (is09(c)) {
                            break;
                        }
                        return false;
                }
            } catch (StringIndexOutOfBoundsException e) {
                return state > 1;
            }

            i++;
        }
    }

    /**
     * Checks if given word is a value (using Finite-State Machine).
     *
     * @param word given word
     * @return <b>true</b> if it is, <b>false</b> - otherwise
     */
    public static boolean valueFSM(String word) {
        word = word.toUpperCase();
        int i = 0, state = 0;

        while (true) {
            try {
                char c = word.charAt(i);
                switch (state) {
                    case -1:
                        return false;
                    case 0:
                        if (c == '\'') {
                            state = 1;
                            break;
                        }
                        if (c == '"') {
                            state = 4;
                            break;
                        }
                        return false;
                    case 1:
                        if (isAZ(c) || isAZRusUkr(c) || is09(c) || c == '(' || c == ')' || c == '"') {
                            state = 2;
                            break;
                        }
                        if (c == '\'') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 2:
                        if (isAZ(c) || isAZRusUkr(c) || is09(c) || c == '(' || c == ')' || c == '"' || c == '%' || c == '-' || c == '_') {
                            state = 3;
                            break;
                        }
                        if (c == '\'') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 3:
                        if (isAZ(c) || isAZRusUkr(c) || is09(c) || c == '(' || c == ')' || c == '"' || c == '%' || c == '-' || c == '_') {
                            state = 3;
                            break;
                        }
                        if (c == '\'') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 4:
                        if (isAZ(c) || isAZRusUkr(c) || is09(c) || c == '(' || c == ')' || c == '\'') {
                            state = 5;
                            break;
                        }
                        if (c == '"') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 5:
                        if (isAZ(c) || isAZRusUkr(c) || is09(c) || c == '(' || c == ')' || c == '\'' || c == '%' || c == '-' || c == '_') {
                            state = 6;
                            break;
                        }
                        if (c == '"') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 6:
                        if (isAZ(c) || isAZRusUkr(c) || is09(c) || c == '(' || c == ')' || c == '\'' || c == '%' || c == '-' || c == '_') {
                            state = 6;
                            break;
                        }
                        if (c == '"') {
                            state = -1;
                            break;
                        }
                        return false;
                }
            } catch (StringIndexOutOfBoundsException e) {
                return state == -1;
            }

            i++;
        }
    }

    public static void showMessageDialog(Component component, String text, String title, String type) {
        int mesType;

        if (type.equalsIgnoreCase("info")) mesType = JOptionPane.INFORMATION_MESSAGE;
        else if (type.equalsIgnoreCase("error")) mesType = JOptionPane.ERROR_MESSAGE;
        else if (type.equalsIgnoreCase("warning")) mesType = JOptionPane.WARNING_MESSAGE;
        else if (type.equalsIgnoreCase("question")) mesType = JOptionPane.QUESTION_MESSAGE;
        else if (type.equalsIgnoreCase("plain")) mesType = JOptionPane.PLAIN_MESSAGE;
        else {
            JOptionPane.showMessageDialog(component, text);
            return;
        }

        JOptionPane.showMessageDialog(component, text, title, mesType);
    }

    public static void showMessageDialog(Component component, String text) {
        showMessageDialog(component, text, "Повідомлення", "plain");
    }

    public static void showParserMessage(String expected, String actual, int position) {
        JOptionPane.showMessageDialog(null, "Syntax error at pos:" + position
                + "\n\texpected: " + expected + "\n\tactual: " + actual, "Синтаксичний аналізатор", JOptionPane.ERROR_MESSAGE);
    }

    public static File setNewFile() throws FileNotFoundException {
        fch.setCurrentDirectory(new File("f:/"));
        if (fch.showDialog(null, "Вибрати файл") == JFileChooser.CANCEL_OPTION) {
            throw new FileNotFoundException();
        }
        return fch.getSelectedFile();
    }

    private static boolean isAZ(char c) {
        return (c >= 'A' && c <= 'Z');
    }

    private static boolean is09(char c) {
        return (c >= '0' && c <= '9');
    }

    private static boolean isAZRusUkr(char c) {
        return (c >= 'А' && c <= 'Я' || c == 'Ё' || c == 'І');
    }
}
