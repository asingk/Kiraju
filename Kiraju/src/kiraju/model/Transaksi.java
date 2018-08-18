package kiraju.model;
// Generated May 18, 2017 12:05:47 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Transaksi generated by hbm2java
 */
public class Transaksi  implements java.io.Serializable {


     private int id;
     private Meja mejaId;
     private Date dtStart;
     private Date dtEnd;
     private Integer total;
     private Short status;
     private Set<Pesan> pesan = new HashSet<>(0);
     private String namaPemesan;
     private Users userStart;
     private Users userEnd;
     private Date endDtOnly;
     private Date endTimeOnly;
     private MetodePembayaran metodePembayaranId;
     private Pelanggan pelangganId;
     private Diskon diskonId;
     private Pajak pajakId;
     private Integer diskonTotal;
     private Integer pajakTotal;
     private Integer modalTotal;
     
     //20170419 - kiraju4
     private Date tanggal;
     private Boolean isLunas;

    public Transaksi() {
    }
    
    public Transaksi(int id) {
        this.id = id;
    }

    public Transaksi(Meja mejaId, Date dtStart, Date dtEnd, Short status, Set<Pesan> pesan, String namaPemesan, Users userStart, Users userEnd, Date endDtOnly, Date endTimeOnly, MetodePembayaran metodePembayaranId) {
       this.mejaId = mejaId;
       this.dtStart = dtStart;
       this.dtEnd = dtEnd;
       this.status = status;
       this.pesan = pesan;
       this.namaPemesan = namaPemesan;
       this.userStart = userStart;
       this.userEnd = userEnd;
       this.endDtOnly = endDtOnly;
       this.endTimeOnly = endTimeOnly;
       this.metodePembayaranId = metodePembayaranId;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Meja getMejaId() {
        return this.mejaId;
    }
    
    public void setMejaId(Meja mejaId) {
        this.mejaId = mejaId;
    }
    public Date getDtStart() {
        return this.dtStart;
    }
    
    public void setDtStart(Date dtStart) {
        this.dtStart = dtStart;
    }
    public Date getDtEnd() {
        return this.dtEnd;
    }
    
    public void setDtEnd(Date dtEnd) {
        this.dtEnd = dtEnd;
    }
    public Integer getTotal() {
        return this.total;
    }
    
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Short getStatus() {
        return this.status;
    }
    
    public void setStatus(Short status) {
        this.status = status;
    }
    public Set<Pesan> getPesan() {
        return this.pesan;
    }
    
    public void setPesan(Set<Pesan> pesan) {
        this.pesan = pesan;
    }
    public String getNamaPemesan() {
        return this.namaPemesan;
    }
    
    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }
    public Users getUserStart() {
        return this.userStart;
    }
    
    public void setUserStart(Users userStart) {
        this.userStart = userStart;
    }
    public Users getUserEnd() {
        return this.userEnd;
    }
    
    public void setUserEnd(Users userEnd) {
        this.userEnd = userEnd;
    }
    public Date getEndDtOnly() {
        return this.endDtOnly;
    }
    
    public void setEndDtOnly(Date endDtOnly) {
        this.endDtOnly = endDtOnly;
    }
    public Date getEndTimeOnly() {
        return this.endTimeOnly;
    }
    
    public void setEndTimeOnly(Date endTimeOnly) {
        this.endTimeOnly = endTimeOnly;
    }

    public MetodePembayaran getMetodePembayaranId() {
        return metodePembayaranId;
    }

    public void setMetodePembayaranId(MetodePembayaran metodePembayaranId) {
        this.metodePembayaranId = metodePembayaranId;
    }

    public Pelanggan getPelangganId() {
        return pelangganId;
    }

    public void setPelangganId(Pelanggan pelangganId) {
        this.pelangganId = pelangganId;
    }

    public Diskon getDiskonId() {
        return diskonId;
    }

    public void setDiskonId(Diskon diskonId) {
        this.diskonId = diskonId;
    }

    public Pajak getPajakId() {
        return pajakId;
    }

    public void setPajakId(Pajak pajakId) {
        this.pajakId = pajakId;
    }

    public Integer getDiskonTotal() {
        return diskonTotal;
    }

    public void setDiskonTotal(Integer diskonTotal) {
        this.diskonTotal = diskonTotal;
    }

    public Integer getPajakTotal() {
        return pajakTotal;
    }

    public void setPajakTotal(Integer pajakTotal) {
        this.pajakTotal = pajakTotal;
    }

    public Integer getModalTotal() {
        return modalTotal;
    }

    public void setModalTotal(Integer modalTotal) {
        this.modalTotal = modalTotal;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public Boolean getIsLunas() {
        return isLunas;
    }

    public void setIsLunas(Boolean isLunas) {
        this.isLunas = isLunas;
    }




}


