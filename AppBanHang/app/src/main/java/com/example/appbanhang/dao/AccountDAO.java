package com.example.appbanhang.dao;

import com.example.appbanhang.model.AccountModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {
    Connection conn;
    PreparedStatement ps;

    public AccountModel CheckPass(String email, String pass) {
        AccountModel tk = null;
        try {
            ConnectSql c = new ConnectSql();
            Connection conn = c.connectionSql();
            if (conn != null) {
                String sql = "select * from Account where Email=? and Pass=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, pass);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    tk = new AccountModel();
                    tk.setId(rs.getInt(1));
                    tk.setUserName(rs.getString(2));
                    tk.setEmail(rs.getString(3));
                    tk.setPhone(rs.getString(4));
                    tk.setPassWord(rs.getString(5));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tk;
    }

    public AccountModel CheckEmail(String email) {
        AccountModel tk = null;
        try {
            ConnectSql c = new ConnectSql();
            Connection conn = c.connectionSql();
            if (conn != null) {
                String sql = "select * from Account where Email=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    tk = new AccountModel();
                    tk.setId(rs.getInt(1));
                    tk.setUserName(rs.getString(2));
                    tk.setEmail(rs.getString(3));
                    tk.setPhone(rs.getString(4));
                    tk.setPassWord(rs.getString(5));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tk;
    }

    public boolean AddAccount(AccountModel ac) throws Exception {
        try {
            ConnectSql c = new ConnectSql();
            Connection conn = c.connectionSql();
            if (conn != null) {
                String sql = "INSERT INTO Account VALUES (?,?,?,?)";
                conn = c.connectionSql();
                ps = conn.prepareStatement(sql);
                ps.setString(1, ac.getUserName());
                ps.setString(2, ac.getEmail());
                ps.setString(3, ac.getPhone());
                ps.setString(4, ac.getPassWord());
                int update = ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
