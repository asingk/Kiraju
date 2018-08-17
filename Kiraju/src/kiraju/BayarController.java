/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kiraju.implement.MenuItemModel;
import kiraju.implement.MetodePembayaranModel;
import kiraju.implement.PelangganModel;
import kiraju.implement.TransaksiModel;
import kiraju.interfaces.IMenuItem;
import kiraju.interfaces.IMetodePembayaran;
import kiraju.interfaces.IPelanggan;
import kiraju.interfaces.ITransaksi;
import kiraju.model.MetodePembayaran;
import kiraju.model.Pelanggan;
import kiraju.model.Transaksi;
import kiraju.property.PesanProperty;
import kiraju.util.CommonConstant;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author arvita
 */
public class BayarController implements Initializable{
    
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));
    
    @FXML
    private ChoiceBox<String> metode;
    @FXML
    private TextField uang;
    @FXML
    private ComboBox<String> bayarPelangganComboBox;
    @FXML
    private Button bayarBtn;
    @FXML
    private Text jumlahBayar;
    @FXML
    private Text totalHarga;
    @FXML
    private Text kembalian;
    
    private ObservableList<PesanProperty> pesanMenuItemOrderedObsList = FXCollections.observableArrayList();
    
    private Stage dialogStage;
    private final IMetodePembayaran iMetodePembayaran = new MetodePembayaranModel();
    private final ITransaksi iTransaksi = new TransaksiModel();
    private final IPelanggan iPelanggan = new PelangganModel();
    private final IMenuItem iMenuItem = new MenuItemModel();
//    private int transaksiId;
//    private Integer totalHargaPesan;
//    private Users loginUser;
    private boolean okClicked;
//    private Integer pesanGlobalDiskon;
//    private Integer pesanGlobalPajak;
    private Transaksi transaksi;
    private Short status;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        metode.setItems(iMetodePembayaran.getAllNama());
        metode.getSelectionModel().selectFirst();
        TextFields.bindAutoCompletion(bayarPelangganComboBox.getEditor(), (AutoCompletionBinding.ISuggestionRequest t) -> {
            return iPelanggan.searchPelangganById(t.getUserText());
        });
//        bayarBtn.disableProperty().bind(Bindings.isEmpty(uang.textProperty()).or(Bindings.lessThan(uang.textProperty(), totalHarga.getText())));
    }

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    @FXML
    private void tutupBtn(ActionEvent event) {
        dialogStage.close();
    }

    void initValue(Transaksi transaksi, short status, ObservableList<PesanProperty> pesanMenuItemOrderedObsList) {
//        transaksiId = transaksi.getId();
//        totalHargaPesan = transaksi.getTotal();
//        loginUser = transaksi.getUserEnd();
//        pesanGlobalDiskon = transaksi.getDiskonTotal();
//        pesanGlobalPajak = transaksi.getPajakTotal();
        this.transaksi = transaksi;
        this.status = status;
        this.pesanMenuItemOrderedObsList = pesanMenuItemOrderedObsList;
        
        uang.textProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && !uang.getText().isEmpty()) {
                Integer bayar = Integer.valueOf(newValue);
                uang.setText(bayar.toString());
                jumlahBayar.setText(numberFormat.format(bayar));
                int kembali = bayar - transaksi.getTotal();
                kembalian.setText(numberFormat.format(kembali));
                if(kembali >= 0) {
                    bayarBtn.setDisable(false);
                }else{
                    bayarBtn.setDisable(true);
                }
            }else{
                uang.setText("0");
            }
        });
        totalHarga.setText(numberFormat.format(transaksi.getTotal()));
    }
    
    @FXML
    private void okBtn(ActionEvent actionEvent) {
        transaksi.setStatus(CommonConstant.TRANSAKSI_BAYAR);
        MetodePembayaran metodePembayaran = new MetodePembayaran();
        metodePembayaran.setId(iMetodePembayaran.getIdByName(metode.getValue()));
        transaksi.setMetodePembayaranId(metodePembayaran);
        if(null != bayarPelangganComboBox.getValue() && !bayarPelangganComboBox.getEditor().getText().isEmpty() && bayarPelangganComboBox.getEditor().getText().contains("-")){
            String pelanggan = bayarPelangganComboBox.getValue();
            String subString = pelanggan.substring(0, pelanggan.indexOf("-"));
            String pelangganId = subString.trim();
            transaksi.setPelangganId(new Pelanggan(pelangganId));
        }
        if(status.equals(CommonConstant.TRANSAKSI_TERSIMPAN)){
            iTransaksi.updateStatusBayar(transaksi);
        }else{
            iTransaksi.updateBayar(transaksi);
        }
        iMenuItem.updateStokBayar(pesanMenuItemOrderedObsList);
        okClicked = true;
        dialogStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    
    public int getUang() {
        int tunai = 0;
        if(null != uang.getText()){
            tunai = Integer.parseInt(uang.getText());
        }
        return tunai;
    }
    
    
}
