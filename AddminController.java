package store;
import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddminController implements Initializable {
    
    @FXML
    private Button adduserbtn;
    @FXML
    private Label LabelUser;
    @FXML
    private Button aboutbtn;
    @FXML
    private TextField txtNo;
    @FXML
    private Button SearchNo;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtQuntity;
    @FXML
    private ComboBox<Curency> currrencycombo;
    @FXML
    private ImageView imgvstPic;
    @FXML
    private Button imgbtn;
    @FXML
    private TextField txt_url;
    @FXML
    private Button gobtn;
    @FXML
    private TextField txtTotal;
    @FXML
    private Button delbtn;
    @FXML
    private Button newbtn;
    @FXML
    private TableView<prod_cus> tbProd;
    @FXML
    private TableColumn<prod_cus, Integer> colNo;
    @FXML
    private TableColumn<prod_cus, Integer> colName;
    @FXML
    private TableColumn<prod_cus, Integer> colprice;
    @FXML
    private TableColumn<prod_cus, Integer> colquantity;
    @FXML
    private TableColumn<prod_cus, Integer> colcurrency;
    @FXML
    private TableColumn<prod_cus, Integer> coltotal;
    @FXML
    private TableColumn<prod_cus, Integer> colurl;
    @FXML
    private Button btnExit;
    
    protected void getMessage(String message) {
        this.LabelUser.setText(message);
    }
    
    private void message(String header, String content) {
        Dialog d = new Dialog();
        d.setHeaderText(header);
        d.setTitle(Store.title);
        d.setContentText(content);
        ButtonType btnOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().add(btnOk);
        d.showAndWait();
    }
    
    public static void exit() {
        Dialog d = new Dialog();
        d.setHeaderText("Exit the app...");
        d.setTitle(Store.title);
        d.setContentText("Are you sure you want to exit the app?");
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        d.getDialogPane().getButtonTypes().addAll(btnYes, btnNo);
        if (d.showAndWait().get() == btnYes) {
            Platform.exit();
        }
    }
    
    private void fillCurrency() {
        ObservableList<Curency> items = FXCollections.observableArrayList(
                new Curency(1, "Dollar"), new Curency(2, "shekel"),
                new Curency(3, "Dinar"), new Curency(4, "euro"));
        this.currrencycombo.setItems(items);
    }
    
    private void fillTable() {
        colNo.setCellValueFactory(new PropertyValueFactory("prodNo"));
        colName.setCellValueFactory(new PropertyValueFactory("prodName"));
        colprice.setCellValueFactory(new PropertyValueFactory("prodPrice"));
        colquantity.setCellValueFactory(new PropertyValueFactory("prodQuantity"));
        colcurrency.setCellValueFactory(new PropertyValueFactory("prodCurrency"));
        coltotal.setCellValueFactory(new PropertyValueFactory("prodTotal"));
        colurl.setCellValueFactory(new PropertyValueFactory("prodUrl"));
        Jdbc jdbc = new Jdbc();
        jdbc.getProduct(" ", this.tbProd);
    }
    
    private void newRecord() {
        this.txtNo.requestFocus();
        this.delbtn.setDisable(true);
        this.txtNo.setText(null);
        this.txtName.setText(null);
        this.txtPrice.setText(null);
        this.txtQuntity.setText(null);
        this.txtTotal.setText(null);
        this.currrencycombo.setValue(null);
        this.imgvstPic.setImage(null);
    }
    
    private int addRecord(prod_cus product) {
        Jdbc jdbc = new Jdbc();
        return Jdbc.addProduct(product);
    }
    
    private void deleteRecord(int pNo) {
        Dialog d = new Dialog();
        d.setHeaderText("Delete Record...");
        d.setTitle(Store.title);
        d.setContentText("Do you want to delete the record?");
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        d.getDialogPane().getButtonTypes().addAll(btnYes, btnNo);
        if (d.showAndWait().get() == btnYes) {
            Jdbc jdbc = new Jdbc();
            if (Jdbc.deleteProduct(pNo) == 1) {
                message("Delete Record", "The deletion process has been successful");
            }
        }
    }
    
    @FXML
    public void handleMouseAction(MouseEvent event) {
        newRecord();
        prod_cus p = this.tbProd.getSelectionModel().getSelectedItem();
        this.txtNo.setText(p.getProdNo().toString());
        this.txtName.setText(p.getProdName());
        this.txtPrice.setText(p.getProdPrice().toString());
        this.txtQuntity.setText(p.getProdQuantity().toString());
        this.txtTotal.setText(p.getProdTotal().toString());
        this.txt_url.setText(p.getProdUrl());
    }
        public double calcTotal() {
        double t = (Double.valueOf(this.txtPrice.getText())) * (Double.valueOf(this.txtQuntity.getText()));
        return t;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCurrency();
        fillTable();
                txtTotal.setOnAction(eh -> {
            txtTotal.setText(String.valueOf(calcTotal()));
            
        });
        gobtn.setOnAction(e -> {
            Desktop d = Desktop.getDesktop();
            try {
                d.browse(new URI(this.txt_url.getText()));
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " errory");
            }
        });
        
        btnExit.setOnAction(e -> {
            exit();
        });
        
        SearchNo.setOnAction((var e) -> {
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("search for Item");
            td.setTitle(Store.title);
            td.setContentText("enter product's number");
            td.showAndWait();
            String stNo1 = td.getEditor().getText().toString();
            if (stNo1.length() > 0) {
                int pNo = Integer.valueOf(td.getEditor().getText());
                prod_cus p = new prod_cus();
                p = Jdbc.getProductByNo(pNo);
                this.txtName.setText(p.getProdName());
                this.txtNo.setText(p.getProdNo().toString());
                this.txtPrice.setText(String.valueOf(p.getProdPrice()));
                this.txtQuntity.setText(p.getProdQuantity().toString());
                this.txtTotal.setText(p.getProdTotal().toString());
                this.txt_url.setText(p.getProdUrl());
                this.delbtn.setDisable(false);
            } else {
                message("error", "rrrrrr");
            }
        });
        
        newbtn.setOnAction(e -> {
            newRecord();
            
        });
        adduserbtn.setOnAction(e -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("adduser.fxml"));
                Parent root = loader.load();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " ooo");
            }
        });

        aboutbtn.setOnAction(e -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
                Parent root = loader.load();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " ccc");
            }
        });
    }
    
}
 
    

