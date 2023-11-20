package com.example.cw_project_mobile.DataConnect;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnect {
    @SuppressLint("API")
    public Connection connection(){
        Connection con = null;

        String ip="192.168.1.5",port="1433",username="sa",password="1",databasename="Hikes";
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionUrl = "jdbc:jtds:sqlserver://"+ip+":"+port+
                    ";databasename="+databasename+";User="+username+";password="+password+";";
            con = DriverManager.getConnection(connectionUrl);
        }
        catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }
        return con;
    }
}
