package db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {
    final String URL = "jdbc:derby:measurementDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";
    Connection conn;
    Statement createStatement;
    DatabaseMetaData dbmd;

    public DB() {

        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (conn != null) {
            try {
                createStatement = conn.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "CONTACTS", null);
            if (!rs.next()) {
                createStatement.execute("create table measurements(id INT not null primary key generated always as identity (start with 1, increment by 1), meastime timestamp (20), filename varchar(20), currentweight int(4), origoweight int (4))");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void addMeasurement(Person person) {
        try {
            String sql = "insert into contacts (lastname, firstname, email) values (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getLastName());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Hiba a contact hozzáadással");
            e.printStackTrace();
        }
    }

    public void addOrigoWeight(Person person) {
        try {
            String sql = "insert into contacts (lastname, firstname, email) values (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getLastName());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Hiba a contact hozzáadással");
            e.printStackTrace();
        }
    }
}