/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DAO;

import com.connections.DBConnections;
import com.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author 
 */
public class UserDAO {
    private Connection conn = null;

    public UserDAO() {
        conn = DBConnections.getConnection();
    }

    public User getUser(String username, String password) throws SQLException {
        String sql = "Select * from `user` where username=? and password=?;";
        PreparedStatement pstm;
      
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, username);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                User user = new User(id, username, password);
                return user;
            }
      

        return null;
    }

    public User findUser(String username) throws ClassNotFoundException, SQLException {
        String sql = "Select * from `user` where username=?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, username);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            String id = rs.getString("id");
            String password = rs.getString("password");
            User user = new User(id, username, password);
            return user;
        }
        return null;
    }
}
