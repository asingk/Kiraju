/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kiraju.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.apache.derby.tools.ij;
import org.apache.log4j.Logger;


/**
 *
 * @author arvita
 */
public class JDBCConnection {
    private final static Logger LOGGER = Logger.getLogger(JDBCConnection.class);
    //for development
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String URL = "jdbc:derby://localhost:1527/kiraju4";
    //for production
//    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
//    private static final String URL = "jdbc:derby:kiraju";
    private static final String SYS_BACKUP = "CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)";
    private static final String USER = "app";
    private static final String PWD = "password";
    
    public static void databaseBackup(String backupDir) {
        try {
            Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(URL, USER, PWD); 
                    CallableStatement cs = conn.prepareCall(SYS_BACKUP)) {
                cs.setString(1, backupDir);
                cs.execute();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            LOGGER.error("Error in database backup", ex);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Backup Error");
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
    }
    
    public static void databaseRestore(String backupDir) {
        if(databaseShutdown()){
            try {
                Class.forName(DRIVER);
                Properties properties = new Properties();
                properties.put("user", USER);
                properties.put("password", PWD);
                properties.put("restoreFrom", backupDir + File.separator + "kiraju");
                Connection conn = DriverManager.getConnection(URL, properties);
                conn.close();
            } catch (ClassNotFoundException | SQLException ex) {
                LOGGER.error("Error in database restore", ex);
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Restore Error");
                alert.setContentText(ex.getMessage());

                alert.showAndWait();
            }
        }
        
    }
    
    public static boolean databaseShutdown() {
        try {
            Class.forName(DRIVER);
            Properties properties = new Properties();
            properties.put("user", USER);
            properties.put("password", PWD);
            properties.put("shutdown", "true");
            DriverManager.getConnection(URL, properties);
        } catch (ClassNotFoundException | SQLException ex) {
            if (ex instanceof SQLException) {
               String sqlState = ((SQLException)ex).getSQLState();
               /*SQL State XJ015 is returned when the Derby Engine is successfully 
                 shutdown, while SQL State 08006 is returned with an individual 
                 database is successfully shutdown.
               */
                if (! (sqlState.equalsIgnoreCase("XJ015") || 
                        sqlState.equalsIgnoreCase("08006"))) {
                    //database or the Derby Engine was not shut down properly
                    LOGGER.error("Error in database shutdown", ex);
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void createDatabase() {
        try {
            Class.forName(DRIVER).newInstance();
            Properties properties = new Properties();
            properties.put("user", USER);
            properties.put("password", PWD);
            properties.put("create", "true");
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("database created");
            }
            try (Connection connection = DriverManager.getConnection(URL, properties)) {
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("connecting...");
                }
                DatabaseMetaData dbmd = connection.getMetaData();
                ResultSet rs = dbmd.getTables(null, "APP", "MEJA", null);
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("dbmd.getTables(null, \"APP\", \"MEJA\", null)");
                }
                if(!rs.next())
                {
                    if(LOGGER.isDebugEnabled()){
                        LOGGER.debug("creating table");
                    }
                    createTable(connection);
                }else{
                    if(LOGGER.isDebugEnabled()){
                        LOGGER.debug("table exist");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            LOGGER.error("Error in database create", ex);
        }
    }

    private static void createTable(final Connection connection) {
       //path to the resource included in the application jar file
       final String resourcePathDDL = "/kiraju/sql/kiraju_ddl.sql"; 
       //get the default encoding of the JVM
       final String fileEncoding = "UTF-8";
       
       try(InputStream sql = new BufferedInputStream(JDBCConnection.class.getResourceAsStream(resourcePathDDL))){
           /*invoke the command to run DDL statements contained in the resource
            stream against the database connected.*/
           try {
                int errorsEncountered = ij.runScript(connection, sql, fileEncoding, System.out, null);
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("table created");
                }
                if (errorsEncountered > 0) {
                    final String str = errorsEncountered + " errors encountered while running the sql script";
                    throw new RuntimeException(str);
                }
                String sqlFirstInstallDate = "INSERT INTO APP.GENERAL (ID, INSTALL_DT, MODE_CAFE, PRINTER_CODE) VALUES (?,?,?,?)";
               try (PreparedStatement preparedStatement = connection.prepareStatement(sqlFirstInstallDate)) {
                    preparedStatement.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
//                   preparedStatement.setDate(1, java.sql.Date.valueOf("2017-01-01"));
                    preparedStatement.setShort(1, (short) 1);
                    preparedStatement.setBoolean(3, true);
                    preparedStatement.setString(4, "58mm");
                    preparedStatement.executeUpdate();
               }
           } catch (UnsupportedEncodingException | RuntimeException e) {
               LOGGER.error("Error in running the sql script", e);
           }
        } catch (IOException | SQLException ex) {
            LOGGER.error("Error in accessing the SQL file resource", ex);
        }
    }
    
    public static boolean checkInstallDate() {
        try {
            Class.forName(DRIVER).newInstance();
            Properties properties = new Properties();
            properties.put("user", USER);
            properties.put("password", PWD);
            properties.put("create", "true");
//            LocalDate installDate = null;
            LocalDate batasDate = null;
            try (Connection conn = DriverManager.getConnection(URL, properties)) {
                String query = "SELECT INSTALL_DT FROM APP.GENERAL";
                PreparedStatement ps = conn.prepareStatement(query);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        java.sql.Date sqlDate = rs.getDate("INSTALL_DT");
                        LocalDate installDate = sqlDate.toLocalDate();
//                        installDate = LocalDate.of(2017, 4, 20);
//                        System.out.println("installDate : " + installDate);
                        batasDate = installDate.plusMonths(1);
                    }
                }
            }
            return LocalDate.now().isBefore(batasDate);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            LOGGER.error("Error in check install date", ex);
        }
        return false;
    }
}
