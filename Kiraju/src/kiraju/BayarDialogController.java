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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kiraju.implement.MejaModel;
import kiraju.implement.TransaksiModel;
import kiraju.interfaces.IMeja;
import kiraju.interfaces.ITransaksi;
import kiraju.model.Meja;
import kiraju.model.Transaksi;
import kiraju.model.Users;
import kiraju.property.PesanProperty;
import kiraju.util.CommonConstant;

/**
 *
 * @author arvita
 */
public class BayarDialogController implements Initializable{
    @FXML
    private TableView<PesanProperty> tableDetail;
    @FXML
    private TableColumn<PesanProperty, String> namaColumn;
    @FXML
    private TableColumn<PesanProperty, String> hargaColumn;
    @FXML
    private TableColumn<PesanProperty, Integer> jumlahColumn;
    @FXML
    private Text mejaNo;
    @FXML
    private Text total;
    @FXML
    private Text tunai;
    @FXML
    private Text kembali;
    
    private Stage dialogStage;
    private String mejaActive = "";
    private String namaPemesan = "";
    private ObservableList<PesanProperty> menuMejaObsList = FXCollections.observableArrayList();
    private final ITransaksi iTransaksi = new TransaksiModel();
    private final IMeja iMeja = new MejaModel();
    private Integer totalBayar = 0;
    private boolean okClicked;
    private Users loginUser;
    private String judulBayar = "";
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        jumlahColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty().asObject());
    }
    
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    
    public void initValue(ObservableList<PesanProperty> menuMejaObsList, Integer tunai, Users loginUsers) {
        this.menuMejaObsList = menuMejaObsList;
        this.loginUser = loginUsers;
        tableDetail.setItems(menuMejaObsList);
        if(menuMejaObsList != null && menuMejaObsList.size() > 0){
            mejaActive = String.valueOf(menuMejaObsList.get(0).getMejaId());
            namaPemesan = menuMejaObsList.get(0).getNamaPemesan();
            totalBayar = menuMejaObsList.stream().map((pesanProperty) -> pesanProperty.getTotalHarga()).reduce(totalBayar, Integer::sum);
        }
        total.setText(numberFormat.format(totalBayar));
        judulBayar = namaPemesan != null && !"".equals(namaPemesan) ? namaPemesan : "Meja " + mejaActive;
        mejaNo.setText(judulBayar);
        this.tunai.setText(numberFormat.format(tunai));
        Integer kembalian = tunai - totalBayar;
        this.kembali.setText(numberFormat.format(kembalian));
    }
    
    @FXML
    private void tutupBtn(ActionEvent event) {
        dialogStage.close();
    }
    
    @FXML
    private void okBtn(ActionEvent actionEvent) {
        Transaksi transaksi = new Transaksi();
        transaksi.setId(menuMejaObsList.get(0).getTransaksiId());
        transaksi.setStatus(CommonConstant.TRANSAKSI_BAYAR);
        transaksi.setTotal(totalBayar);
        transaksi.setUserEnd(loginUser);
        iTransaksi.updateBayar(transaksi);
        if(menuMejaObsList.get(0).getMejaId() > 0){
            Meja meja = new Meja();
            meja.setId(menuMejaObsList.get(0).getMejaId());
            meja.setStatus(CommonConstant.MEJA_TERSEDIA);
            iMeja.update(meja);
        }
        okClicked = true;
        dialogStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}