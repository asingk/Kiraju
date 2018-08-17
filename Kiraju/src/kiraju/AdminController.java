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
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import kiraju.implement.AbsensiModel;
import kiraju.implement.DiskonModel;
import kiraju.implement.GeneralModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import kiraju.model.Laporan;
import kiraju.implement.JenisMenuModel;
import kiraju.implement.MejaModel;
import kiraju.implement.MenuItemModel;
import kiraju.implement.MenuModel;
import kiraju.implement.MetodePembayaranModel;
import kiraju.implement.PajakModel;
import kiraju.implement.PelangganModel;
import kiraju.implement.PengeluaranModel;
import kiraju.implement.PesanModel;
import kiraju.implement.PosisiModel;
import kiraju.implement.StokOpnameItemModel;
import kiraju.implement.StokOpnameModel;
import kiraju.implement.TransaksiModel;
import kiraju.implement.UsersModel;
import kiraju.interfaces.IAbsensi;
import kiraju.interfaces.IDiskon;
import kiraju.interfaces.IGeneral;
import kiraju.interfaces.IJenisMenu;
import kiraju.interfaces.IMeja;
import kiraju.interfaces.IMenu;
import kiraju.interfaces.IMenuItem;
import kiraju.interfaces.IMetodePembayaran;
import kiraju.interfaces.IPajak;
import kiraju.interfaces.IPelanggan;
import kiraju.interfaces.IPengeluaran;
import kiraju.interfaces.IPesan;
import kiraju.interfaces.IPosisi;
import kiraju.interfaces.IStokOpname;
import kiraju.interfaces.IStokOpnameItem;
import kiraju.interfaces.ITransaksi;
import kiraju.interfaces.IUsers;
import kiraju.interfaces.IValidation;
import kiraju.model.Diskon;
import kiraju.model.General;
import kiraju.model.JenisMenu;
import kiraju.model.Meja;
import kiraju.model.Menu;
import kiraju.model.MenuItem;
import kiraju.model.MetodePembayaran;
import kiraju.model.Pajak;
import kiraju.model.Pelanggan;
import kiraju.model.Pengeluaran;
import kiraju.model.Pesan;
import kiraju.model.Posisi;
import kiraju.model.StokOpname;
import kiraju.model.StokOpnameItem;
import kiraju.model.Transaksi;
import kiraju.model.Users;
import kiraju.property.AbsensiProperty;
import kiraju.property.DiskonPajakProperty;
import kiraju.property.JenisMenuProperty;
import kiraju.property.MejaProperty;
import kiraju.property.MenuItemProperty;
import kiraju.property.MenuProperty;
import kiraju.property.MetodePembayaranProperty;
import kiraju.property.PelangganProperty;
import kiraju.property.PengeluaranProperty;
import kiraju.property.PesanProperty;
import kiraju.property.PosisiProperty;
import kiraju.property.StokOpnameItemProperty;
import kiraju.property.StokOpnameProperty;
import kiraju.property.TransaksiProperty;
import kiraju.property.UsersProperty;
import kiraju.util.Choice;
import kiraju.util.CommonConstant;
import kiraju.util.JDBCConnection;
import kiraju.util.Validation;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

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
    private TableView<MenuProperty> pesanMenuTable;
    @FXML
    private TableView<MenuItemProperty> pesanMenuItemTable;
    @FXML
    private TableView<PesanProperty> pesanOrderedTable;
    @FXML
    private TableView<MenuItemProperty> menuItemTable;
    @FXML
    private TableView<PelangganProperty> daftarPelangganTable;
    @FXML
    private TableView<TransaksiProperty> riwayatPelangganTable;
    @FXML
    private TableView<AbsensiProperty> absensiTable;
    @FXML
    private TableView<StokOpnameProperty> stokOpnameTable;
    @FXML
    private TableView<StokOpnameItemProperty> stokOpnameItemTable;
    @FXML
    private TableView<DiskonPajakProperty> diskonTable;
    @FXML
    private TableView<DiskonPajakProperty> pajakTable;
    @FXML
    private TableView<PesanProperty> pemasukanDetailTable;
    @FXML
    private TableView<MetodePembayaranProperty> metodePembayaranTable;
    @FXML
    private TableView<TransaksiProperty> pemasukanMetodePembayaranTable;
    @FXML
    private TableView<MenuProperty> mejaMenuTable;
    @FXML
    private TableView<MenuItemProperty> mejaMenuItemTable;
    @FXML
    private TableColumn<MenuProperty, MenuProperty> noColumn;
//    @FXML
//    private TableColumn<MenuProperty, String> kodeColumn;
    @FXML
    private TableColumn<MenuProperty, String> namaColumn;
//    @FXML
//    private TableColumn<MenuProperty, String> hargaColumn;
    @FXML
    private TableColumn<MenuProperty, String> statusColumn;
    @FXML
    private TableColumn<JenisMenuProperty, JenisMenuProperty> noColumnJenis;
//    @FXML
//    private TableColumn<JenisMenuProperty, String> kodeColumnJenis;
    @FXML
    private TableColumn<JenisMenuProperty, String> namaColumnJenis;
    @FXML
    private TableColumn<JenisMenuProperty, String> statusColumnJenis;
    @FXML
    private TableColumn<PesanProperty, String> menuNamaColumnMeja;
    @FXML
    private TableColumn<PesanProperty, String> menuItemNamaColumnMeja;
    @FXML
    private TableColumn<PesanProperty, Integer> jumlahColumnMeja;
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
//    @FXML
//    private TableColumn<PesanProperty, String> kodeColumnBungkus;
    @FXML
    private TableColumn<PesanProperty, String> menuColumnBungkus;
    @FXML
    private TableColumn<PesanProperty, String> itemColumnBungkus;
    @FXML
    private TableColumn<PesanProperty, Integer> jumlahColumnBungkus;
    @FXML
    private TableColumn<TransaksiProperty, TransaksiProperty> noColumnPemasukan;
    @FXML
    private TableColumn<TransaksiProperty, String> waktuColumnPemasukan;
//    @FXML
//    private TableColumn<TransaksiProperty, Integer> mejaColumnPemasukan;
//    @FXML
//    private TableColumn<TransaksiProperty, String> pelangganColumnPemasukan;
    @FXML
    private TableColumn<TransaksiProperty, String> statusColumnPemasukan;
    @FXML
    private TableColumn<TransaksiProperty, String> totalColumnPemasukan;
    @FXML
    private TableColumn<TransaksiProperty, String> metodePembayaranColumnPemasukan;
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
    private TableColumn<MenuProperty, String> namaColumnPesan;
//    @FXML
//    private TableColumn<MenuProperty, String> kodeColumnPesan;
    @FXML
    private TableColumn<MenuProperty, MenuProperty> noColumnPesan;
    @FXML
    private TableColumn<MenuItemProperty, MenuItemProperty> noColumnMenuItemPesan;
    @FXML
    private TableColumn<MenuItemProperty, String> namaColumnMenuItemPesan;
//    @FXML
//    private TableColumn<PesanProperty, String> kodeColumnOrderedPesan;
    @FXML
    private TableColumn<PesanProperty, String> menuColumnOrderedPesan;
    @FXML
    private TableColumn<PesanProperty, String> itemColumnOrderedPesan;
    @FXML
    private TableColumn<PesanProperty, Integer> jumlahColumnOrderedPesan;
    @FXML
    private TableColumn<MenuItemProperty, String> kodeColumnMenuItem;
    @FXML
    private TableColumn<MenuItemProperty, String> namaColumnMenuItem;
    @FXML
    private TableColumn<MenuItemProperty, String> hargaColumnMenuItem;
    @FXML
    private TableColumn<MenuItemProperty, String> stokColumnMenuItem;
//    @FXML
//    private TableColumn<PelangganProperty, PelangganProperty> noColumnPelanggan;
    @FXML
    private TableColumn<PelangganProperty, String> idColumnPelanggan;
    @FXML
    private TableColumn<PelangganProperty, String> namaColumnPelanggan;
    @FXML
    private TableColumn<PelangganProperty, String> statusColumnPelanggan;
    @FXML
    private TableColumn<PelangganProperty, String> tglColumnPelanggan;
    @FXML
    private TableColumn<TransaksiProperty, TransaksiProperty> noColumnRiwayatPelanggan;
    @FXML
    private TableColumn<TransaksiProperty, String> tglColumnRiwayatPelanggan;
    @FXML
    private TableColumn<TransaksiProperty, String> subtotalColumnRiwayatPelanggan;
    @FXML
    private TableColumn<AbsensiProperty, String> namaColumnAbsensi;
    @FXML
    private TableColumn<AbsensiProperty, String> masukColumnAbsensi;
    @FXML
    private TableColumn<AbsensiProperty, String> keluarColumnAbsensi;
    @FXML
    private TableColumn<AbsensiProperty, String> totalColumnAbsensi;
    @FXML
    private TableColumn<StokOpnameProperty, StokOpnameProperty> nomorColumnStokOpname;
    @FXML
    private TableColumn<StokOpnameProperty, String> tanggalColumnStokOpname;
    @FXML
    private TableColumn<StokOpnameProperty, String> namaColumnStokOpname;
    @FXML
    private TableColumn<StokOpnameProperty, String> statusColumnStokOpname;
    @FXML
    private TableColumn<StokOpnameProperty, String> userNamaColumnStokOpname;
    @FXML
    private TableColumn<StokOpnameItemProperty, String> kodeColumnStokOpnameItem;
    @FXML
    private TableColumn<StokOpnameItemProperty, String> menuColumnStokOpnameItem;
    @FXML
    private TableColumn<StokOpnameItemProperty, String> itemColumnStokOpnameItem;
    @FXML
    private TableColumn<StokOpnameItemProperty, Integer> stokColumnStokOpnameItem;
    @FXML
    private TableColumn<StokOpnameItemProperty, Integer> stokTersediaColumnStokOpnameItem;
    @FXML
    private TableColumn<StokOpnameItemProperty, Integer> selisihColumnStokOpnameItem;
    @FXML
    private TableColumn<DiskonPajakProperty, DiskonPajakProperty> noColumnDiskon;
    @FXML
    private TableColumn<DiskonPajakProperty, String> namaColumnDiskon;
    @FXML
    private TableColumn<DiskonPajakProperty, String> tipeColumnDiskon;
    @FXML
    private TableColumn<DiskonPajakProperty, String> bilanganColumnDiskon;
    @FXML
    private TableColumn<DiskonPajakProperty, String> aktifColumnDiskon;
    @FXML
    private TableColumn<DiskonPajakProperty, DiskonPajakProperty> noColumnPajak;
    @FXML
    private TableColumn<DiskonPajakProperty, String> namaColumnPajak;
    @FXML
    private TableColumn<DiskonPajakProperty, String> tipeColumnPajak;
    @FXML
    private TableColumn<DiskonPajakProperty, String> bilanganColumnPajak;
    @FXML
    private TableColumn<DiskonPajakProperty, String> aktifColumnPajak;
//    @FXML
//    private TableColumn<PesanProperty, String> pemasukanDetailKodeClmn;
    @FXML
    private TableColumn<PesanProperty, String> pemasukanDetailMenuClmn;
    @FXML
    private TableColumn<PesanProperty, String> pemasukanDetailItemClmn;
    @FXML
    private TableColumn<PesanProperty, Integer> pemasukanDetailJumlahClmn;
    @FXML
    private TableColumn<MetodePembayaranProperty, MetodePembayaranProperty> metodePembayaranNoClmn;
    @FXML
    private TableColumn<MetodePembayaranProperty, String> metodePembayaranNamaClmn;
    @FXML
    private TableColumn<MetodePembayaranProperty, String> metodePembayaranStatusClmn;
    @FXML
    private TableColumn<TransaksiProperty, String> pemasukanMetodePembayaranClm;
    @FXML
    private TableColumn<TransaksiProperty, String> pemasukanMetodePembayaranTotalClm;
    @FXML
    private TableColumn<MenuProperty, MenuProperty> noMenuColumnMeja;
    @FXML
    private TableColumn<MenuProperty, String> namaMenuColumnMeja;
    @FXML
    private TableColumn<MenuItemProperty, MenuItemProperty> noColumnMenuItemMeja;
    @FXML
    private TableColumn<MenuItemProperty, String> namaColumnMenuItemMeja;
    @FXML
    private TextField namaTextField;
//    @FXML
//    private TextField kodeTextField;
    @FXML
    private TextField searchTextField;
    @FXML
    private TextField namaJenisTextField;
//    @FXML
//    private TextField kodeJenisTextField;
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
//    @FXML
//    private TextField searchPesanTF;
    @FXML
    private TextField stokTambahTF;
    @FXML
    private TextField menuItemKodeTF;
    @FXML
    private TextField menuItemNamaTF;
    @FXML
    private TextField menuItemModalTF;
    @FXML
    private TextField menuItemUntungTF;
    @FXML
    private TextField menuItemTambahanTF;
    @FXML
    private TextField menuItemHargaJualTF;
    @FXML
    private TextField cariPelangganTF;
    @FXML
    private TextField detilPelangganIdTF;
    @FXML
    private TextField detilPelangganNamaTF;
//    @FXML
//    private TextField detilPelangganAlamatTF;
    @FXML
    private TextField detilPelangganTelpTF;
    @FXML
    private TextField namaDiskonTF;
    @FXML
    private TextField bilanganDiskonTF;
    @FXML
    private TextField namaPajakTF;
    @FXML
    private TextField bilanganPajakTF;
    @FXML
    private TextField metodePembayaranNamaTF;
    @FXML
    private TextField stokOpnameNamaTF;
    @FXML
    private TextField stokOpnameItemTersediaTF;
    @FXML
    private TextField usersIdTF;
    @FXML
    private TextField zakatModalTF;
    @FXML
    private TextField zakatLabaTF;
    @FXML
    private TextField zakatPiutangTF;
    @FXML
    private TextField zakatRugiTF;
    @FXML
    private TextField zakatHutangTF;
    @FXML
    private TextField zakatHargaEmasTF;
    @FXML
    private TextField zakatBulanModalTF;
    @FXML
    private TextField zakatBulanLabaTF;
    @FXML
    private TextField zakatBulanPiutangTF;
    @FXML
    private TextField zakatBulanRugiTF;
    @FXML
    private TextField zakatBulanHutangTF;
    @FXML
    private ChoiceBox<Choice> jenisBox;
    @FXML
    private ChoiceBox<Choice> jenisMenuBox;
    @FXML
    private ChoiceBox<String> posisiUsers;
    @FXML
    private ChoiceBox<String> pemasukanChoiceBox;
    @FXML
    private ChoiceBox<String> grafikBulanCB;
    @FXML
    private ChoiceBox<Integer> grafikTahunCB;
    @FXML
    private ChoiceBox<String> statusAdminMejaCB;
    @FXML
    private ChoiceBox<String> jenisLaporanCB;
    @FXML
    private ChoiceBox<Choice> jenisBoxPesan;
    @FXML
    private ChoiceBox<String> menuItemUntungCB;
    @FXML
    private ChoiceBox<String> menuItemTambahanCB;
    @FXML
    private ChoiceBox<String> stokOpnameBulanCB;
    @FXML
    private ChoiceBox<Integer> stokOpnameTahunCB;
    @FXML
    private ChoiceBox<String> diskonTipeCB;
    @FXML
    private ChoiceBox<String> pajakTipeCB;
    @FXML
    private ChoiceBox<Choice> pesanDiskonCB;
    @FXML
    private ChoiceBox<Choice> pesanPajakCB;
    @FXML
    private ChoiceBox<Choice> jenisMejaCB;
    @FXML
    private ChoiceBox<Choice> mejaDiskonCB;
    @FXML
    private ChoiceBox<Choice> mejaPajakCB;
    @FXML
    private ChoiceBox<String> pengaturanPrinterCB;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab pesanTab;
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
    private Tab diskonPajakTab;
    @FXML
    private Tab stokOpnameTab;
    @FXML
    private Tab pelangganTab;
    @FXML
    private Tab metodePembayaranTab;
    @FXML
    private Tab absensiTab;
    @FXML
    private Tab zakatTab;
    @FXML
    private Tab pengaturanTab;
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
//    @FXML
//    private Text totalSemua;
    @FXML
    private Text totalPengeluaran;
//    @FXML
//    private Text backupDirectory;
//    @FXML
//    private Text restoreDirectory;
//    @FXML
//    private Text laporanDirectory;
    @FXML
    private Text totalPesanOrdered;
    @FXML
    private Text pemasukanDetailDiskon;
    @FXML
    private Text pemasukanDetailPajak;
    @FXML
    private Text pemasukanDetailTotal;
//    @FXML
//    private Text kodeJenisMenu;
//    @FXML
//    private Text kodeMenu;
//    @FXML
//    private Text menuItemHargaJual;
    @FXML
    private Text riwayatPelangganTotal;
    @FXML
    private Text bungkusDetailDiskonNama;
    @FXML
    private Text bungkusDetailPajakNama;
    @FXML
    private Text zakatTglAwalHijrah;
    @FXML
    private Text zakatTglAkhirHijrah;
    @FXML
    private Text zakatTglAkhirMasehi;
    @FXML
    private Text zakatLabaRugi;
    @FXML
    private Text zakatNishab;
    @FXML
    private Text zakatTotal;
    @FXML
    private Text zakatBayarZAkat;
    @FXML
    private Text zakatBulanNama;
    @FXML
    private Text zakatBulanLabaRugi;
    @FXML
    private Text zakatBulanTotal;
    @FXML
    private Text caption;
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
    @FXML
    private Button adminMejaSimpanBtn;
    @FXML
    private Button mejaPindahBtn;
    @FXML
    private Button mejaBayarBtn;
    @FXML
    private Button mejaBatalBtn;
    @FXML
    private Button mejaUbahBtn;
    @FXML
    private Button mejaHapusBtn;
    @FXML
    private Button menuItemSimpanBtn;
    @FXML
    private Button menuItemBatalBtn;
    @FXML
    private Button stokOpnameHapusBtn;
    @FXML
    private Button stokOpnameSimpanBtn;
    @FXML
    private Button stokOpnameSelesaiBtn;
    @FXML
    private Button pesanUbahBtn;
    @FXML
    private Button pesanHapusBtn;
    @FXML
    private Button pesanBatalBtn;
    @FXML
    private Button pesanSimpanBtn;
    @FXML
    private Button pesanBayarBtn;
    @FXML
    private Button bungkusUbahBtn;
    @FXML
    private Button bungkusBatalBtn;
    @FXML
    private Button bungkusBayarBtn;
    @FXML
    private Button pengeluaranSimpanBtn;
    @FXML
    private Button pengeluaranBatalBtn;
    @FXML
    private Button pengeluaranHapusBtn;
    @FXML
    private Button menuSimpanBtn;
    @FXML
    private Button menuBatalBtn;
    @FXML
    private Button jenisMenuSimpanBtn;
    @FXML
    private Button jenisMenuBatalBtn;
    @FXML
    private Button diskonSimpanBtn;
    @FXML
    private Button diskonBatalBtn;
    @FXML
    private Button pajakSimpanBtn;
    @FXML
    private Button pajakBatalBtn;
    @FXML
    private Button metodePembayaranSimpanBtn;
    @FXML
    private Button metodePembayaranBatalBtn;
    @FXML
    private Button pelangganSimpanBtn;
    @FXML
    private Button pelangganBatalBtn;
    @FXML
    private Button userSimpanBtn;
    @FXML
    private Button userBatalBtn;
    @FXML
    private Button stokOpnameItemHapusBtn;
    @FXML
    private Button stokOpnameItemSimpanBtn;
    @FXML
    private Button stokOpnameItemBatalBtn;
    @FXML
    private Button pesanItemBtn;
    @FXML
    private Button pesanBtn;
    @FXML
    private Button mejaCetakBtn;
    @FXML
    private Button mejaItemBtn;
    @FXML
    private Button mejaPesanBtn;
    @FXML
    private DatePicker pemasukanDate;
    @FXML
    private DatePicker pengeluaranDate;
    @FXML
    private DatePicker transaksiDariDate;
    @FXML
    private DatePicker transaksiSampaiDate;
    @FXML
    private DatePicker absensiDate;
    @FXML
    private DatePicker zakatDate;
    @FXML
    private LineChart<String, Number> chartBulan;
    @FXML
    private LineChart<String, Number> chartTahun;
