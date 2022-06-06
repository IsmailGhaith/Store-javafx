package store;

public class prod_cus {

    private Integer prodNo;
    private String prodName;
    private Double prodPrice;
    private Double prodQuantity;
    private Double prodCurrency;
    private Double prodTotal;
    private String prodUrl;
    private String pic;
    private Integer u_id;

    public prod_cus() {
    }

    public Integer getProdNo() {
        return prodNo;
    }

    public void setProdNo(Integer prodNo) {
        this.prodNo = prodNo;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public Double getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(Double prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    public Double getProdCurrency() {
        return prodCurrency;
    }

    public void setProdCurrency(Double prodCurrency) {
        this.prodCurrency = prodCurrency;
    }

    public Double getProdTotal() {
        return prodTotal;
    }

    public void setProdTotal(Double prodTotal) {
        this.prodTotal = prodTotal;
    }

    public String getProdUrl() {
        return prodUrl;
    }

    public void setProdUrl(String prodUrl) {
        this.prodUrl = prodUrl;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    @Override
    public String toString() {
        return "prod_cus{" + "prodNo=" + prodNo + ", prodName=" + prodName + ", prodPrice=" + prodPrice + ", prodQuantity=" + prodQuantity + ", prodCurrency=" + prodCurrency + ", prodTotal=" + prodTotal + ", prodUrl=" + prodUrl + ", pic=" + pic + ", u_id=" + u_id + '}';
    }

    public prod_cus(Integer prodNo, String prodName, Double prodPrice, Double prodQuantity, Double prodCurrency, Double prodTotal, String prodUrl, String pic, Integer u_id) {
        this.prodNo = prodNo;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodQuantity = prodQuantity;
        this.prodCurrency = prodCurrency;
        this.prodTotal = prodQuantity * prodPrice;
        this.prodUrl = prodUrl;
        this.pic = pic;
        this.u_id = u_id;
    }

}
