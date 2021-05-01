package gestao.micro.negocios.dao;

import gestao.micro.negocios.model.User;
import gestao.micro.negocios.dao.LogDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author upper
 */
public class UserDAO {
    private final Connection connection;

    public UserDAO(String dbURL, String user, String password) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(dbURL, user, password);
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void createPerson(String usrName, String usrEmail, String usrPass) throws SQLException, ClassNotFoundException {
        Statement stmnt = connection.createStatement();   
        
        try{
            if(!ValidateUser(usrName, usrEmail, usrPass))
                return;
            
            stmnt.executeUpdate("INSERT INTO `empresa` (`email`, `senha`, `nome`) VALUES ('"+usrName+"', '"+usrEmail+"', '"+usrPass+"')");
        } finally   {
            stmnt.close();
        }
    }

    public List<User> GetPersonList() throws SQLException, ClassNotFoundException {
        Statement stmnt = connection.createStatement();
        List<User> userList = new ArrayList<>();

        LogDAO a = null;
        try {
            a = new LogDAO();
            ResultSet rs = stmnt.executeQuery("select * from empresa");
            a.GenerateLog("Consulta na tabela EMPRESA, retorno Lista de usuário");
            
            while (rs != null && rs.next()) {
                String name = rs.getString("nome");
                String email = rs.getString("email");
                String pass = rs.getString("senha");
                User person = new User(name, email, pass);
                userList.add(person);
            }            
        } catch (InstantiationException | IllegalAccessException ex) {
            a.GenerateLog("Consulta na tabela EMPRESA, retorno Lista de usuário");
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);        
        } finally {
            stmnt.close();
        }
        
        return userList;
    }
    
    public boolean ValidateUser(String usrName, String usrEmail, String usrPass) throws SQLException, ClassNotFoundException{
        List<User> userList = new ArrayList<>(GetPersonList());
        
        for (User a : userList){
            if(a.getEmail().equals(usrEmail) || a.getName().equals(usrName) && (!usrEmail.equals("") || !usrPass.equals("")) )
                return false;        
        }
        
        return true;
    }
    
    public boolean ValidateUser(String usrEmail, String usrPass) throws SQLException, ClassNotFoundException{
        List<User> userList = new ArrayList<>(GetPersonList());
        
        for (User a : userList){
            if((a.getEmail().equals(usrEmail) || a.getPass().equals(usrPass)) && (!usrEmail.equals("") || !usrPass.equals("")) )
                return false;        
        }
        
        return true;
    }
}
