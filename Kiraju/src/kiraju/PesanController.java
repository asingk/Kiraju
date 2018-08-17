/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import kiraju.interfaces.IJenisMenu;
import kiraju.interfaces.IMeja;
import kiraju.interfaces.IPesan;
import kiraju.interfaces.ITransaksi;
import kiraju.model.JenisMenu;
import kiraju.implement.JenisMenuModel;
import kiraju.model.Meja;
import kiraju.implement.MejaModel;
import kiraju.implement.MenuModel;
import kiraju.implement.PesanModel;
import kiraju.model.Transaksi;
import kiraju.implement.TransaksiModel;
import kiraju.property.PesanProperty;
import kiraju.util.CommonConstant;
import kiraju.interfaces.IMenu;

/**
 *
 * @author arvita
 */
public class PesanController implements Initializable{
    @FXML
    private TableView<PesanProperty> menuTable;
    @FXML
    private TableColumn<PesanProperty, PesanProperty> noColumn;
    @FXML
    private TableColumn<PesanProperty, String> namaColumn;
    @FXML
    private TableColumn<PesanProperty, String> jumlahColumn;
    @FXML
    private TextField searchTextField;
    @FXML
    private ChoiceBox<String> jenisBox;
    
    private Stage dialogStage;
    private int transaksiId;
    private ObservableList<PesanProperty> menuPropObsList;
    private ObservableList<PesanProperty> pesanPropObsList;
    private ObservableList<String> choicesString = FXCollections.observableArrayList();
    private ObservableList<String> choicesList = FXCollections.observableArrayList();
    private Map<String,Short> map = new HashMap<String,Short>();
    private ObservableMap<String,Short> choicesMap = FXCollections.observableMap(map);
    private final IMenu iMenu = new MenuModel();
    private final IJenisMenu iJenis = new JenisMenuModel();
    private final IPesan iPesan = new PesanModel();
    private final IMeja iMeja = new MejaModel();
    private final ITransaksi iTransaksi = new TransaksiModel();
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    
    @FXML
    private void pesanBtn(ActionEvent event){
        pesanPropObsList = iPesan.getDetailByTransaksiId(transaksiId);
        if(pesanPropObsList != null && pesanPropObsList.size() > 0) {
            Meja meja = new Meja();
            meja.setId(pesanPropObsList.get(0).getMejaId());
            meja.setStatus(CommonConstant.MEJA_TERISI);
            iMeja.update(meja);
        }else{
            iPesan.deleteByTransaksiId(transaksiId);
            Transaksi transaksi = new Transaksi();
            transaksi.setId(transaksiId);
            iTransaksi.deleteById(transaksi);
        }
        dialogStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getChoiceList();
        jenisBox.setItems(choicesString);
        jenisBox.getSelectionModel().select(0);       
        jenisBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            menuPropObsList.clear();
            searchTextField.clear();
            if(null != newValue)
                menuPropObsList = iMenu.getAllAndJumlah(choicesMap.get(newValue), transaksiId);
            displayFilteredData();
        });
        menuTable.setEditable(true);
        noColumn.setCellValueFactory((TableColumn.CellDataFeatures<PesanProperty, PesanProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumn.setCellFactory((TableColumn<PesanProperty, PesanProperty> param) -> new TableCell<PesanProperty, PesanProperty>() {
            @Override protected void updateItem(PesanProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noColumn.setSortable(false);
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        jumlahColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty());        
        jumlahColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        jumlahColumn.setOnEditCommit((TableColumn.CellEditEvent<PesanProperty, String> t) -> {
            ((PesanProperty) t.getTableView().getItems().get(t.getTablePosition().getRow())).setJumlah(t.getNewValue());
            PesanProperty pesanProperty = ((PesanProperty) t.getTableView().getItems().get(t.getTablePosition().getRow()));
            if(0 == pesanProperty.getId()){
                int id = iPesan.insert(pesanProperty);
                ((PesanProperty) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(id);
            }else{
                iPesan.update(pesanProperty);
            }
        });
    }
    
    private void displayFilteredData() {
        
        FilteredList<PesanProperty> filteredData = new FilteredList<>(menuPropObsList, p -> true);
        
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (person.getNama().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				}
				return false; // Does not match.
			});
		});
        
        SortedList<PesanProperty> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(menuTable.comparatorProperty());
        menuTable.setItems(sortedData);
    }
    
    private void getChoiceList() {
        choicesMap.put("Semua", (short)0);
        choicesString.add("Semua");
        List resultList = iJenis.getAllActive();
        if(null != resultList){
            for(Object o : resultList){
                JenisMenu jenisMenu = (JenisMenu) o;
                    choicesMap.put(jenisMenu.getNama(), jenisMenu.getId());
                    choicesString.add(jenisMenu.getNama());
                    choicesList.add(jenisMenu.getNama());
            }
        }
    }

    public void setTransaksiId(int transaksiId) {
        this.transaksiId = transaksiId;
        menuPropObsList = iMenu.getAllAndJumlah((short)0, transaksiId);
        displayFilteredData();
    }

    public ObservableList<PesanProperty> getPesanPropObsList() {
        return pesanPropObsList;
    }
    
}