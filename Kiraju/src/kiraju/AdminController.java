/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import kiraju.interfaces.IJenisMenu;
import kiraju.interfaces.IMeja;
import kiraju.interfaces.IPesan;
import kiraju.interfaces.IPosisi;
import kiraju.interfaces.ITransaksi;
import kiraju.interfaces.IUsers;
import kiraju.model.JenisMenu;
import kiraju.implement.JenisMenuModel;
import kiraju.model.Meja;
import kiraju.implement.MejaModel;
import kiraju.model.Menu;
import kiraju.implement.MenuModel;
import kiraju.implement.PengeluaranModel;
import kiraju.implement.PesanModel;
import kiraju.model.Posisi;
import kiraju.implement.PosisiModel;
import kiraju.model.Transaksi;
import kiraju.implement.TransaksiModel;
import kiraju.model.Users;
import kiraju.implement.UsersModel;
import kiraju.interfaces.IPengeluaran;
import kiraju.model.Pengeluaran;
import kiraju.property.JenisMenuProperty;
import kiraju.property.MenuProperty;
import kiraju.property.PengeluaranProperty;
import kiraju.property.PesanProperty;
import kiraju.property.PosisiProperty;
import kiraju.property.TransaksiProperty;
import kiraju.property.UsersProperty;
import kiraju.util.CommonConstant;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import kiraju.interfaces.IMenu;
import kiraju.interfaces.IValidation;
import kiraju.model.Laporan;
import kiraju.property.MejaProperty;
import kiraju.util.JDBCConnection;
import kiraju.util.Validation;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;

/**
 *
 * @author arvita
 */
public class AdminController implements Initializable {
    private final static Logger LOGGER = Logger.getLogger(AdminController.class);
    @FXML
    private TableView<MenuProperty> menuTable;
    @FXML
    private TableView<JenisMenuProperty> jenisMenuTable;
    @FXML
    private TableView<PesanProperty> listMenuMeja;
    @FXML
    private TableView<UsersProperty> usersTable;
    @FXML
    private TableView<TransaksiProperty> bungkusNamaTable;
    @FXML
    private TableView<PesanProperty> bungkusMenuTable;
    @FXML
    private TableView<TransaksiProperty> pemasukanTable;
    @FXML
    private TableView<PengeluaranProperty> pengeluaranTable;
    @FXML
    private TableView<MejaProperty> adminMejaTable;
    @FXML
    private TableColumn<MenuProperty, MenuProperty> noColumn;
    @FXML
    private TableColumn<MenuProperty, String> namaColumn;
    @FXML
    private TableColumn<MenuProperty, String> hargaColumn;
    @FXML
    private TableColumn<MenuProperty, String> statusColumn;
    @FXML
    private TableColumn<JenisMenuProperty, JenisMenuProperty> noColumnJenis;
    @FXML
    private TableColumn<JenisMenuProperty, String> namaColumnJenis;
    @FXML
    private TableColumn<JenisMenuProperty, String> statusColumnJenis;
    @FXML
    private TableColumn<PesanProperty, String> namaColumnMeja;
    @FXML
    private TableColumn<PesanProperty, String> jumlahColumnMeja;
    @FXML
    private TableColumn<UsersProperty, UsersProperty> noColumnUsers;
    @FXML
    private TableColumn<UsersProperty, String> namaColumnUsers;
    @FXML
    private TableColumn<UsersProperty, String> posisiColumnUsers;
    @FXML
    private TableColumn<TransaksiProperty, TransaksiProperty> noColumnBungkusNama;
    @FXML
    private TableColumn<TransaksiProperty, String> namaColumnBungkusNama;
    @FXML
    private TableColumn<PesanProperty, String> namaColumnBungkus;
    @FXML
    private TableColumn<PesanProperty, String> jumlahColumnBungkus;
    @FXML
    private TableColumn<TransaksiProperty, TransaksiProperty> noColumnPemasukan;
    @FXML
    private TableColumn<TransaksiProperty, LocalTime> waktuColumnPemasukan;
    @FXML
    private TableColumn<TransaksiProperty, Integer> mejaColumnPemasukan;
    @FXML
    private TableColumn<TransaksiProperty, String> bungkusColumnPemasukan;
    @FXML
    private TableColumn<TransaksiProperty, String> statusColumnPemasukan;
    @FXML
    private TableColumn<TransaksiProperty, String> totalColumnPemasukan;
    @FXML
    private TableColumn<PengeluaranProperty, PengeluaranProperty> noColumnPengeluaran;
    @FXML
    private TableColumn<PengeluaranProperty, String> namaColumnPengeluaran;
    @FXML
    private TableColumn<PengeluaranProperty, String> hargaColumnPengeluaran;
    @FXML
    private TableColumn<MejaProperty, Integer> noColumnAdminMeja;
    @FXML
    private TableColumn<MejaProperty, String> statusColumnAdminMeja;
    @FXML
    private TableColumn<MejaProperty, String> nomorColumnAdminMeja;
    @FXML
    private TextField namaTextField;
    @FXML
    private TextField hargaTextField;
    @FXML
    private TextField searchTextField;
    @FXML
    private TextField namaJenisTextField;
    @FXML
    private TextField namaUsersTF;
    @FXML
    private TextField userNameUsersTF;
    @FXML
    private TextField passwordUsersTF;
    @FXML
    private TextField namaPengeluaranTF;
    @FXML
    private TextField hargaPengeluaranTF;
    @FXML
    private TextField nomorAdminMejaTF;
    @FXML
    private ChoiceBox<String> jenisBox;
    @FXML
    private ChoiceBox<String> jenisMenuBox;
    @FXML
    private ChoiceBox<String> posisiUsers;
    @FXML
    private ChoiceBox<String> pemasukanChoiceBox;
    @FXML
    private ChoiceBox<String> grafikBulanCB;
    @FXML
    private ChoiceBox<Integer> grafikTahunCB;
//    @FXML
//    private ChoiceBox<String> grafikBulanMenuCB;
//    @FXML
//    private ChoiceBox<String> grafikBulanJenisMenuCB;
    @FXML
    private ChoiceBox<String> statusAdminMejaCB;
    @FXML
    private ChoiceBox<String> jenisStatusCB;
    @FXML
    private ChoiceBox<String> menuStatusCB;
    @FXML
    private ChoiceBox<String> userStatusCB;
    @FXML
    private ChoiceBox<String> jenisLaporanCB;
    @FXML
    private TabPane tabPane;
    @FXML
    private TabPane laporanTabPane;
    @FXML
    private Tab menuTab;
    @FXML
    private Tab jenisMenutab;
    @FXML
    private Tab usersTab;
    @FXML
    private Tab pemasukanTab;
    @FXML
    private Tab mejaTab;
    @FXML
    private Tab bungkusTab;
    @FXML
    private Tab pengeluaranTab;
    @FXML
    private Tab grafikTab;
    @FXML
    private Tab adminMejaTab;
    @FXML
    private Tab backupRestoreTab;
    @FXML
    private Tab aboutTab;
    @FXML
    private Tab grafikBulananTab;
    @FXML
    private Tab grafikTahunanTab;
    @FXML
    private Text namaMeja;
    @FXML
    private Text total;
    @FXML
    private Text namaBungkus;
    @FXML
    private Text totalBungkus;
    @FXML
    private Text totalBayar;
    @FXML
    private Text totalBatal;
    @FXML
    private Text totalSemua;
    @FXML
    private Text totalPengeluaran;
    @FXML
    private Text backupDirectory;
    @FXML
    private Text restoreDirectory;
    @FXML
    private Text laporanDirectory;
    @FXML
    private Button meja1;
    @FXML
    private Button meja2;
    @FXML
    private Button meja3;
    @FXML
    private Button meja4;
    @FXML
    private Button meja5;
    @FXML
    private Button meja6;
    @FXML
    private Button meja7;
    @FXML
    private Button meja8;
    @FXML
    private Button meja9;
    @FXML
    private Button meja10;
    @FXML
    private Button meja11;
    @FXML
    private Button meja12;
    @FXML
    private Button meja13;
    @FXML
    private Button meja14;
    @FXML
    private Button meja15;
    @FXML
    private Button meja16;
    @FXML
    private Button meja17;
    @FXML
    private Button meja18;
    @FXML
    private Button meja19;
    @FXML
    private Button meja20;
    @FXML
    private Button meja21;
    @FXML
    private Button meja22;
    @FXML
    private Button meja23;
    @FXML
    private Button meja24;
    @FXML
    private Button meja25;
    @FXML
    private Button meja26;
    @FXML
    private Button meja27;
    @FXML
    private Button meja28;
    @FXML
    private Button meja29;
    @FXML
    private Button meja30;
    @FXML
    private Button meja31;
    @FXML
    private Button meja32;
    @FXML
    private Button meja33;
    @FXML
    private Button meja34;
    @FXML
    private Button meja35;
    @FXML
    private Button meja36;
    @FXML
    private Button meja37;
    @FXML
    private Button meja38;
    @FXML
    private Button meja39;
    @FXML
    private Button meja40;
    @FXML
    private Button meja41;
    @FXML
    private Button meja42;
    @FXML
    private Button meja43;
    @FXML
    private Button meja44;
    @FXML
    private Button meja45;
    @FXML
    private Button meja46;
    @FXML
    private Button meja47;
    @FXML
    private Button meja48;
    @FXML
    private Button meja49;
    @FXML
    private Button meja50;
    @FXML
    private Button meja51;
    @FXML
    private Button meja52;
    @FXML
    private Button meja53;
    @FXML
    private Button meja54;
    @FXML
    private Button meja55;
    @FXML
    private Button meja56;
    @FXML
    private Button meja57;
    @FXML
    private Button meja58;
    @FXML
    private Button meja59;
    @FXML
    private Button meja60;
    @FXML
    private Button meja61;
    @FXML
    private Button meja62;
    @FXML
    private Button meja63;
    @FXML
    private Button meja64;
    @FXML
    private Button meja65;
    @FXML
    private Button meja66;
    @FXML
    private Button meja67;
    @FXML
    private Button meja68;
    @FXML
    private Button meja69;
    @FXML
    private Button meja70;
    @FXML
    private Button meja71;
    @FXML
    private Button meja72;
    @FXML
    private Button meja73;
    @FXML
    private Button meja74;
    @FXML
    private Button meja75;
    @FXML
    private Button meja76;
    @FXML
    private Button meja77;
    @FXML
    private Button meja78;
    @FXML
    private Button meja79;
    @FXML
    private Button meja80;
    @FXML
    private Button meja81;
    @FXML
    private Button meja82;
    @FXML
    private Button meja83;
    @FXML
    private Button meja84;
    @FXML
    private Button meja85;
    @FXML
    private Button meja86;
    @FXML
    private Button meja87;
    @FXML
    private Button meja88;
    @FXML
    private Button meja89;
    @FXML
    private Button meja90;
    @FXML
    private Button meja91;
    @FXML
    private Button meja92;
    @FXML
    private Button meja93;
    @FXML
    private Button meja94;
    @FXML
    private Button meja95;
    @FXML
    private Button meja96;
    @FXML
    private Button meja97;
    @FXML
    private Button meja98;
    @FXML
    private Button meja99;
    @FXML
    private Button meja100;
    //20170812 - V1.1
//    @FXML
//    private Button meja101;
//    @FXML
//    private Button meja102;
//    @FXML
//    private Button meja103;
//    @FXML
//    private Button meja104;
//    @FXML
//    private Button usersHapusBtn;
    @FXML
    private Button adminMejaSimpanBtn;
    @FXML
    private Button pindahBtn;
    @FXML
    private DatePicker pemasukanDate;
    @FXML
    private DatePicker pengeluaranDate;
    @FXML
    private DatePicker transaksiDariDate;
    @FXML
    private DatePicker transaksiSampaiDate;
    @FXML
    private LineChart<String, Number> chartBulan;
    @FXML
    private LineChart<String, Number> chartTahun;
    @FXML
    private LineChart<String, Number> chartMenuBulan;
    @FXML
    private CategoryAxis xAxisBulanan;
    @FXML
    private CategoryAxis xAxisTahunan;
    @FXML
    private CategoryAxis xAxisMenuBulanan;
    
