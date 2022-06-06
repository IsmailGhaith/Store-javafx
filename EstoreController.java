package store;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class EstoreController implements Initializable {

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
    private Button savebtn;
    @FXML
    private Button delbtn;
    @FXML
    private Button newbtn;
    @FXML
    private Button btnExit;
    @FXML
    private TableView<prod_cus> tbProd;
    @FXML
    private TableColumn<prod_cus, Integer> colNo;
    @FXML
    private TableColumn<prod_cus, String> colName;
    @FXML
    private TableColumn<prod_cus, Integer> colprice;
    @FXML
    private TableColumn<prod_cus, Integer> colquantity;
    @FXML
    private TableColumn<prod_cus, Integer> colcurrency;
    @FXML
    private TableColumn<prod_cus, Integer> coltotal;
    @FXML
    private TableColumn<prod_cus, String> colurl;
    @FXML
    private Label LabelUser;
    @FXML
    private TextField txt_u_id;
    @FXML
    private Button change_passBtn;
    prod_cus product;

    String imagePath;

    Jdbc jdbc;

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
        d.setContentText("Are you sure you want to exit the application?");
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
        try {
            Jdbc jdbc = new Jdbc();
            jdbc.getProduct("WHERE u_id=" + EnterController.result.getString("u_id") + ";", tbProd);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "fff");
        }
    }

    private void newRecord() {
        this.txtNo.requestFocus();
        this.savebtn.setText("Save");
        this.delbtn.setDisable(true);
        this.txtNo.setText(null);
        this.txtName.setText(null);
        this.txtPrice.setText(null);
        this.txtQuntity.setText(null);
        this.txtTotal.setText(null);
        this.currrencycombo.setValue(null);
        this.imgvstPic.setImage(null);
    }

    private boolean isValidation() {
        boolean x = true;
        if (this.txtNo.getText().trim().length() == 0) {
            x = false;
        }
        if (this.txtName.getText().trim().length() == 0) {
            x = false;
        }
        if (this.txtPrice.getText().trim().length() == 0) {
            x = false;
        }
        if (this.txtQuntity.getText().trim().length() == 0) {
            x = false;
        }

        if (this.txt_url.getText().trim().length() == 0) {
            x = false;
        }
        if (this.currrencycombo.getValue() == null) {
            x = false;
        }
        return x;
    }

    private int addRecord(prod_cus product) {
        jdbc = new Jdbc();
        return Jdbc.addProduct(product);
    }

    private int updateRecord(prod_cus product) {
        jdbc = new Jdbc();//Create Connection
        return Jdbc.updateProduct(product);
    }

    private void deleteRecord(int pNo) {
        Dialog d = new Dialog();
        d.setHeaderText("Delete Record");
        d.setTitle(Store.title);
        d.setContentText("Are you sure you want to delete the Record?");
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        d.getDialogPane().getButtonTypes().addAll(btnYes, btnNo);
        if (d.showAndWait().get() == btnYes) {
            Jdbc jdb = new Jdbc();
            if (Jdbc.deleteProduct(pNo) == 1) {
                message("delete record", "The deletion process has been successful");
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

        this.savebtn.setText("Edit");
        this.delbtn.setDisable(false);
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
        btnExit.setOnAction(e -> {
            exit();
        });

        savebtn.setOnAction((var e) -> {
            if (isValidation()) {
                var p = new prod_cus();
                p.setProdNo(Integer.valueOf(this.txtNo.getText().trim()));
                p.setProdName(this.txtName.getText().trim());
                p.setProdPrice(Double.valueOf(this.txtPrice.getText().trim()));
                p.setProdQuantity(Double.valueOf(this.txtQuntity.getText().trim()));
                p.setProdTotal(Double.valueOf(this.txtTotal.getText().trim()));
                p.setProdUrl(this.txt_url.getText().trim());
                p.setProdCurrency(Double.valueOf(this.currrencycombo.getValue().toString().substring(0, 3)));
                p.setPic(imagePath);
                p.setU_id(Integer.valueOf(txt_u_id.getText()));
                if (savebtn.getText().equals("Save")) {
                    if (addRecord(p) == 1) {
                        fillTable();
                        message("save the Record", "Saved successfully");
                        this.savebtn.setText("Edit");
                        this.delbtn.setDisable(false);
                    }
                } else {
                    if (updateRecord(p) == 1) {
                        fillTable();
                        message("save the Record", "Editing completed successfully");
                        this.delbtn.setDisable(false);
                    }
                }
            } else {
                message("Save error", "There are empty fields that require data entry to complete saving");
            }
        });
        newbtn.setOnAction(e -> {
            newRecord();
        });

        imgbtn.setOnAction((ActionEvent e) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(Store.title);
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                imagePath = selectedFile.toURI().toString();
                imgvstPic.setImage(new Image(imagePath));
            }
        });

        delbtn.setOnAction(e -> {
            if (this.txtNo.getText().trim().length() > 0) {
                int pNo = Integer.valueOf(this.txtNo.getText().trim());
                if (pNo > 0) {
                    deleteRecord(pNo);
                    fillTable();
                    newRecord();
                } else {
                    System.out.println("error deleted");
                }
            }
        });
        SearchNo.setOnAction((var e) -> {
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("search for Item");
            td.setTitle(Store.title);
            td.setContentText("enter product's number");
            td.showAndWait();
            String stNo1 = td.getEditor().getText();
            if (stNo1.length() > 0) {
                int pNo = Integer.valueOf(td.getEditor().getText());
                prod_cus p = new prod_cus();
                p = Jdbc.getProductByNo(pNo);
                this.txtName.setText(p.getProdName());
                this.txtNo.setText(p.getProdNo().toString());
                this.txtPrice.setText(p.getProdPrice().toString());
                this.txtQuntity.setText(p.getProdQuantity().toString());
                this.txtTotal.setText(p.getProdTotal().toString());
                this.txt_url.setText(p.getProdUrl());
                this.delbtn.setDisable(false);
                this.savebtn.setText("Edit");
            } else {
                message("error", "error proccess");
            }
        });
        gobtn.setOnAction(e -> {

            Desktop d = Desktop.getDesktop();
            try {
                d.browse(new URI(this.txt_url.getText()));
            } catch (URISyntaxException | IOException ex) {
            }

        });
        change_passBtn.setOnAction(e -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ch_pass.fxml"));
                Parent root = loader.load();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "error password");
            }
        });
    }
}
