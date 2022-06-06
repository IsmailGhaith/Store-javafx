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

public class Ch_passController implements Initializable {

    @FXML
    private TextField u_id;
    @FXML
    private TextField u_name;
    @FXML
    private TextField o_pass;
    @FXML
    private TextField n_pass;
    @FXML
    private Button change;
    @FXML
    private Button cancel;

    private void message(String header, String content) {
        Dialog d = new Dialog();
        d.setHeaderText(header);
        d.setTitle(Store.title);
        d.setContentText(content);
        ButtonType btnOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().add(btnOk);
        d.showAndWait();
    }
     protected static int updatepass(user u) {
        Jdbc jdbc = new Jdbc();
        return Jdbc.changePass(u);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       change .setOnAction(e -> {
            user u = new user();
            u.setU_id(Integer.valueOf(this.u_id.getText().trim()));
            u.setU_name(this.u_name.getText().trim());
            u.setU_pass(this.n_pass.getText().trim());

            if (updatepass(u) == 1) {

                message("Update Record", "The update process has been completed successfully");

            } else {
                System.out.println("eeee");
            }
        });
       cancel.setOnAction(eh->{
           u_id.setText(null);
           u_name.setText(null);
           o_pass.setText(null);
           n_pass.setText(null);
           u_id.requestFocus();
       });
    }    
    
}