    private Stage primaryStage;
    private Users loginUser;
    private ObservableList<MenuProperty> menuPropObsList;
    private ObservableList<JenisMenuProperty> jenisMenuPropList;
    private ObservableList<String> choicesString = FXCollections.observableArrayList();
    private ObservableList<String> choicesList = FXCollections.observableArrayList();
    private ObservableList<String> usersChoicesString = FXCollections.observableArrayList();
    private ObservableList<String> grafikBulanChoice = FXCollections.observableArrayList();
    private ObservableList<PesanProperty> menuMejaObsList = FXCollections.observableArrayList();
    private ObservableList<UsersProperty> usersObsList = FXCollections.observableArrayList();
    private ObservableList<PosisiProperty> posisiObsList = FXCollections.observableArrayList();
    private ObservableList<TransaksiProperty> bungkusObsList = FXCollections.observableArrayList();
    private ObservableList<PesanProperty> menuBungkusObsList = FXCollections.observableArrayList();
    private ObservableList<TransaksiProperty> pemasukanObsList = FXCollections.observableArrayList();
    private ObservableList<PengeluaranProperty> pengeluaranObsList = FXCollections.observableArrayList();
    private ObservableList<MejaProperty> adminMejaObsList = FXCollections.observableArrayList();
    private Map<String,Short> map1 = new HashMap<String,Short>();
    private Map<String,Short> map3 = new HashMap<String,Short>();
    private ObservableMap<String ,Short> choicesMap = FXCollections.observableMap(map1);
    private ObservableMap<String, Short> usersChoiceObsMap = FXCollections.observableMap(map3);
    private final IMenu iMenu = new MenuModel();
    private final IJenisMenu iJenis = new JenisMenuModel();
    private final IMeja iMeja = new MejaModel();
    private final ITransaksi iTransaksi = new TransaksiModel();
    private final IUsers iUsers = new UsersModel();
    private final IPosisi iPosisi = new PosisiModel();
    private final IPesan iPesan = new PesanModel();
    private final IPengeluaran iPengeluaran = new PengeluaranModel();
    private final IValidation iValidation = new Validation();
    private short mejaActive = 0;
    private TransaksiProperty selectedNamaBungkus;
    private Integer totalHargaMeja = 0;
    private Integer totalHargaBungkus = 0;
//    private int bulanBaruMenu = 0;
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));
    private final String datePattern = "dd-MM-yyyy";
    
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLoginUser(Users loginUser) {
        this.loginUser = loginUser;
        pemasukanObsList.clear();
        
        tabPane.getTabs().removeAll(mejaTab, bungkusTab, pemasukanTab, pengeluaranTab, menuTab, jenisMenutab, adminMejaTab, grafikTab, backupRestoreTab, usersTab);
        pemasukanChoiceBox.setDisable(false);
        usersTab.setDisable(false);
        switch (loginUser.getPosisiId().getId()) {
            case CommonConstant.USER_HIDDEN:
                tabPane.getTabs().add(usersTab);
                tabPane.getTabs().remove(aboutTab);
                break;
            case CommonConstant.USER_SUPER:
                tabPane.getTabs().addAll(mejaTab, bungkusTab, pemasukanTab, pengeluaranTab, menuTab, jenisMenutab, adminMejaTab, grafikTab, backupRestoreTab, usersTab);
                tabPane.getTabs().remove(aboutTab);
                break;
            case CommonConstant.USER_ADMIN:
                tabPane.getTabs().addAll(mejaTab, bungkusTab, pemasukanTab, pengeluaranTab, menuTab, jenisMenutab, adminMejaTab, grafikTab, backupRestoreTab);
                tabPane.getTabs().remove(aboutTab);
                break;
            case CommonConstant.USER_KASIR:
                tabPane.getTabs().addAll(mejaTab, bungkusTab, pemasukanTab);
                pemasukanChoiceBox.setDisable(true);
                tabPane.getTabs().remove(aboutTab);
                break;
            default:
                if(!JDBCConnection.checkInstallDate() && !CommonConstant.ISPREMIUM.equalsIgnoreCase("Y")){
                    tabPane.getTabs().addAll(mejaTab, bungkusTab, pemasukanTab, pengeluaranTab, menuTab, jenisMenutab, adminMejaTab, grafikTab, backupRestoreTab, usersTab, aboutTab);
                    tabPane.getTabs().remove(aboutTab);
                    usersTab.setDisable(true);
                    pemasukanChoiceBox.setDisable(true);
                }else{
                    if(!tabPane.getTabs().contains(aboutTab)){
                        tabPane.getTabs().add(aboutTab);
                    }
                }
                break;
        }
    }
    
    @FXML
    private void handleSimpanAction(ActionEvent event) {
        TextField[] textFields = {namaTextField, hargaTextField};
        String[] namaTextFields = {"Nama", "Harga"};
        List<ChoiceBox<String>> choiceBoxs = new ArrayList<>();
        choiceBoxs.add(jenisMenuBox);
        choiceBoxs.add(menuStatusCB);
        String[] namaChoiceBoxs = {"Jenis", "Status"};
        if(iValidation.isTextFieldInputValid(textFields, namaTextFields, choiceBoxs, namaChoiceBoxs, primaryStage)) {
            MenuProperty selectedMenuProp = menuTable.getSelectionModel().getSelectedItem();
            Menu menu = new Menu();
            menu.setNama(namaTextField.getText());
            menu.setHarga(Integer.parseInt(hargaTextField.getText()));
            menu.setDeletedFlag(menuStatusCB.getValue().equalsIgnoreCase("Aktif") ? (short) 0 : (short) 1);
            JenisMenu jenisMenu = new JenisMenu();
            jenisMenu.setId(choicesMap.get(jenisMenuBox.getValue()));
            menu.setJenis(jenisMenu);
            if(null != selectedMenuProp){
                menu.setId(selectedMenuProp.getId());
                iMenu.update(menu);
            }else{
                iMenu.insert(menu);
            }
            handleCancelAction(event);
            setMenuTable(choicesMap.get(jenisBox.getValue()));
        }
    }
    
    @FXML
    private void handleCancelAction(ActionEvent event){
        menuTable.getSelectionModel().clearSelection();
        namaTextField.setText("");
        hargaTextField.setText("");
        jenisMenuBox.setValue(null);
        menuStatusCB.setValue(null);
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getMejaStatus();     
        setBungkus();
        setDaftarMenu();
        setJenisMenu();
        adminMeja();
        setUsers();
        tabActive();
        
        Users users = new Users();
        users.setId(CommonConstant.USER_NONE);
        users.setNama("Tanpa User");
        users.setPosisiId(new Posisi(CommonConstant.USER_NONE));
        setLoginUser(users);
        setPemasukan();
        displayChart();
        setPengeluaran();
        setBackupAndRestore();
    }    
    
    private void displayEdit(MenuProperty menuProp){
        if(null != menuProp){
            namaTextField.setText(menuProp.getNama());
            hargaTextField.setText(menuProp.getHarga().replace(".", ""));
            jenisMenuBox.setValue(menuProp.getJenisNama());
            menuStatusCB.setValue(menuProp.deletedFlagProperty().get());
        }else{
            namaTextField.setText("");
            hargaTextField.setText("");
            jenisMenuBox.setValue(null);
            menuStatusCB.setValue(null);
        }  
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

    private void displayFilteredData() {
        
        FilteredList<MenuProperty> filteredData = new FilteredList<>(menuPropObsList, p -> true);
        
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}else{
//                                    String lowerCaseFilter = newValue.toLowerCase();
                                    return person.getNama().toLowerCase().contains(newValue.toLowerCase()); 
                                }
				
				// Compare first name and last name of every person with filter text.
//				String lowerCaseFilter = newValue.toLowerCase();
                            // Does not match.
				
//				return person.getNama().toLowerCase().contains(lowerCaseFilter); 
			});
		});
        
        SortedList<MenuProperty> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(menuTable.comparatorProperty());
        menuTable.setItems(sortedData);
    }

    private void displayEditJenis(JenisMenuProperty jenisMenuProp) {
        if(null != jenisMenuProp){
            namaJenisTextField.setText(jenisMenuProp.getNama());
            jenisStatusCB.setValue(jenisMenuProp.deletedFlagProperty().get());
        }
    }
    
    @FXML
    private void jenisSimpanAction(ActionEvent event){
        TextField[] textFields = {namaJenisTextField};
        String[] namaTextFields = {"Nama"};
        List<ChoiceBox<String>> choiceBoxs = new ArrayList<>();
        choiceBoxs.add(jenisStatusCB);
        String[] namaChoiceBoxs = {"Status"};
        if(iValidation.isTextFieldInputValid(textFields, namaTextFields, choiceBoxs, namaChoiceBoxs, primaryStage)){
            JenisMenuProperty selectedJenisMenuProp = jenisMenuTable.getSelectionModel().getSelectedItem();
            JenisMenu jenisMenu = new JenisMenu();
            jenisMenu.setNama(namaJenisTextField.getText());
            jenisMenu.setDeletedFlag(jenisStatusCB.getValue().equalsIgnoreCase("Aktif") ? (short) 0 : (short) 1);
            if(null != selectedJenisMenuProp){
                jenisMenu.setId(selectedJenisMenuProp.getId());
                iJenis.update(jenisMenu, primaryStage);
            }else{
                iJenis.insert(jenisMenu, primaryStage);
            }
            jenisCancelAction(event);
            setJenisMenuTable();
        }
    }
    
    @FXML
    private void jenisCancelAction(ActionEvent event){
        jenisMenuTable.getSelectionModel().clearSelection();
        namaJenisTextField.setText("");
        jenisStatusCB.setValue(null);
    }
    
    private void tabActive(){
        tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
            if(tabPane.getTabs().contains(menuTab) && newValue.equals(menuTab)){
                choicesMap.clear();
                choicesString.clear();
                choicesList.clear();
                getChoiceList();
                jenisBox.getSelectionModel().select(0); 
            }else if(tabPane.getTabs().contains(pemasukanTab) && newValue == pemasukanTab){
                pemasukanObsList.clear();
                getUsersChoiceList();
                pemasukanChoiceBox.getSelectionModel().select(loginUser.getNama());
            }else if(tabPane.getTabs().contains(mejaTab) && newValue.equals(mejaTab)){
                getMejaStatus();
            }else if(tabPane.getTabs().contains(grafikTab) && grafikTab.equals(newValue)){
//                choicesMap.clear();
//                choicesString.clear();
//                choicesList.clear();
//                getChoiceList();
//                displayChart();
                grafikBulanCB.getSelectionModel().clearSelection();
                grafikBulanCB.getSelectionModel().select(LocalDate.now().getMonthValue()-1);
                grafikTahunCB.getSelectionModel().clearSelection();
                grafikTahunCB.getSelectionModel().selectLast();
            }
        });
    }
    
    private void displayOrder(short i) {
        String nomorMeja = iMeja.getNomorById(i);
        menuMejaObsList.clear();
        totalHargaMeja = 0;
        if(i > 0){
            namaMeja.setText("Meja " + nomorMeja);
            menuMejaObsList =  iTransaksi.getbyMeja(i);
        }else{
            namaMeja.setText("");
        }
        listMenuMeja.setItems(menuMejaObsList);
        namaColumnMeja.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        jumlahColumnMeja.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty());
        
        for(int j=0; j<menuMejaObsList.size(); j++){
            totalHargaMeja += Integer.parseInt(menuMejaObsList.get(j).getHarga()) * Integer.parseInt(menuMejaObsList.get(j).getJumlah());
        }
        total.setText(numberFormat.format(totalHargaMeja));
        mejaActive = i;
        if(menuMejaObsList != null && menuMejaObsList.size() > 0) {
            pindahBtn.setDisable(false);
        }else{
            pindahBtn.setDisable(true);
        }
    }
    
    @FXML
    private void meja1Action(ActionEvent actionEvent){
        displayOrder((short)1);
    }

    @FXML
    private void meja2Action(ActionEvent actionEvent){
        displayOrder((short)2);
    }
    
    @FXML
    private void meja3Action(ActionEvent actionEvent){
        displayOrder((short)3);
    }
    
    @FXML
    private void meja4Action(ActionEvent actionEvent){
        displayOrder((short)4);
    }
    
    @FXML
    private void meja5Action(ActionEvent actionEvent){
        displayOrder((short)5);
    }
    
    @FXML
    private void meja6Action(ActionEvent actionEvent){
        displayOrder((short)6);
    }

    @FXML
    private void meja7Action(ActionEvent actionEvent){
        displayOrder((short)7);
    }
    
    @FXML
    private void meja8Action(ActionEvent actionEvent){
        displayOrder((short)8);
    }
    
    @FXML
    private void meja9Action(ActionEvent actionEvent){
        displayOrder((short)9);
    }
    
    @FXML
    private void meja10Action(ActionEvent actionEvent){
        displayOrder((short)10);
    }
    
    @FXML
    private void meja11Action(ActionEvent actionEvent){
        displayOrder((short)11);
    }

    @FXML
    private void meja12Action(ActionEvent actionEvent){
        displayOrder((short)12);
    }
    
    @FXML
    private void meja13Action(ActionEvent actionEvent){
        displayOrder((short)13);
    }
    
    @FXML
    private void meja14Action(ActionEvent actionEvent){
        displayOrder((short)14);
    }
    
    @FXML
    private void meja15Action(ActionEvent actionEvent){
        displayOrder((short)15);
    }
    
    @FXML
    private void meja16Action(ActionEvent actionEvent){
        displayOrder((short)16);
    }

    @FXML
    private void meja17Action(ActionEvent actionEvent){
        displayOrder((short)17);
    }
    
    @FXML
    private void meja18Action(ActionEvent actionEvent){
        displayOrder((short)18);
    }
    
    @FXML
    private void meja19Action(ActionEvent actionEvent){
        displayOrder((short)19);
    }
    
    @FXML
    private void meja20Action(ActionEvent actionEvent){
        displayOrder((short)20);
    }
    
    @FXML
    private void meja21Action(ActionEvent actionEvent){
        displayOrder((short)21);
    }

    @FXML
    private void meja22Action(ActionEvent actionEvent){
        displayOrder((short)22);
    }
    
    @FXML
    private void meja23Action(ActionEvent actionEvent){
        displayOrder((short)23);
    }
    
    @FXML
    private void meja24Action(ActionEvent actionEvent){
        displayOrder((short)24);
    }
    
    @FXML
    private void meja25Action(ActionEvent actionEvent){
        displayOrder((short)25);
    }
    
    @FXML
    private void meja26Action(ActionEvent actionEvent){
        displayOrder((short)26);
    }

    @FXML
    private void meja27Action(ActionEvent actionEvent){
        displayOrder((short)27);
    }
    
    @FXML
    private void meja28Action(ActionEvent actionEvent){
        displayOrder((short)28);
    }
    
    @FXML
    private void meja29Action(ActionEvent actionEvent){
        displayOrder((short)29);
    }
    
    @FXML
    private void meja30Action(ActionEvent actionEvent){
        displayOrder((short)30);
    }
    
    @FXML
    private void meja31Action(ActionEvent actionEvent){
        displayOrder((short)31);
    }

    @FXML
    private void meja32Action(ActionEvent actionEvent){
        displayOrder((short)32);
    }
    
    @FXML
    private void meja33Action(ActionEvent actionEvent){
        displayOrder((short)33);
    }
    
    @FXML
    private void meja34Action(ActionEvent actionEvent){
        displayOrder((short)34);
    }
    
    @FXML
    private void meja35Action(ActionEvent actionEvent){
        displayOrder((short)35);
    }
    
    @FXML
    private void meja36Action(ActionEvent actionEvent){
        displayOrder((short)36);
    }

    @FXML
    private void meja37Action(ActionEvent actionEvent){
        displayOrder((short)37);
    }
    
    @FXML
    private void meja38Action(ActionEvent actionEvent){
        displayOrder((short)38);
    }
    
    @FXML
    private void meja39Action(ActionEvent actionEvent){
        displayOrder((short)39);
    }
    
    @FXML
    private void meja40Action(ActionEvent actionEvent){
        displayOrder((short)40);
    }
    
    @FXML
    private void meja41Action(ActionEvent actionEvent){
        displayOrder((short)41);
    }

    @FXML
    private void meja42Action(ActionEvent actionEvent){
        displayOrder((short)42);
    }
    
    @FXML
    private void meja43Action(ActionEvent actionEvent){
        displayOrder((short)43);
    }
    
    @FXML
    private void meja44Action(ActionEvent actionEvent){
        displayOrder((short)44);
    }
    
    @FXML
    private void meja45Action(ActionEvent actionEvent){
        displayOrder((short)45);
    }
    
    @FXML
    private void meja46Action(ActionEvent actionEvent){
        displayOrder((short)46);
    }

    @FXML
    private void meja47Action(ActionEvent actionEvent){
        displayOrder((short)47);
    }
    
    @FXML
    private void meja48Action(ActionEvent actionEvent){
        displayOrder((short)48);
    }
    
    @FXML
    private void meja49Action(ActionEvent actionEvent){
        displayOrder((short)49);
    }
    
    @FXML
    private void meja50Action(ActionEvent actionEvent){
        displayOrder((short)50);
    }
    
    @FXML
    private void meja51Action(ActionEvent actionEvent){
        displayOrder((short)51);
    }

    @FXML
    private void meja52Action(ActionEvent actionEvent){
        displayOrder((short)52);
    }
    
    @FXML
    private void meja53Action(ActionEvent actionEvent){
        displayOrder((short)53);
    }
    
    @FXML
    private void meja54Action(ActionEvent actionEvent){
        displayOrder((short)54);
    }
    
    @FXML
    private void meja55Action(ActionEvent actionEvent){
        displayOrder((short)55);
    }
    
    @FXML
    private void meja56Action(ActionEvent actionEvent){
        displayOrder((short)56);
    }

    @FXML
    private void meja57Action(ActionEvent actionEvent){
        displayOrder((short)57);
    }
    
    @FXML
    private void meja58Action(ActionEvent actionEvent){
        displayOrder((short)58);
    }
    
    @FXML
    private void meja59Action(ActionEvent actionEvent){
        displayOrder((short)59);
    }
    
    @FXML
    private void meja60Action(ActionEvent actionEvent){
        displayOrder((short)60);
    }
    
    @FXML
    private void meja61Action(ActionEvent actionEvent){
        displayOrder((short)61);
    }

    @FXML
    private void meja62Action(ActionEvent actionEvent){
        displayOrder((short)62);
    }
    
    @FXML
    private void meja63Action(ActionEvent actionEvent){
        displayOrder((short)63);
    }
    
    @FXML
    private void meja64Action(ActionEvent actionEvent){
        displayOrder((short)64);
    }
    
    @FXML
    private void meja65Action(ActionEvent actionEvent){
        displayOrder((short)65);
    }
    
    @FXML
    private void meja66Action(ActionEvent actionEvent){
        displayOrder((short)66);
    }

    @FXML
    private void meja67Action(ActionEvent actionEvent){
        displayOrder((short)67);
    }
    
    @FXML
    private void meja68Action(ActionEvent actionEvent){
        displayOrder((short)68);
    }
    
    @FXML
    private void meja69Action(ActionEvent actionEvent){
        displayOrder((short)69);
    }
    
    @FXML
    private void meja70Action(ActionEvent actionEvent){
        displayOrder((short)70);
    }
    
    @FXML
    private void meja71Action(ActionEvent actionEvent){
        displayOrder((short)71);
    }

    @FXML
    private void meja72Action(ActionEvent actionEvent){
        displayOrder((short)72);
    }
    
    @FXML
    private void meja73Action(ActionEvent actionEvent){
        displayOrder((short)73);
    }
    
    @FXML
    private void meja74Action(ActionEvent actionEvent){
        displayOrder((short)74);
    }
    
    @FXML
    private void meja75Action(ActionEvent actionEvent){
        displayOrder((short)75);
    }
    
    @FXML
    private void meja76Action(ActionEvent actionEvent){
        displayOrder((short)76);
    }

    @FXML
    private void meja77Action(ActionEvent actionEvent){
        displayOrder((short)77);
    }
    
    @FXML
    private void meja78Action(ActionEvent actionEvent){
        displayOrder((short)78);
    }
    
    @FXML
    private void meja79Action(ActionEvent actionEvent){
        displayOrder((short)79);
    }
    
    @FXML
    private void meja80Action(ActionEvent actionEvent){
        displayOrder((short)80);
    }
    
    @FXML
    private void meja81Action(ActionEvent actionEvent){
        displayOrder((short)81);
    }

    @FXML
    private void meja82Action(ActionEvent actionEvent){
        displayOrder((short)82);
    }
    
    @FXML
    private void meja83Action(ActionEvent actionEvent){
        displayOrder((short)83);
    }
    
    @FXML
    private void meja84Action(ActionEvent actionEvent){
        displayOrder((short)84);
    }
    
    @FXML
    private void meja85Action(ActionEvent actionEvent){
        displayOrder((short)85);
    }
    
    @FXML
    private void meja86Action(ActionEvent actionEvent){
        displayOrder((short)86);
    }

    @FXML
    private void meja87Action(ActionEvent actionEvent){
        displayOrder((short)87);
    }
    
    @FXML
    private void meja88Action(ActionEvent actionEvent){
        displayOrder((short)88);
    }
    
    @FXML
    private void meja89Action(ActionEvent actionEvent){
        displayOrder((short)89);
    }
    
    @FXML
    private void meja90Action(ActionEvent actionEvent){
        displayOrder((short)90);
    }
    
    @FXML
    private void meja91Action(ActionEvent actionEvent){
        displayOrder((short)91);
    }

    @FXML
    private void meja92Action(ActionEvent actionEvent){
        displayOrder((short)92);
    }
    
    @FXML
    private void meja93Action(ActionEvent actionEvent){
        displayOrder((short)93);
    }
    
    @FXML
    private void meja94Action(ActionEvent actionEvent){
        displayOrder((short)94);
    }
    
    @FXML
    private void meja95Action(ActionEvent actionEvent){
        displayOrder((short)95);
    }
    
    @FXML
    private void meja96Action(ActionEvent actionEvent){
        displayOrder((short)96);
    }

    @FXML
    private void meja97Action(ActionEvent actionEvent){
        displayOrder((short)97);
    }
    
    @FXML
    private void meja98Action(ActionEvent actionEvent){
        displayOrder((short)98);
    }
    
    @FXML
    private void meja99Action(ActionEvent actionEvent){
        displayOrder((short)99);
    }
    
    @FXML
    private void meja100Action(ActionEvent actionEvent){
        displayOrder((short)100);
    }
    
