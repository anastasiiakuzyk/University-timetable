//package HelpWindows;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import sample.Main;
//
//public class RegisterWindow {
//
//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;
//
//    @FXML
//    private TextField login_field;
//
//    @FXML
//    private TextField name_field;
//
//    @FXML
//    private PasswordField pass_field;
//
//    @FXML
//    private Button registerBtn;
//
//    @FXML
//    private Button backBtn;
//
//    private static Stage window = AuthWindow.dialog;
//    private static Scene registerScene;
//    private static TextField loginClone;
//
//    @FXML
//    void back_click(ActionEvent event) throws IOException {
//        AuthWindow.ReturnToAuth();
//    }
//
//    @FXML
//    void register_click(ActionEvent event) throws IOException {
//        if (!login_field.getText().equals("allow"))
//        {
//            // Animation
//            return;
//        }
//        // add to database
//    }
//
//    @FXML
//    void initialize()
//    {
//        loginClone = login_field;
//    }
//
//    public static void ShowRegister() throws IOException {
//        Parent root = FXMLLoader.load(RegisterWindow.class.getResource("FXMLFiles/register.fxml"));
//        registerScene = new Scene(root);
//        window.setTitle("Авторизация");
//        window.setScene(registerScene);
//        loginClone.requestFocus();
//    }
//
//}
