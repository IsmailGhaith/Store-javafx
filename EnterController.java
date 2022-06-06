package store;

import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EnterController implements Initializable {

    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtPass;
    @FXML
    private Button entrybtn;
    @FXML
    private Button cancelbtn;
    private static Connection con;
    private static Statement stmt;
    public static ResultSet result;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String ur = "jdbc:mysql://127.0.0.1:3306/shop?user=root&password=";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(ur);
            stmt = con.createStatement();
            result = stmt.executeQuery("SELECT * FROM users;");
            entrybtn.setOnAction((var e) -> {
                try {
                    if (txtUser.getText().trim().equals("admin") && txtPass.getText().trim().equals("711711")) {
                        System.out.println("??");
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("addmin.fxml"));
                        Parent root = loader.load();
                        AddminController contr = loader.getController();
                        contr.getMessage(this.txtUser.getText());
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                    result.next();
                    if (txtUser.getText().trim().equals(result.getString("u_name")) && txtPass.getText().trim().equals(result.getString("u_pass"))) {
                        System.out.println("??");
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("estore.fxml"));
                        Parent root = loader.load();
                        EstoreController contr = loader.getController();
                        contr.getMessage(this.txtUser.getText());
                        stage.setScene(new Scene(root));
                        stage.show();

                    }

                } catch (Exception ex) {
                    System.out.println(ex.getMessage() + "errorttttt");
                }
            });
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EnterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EnterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