//    @FXML
//    private void meja101Action(ActionEvent actionEvent){
//        displayOrder((short)101);
//    }
//
//    @FXML
//    private void meja102Action(ActionEvent actionEvent){
//        displayOrder((short)102);
//    }
//    
//    @FXML
//    private void meja103Action(ActionEvent actionEvent){
//        displayOrder((short)103);
//    }
//    
//    @FXML
//    private void meja104Action(ActionEvent actionEvent){
//        displayOrder((short)104);
//    }

    private void getMejaStatus() {
        List resultList = iMeja.getAll();
        if(resultList != null){
            for(int i=0; i<resultList.size(); i++){
                Meja meja = (Meja) resultList.get(i);
                switch (meja.getId()) {
                    case 1:
                        setStatusMeja(meja1, meja.getStatus(), meja.getNomor());
                        break;
                    case 2:
                        setStatusMeja(meja2, meja.getStatus(), meja.getNomor());
                        break;
                    case 3:
                        setStatusMeja(meja3, meja.getStatus(), meja.getNomor());
                        break;
                    case 4:
                        setStatusMeja(meja4, meja.getStatus(), meja.getNomor());
                        break;
                    case 5:
                        setStatusMeja(meja5, meja.getStatus(), meja.getNomor());
                        break;
                    case 6:
                        setStatusMeja(meja6, meja.getStatus(), meja.getNomor());
                        break;
                    case 7:
                        setStatusMeja(meja7, meja.getStatus(), meja.getNomor());
                        break;
                    case 8:
                        setStatusMeja(meja8, meja.getStatus(), meja.getNomor());
                        break;
                    case 9:
                        setStatusMeja(meja9, meja.getStatus(), meja.getNomor());
                        break;
                    case 10:
                        setStatusMeja(meja10, meja.getStatus(), meja.getNomor());
                        break;
                    case 11:
                        setStatusMeja(meja11, meja.getStatus(), meja.getNomor());
                        break;
                    case 12:
                        setStatusMeja(meja12, meja.getStatus(), meja.getNomor());
                        break;
                    case 13:
                        setStatusMeja(meja13, meja.getStatus(), meja.getNomor());
                        break;
                    case 14:
                        setStatusMeja(meja14, meja.getStatus(), meja.getNomor());
                        break;
                    case 15:
                        setStatusMeja(meja15, meja.getStatus(), meja.getNomor());
                        break;
                    case 16:
                        setStatusMeja(meja16, meja.getStatus(), meja.getNomor());
                        break;
                    case 17:
                        setStatusMeja(meja17, meja.getStatus(), meja.getNomor());
                        break;
                    case 18:
                        setStatusMeja(meja18, meja.getStatus(), meja.getNomor());
                        break;
                    case 19:
                        setStatusMeja(meja19, meja.getStatus(), meja.getNomor());
                        break;
                    case 20:
                        setStatusMeja(meja20, meja.getStatus(), meja.getNomor());
                        break;
                    case 21:
                        setStatusMeja(meja21, meja.getStatus(), meja.getNomor());
                        break;
                    case 22:
                        setStatusMeja(meja22, meja.getStatus(), meja.getNomor());
                        break;
                    case 23:
                        setStatusMeja(meja23, meja.getStatus(), meja.getNomor());
                        break;
                    case 24:
                        setStatusMeja(meja24, meja.getStatus(), meja.getNomor());
                        break;
                    case 25:
                        setStatusMeja(meja25, meja.getStatus(), meja.getNomor());
                        break;
                    case 26:
                        setStatusMeja(meja26, meja.getStatus(), meja.getNomor());
                        break;
                    case 27:
                        setStatusMeja(meja27, meja.getStatus(), meja.getNomor());
                        break;
                    case 28:
                        setStatusMeja(meja28, meja.getStatus(), meja.getNomor());
                        break;
                    case 29:
                        setStatusMeja(meja29, meja.getStatus(), meja.getNomor());
                        break;
                    case 30:
                        setStatusMeja(meja30, meja.getStatus(), meja.getNomor());
                        break;
                    case 31:
                        setStatusMeja(meja31, meja.getStatus(), meja.getNomor());
                        break;
                    case 32:
                        setStatusMeja(meja32, meja.getStatus(), meja.getNomor());
                        break;
                    case 33:
                        setStatusMeja(meja33, meja.getStatus(), meja.getNomor());
                        break;
                    case 34:
                        setStatusMeja(meja34, meja.getStatus(), meja.getNomor());
                        break;
                    case 35:
                        setStatusMeja(meja35, meja.getStatus(), meja.getNomor());
                        break;
                    case 36:
                        setStatusMeja(meja36, meja.getStatus(), meja.getNomor());
                        break;
                    case 37:
                        setStatusMeja(meja37, meja.getStatus(), meja.getNomor());
                        break;
                    case 38:
                        setStatusMeja(meja38, meja.getStatus(), meja.getNomor());
                        break;
                    case 39:
                        setStatusMeja(meja39, meja.getStatus(), meja.getNomor());
                        break;
                    case 40:
                        setStatusMeja(meja40, meja.getStatus(), meja.getNomor());
                        break;
                    case 41:
                        setStatusMeja(meja41, meja.getStatus(), meja.getNomor());
                        break;
                    case 42:
                        setStatusMeja(meja42, meja.getStatus(), meja.getNomor());
                        break;
                    case 43:
                        setStatusMeja(meja43, meja.getStatus(), meja.getNomor());
                        break;
                    case 44:
                        setStatusMeja(meja44, meja.getStatus(), meja.getNomor());
                        break;
                    case 45:
                        setStatusMeja(meja45, meja.getStatus(), meja.getNomor());
                        break;
                    case 46:
                        setStatusMeja(meja46, meja.getStatus(), meja.getNomor());
                        break;
                    case 47:
                        setStatusMeja(meja47, meja.getStatus(), meja.getNomor());
                        break;
                    case 48:
                        setStatusMeja(meja48, meja.getStatus(), meja.getNomor());
                        break;
                    case 49:
                        setStatusMeja(meja49, meja.getStatus(), meja.getNomor());
                        break;
                    case 50:
                        setStatusMeja(meja50, meja.getStatus(), meja.getNomor());
                        break;
                    case 51:
                        setStatusMeja(meja51, meja.getStatus(), meja.getNomor());
                        break;
                    case 52:
                        setStatusMeja(meja52, meja.getStatus(), meja.getNomor());
                        break;
                    case 53:
                        setStatusMeja(meja53, meja.getStatus(), meja.getNomor());
                        break;
                    case 54:
                        setStatusMeja(meja54, meja.getStatus(), meja.getNomor());
                        break;
                    case 55:
                        setStatusMeja(meja55, meja.getStatus(), meja.getNomor());
                        break;
                    case 56:
                        setStatusMeja(meja56, meja.getStatus(), meja.getNomor());
                        break;
                    case 57:
                        setStatusMeja(meja57, meja.getStatus(), meja.getNomor());
                        break;
                    case 58:
                        setStatusMeja(meja58, meja.getStatus(), meja.getNomor());
                        break;
                    case 59:
                        setStatusMeja(meja59, meja.getStatus(), meja.getNomor());
                        break;
                    case 60:
                        setStatusMeja(meja60, meja.getStatus(), meja.getNomor());
                        break;
                    case 61:
                        setStatusMeja(meja61, meja.getStatus(), meja.getNomor());
                        break;
                    case 62:
                        setStatusMeja(meja62, meja.getStatus(), meja.getNomor());
                        break;
                    case 63:
                        setStatusMeja(meja63, meja.getStatus(), meja.getNomor());
                        break;
                    case 64:
                        setStatusMeja(meja64, meja.getStatus(), meja.getNomor());
                        break;
                    case 65:
                        setStatusMeja(meja65, meja.getStatus(), meja.getNomor());
                        break;
                    case 66:
                        setStatusMeja(meja66, meja.getStatus(), meja.getNomor());
                        break;
                    case 67:
                        setStatusMeja(meja67, meja.getStatus(), meja.getNomor());
                        break;
                    case 68:
                        setStatusMeja(meja68, meja.getStatus(), meja.getNomor());
                        break;
                    case 69:
                        setStatusMeja(meja69, meja.getStatus(), meja.getNomor());
                        break;
                    case 70:
                        setStatusMeja(meja70, meja.getStatus(), meja.getNomor());
                        break;
                    case 71:
                        setStatusMeja(meja71, meja.getStatus(), meja.getNomor());
                        break;
                    case 72:
                        setStatusMeja(meja72, meja.getStatus(), meja.getNomor());
                        break;
                    case 73:
                        setStatusMeja(meja73, meja.getStatus(), meja.getNomor());
                        break;
                    case 74:
                        setStatusMeja(meja74, meja.getStatus(), meja.getNomor());
                        break;
                    case 75:
                        setStatusMeja(meja75, meja.getStatus(), meja.getNomor());
                        break;
                    case 76:
                        setStatusMeja(meja76, meja.getStatus(), meja.getNomor());
                        break;
                    case 77:
                        setStatusMeja(meja77, meja.getStatus(), meja.getNomor());
                        break;
                    case 78:
                        setStatusMeja(meja78, meja.getStatus(), meja.getNomor());
                        break;
                    case 79:
                        setStatusMeja(meja79, meja.getStatus(), meja.getNomor());
                        break;
                    case 80:
                        setStatusMeja(meja80, meja.getStatus(), meja.getNomor());
                        break;
                    case 81:
                        setStatusMeja(meja81, meja.getStatus(), meja.getNomor());
                        break;
                    case 82:
                        setStatusMeja(meja82, meja.getStatus(), meja.getNomor());
                        break;
                    case 83:
                        setStatusMeja(meja83, meja.getStatus(), meja.getNomor());
                        break;
                    case 84:
                        setStatusMeja(meja84, meja.getStatus(), meja.getNomor());
                        break;
                    case 85:
                        setStatusMeja(meja85, meja.getStatus(), meja.getNomor());
                        break;
                    case 86:
                        setStatusMeja(meja86, meja.getStatus(), meja.getNomor());
                        break;
                    case 87:
                        setStatusMeja(meja87, meja.getStatus(), meja.getNomor());
                        break;
                    case 88:
                        setStatusMeja(meja88, meja.getStatus(), meja.getNomor());
                        break;
                    case 89:
                        setStatusMeja(meja89, meja.getStatus(), meja.getNomor());
                        break;
                    case 90:
                        setStatusMeja(meja90, meja.getStatus(), meja.getNomor());
                        break;
                    case 91:
                        setStatusMeja(meja91, meja.getStatus(), meja.getNomor());
                        break;
                    case 92:
                        setStatusMeja(meja92, meja.getStatus(), meja.getNomor());
                        break;
                    case 93:
                        setStatusMeja(meja93, meja.getStatus(), meja.getNomor());
                        break;
                    case 94:
                        setStatusMeja(meja94, meja.getStatus(), meja.getNomor());
                        break;
                    case 95:
                        setStatusMeja(meja95, meja.getStatus(), meja.getNomor());
                        break;
                    case 96:
                        setStatusMeja(meja96, meja.getStatus(), meja.getNomor());
                        break;
                    case 97:
                        setStatusMeja(meja97, meja.getStatus(), meja.getNomor());
                        break;
                    case 98:
                        setStatusMeja(meja98, meja.getStatus(), meja.getNomor());
                        break;
                    case 99:
                        setStatusMeja(meja99, meja.getStatus(), meja.getNomor());
                        break;
                    case 100:
                        setStatusMeja(meja100, meja.getStatus(), meja.getNomor());
                        break;
//                    case 101:
//                        setStatusMeja(meja101, meja.getStatus(), meja.getNomor());
//                        break;
//                    case 102:
//                        setStatusMeja(meja102, meja.getStatus(), meja.getNomor());
//                        break;
//                    case 103:
//                        setStatusMeja(meja103, meja.getStatus(), meja.getNomor());
//                        break;
//                    case 104:
//                        setStatusMeja(meja104, meja.getStatus(), meja.getNomor());
//                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void setStatusMeja(Button meja, Short status, String nomor) {
        switch (status) {
            case CommonConstant.MEJA_TERISI:
                meja.setStyle("-fx-background-color: #ff9800");
                break;
            case CommonConstant.MEJA_DISABLE:
                meja.setDisable(true);
                break;
            case CommonConstant.MEJA_INVISIBLE:
                meja.setVisible(false);
                break;
            default:
                meja.setDisable(false);
                meja.setVisible(true);
                meja.setStyle(null);
                break;
        }
        meja.setText(nomor);
    }
    
    @FXML
    private void pesanBtn(ActionEvent actionEvent){
        if(mejaActive > 0){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(AdminController.class.getResource("Pesan.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Pesan");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);

                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                PesanController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                if(menuMejaObsList != null && menuMejaObsList.size() > 0){
                    controller.setTransaksiId(menuMejaObsList.get(0).getTransaksiId());
                }else{
                    controller.setTransaksiId(iTransaksi.insertByMeja(mejaActive, loginUser.getId()));
                }

                dialogStage.showAndWait();

                if(controller.getPesanPropObsList() != null && controller.getPesanPropObsList().size() > 0){
                    getMejaStatus();
                    displayOrder(mejaActive);
                }
               
            } catch (IOException ex) {
                LOGGER.error("failed to load Pesan.fxml", ex);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("Tidak ada meja yang dipilih");
            alert.setContentText("Silahkan pilih meja terlebih dahulu");
            
            alert.showAndWait();
        }
    }

    private void setDaftarMenu() {
//        getChoiceList();
        jenisBox.setItems(choicesString);
//        jenisBox.getSelectionModel().select(0);       
        jenisBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(null != newValue){
                setMenuTable(choicesMap.get(newValue));
            }
        });
        noColumn.setCellValueFactory((TableColumn.CellDataFeatures<MenuProperty, MenuProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumn.setCellFactory((TableColumn<MenuProperty, MenuProperty> param) -> new TableCell<MenuProperty, MenuProperty>() {
            @Override protected void updateItem(MenuProperty item, boolean empty) {
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
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().deletedFlagProperty());
        setMenuTable((short) 0);
        jenisMenuBox.setItems(choicesList);
        menuTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> displayEdit(newValue));
        menuStatusCB.getItems().addAll("Aktif", "Tidak Aktif");
    }
    
    private void setMenuTable(short jenis) {
        if(menuPropObsList != null){
            menuPropObsList.clear();
            searchTextField.clear();
        }
        menuPropObsList = iMenu.getAllProperty(jenis);
        displayFilteredData();
    }

    private void setJenisMenu() {
        noColumnJenis.setCellValueFactory((TableColumn.CellDataFeatures<JenisMenuProperty, JenisMenuProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumnJenis.setCellFactory((TableColumn<JenisMenuProperty, JenisMenuProperty> param) -> new TableCell<JenisMenuProperty, JenisMenuProperty>() {
            @Override protected void updateItem(JenisMenuProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noColumnJenis.setSortable(false);
        namaColumnJenis.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        statusColumnJenis.setCellValueFactory(cellData -> cellData.getValue().deletedFlagProperty());
        setJenisMenuTable();
        jenisMenuTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> displayEditJenis(newValue));
        jenisStatusCB.getItems().addAll("Aktif", "Tidak Aktif");
    }
    
    private void setJenisMenuTable() {
        if(jenisMenuPropList != null){
            jenisMenuPropList.clear();
            jenisMenuTable.getItems().clear();
        }
        jenisMenuPropList = iJenis.getAllProperty();
        jenisMenuTable.setItems(jenisMenuPropList);
    }

    private void setUsers() {
        noColumnUsers.setCellValueFactory((TableColumn.CellDataFeatures<UsersProperty, UsersProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumnUsers.setCellFactory((TableColumn<UsersProperty, UsersProperty> param) -> new TableCell<UsersProperty, UsersProperty>() {
            @Override protected void updateItem(UsersProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noColumnUsers.setSortable(false);
        namaColumnUsers.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        posisiColumnUsers.setCellValueFactory(cellData -> cellData.getValue().posisiNamaProperty());
        posisiUsers.getItems().addAll("Administrator", "Kasir");
        userStatusCB.getItems().addAll("Aktif", "Tidak Aktif");
        setUsersTable();
        usersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue){
                namaUsersTF.setText(newValue.getNama());
                userNameUsersTF.setText(newValue.getUserName());
                passwordUsersTF.setText(newValue.getPassword());
                userStatusCB.setValue(newValue.deletedFlagProperty().get());
                if(newValue.getPosisiId() != CommonConstant.USER_SUPER){
                    namaUsersTF.setDisable(false);
                    posisiUsers.setDisable(false);
                    posisiUsers.setValue(newValue.getPosisiNama());
                    userStatusCB.setDisable(false);
//                    usersHapusBtn.setDisable(false);
                }else{
                    namaUsersTF.setDisable(true);
                    posisiUsers.setValue(null);
                    posisiUsers.setDisable(true);
                    userStatusCB.setDisable(true);
//                    usersHapusBtn.setDisable(true);
                }
            }
        });
    }
    
    private void setUsersTable() {
        if(usersObsList != null){
            usersObsList.clear();
            usersTable.getItems().clear();
        }
        usersObsList = iUsers.getAll();
        usersTable.setItems(usersObsList);
    }
    
    @FXML
    private void usersSimpanBtn(ActionEvent event) {
        TextField[] textFields = {namaUsersTF, userNameUsersTF, passwordUsersTF};
        String[] namaTextFields = {"Nama", "Username", "Password"};
        List<ChoiceBox<String>> choiceBoxs = new ArrayList<>();
        choiceBoxs.add(posisiUsers);
        choiceBoxs.add(userStatusCB);
        String[] namaChoiceBoxs = {"Posisi", "Status"};
        if(iValidation.isTextFieldInputValid(textFields, namaTextFields, choiceBoxs, namaChoiceBoxs, primaryStage)) {
            UsersProperty selectedUsersProp = usersTable.getSelectionModel().getSelectedItem();
            Users users = new Users();
            Posisi posisi = new Posisi();
            short posisiId = 0;
//            if(null != posisiUsers.getValue()){
                posisiId = iPosisi.getIdByName(posisiUsers.getValue());
//            }
            users.setNama(namaUsersTF.getText());
            users.setUsername(userNameUsersTF.getText());
            users.setPassword(passwordUsersTF.getText());
            posisi.setId(posisiId);
            users.setPosisiId(posisi);
            users.setDeletedFlag(userStatusCB.getValue().equalsIgnoreCase("Aktif") ? (short) 0 : (short) 1);
            if(null != selectedUsersProp){
                users.setId(selectedUsersProp.getId());
                iUsers.update(users, primaryStage);
//                if(iUsers.update(users, primaryStage)){
//                    selectedUsersProp.setNama(namaUsersTF.getText());
//                    selectedUsersProp.setUserName(userNameUsersTF.getText());
//                    selectedUsersProp.setPassword(passwordUsersTF.getText());
//                    selectedUsersProp.setPosisiId(posisiId);
//                    selectedUsersProp.setPosisiNama(posisiUsers.getValue());
//                }
            }else{
//                selectedUsersProp = new UsersProperty();
                iUsers.insert(users, primaryStage);
//                short id = iUsers.insert(users, primaryStage);
//                selectedUsersProp.setId(id);
//                selectedUsersProp.setNama(namaUsersTF.getText());
//                selectedUsersProp.setUserName(userNameUsersTF.getText());
//                selectedUsersProp.setPassword(passwordUsersTF.getText());
//                selectedUsersProp.setPosisiId(posisiId);
//                selectedUsersProp.setPosisiNama(posisiUsers.getValue());
//                usersObsList.add(selectedUsersProp);
            }
            usersBatalBtn(event);
            setUsersTable();
        }
    }
    
    @FXML
    private void usersBatalBtn(ActionEvent event) {
        usersTable.getSelectionModel().clearSelection();
        namaUsersTF.setText("");
        userNameUsersTF.setText("");
        passwordUsersTF.setText("");
        posisiUsers.setValue(null);
        userStatusCB.setValue(null);
    }
    
    @FXML
    private void usersHapusAction(ActionEvent event) {
        UsersProperty selectedUsersProp = usersTable.getSelectionModel().getSelectedItem();
        if(null != selectedUsersProp){
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Konfirmasi");
            alertConfirm.setHeaderText("Anda Yakin?");

            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK){
                iUsers.delete(selectedUsersProp.getId());
                usersObsList.remove(selectedUsersProp);
                usersTable.getSelectionModel().clearSelection();
                usersBatalBtn(event);
            }
        }else{
            //Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("Tidak ada user yang dipilih");
            alert.setContentText("Silahkan pilih user terlebih dahulu");
            
            alert.showAndWait();
        }
    }

    private void setBungkus() {
        bungkusObsList.clear();
        bungkusObsList = iTransaksi.getBungkus();
        noColumnBungkusNama.setCellValueFactory((TableColumn.CellDataFeatures<TransaksiProperty, TransaksiProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumnBungkusNama.setCellFactory((TableColumn<TransaksiProperty, TransaksiProperty> param) -> new TableCell<TransaksiProperty, TransaksiProperty>() {
            @Override protected void updateItem(TransaksiProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noColumnBungkusNama.setSortable(false);
        namaColumnBungkusNama.setCellValueFactory(cellData -> cellData.getValue().namaPemesanProperty());
        bungkusNamaTable.setItems(bungkusObsList);
        bungkusNamaTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue){
                displayOrderBungkus(newValue);
            }
        });
    }
    
    @FXML
    private void pesanBungkusBtn(ActionEvent event) {
        try {
            selectedNamaBungkus = bungkusNamaTable.getSelectionModel().getSelectedItem();
            if(null != selectedNamaBungkus) {
                dataEntryMenu(selectedNamaBungkus);
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(primaryStage);
                alert.setTitle("No Selection");
                alert.setHeaderText("Tidak ada pemesan yang dipilih");
                alert.setContentText("Silahkan pilih pemesan terlebih dahulu");

                alert.showAndWait();
            }
        } catch (IOException ex) {
            LOGGER.error("", ex);
            }
    }
    
    @FXML
    private void tambahBaruBungkusBtn(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminController.class.getResource("NamaPemesanBungkus.fxml"));
            VBox page = (VBox) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Masukkan Nama Pemesan");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            NamaPemesanBungkusController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setLoginUser(loginUser);

            dialogStage.showAndWait();
 
            if(controller.isOkClicked() == true){
                TransaksiProperty transaksiProperty = new TransaksiProperty();
                transaksiProperty.setId(controller.getId());
                dataEntryMenu(transaksiProperty);
                bungkusObsList.clear();
                bungkusObsList = iTransaksi.getBungkus();
                bungkusNamaTable.setItems(bungkusObsList);
                bungkusNamaTable.getSelectionModel().selectLast();
            }
        } catch (IOException e) {
            LOGGER.error("failed to load NamaPemesanBungkus.fxml", e);
        }
    }

    private void displayOrderBungkus(TransaksiProperty tp) {
        menuBungkusObsList.clear();
        totalHargaBungkus = 0;
        if(null != tp){
            menuBungkusObsList = iPesan.getDetailByTransaksiId(tp.getId());
            namaBungkus.setText(tp.getNamaPemesan());
            for(int i=0; i<menuBungkusObsList.size(); i++){
                totalHargaBungkus += menuBungkusObsList.get(i).getTotalHarga();
            }
        }else{
            namaBungkus.setText("");
        }
        namaColumnBungkus.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        jumlahColumnBungkus.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty());
        bungkusMenuTable.setItems(menuBungkusObsList);
        totalBungkus.setText(numberFormat.format(totalHargaBungkus));
    }

    private void dataEntryMenu(TransaksiProperty transaksiProperty) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AdminController.class.getResource("Pesan.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Pesan");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        PesanController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setTransaksiId(transaksiProperty.getId());
        dialogStage.showAndWait();
        bungkusNamaTable.getSelectionModel().clearSelection();
        if(controller.getPesanPropObsList() != null && controller.getPesanPropObsList().size() > 0){
            bungkusNamaTable.getSelectionModel().select(transaksiProperty);
        }
    }
    
    @FXML
    private void mejaBatalBtn(ActionEvent actionEvent) {
        if(mejaActive > 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi");
            alert.setHeaderText("Anda Yakin?");
//            alert.setContentText("Anda Yakin?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Transaksi transaksi = new Transaksi();
                transaksi.setId(menuMejaObsList.get(0).getTransaksiId());
                transaksi.setStatus(CommonConstant.TRANSAKSI_BATAL);
                transaksi.setTotal(totalHargaMeja);
                Users users = new Users();
                users.setId(loginUser.getId());
                transaksi.setUserEnd(users);
                iTransaksi.updateStatus(transaksi);
                Meja meja = new Meja();
                meja.setId(mejaActive);
                meja.setStatus(CommonConstant.MEJA_TERSEDIA);
                iMeja.update(meja);
                getMejaStatus();
                displayOrder((short) 0);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("Tidak ada meja yang dipilih");
            alert.setContentText("Silahkan pilih meja terlebih dahulu");
            
            alert.showAndWait();
        }
        
    }
    
    @FXML
    private void bungkusBatalBtn(ActionEvent actionEvent) {
        selectedNamaBungkus = bungkusNamaTable.getSelectionModel().getSelectedItem();
        if(selectedNamaBungkus != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi");
            alert.setHeaderText("Anda Yakin?");
//            alert.setContentText("Anda Yakin?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Transaksi transaksi = new Transaksi();
                transaksi.setId(selectedNamaBungkus.getId());
                transaksi.setStatus(CommonConstant.TRANSAKSI_BATAL);
                transaksi.setTotal(totalHargaBungkus);
                Users users = new Users();
                users.setId(loginUser.getId());
                transaksi.setUserEnd(users);
                iTransaksi.updateStatus(transaksi);
                bungkusNamaTable.getSelectionModel().clearSelection();
                bungkusObsList.remove(selectedNamaBungkus);
                displayOrderBungkus(new TransaksiProperty());
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("Tidak ada pemesan yang dipilih");
            alert.setContentText("Silahkan pilih pemesan terlebih dahulu");
            
            alert.showAndWait();
        }
        
    }
    
    @FXML
    private void pindahAction(ActionEvent actionEvent) {
        List<String> choices = iMeja.getAvailableForPindah(mejaActive);
        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Pilihan");
        dialog.setHeaderText("Pindah Meja");
        dialog.setContentText("Silahkan pilih meja:");

        Optional<String> result = dialog.showAndWait();

        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(letter -> {
            short id = iMeja.getIdByNomor(letter);
            Transaksi transaksi = new Transaksi();
            transaksi.setId(menuMejaObsList.get(0).getTransaksiId());
            Meja meja = new Meja(id);
            transaksi.setMejaId(meja);
            iTransaksi.updateMejaNo(transaksi);
            Meja oldMeja = new Meja();
            oldMeja.setId(mejaActive);
            oldMeja.setStatus(CommonConstant.MEJA_TERSEDIA);
            iMeja.update(oldMeja);
            Meja newMeja = new Meja();
            newMeja.setId(id);
            newMeja.setStatus(CommonConstant.MEJA_TERISI);
            iMeja.update(newMeja);
            mejaActive = id;
            getMejaStatus();
            displayOrder(mejaActive);
        });
    }
    
    @FXML
    private void btnCetakMeja(ActionEvent actionEvent) throws JRException, ClassNotFoundException, SQLException, IOException {
        if(mejaActive > 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi");
            alert.setHeaderText("Anda Yakin?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                InputStream logoStream = this.getClass().getResourceAsStream("img/logo_kedai.jpg");
                InputStream reportStream = this.getClass().getResourceAsStream("reports/print_A7_logo.jasper");
                
                HashMap<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("logoImage", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(logoStream))));
                parameters.put("title", CommonConstant.KEDAI_NAMA);
                parameters.put("subtitle", CommonConstant.KEDAI_SUB_NAMA);
                parameters.put("alamat1", CommonConstant.KEDAI_ALAMAT1);
                parameters.put("alamat2", CommonConstant.KEDAI_ALAMAT2);
                parameters.put("alamat3", CommonConstant.KEDAI_ALAMAT3);
                parameters.put("waktu", new Date());
                parameters.put("meja", "Meja "+iMeja.getNomorById(mejaActive));
                parameters.put("kasir", loginUser.getNama());
                parameters.put("total", numberFormat.format(totalHargaMeja));

                JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(menuMejaObsList);
                JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanCollectionDataSource);
                JasperPrintManager.printReport(print, false);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("Tidak ada meja yang dipilih");
            alert.setContentText("Silahkan pilih meja terlebih dahulu");
            
            alert.showAndWait();
        }
    }
    
    @FXML
    private void btnCetakBungkus(ActionEvent actionEvent) throws JRException, ClassNotFoundException, SQLException, IOException {
        selectedNamaBungkus = bungkusNamaTable.getSelectionModel().getSelectedItem();
        if(selectedNamaBungkus != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi");
            alert.setHeaderText("Anda Yakin?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                InputStream logoStream = this.getClass().getResourceAsStream("img/logo_kedai.jpg");
                InputStream reportStream = this.getClass().getResourceAsStream("reports/print_A7_logo.jasper");
                HashMap<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("logoImage", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(logoStream))));
                parameters.put("title", CommonConstant.KEDAI_NAMA);
                parameters.put("subtitle", CommonConstant.KEDAI_SUB_NAMA);
                parameters.put("alamat1", CommonConstant.KEDAI_ALAMAT1);
                parameters.put("alamat2", CommonConstant.KEDAI_ALAMAT2);
                parameters.put("alamat3", CommonConstant.KEDAI_ALAMAT3);
                parameters.put("waktu", new Date());
                parameters.put("meja", namaBungkus.getText());
                parameters.put("kasir", loginUser.getNama());
                parameters.put("total", numberFormat.format(totalHargaBungkus));
                JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(menuBungkusObsList);
                JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanCollectionDataSource);
                JasperPrintManager.printReport(print, false);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("Tidak ada pemesan yang dipilih");
            alert.setContentText("Silahkan pilih pemesan terlebih dahulu");
            
            alert.showAndWait();
        }
    }
    
    @FXML
    private void bayarBtn(ActionEvent actionEvent) {
        if(mejaActive > 0){
            TextInputDialog dialog = new TextInputDialog("0");
            dialog.setTitle("Bayar");
            dialog.setHeaderText("Tunai");

            Optional<String> result = dialog.showAndWait();

            // The Java 8 way to get the response value (with lambda expression).
            result.ifPresent(tunai -> {
                bayarDialog(Integer.parseInt(tunai));
            });
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("Tidak ada meja yang dipilih");
            alert.setContentText("Silahkan pilih meja terlebih dahulu");
            
            alert.showAndWait();
        }
    }

    private void bayarDialog(Integer tunai) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(AdminController.class.getResource("BayarDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Bayar");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);

                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                BayarDialogController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.initValue(menuMejaObsList, tunai, loginUser);

                dialogStage.showAndWait();
                
                if(controller.isOkClicked()){
                    InputStream logoStream = this.getClass().getResourceAsStream("img/logo_kedai.jpg");
                    InputStream reportStream = this.getClass().getResourceAsStream("reports/bayar_A7_logo.jasper");                
                    HashMap<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("logoImage", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(logoStream))));
                    parameters.put("title", CommonConstant.KEDAI_NAMA);
                    parameters.put("subtitle", CommonConstant.KEDAI_SUB_NAMA);
                    parameters.put("alamat1", CommonConstant.KEDAI_ALAMAT1);
                    parameters.put("alamat2", CommonConstant.KEDAI_ALAMAT2);
                    parameters.put("alamat3", CommonConstant.KEDAI_ALAMAT3);
                    parameters.put("waktu", new Date());
                    parameters.put("meja", "Meja " + iMeja.getNomorById(mejaActive));
                    parameters.put("kasir", loginUser.getNama());
                    parameters.put("total", numberFormat.format(totalHargaMeja));
                    parameters.put("tunai", numberFormat.format(tunai));
                    parameters.put("kembali", numberFormat.format(tunai-totalHargaMeja));
                    parameters.put("syukran1", CommonConstant.KEDAI_SYUKRAN_1);
                    parameters.put("syukran2", CommonConstant.KEDAI_SYUKRAN_2);
                    JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(menuMejaObsList);
                    JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanCollectionDataSource);
                    JasperPrintManager.printReport(print, false);
                    getMejaStatus();
                    displayOrder((short) 0);
                }
                
            } catch (IOException e) {
                LOGGER.error("failed to load BayarDialog.fxml", e);
            } catch (JRException ex) {
                LOGGER.error("failed to print report", ex);
        }
        
    }
    
    @FXML
    private void bungkusBayarBtn(ActionEvent actionEvent) {
        selectedNamaBungkus = bungkusNamaTable.getSelectionModel().getSelectedItem();
        if(selectedNamaBungkus != null){
            TextInputDialog dialog = new TextInputDialog("0");
            dialog.setTitle("Bayar");
            dialog.setHeaderText("Tunai");

            Optional<String> result = dialog.showAndWait();

            // The Java 8 way to get the response value (with lambda expression).
            result.ifPresent(tunai -> {
                bungkusBayarDialog(Integer.parseInt(tunai));
            });
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("Tidak ada pemesan yang dipilih");
            alert.setContentText("Silahkan pilih pemesan terlebih dahulu");
            
            alert.showAndWait();
        }
    }
    
    private void bungkusBayarDialog(Integer tunai) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(AdminController.class.getResource("BayarDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Bayar");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);

                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                BayarDialogController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.initValue(menuBungkusObsList, tunai, loginUser);
                
                dialogStage.showAndWait();
                
                if(controller.isOkClicked()){
                    InputStream logoStream = this.getClass().getResourceAsStream("img/logo_kedai.jpg");
                    InputStream reportStream = this.getClass().getResourceAsStream("reports/bayar_A7_logo.jasper");
                    HashMap<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("logoImage", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(logoStream))));
                    parameters.put("title", CommonConstant.KEDAI_NAMA);
                    parameters.put("subtitle", CommonConstant.KEDAI_SUB_NAMA);
                    parameters.put("alamat1", CommonConstant.KEDAI_ALAMAT1);
                    parameters.put("alamat2", CommonConstant.KEDAI_ALAMAT2);
                    parameters.put("alamat3", CommonConstant.KEDAI_ALAMAT3);
                    parameters.put("waktu", new Date());
                    parameters.put("meja", selectedNamaBungkus.getNamaPemesan());
                    parameters.put("kasir", loginUser.getNama());
                    parameters.put("total", numberFormat.format(totalHargaBungkus));
                    parameters.put("tunai", numberFormat.format(tunai));
                    parameters.put("kembali", numberFormat.format(tunai-totalHargaBungkus));
                    parameters.put("syukran1", CommonConstant.KEDAI_SYUKRAN_1);
                    parameters.put("syukran2", CommonConstant.KEDAI_SYUKRAN_2);
                    JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(menuBungkusObsList);
                    JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanCollectionDataSource);
                    JasperPrintManager.printReport(print, false);
                    bungkusNamaTable.getSelectionModel().clearSelection();
                    bungkusObsList.remove(selectedNamaBungkus);
                    displayOrderBungkus(new TransaksiProperty());
                }
                
            } catch (IOException e) {
                LOGGER.error("failed to load BayarDialog.fxml", e);
            } catch (JRException ex) {
                LOGGER.error("failed to print report", ex);
            }
    }

    private void setPemasukan() {
        pemasukanDate.setValue(LocalDate.now());
        pemasukanDate.setConverter(new StringConverter<LocalDate>(){
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
            
        });
        pemasukanChoiceBox.setItems(usersChoicesString);
        pemasukanChoiceBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            pemasukanObsList.clear();
            if(null != newValue){
                setPemasukanTable(usersChoiceObsMap.get(newValue));
            }
        });