//    @FXML
//    private LineChart<String, Number> chartMenuBulan;
    @FXML
    private PieChart pelangganTop10;
    @FXML
    private CategoryAxis xAxisBulanan;
    @FXML
    private CategoryAxis xAxisTahunan;
//    @FXML
//    private CategoryAxis xAxisMenuBulanan;
    @FXML
    private CheckBox stokCheckBox;
    @FXML
    private CheckBox diskonCheckBox;
    @FXML
    private CheckBox pajakCheckBox;
    @FXML
    private CheckBox metodePembayaranCheckBox;
    @FXML
    private CheckBox pelangganCheckBox;
    @FXML
    private CheckBox usersStatusCheckBox;
    @FXML
    private CheckBox jenisMenuCheckBox;
    @FXML
    private CheckBox menuStatusCheckBox;
    @FXML
    private CheckBox pengaturanModeCafeCheckBox;
    @FXML
    private ComboBox<String> searchmenuItemComboBox;
    @FXML
    private ComboBox<String> stokOpnameItemKodeComboBox;
    @FXML
    private ComboBox<String> mejaSearchmenuItemComboBox;
    @FXML
    private TextArea detilPelangganAlamat;
    @FXML
    private TextArea stokOpnameItemKet;
    @FXML
    private VBox pesanMenuBox;
    @FXML
    private VBox pesanMenuItemBox;
    @FXML
    private VBox mejaMenuBox;
    @FXML
    private VBox mejaMenuItemBox;
    @FXML
    private ImageView logoKiraju;
    @FXML
    private GridPane mejaViewPane;
    @FXML
    private BorderPane mejaDetailPane;
    
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
//    private ObservableList<MenuProperty> pesanMenuPropObsList;
//    private ObservableList<PesanProperty> pesanMenuItemObsList;
    private ObservableList<PesanProperty> pesanMenuItemOrderedObsList = FXCollections.observableArrayList();
    private ObservableList<MenuItemProperty> menuItemPropObsList;
    private ObservableList<PelangganProperty> daftarPelangganPropObsList;
    private ObservableList<TransaksiProperty> riwayatPelangganPropObsList = FXCollections.observableArrayList();
//    private Map<String,Integer> map1 = new HashMap<>();
    private Map<String,String> map3 = new HashMap<>();
//    private ObservableMap<String ,Integer> choicesMap = FXCollections.observableMap(map1);
    private ObservableMap<String, String> usersChoiceObsMap = FXCollections.observableMap(map3);
    private final IMenu iMenu = new MenuModel();
    private final IJenisMenu iJenis = new JenisMenuModel();
    private final IMeja iMeja = new MejaModel();
    private final ITransaksi iTransaksi = new TransaksiModel();
    private final IUsers iUsers = new UsersModel();
    private final IPosisi iPosisi = new PosisiModel();
    private final IPesan iPesan = new PesanModel();
    private final IPengeluaran iPengeluaran = new PengeluaranModel();
    private final IValidation iValidation = new Validation();
    private final IMenuItem iMenuItem = new MenuItemModel();
    private final IPelanggan iPelanggan = new PelangganModel();
    private final IStokOpname iStokOpname = new StokOpnameModel();
    private final IStokOpnameItem iStokOpnameItem = new StokOpnameItemModel();
    private final IDiskon iDiskon = new DiskonModel();
    private final IPajak iPajak = new PajakModel();
    private final IMetodePembayaran iMetodePembayaran = new MetodePembayaranModel();
    private final IAbsensi iAbsensi = new AbsensiModel();
    private final IGeneral iGeneral = new GeneralModel();
    private short mejaActive = 0;
    private TransaksiProperty selectedNamaBungkus;
    private Integer totalHargaMeja = 0;
    private Integer totalModalMeja = 0;
//    private Integer totalHargaBungkus = 0;
    private Integer totalHargaPesan = 0;
    private Integer totalModalPesan = 0;
//    private int bulanBaruMenu = 0;
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));
    private final String datePattern = "dd/MM/yyyy";
    private int transaksiId = 0;
    private int transaksiIdMeja = 0;
    private Integer hargaGrandTotal = 0;
    private Integer pesanGlobalDiskon = 0;
    private Integer pesanGlobalPajak = 0;
    private Integer mejaGrandTotal = 0;
    private Integer mejaGlobalDiskon = 0;
    private Integer mejaGlobalPajak = 0;
    private int hartaTot = 0;
    private long zakatTot = 0;
    private General general;
    
    private final StringBuffer barcode = new StringBuffer();
    private long lastEventTimeStamp = 0L;
    
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLoginUser(Users loginUser) {
        general = iGeneral.getGeneral();
        
        List superuser;
        List admin;
        List kasir;
        if(general.getModeCafe()){
            superuser = Arrays.asList(pesanTab, bungkusTab, mejaTab, pemasukanTab, pengeluaranTab, absensiTab, menuTab, jenisMenutab, diskonPajakTab, stokOpnameTab, pelangganTab, metodePembayaranTab, grafikTab, adminMejaTab, pengaturanTab, backupRestoreTab, zakatTab, usersTab);
            admin = Arrays.asList(pesanTab, bungkusTab, mejaTab, pemasukanTab, pengeluaranTab, absensiTab, menuTab, jenisMenutab, diskonPajakTab, stokOpnameTab, pelangganTab, metodePembayaranTab, grafikTab, adminMejaTab, pengaturanTab, backupRestoreTab, zakatTab);
            kasir = Arrays.asList(pesanTab, bungkusTab, mejaTab, pemasukanTab);
        }else{
            superuser = Arrays.asList(pesanTab, bungkusTab, pemasukanTab, pengeluaranTab, absensiTab, menuTab, jenisMenutab, diskonPajakTab, stokOpnameTab, pelangganTab, metodePembayaranTab, grafikTab, pengaturanTab, backupRestoreTab, zakatTab, usersTab);
            admin = Arrays.asList(pesanTab, bungkusTab, pemasukanTab, pengeluaranTab, absensiTab, menuTab, jenisMenutab, diskonPajakTab, stokOpnameTab, pelangganTab, metodePembayaranTab, grafikTab, pengaturanTab, backupRestoreTab, zakatTab);
            kasir = Arrays.asList(pesanTab, bungkusTab, pemasukanTab);
        }
        
        this.loginUser = loginUser;
        pemasukanObsList.clear();
        
        tabPane.getTabs().removeAll(pesanTab, mejaTab, bungkusTab, pemasukanTab, pengeluaranTab, absensiTab, menuTab, jenisMenutab, diskonPajakTab ,adminMejaTab, stokOpnameTab, metodePembayaranTab, pelangganTab, grafikTab, adminMejaTab, pengaturanTab, backupRestoreTab, zakatTab, usersTab);
        pemasukanChoiceBox.setDisable(false);
        usersTab.setDisable(false);
        switch (loginUser.getPosisiId().getId()) {
            case CommonConstant.USER_HIDDEN:
                tabPane.getTabs().add(usersTab);
                tabPane.getTabs().remove(aboutTab);
                break;
            case CommonConstant.USER_SUPER:
                tabPane.getTabs().addAll(superuser);
                tabPane.getTabs().remove(aboutTab);
                break;
            case CommonConstant.USER_ADMIN:
                tabPane.getTabs().addAll(admin);
                tabPane.getTabs().remove(aboutTab);
                break;
            case CommonConstant.USER_KASIR:
                tabPane.getTabs().addAll(kasir);
                pemasukanChoiceBox.setDisable(true);
                tabPane.getTabs().remove(aboutTab);
                break;
            default:
                if(!JDBCConnection.checkInstallDate() && !CommonConstant.ISPREMIUM){
                    tabPane.getTabs().addAll(superuser);
//                    tabPane.getTabs().remove(aboutTab);
                    usersTab.setDisable(true);
                    pesanTab.setDisable(true);
                    bungkusTab.setDisable(true);
                    pemasukanTab.setDisable(true);
                    pengeluaranTab.setDisable(true);
                    absensiTab.setDisable(true);
                    menuTab.setDisable(true);
                    jenisMenutab.setDisable(true);
                    diskonPajakTab.setDisable(true);
                    stokOpnameTab.setDisable(true);
                    metodePembayaranTab.setDisable(true);
                    pelangganTab.setDisable(true);
                    grafikTab.setDisable(true);
                    backupRestoreTab.setDisable(true);
                    zakatTab.setDisable(true);
                    pengaturanTab.setDisable(true);
                    mejaTab.setDisable(true);
                    adminMejaTab.setDisable(true);
                    
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
        TextField[] textFields = {namaTextField};
        String[] namaTextFields = {"Nama"};
        List<ChoiceBox<Choice>> choiceBoxs = new ArrayList<>();
        choiceBoxs.add(jenisMenuBox);
//        choiceBoxs.add(menuStatusCB);
        String[] namaChoiceBoxs = {"Jenis"};
        if(iValidation.isTextFieldInputValid(textFields, namaTextFields, choiceBoxs, namaChoiceBoxs, primaryStage)) {
            MenuProperty selectedMenuProp = menuTable.getSelectionModel().getSelectedItem();
            Menu menu = new Menu();
            menu.setNama(namaTextField.getText());
//            menu.setCode(kodeTextField.getText());
//            menu.setDeletedFlag(menuStatusCB.getValue().equalsIgnoreCase("Aktif") ? (short) 0 : (short) 1);
            menu.setStatus(menuStatusCheckBox.isSelected());
            JenisMenu jenisMenu = new JenisMenu();
            jenisMenu.setId(jenisMenuBox.getValue().getId());
            menu.setJenisMenuId(jenisMenu);
            if(null != selectedMenuProp){
                menu.setId(selectedMenuProp.getId());
                if(!iMenu.update(menu, primaryStage)) {
                    return;
                }
            }else{
                if(!iMenu.insert(menu, primaryStage)) {
                    return;
                }
            }
            handleCancelAction(event);
            setMenuTable(jenisBox.getValue().getId());
        }
    }
    
    @FXML
    private void handleCancelAction(ActionEvent event){
        menuTable.getSelectionModel().clearSelection();
        namaTextField.setText("");
//        kodeTextField.setText("");
        jenisMenuBox.setValue(null);
//        menuStatusCB.setValue(null);
        menuStatusCheckBox.setSelected(true);
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setPesan();
        setMeja();
        setBungkus();
        setDaftarMenu();
        setJenisMenu();
        adminMeja();
        setUsers();
        tabActive();
        
        setLoginUser(new Users(String.valueOf(CommonConstant.USER_NONE), "Tanpa User", new Posisi(CommonConstant.USER_NONE)));
        setPemasukan();
        displayChart();
        setPengeluaran();
//        setBackupAndRestore();
        setPelanggan();
        setStokOpname();
        setDiskonPajak();
        setMetodePembayaran();
        setAbsensi();
        logoKiraju.setImage(new Image(this.getClass().getResource("img/kiraju-edited2.png").toString()));
        kalkulatorZakat();
        pengaturan();
    }    
    
    private void displayEdit(MenuProperty menuProp){
        menuItemUntungCB.getSelectionModel().selectFirst();
        menuItemTambahanCB.getSelectionModel().selectFirst();
        if(null != menuProp){
            namaTextField.setText(menuProp.getNama());
//            kodeJenisMenu.setText(menuProp.getJenisCode());
//            kodeTextField.setText(menuProp.getCode());
            jenisMenuBox.setValue(new Choice(menuProp.getJenisId(), menuProp.getJenisNama()));
//            menuStatusCB.setValue(menuProp.deletedFlagProperty().get());
            menuStatusCheckBox.setSelected(menuProp.getStatus());
            
            //Menu Item
//            kodeMenu.setText(menuProp.getCode());
            menuItemPropObsList = iMenuItem.getPropertyByMenuId(menuProp.getId());
            menuItemKodeTF.setDisable(false);
            menuItemNamaTF.setDisable(false);
            menuItemModalTF.setDisable(false);
            menuItemUntungTF.setDisable(false);
            menuItemTambahanTF.setDisable(false);
            menuItemModalTF.setText("");
            menuItemUntungTF.setText("");
            menuItemTambahanTF.setText("");
            stokCheckBox.setDisable(false);
            menuItemBatalBtn.setDisable(false);
//            menuItemSimpanBtn.setDisable(false);
        }else{
            namaTextField.setText("");
//            kodeJenisMenu.setText("");
//            kodeTextField.setText("");
            jenisMenuBox.setValue(null);
//            menuStatusCB.setValue(null);
            menuStatusCheckBox.setSelected(true);
            
            //Menu Item
//            kodeMenu.setText("");
            menuItemPropObsList.clear();
            menuItemKodeTF.setDisable(true);
            menuItemNamaTF.setDisable(true);
            menuItemModalTF.setDisable(true);
            menuItemUntungTF.setDisable(true);
            menuItemTambahanTF.setDisable(true);
            stokCheckBox.setDisable(true);
            menuItemBatalBtn.setDisable(true);
//            menuItemSimpanBtn.setDisable(true);
        }  
        menuItemTable.setItems(menuItemPropObsList);
    }

//    private void getChoiceList() {
//        choicesMap.clear();
//        choicesString.clear();
//        choicesList.clear();
//        choicesMap.put("Semua", 0);
//        choicesString.add("Semua");
//        System.out.println("getChoiceList");
//        List resultList = iJenis.getAllActive();
//        if(null != resultList){
//            for(Object o : resultList){
//                JenisMenu jenisMenu = (JenisMenu) o;
//                choicesMap.put(jenisMenu.getNama(), jenisMenu.getId());
//                choicesString.add(jenisMenu.getNama());
//                choicesList.add(jenisMenu.getNama());
//            }
//        }
//    }

    private void displayFilteredData() {
        
        FilteredList<MenuProperty> filteredData = new FilteredList<>(menuPropObsList, p -> true);
        
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
//                            else if (person.getNama().toLowerCase().contains(lowerCaseFilter)) {
//                                return true; // Filter matches last name.
//                            }
                            return false; // Does not match.
			});
		});
        
        SortedList<MenuProperty> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(menuTable.comparatorProperty());
        menuTable.setItems(sortedData);
    }

    private void displayEditJenis(JenisMenuProperty jenisMenuProp) {
        if(null != jenisMenuProp){
            namaJenisTextField.setText(jenisMenuProp.getNama());
//            kodeJenisTextField.setText(jenisMenuProp.getKode());
//            jenisStatusCB.setValue(jenisMenuProp.deletedFlagProperty().get());
            jenisMenuCheckBox.setSelected(jenisMenuProp.getStatus());
        }
    }
    
    @FXML
    private void jenisSimpanAction(ActionEvent event){
//        TextField[] textFields = {namaJenisTextField, kodeJenisTextField};
//        String[] namaTextFields = {"Nama", "Kode"};
        TextField[] textFields = {namaJenisTextField};
        String[] namaTextFields = {"Nama"};
//        List<ChoiceBox<String>> choiceBoxs = new ArrayList<>();
//        choiceBoxs.add(jenisStatusCB);
//        String[] namaChoiceBoxs = {"Status"};
        if(iValidation.isTextFieldInputValid(textFields, namaTextFields, primaryStage)){
            JenisMenuProperty selectedJenisMenuProp = jenisMenuTable.getSelectionModel().getSelectedItem();
            JenisMenu jenisMenu = new JenisMenu();
            jenisMenu.setNama(namaJenisTextField.getText());
//            jenisMenu.setCode(kodeJenisTextField.getText());
//            jenisMenu.setDeletedFlag(jenisStatusCB.getValue().equalsIgnoreCase("Aktif") ? (short) 0 : (short) 1);
            jenisMenu.setStatus(jenisMenuCheckBox.isSelected());
            if(null != selectedJenisMenuProp){
                jenisMenu.setId(selectedJenisMenuProp.getId());
                iJenis.update(jenisMenu, primaryStage);
            }else{
                if(!iJenis.insert(jenisMenu, primaryStage)) {
                    return;
                }
            }
            jenisCancelAction(event);
            setJenisMenuTable();
        }
    }
    
    @FXML
    private void jenisCancelAction(ActionEvent event){
        jenisMenuTable.getSelectionModel().clearSelection();
        namaJenisTextField.setText("");
//        kodeJenisTextField.setText("");
//        jenisStatusCB.setValue(null);
        jenisMenuCheckBox.setSelected(true);
    }
    
    private void tabActive(){
        tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
            if(tabPane.getTabs().contains(menuTab) && newValue.equals(menuTab)){
//                choicesMap.clear();
//                choicesString.clear();
//                choicesList.clear();
//                getChoiceList();
//                System.out.println("menuTab");
                ObservableList<Choice> jenisMenu = iJenis.getAllActiveChoice();
                jenisBox.getItems().clear();
                jenisBox.getItems().add(new Choice(0, "Semua"));
                jenisBox.getItems().addAll(jenisMenu);
                jenisBox.getSelectionModel().selectFirst(); 
                jenisMenuBox.setItems(jenisMenu);
            }else if(tabPane.getTabs().contains(pemasukanTab) && newValue == pemasukanTab){
                pemasukanObsList.clear();
                getUsersChoiceList();
                pemasukanChoiceBox.getSelectionModel().select(loginUser.getNama());
            }else if(tabPane.getTabs().contains(mejaTab) && newValue.equals(mejaTab)){
                getMejaStatus();
            }else if(tabPane.getTabs().contains(grafikTab) && grafikTab.equals(newValue)){
                grafikBulanCB.getSelectionModel().clearSelection();
                grafikBulanCB.getSelectionModel().select(LocalDate.now().getMonthValue()-1);
//                grafikTahunCB.getSelectionModel().clearSelection();
                grafikTahunCB.setItems(iTransaksi.getYear());
                grafikTahunCB.getSelectionModel().selectFirst();
            }else if(tabPane.getTabs().contains(bungkusTab) && newValue == bungkusTab){
                bungkusObsList.clear();
                bungkusObsList = iTransaksi.getBungkus();
                bungkusNamaTable.setItems(bungkusObsList);
                displayOrderBungkus(null);
            }else if(tabPane.getTabs().contains(pesanTab) && newValue == pesanTab){
//                pesanDiskonCB.setItems(iDiskon.getAllActive());
//                pesanPajakCB.setItems(iPajak.getAllActive());
//                displayOrderPesan();
            }else if(tabPane.getTabs().contains(absensiTab) && newValue.equals(absensiTab)){
                setAbsensiValue();
            }
            if(tabPane.getTabs().contains(diskonPajakTab) && oldValue.equals(diskonPajakTab)) {
                pesanDiskonCB.getItems().clear();
                pesanDiskonCB.setItems(iDiskon.getAllActive());
                pesanPajakCB.getItems().clear();
                pesanPajakCB.setItems(iPajak.getAllActive());
//                displayOrderPesan();
                pesanDiskonCB.getSelectionModel().selectFirst();
                pesanPajakCB.getSelectionModel().selectFirst();
            }else if(tabPane.getTabs().contains(jenisMenutab) && oldValue.equals(jenisMenutab)) {
//                getChoiceList();
                setPesanCB();
                setMejaCB();
                
                //set menu CB
                jenisBox.getSelectionModel().selectFirst(); 
                
            }else if(tabPane.getTabs().contains(menuTab) && oldValue.equals(menuTab)) {
//                getChoiceList();
                setPesanCB();
                setMejaCB();
            }else if(tabPane.getTabs().contains(bungkusTab) && oldValue.equals(bungkusTab)) {
                displayOrderPesan();
            }
        });
    }
    
    private void displayOrder(short mejaId) {
        String nomorMeja = iMeja.getNomorById(mejaId);
        namaMeja.setText("Meja " + nomorMeja);
        mejaActive = mejaId;
        
        mejaMenuAction();
        mejaViewPane.setVisible(false);
        mejaDetailPane.setVisible(true);
        
        displayOrderedMeja();
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
    
//    @FXML
//    private void pesanBtn(ActionEvent actionEvent){
//        if(mejaActive > 0){
//            try {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(AdminController.class.getResource("Pesan.fxml"));
//                AnchorPane page = (AnchorPane) loader.load();
//
//                Stage dialogStage = new Stage();
//                dialogStage.setTitle("Pesan");
//                dialogStage.initModality(Modality.WINDOW_MODAL);
//                dialogStage.initOwner(primaryStage);
//
//                Scene scene = new Scene(page);
//                dialogStage.setScene(scene);
//
//                PesanController controller = loader.getController();
//                controller.setDialogStage(dialogStage);
////                if(menuMejaObsList != null && menuMejaObsList.size() > 0){
////                    controller.setTransaksiId(menuMejaObsList.get(0).getTransaksiId());
////                }else{
////                    controller.setTransaksiId(iTransaksi.insertByMeja(mejaActive, loginUser.getId()));
////                }
//
//                dialogStage.showAndWait();
//
////                if(controller.getPesanPropObsList() != null && controller.getPesanPropObsList().size() > 0){
//                    getMejaStatus();
//                    displayOrderedMeja();
////                }
//               
//            } catch (IOException ex) {
//                LOGGER.error("failed to load Pesan.fxml", ex);
//            }
//        }else{
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.initOwner(primaryStage);
//            alert.setTitle("No Selection");
//            alert.setHeaderText("Tidak ada meja yang dipilih");
//            alert.setContentText("Silahkan pilih meja terlebih dahulu");
//            
//            alert.showAndWait();
//        }
//    }

    private void setDaftarMenu() {
        
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
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        
        //set kategori choice box
        
        jenisBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Choice> observable, Choice oldValue, Choice newValue) -> {
            if(null != newValue){
                setMenuTable(newValue.getId());
            }
        });
