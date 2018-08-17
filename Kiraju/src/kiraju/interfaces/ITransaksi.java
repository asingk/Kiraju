/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.interfaces;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import javafx.collections.ObservableList;
import kiraju.model.Laporan;
import kiraju.model.Pelanggan;
import kiraju.model.Transaksi;
import kiraju.property.PesanProperty;
import kiraju.property.TransaksiProperty;

/**
 *
 * @author arvita
 */
public interface ITransaksi {
    ObservableList<PesanProperty> getbyMeja(short mejaId);
    ObservableList<TransaksiProperty> getBungkus();
//    int insertByNama(String nama, short userId);
    void updateStatus(Transaksi transaksi);
    int insertByMeja(short mejaActive, String userId);
    void updateMejaNo(Transaksi transaksi);
    void updateBayar(Transaksi transaksi);
    void deleteById(Transaksi transaksi);
    ObservableList<TransaksiProperty> getPemasukanByTglAndUser(LocalDate localDate, String userId);
    List getChartByBulan(int bulan);
    ObservableList<Integer> getYear();
    List getChartByTahun(int tahun);
    List<Laporan> getLaporan(LocalDate tglDari, LocalDate tglSampai);
    List<Laporan> getLaporanPenjualan(LocalDate tglDari, LocalDate tglSampai);
    int insert(String userId);
    void updateSimpan(Transaksi transaksi);
    void updateStatusOnly(Transaksi transaksi);
    ObservableList<TransaksiProperty> getTransaksiByPelanggan(Pelanggan pelanggan);
    void updateStatusBayar(Transaksi transaksi);
    ObservableList<TransaksiProperty> getMetodePembayaranByTglAndUser(LocalDate localDate, String userId);
    Laporan getLaporanPenjualan2(LocalDate tglDari, LocalDate tglSampai);
    Laporan getlabaRugi(LocalDate tglDari, LocalDate tglSampai);
    Laporan getlabaRugi(YearMonth yearMonth);
}