//        getUsersChoiceList();
        noColumnPemasukan.setCellValueFactory((TableColumn.CellDataFeatures<TransaksiProperty, TransaksiProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumnPemasukan.setCellFactory((TableColumn<TransaksiProperty, TransaksiProperty> param) -> new TableCell<TransaksiProperty, TransaksiProperty>() {
            @Override protected void updateItem(TransaksiProperty item, boolean empty) {
                super.updateItem(item, empty);
                
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noColumnPemasukan.setSortable(false);
        waktuColumnPemasukan.setCellValueFactory(cellData -> cellData.getValue().waktuProperty());
        mejaColumnPemasukan.setCellValueFactory(cellData -> cellData.getValue().mejaproperty().asObject());
        bungkusColumnPemasukan.setCellValueFactory(cellData -> cellData.getValue().namaPemesanProperty());
        statusColumnPemasukan.setCellValueFactory(cellData -> cellData.getValue().statusTransaksiproperty());
        totalColumnPemasukan.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
    }

    private void getUsersChoiceList() {
        setPemasukanTable(loginUser.getId());
        usersChoiceObsMap.clear();
        usersChoicesString.clear();
        usersChoiceObsMap.put("Semua", (short)0);
        usersChoicesString.add("Semua");
        List<Users> resultList = iUsers.getAllWithin99();
        if(null != resultList){
            resultList.stream().map((users) -> {
                usersChoiceObsMap.put(users.getNama(), users.getId());
                return users;
            }).forEachOrdered((users) -> {
                usersChoicesString.add(users.getNama());
            });
        }
        
    }

    private void setPemasukanTable(short userId) {
        Integer pemasukanTotalBayar = 0;
        Integer pemasukanTotalBatal = 0;
        pemasukanObsList = iTransaksi.getPemasukanByTglAndUser(pemasukanDate.getValue(), userId);
        for(int i = 0; i < pemasukanObsList.size(); i++){
            TransaksiProperty transaksiProperty = (TransaksiProperty) pemasukanObsList.get(i);
            if(transaksiProperty.getStatusTransaksi() == CommonConstant.TRANSAKSI_BAYAR){
                pemasukanTotalBayar += Integer.parseInt(transaksiProperty.getTotal());
            }else if(transaksiProperty.getStatusTransaksi() == CommonConstant.TRANSAKSI_BATAL){
                pemasukanTotalBatal += Integer.parseInt(transaksiProperty.getTotal());
            }
        }
        Integer pemasukanTotalSemua = pemasukanTotalBayar + pemasukanTotalBatal;
        totalBayar.setText(numberFormat.format(pemasukanTotalBayar));
        totalBatal.setText(numberFormat.format(pemasukanTotalBatal));
        totalSemua.setText(numberFormat.format(pemasukanTotalSemua));
        pemasukanTable.setItems(pemasukanObsList);
    }
    
    @FXML
    private void pemasukanPilihTanggal(ActionEvent actionEvent) {
        setPemasukanTable(usersChoiceObsMap.get(pemasukanChoiceBox.getSelectionModel().getSelectedItem()));
    }

    private void displayChart() {
//        grafikBulanCB.setItems(grafikBulanChoice);
        grafikBulanCB.getItems().addAll("JANUARI", "FEBRUARI", "MARET", "APRIL", "MEI", "JUNI", "JULI", "AGUSTUS", "SEPTEMBER", "NOVEMBER", "DESEMBER");
        grafikBulanCB.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && newValue.intValue() >= 0){
                int bulanBaru = newValue.intValue() + 1;
                grafikBulanan(bulanBaru);
            }
        });
        grafikTahunCB.setItems(iTransaksi.getYear());
        grafikTahunCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue){
                grafikTahunan(newValue);
            }
        });
        jenisLaporanCB.getItems().addAll("Keuangan", "Penjualan");
        jenisLaporanCB.getSelectionModel().selectFirst();