//        ObservableList<Choice> jenisMenu = iJenis.getAllActiveChoice();
//        jenisBox.setItems(jenisMenu);
//        jenisBox.getSelectionModel().selectFirst(); 
//        setMenuTable(0);
//        jenisMenuBox.setItems(jenisMenu);
        menuTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> displayEdit(newValue));        
        menuSimpanBtn.disableProperty().bind(Bindings.isEmpty(namaTextField.textProperty()));
        menuBatalBtn.disableProperty().bind(Bindings.isEmpty(namaTextField.textProperty()));
        
        //Menu Item
        kodeColumnMenuItem.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        namaColumnMenuItem.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        hargaColumnMenuItem.setCellValueFactory(cellData -> cellData.getValue().hargaJualProperty());
        stokColumnMenuItem.setCellValueFactory(cellData -> cellData.getValue().stokProperty());
        menuItemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> displayMenuItemEdit(newValue));
        menuItemUntungCB.getItems().addAll(CommonConstant.RUPIAH_SIMBOL, CommonConstant.PERSEN_SIMBOL);
        menuItemTambahanCB.getItems().addAll(CommonConstant.RUPIAH_SIMBOL, CommonConstant.PERSEN_SIMBOL);
        menuItemModalTF.textProperty().addListener(((observable, oldValue, newValue) -> {
            Integer modal = null != newValue && !newValue.isEmpty() ? Integer.valueOf(newValue) : 0;
            Integer untungBefore = null != menuItemUntungTF.getText() && !menuItemUntungTF.getText().isEmpty() ? Integer.valueOf(menuItemUntungTF.getText()) : 0;
            Integer tambahanBefore = null != menuItemTambahanTF.getText() && !menuItemTambahanTF.getText().isEmpty() ? Integer.valueOf(menuItemTambahanTF.getText()) : 0;
            sumHargaJual(modal, untungBefore, tambahanBefore);
        }));
        menuItemUntungTF.textProperty().addListener(((observable, oldValue, newValue) -> {
            Integer modal = null != menuItemModalTF.getText() && !menuItemModalTF.getText().isEmpty() ? Integer.valueOf(menuItemModalTF.getText()) : 0;
            Integer untungBefore = null != newValue && !newValue.isEmpty() ? Integer.valueOf(newValue) : 0;
            Integer tambahanBefore = null != menuItemTambahanTF.getText() && !menuItemTambahanTF.getText().isEmpty() ? Integer.valueOf(menuItemTambahanTF.getText()) : 0;
            sumHargaJual(modal, untungBefore, tambahanBefore);
        }));
        menuItemTambahanTF.textProperty().addListener(((observable, oldValue, newValue) -> {
            Integer modal = null != menuItemModalTF.getText() && !menuItemModalTF.getText().isEmpty() ? Integer.valueOf(menuItemModalTF.getText()) : 0;
            Integer untungBefore = null != menuItemUntungTF.getText() && !menuItemUntungTF.getText().isEmpty() ? Integer.valueOf(menuItemUntungTF.getText()) : 0;
            Integer tambahanBefore = null != newValue && !newValue.isEmpty() ? Integer.valueOf(newValue) : 0;
            sumHargaJual(modal, untungBefore, tambahanBefore);
        }));
        menuItemUntungCB.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            Integer modal = null != menuItemModalTF.getText() && !menuItemModalTF.getText().isEmpty() ? Integer.valueOf(menuItemModalTF.getText()) : 0;
            Integer untungBefore = null != menuItemUntungTF.getText() && !menuItemUntungTF.getText().isEmpty() ? Integer.valueOf(menuItemUntungTF.getText()) : 0;
            Integer tambahanBefore = null != menuItemTambahanTF.getText() && !menuItemTambahanTF.getText().isEmpty() ? Integer.valueOf(menuItemTambahanTF.getText()) : 0;
            sumHargaJual(modal, untungBefore, tambahanBefore);
        }));
        menuItemTambahanCB.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            Integer modal = null != menuItemModalTF.getText() && !menuItemModalTF.getText().isEmpty() ? Integer.valueOf(menuItemModalTF.getText()) : 0;
            Integer untungBefore = null != menuItemUntungTF.getText() && !menuItemUntungTF.getText().isEmpty() ? Integer.valueOf(menuItemUntungTF.getText()) : 0;
            Integer tambahanBefore = null != menuItemTambahanTF.getText() && !menuItemTambahanTF.getText().isEmpty() ? Integer.valueOf(menuItemTambahanTF.getText()) : 0;
            sumHargaJual(modal, untungBefore, tambahanBefore);
        }));
        
        menuItemSimpanBtn.disableProperty().bind(Bindings.isEmpty(menuItemKodeTF.textProperty()).or(Bindings.isEmpty(menuItemNamaTF.textProperty()).or(Bindings.isEmpty(menuItemModalTF.textProperty())).or(Bindings.isEmpty(menuItemUntungTF.textProperty()))));
//        menuItemBatalBtn.disableProperty().bind(Bindings.isEmpty(menuItemKodeTF.textProperty()).or(Bindings.isEmpty(menuItemNamaTF.textProperty()).or(Bindings.isEmpty(menuItemModalTF.textProperty())).or(Bindings.isEmpty(menuItemUntungTF.textProperty())).or(Bindings.isEmpty(menuItemTambahanTF.textProperty()))));
    }
    
    private void setMenuTable(int jenis) {
        if(menuPropObsList != null){
            menuPropObsList.clear();
            searchTextField.clear();
        }
        menuPropObsList = iMenu.getAllProperty(jenis);
        displayFilteredData();   
    }

    private void setJenisMenu() {
//        kodeColumnJenis.setCellValueFactory(cellData -> cellData.getValue().kodeProperty());
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
        statusColumnJenis.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        setJenisMenuTable();
        jenisMenuTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> displayEditJenis(newValue));
//        jenisStatusCB.getItems().addAll("Aktif", "Tidak Aktif");
        
        
        jenisMenuSimpanBtn.disableProperty().bind(Bindings.isEmpty(namaJenisTextField.textProperty()));
        jenisMenuBatalBtn.disableProperty().bind(Bindings.isEmpty(namaJenisTextField.textProperty()));
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
        posisiUsers.getItems().addAll("Administrator", "Kasir", "Staf");
        setUsersTable();
        usersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            usersDetail(newValue);
        });
        
        userSimpanBtn.disableProperty().bind(Bindings.isEmpty(namaUsersTF.textProperty()).or(Bindings.isEmpty(userNameUsersTF.textProperty()).or(Bindings.isEmpty(passwordUsersTF.textProperty()))));
        userBatalBtn.disableProperty().bind(Bindings.isEmpty(namaUsersTF.textProperty()).or(Bindings.isEmpty(userNameUsersTF.textProperty()).or(Bindings.isEmpty(passwordUsersTF.textProperty()))));
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
        TextField[] textFields = {usersIdTF ,namaUsersTF, userNameUsersTF, passwordUsersTF};
        String[] namaTextFields = {"ID", "Nama", "Username", "Password"};
        List<ChoiceBox<String>> choiceBoxs = new ArrayList<>();
        choiceBoxs.add(posisiUsers);
        String[] namaChoiceBoxs = {"Posisi"};
        if(iValidation.isTextFieldInputValid3(textFields, namaTextFields, choiceBoxs, namaChoiceBoxs, primaryStage)) {
            UsersProperty selectedUsersProp = usersTable.getSelectionModel().getSelectedItem();
            Users users = new Users();
//            Posisi posisi = new Posisi();
//            short posisiId = 0;
//            if(null != posisiUsers.getValue()){
            
//            }
            users.setNama(namaUsersTF.getText());
            users.setUsername(userNameUsersTF.getText());
            users.setPassword(passwordUsersTF.getText());
//            posisi.setId(posisiId);
            int posisiId = iPosisi.getIdByName(posisiUsers.getValue());
            users.setPosisiId(new Posisi(posisiId));
//            users.setDeletedFlag(userStatusCB.getValue().equalsIgnoreCase("Aktif") ? (short) 0 : (short) 1);
            users.setStatus(usersStatusCheckBox.isSelected());
            if(null != selectedUsersProp){
                users.setId(selectedUsersProp.getId());
                if(!iUsers.update(users, primaryStage)) {
                    return;
                }
//                if(iUsers.update(users, primaryStage)){
//                    selectedUsersProp.setNama(namaUsersTF.getText());
//                    selectedUsersProp.setUserName(userNameUsersTF.getText());
//                    selectedUsersProp.setPassword(passwordUsersTF.getText());
//                    selectedUsersProp.setPosisiId(posisiId);
//                    selectedUsersProp.setPosisiNama(posisiUsers.getValue());
//                }
            }else{
//                selectedUsersProp = new UsersProperty();
                users.setId(usersIdTF.getText());
                users.setStatusAbsensi(Boolean.FALSE);
                if(!iUsers.insert(users, primaryStage)) {
                    return;
                }
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
        usersDetail(null);
    }
    
//    @FXML
//    private void usersHapusAction(ActionEvent event) {
//        UsersProperty selectedUsersProp = usersTable.getSelectionModel().getSelectedItem();
//        if(null != selectedUsersProp){
//            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
//            alertConfirm.setTitle("Konfirmasi");
//            alertConfirm.setHeaderText("Anda Yakin?");
//
//            Optional<ButtonType> result = alertConfirm.showAndWait();
//            if (result.get() == ButtonType.OK){
//                iUsers.delete(selectedUsersProp.getId());
//                usersObsList.remove(selectedUsersProp);
//                usersTable.getSelectionModel().clearSelection();
//                usersBatalBtn(event);
//            }
//        }else{
//            //Nothing selected.
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.initOwner(primaryStage);
//            alert.setTitle("No Selection");
//            alert.setHeaderText("Tidak ada user yang dipilih");
//            alert.setContentText("Silahkan pilih user terlebih dahulu");
//            
//            alert.showAndWait();
//        }
//    }

    private void setBungkus() {
//        bungkusObsList.clear();
//        bungkusObsList = iTransaksi.getBungkus();
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
//        bungkusNamaTable.setItems(bungkusObsList);
        bungkusNamaTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue){
                displayOrderBungkus(newValue);
            }
        });
        
        //display ordered
//        kodeColumnBungkus.setCellValueFactory(cellDate -> cellDate.getValue().codeProperty());
        menuColumnBungkus.setCellValueFactory(cellData -> cellData.getValue().menuNamaProperty());
        itemColumnBungkus.setCellValueFactory(cellData -> cellData.getValue().menuItemNamaProperty());
        jumlahColumnBungkus.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty().asObject());
    }
    
//    @FXML
//    private void pesanBungkusBtn(ActionEvent event) {
//        try {
//            selectedNamaBungkus = bungkusNamaTable.getSelectionModel().getSelectedItem();
//            if(null != selectedNamaBungkus) {
//                dataEntryMenu(selectedNamaBungkus);
//            }else{
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.initOwner(primaryStage);
//                alert.setTitle("No Selection");
//                alert.setHeaderText("Tidak ada pemesan yang dipilih");
//                alert.setContentText("Silahkan pilih pemesan terlebih dahulu");
//
//                alert.showAndWait();
//            }
//        } catch (IOException ex) {
//            LOGGER.error("", ex);
//            }
//    }
    
//    @FXML
//    private void tambahBaruBungkusBtn(ActionEvent actionEvent){
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(AdminController.class.getResource("NamaPemesanBungkus.fxml"));
//            VBox page = (VBox) loader.load();
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Masukkan Nama Pemesan");
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.initOwner(primaryStage);
//            Scene scene = new Scene(page);
//            dialogStage.setScene(scene);
//            NamaPemesanBungkusController controller = loader.getController();
//            controller.setDialogStage(dialogStage);
//            controller.setLoginUser(loginUser);
//
//            dialogStage.showAndWait();
// 
//            if(controller.isOkClicked() == true){
//                TransaksiProperty transaksiProperty = new TransaksiProperty();
//                transaksiProperty.setId(controller.getId());
//                dataEntryMenu(transaksiProperty);
//                bungkusObsList.clear();
//                bungkusObsList = iTransaksi.getBungkus();
//                bungkusNamaTable.setItems(bungkusObsList);
//                bungkusNamaTable.getSelectionModel().selectLast();
//            }
//        } catch (IOException e) {
//            LOGGER.error("failed to load NamaPemesanBungkus.fxml", e);
//        }
//    }

    private void displayOrderBungkus(TransaksiProperty tp) {
        menuBungkusObsList.clear();
//        totalHargaBungkus = 0;
        if(null != tp){
            menuBungkusObsList = iPesan.getDetailByTransaksiId(tp.getId());
            namaBungkus.setText(tp.getNamaPemesan());
//            for(int i=0; i<menuBungkusObsList.size(); i++){
//                totalHargaBungkus += menuBungkusObsList.get(i).getTotalHarga();
//            }
            bungkusDetailDiskonNama.setText(menuBungkusObsList.get(0).getDiskonNama());
            bungkusDetailPajakNama.setText(menuBungkusObsList.get(0).getPajakNama());
            totalBungkus.setText(numberFormat.format(tp.getTotal()));
            
            bungkusBayarBtn.setDisable(false);
            bungkusBatalBtn.setDisable(false);
            bungkusUbahBtn.setDisable(false);
        }else{
            namaBungkus.setText("");
            bungkusDetailDiskonNama.setText("");
            bungkusDetailPajakNama.setText("");
            totalBungkus.setText("0");
            
            bungkusBayarBtn.setDisable(true);
            bungkusBatalBtn.setDisable(true);
            bungkusUbahBtn.setDisable(true);
        }
//        namaColumnBungkus.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
//        jumlahColumnBungkus.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty().asObject());
        bungkusMenuTable.setItems(menuBungkusObsList);
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
//        controller.setTransaksiId(transaksiProperty.getId());
        dialogStage.showAndWait();
        bungkusNamaTable.getSelectionModel().clearSelection();
//        if(controller.getPesanPropObsList() != null && controller.getPesanPropObsList().size() > 0){
            bungkusNamaTable.getSelectionModel().select(transaksiProperty);
//        }
    }
    
    @FXML
    private void mejaBatalAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText("Anda Yakin?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Transaksi transaksi = new Transaksi();
            transaksi.setId(transaksiIdMeja);
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
            
            transaksiIdMeja = 0;
            mejaActive = 0;
            mejaViewAction();
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
                transaksi.setTotal(selectedNamaBungkus.getTotal());
                Users users = new Users();
                users.setId(loginUser.getId());
                transaksi.setUserEnd(users);
                iTransaksi.updateStatus(transaksi);
                
                bungkusNamaTable.getSelectionModel().clearSelection();
                bungkusObsList.remove(selectedNamaBungkus);
                displayOrderBungkus(null);
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
    private void mejaPindahAction(ActionEvent actionEvent) {
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
//            getMejaStatus();
//            displayOrder(mejaActive);
            namaMeja.setText("Meja " + letter);
            displayOrderedMeja();
        });
    }
    
    @FXML
    private void mejaCetakAction(ActionEvent actionEvent) throws JRException, ClassNotFoundException, SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText("Anda Yakin?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
             InputStream logoStream = this.getClass().getResourceAsStream(CommonConstant.LOGO_KEDAI);
            String printer;
            if(general.getPrinterCode().equalsIgnoreCase("58mm")) {
                printer = "reports/print_A8_logo.jasper";
            }else{
                printer = "reports/print_A7_logo.jasper";
            }
            InputStream reportStream = this.getClass().getResourceAsStream(printer);

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
            parameters.put("logoImage", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(logoStream))));
            parameters.put("title", CommonConstant.KEDAI_NAMA);
            parameters.put("subtitle", CommonConstant.KEDAI_SUB_NAMA);
            parameters.put("alamat1", CommonConstant.KEDAI_ALAMAT1);
            parameters.put("alamat2", CommonConstant.KEDAI_ALAMAT2);
            parameters.put("alamat3", CommonConstant.KEDAI_ALAMAT3);
            parameters.put("waktu", new Date());
            parameters.put("meja", "Meja "+ iMeja.getNomorById(mejaActive));
            parameters.put("kasir", loginUser.getNama());
            parameters.put("total", mejaGrandTotal);
            parameters.put("diskonNama", mejaDiskonCB.getValue().getDisplayString());
            parameters.put("diskonTotal", mejaGlobalDiskon > 0 ? mejaGlobalDiskon : null);
            parameters.put("pajakNama", mejaPajakCB.getValue().getDisplayString());
            parameters.put("pajakTotal", mejaGlobalPajak > 0 ? mejaGlobalPajak : null);

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(menuMejaObsList);
            JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanCollectionDataSource);
            JasperPrintManager.printReport(print, false);
        }
    }
    
