package kiraju.model;
// Generated Mar 29, 2018 3:23:13 PM by Hibernate Tools 4.3.1



/**
 * DaftarPembelian generated by hbm2java
 */
public class DaftarPembelian  implements java.io.Serializable {


     private int id;
     private Integer banyak;
     private Integer harga;
//     private MenuItem menuItemCode;
//     private TransaksiPembelian trxPembelianId;
     private Integer trxPembelianId;
     private String namaProduk;

    public DaftarPembelian() {
    }

	
    public DaftarPembelian(int id) {
        this.id = id;
    }
    public DaftarPembelian(int id, Integer banyak, Integer harga, String namaProduk, Integer trxPembelianId) {
       this.id = id;
       this.banyak = banyak;
       this.harga = harga;
//       this.menuItemCode = menuItemCode;
       this.trxPembelianId = trxPembelianId;
       this.namaProduk = namaProduk;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Integer getBanyak() {
        return this.banyak;
    }
    
    public void setBanyak(Integer banyak) {
        this.banyak = banyak;
    }
    public Integer getHarga() {
        return this.harga;
    }
    
    public void setHarga(Integer harga) {
        this.harga = harga;
    }
//    public MenuItem getMenuItemCode() {
//        return this.menuItemCode;
//    }
//    
//    public void setMenuItemCode(MenuItem menuItemCode) {
//        this.menuItemCode = menuItemCode;
//    }
    public Integer getTrxPembelianId() {
        return this.trxPembelianId;
    }
    
    public void setTrxPembelianId(Integer trxPembelianId) {
        this.trxPembelianId = trxPembelianId;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }




}