//        laporanTabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
//            if(laporanTabPane.getTabs().contains(grafikBulananTab) && newValue.equals(grafikBulananTab)){
//                grafikBulanSelected();
//            }else if(laporanTabPane.getTabs().contains(grafikTahunanTab) && newValue.equals(grafikTahunanTab)){
//                grafikTahunSelected();
//            }
//        });
//        grafikBulanSelected();
//        if(grafikBulanChoice == null){
//            grafikBulanChoice = FXCollections.observableArrayList();
//            for(int m = 1; m <= LocalDate.now().getMonthValue(); m++){
//                switch (m) {
//                    case CommonConstant.BULAN_JANUARI:
//                        grafikBulanChoice.add("JANUARI");
//                        break;
//                    case CommonConstant.BULAN_FEBRUARI:
//                        grafikBulanChoice.add("FEBRUARI");
//                        break;
//                    case CommonConstant.BULAN_MARET:
//                        grafikBulanChoice.add("MARET");
//                        break;
//                    case CommonConstant.BULAN_APRIL:
//                        grafikBulanChoice.add("APRIL");
//                        break;
//                    case CommonConstant.BULAN_MEI:
//                        grafikBulanChoice.add("MEI");
//                        break;
//                    case CommonConstant.BULAN_JUNI:
//                        grafikBulanChoice.add("JUNI");
//                        break;
//                    case CommonConstant.BULAN_JULI:
//                        grafikBulanChoice.add("JULI");
//                        break;
//                    case CommonConstant.BULAN_AGUSTUS:
//                        grafikBulanChoice.add("AGUSTUS");
//                        break;
//                    case CommonConstant.BULAN_SEPTEMBER:
//                        grafikBulanChoice.add("SEPTEMBER");
//                        break;
//                    case CommonConstant.BULAN_NOVEMBER:
//                        grafikBulanChoice.add("NOVEMBER");
//                        break;
//                    case CommonConstant.BULAN_DESEMBER:
//                        grafikBulanChoice.add("DESEMBER");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//        
//        grafikBulanCB.setItems(grafikBulanChoice);
//        grafikBulanCB.getSelectionModel().selectLast();
//        grafikBulanCB.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            int bulanBaru = newValue.intValue() + 1;
//            grafikBulanan(bulanBaru);
//        });
//        grafikBulanan(LocalDate.now().getMonthValue());
        
        //grafik tahunan