//    @FXML
//    private void btnCetakBungkus(ActionEvent actionEvent) throws JRException, ClassNotFoundException, SQLException, IOException {
//        selectedNamaBungkus = bungkusNamaTable.getSelectionModel().getSelectedItem();
//        if(selectedNamaBungkus != null){
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Konfirmasi");
//            alert.setHeaderText("Anda Yakin?");
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK){
//                InputStream logoStream = this.getClass().getResourceAsStream("img/logo_kedai.png");
//                InputStream reportStream = this.getClass().getResourceAsStream("reports/print_A7_logo.jasper");
//                HashMap<String, Object> parameters = new HashMap<>();
//                parameters.put("logoImage", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(logoStream))));
//                parameters.put("title", CommonConstant.KEDAI_NAMA);
//                parameters.put("subtitle", CommonConstant.KEDAI_SUB_NAMA);
//                parameters.put("alamat1", CommonConstant.KEDAI_ALAMAT1);
//                parameters.put("alamat2", CommonConstant.KEDAI_ALAMAT2);
//                parameters.put("alamat3", CommonConstant.KEDAI_ALAMAT3);
//                parameters.put("waktu", new Date());
//                parameters.put("meja", namaBungkus.getText());
//                parameters.put("kasir", loginUser.getNama());
//                parameters.put("total", numberFormat.format(selectedNamaBungkus.getTotal()));
//                JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(menuBungkusObsList);
//                JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanCollectionDataSource);
//                JasperPrintManager.printReport(print, false);
//            }
//        }else{
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.initOwner(primaryStage);
//            alert.setTitle("No Selection");
//            alert.setHeaderText("Tidak ada pemesan yang dipilih");
//            alert.setContentText("Silahkan pilih pemesan terlebih dahulu");
//            
//            alert.showAndWait();
//        }
//    }
    
    @FXML
    private void mejaBayarAction(ActionEvent actionEvent) {
        try {
            if(iMenuItem.cekStokBayar(menuMejaObsList, primaryStage)){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(AdminController.class.getResource("Bayar.fxml"));
                BorderPane page = (BorderPane) loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Bayar");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);

                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                BayarController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                Transaksi transaksi = new Transaksi();
                transaksi.setId(transaksiIdMeja);
                transaksi.setTotal(mejaGrandTotal);
                if(mejaDiskonCB.getValue().getId() > 0) {
                    transaksi.setDiskonId(new Diskon(mejaDiskonCB.getValue().getId()));
                    transaksi.setDiskonTotal(mejaGlobalDiskon);
                }
                if(mejaPajakCB.getValue().getId() > 0) {
                    transaksi.setPajakId(new Pajak(mejaPajakCB.getValue().getId()));
                    transaksi.setPajakTotal(mejaGlobalPajak);
                }
                transaksi.setUserEnd(loginUser);
                transaksi.setMejaId(new Meja(mejaActive));
                transaksi.setModalTotal(totalModalMeja);
                controller.initValue(transaksi, CommonConstant.TRANSAKSI_PESAN, menuMejaObsList);

                dialogStage.showAndWait();

                if(controller.isOkClicked()){
                    int tunai = controller.getUang();
                    ObservableList<PesanProperty> obsList = FXCollections.observableArrayList();
                    obsList.addAll(menuMejaObsList);
                    PrintStruk printBayar = new PrintStruk(tunai, loginUser, obsList, mejaGrandTotal, "Meja " + mejaActive, mejaDiskonCB.getValue().getDisplayString(), mejaGlobalDiskon, mejaPajakCB.getValue().getDisplayString(), mejaGlobalPajak);
                    new Thread(printBayar).start();
                    displayKembalian(numberFormat.format(tunai-mejaGrandTotal));
                    Meja meja = new Meja();
                    meja.setId(mejaActive);
                    meja.setStatus(CommonConstant.MEJA_TERSEDIA);
                    iMeja.update(meja);

                    transaksiIdMeja = 0;
                    mejaActive = 0;

                    mejaViewAction();
                }
            }
            
        } catch (IOException ex) {
            LOGGER.error("failed to load Bayar.fxml", ex);
        }
    }

//    private void bayarDialog(Integer tunai) {
//            try {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(AdminController.class.getResource("BayarDialog.fxml"));
//                AnchorPane page = (AnchorPane) loader.load();
//
//                Stage dialogStage = new Stage();
//                dialogStage.setTitle("Bayar");
//                dialogStage.initModality(Modality.WINDOW_MODAL);
//                dialogStage.initOwner(primaryStage);
//
//                Scene scene = new Scene(page);
//                dialogStage.setScene(scene);
//
//                BayarDialogController controller = loader.getController();
//                controller.setDialogStage(dialogStage);
//                controller.initValue(menuMejaObsList, tunai, loginUser);
//
//                dialogStage.showAndWait();
//                
//                if(controller.isOkClicked()){
//                    InputStream logoStream = this.getClass().getResourceAsStream("img/logo_kedai.jpg");
//                    InputStream reportStream = this.getClass().getResourceAsStream("reports/bayar_A7_logo.jasper");                
//                    HashMap<String, Object> parameters = new HashMap<>();
//                    parameters.put("logoImage", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(logoStream))));
//                    parameters.put("title", CommonConstant.KEDAI_NAMA);
//                    parameters.put("subtitle", CommonConstant.KEDAI_SUB_NAMA);
//                    parameters.put("alamat1", CommonConstant.KEDAI_ALAMAT1);
//                    parameters.put("alamat2", CommonConstant.KEDAI_ALAMAT2);
//                    parameters.put("alamat3", CommonConstant.KEDAI_ALAMAT3);
//                    parameters.put("waktu", new Date());
//                    parameters.put("meja", "Meja " + iMeja.getNomorById(mejaActive));
//                    parameters.put("kasir", loginUser.getNama());
//                    parameters.put("total", numberFormat.format(totalHargaMeja));
//                    parameters.put("tunai", numberFormat.format(tunai));
//                    parameters.put("kembali", numberFormat.format(tunai-totalHargaMeja));
//                    parameters.put("syukran1", CommonConstant.KEDAI_SYUKRAN_1);
//                    parameters.put("syukran2", CommonConstant.KEDAI_SYUKRAN_2);
//                    JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(menuMejaObsList);
//                    JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanCollectionDataSource);
//                    JasperPrintManager.printReport(print, false);
//                    getMejaStatus();
//                    displayOrder((short) 0);
//                }
//                
//            } catch (IOException e) {
//                LOGGER.error("failed to load BayarDialog.fxml", e);
//            } catch (JRException ex) {
//                LOGGER.error("failed to print report", ex);
//        }
//    }
    
    @FXML
    private void bungkusBayarBtn(ActionEvent actionEvent) {
        selectedNamaBungkus = bungkusNamaTable.getSelectionModel().getSelectedItem();
        try {
            if(iMenuItem.cekStokBayar(menuBungkusObsList, primaryStage)){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(AdminController.class.getResource("Bayar.fxml"));
                BorderPane page = (BorderPane) loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Bayar");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);

                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                BayarController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                Transaksi transaksi = new Transaksi();
                transaksi.setId(selectedNamaBungkus.getId());
                transaksi.setTotal(selectedNamaBungkus.getTotal());
//                transaksi.setDiskonId(new Diskon(pesanDiskonCB.getValue().getId()));
//                transaksi.setPajakId(new Pajak(pesanPajakCB.getValue().getId()));
//                transaksi.setDiskonTotal(pesanGlobalDiskon);
//                transaksi.setPajakTotal(pesanGlobalPajak);
                transaksi.setUserEnd(loginUser);
                transaksi.setMejaId(new Meja((short) 0));
                controller.initValue(transaksi, CommonConstant.TRANSAKSI_TERSIMPAN, menuBungkusObsList);
//                controller.initValue(selectedNamaBungkus.getId(), hargaGrandTotal, loginUser, pesanGlobalDiskon, pesanGlobalPajak);

                dialogStage.showAndWait();

                if(controller.isOkClicked()){
                    int tunai = controller.getUang();
                    ObservableList<PesanProperty> obsList = FXCollections.observableArrayList();
                    obsList.addAll(menuBungkusObsList);
                    PrintStruk printBayar = new PrintStruk(tunai, loginUser, obsList, selectedNamaBungkus.getTotal(), selectedNamaBungkus.getNamaPemesan(), bungkusDetailDiskonNama.getText(), selectedNamaBungkus.getDiskonTotal(), bungkusDetailPajakNama.getText(), selectedNamaBungkus.getPajakTotal());
                    new Thread(printBayar).start();
                    displayKembalian(numberFormat.format(tunai-selectedNamaBungkus.getTotal()));

                    bungkusNamaTable.getSelectionModel().clearSelection();
                    bungkusObsList.remove(selectedNamaBungkus);
                    displayOrderBungkus(null);
                }
            }

        } catch (IOException ex) {
            LOGGER.error("failed to load Bayar.fxml", ex);
        }
    }
    
//    private void bungkusBayarDialog(Integer tunai) {
//            try {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(AdminController.class.getResource("BayarDialog.fxml"));
//                AnchorPane page = (AnchorPane) loader.load();
//
//                Stage dialogStage = new Stage();
//                dialogStage.setTitle("Bayar");
//                dialogStage.initModality(Modality.WINDOW_MODAL);
//                dialogStage.initOwner(primaryStage);
//
//                Scene scene = new Scene(page);
//                dialogStage.setScene(scene);
//
//                BayarDialogController controller = loader.getController();
//                controller.setDialogStage(dialogStage);
//                controller.initValue(menuBungkusObsList, tunai, loginUser);
//                
//                dialogStage.showAndWait();
//                
//                if(controller.isOkClicked()){
//                    InputStream logoStream = this.getClass().getResourceAsStream("img/logo_kedai.png");
//                    InputStream reportStream = this.getClass().getResourceAsStream("reports/bayar_A7_logo.jasper");
//                    HashMap<String, Object> parameters = new HashMap<String, Object>();
//                    parameters.put("logoImage", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(logoStream))));
//                    parameters.put("title", CommonConstant.KEDAI_NAMA);
//                    parameters.put("subtitle", CommonConstant.KEDAI_SUB_NAMA);
//                    parameters.put("alamat1", CommonConstant.KEDAI_ALAMAT1);
//                    parameters.put("alamat2", CommonConstant.KEDAI_ALAMAT2);
//                    parameters.put("alamat3", CommonConstant.KEDAI_ALAMAT3);
//                    parameters.put("waktu", new Date());
//                    parameters.put("meja", selectedNamaBungkus.getNamaPemesan());
//                    parameters.put("kasir", loginUser.getNama());
//                    parameters.put("total", numberFormat.format(totalHargaBungkus));
//                    parameters.put("tunai", numberFormat.format(tunai));
//                    parameters.put("kembali", numberFormat.format(tunai-totalHargaBungkus));
//                    parameters.put("syukran1", CommonConstant.KEDAI_SYUKRAN_1);
//                    parameters.put("syukran2", CommonConstant.KEDAI_SYUKRAN_2);
//                    JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(menuBungkusObsList);
//                    JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanCollectionDataSource);
//                    JasperPrintManager.printReport(print, false);
//                    bungkusNamaTable.getSelectionModel().clearSelection();
//                    bungkusObsList.remove(selectedNamaBungkus);
//                    displayOrderBungkus(new TransaksiProperty());
//                }
//                
//            } catch (IOException e) {
//                LOGGER.error("failed to load BayarDialog.fxml", e);
//            } catch (JRException ex) {
//                LOGGER.error("failed to print report", ex);
//            }
//    }

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
        
        getUsersChoiceList();
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
//        mejaColumnPemasukan.setCellValueFactory(cellData -> cellData.getValue().mejaproperty().asObject());
//        pelangganColumnPemasukan.setCellValueFactory(cellData -> cellData.getValue().namaPemesanProperty());
        statusColumnPemasukan.setCellValueFactory(cellData -> cellData.getValue().statusTransaksiproperty());
        totalColumnPemasukan.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        metodePembayaranColumnPemasukan.setCellValueFactory(cellData -> cellData.getValue().metodePembayaranNamaProperty());
        
        pemasukanTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayPemasukanDetails(newValue);
        });
        
        //metodePembayaranTable
        pemasukanMetodePembayaranClm.setCellValueFactory(cellData -> cellData.getValue().metodePembayaranNamaProperty());
        pemasukanMetodePembayaranTotalClm.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        
        //displayDetail
