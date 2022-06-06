package store;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

public class AdduserController implements Initializable {

    @FXML
    private TextField txtID;
    @FXML
    private TextField txtUseradd;
    @FXML
    private TextField txtpass;
    @FXML
    private Button addbtn;
    @FXML
    private Button cancelbtn;

    private void message(String header, String content) {
        Dialog d = new Dialog();
        d.setHeaderText(header);
        d.setTitle(Store.title);
        d.setContentText(content);
        ButtonType btnOk = new ButtonType("موافق", ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().add(btnOk);
        d.showAndWait();
    }

    protected static int createuser(user u) {
        Jdbc jdbc = new Jdbc();
        return Jdbc.addUser(u);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addbtn.setOnAction(e -> {
            user u = new user();

            u.setU_id(Integer.valueOf(this.txtID.getText().trim()));
            u.setU_name(this.txtUseradd.getText().trim());
            u.setU_pass(this.txtpass.getText().trim());
            if (createuser(u) == 1) {
                message("save record", "The update process has been completed successfully");

            }

        });
        cancelbtn.setOnAction(eh->{
            txtID.setText(null);
            txtUseradd.setText(null);
            txtpass.setText(null);
            txtID.requestFocus();
        });
    }
}
