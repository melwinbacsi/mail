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
            ResultSet rs = dbmd.getTables(null, "MAIL", "MEASUREMENTS", null);
            if (!rs.next()) {
                createStatement.execute("create table measurements(id INT not null primary key generated always as identity (start with 1, increment by 1), meastime varchar (14), origotime varchar (14),actualweight int(4), origoweight int (4))");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addMeasurement(Measurement measurement) {
        try {
            String sql = "insert into measurements (meastime, origotime, actualweight, origoweight) values (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, measurement.getMeasTime());
            preparedStatement.setString(2, measurement.getOrigoTime());
            preparedStatement.setInt(2, measurement.getActualWeight());
            preparedStatement.setInt(3, measurement.getOrigoWeight());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getOrigoWeight() {
        int origoWeight = 0;
        String sql = "select origoweight from measurements order by meastime desc limit 1";
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            if(!rs.first()){
            origoWeight = rs.getInt("origoweight");}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return origoWeight;
    }

    public String getOrigoTime() {
        String origoTime = "";
        String sql = "select origotime from measurements order by meastime desc limit 1";
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            if(!rs.first()){
            origoTime = rs.getString("origotime");}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return origoTime;
    }
}