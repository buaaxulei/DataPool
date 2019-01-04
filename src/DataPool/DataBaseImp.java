package DataPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DataBaseImp {
    private static DataBaseImp instance = null;
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String urlFormat = "jdbc:mysql://localhost:3306/%s?&serverTimezone=MST";
    private Connection con;

    private DataBaseImp(){
        con = null;
    }

    public static DataBaseImp getInstance(){
        if(instance == null)
            instance = new DataBaseImp();
        return instance;
    }

    public void initConnection(String dataBaseName, String userName, String password){
        con = null;
        String url = String.format(urlFormat, dataBaseName);
        try {
            Class.forName(driver);

            con = DriverManager.getConnection(url, userName, password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query){
        try {
            if (con != null)
                return con.createStatement().executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void execute(String query){
        try {
            if (con != null)
                con.createStatement().execute(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    public void close(){
        try {
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
