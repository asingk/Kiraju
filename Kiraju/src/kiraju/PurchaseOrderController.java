/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kiraju.implement.DaftarPembelianModel;
import kiraju.implement.PemasokModel;
import kiraju.implement.TransaksiPembelianModel;
import kiraju.interfaces.IDaftarPembelian;
import kiraju.interfaces.IPemasok;
import kiraju.interfaces.ITransaksiPembelian;
import kiraju.model.MetodePembayaran;
import kiraju.model.Pemasok;
import kiraju.model.TransaksiPembelian;
import kiraju.property.DaftarPembelianProperty;
import kiraju.property.TransaksiPembelianProperty;
import kiraju.util.Choice;
import org.apache.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author arvita
 */
public class PurchaseOrderController implements Initializable {
    
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));
    private final static Logger LOGGER = Logger.getLogger(PurchaseOrderController.class);
    
//    @FXML
//    private ComboBox<String> pemasokComboBox;
//    @FXML
//    private ChoiceBox<Choice> pemasokCB;
    @FXML
    private TextField produkTF;
    @FXML
    private TextField jumlahTF;
    @FXML
    private TextField hargaTF;
    @FXML
    private Button simpanBtn;
    @FXML
    private Button hapusBtn;
//    @FXML
//    private Button batalBtn;
//    @FXML
//    private Button okBtn;
    @FXML
    private TableView<DaftarPembelianProperty> daftarBeliTbl;
    @FXML
    private TableColumn<DaftarPembelianProperty, DaftarPembelianProperty> noClm;
    @FXML
    private TableColumn<DaftarPembelianProperty, String> produkClm;
    @FXML
    private TableColumn<DaftarPembelianProperty, String> hargaClm;
    @FXML
    private TableColumn<DaftarPembelianProperty, String> jumlahClm;
    @FXML
    private Text totalHarga;
    
    private Stage dialogStage;
    private boolean okClicked;
    
    private final IPemasok iPemasok = new PemasokModel();
    private final ITransaksiPembelian iTransaksiPembelian = new TransaksiPembelianModel();
    private final IDaftarPembelian iDaftarPembelian = new DaftarPembelianModel();
    
    private ObservableList<DaftarPembelianProperty> data = FXCollections.observableArrayList();
    private TransaksiPembelianProperty pembelianProperty;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        TextFields.bindAutoCompletion(pemasokComboBox.getEditor(), (AutoCompletionBinding.ISuggestionRequest t) -> {
//            return iPemasok.searchPemasokByNama(t.getUserText());
//        });
//        pemasokCB.getItems().addAll(iPemasok.getAllOrderByName());
        simpanBtn.disableProperty().bind(Bindings.isEmpty(produkTF.textProperty()).or(Bindings.isEmpty(hargaTF.textProperty()).or(Bindings.isEmpty(jumlahTF.textProperty()))));
        daftarBeliTbl.setEditable(true);
        noClm.setCellValueFactory((TableColumn.CellDataFeatures<DaftarPembelianProperty, DaftarPembelianProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noClm.setCellFactory((TableColumn<DaftarPembelianProperty, DaftarPembelianProperty> param) -> new TableCell<DaftarPembelianProperty, DaftarPembelianProperty>() {
            @Override protected void updateItem(DaftarPembelianProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noClm.setSortable(false);
        produkClm.setCellValueFactory(cellData -> cellData.getValue().produkProperty());
        produkClm.setCellFactory(TextFieldTableCell.forTableColumn());
        produkClm.setOnEditCommit((t) -> {
            ((DaftarPembelianProperty) t.getTableView().getItems().get(t.getTablePosition().getRow())).setProduk(t.getNewValue());
        });
        hargaClm.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        hargaClm.setCellFactory(TextFieldTableCell.<DaftarPembelianProperty>forTableColumn());
        hargaClm.setOnEditCommit((event) -> {
            ((DaftarPembelianProperty) event.getTableView().getItems().get(event.getTablePosition().getRow())).setHarga(event.getNewValue());
            hitungTotal();
        });
        jumlahClm.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty());
        jumlahClm.setCellFactory(TextFieldTableCell.<DaftarPembelianProperty>forTableColumn());
        jumlahClm.setOnEditCommit((event) -> {
            ((DaftarPembelianProperty) event.getTableView().getItems().get(event.getTablePosition().getRow())).setJumlah(event.getNewValue());
            hitungTotal();
        });
        daftarBeliTbl.setItems(data);
        daftarBeliTbl.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectTable(newValue)));
    }    
    
    private void selectTable(DaftarPembelianProperty property) {
        if(null != property) {
            hapusBtn.setDisable(false);
        }else{
            produkTF.setText("");
            hargaTF.setText("");
            jumlahTF.setText("");
            hapusBtn.setDisable(true);
        }
    }

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void simpanAction(ActionEvent event) {
        DaftarPembelianProperty property = new DaftarPembelianProperty();
        property.setProduk(produkTF.getText());
        property.setHarga(hargaTF.getText());
        property.setJumlah(jumlahTF.getText());
        data.add(property);
        daftarBeliTbl.getSelectionModel().clearSelection();
        selectTable(null);
        hitungTotal();
    }
    
    @FXML
    private void hapusAction(ActionEvent event) {
        DaftarPembelianProperty property = daftarBeliTbl.getSelectionModel().getSelectedItem();
        data.remove(property);
        daftarBeliTbl.getSelectionModel().clearSelection();
        selectTable(null);
        hitungTotal();
    }
    
    @FXML
    private void batalAction(ActionEvent event) {
        dialogStage.close();
    }
    
    @FXML
    private void okeAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminController.class.getResource("SubmitPembelian.fxml"));
            BorderPane page = (BorderPane) loader.load();

            Stage submitDialogStage = new Stage();
            submitDialogStage.setTitle("Pembelian - Buat baru");
            submitDialogStage.initModality(Modality.WINDOW_MODAL);
            submitDialogStage.initOwner(dialogStage);

            Scene scene = new Scene(page);
            submitDialogStage.setScene(scene);

            SubmitPembelianController controller = loader.getController();
            controller.setDialogStage(submitDialogStage);
//            controller.initValue(pembelianProperty);

            submitDialogStage.showAndWait();

            if(controller.isOkClicked()){
                
                okClicked = true;
                TransaksiPembelian pembelian = new TransaksiPembelian();
                if(null != pembelianProperty) {
                    pembelian.setId(pembelianProperty.getId());
                }
                pembelian.setTanggal(new Date());
                pembelian.setPemasokId(new Pemasok(controller.getPemasokId()));
                pembelian.setTotal(Integer.valueOf(totalHarga.getText().replace(".", "")));
                pembelian.setMtdByrId(new MetodePembayaran(controller.getMetodePembayaranId()));
                pembelian.setIsLunas((controller.getMetodePembayaranId() == 1));
                pembelian.setStatus(controller.getStatus());
                int id = iTransaksiPembelian.insertOrUpdate(pembelian);
                if(null != pembelianProperty) {
                    iDaftarPembelian.deleteByTrxPembelianId(pembelian);
                }
                iDaftarPembelian.insertList(data, id);
                dialogStage.close();
            }

        } catch (IOException ex) {
            LOGGER.error("failed to load PurchaseOrder.fxml", ex);
        }
        
    }
    
    private void hitungTotal() {
        int total = 0;
        for(int i=0; i<data.size(); i++) {
            int harga = Integer.parseInt(data.get(i).getHarga());
            int jumlah = Integer.parseInt(data.get(i).getJumlah());
            total += harga*jumlah;
        }
        totalHarga.setText(numberFormat.format(total));
    }

    void iniValue(TransaksiPembelianProperty pembelianProperty) {
        if(null != pembelianProperty) {
            this.pembelianProperty = pembelianProperty;
//            pemasokCB.setValue(new Choice(pembelianProperty.getPemasokId(), pembelianProperty.getPemasokNama()));
            data.addAll(iDaftarPembelian.getListBeli(new TransaksiPembelian(pembelianProperty.getId())));
            hitungTotal();
        }
    }
    
}
