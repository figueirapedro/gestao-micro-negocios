/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestao.micro.negocios.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author Escola
 */
public class LogDAO {
    
    private final Connection connection;

    public LogDAO(String dbURL, String user, String password) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(dbURL, user, password);
    }
    
    public LogDAO() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/8BqaG7Joaq?zeroDateTimeBehavior=convertToNull", "8BqaG7Joaq", "KZHhe6stfM");
    }
    
    public void GenerateLog(String action) throws SQLException{
        Statement stmnt = connection.createStatement();
        try{
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            stmnt.executeUpdate("INSERT INTO `logs` (`date`, `action`) VALUES ('"+formatter.format(date)+"', '"+action+"')");
        } finally   {
            stmnt.close();
        }        
    };
    
}
