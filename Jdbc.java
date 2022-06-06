package store;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class Jdbc {
    
    private static Connection con;
    private static Statement stmt;
    
    public Jdbc() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.01:3306/shop?user=root&password=");
            stmt = con.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    protected static int addUser(user u) {
        int rowStatus = 0;
        try {
            String SQL = "INSERT INTO users (u_id,u_name,u_pass)";
            SQL += "VALUES(" + u.getU_id() + ",'" + u.getU_name() + "','" + u.getU_pass() + "');";
            try {
                if (con != null && !con.isClosed()) {
                    rowStatus = stmt.executeUpdate(SQL);
                } else {
                    System.out.println("Error in Connection...");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage() + " ggg");
            }
        } catch (Exception ex) {
        }
        return rowStatus;
    }
    
    protected static int addProduct(prod_cus p) {
        int rowStatus = 0;
        String SQL = "INSERT INTO products(Item_Number,Item_Name,Purchasing_Price,quantities,currency,total_,url_,pic_,u_id)";
        SQL += "VALUES(" + p.getProdNo() + ",'" + p.getProdName() + " '," + p.getProdPrice() + "," + p.getProdQuantity() + ",";
        SQL += " " + p.getProdCurrency() + "," + p.getProdTotal() + ",'" + p.getProdUrl() + "',' " + p.getPic() + " '," + p.getU_id() + ");";
        try {
            if (con != null && !con.isClosed()) {
                rowStatus = stmt.executeUpdate(SQL);
            } else {
                System.out.println("Error in Connection...");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " ggg");
        }
        return rowStatus;
    }
    
    protected static int updateProduct(prod_cus p) {
        int rowStatus = 0;
        String SQL = "UPDATE products SET  Item_Name='" + p.getProdName() + "',Purchasing_Price=" + p.getProdPrice()
                + ",quantities=" + p.getProdQuantity()
                + ",currency=" + p.getProdCurrency() + ",total_=" + p.getProdTotal() + ",url_=' " + p.getProdUrl() + " ',pic_=' " + p.getPic()
                + " ' " + " WHERE Item_Number=" + p.getProdNo() + ";";
        
        try {
            if (con != null && !con.isClosed()) {
                rowStatus = stmt.executeUpdate(SQL);
            } else {
                System.out.println("Error in Connection...");
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + "tttttttttttttttttttt");
            
        }
        return rowStatus;
    }
    
    protected static int deleteProduct(int prodNo) {
        int rowStatus = 0;
        try {
            if (con != null && !con.isClosed()) {
                rowStatus = stmt.executeUpdate("DELETE FROM products WHERE Item_Number=" + prodNo + ";");
            } else {
                System.out.println("Error in Connection...");
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " ggg");
        }
        return rowStatus;
    }
    
    protected static int changePass(user u) {
        int rowStatus = 0;
        
        String SQL = "UPDATE  users SET u_name='" + u.getU_name() + "',u_pass='" + u.getU_pass() + "'WHERE u_id=" + u.getU_id() + ";";
        try {
            if (con != null && !con.isClosed()) {
                rowStatus = stmt.executeUpdate(SQL);
            } else {
                System.out.println("Error in connection");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + "errortttt");
        }
        return rowStatus;
    }
    
    protected void getProduct(String Fill, TableView tb) {
        ObservableList<prod_cus> items = FXCollections.observableArrayList();
        try {
            var r = stmt.executeQuery("SELECT * FROM products " + Fill);
            while (r.next()) {
                prod_cus product = new prod_cus(
                        Integer.valueOf(r.getString("Item_Number")), r.getString("Item_Name"),
                        Double.valueOf(r.getString("Purchasing_Price")), Double.valueOf(r.getString("quantities")),
                        Double.valueOf(r.getString("currency")), Double.valueOf(r.getString("total_")),
                        r.getString("url_"), r.getString("pic_"), Integer.valueOf(r.getString("u_id")));
                items.add(product);
            }
        } catch (NumberFormatException | SQLException ex) {
            System.out.println(ex.getMessage() + " 3333333");
        }
        
        tb.setItems(items);
    }
    
    protected static prod_cus getProductByNo(int pNo) {
        prod_cus p = new prod_cus();
        try {
            var r = stmt.executeQuery("SELECT * FROM products WHERE Item_Number=" + pNo);
            while (r.next()) {
                p.setProdNo(Integer.valueOf(r.getString("Item_Number")));
                p.setProdName(r.getString("Item_Name"));
                p.setProdCurrency(Double.valueOf(r.getString("currency")));
                p.setProdPrice(Double.valueOf(r.getString("Purchasing_Price")));
                p.setProdQuantity(Double.valueOf(r.getString("quantities")));
                p.setPic(r.getString("pic_"));
                p.setProdName(r.getString("url_"));
                p.setProdTotal(Double.valueOf(r.getString("total_")));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " gg,lkoioooooooooooooooooooooo");
        }
        return p;
    }
    
    private void closeConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
        }
    }
}
