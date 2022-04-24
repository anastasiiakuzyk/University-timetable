//package HelpWindows;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.Dictionary;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import DataTables.SqlActions.SqlClient;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.scene.input.MouseEvent;
//import sample.Main;
//
//public class AuthWindow {
//
//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;
//
//
//    @FXML
//    private PasswordField passField;
//
//    @FXML
//    private TextField loginField;
//
//    @FXML
//    private Button loginBtn;
//
//    @FXML
//    private Label forgetLabel;
//
//    @FXML
//    private Button registerBtn;
//
//    @FXML
//    private AnchorPane page;
//
//    private static TextField clone;
//    public static Stage dialog;
//    public static Scene authScene;
//
//    @FXML
//    void register_click(ActionEvent event) throws IOException {
//        RegisterWindow.ShowRegister();
//    }
//
//    @FXML
//    void forgetLabel_Click(javafx.scene.input.MouseEvent event) {
//        System.out.println("Forget clicked");
//    }
//
//    @FXML
//    void loginBtn_Click(javafx.event.ActionEvent event) {
//        List<Dictionary<String, String>> userAcaunts = SqlClient.CreateCommand(
//                String.format("select userlogin, userpassword, username from %s", SqlClient.Tables.LOGINS)).ExecObjects();
//
//        for (Dictionary<String, String> acount : userAcaunts)
//        {
////            try
////            {
//////                if (acount.get("userlogin").equals(loginField.getText()) &&
////                        acount.get("userpassword").equals(passField.getText()))
////                {
////                    MessageWindow.ShowMessage("Успех",
////                            String.format("Здавствуйте, %s", acount.get("username")),
////                            MessageWindow.State.SUCCESS, dialog);
////                }
////                else
////                {
////                    MessageWindow.ShowMessage("Ошибка", "Ошибка при входе.\nТакого акаунта нет",
////                            MessageWindow.State.FAILURE, dialog);
////                }
//            }
////            catch (IOException e)
////            {
////                e.printStackTrace();
////            }
//        }
//
//    @FXML
//    void lbl_enter(MouseEvent event) {
//        forgetLabel.setStyle("-fx-text-fill:#6E70FF");
//    }
//
//    @FXML
//    void lbl_exit(MouseEvent event) {
//        forgetLabel.setStyle("-fx-text-fill:black");
//    }
//
//    @FXML
//    void initialize() {
//        clone = loginField;
//    }
//
//    public static void ShowAuth() throws IOException
//    {
//        Parent root = FXMLLoader.load(AuthWindow.class.getResource("FXMLFiles/auth.fxml"));
//        dialog = new Stage();
//        Scene authScene = new Scene(root);
//        dialog.setTitle("Авторизация");
//        dialog.initModality(Modality.APPLICATION_MODAL);
//        dialog.initOwner(Main.PrimaryStage);
//        clone.requestFocus();
//        dialog.setScene(authScene);
//        dialog.showAndWait();
//    }
//
//    public static void ReturnToAuth() throws IOException {
//        Parent root = FXMLLoader.load(AuthWindow.class.getResource("FXMLFiles/auth.fxml"));
//        Scene authScene = new Scene(root);
//        dialog.setTitle("Авторизация");
//        clone.requestFocus();
//        dialog.setScene(authScene);
//    }
//}
//