//        pemasukanDetailKodeClmn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        pemasukanDetailMenuClmn.setCellValueFactory(cellData -> cellData.getValue().menuNamaProperty());
        pemasukanDetailItemClmn.setCellValueFactory(cellData -> cellData.getValue().menuItemNamaProperty());
        pemasukanDetailJumlahClmn.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty().asObject());
    }

    private void getUsersChoiceList() {
        setPemasukanTable(loginUser.getId());
        usersChoiceObsMap.clear();
        usersChoicesString.clear();
        usersChoiceObsMap.put("Semua", "");
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

    private void setPemasukanTable(String userId) {
        Integer pemasukanTotalBayar = 0;
        Integer pemasukanTotalBatal = 0;
        pemasukanObsList = iTransaksi.getPemasukanByTglAndUser(pemasukanDate.getValue(), userId);
        for(int i = 0; i < pemasukanObsList.size(); i++){
            TransaksiProperty transaksiProperty = (TransaksiProperty) pemasukanObsList.get(i);
            if(Objects.equals(transaksiProperty.getStatusTransaksi(), CommonConstant.TRANSAKSI_BAYAR)){
                pemasukanTotalBayar += transaksiProperty.getTotal();
            }else if(Objects.equals(transaksiProperty.getStatusTransaksi(), CommonConstant.TRANSAKSI_BATAL)){
                pemasukanTotalBatal += transaksiProperty.getTotal();
            }
        }
//        Integer pemasukanTotalSemua = pemasukanTotalBayar + pemasukanTotalBatal;
        totalBayar.setText(numberFormat.format(pemasukanTotalBayar));
        totalBatal.setText(numberFormat.format(pemasukanTotalBatal));
//        totalSemua.setText(numberFormat.format(pemasukanTotalSemua));
        pemasukanTable.setItems(pemasukanObsList);
        
        //metodePembayaranTable
        pemasukanMetodePembayaranTable.setItems(iTransaksi.getMetodePembayaranByTglAndUser(pemasukanDate.getValue(), userId));
    }
    
    @FXML
    private void pemasukanPilihTanggal(ActionEvent actionEvent) {
        setPemasukanTable(usersChoiceObsMap.get(pemasukanChoiceBox.getSelectionModel().getSelectedItem()));
    }

    private void displayChart() {
//        grafikBulanCB.setItems(grafikBulanChoice);
        grafikBulanCB.getItems().addAll(CommonConstant.NAMA_BULAN);
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
        jenisLaporanCB.getItems().addAll("Penjualan Harian", "Penjualan Produk", "Absensi");
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
//        laporanDirectory.setText(System.getProperty("user.home"));
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
        series1.setName("Penjualan");
        XYChart.Series<String, Number> series2 = new XYChart.Series();
        series2.setName("Beban");
        
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
        series1.setName("Penjualan");
        XYChart.Series<String, Number> series2 = new XYChart.Series();
        series2.setName("Beban");
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
        
        pengeluaranSimpanBtn.disableProperty().bind(Bindings.isEmpty(namaPengeluaranTF.textProperty()).or(Bindings.isEmpty(hargaPengeluaranTF.textProperty())));
        pengeluaranBatalBtn.disableProperty().bind(Bindings.isEmpty(namaPengeluaranTF.textProperty()).or(Bindings.isEmpty(hargaPengeluaranTF.textProperty())));
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
            pengeluaranHapusBtn.setDisable(false);
        }else{
            pengeluaranHapusBtn.setDisable(true);
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
//        List<ChoiceBox> choiceBoxs = new ArrayList<>();
//        choiceBoxs.add(statusAdminMejaCB);
//        String[] namaChoiceBoxs = {"Status"};
        if(iValidation.isTextFieldInputValid(textFields, namaTextFields, primaryStage)){
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
    
//    @FXML
//    private void direktoriBackupBtn(ActionEvent event) {
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        directoryChooser.setTitle("Pilih Direktori/Folder");
//        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//        
//        File dirTarget = directoryChooser.showDialog(primaryStage);
//        if(dirTarget != null){
//            backupDirectory.setText(dirTarget.getAbsolutePath());
//            directoryChooser.setInitialDirectory(new File(dirTarget.getAbsolutePath()));
//        }
//    }

//    private void setBackupAndRestore() {
//        backupDirectory.setText(System.getProperty("user.home"));
//        restoreDirectory.setText(System.getProperty("user.home"));
//    }
    
    @FXML
    private void backupAction(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Pilih Direktori/Folder");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dirTarget = directoryChooser.showDialog(primaryStage);
        if(dirTarget != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedDate = LocalDate.now().format(formatter);
            String backupDir = dirTarget.getAbsolutePath() + File.separator + "backup_" + formattedDate;
            
            JDBCConnection.databaseBackup(backupDir);
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setHeaderText("Selesai");
            alert.setContentText("Proses backup telah selesai. Silahkan lanjutkan!");

            alert.showAndWait();
        }
    }
    
//    @FXML
//    private void direktoriRestoreBtn(ActionEvent event) {
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        directoryChooser.setTitle("Pilih Direktori/Folder");
//        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//        File dirTarget = directoryChooser.showDialog(primaryStage);
//        if(dirTarget != null){
//            restoreDirectory.setText(dirTarget.getAbsolutePath());
//            directoryChooser.setInitialDirectory(new File(dirTarget.getAbsolutePath()));
//        }
//    }
    
    @FXML
    private void restoreAction(ActionEvent actionEvent) {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Konfirmasi");
        alertConfirm.setHeaderText("Anda Yakin?");

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get() == ButtonType.OK){
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Pilih Direktori/Folder");
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File dirTarget = directoryChooser.showDialog(primaryStage);
            if(dirTarget != null){
//                String backupDir = dirTarget.getAbsolutePath();
                JDBCConnection.databaseRestore(dirTarget.getAbsolutePath());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Informasi");
                alert.setHeaderText("Selesai");
                alert.setContentText("Proses restore telah selesai. Silahkan restart aplikasi ini!");
                alert.showAndWait();
                Platform.exit();
            }
        }
    }
    
//    @FXML
//    private void direktoriLaporanBtn(ActionEvent event) {
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        directoryChooser.setTitle("Pilih Direktori/Folder");
//        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//        File dirTarget = directoryChooser.showDialog(primaryStage);
//        if(dirTarget != null){
//
//        }
//    }
    
    @FXML
    private void downloadLaporan(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Pilih Direktori/Folder");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dirTarget = directoryChooser.showDialog(primaryStage);
        if(dirTarget != null){
//            directoryChooser.setInitialDirectory(new File(dirTarget.getAbsolutePath()));
            try {
                if(jenisLaporanCB.getSelectionModel().isSelected(0)){
                    List<Laporan> dataList = iTransaksi.getLaporan(transaksiDariDate.getValue(), transaksiSampaiDate.getValue());
                    if(null != dataList && dataList.size() > 0) {
                        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
                        Date tglDari = Date.from(transaksiDariDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Date tglSampai = Date.from(transaksiSampaiDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        HashMap<String, Object> parameters = new HashMap<>();
                        parameters.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
                        parameters.put("cafe", CommonConstant.KEDAI_NAMA);
                        parameters.put("tglDari", tglDari);
                        parameters.put("tglSampai", tglSampai);
                        InputStream reportStream = this.getClass().getResourceAsStream("reports/laporan.jasper");
                        JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanColDataSource);
                        if(print != null) {
                            DateFormat df = new SimpleDateFormat("yyyyMMdd");
                            String printOutDir= dirTarget.getAbsolutePath() + File.separator + "Laporan_Penjualan_Harian_" + df.format(tglDari) + "-" + df.format(tglSampai);
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
                }else if(jenisLaporanCB.getSelectionModel().isSelected(1)){
                    List<Laporan> dataList = iTransaksi.getLaporanPenjualan(transaksiDariDate.getValue(), transaksiSampaiDate.getValue());
                    Laporan laporan = iTransaksi.getLaporanPenjualan2(transaksiDariDate.getValue(), transaksiSampaiDate.getValue());
                    if(null != dataList && dataList.size() > 0) {
                        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
                        Date tglDari = Date.from(transaksiDariDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Date tglSampai = Date.from(transaksiSampaiDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        HashMap<String, Object> parameters = new HashMap<>();
                        parameters.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
                        parameters.put("cafe", CommonConstant.KEDAI_NAMA);
                        parameters.put("tglDari", tglDari);
                        parameters.put("tglSampai", tglSampai);
                        parameters.put("diskon", laporan.getDiskon());
                        parameters.put("pajak", laporan.getPajak());
                        InputStream reportStream = this.getClass().getResourceAsStream("reports/lap_penjualan.jasper");
                        JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanColDataSource);
                        if(print != null) {
                            DateFormat df = new SimpleDateFormat("yyyyMMdd");
                            String printOutDir= dirTarget.getAbsolutePath() + File.separator + "Laporan_Penjualan_Produk_" + df.format(tglDari) + "-" + df.format(tglSampai);
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
                }else if(jenisLaporanCB.getSelectionModel().isSelected(2)) {
                    List<AbsensiProperty> dataList = iAbsensi.getLaporan(transaksiDariDate.getValue(), transaksiSampaiDate.getValue());
                    if(null != dataList && dataList.size() > 0) {
                        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
                        Date tglDari = Date.from(transaksiDariDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Date tglSampai = Date.from(transaksiSampaiDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        HashMap<String, Object> parameters = new HashMap<>();
                        parameters.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
                        parameters.put("cafe", CommonConstant.KEDAI_NAMA);
                        parameters.put("tglDari", tglDari);
                        parameters.put("tglSampai", tglSampai);
                        InputStream reportStream = this.getClass().getResourceAsStream("reports/absensi.jasper");
                        JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanColDataSource);
                        if(print != null) {
                            DateFormat df = new SimpleDateFormat("yyyyMMdd");
                            String printOutDir= dirTarget.getAbsolutePath() + File.separator + "Absensi_" + df.format(tglDari) + "-" + df.format(tglSampai);
                            JasperExportManager.exportReportToPdfFile(print, printOutDir + ".pdf");
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Informasi");
                            alert.setHeaderText("Selesai");
                            alert.setContentText("Absensi telah dibuat");
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

    private void setPesan() {
        //set table
        noColumnPesan.setCellValueFactory((TableColumn.CellDataFeatures<MenuProperty, MenuProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumnPesan.setCellFactory((TableColumn<MenuProperty, MenuProperty> param) -> new TableCell<MenuProperty, MenuProperty>() {
            @Override protected void updateItem(MenuProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noColumnPesan.setSortable(false);
        namaColumnPesan.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        
        //set BoxChoice
        jenisBoxPesan.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Choice> observable, Choice oldValue, Choice newValue) -> {
            if(null != newValue){
                pesanMenuTable.setItems(iMenu.getActiveProperty(newValue.getId()));
            }
        });
        setPesanCB();
        
        noColumnMenuItemPesan.setCellValueFactory((TableColumn.CellDataFeatures<MenuItemProperty, MenuItemProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumnMenuItemPesan.setCellFactory((TableColumn<MenuItemProperty, MenuItemProperty> param) -> new TableCell<MenuItemProperty, MenuItemProperty>() {
            @Override protected void updateItem(MenuItemProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noColumnMenuItemPesan.setSortable(false);
        namaColumnMenuItemPesan.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        
        pesanMenuTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue) {
                pesanItemBtn.setDisable(false);
            }else{
                pesanItemBtn.setDisable(true);
            }
        });
        
        pesanMenuItemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue) {
                pesanBtn.setDisable(false);
            }else{
                pesanBtn.setDisable(true);
            }
        });

        //barcode reader
        pesanOrderedTable.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent event) -> {
            keyTyped(event);
        });
        
        TextFields.bindAutoCompletion(searchmenuItemComboBox.getEditor(), (AutoCompletionBinding.ISuggestionRequest t) -> {
            return iMenuItem.searchMenuItemByCode(t.getUserText());
        });
        searchmenuItemComboBox.getSelectionModel().selectedItemProperty().addListener(((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(null != newValue) {
                int iend = newValue.indexOf("-");
                if(iend != -1) {
                    String subString = newValue.substring(0, iend);
                    String code = subString.trim();
                    searchmenuItemComboBox.getEditor().setText(null);

                    TextInputDialog dialog = new TextInputDialog("1");
                    dialog.setTitle(newValue);
                    dialog.setHeaderText("Jumlah");
                    dialog.setContentText("Masukkan jumlah barang yang dipesan:");

                    Optional<String> result = dialog.showAndWait();
                    // The Java 8 way to get the response value (with lambda expression).
                    result.ifPresent((String jumlah) -> {
                        
                        addNewOrder(code, Integer.valueOf(jumlah));
                        
                    });
                }
            }
        }));
        
        //display ordered
//        kodeColumnOrderedPesan.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        menuColumnOrderedPesan.setCellValueFactory(cellData -> cellData.getValue().menuNamaProperty());
        itemColumnOrderedPesan.setCellValueFactory(cellData -> cellData.getValue().menuItemNamaProperty());
        jumlahColumnOrderedPesan.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty().asObject());
        
        pesanDiskonCB.setItems(iDiskon.getAllActive());
        pesanDiskonCB.getSelectionModel().selectFirst();
        pesanDiskonCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && null != newValue.getId() && null != pesanPajakCB.getSelectionModel().getSelectedItem()) {
                Integer diskonId = newValue.getId();
                Integer pajakId = pesanPajakCB.getSelectionModel().getSelectedItem().getId();
                setGrandTotalPesan(totalHargaPesan, diskonId, pajakId);
            }
        });
        pesanPajakCB.setItems(iPajak.getAllActive());
        pesanPajakCB.getSelectionModel().selectFirst();
        pesanPajakCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && null != newValue.getId() && null != pesanDiskonCB.getSelectionModel().getSelectedItem()) {
                Integer diskonId = pesanDiskonCB.getSelectionModel().getSelectedItem().getId();
                Integer pajakId = newValue.getId();
                setGrandTotalPesan(totalHargaPesan, diskonId, pajakId);
            }
        });
        
        pesanOrderedTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue) {
                pesanUbahBtn.setDisable(false);
                pesanHapusBtn.setDisable(false);
            }else{
                pesanUbahBtn.setDisable(true);
                pesanHapusBtn.setDisable(true);
            }
        });
    }

    private void setPesanCB() {
        jenisBoxPesan.getItems().clear();
        jenisBoxPesan.getItems().add(new Choice(0, "Semua"));
        jenisBoxPesan.getItems().addAll(iJenis.getAllActiveChoice());
        jenisBoxPesan.getSelectionModel().selectFirst();
    }
    
//    private void setPesanMenuTable(short jenis) {
//        if(pesanMenuPropObsList != null){
//            pesanMenuPropObsList.clear();
//            searchPesanTF.clear();
//        }
//        pesanMenuPropObsList = iMenu.getActiveProperty(jenis);
//        displayFilteredDataPesan();
//    }

//    private void displayFilteredDataPesan() {
//        FilteredList<MenuProperty> filteredData = new FilteredList<>(pesanMenuPropObsList, p -> true);
//        
//        searchPesanTF.textProperty().addListener((observable, oldValue, newValue) -> {
//			filteredData.setPredicate(person -> {
//                            // If filter text is empty, display all persons.
//                            if (newValue == null || newValue.isEmpty()) {
//                                return true;
//                            }
//
//                            // Compare first name and last name of every person with filter text.
//                            String lowerCaseFilter = newValue.toLowerCase();
//
//                            if (person.getNama().toLowerCase().contains(lowerCaseFilter)) {
//                                return true; // Filter matches first name.
//                            } 
////                            else if (person.getNama().toLowerCase().contains(lowerCaseFilter)) {
////                                return true; // Filter matches last name.
////                            }
//                            return false; // Does not match.
//			});
//		});
//        
//        SortedList<MenuProperty> sortedData = new SortedList<>(filteredData);
//        sortedData.comparatorProperty().bind(pesanMenuTable.comparatorProperty());
//        pesanMenuTable.setItems(sortedData);
//    }
    
    @FXML
    private void pilihBtnPesan(ActionEvent actionEvent){
        MenuProperty selectedMenuProp = pesanMenuTable.getSelectionModel().getSelectedItem();
        
        if(null != selectedMenuProp){
//            try {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(AdminController.class.getResource("Pesan.fxml"));
//                BorderPane page = (BorderPane) loader.load();
//
//                Stage dialogStage = new Stage();
//                dialogStage.setTitle("Pesan");
//                dialogStage.initModality(Modality.WINDOW_MODAL);
//                dialogStage.initOwner(primaryStage);
//
//                Scene scene = new Scene(page);
//                dialogStage.setScene(scene);
//
//                PesanController controller = loader.getController();
//                controller.setDialogStage(dialogStage);
//                controller.setData(selectedMenuProp, transaksiId, loginUser.getId());
//
//                dialogStage.showAndWait();
//
//                transaksiId = controller.getTransaksiId();
//                displayOrderPesan();
//               
//            } catch (IOException ex) {
//                LOGGER.error("failed to load Pesan.fxml", ex);
//            }
            pesanMenuItemTable.setItems(iMenuItem.getPropertyByMenuId(selectedMenuProp.getId()));
            pesanMenuBox.setVisible(false);
            pesanMenuItemBox.setVisible(true);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(primaryStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("Tidak ada menu yang dipilih");
            alert.setContentText("Silahkan pilih menu terlebih dahulu");
            
            alert.showAndWait();
        }
    }

    private void displayOrderPesan() {
        pesanMenuItemOrderedObsList.clear();
        totalHargaPesan = 0;
        totalModalPesan = 0;
        if(transaksiId > 0){
            pesanMenuItemOrderedObsList = iPesan.getDetailByTransaksiId(transaksiId);
            if(null != pesanMenuItemOrderedObsList && !pesanMenuItemOrderedObsList.isEmpty()) {
                for(int i=0; i<pesanMenuItemOrderedObsList.size(); i++){
                    totalHargaPesan += pesanMenuItemOrderedObsList.get(i).getTotalHarga();
                    totalModalPesan += pesanMenuItemOrderedObsList.get(i).getTotalModal();
                }
                pesanDiskonCB.setValue(new Choice(pesanMenuItemOrderedObsList.get(0).getDiskonId(), pesanMenuItemOrderedObsList.get(0).getDiskonNama()));
                pesanPajakCB.setValue(new Choice(pesanMenuItemOrderedObsList.get(0).getPajakId(), pesanMenuItemOrderedObsList.get(0).getPajakNama()));
            }else{
                pesanDiskonCB.getSelectionModel().selectFirst();
                pesanPajakCB.getSelectionModel().selectFirst();
            }
            pesanBatalBtn.setDisable(false);
            pesanSimpanBtn.setDisable(false);
            pesanBayarBtn.setDisable(false);
        }else{
            pesanDiskonCB.getSelectionModel().selectFirst();
            pesanPajakCB.getSelectionModel().selectFirst();
            
            pesanBatalBtn.setDisable(true);
            pesanSimpanBtn.setDisable(true);
            pesanBayarBtn.setDisable(true);
        }
        
//        namaColumnOrderedPesan.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
//        jumlahColumnOrderedPesan.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty().asObject());
        pesanOrderedTable.setItems(pesanMenuItemOrderedObsList);
        Integer diskonId = pesanDiskonCB.getSelectionModel().getSelectedItem().getId();
        Integer pajakId = pesanPajakCB.getSelectionModel().getSelectedItem().getId();
        setGrandTotalPesan(totalHargaPesan, diskonId, pajakId);
//        totalPesanOrdered.setText(numberFormat.format(totalHargaPesan));
    }
    
    @FXML
    private void pesanBayarBtn(ActionEvent actionEvent) {
        try {
            if(iMenuItem.cekStokBayar(pesanMenuItemOrderedObsList, primaryStage)){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(AdminController.class.getResource("Bayar.fxml"));
                BorderPane page = (BorderPane) loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Bayar");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);

                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                BayarController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                Transaksi transaksi = new Transaksi();
                transaksi.setId(transaksiId);
                transaksi.setTotal(hargaGrandTotal);
                if(pesanDiskonCB.getValue().getId() > 0) {
                    transaksi.setDiskonId(new Diskon(pesanDiskonCB.getValue().getId()));
                    transaksi.setDiskonTotal(pesanGlobalDiskon);
                }
                if(pesanPajakCB.getValue().getId() > 0) {
                    transaksi.setPajakId(new Pajak(pesanPajakCB.getValue().getId()));
                    transaksi.setPajakTotal(pesanGlobalPajak);
                }
                transaksi.setUserEnd(loginUser);
                transaksi.setMejaId(new Meja((short) 0));
                transaksi.setModalTotal(totalModalPesan);
                controller.initValue(transaksi, CommonConstant.TRANSAKSI_PESAN, pesanMenuItemOrderedObsList);

                dialogStage.showAndWait();

                if(controller.isOkClicked()){
                    int tunai = controller.getUang();
                    ObservableList<PesanProperty> obsList = FXCollections.observableArrayList();
                    obsList.addAll(pesanMenuItemOrderedObsList);
                    PrintStruk printBayar = new PrintStruk(tunai, loginUser, obsList, hargaGrandTotal, "Walk in Customer", pesanDiskonCB.getValue().getDisplayString(), pesanGlobalDiskon, pesanPajakCB.getValue().getDisplayString(), pesanGlobalPajak);
                    new Thread(printBayar).start();
                    displayKembalian(numberFormat.format(tunai-hargaGrandTotal));

                    transaksiId = 0;

                    displayOrderPesan();
                }
            }
        } catch (IOException ex) {
            LOGGER.error("failed to load Bayar.fxml", ex);
        }
    }

    private void displayKembalian(String kembalian) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminController.class.getResource("Kembalian.fxml"));
            BorderPane page = (BorderPane) loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Bayar");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            KembalianController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.initValue(kembalian);
            
            dialogStage.showAndWait();
        } catch (IOException ex) {
            LOGGER.error("failed to load Kembalian.fxml", ex);
        }
    }
    
    @FXML
    private void pesanSimpanBtn(ActionEvent actionEvent) {
        String namaPemesan = pesanMenuItemOrderedObsList.get(0).getNamaPemesan();
        TextInputDialog dialog = new TextInputDialog(namaPemesan);
        dialog.setTitle("Simpan");
        dialog.setHeaderText("Simpan Sebagai");
        dialog.setHeaderText("Masukkan Nama");

        Optional<String> result = dialog.showAndWait();

        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(nama -> {
            Transaksi transaksi = new Transaksi();
            transaksi.setId(transaksiId);
            transaksi.setNamaPemesan(nama);
            transaksi.setStatus(CommonConstant.TRANSAKSI_TERSIMPAN);
            if(pesanDiskonCB.getValue().getId() > 0){
                transaksi.setDiskonId(new Diskon(pesanDiskonCB.getValue().getId()));
            }
            if(pesanPajakCB.getValue().getId() > 0) {
                transaksi.setPajakId(new Pajak(pesanPajakCB.getValue().getId()));
            }
            transaksi.setTotal(hargaGrandTotal);
            transaksi.setDiskonTotal(pesanGlobalDiskon);
            transaksi.setPajakTotal(pesanGlobalPajak);
            transaksi.setModalTotal(totalModalPesan);
            iTransaksi.updateSimpan(transaksi);
            
            transaksiId = 0;
            displayOrderPesan();
        });
    }
    
    @FXML
    private void pesanBatalBtn(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText("Anda Yakin?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Transaksi transaksi = new Transaksi();
            transaksi.setId(transaksiId);
            transaksi.setStatus(CommonConstant.TRANSAKSI_BATAL);
            transaksi.setTotal(totalHargaPesan);
            Users users = new Users();
            users.setId(loginUser.getId());
            transaksi.setUserEnd(users);
            iTransaksi.updateStatus(transaksi);
            
            transaksiId = 0;
            displayOrderPesan();
        }
    }
    
    @FXML
    private void hapusBtnPesan(ActionEvent actionEvent){
        PesanProperty selectedPesanProp = pesanOrderedTable.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText("Anda Yakin?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Pesan pesan = new Pesan();
            pesan.setId(selectedPesanProp.getId());
            Transaksi transaksi = new Transaksi();
            transaksi.setId(transaksiId);
            pesan.setTransaksiId(transaksi);
//            MenuItem menuItem = new MenuItem();
//            menuItem.setId(selectedPesanProp.getMenuItemId());
//            pesan.setMenuItemId(menuItem);
            pesan.setMenuItemCode(new  MenuItem(selectedPesanProp.getCode()));
            iPesan.deleteById(pesan);
            
            displayOrderPesan();
        }
    }
    
    @FXML
    private void ubahBtnPesan(ActionEvent actionEvent){
        PesanProperty selectedPesanProp = pesanOrderedTable.getSelectionModel().getSelectedItem();
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle(selectedPesanProp.getNama());
        dialog.setHeaderText("Masukkan jumlah barang yang dipesan:");

        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(jumlah -> {
            MenuItem menuItem = iMenuItem.getByCode(selectedPesanProp.getCode());
            Integer stok = menuItem.getStok();
            int sisaStok = stok - Integer.valueOf(jumlah);
            if(menuItem.getStokFlag() && sisaStok < 0) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(primaryStage);
                alert.setTitle("Error!");
                alert.setHeaderText("Stok Tidak Mencukupi");
                alert.setContentText("Stok yang tersedia = " + stok);

                alert.showAndWait();
            }else{
                Transaksi transaksi = new Transaksi(transaksiId);
                List<Object[]> obj = iPesan.getByMenuItemAndTransaksi(menuItem, transaksi);
                if(null != obj && !obj.isEmpty()) {
                    for(Object[] row : obj){
                        Pesan pesan = (Pesan) row[0];
                        pesan.setJumlah(Integer.valueOf(jumlah));
                        iPesan.update(pesan);
                    }
                }
                displayOrderPesan();
            }
            
        });
    }
    
    @FXML
    private void ubahBtnBungkus(ActionEvent actionEvent){
        selectedNamaBungkus = bungkusNamaTable.getSelectionModel().getSelectedItem();

        Transaksi transaksi = new Transaksi();
        transaksi.setId(selectedNamaBungkus.getId());
        transaksi.setStatus(CommonConstant.TRANSAKSI_PESAN);
        iTransaksi.updateStatusOnly(transaksi);
        
        transaksiId = selectedNamaBungkus.getId();

        bungkusNamaTable.getSelectionModel().clearSelection();
        bungkusObsList.remove(selectedNamaBungkus);
        displayOrderBungkus(null);
    }

    private void displayMenuItemEdit(MenuItemProperty newValue) {
        if(null != newValue){
//            MenuItem menuItem = iMenuItem.getById(newValue.getId());
            menuItemKodeTF.setText(newValue.getCode());
            menuItemKodeTF.setDisable(true);
            menuItemNamaTF.setText(newValue.getNama());
            menuItemModalTF.setText(newValue.getModal().toString());
            menuItemUntungCB.getSelectionModel().select(newValue.getUntungCode()-1);
            menuItemUntungTF.setText(newValue.getUntung().toString());
            menuItemTambahanCB.getSelectionModel().select(newValue.getTambahanCode()-1);
            menuItemTambahanTF.setText(newValue.getTambahan().toString());
            menuItemHargaJualTF.setText(newValue.getHargaJual());
            stokCheckBox.setSelected(newValue.getStokFlag());
            if(newValue.getStokFlag()) {
                stokTambahTF.setDisable(false);
            }else{
                stokTambahTF.setDisable(true);
            }
            
        }else{
            menuItemKodeTF.setText("");
            menuItemKodeTF.setDisable(false);
            menuItemNamaTF.setText("");
            menuItemModalTF.setText("");
            menuItemUntungTF.setText("");
            menuItemTambahanTF.setText("");
            menuItemHargaJualTF.setText("0");
            stokCheckBox.setSelected(false);
            stokTambahTF.setText("0");
            stokTambahTF.setDisable(true);
        }
        
    }
    
    @FXML
    private void handleStokCheckBoxAction(ActionEvent event) {
        if(stokCheckBox.isSelected()){
            stokTambahTF.setDisable(false);
        }else{
            stokTambahTF.setDisable(true);
        }
    }

    private void sumHargaJual(Integer modal, Integer untungBefore, Integer tambahanBefore) {
        float untungPersen = (float) untungBefore/100;
        float tambahanPersen = (float) tambahanBefore/100;
        System.out.println("untungPersen = " + untungPersen);
        Integer untung = menuItemUntungCB.getSelectionModel().isSelected(0) ? untungBefore : Math.round(untungPersen*modal);
        Integer tambahan = menuItemTambahanCB.getSelectionModel().isSelected(0) ? tambahanBefore : Math.round(tambahanPersen*(modal+untung));
        Integer hargaJual = modal + untung + tambahan;
        menuItemHargaJualTF.setText(numberFormat.format(hargaJual));
//        menuItemHargaJual.setText(numberFormat.format(hargaJual));
    }
    
    @FXML
    private void batalBtnMenuItem(ActionEvent event) {
        menuItemTable.getSelectionModel().clearSelection();
        displayMenuItemEdit(null);
    }
    
    @FXML
    private void simpanBtnMenuItem(ActionEvent event) {
        MenuProperty selectedMenu = menuTable.getSelectionModel().getSelectedItem();
        MenuItemProperty selectedMenuItem = menuItemTable.getSelectionModel().getSelectedItem();
        MenuItem menuItem = new MenuItem();

        menuItem.setNama(menuItemNamaTF.getText());
        Menu menu = new Menu();
        menu.setId(selectedMenu.getId());
        menuItem.setMenuId(menu);
        menuItem.setModal(Integer.valueOf(menuItemModalTF.getText()));
        menuItem.setUntungCode(menuItemUntungCB.getSelectionModel().getSelectedIndex()+1);
        menuItem.setUntung(Integer.valueOf(menuItemUntungTF.getText()));
        menuItem.setTambahanCode(menuItemTambahanCB.getSelectionModel().getSelectedIndex()+1);
        menuItem.setTambahan(null != menuItemTambahanTF.getText() && !menuItemTambahanTF.getText().isEmpty() ? Integer.valueOf(menuItemTambahanTF.getText()) : 0);
        menuItem.setHargaTotal(Integer.valueOf(menuItemHargaJualTF.getText().replace(".", "")));
        menuItem.setStokFlag(stokCheckBox.isSelected());
        if(stokCheckBox.isSelected()){
            Integer stokTambah = Integer.valueOf(null != stokTambahTF.getText() ? stokTambahTF.getText() : "0");
            Integer stok = 0;
            if(null != selectedMenuItem && null != selectedMenuItem.getStok()){
                stok = Integer.valueOf(selectedMenuItem.getStok());
            }
            menuItem.setStok(stokTambah + stok);
        }else{
            menuItem.setStok(0);
        }
        if(null != selectedMenuItem){
            menuItem.setCode(selectedMenuItem.getCode());
            iMenuItem.update(menuItem);
        }else{
            menuItem.setCode(menuItemKodeTF.getText().toUpperCase());
            if(!iMenuItem.insert(menuItem, primaryStage)){
                return;
            }
//            iMenuItem.insert(menuItem, primaryStage);
        }
        displayEdit(menuTable.getSelectionModel().getSelectedItem());
        batalBtnMenuItem(event);
    }

    private void setPelanggan() {
        //daftarPelanggan
        idColumnPelanggan.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        namaColumnPelanggan.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        statusColumnPelanggan.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        tglColumnPelanggan.setCellValueFactory(cellData -> cellData.getValue().tanggalProperty());
        daftarPelangganPropObsList = iPelanggan.getAllProp();
        daftarPelangganTable.setItems(daftarPelangganPropObsList);
        FilteredList<PelangganProperty> filteredData = new FilteredList<>(daftarPelangganPropObsList, p -> true);
        
        cariPelangganTF.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
                            // If filter text is empty, display all persons.
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }

                            // Compare first name and last name of every person with filter text.
                            String lowerCaseFilter = newValue.toLowerCase();

                            if (person.getId().toLowerCase().contains(lowerCaseFilter)) {
                                return true; // Filter matches first name.
                            } else if (person.getNama().toLowerCase().contains(lowerCaseFilter)) {
                                return true; // Filter matches last name.
                            }
                            return false; // Does not match.
			});
		});
        
        SortedList<PelangganProperty> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(daftarPelangganTable.comparatorProperty());
        daftarPelangganTable.setItems(sortedData);
        
        daftarPelangganTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
                displayPelangganDetail(newValue);
        }));
        
        pelangganSimpanBtn.disableProperty().bind(Bindings.isEmpty(detilPelangganIdTF.textProperty()).or(Bindings.isEmpty(detilPelangganNamaTF.textProperty())));
        pelangganBatalBtn.disableProperty().bind(Bindings.isEmpty(detilPelangganIdTF.textProperty()).or(Bindings.isEmpty(detilPelangganNamaTF.textProperty())));
        
        //riwayat pelanggan
        noColumnRiwayatPelanggan.setCellValueFactory((TableColumn.CellDataFeatures<TransaksiProperty, TransaksiProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumnRiwayatPelanggan.setCellFactory((TableColumn<TransaksiProperty, TransaksiProperty> param) -> new TableCell<TransaksiProperty, TransaksiProperty>() {
            @Override protected void updateItem(TransaksiProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noColumnRiwayatPelanggan.setSortable(false);
        tglColumnRiwayatPelanggan.setCellValueFactory(cellData -> cellData.getValue().tanggalProperty());
        subtotalColumnRiwayatPelanggan.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        
        
    }

    private void displayPelangganDetail(PelangganProperty newValue) {
        int totalRiwayatPelanggan = 0;
        riwayatPelangganPropObsList.clear();
        pelangganTop10.getData().clear();
        caption.setText("");
        
        if(null != newValue){
            //detil pelanggan
            detilPelangganIdTF.setText(newValue.getId());
            detilPelangganIdTF.setDisable(true);
            detilPelangganNamaTF.setText(newValue.getNama());
            detilPelangganAlamat.setText(newValue.getAlamat());
            detilPelangganTelpTF.setText(newValue.getTelp());
            pelangganCheckBox.setSelected(newValue.getStatus());

            //riwayat pelanggan
            Pelanggan pelanggan = new Pelanggan(newValue.getId());
            
            riwayatPelangganPropObsList = iTransaksi.getTransaksiByPelanggan(pelanggan);
            if(null != riwayatPelangganPropObsList){
                for (TransaksiProperty riwayatPelangganPropObsList1 : riwayatPelangganPropObsList) {
                    totalRiwayatPelanggan += riwayatPelangganPropObsList1.getTotal();
                }
            }
            

            //pie chart  
            List resultList = iPesan.getPieChartByPelanggan(pelanggan);
            if(null != resultList && !resultList.isEmpty()) {
                for(Object rows : resultList){
                    Object[] row = (Object[]) rows;
                    String data1 = (String) row[0];
                    Long data2 = (Long) row[1];
                    PieChart.Data slice = new PieChart.Data(data1, data2);
                    pelangganTop10.getData().add(slice);
                }
            }

            

            for (final PieChart.Data data : pelangganTop10.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
    //                        caption.setTranslateX(e.getSceneX());
    //                        caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf((int) data.getPieValue()));
                         }
                    });
            }
        }else{
            //detil pelanggan
            detilPelangganIdTF.setDisable(false);
            detilPelangganIdTF.setText("");
            detilPelangganNamaTF.setText("");
            detilPelangganAlamat.setText("");
            detilPelangganTelpTF.setText("");
            pelangganCheckBox.setSelected(true);
        }
        riwayatPelangganTable.setItems(riwayatPelangganPropObsList);
        riwayatPelangganTotal.setText(numberFormat.format(totalRiwayatPelanggan));

    }
    
    @FXML
    private void detilPelangganBatalBtn(ActionEvent event) {
        daftarPelangganTable.getSelectionModel().clearSelection();
        displayPelangganDetail(null);
    }
    
    @FXML
    private void detilPelangganSimpanBtn(ActionEvent event) {
        PelangganProperty selectedDaftarPelangganProp = daftarPelangganTable.getSelectionModel().getSelectedItem();
        Pelanggan pelanggan = new Pelanggan();
        if(null != selectedDaftarPelangganProp){
            pelanggan.setId(selectedDaftarPelangganProp.getId());
        }else{
            pelanggan.setId(detilPelangganIdTF.getText().toUpperCase());
        }
        pelanggan.setNama(detilPelangganNamaTF.getText());
        pelanggan.setAlamat(detilPelangganAlamat.getText());
        pelanggan.setNoTelp(detilPelangganTelpTF.getText());
//        pelanggan.setIdCard(detilPelangganIdTF.getText());
        pelanggan.setStatus(pelangganCheckBox.isSelected());
        pelanggan.setDtStart(new Date());
        iPelanggan.insertOrUpdate(pelanggan);
        detilPelangganBatalBtn(event);
        daftarPelangganPropObsList.clear();
        daftarPelangganPropObsList = iPelanggan.getAllProp();
        daftarPelangganTable.setItems(daftarPelangganPropObsList);
        displayPelangganDetail(null);
    }

    private void setStokOpname() {
        stokOpnameTahunCB.setItems(iStokOpname.getYear());
        stokOpnameBulanCB.getItems().addAll(CommonConstant.NAMA_BULAN);
        
        stokOpnameTahunCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue){
                if(null == stokOpnameBulanCB.getValue()){
                    stokOpnameBulanCB.getSelectionModel().select(LocalDate.now().getMonthValue()-1);
                }
                setStokOpnameTable(newValue, stokOpnameBulanCB.getSelectionModel().getSelectedIndex()+1);
            }
        });
        stokOpnameBulanCB.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && null != stokOpnameTahunCB.getValue()){
                setStokOpnameTable(stokOpnameTahunCB.getValue(), newValue.intValue()+1);
            }
        });
        
        nomorColumnStokOpname.setCellValueFactory((TableColumn.CellDataFeatures<StokOpnameProperty, StokOpnameProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        nomorColumnStokOpname.setCellFactory((TableColumn<StokOpnameProperty, StokOpnameProperty> param) -> new TableCell<StokOpnameProperty, StokOpnameProperty>() {
            @Override protected void updateItem(StokOpnameProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        nomorColumnStokOpname.setSortable(false);
        tanggalColumnStokOpname.setCellValueFactory(cellData -> cellData.getValue().tanggalProperty());
        namaColumnStokOpname.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        statusColumnStokOpname.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        userNamaColumnStokOpname.setCellValueFactory(cellData -> cellData.getValue().userNamaProperty());
        stokOpnameTahunCB.getSelectionModel().selectFirst();
        
        stokOpnameTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayStokOpnameDetail(newValue);
        });
        
        kodeColumnStokOpnameItem.setCellValueFactory(value -> value.getValue().kodeProperty());
        menuColumnStokOpnameItem.setCellValueFactory(value -> value.getValue().menuNamaProperty());
        itemColumnStokOpnameItem.setCellValueFactory(value -> value.getValue().menuItemNamaProperty());
        stokColumnStokOpnameItem.setCellValueFactory(value -> value.getValue().stokProperty().asObject());
        stokTersediaColumnStokOpnameItem.setCellValueFactory(value -> value.getValue().stokTersediaProperty().asObject());
        selisihColumnStokOpnameItem.setCellValueFactory(value -> value.getValue().selisihProperty().asObject());
        
        TextFields.bindAutoCompletion(stokOpnameItemKodeComboBox.getEditor(), (AutoCompletionBinding.ISuggestionRequest t) -> {
            ObservableList<StokOpnameItemProperty> obsList = stokOpnameItemTable.getItems();
            return iMenuItem.searchMenuItemByCodeOnStokOpname(t.getUserText(), obsList);
        });
        
        stokOpnameItemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            stokOpnameItemDetail(newValue);
        });
    }

    private void setStokOpnameTable(int year, int month) {
        System.out.println("tahun = " + year + " bulan = " + month);
        ObservableList<StokOpnameProperty> dataProp = iStokOpname.getByBulan(year, month);
        stokOpnameTable.setItems(dataProp);
    }

    private void displayStokOpnameDetail(StokOpnameProperty newValue) {
        stokOpnameNamaTF.setDisable(false);
        stokOpnameSimpanBtn.setDisable(false);
        stokOpnameSelesaiBtn.setDisable(false);
        if(null != newValue) {
            stokOpnameNamaTF.setText(newValue.getNama());
            stokOpnameItemTable.setItems(iStokOpnameItem.getByStokOpnameId(newValue));
            if(newValue.getStatus().equalsIgnoreCase("Simpan")){
                stokOpnameHapusBtn.setDisable(false);
            }else{
                stokOpnameHapusBtn.setDisable(true);
                stokOpnameNamaTF.setDisable(true);
                stokOpnameSimpanBtn.setDisable(true);
                stokOpnameSelesaiBtn.setDisable(true);
            }
        }else{
            stokOpnameItemTable.getItems().clear();
            stokOpnameNamaTF.setText("");
        }
        stokOpnameItemDetail(null);
        
    }
    
    @FXML
    private void stokOpnameSimpanAction() {
        TextField[] textFields = {stokOpnameNamaTF};
        String[] namaTextFields = {"Nama"};
        if(iValidation.isTextFieldInputValid(textFields, namaTextFields, primaryStage)){
            StokOpnameProperty selectedProp = stokOpnameTable.getSelectionModel().getSelectedItem();
            StokOpname stokOpname = new StokOpname();
            stokOpname.setNama(stokOpnameNamaTF.getText());
            if(null != selectedProp) {
                selectedProp.setNama(stokOpnameNamaTF.getText());
                iStokOpname.update(stokOpname);
            }else{
                stokOpname.setTanggal(new Date());
                stokOpname.setUserId(loginUser);
                stokOpname.setStatus(Boolean.FALSE);
                iStokOpname.insert(stokOpname);
            }
            setStokOpnameTable(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
            displayStokOpnameDetail(null);
        }
    }
    
    @FXML
    private void stokOpnameBatalAction() {
        stokOpnameTable.getSelectionModel().clearSelection();
        displayStokOpnameDetail(null);
    }
    
    @FXML
    private void stokOpnameSelesaiAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText("Anda Yakin?");
//            alert.setContentText("Anda Yakin?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            StokOpnameProperty stokOpnameProperty = stokOpnameTable.getSelectionModel().getSelectedItem();
            StokOpname stokOpname = new StokOpname();
            stokOpname.setId(stokOpnameProperty.getId());
            stokOpname.setStatus(Boolean.TRUE);
            iStokOpname.updateStatus(stokOpname);
            setStokOpnameTable(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
            ObservableList<StokOpnameItemProperty> obsList = iStokOpnameItem.getByStokOpnameId(stokOpnameProperty);
            iMenuItem.updateStok(obsList);
            displayStokOpnameDetail(null);
        }
    }
    
    @FXML
    private void tambahBaruBtnStokOpname(ActionEvent actionEvent) {
        int stokOpnameId = iStokOpname.insert(new StokOpname());
        loadStokOpnamePage(new StokOpname(stokOpnameId));
        stokOpnameTable.getSelectionModel().selectLast();
    }
    
    @FXML
    private void editBtnStokOpname(ActionEvent event) {
        StokOpnameProperty stokOpnameProperty = stokOpnameTable.getSelectionModel().getSelectedItem();
        int selectedIndex = stokOpnameTable.getSelectionModel().getSelectedIndex();
        StokOpname stokOpname = new StokOpname();
        stokOpname.setId(stokOpnameProperty.getId());
        stokOpname.setNama(stokOpnameProperty.getNama());
        loadStokOpnamePage(stokOpname);
        stokOpnameTable.getSelectionModel().select(selectedIndex);
    }
    
    @FXML
    private void deleteBtnStokOpname() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText("Anda Yakin?");