//        grafikTahunCB.setItems(iTransaksi.getYear());
//        grafikTahunCB.getSelectionModel().selectLast();
//        grafikTahunCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if(newValue != null){
//                grafikTahunan(newValue);
//            }
//        });
//        grafikTahunan(LocalDate.now().getYear());
        
        //grafik menu
//        grafikBulanMenuCB.setItems(grafikBulanChoice);
//        grafikBulanMenuCB.getSelectionModel().selectLast();
//        grafikBulanMenuCB.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
//            int bulanBaru = newValue.intValue() + 1;
//            System.out.println("start grafik menu 1");
//            long start = System.currentTimeMillis();
//            grafikMenuBulanan(bulanBaru, choicesMap.get(grafikBulanJenisMenuCB.getValue()));
//            long end = System.currentTimeMillis();
//            long duration = (end - start);
//            System.out.println("grafik menu 1 = " + duration);
//        });
//        grafikBulanJenisMenuCB.setItems(choicesList);
//        grafikBulanJenisMenuCB.getSelectionModel().select(0);
//        grafikBulanJenisMenuCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("start grafik menu 2");
//            long start = System.currentTimeMillis();
//            grafikMenuBulanan(bulanBaruMenu, choicesMap.get(newValue));
//            long end = System.currentTimeMillis();
//            long duration = (end - start);
//            System.out.println("grafik menu 2 = " + duration);
//        });
//        System.out.println("start grafik menu 3");
//        long start = System.currentTimeMillis();
//        grafikMenuBulanan(LocalDate.now().getMonthValue(), choicesMap.get(grafikBulanJenisMenuCB.getValue()));
//        long end = System.currentTimeMillis();
//        long duration = (end - start);
//        System.out.println("grafik menu 3 = " + duration);
        
        //Laporan PDF
