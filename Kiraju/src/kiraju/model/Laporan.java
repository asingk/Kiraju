/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.model;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author arvita
 */
public class Laporan {
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));
    
    private Date tgl;
    private Integer pemasukan;
    private Integer pengeluaran;
    //added Arvit@20170819 - v1.2 bug laporan
    private String daftarMenu;
    private String jenisMenu;
    private Integer jumlah;

    public Date getTgl() {
        return tgl;
    }

    public void setTgl(Date tgl) {
        this.tgl = tgl;
    }

    public Integer getPemasukan() {
        return pemasukan;
    }

    public void setPemasukan(Integer pemasukan) {
        this.pemasukan = pemasukan;
    }
    
    public String getPemasukanNumberFormat() {
        return numberFormat.format(pemasukan);
    }

    public Integer getPengeluaran() {
        return pengeluaran;
    }

    public void setPengeluaran(Integer pengeluaran) {
        this.pengeluaran = pengeluaran;
    }
    
    public String getPengeluaranNumberFormat() {
        return numberFormat.format(pengeluaran);
    }

    public String getDaftarMenu() {
        return daftarMenu;
    }

    public void setDaftarMenu(String daftarMenu) {
        this.daftarMenu = daftarMenu;
    }

    public String getJenisMenu() {
        return jenisMenu;
    }

    public void setJenisMenu(String jenisMenu) {
        this.jenisMenu = jenisMenu;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }
}
