/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju;

import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;
import kiraju.implement.JenisMenuModel;
import kiraju.implement.MenuItemModel;
import kiraju.implement.MenuModel;
import kiraju.implement.StokOpnameItemModel;
import kiraju.implement.StokOpnameModel;
import kiraju.interfaces.IJenisMenu;
import kiraju.interfaces.IMenu;
import kiraju.interfaces.IMenuItem;
import kiraju.interfaces.IStokOpname;
import kiraju.interfaces.IStokOpnameItem;
import kiraju.model.StokOpname;
import kiraju.model.Users;
import kiraju.property.StokOpnameItemProperty;
import kiraju.property.StokOpnameProperty;
import kiraju.util.Choice;

/**
 * FXML Controller class
 *
 * @author arvita
 */
public class StokOpnameController implements Initializable {
    
    @FXML
    private Text judul;
    @FXML
    private TextField cari;
    @FXML
    private ChoiceBox<Choice> jenisMenuCB;
    @FXML
    private ChoiceBox<Choice> menuCB;
    @FXML
    private TableView<StokOpnameItemProperty> menuItemTable;
    @FXML
    private TableColumn<StokOpnameItemProperty, String> kode;
    @FXML
    private TableColumn<StokOpnameItemProperty, String> menu;
    @FXML
    private TableColumn<StokOpnameItemProperty, String> item;
    @FXML
    private TableColumn<StokOpnameItemProperty, Integer> stok;
    @FXML
    private TableColumn<StokOpnameItemProperty, Integer> tersedia;
    
    private Stage dialogStage;
    private int stokOpnameId;
    private Users users;
    private String stokOpnameNama = "";
    private ObservableList<StokOpnameItemProperty> dataTable = FXCollections.observableArrayList();
    
    private final IMenuItem iMenuItem = new MenuItemModel();
    private final IJenisMenu iJenisMenu = new JenisMenuModel();
    private final IMenu iMenu = new MenuModel();
    private final IStokOpnameItem iStokOpnameItem = new StokOpnameItemModel();
    private final IStokOpname iStokOpname = new StokOpnameModel();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //choice box
        judul.setText(stokOpnameNama);
        ObservableList<Choice> jenisMenuChoices = FXCollections.observableArrayList();
        jenisMenuChoices.add(new Choice(0, "Semua"));
        iJenisMenu.getAllActive().forEach((jenisMenu) -> {
            jenisMenuChoices.add(new Choice((int) jenisMenu.getId(), jenisMenu.getNama()));
        });
        jenisMenuCB.setItems(jenisMenuChoices);
        jenisMenuCB.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Choice> observable, Choice oldValue, Choice newValue) -> {
            ObservableList<Choice> menuChoices = FXCollections.observableArrayList();
            menuChoices.add(new Choice(0, "Semua"));
            iMenu.getActiveProperty(newValue.getId().shortValue()).forEach((menuProperty) -> {
                menuChoices.add(new Choice(menuProperty.getId(), menuProperty.getNama()));
            });
            menuCB.setItems(menuChoices);
            menuCB.getSelectionModel().selectFirst();
        });
        jenisMenuCB.getSelectionModel().selectFirst();
        menuCB.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Choice> observable, Choice oldValue, Choice newValue) -> {
            if(null != newValue){
                cari.clear();
                dataTable.clear();
//                dataTable = iMenuItem.getMenuAndStok(stokOpnameId, newValue.getId(), jenisMenuCB.getSelectionModel().getSelectedItem().getId());
                displayFilteredData();
            }
        });
        
        //table
        menuItemTable.setEditable(true);
        kode.setCellValueFactory(cellData -> cellData.getValue().kodeProperty());
        menu.setCellValueFactory(cellData -> cellData.getValue().menuNamaProperty());
        item.setCellValueFactory(cellData -> cellData.getValue().menuItemNamaProperty());
        stok.setCellValueFactory(cellData -> cellData.getValue().stokProperty().asObject());
        tersedia.setCellValueFactory(cellData -> cellData.getValue().stokTersediaProperty().asObject());
        tersedia.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
//        tersedia.setOnEditCommit((TableColumn.CellEditEvent<StokOpnameItemProperty, Integer> t) -> {
//            ((StokOpnameItemProperty) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStokTersedia(t.getNewValue());
//            StokOpnameItemProperty itemProperty = ((StokOpnameItemProperty) t.getTableView().getItems().get(t.getTablePosition().getRow()));
//            if(0 == itemProperty.getId()){
//                int id = iStokOpnameItem.insert(itemProperty);
//                ((StokOpnameItemProperty) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(id);
//            }else{
//                iStokOpnameItem.update(itemProperty);
//            }
//        });
    }
    
    private void displayFilteredData() {
        
        FilteredList<StokOpnameItemProperty> filteredData = new FilteredList<>(dataTable, p -> true);
        
        cari.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
                                if (person.getKode().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				}else if (person.getMenuItemNama().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				}
				return false; // Does not match.
			});
		});
        
        SortedList<StokOpnameItemProperty> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(menuItemTable.comparatorProperty());
        menuItemTable.setItems(sortedData);
    }

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setOnCloseRequest((WindowEvent event) -> {
            simpanBtn();
        });
    }

    void iniValue(int stokOpnameId, String stokOpnameNama, Users users) {
        this.stokOpnameId = stokOpnameId;
        if(null != stokOpnameNama){
            this.stokOpnameNama = stokOpnameNama;
        }
        this.users = users;
//        dataTable = iMenuItem.getMenuAndStok(stokOpnameId, 0, 0);
        displayFilteredData();
    }
    
    @FXML
    private void simpanBtn() {
        TextInputDialog dialog = new TextInputDialog(stokOpnameNama);
        dialog.setTitle("Tambah Stok Opname");
        dialog.setHeaderText("Simpan Sebagai");
//        dialog.setContentText("Masukkan nama:");

        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(nama -> {
            StokOpname stokOpname = new StokOpname();
            stokOpname.setId(stokOpnameId);
            stokOpname.setTanggal(new Date());
            stokOpname.setUserId(users);
            stokOpname.setStatus(Boolean.FALSE);
            stokOpname.setNama(nama);
            iStokOpname.update(stokOpname);

        });
        dialogStage.close();
    }
    
    @FXML
    private void finishBtn(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog(stokOpnameNama);
        dialog.setTitle("Tambah Stok Opname");
        dialog.setHeaderText("Simpan Sebagai");
//        dialog.setContentText("Masukkan nama:");

        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(nama -> {
            StokOpname stokOpname = new StokOpname();
            stokOpname.setId(stokOpnameId);
            stokOpname.setTanggal(new Date());
            stokOpname.setUserId(users);
            stokOpname.setStatus(Boolean.TRUE);
            stokOpname.setNama(nama);
            iStokOpname.update(stokOpname);
            
            StokOpnameProperty opnameProperty = new StokOpnameProperty();
            opnameProperty.setId(stokOpnameId);
            ObservableList<StokOpnameItemProperty> itemObsList = iStokOpnameItem.getByStokOpnameId(opnameProperty);
            if(null != itemObsList && !itemObsList.isEmpty()){
                iMenuItem.updateStok(itemObsList);
            }

        });

        
        
        dialogStage.close();
    }
    
    
}
