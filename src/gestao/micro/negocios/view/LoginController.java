package gestao.micro.negocios.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import gestao.micro.negocios.MainApp;
import gestao.micro.negocios.dao.UserDAO;


/**
 *
 * @author upper
 */
public class LoginController {
    private UserDAO dataAccessor ;
    
    @FXML
    private TextField user;
    @FXML
    private TextField password;

    private MainApp mainApp;
    
    @FXML
    private void initialize() {
    }

    @FXML
    private void handleEntrar() throws Exception {
        dataAccessor = new UserDAO("jdbc:mysql://remotemysql.com:3306/8BqaG7Joaq?zeroDateTimeBehavior=convertToNull", "8BqaG7Joaq", "KZHhe6stfM");
        if(!dataAccessor.ValidateUser(user.getText(), password.getText()))
            mainApp.showDashboard();
        else    {
            user.setText("Usuário não foi encontrado!");
            password.setText("");
        }
    }

    @FXML
    private void handleRegister() {
        mainApp.showRegister();
    }

    @FXML
    private void handleLimpar() {
        user.setText("");
        password.setText("");
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
