package com.example.appbanhang.dao;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectSql {
    @SuppressLint("NewApi")
    Connection con = null;
    public Connection connectionSql()
    {
        /* NTSC */
        //String ip = "10.0.0.174", port = "1433", db = "DoAnAndroid", username = "sa", password = "hungtamdiem99";

        /* Home */
        String ip = "192.168.0.102", port = "1433", db = "DoAnAndroid", username = "sa", password = "hungtamdiem99";

        /* Tuy chon */
        //String ip = "192.168.170.245", port = "1433", db = "DoAnAndroid", username = "sa", password = "hungtamdiem99";

        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        String connectURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connectURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + db + ";user=" + username + ";"+"password=" + password + ";";
            con = DriverManager.getConnection(connectURL);
        } catch (Exception e) {
            Log.e("Error :", e.getMessage());
        }
        return con;
    }
}
