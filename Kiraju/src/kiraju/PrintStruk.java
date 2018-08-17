/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.imageio.ImageIO;
import kiraju.implement.GeneralModel;
import kiraju.interfaces.IGeneral;
import kiraju.model.General;
import kiraju.model.Users;
import kiraju.property.PesanProperty;
import kiraju.util.CommonConstant;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author arvita
 */
public class PrintStruk implements Runnable{
    
    private final static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(PrintStruk.class);
//    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID"));
    private int tunai = 0;
    private Users user = new Users();
    private ObservableList<PesanProperty> pesanMenuItemOrderedObsList = FXCollections.observableArrayList();
    private Integer totalHargaPesan = 0;
    private String namaMeja = "";
    private Integer diskonTotal = 0;
    private Integer pajakTotal = 0;
    private String diskonNama = "";
    private String pajakNama = "";
    
    private final IGeneral iGeneral = new GeneralModel();
    
    public PrintStruk(int tunai, Users user, ObservableList<PesanProperty> pesanMenuItemOrderedObsList, Integer totalHargaPesan, String namaMeja, String diskonNama, Integer diskonTotal, String pajakNama, Integer pajakTotal) {
        this.tunai = tunai;
        this.user = user;
        this.pesanMenuItemOrderedObsList = pesanMenuItemOrderedObsList;
        this.totalHargaPesan = totalHargaPesan;
        this.namaMeja = namaMeja;
        this.diskonTotal = diskonTotal;
        this.pajakTotal = pajakTotal;
        if(null != diskonNama && !diskonNama.isEmpty()) {
            this.diskonNama = diskonNama;
        }
        if(null != pajakNama && !pajakNama.isEmpty()) {
            this.pajakNama = pajakNama;
        }
    }

    @Override
    public void run() {
        try {
            InputStream logoStream = this.getClass().getResourceAsStream(CommonConstant.LOGO_KEDAI);
            String printer;
            General general = iGeneral.getGeneral();
            if(general.getPrinterCode().equalsIgnoreCase("58mm")) {
                printer = "reports/bayar_A8_logo.jasper";
            }else{
                printer = "reports/bayar_A7_logo.jasper";
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
            parameters.put("meja", namaMeja);
            parameters.put("waktu", new Date());
            parameters.put("kasir", user.getNama());
            parameters.put("total", totalHargaPesan);
            parameters.put("tunai", tunai);
            parameters.put("kembali", tunai-totalHargaPesan);
            parameters.put("syukran1", CommonConstant.KEDAI_SYUKRAN_1);
            parameters.put("syukran2", CommonConstant.KEDAI_SYUKRAN_2);
            parameters.put("diskonNama", diskonNama);
            parameters.put("diskonTotal", diskonTotal > 0 ? diskonTotal : null);
            parameters.put("pajakNama", pajakNama);
            parameters.put("pajakTotal", pajakTotal > 0 ? pajakTotal : null);
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(pesanMenuItemOrderedObsList);
            JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, beanCollectionDataSource);
            JasperPrintManager.printReport(print, false);
        } catch (JRException | IOException ex) {
            LOGGER.error("failed to print report", ex);
        }
    }
    
}
