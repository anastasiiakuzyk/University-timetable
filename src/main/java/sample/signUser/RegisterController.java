package sample.signUser;

import java.net.URL;
import java.util.ResourceBundle;

import DataTables.SqlActions.SqlClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Commands;
import sample.animations.Shake;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUnButton;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpSurname;

    @FXML
    private PasswordField codeField;

    @FXML
    private Button authSignInButton;

    @FXML
    void initialize() {
        signUnButton.setOnAction(event -> {
            registerNewUser();
        });
        authSignInButton.setOnAction(event -> {
            Commands.openNewWindow(authSignInButton, "/sample/signUser/auth.fxml");
        });
    }

    private void registerNewUser() {

        String firstName = signUpName.getText();
        String lastName = signUpSurname.getText();
        String userName = loginField.getText();
        String password = passwordField.getText();

        if (codeField.getText().equals("access") && !firstName.equals("") &&
                !lastName.equals("")&& !userName.equals("")&& !password.equals("")) {
            SqlClient.CreateCommand(String.format("INSERT INTO USERS (firstname,lastname,username,password) " +
                            "values('%s','%s','%s','%s')"
                    ,firstName,lastName,userName,password)).ExecNonQuarry();
            Commands.openNewWindow(authSignInButton, "/sample/signUser/auth.fxml");
        } else {
            Shake.wrongEnter(signUnButton);
        }
    }
}