//            alert.setContentText("Anda Yakin?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            StokOpnameProperty stokOpnameProperty = stokOpnameTable.getSelectionModel().getSelectedItem();
            StokOpname stokOpname = new StokOpname();
            stokOpname.setId(stokOpnameProperty.getId());
            stokOpname.setUserId(new Users(stokOpnameProperty.getUserId()));
            iStokOpname.delete(stokOpname);
            setStokOpnameTable(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        }
    }

    private void loadStokOpnamePage(StokOpname stokOpname) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AdminController.class.getResource("StokOpname.fxml"));
            BorderPane page = (BorderPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Stok Opname");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            StokOpnameController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.iniValue(stokOpname.getId(), stokOpname.getNama(), loginUser);

            dialogStage.showAndWait();

            setStokOpnameTable(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
            

        } catch (IOException ex) {
            LOGGER.error("failed to load Pesan.fxml", ex);
        }
    }

    private void setDiskonPajak() {
        //diskon
        noColumnDiskon.setCellValueFactory((TableColumn.CellDataFeatures<DiskonPajakProperty, DiskonPajakProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumnDiskon.setCellFactory((TableColumn<DiskonPajakProperty, DiskonPajakProperty> param) -> new TableCell<DiskonPajakProperty, DiskonPajakProperty>() {
            @Override protected void updateItem(DiskonPajakProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noColumnDiskon.setSortable(false);
        namaColumnDiskon.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        tipeColumnDiskon.setCellValueFactory(cellData -> cellData.getValue().tipeProperty());
        bilanganColumnDiskon.setCellValueFactory(cellData -> cellData.getValue().bilanganProperty());
        aktifColumnDiskon.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        diskonTable.setItems(iDiskon.getAllProperty());
        diskonTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            diskonDetails(newValue);
        });
        diskonTipeCB.getItems().addAll(CommonConstant.RUPIAH_SIMBOL, CommonConstant.PERSEN_SIMBOL);
        diskonTipeCB.getSelectionModel().selectFirst();
        
        diskonSimpanBtn.disableProperty().bind(Bindings.isEmpty(namaDiskonTF.textProperty()).or(Bindings.isEmpty(bilanganDiskonTF.textProperty())));
        diskonBatalBtn.disableProperty().bind(Bindings.isEmpty(namaDiskonTF.textProperty()).or(Bindings.isEmpty(bilanganDiskonTF.textProperty())));
        
        //pajak
        noColumnPajak.setCellValueFactory((TableColumn.CellDataFeatures<DiskonPajakProperty, DiskonPajakProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumnPajak.setCellFactory((TableColumn<DiskonPajakProperty, DiskonPajakProperty> param) -> new TableCell<DiskonPajakProperty, DiskonPajakProperty>() {
            @Override protected void updateItem(DiskonPajakProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noColumnPajak.setSortable(false);
        namaColumnPajak.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        tipeColumnPajak.setCellValueFactory(cellData -> cellData.getValue().tipeProperty());
        bilanganColumnPajak.setCellValueFactory(cellData -> cellData.getValue().bilanganProperty());
        aktifColumnPajak.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        pajakTable.setItems(iPajak.getAllProperty());
        pajakTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            pajakDetails(newValue);
        });
        pajakTipeCB.getItems().addAll(CommonConstant.RUPIAH_SIMBOL, CommonConstant.PERSEN_SIMBOL);
        pajakTipeCB.getSelectionModel().selectFirst();
        
        pajakSimpanBtn.disableProperty().bind(Bindings.isEmpty(namaPajakTF.textProperty()).or(Bindings.isEmpty(bilanganPajakTF.textProperty())));
        pajakBatalBtn.disableProperty().bind(Bindings.isEmpty(namaPajakTF.textProperty()).or(Bindings.isEmpty(bilanganPajakTF.textProperty())));
    }
    
    @FXML
    private void simpanBtnDiskon(ActionEvent event) {
        Diskon diskon = new Diskon();
        DiskonPajakProperty property = diskonTable.getSelectionModel().getSelectedItem();
        if(null != property){
            diskon.setId(property.getId());
        }
        diskon.setNama(namaDiskonTF.getText());
        diskon.setTipe(diskonTipeCB.getSelectionModel().getSelectedIndex()+1);
        diskon.setBilangan(Integer.valueOf(bilanganDiskonTF.getText()));
        diskon.setStatus(diskonCheckBox.isSelected());
        iDiskon.saveOrupdate(diskon);
        diskonTable.setItems(iDiskon.getAllProperty());
        diskonDetails(null);
    }
    
    @FXML
    private void batalBtnDiskon(ActionEvent event) {
        diskonTable.getSelectionModel().clearSelection();
        diskonDetails(null);
    }
    
    @FXML
    private void simpanBtnPajak(ActionEvent event) {
        Pajak pajak = new Pajak();
        DiskonPajakProperty property = pajakTable.getSelectionModel().getSelectedItem();
        if(null != property){
            pajak.setId(property.getId());
        }
        pajak.setNama(namaPajakTF.getText());
        pajak.setTipe(pajakTipeCB.getSelectionModel().getSelectedIndex()+1);
        pajak.setBilangan(Integer.valueOf(bilanganPajakTF.getText()));
        pajak.setStatus(pajakCheckBox.isSelected());
        iPajak.saveOrupdate(pajak);
        pajakTable.setItems(iPajak.getAllProperty());
        pajakDetails(null);
    }
    
    @FXML
    private void batalBtnPajak(ActionEvent event) {
        pajakTable.getSelectionModel().clearSelection();
        pajakDetails(null);
    }

    private void diskonDetails(DiskonPajakProperty newValue) {
        if(null != newValue) {
            namaDiskonTF.setText(newValue.getNama());
            diskonTipeCB.setValue(newValue.getTipe());
            bilanganDiskonTF.setText(newValue.getBilangan());
            diskonCheckBox.setSelected(newValue.getStatus());
        }else{
            namaDiskonTF.setText("");
            diskonTipeCB.getSelectionModel().selectFirst();
            bilanganDiskonTF.setText("");
            diskonCheckBox.setSelected(Boolean.TRUE);
        }
    }

    private void pajakDetails(DiskonPajakProperty newValue) {
        if(null != newValue) {
            namaPajakTF.setText(newValue.getNama());
            pajakTipeCB.setValue(newValue.getTipe());
            bilanganPajakTF.setText(newValue.getBilangan());
            pajakCheckBox.setSelected(newValue.getStatus());
        }else{
            namaPajakTF.setText("");
            pajakTipeCB.getSelectionModel().selectFirst();
            bilanganPajakTF.setText("");
            pajakCheckBox.setSelected(Boolean.TRUE);
        }
    }

    private void setGrandTotalPesan(Integer totalHargaPesan, Integer diskonId, Integer pajakId) {
        //diskon
        pesanGlobalDiskon = 0;
        if(diskonId > 0){
            Diskon diskon = iDiskon.getById(diskonId);
            if(diskon.getTipe().equals(CommonConstant.RUPIAH_CODE)){
                pesanGlobalDiskon = diskon.getBilangan();
            }else{
                float untungPersen = (float) diskon.getBilangan()/100;
                pesanGlobalDiskon = Math.round(untungPersen*totalHargaPesan);
            }
        }
        
        //pajak
        pesanGlobalPajak = 0;
        if(pajakId > 0) {
            Pajak pajak = iPajak.getById(pajakId);
            if(pajak.getTipe().equals(CommonConstant.RUPIAH_CODE)){
                pesanGlobalPajak = pajak.getBilangan();
            }else{
                float pajakPersen = (float) pajak.getBilangan()/100;
                pesanGlobalPajak = Math.round(pajakPersen*(totalHargaPesan-pesanGlobalDiskon));
            }
        }
        
        hargaGrandTotal = totalHargaPesan - pesanGlobalDiskon + pesanGlobalPajak;
        totalPesanOrdered.setText(numberFormat.format(hargaGrandTotal));
    }

    private void displayPemasukanDetails(TransaksiProperty newValue) {
        if(null != newValue) {
            pemasukanDetailTable.setItems(iPesan.getDetailByTransaksiId(newValue.getId()));
            pemasukanDetailDiskon.setText(newValue.getDiskonNama());
            pemasukanDetailPajak.setText(newValue.getPajakNama());
            pemasukanDetailTotal.setText(numberFormat.format(newValue.getTotal()));
        }else{
            pemasukanDetailTable.getItems().clear();
            pemasukanDetailDiskon.setText("");
            pemasukanDetailPajak.setText("");
            pemasukanDetailTotal.setText("0");
        }
    }

    private void setMetodePembayaran() {
        metodePembayaranNoClmn.setCellValueFactory((TableColumn.CellDataFeatures<MetodePembayaranProperty, MetodePembayaranProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        metodePembayaranNoClmn.setCellFactory((TableColumn<MetodePembayaranProperty, MetodePembayaranProperty> param) -> new TableCell<MetodePembayaranProperty, MetodePembayaranProperty>() {
            @Override protected void updateItem(MetodePembayaranProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        metodePembayaranNoClmn.setSortable(false);
        metodePembayaranNamaClmn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        metodePembayaranStatusClmn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        metodePembayaranTable.setItems(iMetodePembayaran.getAllProperty());
        metodePembayaranTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayMetodePembayaranDetail(newValue);
        });
        
        metodePembayaranSimpanBtn.disableProperty().bind(Bindings.isEmpty(metodePembayaranNamaTF.textProperty()));
        metodePembayaranBatalBtn.disableProperty().bind(Bindings.isEmpty(metodePembayaranNamaTF.textProperty()));
    }

    private void displayMetodePembayaranDetail(MetodePembayaranProperty newValue) {
        if(null != newValue) {
            metodePembayaranNamaTF.setText(newValue.getNama());
            metodePembayaranCheckBox.setSelected(newValue.getStatus());
        }else{
            metodePembayaranNamaTF.setText("");
            metodePembayaranCheckBox.setSelected(true);
        }
    }
    
    @FXML
    private void simpanBtnMetodePembayaran(ActionEvent actionEvent) {
        MetodePembayaranProperty selected = metodePembayaranTable.getSelectionModel().getSelectedItem();
        MetodePembayaran metodePembayaran = new MetodePembayaran();
        if(null != selected){
            metodePembayaran.setId(selected.getId());
        }
        metodePembayaran.setNama(metodePembayaranNamaTF.getText());
        metodePembayaran.setStatus(metodePembayaranCheckBox.isSelected());
        iMetodePembayaran.insertOrUpdate(metodePembayaran);
        metodePembayaranTable.setItems(iMetodePembayaran.getAllProperty());
        displayMetodePembayaranDetail(null);
    }
    
    @FXML
    private void batalBtnMetodePembayaran(ActionEvent actionEvent) {
        metodePembayaranTable.getSelectionModel().clearSelection();
        displayMetodePembayaranDetail(null);
    }

    private void setAbsensi() {
        namaColumnAbsensi.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        masukColumnAbsensi.setCellValueFactory(cellData -> cellData.getValue().masukProperty());
        keluarColumnAbsensi.setCellValueFactory(cellData -> cellData.getValue().keluarProperty());
        totalColumnAbsensi.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        
        absensiDate.setValue(LocalDate.now());
        absensiDate.setConverter(new StringConverter<LocalDate>(){
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
    
    @FXML
    private void pilihAbsensiDatePicker() {
        absensiTable.setItems(iAbsensi.getByDate(absensiDate.getValue()));
    }
    
    public void setAbsensiValue() {
        pilihAbsensiDatePicker();
    }
    
    public void keyTyped(KeyEvent keyEvent) {
        long now = Instant.now().toEpochMilli();

        // events must come fast enough to separate from manual input
        if (now - this.lastEventTimeStamp > 100) {
            barcode.delete(0, barcode.length());
        }
        this.lastEventTimeStamp = now;

        // ENTER comes as 0x000d
        if (keyEvent.getCharacter().charAt(0) == (char) 0x000d) {
            if (barcode.length() >= 8) {
                addNewOrder(barcode.toString(), 1);
            }
            barcode.delete(0, barcode.length());
        } else {
            barcode.append(keyEvent.getCharacter());
        }
        keyEvent.consume();
    }
    
    private void addNewOrder(String code, Integer jumlah) {
        MenuItem menuItem = iMenuItem.getByCode(code);
        Integer stok = menuItem.getStok();
        int sisaStok = stok - jumlah;
        if(menuItem.getStokFlag() && sisaStok < 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(primaryStage);
            alert.setTitle("Error!");
            alert.setHeaderText("Stok Tidak Mencukupi");
            alert.setContentText("Stok yang tersedia = " + stok);

            alert.showAndWait();
        }else{
            if(transaksiId == 0){
                transaksiId = iTransaksi.insert(loginUser.getId());
            }
            Transaksi transaksi = new Transaksi(transaksiId);
            Pesan pesan = new Pesan();
            pesan.setModal(menuItem.getModal());
            Integer untung;
            if(Objects.equals(menuItem.getUntungCode(), CommonConstant.RUPIAH_CODE)) {
                untung = menuItem.getUntung();
            }else{
                float untungPersen = (float) menuItem.getUntung()/100;
                untung = Math.round(untungPersen*menuItem.getModal());
            }
            pesan.setUntung(untung);
            Integer tambahan;
            if(Objects.equals(menuItem.getTambahanCode(), CommonConstant.RUPIAH_CODE)) {
                tambahan = menuItem.getTambahan();
            }else{
                float tambahanPersen = (float) menuItem.getTambahan()/100;
                tambahan = Math.round(tambahanPersen*(menuItem.getModal()+untung));
            }
            pesan.setTambahan(tambahan);
            List<Object[]> obj = iPesan.getByMenuItemAndTransaksi(menuItem, transaksi);
            if(null != obj && !obj.isEmpty()) {
                for(Object[] row : obj){
                    pesan = (Pesan) row[0];
                    pesan.setJumlah(pesan.getJumlah()+jumlah);

                    iPesan.update(pesan);
                }
            }else{
                pesan.setMenuItemCode(menuItem);
                pesan.setTransaksiId(transaksi);
                pesan.setJumlah(jumlah);
                iPesan.insert(pesan);
            }
            displayOrderPesan();
        }
        
    }
    
    @FXML
    private void kembaliBtnPesan() {
        pesanMenuItemBox.setVisible(false);
        pesanMenuBox.setVisible(true);
    }
    
    @FXML
    private void pilihMenuItemBtnPesan() {
        MenuItemProperty selectedProp = pesanMenuItemTable.getSelectionModel().getSelectedItem();
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Pesan");
        dialog.setHeaderText("Jumlah");
        dialog.setContentText("Masukkan jumlah barang yang dipesan:");

        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent((String jumlah) -> {
            addNewOrder(selectedProp.getCode(), Integer.valueOf(jumlah));
        });
    }

    private void stokOpnameItemDetail(StokOpnameItemProperty newValue) {
        StokOpnameProperty opnameProperty = stokOpnameTable.getSelectionModel().getSelectedItem();
        if(null != opnameProperty && opnameProperty.getStatus().equalsIgnoreCase("Simpan")) {
            
            stokOpnameItemTersediaTF.setDisable(false);
            stokOpnameItemHapusBtn.setDisable(false);
            stokOpnameItemSimpanBtn.setDisable(false);
            stokOpnameItemBatalBtn.setDisable(false);
            stokOpnameItemKet.setDisable(false);
            if(null != newValue) {
                stokOpnameItemKodeComboBox.setValue(newValue.getKode() + " - " + newValue.getMenuNama() + " " + newValue.getMenuItemNama());
                stokOpnameItemTersediaTF.setText(newValue.getStokTersedia().toString());
                stokOpnameItemKet.setText(newValue.getKet());
                stokOpnameItemKodeComboBox.setDisable(true);
            }else{
                stokOpnameItemKodeComboBox.setValue(null);
                stokOpnameItemTersediaTF.setText("");
                stokOpnameItemKet.setText("");
                stokOpnameItemKodeComboBox.setDisable(false);
            }
        }else{
            if(null != newValue) {
                stokOpnameItemKodeComboBox.setValue(newValue.getKode() + " - " + newValue.getMenuNama() + " " + newValue.getMenuItemNama());
                stokOpnameItemTersediaTF.setText(newValue.getStokTersedia().toString());
                stokOpnameItemKet.setText(newValue.getKet());
            }else{
                stokOpnameItemKodeComboBox.setValue(null);
                stokOpnameItemTersediaTF.setText("");
                stokOpnameItemKet.setText("");
            }
            stokOpnameItemKodeComboBox.setDisable(true);
            stokOpnameItemTersediaTF.setDisable(true);
            stokOpnameItemHapusBtn.setDisable(true);
            stokOpnameItemSimpanBtn.setDisable(true);
            stokOpnameItemBatalBtn.setDisable(true);
            stokOpnameItemKet.setDisable(true);
        }
        
    }
    
    @FXML
    private void stokOpnameItemBatalAction() {
        stokOpnameItemTable.getSelectionModel().clearSelection();
        stokOpnameItemDetail(null);
    }
    
    @FXML
    private void stokOpnameItemSimpanAction() {
        TextField[] textFields = {stokOpnameItemTersediaTF};
        String[] namaTextFields = {"Tersedia"};
        List<ComboBox<String>> comboBoxs = new ArrayList<>();
        comboBoxs.add(stokOpnameItemKodeComboBox);
        String[] namaComboBoxs = {"Kode"};
        if(iValidation.isTextFieldInputValidv2(textFields, namaTextFields, comboBoxs, namaComboBoxs, primaryStage)){
            StokOpnameProperty opnameProperty = stokOpnameTable.getSelectionModel().getSelectedItem();
            StokOpnameItemProperty selectedProp = stokOpnameItemTable.getSelectionModel().getSelectedItem();
            StokOpnameItem opnameItem = new StokOpnameItem();
            opnameItem.setStokTersedia(Integer.valueOf(stokOpnameItemTersediaTF.getText()));
            opnameItem.setKet(stokOpnameItemKet.getText());
            if(null != selectedProp) {
                opnameItem.setId(selectedProp.getId());
                opnameItem.setStokSelisih(opnameItem.getStokTersedia() - selectedProp.getStok());
                iStokOpnameItem.update(opnameItem);
            }else{
                int iend = stokOpnameItemKodeComboBox.getValue().indexOf("-");
                if(iend != -1) {
                    String menuItemCode = stokOpnameItemKodeComboBox.getValue().substring(0, iend).trim();
                    MenuItem item = iMenuItem.getByCode(menuItemCode);
                    opnameItem.setMenuItemCode(item);
                    opnameItem.setStokAwal(item.getStok());
                    opnameItem.setStokSelisih(opnameItem.getStokTersedia() - opnameItem.getStokAwal());
                    
                    opnameItem.setStokOpnameId(new StokOpname(opnameProperty.getId()));
                    iStokOpnameItem.insert(opnameItem);
                }else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.initOwner(primaryStage);
                    alert.setTitle("Salah!");
                    alert.setHeaderText("Kode Salah");
                    alert.setContentText("Silahkan masukkan kode yang benar");

                    alert.showAndWait();
                    
                    return;
                }
            }
            displayStokOpnameDetail(opnameProperty);
        }
    }
    
    @FXML
    private void stokOpnameItemHapusAction() {
        StokOpnameItemProperty selectedProp = stokOpnameItemTable.getSelectionModel().getSelectedItem();
        StokOpnameProperty opnameProperty = stokOpnameTable.getSelectionModel().getSelectedItem();
        if(null != selectedProp){
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Konfirmasi");
            alertConfirm.setHeaderText("Anda Yakin?");

            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK){
                iStokOpnameItem.delete(new StokOpnameItem(selectedProp.getId()));
                displayStokOpnameDetail(opnameProperty);
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

    private void usersDetail(UsersProperty newValue) {
        if(null != newValue){
            usersIdTF.setText(newValue.getId());
            usersIdTF.setDisable(true);
            namaUsersTF.setText(newValue.getNama());
            userNameUsersTF.setText(newValue.getUserName());
            passwordUsersTF.setText(newValue.getPassword());
            usersStatusCheckBox.setSelected(newValue.getStatus());
            if(newValue.getPosisiId() != CommonConstant.USER_SUPER){
                namaUsersTF.setDisable(false);
                posisiUsers.setDisable(false);
                posisiUsers.setValue(newValue.getPosisiNama());
                usersStatusCheckBox.setDisable(false);
            }else{
                namaUsersTF.setDisable(true);
                posisiUsers.setValue(newValue.getPosisiNama());
                posisiUsers.setDisable(true);
                usersStatusCheckBox.setDisable(true);
            }
        }else{
            usersIdTF.setDisable(false);
            usersIdTF.setText("");
            namaUsersTF.setText("");
            userNameUsersTF.setText("");
            passwordUsersTF.setText("");
            posisiUsers.setValue(null);
            usersStatusCheckBox.setSelected(true);
        }
    }

    private void kalkulatorZakat() {
        //bulanan
//        Month bulan = LocalDate.now().minusMonths(1).getMonth();
        YearMonth yearMonth = YearMonth.now().minusMonths(1);
        zakatBulanNama.setText(yearMonth.getMonth().getDisplayName(TextStyle.FULL, new Locale("id", "ID")) + " " + yearMonth.getYear());
        
        
        Laporan laporan = iTransaksi.getlabaRugi(yearMonth);
        if(null != laporan.getUntung()){
            zakatBulanLabaRugi.setText(numberFormat.format(laporan.getUntung()));
        }else{
            zakatBulanLabaRugi.setText("0");
        }
        rumusZakatBulanan("0", "0", "0", "0", "0");
        
        zakatBulanModalTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && !zakatBulanModalTF.getText().isEmpty()) {
                String input = newValue.replaceAll("[^\\d.]", "");
                int angka = Integer.parseInt(input.replace(".", ""));
                String format = numberFormat.format(angka);
                zakatBulanModalTF.setText(format);
                rumusZakatBulanan(input, zakatBulanLabaTF.getText(), zakatBulanPiutangTF.getText(), zakatBulanRugiTF.getText(), zakatBulanHutangTF.getText());
            }
            else{
                zakatBulanModalTF.setText("0");
//                rumusZakat("0", zakatLabaTF.getText(), zakatPiutangTF.getText(), zakatRugiTF.getText(), zakatHutangTF.getText());
            }
        });
        zakatBulanLabaTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && !zakatBulanLabaTF.getText().isEmpty()) {
                String input = newValue.replaceAll("[^\\d.]", "");
                int angka = Integer.parseInt(input.replace(".", ""));
                String format = numberFormat.format(angka);
                zakatBulanLabaTF.setText(format);
                rumusZakatBulanan(zakatBulanModalTF.getText(), input, zakatBulanPiutangTF.getText(), zakatBulanRugiTF.getText(), zakatBulanHutangTF.getText());
            }else{
                zakatBulanLabaTF.setText("0");
//                rumusZakat(zakatModalTF.getText(), "0", zakatPiutangTF.getText(), zakatRugiTF.getText(), zakatHutangTF.getText());
            }
        });
        zakatBulanPiutangTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && !zakatBulanPiutangTF.getText().isEmpty()) {
                String input = newValue.replaceAll("[^\\d.]", "");
                int angka = Integer.parseInt(input.replace(".", ""));
                String format = numberFormat.format(angka);
                zakatBulanPiutangTF.setText(format);
                rumusZakatBulanan(zakatBulanModalTF.getText(), zakatBulanLabaTF.getText(), input, zakatBulanRugiTF.getText(), zakatBulanHutangTF.getText());
            }else{
                zakatBulanPiutangTF.setText("0");
//                rumusZakat(zakatModalTF.getText(), zakatPiutangTF.getText(), "0", zakatRugiTF.getText(), zakatHutangTF.getText());
            }
        });
        zakatBulanRugiTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && !zakatBulanRugiTF.getText().isEmpty()) {
                String input = newValue.replaceAll("[^\\d.]", "");
                int angka = Integer.parseInt(input.replace(".", ""));
                String format = numberFormat.format(angka);
                zakatBulanRugiTF.setText(format);
                rumusZakatBulanan(zakatBulanModalTF.getText(), zakatBulanLabaTF.getText(), zakatBulanPiutangTF.getText(), input, zakatBulanHutangTF.getText());
            }else{
                zakatBulanRugiTF.setText("0");
//                rumusZakat(zakatModalTF.getText(), zakatPiutangTF.getText(), zakatRugiTF.getText(), "0", zakatHutangTF.getText());
            }
        });
        zakatBulanHutangTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && !zakatBulanHutangTF.getText().isEmpty()) {
                String input = newValue.replaceAll("[^\\d.]", "");
                int angka = Integer.parseInt(input.replace(".", ""));
                String format = numberFormat.format(angka);
                zakatBulanHutangTF.setText(format);
                rumusZakatBulanan(zakatBulanModalTF.getText(), zakatBulanLabaTF.getText(), zakatBulanPiutangTF.getText(), zakatBulanRugiTF.getText(), input);
            }else{
                zakatBulanHutangTF.setText("0");
//                rumusZakat(zakatModalTF.getText(), zakatPiutangTF.getText(), zakatRugiTF.getText(), zakatHutangTF.getText(), "0");
            }
        });
        
        
        //tahunan
        HijrahDate lastYearDateH = HijrahDate.from(LocalDate.now()).minus(1, ChronoUnit.YEARS).plus(1, ChronoUnit.DAYS);
        zakatDate.setValue(LocalDate.from(lastYearDateH));
        tanggalZakatPicked();
        final Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker datePicker1) -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                
                if (item.isAfter(LocalDate.from(lastYearDateH))) {
                    setDisable(true);
                }
            }
        };
        zakatDate.setDayCellFactory(dayCellFactory);
        zakatDate.setConverter(new StringConverter<LocalDate>(){
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
        
        zakatModalTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && !zakatModalTF.getText().isEmpty()) {
                String input = newValue.replaceAll("[^\\d.]", "");
                int angka = Integer.parseInt(input.replace(".", ""));
                String format = numberFormat.format(angka);
                zakatModalTF.setText(format);
                rumusZakat(input, zakatLabaTF.getText(), zakatPiutangTF.getText(), zakatRugiTF.getText(), zakatHutangTF.getText());
            }
            else{
                zakatModalTF.setText("0");
//                rumusZakat("0", zakatLabaTF.getText(), zakatPiutangTF.getText(), zakatRugiTF.getText(), zakatHutangTF.getText());
            }
        });
        zakatLabaTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && !zakatLabaTF.getText().isEmpty()) {
                String input = newValue.replaceAll("[^\\d.]", "");
                int angka = Integer.parseInt(input.replace(".", ""));
                String format = numberFormat.format(angka);
                zakatLabaTF.setText(format);
                rumusZakat(zakatModalTF.getText(), input, zakatPiutangTF.getText(), zakatRugiTF.getText(), zakatHutangTF.getText());
            }else{
                zakatLabaTF.setText("0");
//                rumusZakat(zakatModalTF.getText(), "0", zakatPiutangTF.getText(), zakatRugiTF.getText(), zakatHutangTF.getText());
            }
        });
        zakatPiutangTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && !zakatPiutangTF.getText().isEmpty()) {
                String input = newValue.replaceAll("[^\\d.]", "");
                int angka = Integer.parseInt(input.replace(".", ""));
                String format = numberFormat.format(angka);
                zakatPiutangTF.setText(format);
                rumusZakat(zakatModalTF.getText(), zakatLabaTF.getText(), input, zakatRugiTF.getText(), zakatHutangTF.getText());
            }else{
                zakatPiutangTF.setText("0");
//                rumusZakat(zakatModalTF.getText(), zakatPiutangTF.getText(), "0", zakatRugiTF.getText(), zakatHutangTF.getText());
            }
        });
        zakatRugiTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && !zakatRugiTF.getText().isEmpty()) {
                String input = newValue.replaceAll("[^\\d.]", "");
                int angka = Integer.parseInt(input.replace(".", ""));
                String format = numberFormat.format(angka);
                zakatRugiTF.setText(format);
                rumusZakat(zakatModalTF.getText(), zakatLabaTF.getText(), zakatPiutangTF.getText(), input, zakatHutangTF.getText());
            }else{
                zakatRugiTF.setText("0");
//                rumusZakat(zakatModalTF.getText(), zakatPiutangTF.getText(), zakatRugiTF.getText(), "0", zakatHutangTF.getText());
            }
        });
        zakatHutangTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && !zakatHutangTF.getText().isEmpty()) {
                String input = newValue.replaceAll("[^\\d.]", "");
                int angka = Integer.parseInt(input.replace(".", ""));
                String format = numberFormat.format(angka);
                zakatHutangTF.setText(format);
                rumusZakat(zakatModalTF.getText(), zakatLabaTF.getText(), zakatPiutangTF.getText(), zakatRugiTF.getText(), input);
            }else{
                zakatHutangTF.setText("0");
//                rumusZakat(zakatModalTF.getText(), zakatPiutangTF.getText(), zakatRugiTF.getText(), zakatHutangTF.getText(), "0");
            }
        });
        zakatHargaEmasTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && !zakatHargaEmasTF.getText().isEmpty()) {
                String input = newValue.replaceAll("[^\\d.]", "");
                int angka = Integer.parseInt(input.replace(".", ""));
                String format = numberFormat.format(angka);
                zakatHargaEmasTF.setText(format);
                int nishab = 85 * angka;
                zakatNishab.setText(numberFormat.format(nishab));
                if(hartaTot >= nishab) {
                    zakatTotal.setText(numberFormat.format(zakatTot));
                    zakatBayarZAkat.setText("Ya");
                }else{
                    zakatTotal.setText("0");
                    zakatBayarZAkat.setText("Tidak");
                }
            }else{
                zakatHargaEmasTF.setText("0");
//                rumusZakat(zakatModalTF.getText(), zakatPiutangTF.getText(), zakatRugiTF.getText(), zakatHutangTF.getText(), "0");
            }
        });
    }
    
    @FXML
    private void tanggalZakatPicked() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
        HijrahDate tglAwalH = HijrahDate.from(zakatDate.getValue());
        HijrahDate tglAkhirH = tglAwalH.plus(1, ChronoUnit.YEARS).minus(1, ChronoUnit.DAYS);
        LocalDate tglAkhirM = LocalDate.from(tglAkhirH);
        zakatTglAwalHijrah.setText(tglAwalH.format(dateFormatter));
        zakatTglAkhirHijrah.setText(tglAkhirH.format(dateFormatter));
        zakatTglAkhirMasehi.setText(tglAkhirM.format(dateFormatter));
        Laporan laporan = iTransaksi.getlabaRugi(zakatDate.getValue(), tglAkhirM);
        if(null != laporan.getUntung()) {
            zakatLabaRugi.setText(numberFormat.format(laporan.getUntung()));
        }
    }

    private void rumusZakat(String text, String text0, String text1, String text2, String text3) {
        int labaRugi = Integer.parseInt(zakatLabaRugi.getText().replace(".", ""));
        int modal = Integer.parseInt(text.replace(".", ""));
        int laba = Integer.parseInt(text0.replace(".", ""));
        int piutang = Integer.parseInt(text1.replace(".", ""));
        int rugi = Integer.parseInt(text2.replace(".", ""));
        int hutang = Integer.parseInt(text3.replace(".", ""));
        hartaTot = labaRugi + modal + laba + piutang - rugi - hutang;
        double zakat = hartaTot * (2.5/100);
        zakatTot = Math.round(zakat);
        if(hartaTot >= Integer.parseInt(zakatNishab.getText().replace(".", ""))) {
            zakatTotal.setText(numberFormat.format(zakatTot));
            zakatBayarZAkat.setText("Ya");
        }else{
            zakatTotal.setText("0");
            zakatBayarZAkat.setText("Tidak");
        }
    }
    
    private void rumusZakatBulanan(String text, String text0, String text1, String text2, String text3) {
        int labaRugi = Integer.parseInt(zakatBulanLabaRugi.getText().replace(".", ""));
        int modal = Integer.parseInt(text.replace(".", ""))/12;
        int laba = Integer.parseInt(text0.replace(".", ""));
        int piutang = Integer.parseInt(text1.replace(".", ""));
        int rugi = Integer.parseInt(text2.replace(".", ""));
        int hutang = Integer.parseInt(text3.replace(".", ""));
        int hartaTotBulanan = labaRugi + modal + laba + piutang - rugi - hutang;
        double zakat = hartaTotBulanan * (2.577/100);
        long zakatTotBulanan = Math.round(zakat);
//        if(hartaTot >= Integer.parseInt(zakatNishab.getText().replace(".", ""))) {
            zakatBulanTotal.setText(numberFormat.format(zakatTotBulanan));
//            zakatBayarZAkat.setText("Ya");
//        }else{
//            zakatTotal.setText("0");
//            zakatBayarZAkat.setText("Tidak");
//        }
    }

    private void setMeja() {
        getMejaStatus();
        
        //set menu table
        noMenuColumnMeja.setCellValueFactory((TableColumn.CellDataFeatures<MenuProperty, MenuProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noMenuColumnMeja.setCellFactory((TableColumn<MenuProperty, MenuProperty> param) -> new TableCell<MenuProperty, MenuProperty>() {
            @Override protected void updateItem(MenuProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noMenuColumnMeja.setSortable(false);
        namaMenuColumnMeja.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        
        //set BoxChoice
        jenisMejaCB.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Choice> observable, Choice oldValue, Choice newValue) -> {
            if(null != newValue){
                mejaMenuTable.setItems(iMenu.getActiveProperty(newValue.getId()));
            }
        });
        setMejaCB();
        
        //set menu item table
        noColumnMenuItemMeja.setCellValueFactory((TableColumn.CellDataFeatures<MenuItemProperty, MenuItemProperty> p) -> new ReadOnlyObjectWrapper(p.getValue()));
        noColumnMenuItemMeja.setCellFactory((TableColumn<MenuItemProperty, MenuItemProperty> param) -> new TableCell<MenuItemProperty, MenuItemProperty>() {
            @Override protected void updateItem(MenuItemProperty item, boolean empty) {
                super.updateItem(item, empty);
                if (this.getTableRow() != null && item != null) {
                    setText(this.getTableRow().getIndex()+1+"");
                } else {
                    setText("");
                }
            }
        });
        noColumnMenuItemMeja.setSortable(false);
        namaColumnMenuItemMeja.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        
        //set button
        mejaMenuTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue) {
                mejaItemBtn.setDisable(false);
            }else{
                mejaItemBtn.setDisable(true);
            }
        });
        
        mejaMenuItemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue) {
                mejaPesanBtn.setDisable(false);
            }else{
                mejaPesanBtn.setDisable(true);
            }
        });
        
        //set search combo box
        TextFields.bindAutoCompletion(mejaSearchmenuItemComboBox.getEditor(), (AutoCompletionBinding.ISuggestionRequest t) -> {
            return iMenuItem.searchMenuItemByCode(t.getUserText());
        });
        mejaSearchmenuItemComboBox.getSelectionModel().selectedItemProperty().addListener(((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(null != newValue) {
                int iend = newValue.indexOf("-");
                if(iend != -1) {
                    String subString = newValue.substring(0, iend);
                    String code = subString.trim();
                    mejaSearchmenuItemComboBox.getEditor().setText(null);

                    TextInputDialog dialog = new TextInputDialog("1");
                    dialog.setTitle(newValue);
                    dialog.setHeaderText("Jumlah");
                    dialog.setContentText("Masukkan jumlah barang yang dipesan:");

                    Optional<String> result = dialog.showAndWait();
                    // The Java 8 way to get the response value (with lambda expression).
                    result.ifPresent((String jumlah) -> {
                        
                        addNewOrderMeja(code, Integer.valueOf(jumlah));
                        
                    });
                }
            }
        }));
        
        //set ordered table
        menuNamaColumnMeja.setCellValueFactory(cellData -> cellData.getValue().menuNamaProperty());
        menuItemNamaColumnMeja.setCellValueFactory(cellData -> cellData.getValue().menuItemNamaProperty());
        jumlahColumnMeja.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty().asObject());
        
        //set diskon & pajak Choice Box
