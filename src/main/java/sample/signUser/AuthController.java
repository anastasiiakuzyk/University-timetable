package sample.signUser;

import java.net.URL;
import java.util.Dictionary;
import java.util.List;
import java.util.ResourceBundle;

import DataTables.SqlActions.SqlClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Commands;
import sample.animations.Shake;

public class AuthController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button authSignInButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private Button backButton;

    @FXML
    void initialize() {
        authSignInButton.setOnAction(event -> {
            String loginText = loginField.getText().trim();
            String loginPassword = passwordField.getText().trim();

            if (!loginText.equals("") && !loginPassword.equals("")) {
                loginUser(loginText, loginPassword);
            } else {
                Shake.wrongEnter(loginField, passwordField);
            }
        });
        loginSignUpButton.setOnAction(event -> {
            Commands.openNewWindow(loginSignUpButton, "/sample/signUser/register.fxml");
        });
        backButton.setOnAction(event -> {
            Commands.openNewWindow(backButton, "/sample/sample.fxml");
        });
    }

    private void loginUser(String loginText, String loginPassword) {
        List<Dictionary<String, String>> accounts = SqlClient.CreateCommand("SELECT username, password FROM Users").ExecObjects();
        for (Dictionary<String, String> account : accounts) {
            if (account.get("username").equals(loginText) && account.get("password").equals(loginPassword)) {
                Commands.openNewWindow(loginSignUpButton, "/sample/admin.fxml");
                return;
            }
        }
        Shake.wrongEnter(loginField, passwordField);
    }
}

