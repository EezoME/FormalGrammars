package edu.eezo.tafl;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Created by Eezo on 19.06.2017.
 */
public class Connect {
    private Connection con;

    public Connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:CARS.sqlite");
            Requests.connectedDB = true;
            System.out.println("Connection succesful.");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Клас для JDBC відсутній.");
            System.err.println("Connection failure.");
            System.exit(0);
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            int op = JOptionPane.showConfirmDialog(null, "Можливо, не вірно вказана адреса бази даних.\nБажаєте вказати місцезнахождення файлу?\nПримітка: программа не приймає назви з кирилицею.");
            if (op == 0) {
                try {
                    String newFileName = AdditionMethods.setNewFile().getAbsolutePath();
                    System.out.println("currentfile: " + newFileName);
                    con = DriverManager.getConnection("jdbc:sqlite:" + newFileName);
                    Requests.connectedDB = true;
                    System.out.println("Connection succesful.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Не вдалося під'єднатися до бази данних.");
                    System.exit(0);
                } catch (FileNotFoundException ex) {
                    System.err.println(ex);
                }
            } else if (op == 1)
                JOptionPane.showMessageDialog(null, "Без під'єднаної БД программа здатна тільки виводити таблицю токенів.");
        }
    }

    public Connection getCon() {
        return this.con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public static void main(String args[]) {
        //Connect testing = new Connect();
        /*try {
         testing.con.close();
         } catch (SQLException ex) {
         Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }
}