//        mejaDiskonCB.setItems(iDiskon.getAllActive());
//        mejaDiskonCB.getSelectionModel().selectFirst();
        mejaDiskonCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && null != newValue.getId() && null != mejaPajakCB.getSelectionModel().getSelectedItem()) {
                Integer diskonId = newValue.getId();
                Integer pajakId = mejaPajakCB.getSelectionModel().getSelectedItem().getId();
                setGrandTotalMeja(totalHargaMeja, diskonId, pajakId);
            }
        });
//        mejaPajakCB.setItems(iPajak.getAllActive());
//        mejaPajakCB.getSelectionModel().selectFirst();
        mejaPajakCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue && null != newValue.getId() && null != mejaDiskonCB.getSelectionModel().getSelectedItem()) {
                Integer diskonId = mejaDiskonCB.getSelectionModel().getSelectedItem().getId();
                Integer pajakId = newValue.getId();
                setGrandTotalMeja(totalHargaMeja, diskonId, pajakId);
            }
        });
        
        listMenuMeja.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null != newValue) {
                mejaUbahBtn.setDisable(false);
                mejaHapusBtn.setDisable(false);
            }else{
                mejaUbahBtn.setDisable(true);
                mejaHapusBtn.setDisable(true);
            }
        });
    }
    
    private void setMejaCB() {
        jenisMejaCB.getItems().clear();
        jenisMejaCB.getItems().add(new Choice(0, "Semua"));
        jenisMejaCB.getItems().addAll(iJenis.getAllActiveChoice());
        jenisMejaCB.getSelectionModel().selectFirst();
    }
    
    @FXML
    private void mejaItemAction(ActionEvent actionEvent){
        MenuProperty selectedMenuProp = mejaMenuTable.getSelectionModel().getSelectedItem();
        mejaMenuItemTable.setItems(iMenuItem.getPropertyByMenuId(selectedMenuProp.getId()));
        mejaMenuBox.setVisible(false);
        mejaMenuItemBox.setVisible(true);
    }
    
    @FXML
    private void mejaPesanAction() {
        MenuItemProperty selectedProp = mejaMenuItemTable.getSelectionModel().getSelectedItem();
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Pesan");
        dialog.setHeaderText("Jumlah");
        dialog.setContentText("Masukkan jumlah barang yang dipesan:");

        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent((String jumlah) -> {
            addNewOrderMeja(selectedProp.getCode(), Integer.valueOf(jumlah));
        });
    }
    
    private void addNewOrderMeja(String code, Integer jumlah) {
        MenuItem menuItem = iMenuItem.getByCode(code);
        Integer stok = menuItem.getStok();
        int sisaStok = stok - jumlah;
        if(menuItem.getStokFlag() && sisaStok < 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(primaryStage);
            alert.setTitle("Error!");
            alert.setHeaderText("Stok Tidak Mencukupi");
            alert.setContentText("Stok yang tersedia = " + stok);

            alert.showAndWait();
        }else{
            if(transaksiIdMeja == 0){
                transaksiIdMeja = iTransaksi.insertByMeja(mejaActive, loginUser.getId());
            }
            Transaksi transaksi = new Transaksi(transaksiIdMeja);
            Pesan pesan = new Pesan();
            pesan.setModal(menuItem.getModal());
            Integer untung;
            if(Objects.equals(menuItem.getUntungCode(), CommonConstant.RUPIAH_CODE)) {
                untung = menuItem.getUntung();
            }else{
                float untungPersen = (float) menuItem.getUntung()/100;
                untung = Math.round(untungPersen*menuItem.getModal());
            }
            pesan.setUntung(untung);
            Integer tambahan;
            if(Objects.equals(menuItem.getTambahanCode(), CommonConstant.RUPIAH_CODE)) {
                tambahan = menuItem.getTambahan();
            }else{
                float tambahanPersen = (float) menuItem.getTambahan()/100;
                tambahan = Math.round(tambahanPersen*(menuItem.getModal()+untung));
            }
            pesan.setTambahan(tambahan);
            List<Object[]> obj = iPesan.getByMenuItemAndTransaksi(menuItem, transaksi);
            if(null != obj && !obj.isEmpty()) {
                for(Object[] row : obj){
                    pesan = (Pesan) row[0];
                    pesan.setJumlah(pesan.getJumlah()+jumlah);

                    iPesan.update(pesan);
                }
            }else{
                pesan.setMenuItemCode(menuItem);
                pesan.setTransaksiId(transaksi);
                pesan.setJumlah(jumlah);
                iPesan.insert(pesan);
            }
            displayOrderedMeja();
        }
        
    }
    
    @FXML
    private void mejaMenuAction() {
        mejaMenuItemBox.setVisible(false);
        mejaMenuBox.setVisible(true);
    }

    private void displayOrderedMeja() {
        menuMejaObsList.clear();
        menuMejaObsList =  iTransaksi.getbyMeja(mejaActive);
        listMenuMeja.setItems(menuMejaObsList);
        totalHargaMeja = 0;
        totalModalMeja = 0;
        mejaDiskonCB.getItems().clear();
        mejaDiskonCB.setItems(iDiskon.getAllActive());
        mejaPajakCB.getItems().clear();
        mejaPajakCB.setItems(iPajak.getAllActive());
        if(null != menuMejaObsList && !menuMejaObsList.isEmpty()) {
            for(int i=0; i<menuMejaObsList.size(); i++){
                totalHargaMeja += menuMejaObsList.get(i).getTotalHarga();
                totalModalMeja += menuMejaObsList.get(i).getTotalModal();
            }
            mejaDiskonCB.setValue(new Choice(menuMejaObsList.get(0).getDiskonId(), menuMejaObsList.get(0).getDiskonNama()));
            mejaPajakCB.setValue(new Choice(menuMejaObsList.get(0).getPajakId(), menuMejaObsList.get(0).getPajakNama()));
            
            mejaDiskonCB.setDisable(false);
            mejaPajakCB.setDisable(false);
            
            mejaBayarBtn.setDisable(false);
            mejaBatalBtn.setDisable(false);
            mejaPindahBtn.setDisable(false);
//            mejaUbahBtn.setDisable(false);
//            mejaHapusBtn.setDisable(false);
            mejaCetakBtn.setDisable(false);
            
            transaksiIdMeja = menuMejaObsList.get(0).getTransaksiId();
        }else{
            mejaDiskonCB.getSelectionModel().selectFirst();
            mejaPajakCB.getSelectionModel().selectFirst();
            
            mejaDiskonCB.setDisable(true);
            mejaPajakCB.setDisable(true);
            
            mejaBayarBtn.setDisable(true);
            mejaBatalBtn.setDisable(true);
            mejaPindahBtn.setDisable(true);
//            mejaUbahBtn.setDisable(true);
//            mejaHapusBtn.setDisable(true);
            mejaCetakBtn.setDisable(true);
        }

        //diskon
        mejaGlobalDiskon = 0;
        Integer diskonId = mejaDiskonCB.getSelectionModel().getSelectedItem().getId();
        Integer pajakId = mejaPajakCB.getSelectionModel().getSelectedItem().getId();
        setGrandTotalMeja(totalHargaMeja, diskonId, pajakId);
    }
    
    @FXML
    private void mejaViewAction() {
        transaksiIdMeja = 0;
        getMejaStatus();
        mejaDetailPane.setVisible(false);
        mejaViewPane.setVisible(true);
    }
    
    @FXML
    private void mejaUbahAction(ActionEvent actionEvent){
        PesanProperty selectedPesanProp = listMenuMeja.getSelectionModel().getSelectedItem();
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle(selectedPesanProp.getNama());
        dialog.setHeaderText("Masukkan jumlah barang yang dipesan:");

        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(jumlah -> {
            MenuItem menuItem = iMenuItem.getByCode(selectedPesanProp.getCode());
            Integer stok = menuItem.getStok();
            int sisaStok = stok - Integer.valueOf(jumlah);
            if(menuItem.getStokFlag() && sisaStok < 0) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(primaryStage);
                alert.setTitle("Error!");
                alert.setHeaderText("Stok Tidak Mencukupi");
                alert.setContentText("Stok yang tersedia = " + stok);

                alert.showAndWait();
            }else{
                Transaksi transaksi = new Transaksi(transaksiIdMeja);
                List<Object[]> obj = iPesan.getByMenuItemAndTransaksi(menuItem, transaksi);
                if(null != obj && !obj.isEmpty()) {
                    for(Object[] row : obj){
                        Pesan pesan = (Pesan) row[0];
                        pesan.setJumlah(Integer.valueOf(jumlah));
                        iPesan.update(pesan);
                    }
                }
                displayOrderedMeja();
            }
            
        });
    }
    
    @FXML
    private void mejaHapusAction(ActionEvent actionEvent){
        PesanProperty selectedPesanProp = listMenuMeja.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText("Anda Yakin?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Pesan pesan = new Pesan();
            pesan.setId(selectedPesanProp.getId());
            Transaksi transaksi = new Transaksi();
            transaksi.setId(transaksiIdMeja);
            pesan.setTransaksiId(transaksi);
            pesan.setMenuItemCode(new  MenuItem(selectedPesanProp.getCode()));
            iPesan.deleteById(pesan);
            
            displayOrderedMeja();
        }
    }
    
    @FXML
    private void pengaturanSimpanAction() {
        General generalUpdate = new General();
        generalUpdate.setModeCafe(pengaturanModeCafeCheckBox.isSelected());
        generalUpdate.setPrinterCode(pengaturanPrinterCB.getValue());
        iGeneral.update(generalUpdate);
        
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Informasi");
        alert.setHeaderText("Restart!");
        alert.setContentText("Silahkan restart Kiraju untuk pengaturan baru");
        alert.showAndWait();
    }
    
    @FXML
    private void pengaturanBatalAction() {
//        General general = iGeneral.getGeneral();
        pengaturanModeCafeCheckBox.setSelected(general.getModeCafe());
        pengaturanPrinterCB.setValue(general.getPrinterCode());
    }

    private void pengaturan() {
//        general = iGeneral.getGeneral();
        pengaturanModeCafeCheckBox.setSelected(general.getModeCafe());
        pengaturanPrinterCB.getItems().addAll("58mm", "80mm");
        pengaturanPrinterCB.setValue(general.getPrinterCode());
    }

    private void setGrandTotalMeja(Integer totalHargaMeja, Integer diskonId, Integer pajakId) {
        mejaGlobalDiskon = 0;
        if(diskonId > 0){
            Diskon diskon = iDiskon.getById(diskonId);
            if(diskon.getTipe().equals(CommonConstant.RUPIAH_CODE)){
                mejaGlobalDiskon = diskon.getBilangan();
            }else{
                float untungPersen = (float) diskon.getBilangan()/100;
                mejaGlobalDiskon = Math.round(untungPersen*totalHargaMeja);
            }
        }
        
        //pajak
        mejaGlobalPajak = 0;
        if(pajakId > 0) {
            Pajak pajak = iPajak.getById(pajakId);
            if(pajak.getTipe().equals(CommonConstant.RUPIAH_CODE)){
                mejaGlobalPajak = pajak.getBilangan();
            }else{
                float pajakPersen = (float) pajak.getBilangan()/100;
                mejaGlobalPajak = Math.round(pajakPersen*(totalHargaMeja-mejaGlobalDiskon));
            }
        }
        
        mejaGrandTotal = totalHargaMeja - mejaGlobalDiskon + mejaGlobalPajak;
        total.setText(numberFormat.format(mejaGrandTotal));
    }
}