//        jenisLaporanCB.setItems(null);
//        jenisLaporanCB.getItems().addAll("Keuangan", "Penjualan");
//        jenisLaporanCB.getSelectionModel().selectFirst();
        laporanDirectory.setText(System.getProperty("user.home"));
        transaksiDariDate.setValue(LocalDate.now());
        transaksiDariDate.setConverter(new StringConverter<LocalDate>(){
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        transaksiSampaiDate.setValue(LocalDate.now());
        transaksiSampaiDate.setConverter(new StringConverter<LocalDate>(){
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
            
        });
    }

    private void grafikBulanan(int bulan) {
        Locale locale = new Locale("id", "ID");
        String monthIndo = new DateFormatSymbols(locale).getMonths()[bulan-1];
        chartBulan.getData().clear();      
        xAxisBulanan.setLabel("Tanggal");
        List resultList1 = iTransaksi.getChartByBulan(bulan);
        List resultList2 = iPengeluaran.getChartByBulan(bulan);
        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), bulan);
        chartBulan.setTitle("Keuangan "+monthIndo+" "+LocalDate.now().getYear());
        XYChart.Series<String, Number> series1 = new XYChart.Series();
        series1.setName("Pemasukan");
        XYChart.Series<String, Number> series2 = new XYChart.Series();
        series2.setName("Pengeluaran");
        
        for(int i = 1; i <= yearMonth.lengthOfMonth(); i++){
            XYChart.Data<String, Number> data1 = new XYChart.Data(Integer.toString(i), 0);
            if(resultList1 != null){
                for(Object rows : resultList1){
                    Object[] row = (Object[]) rows;
                    Integer hari = (Integer) row[0];
                    Long tot = (Long) row[1];
                    if(i == hari){
                        data1.setYValue(tot);
                        break;
                    }
                }
            }
            series1.getData().add(data1);
            XYChart.Data<String, Number> data2 = new XYChart.Data(Integer.toString(i), 0);
            if(resultList2 != null){
                for(Object rows : resultList2){
                    Object[] row = (Object[]) rows;
                    Integer hari = (Integer) row[0];
                    Long tot = (Long) row[1];
                    if(i == hari){
                        data2.setYValue(tot);
                        break;
                    }
                }
            }
            series2.getData().add(data2);
        }
        chartBulan.getData().addAll(series1, series2);
        
        /**
         * Browsing through the Data and applying ToolTip
         * as well as the class on hover
         */
        for (XYChart.Series<String, Number> s : chartBulan.getData()) {
            for (XYChart.Data<String, Number> d : s.getData()) {
                Tooltip.install(d.getNode(), new Tooltip("Tanggal " + d.getXValue() + "\n" + "Rp. " + d.getYValue()));

                //Adding class on hover
//                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                //Removing class on exit
//                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            }
        }
    }

    private void grafikTahunan(int year) {
        chartTahun.getData().clear();
        xAxisTahunan.setLabel("Bulan");
        chartTahun.setTitle("Keuangan "+ year);
        List resultList1 = iTransaksi.getChartByTahun(year);
        List resultList2 = iPengeluaran.getChartByTahun(year);
        XYChart.Series<String, Number> series1 = new XYChart.Series();
        series1.setName("Pemasukan");
        XYChart.Series<String, Number> series2 = new XYChart.Series();
        series2.setName("Pengeluaran");
        for(int i = 1; i <= 12; i++){
            XYChart.Data<String, Number> data1 = new XYChart.Data(Integer.toString(i), 0);
            if(resultList1 != null){
                for(Object rows : resultList1){
                    Object[] row = (Object[]) rows;
                    Integer bln = (Integer) row[0];
                    Long tot = (Long) row[1];
                    if(i == bln){
                        data1.setYValue(tot);
                        break;
                    }
                }
            }
            series1.getData().add(data1);
            XYChart.Data<String, Number> data2 = new XYChart.Data(Integer.toString(i), 0);
            if(resultList2 != null){
                for(Object rows : resultList2){
                    Object[] row = (Object[]) rows;
                    Integer bln = (Integer) row[0];
                    Long tot = (Long) row[1];
                    if(i == bln){
                        data2.setYValue(tot);
                        break;
                    }
                }
            }
            series2.getData().add(data2);
        }
        
        chartTahun.getData().addAll(series1, series2);
        
        /**
         * Browsing through the Data and applying ToolTip
         * as well as the class on hover
         */
        for (XYChart.Series<String, Number> s : chartTahun.getData()) {
            for (XYChart.Data<String, Number> d : s.getData()) {
                Tooltip.install(d.getNode(), new Tooltip("Bulan " + d.getXValue() + "\n" + "Rp. " + d.getYValue()));

                //Adding class on hover
//                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                //Removing class on exit
//                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            }
        }
    }

    private void setPengeluaran() {
        
        pengeluaranDate.setValue(LocalDate.now());
        pengeluaranDate.setConverter(new StringConverter<LocalDate>(){
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
            
        });
        noColumnPengeluaran.setCellValueFactory((TableColumn.CellDataFeatures<PengeluaranProperty, PengeluaranProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumnPengeluaran.setCellFactory((TableColumn<PengeluaranProperty, PengeluaranProperty> param) -> new TableCell<PengeluaranProperty, PengeluaranProperty>() {
            @Override protected void updateItem(PengeluaranProperty item, boolean empty) {
                super.updateItem(item, empty);
                
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noColumnPemasukan.setSortable(false);
        namaColumnPengeluaran.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        hargaColumnPengeluaran.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        pengeluaranTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> displayEditPengeluaran(newValue));
        setPengeluaranTable();
    }
    
    @FXML
    private void pengeluaranPilihTanggal(ActionEvent actionEvent) {
        setPengeluaranTable();
    }

    private void setPengeluaranTable() {
        Integer totPengeluaran = 0;
        pengeluaranObsList.clear();
        pengeluaranObsList = iPengeluaran.getByDate(pengeluaranDate.getValue());
        pengeluaranTable.setItems(pengeluaranObsList);
        for(int i = 0; i < pengeluaranObsList.size(); i++) {
            totPengeluaran += Integer.parseInt(pengeluaranObsList.get(i).getHarga().replace(".", ""));
        }
        totalPengeluaran.setText(numberFormat.format(totPengeluaran));
    }
    
    private void displayEditPengeluaran(PengeluaranProperty pengeluaranProperty){
        if(null != pengeluaranProperty){
            namaPengeluaranTF.setText(pengeluaranProperty.getNama());
            hargaPengeluaranTF.setText(pengeluaranProperty.getHarga().replace(".", ""));
        }
    }
    
    @FXML
    private void pengeluaranSimpanAction(ActionEvent event){
        TextField[] textFields = {namaPengeluaranTF, hargaPengeluaranTF};
        String[] namaTextFields = {"Nama", "Harga"};
        if(iValidation.isTextFieldInputValid(textFields, namaTextFields, primaryStage)){
            PengeluaranProperty selectedPengeluaranProp = pengeluaranTable.getSelectionModel().getSelectedItem();
            Pengeluaran pengeluaran = new Pengeluaran();
            pengeluaran.setNama(namaPengeluaranTF.getText());
            pengeluaran.setHarga(Integer.parseInt(hargaPengeluaranTF.getText()));
            if(null != selectedPengeluaranProp){
                pengeluaran.setId(selectedPengeluaranProp.getId());
                iPengeluaran.update(pengeluaran);
            }else{
                pengeluaran.setDtOnly(Date.from(pengeluaranDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                iPengeluaran.insert(pengeluaran);
            }
            setPengeluaranTable();
            pengeluaranCancelAction(event);
        }
    }
    
    @FXML
    private void pengeluaranCancelAction(ActionEvent event){
        pengeluaranTable.getSelectionModel().clearSelection();
        namaPengeluaranTF.setText("");
        hargaPengeluaranTF.setText("");
    }
    
    @FXML
    private void pengeluaranDeleteAction(ActionEvent event){
        PengeluaranProperty selectedPengeluaranProp = pengeluaranTable.getSelectionModel().getSelectedItem();
        if(null != selectedPengeluaranProp){
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Konfirmasi");
            alertConfirm.setHeaderText("Anda Yakin?");
//            alertConfirm.setContentText("Anda Yakin?");

            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK){
                iPengeluaran.delete(selectedPengeluaranProp.getId());
                pengeluaranTable.getSelectionModel().clearSelection();
                setPengeluaranTable();
                pengeluaranCancelAction(event);
            }
        }else{
            //Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("Tidak ada yang dipilih");
            alert.setContentText("Silahkan pilih di daftar pengeluaran");
            
            alert.showAndWait();
        }
    }

//    private void grafikMenuBulanan(int bulan, Short jenisMenu) {
//        bulanBaruMenu = bulan;
//        Locale locale = new Locale("id", "ID");
//        String monthIndo = new DateFormatSymbols(locale).getMonths()[bulan-1];
//        chartMenuBulan.getData().clear();   
//        chartMenuBulan.setTitle("Total Penjualan Per Menu "+ monthIndo);
//        xAxisMenuBulanan.setLabel("Tanggal");
//        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), bulan);
//        long start = System.currentTimeMillis();
//        List<List> resultList = iMenu.getChartByMonthAndJenisMenu(bulan, jenisMenu);
//        long end = System.currentTimeMillis();
//        long duration = (end-start);
//        System.out.println("grafikMenuBulanan 1 duration = " + duration);
//        start = System.currentTimeMillis();
//        for(List parent : resultList){
//            XYChart.Series<String, Number> series = new XYChart.Series();
//            String menuNama = "";
//            for(int i = 1; i <= yearMonth.lengthOfMonth(); i++){
//                XYChart.Data<String, Number> data = new XYChart.Data(Integer.toString(i), 0);
//                for(int j = 0; j < parent.size(); j++){
//                    Object[] row = (Object[]) parent.get(j);
//                    Integer hari = (Integer) row[0];
//                    Short jumlah = (Short) row[1];
//                    if(i == hari){
//                        data.setYValue(jumlah.intValue());
//                        menuNama = (String) row[3];
//                        data.setExtraValue(menuNama);
//                        break;
//                    }
//                }
//                series.getData().add(data);
//            }
//            series.setName(menuNama);
//            chartMenuBulan.getData().add(series);
//        }
//        end = System.currentTimeMillis();
//        duration = (end-start);
//        System.out.println("grafikMenuBulanan 2 duration = " + duration);
//        
//        start = System.currentTimeMillis();
//        for (XYChart.Series<String, Number> s : chartMenuBulan.getData()) {
//            for (XYChart.Data<String, Number> d : s.getData()) {
//                Tooltip.install(d.getNode(), new Tooltip(d.getExtraValue()+"\n"+"Tanggal " + d.getXValue() + "\n" + "Jumlah: " + d.getYValue()));
//            }
//        }
//        end = System.currentTimeMillis();
//        duration = (end-start);
//        System.out.println("grafikMenuBulanan 3 duration = " + duration);
//    }

    private void adminMeja() {
        noColumnAdminMeja.setCellValueFactory(callData -> callData.getValue().idProperty().asObject());
        nomorColumnAdminMeja.setCellValueFactory(cellData -> cellData.getValue().nomorProperty());
        statusColumnAdminMeja.setCellValueFactory(callData -> callData.getValue().statusProperty());
        adminMejaObsList = iMeja.getAllProperty();
        adminMejaTable.setItems(adminMejaObsList);
        adminMejaTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            adminMejaSimpanBtn.setDisable(false);
            displayEditMeja(newValue);
        });
        adminMejaSimpanBtn.setDisable(true);        
        statusAdminMejaCB.getItems().addAll(CommonConstant.ADMIN_MEJA_ACTIVE, CommonConstant.ADMIN_MEJA_NON_ACTIVE, CommonConstant.ADMIN_MEJA_HIDDEN);
    }

    private void displayEditMeja(MejaProperty mejaProperty) {
        if(null != mejaProperty){
            nomorAdminMejaTF.setText(mejaProperty.getNomor());
            statusAdminMejaCB.setValue(mejaProperty.getStatus());
        }else{
            nomorAdminMejaTF.setText("");
            statusAdminMejaCB.setValue(null);
            adminMejaSimpanBtn.setDisable(true);
        }
    }
    
    @FXML
    private void adminMejaSimpanAction(ActionEvent actionEvent) {
        TextField[] textFields = {nomorAdminMejaTF};
        String[] namaTextFields = {"Nama"};
        List<ChoiceBox<String>> choiceBoxs = new ArrayList<>();
        choiceBoxs.add(statusAdminMejaCB);
        String[] namaChoiceBoxs = {"Status"};
        if(iValidation.isTextFieldInputValid(textFields, namaTextFields, choiceBoxs, namaChoiceBoxs, primaryStage)){
            MejaProperty selectedMejaProp = adminMejaTable.getSelectionModel().getSelectedItem();
            if(null != selectedMejaProp){
                Meja meja = new Meja();
                meja.setId(selectedMejaProp.getId());
                meja.setNomor(nomorAdminMejaTF.getText());
                switch(statusAdminMejaCB.getValue()){
                    case CommonConstant.ADMIN_MEJA_ACTIVE:
                        meja.setStatus(CommonConstant.MEJA_TERSEDIA);
                        break;
                    case CommonConstant.ADMIN_MEJA_NON_ACTIVE:
                        meja.setStatus(CommonConstant.MEJA_DISABLE);
                        break;
                    case CommonConstant.ADMIN_MEJA_HIDDEN:
                        meja.setStatus(CommonConstant.MEJA_INVISIBLE);
                        break;
                    default:
                        break;
                }
                if(iMeja.updateAdminEdit(meja, primaryStage)){
                    selectedMejaProp.setNomor(nomorAdminMejaTF.getText());
                    selectedMejaProp.setStatus(statusAdminMejaCB.getValue());
                }
                
            }else{
                //Nothing selected.
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.initOwner(primaryStage);
                alertWarning.setTitle("No Selection");
                alertWarning.setHeaderText("Tidak ada meja yang dipilih");
                alertWarning.setContentText("Silahkan pilih meja terlebih dahulu");

                alertWarning.showAndWait();
            }
        }
        
    }
    
    @FXML
    private void adminMejaBatalBtn(ActionEvent event) {
        adminMejaSimpanBtn.setDisable(true);
        adminMejaTable.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void direktoriBackupBtn(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Pilih Direktori/Folder");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        
        File dirTarget = directoryChooser.showDialog(primaryStage);
        if(dirTarget != null){
            backupDirectory.setText(dirTarget.getAbsolutePath());
            directoryChooser.setInitialDirectory(new File(dirTarget.getAbsolutePath()));
        }
    }

    private void setBackupAndRestore() {
        backupDirectory.setText(System.getProperty("user.home"));
        restoreDirectory.setText(System.getProperty("user.home"));
    }
    
    @FXML
    private void backupAction(ActionEvent actionEvent) {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Konfirmasi");
        alertConfirm.setHeaderText("Anda Yakin?");

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get() == ButtonType.OK){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedDate = LocalDate.now().format(formatter);
            String backupDir = backupDirectory.getText() + File.separator + "backup_" + formattedDate;
            
            JDBCConnection.databaseBackup(backupDir);
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setHeaderText("Selesai");
            alert.setContentText("Proses backup telah selesai. Silahkan lanjutkan!");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void direktoriRestoreBtn(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Pilih Direktori/Folder");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dirTarget = directoryChooser.showDialog(primaryStage);
        if(dirTarget != null){
            restoreDirectory.setText(dirTarget.getAbsolutePath());
            directoryChooser.setInitialDirectory(new File(dirTarget.getAbsolutePath()));
        }
    }
    
    @FXML
    private void restoreAction(ActionEvent actionEvent) {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Konfirmasi");
        alertConfirm.setHeaderText("Anda Yakin?");

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get() == ButtonType.OK){
            String backupDir = restoreDirectory.getText();
            JDBCConnection.databaseRestore(backupDir);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setHeaderText("Selesai");
            alert.setContentText("Proses restore telah selesai. Silahkan restart aplikasi ini!");
            alert.showAndWait();
            Platform.exit();
        }
    }
    
    @FXML
    private void direktoriLaporanBtn(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Pilih Direktori/Folder");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dirTarget = directoryChooser.showDialog(primaryStage);
        if(dirTarget != null){
            laporanDirectory.setText(dirTarget.getAbsolutePath());
            directoryChooser.setInitialDirectory(new File(dirTarget.getAbsolutePath()));
        }
    }
    
    @FXML
    private void downloadLaporan(ActionEvent event) {
        try {
            if(jenisLaporanCB.getValue().equalsIgnoreCase("Keuangan")){
                List<Laporan> dataList = iTransaksi.getLaporan(transaksiDariDate.getValue(), transaksiSampaiDate.getValue());
                if(null != dataList && dataList.size() > 0) {
                    JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
                    Integer totPemasukan = 0;
                    Integer totPengeluaran = 0;
                    for(int i=0; i<dataList.size(); i++){
                        totPemasukan += dataList.get(i).getPemasukan();
                        totPengeluaran += dataList.get(i).getPengeluaran();
                    }
                    Date tglDari = Date.from(transaksiDariDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date tglSampai = Date.from(transaksiSampaiDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    HashMap<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("cafe", CommonConstant.KEDAI_NAMA);
                    parameters.put("tglDari", tglDari);
                    parameters.put("tglSampai", tglSampai);
                    parameters.put("totalPemasukan", numberFormat.format(totPemasukan));
                    parameters.put("totalPengeluaran", numberFormat.format(totPengeluaran));
                    parameters.put("hasil", numberFormat.format(totPemasukan-totPengeluaran));
                    InputStream reportStream = this.getClass().getResourceAsStream("reports/laporan.jasper");
                    JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanColDataSource);
                    if(print != null) {
                        DateFormat df = new SimpleDateFormat("yyyyMMdd");
                        String printOutDir= laporanDirectory.getText() + File.separator + "Laporan_Keuangan_" + df.format(tglDari) + "-" + df.format(tglSampai);
                        JasperExportManager.exportReportToPdfFile(print, printOutDir + ".pdf");
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Informasi");
                        alert.setHeaderText("Selesai");
                        alert.setContentText("Laporan telah dibuat");
                        alert.showAndWait();
                    }
                }else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.initOwner(primaryStage);
                    alert.setTitle("Salah!");
                    alert.setHeaderText("Tidak ada transaksi pada tanggal yang dipilih");
                    alert.setContentText("Silahkan pilih tanggal yang lain");
                    alert.showAndWait();
                }
            }else{
                List<Laporan> dataList = iTransaksi.getLaporanPenjualan(transaksiDariDate.getValue(), transaksiSampaiDate.getValue());
                if(null != dataList && dataList.size() > 0) {
                    JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
                    Date tglDari = Date.from(transaksiDariDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date tglSampai = Date.from(transaksiSampaiDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    HashMap<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("cafe", CommonConstant.KEDAI_NAMA);
                    parameters.put("tglDari", tglDari);
                    parameters.put("tglSampai", tglSampai);
                    InputStream reportStream = this.getClass().getResourceAsStream("reports/lap_penjualan.jasper");
                    JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanColDataSource);
                    if(print != null) {
                        DateFormat df = new SimpleDateFormat("yyyyMMdd");
                        String printOutDir= laporanDirectory.getText() + File.separator + "Laporan_Penjualan_" + df.format(tglDari) + "-" + df.format(tglSampai);
                        JasperExportManager.exportReportToPdfFile(print, printOutDir + ".pdf");
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Informasi");
                        alert.setHeaderText("Selesai");
                        alert.setContentText("Laporan Penjualan telah dibuat");
                        alert.showAndWait();
                    }
                }else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.initOwner(primaryStage);
                    alert.setTitle("Salah!");
                    alert.setHeaderText("Tidak ada transaksi pada tanggal yang dipilih");
                    alert.setContentText("Silahkan pilih tanggal yang lain");
                    alert.showAndWait();
                }
            }
        } catch (JRException e) {
            LOGGER.error("failed to fill report to file", e);
        }
    }
}
