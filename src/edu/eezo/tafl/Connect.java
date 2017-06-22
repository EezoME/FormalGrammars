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
            con = DriverManager.getConnection("jdbc:sqlite:TEST.sqlite");
            Requests.connectedDB = true;
            System.out.println(Data.DB_CONNECTION_SUCCESSFUL);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, Data.DB_MISSING_JDBC_CLASS);
            System.err.println(Data.DB_CONNECTION_FAILURE);
            System.exit(0);
        } catch (SQLException e) {
            System.out.println(Data.DB_CONNECTION_FAILURE);
            int op = JOptionPane.showConfirmDialog(null, Data.DB_WRONG_PATH);
            if (op == 0) {
                try {
                    String newFileName = AdditionMethods.getNewFile().getAbsolutePath();
                    System.out.println("currentfile: " + newFileName);
                    con = DriverManager.getConnection("jdbc:sqlite:" + newFileName);
                    Requests.connectedDB = true;
                    System.out.println(Data.DB_CONNECTION_SUCCESSFUL);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, Data.DB_CONNECTION_ERROR);
                    System.exit(0);
                } catch (FileNotFoundException ex) {
                    System.err.println(ex.getMessage());
                }
            } else if (op == 1)
                JOptionPane.showMessageDialog(null, Data.WITHOUT_DB_RESTRICTIONS);
        }
    }

    public Connection getCon() {
        return this.con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
}
