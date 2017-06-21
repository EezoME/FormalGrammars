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
    public static boolean mathOpFSM(String word) {
        int i = 0, state = 0;
        while (true) {
            try {
                char s = word.charAt(i);
                switch (state) {
                    case -1:
                        return false;
                    case 0:
                        if (s == '=' | s == '*' | s == '/' | s == '+' | s == '-') {
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

    public static boolean keywordFSM(String word) {
        word = word.toUpperCase();
        int i = 0, state = 0;
        while (true) {
            try {
                char s = word.charAt(i);
                switch (state) {
                    case -1:
                        return false;
                    case 0:
                        if (s == 'S') {
                            state = 1;
                            break;
                        }
                        if (s == 'F') {
                            state = 11;
                            break;
                        }
                        if (s == 'W') {
                            state = 21;
                            break;
                        }
                        if (s == 'J') {
                            state = 31;
                            break;
                        }
                        if (s == 'I') {
                            state = 41;
                            break;
                        }
                        if (s == 'O') {
                            state = 51;
                            break;
                        }
                        if (s == 'A') {
                            state = 61;
                            break;
                        }
                        return false;
                    case 1:
                        if (s == 'E') {
                            state = 2;
                            break;
                        }
                        return false;
                    case 2:
                        if (s == 'L') {
                            state = 3;
                            break;
                        }
                        return false;
                    case 3:
                        if (s == 'E') {
                            state = 4;
                            break;
                        }
                        return false;
                    case 4:
                        if (s == 'C') {
                            state = 5;
                            break;
                        }
                        return false;
                    case 5:
                        if (s == 'T') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 11:
                        if (s == 'R') {
                            state = 12;
                            break;
                        }
                        return false;
                    case 12:
                        if (s == 'O') {
                            state = 13;
                            break;
                        }
                        return false;
                    case 13:
                        if (s == 'M') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 21:
                        if (s == 'H') {
                            state = 22;
                            break;
                        }
                        return false;
                    case 22:
                        if (s == 'E') {
                            state = 23;
                            break;
                        }
                        return false;
                    case 23:
                        if (s == 'R') {
                            state = 24;
                            break;
                        }
                        return false;
                    case 24:
                        if (s == 'E') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 31:
                        if (s == 'O') {
                            state = 32;
                            break;
                        }
                        return false;
                    case 32:
                        if (s == 'I') {
                            state = 33;
                            break;
                        }
                        return false;
                    case 33:
                        if (s == 'N') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 41:
                        if (s == 'N') {
                            state = 42;
                            break;
                        }
                        return false;
                    case 42:
                        if (s == 'N') {
                            state = 43;
                            break;
                        }
                        return false;
                    case 43:
                        if (s == 'E') {
                            state = 44;
                            break;
                        }
                        return false;
                    case 44:
                        if (s == 'R') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 51:
                        if (s == 'N') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 61:
                        if (s == 'S') {
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

    public static boolean tablenameFSM(String word) {
        word = word.toUpperCase();
//        word.matches("[A-Z]{1}[A-Z0-9]{1,7}");
        int i = 0, state = 0;
        while (true) {
            try {
                char s = word.charAt(i);
                switch (state) {
                    case -1:
                        return false;
                    case 0:
                        if (s >= 65 & s <= 90) {
                            state = 1;
                            break;
                        }
                        return false;
                    case 1:
                        if ((s >= 48 & s <= 57) | (s >= 65 & s <= 90)) {
                            state = 2;
                            break;
                        }
                        return false;
                    case 2:
                        if ((s >= 48 & s <= 57) | (s >= 65 & s <= 90)) {
                            state = 3;
                            break;
                        }
                        return false;
                    case 3:
                        if ((s >= 48 & s <= 57) | (s >= 65 & s <= 90)) {
                            state = 4;
                            break;
                        }
                        return false;
                    case 4:
                        if ((s >= 48 & s <= 57) | (s >= 65 & s <= 90)) {
                            state = 5;
                            break;
                        }
                        return false;
                    case 5:
                        if ((s >= 48 & s <= 57) | (s >= 65 & s <= 90)) {
                            state = 6;
                            break;
                        }
                        return false;
                    case 6:
                        if ((s >= 48 & s <= 57) | (s >= 65 & s <= 90)) {
                            state = 7;
                            break;
                        }
                        return false;
                    case 7:
                        if ((s >= 48 & s <= 57) | (s >= 65 & s <= 90)) {
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

    public static boolean fieldFSM(String word) {
        word = word.toUpperCase();
// word.matches("\\*|([A-Z]{1}[A-Z0-9]{0,7}\\.)?[A-Z\\_]{1}[A-Z0-9\\_-]{0,15}");
        int i = 0, state = 0;
        while (true) {
            try {
                char s = word.charAt(i);
                switch (state) {
                    case -1:
                        return false;
                    case 0:
                        if (s == '*') {
                            state = -1;
                            break;
                        }
                        if (s >= 65 & s <= 90) {
                            state = 1;
                            break;
                        }
                        if (s == '_') {
                            state = 10;
                            break;
                        }
                        return false;
                    case 1:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57)) {
                            state = 2;
                            break;
                        }
                        if (s == '_' | s == '-') {
                            state = 11;
                            break;
                        }
                        if (s == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 2:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57)) {
                            state = 3;
                            break;
                        }
                        if (s == '_' | s == '-') {
                            state = 12;
                            break;
                        }
                        if (s == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 3:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57)) {
                            state = 4;
                            break;
                        }
                        if (s == '_' | s == '-') {
                            state = 13;
                            break;
                        }
                        if (s == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 4:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57)) {
                            state = 5;
                            break;
                        }
                        if (s == '_' | s == '-') {
                            state = 14;
                            break;
                        }
                        if (s == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 5:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57)) {
                            state = 6;
                            break;
                        }
                        if (s == '_' | s == '-') {
                            state = 15;
                            break;
                        }
                        if (s == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 6:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57)) {
                            state = 7;
                            break;
                        }
                        if (s == '_' | s == '-') {
                            state = 16;
                            break;
                        }
                        if (s == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 7:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57)) {
                            state = 8;
                            break;
                        }
                        if (s == '_' | s == '-') {
                            state = 17;
                            break;
                        }
                        if (s == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 8:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 18;
                            break;
                        }
                        if (s == '.') {
                            state = 9;
                            break;
                        }
                        return false;
                    case 9:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_') {
                            state = 10;
                            break;
                        }
                        return false;
                    case 10:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 11;
                            break;
                        }
                        return false;
                    case 11:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 12;
                            break;
                        }
                        return false;
                    case 12:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 13;
                            break;
                        }
                        return false;
                    case 13:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 14;
                            break;
                        }
                        return false;
                    case 14:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 15;
                            break;
                        }
                        return false;
                    case 15:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 16;
                            break;
                        }
                        return false;
                    case 16:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 17;
                            break;
                        }
                        return false;
                    case 17:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 18;
                            break;
                        }
                        return false;
                    case 18:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 19;
                            break;
                        }
                        return false;
                    case 19:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 20;
                            break;
                        }
                        return false;
                    case 20:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 21;
                            break;
                        }
                        return false;
                    case 21:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 22;
                            break;
                        }
                        return false;
                    case 22:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 23;
                            break;
                        }
                        return false;
                    case 23:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
                            state = 24;
                            break;
                        }
                        return false;
                    case 24:
                        if ((s >= 65 & s <= 90) | (s >= 48 & s <= 57) | s == '_' | s == '-') {
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

    public static boolean relationFSM(String word) {
        int i = 0, state = 0;
        while (true) {
            try {
                char s = word.charAt(i);
                switch (state) {
                    case -1:
                        return false;
                    case 0:
                        if (s == '>' | s == '<') {
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

    public static boolean numberFSM(String word) {
        int i = 0, state = 0;
        while (true) {
            try {
                char s = word.charAt(i);
                switch (state) {
                    case 0:
                        if (s == '-') {
                            state = 1;
                            break;
                        }
                        if (s == 48) {
                            state = 2;
                            break;
                        }
                        if (s > 48 & s <= 57) {
                            state = 3;
                            break;
                        }
                        return false;
                    case 1:
                        if (s == 48) {
                            state = 2;
                            break;
                        }
                        if (s > 48 & s <= 57) {
                            state = 3;
                            break;
                        }
                        return false;
                    case 2:
                        if (s == '.') {
                            state = 4;
                            break;
                        }
                        return false;
                    case 3:
                        if (s >= 48 & s <= 57) {
                            break;
                        }
                        if (s == '.') {
                            state = 4;
                            break;
                        }
                        return false;
                    case 4:
                        if (s >= 48 & s <= 57) {
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

    public static boolean valueFSM(String word) {
        word = word.toUpperCase();
        int i = 0, state = 0;
        while (true) {
            try {
                char s = word.charAt(i);
                switch (state) {
                    case -1:
                        return false;
                    case 0:
                        if (s == '\'') {
                            state = 1;
                            break;
                        }
                        if (s == '"') {
                            state = 4;
                            break;
                        }
                        return false;
                    case 1:
                        if ((s >= 65 & s <= 90) | (s >= 1040 & s <= 1071) | (s >= 48 & s <= 57) | s == 1025 | s == '(' | s == ')' | s == '"' | s == 1030) {
                            state = 2;
                            break;
                        }
                        if (s == '\'') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 2:
                        if ((s >= 65 & s <= 90) | (s >= 1040 & s <= 1071) | (s >= 48 & s <= 57) | s == 1025 | s == '(' | s == ')' | s == '"' | s == '%' | s == '-' | s == '_' | s == 1030) {
                            state = 3;
                            break;
                        }
                        if (s == '\'') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 3:
                        if ((s >= 65 & s <= 90) | (s >= 1040 & s <= 1071) | (s >= 48 & s <= 57) | s == 1025 | s == '(' | s == ')' | s == '"' | s == '%' | s == '-' | s == '_' | s == 1030) {
                            state = 3;
                            break;
                        }
                        if (s == '\'') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 4:
                        if ((s >= 65 & s <= 90) | (s >= 1040 & s <= 1071) | (s >= 48 & s <= 57) | s == 1025 | s == '(' | s == ')' | s == '\'' | s == 1030) {
                            state = 5;
                            break;
                        }
                        if (s == '"') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 5:
                        if ((s >= 65 & s <= 90) | (s >= 1040 & s <= 1071) | (s >= 48 & s <= 57) | s == 1025 | s == '(' | s == ')' | s == '\'' | s == '%' | s == '-' | s == '_' | s == 1030) {
                            state = 6;
                            break;
                        }
                        if (s == '"') {
                            state = -1;
                            break;
                        }
                        return false;
                    case 6:
                        if ((s >= 65 & s <= 90) | (s >= 1040 & s <= 1071) | (s >= 48 & s <= 57) | s == 1025 | s == '(' | s == ')' | s == '\'' | s == '%' | s == '-' | s == '_' | s == 1030) {
                            state = 6;
                            break;
                        }
                        if (s == '"') {
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

    public static void showParcerMessage(String expected, String actual, int position) {
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
}
