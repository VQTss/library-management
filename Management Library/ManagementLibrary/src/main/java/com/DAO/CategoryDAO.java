/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DAO;

import com.connections.DBConnections;
import com.models.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author 
 */
public class CategoryDAO {

    private Connection conn = null;

    public CategoryDAO() {
        conn = DBConnections.getConnection();
    }

    public Category findCategory(String id) throws SQLException, ClassNotFoundException {

        String sql = "Select * from Category where id=?";

        PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            int _id = rs.getInt("Id");
            String name = rs.getString("Name");
            Category category = new Category(_id, name);
            return category;
        }
        return null;
    }

    public int insertCategory(Category category) throws SQLException, ClassNotFoundException {

        int result = 0;
        String insert = "INSERT INTO Category (name) VALUES (?)";
        PreparedStatement preSt = conn.prepareStatement(insert);
        preSt.setString(1, category.getName());
        result = preSt.executeUpdate();
        return result;
    }

    public ArrayList<Category> getAllCategory() throws ClassNotFoundException, SQLException {
       
        ArrayList<Category> list = new ArrayList<Category>();
        String sql = "Select * from Category";
        PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Category category = new Category(id,name);
            list.add(category);
        }
        return list;
    }

    public int updateCategory(Category category) throws SQLException, ClassNotFoundException {
        int result = 0;
        String sql = "Update Category set Name =? where id=? ";
        PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(sql);
        pstm.setString(1, category.getName());
        pstm.setFloat(2, category.getId());
        result = pstm.executeUpdate();
        return result;
    }

    public int deleteCategory(String id) throws ClassNotFoundException, SQLException {
        int result = 0;
        BookDAO bdao = new BookDAO();
        bdao.deleteBookCategory(id);
        String sql = "Delete From Category where id= ?";
        PreparedStatement pstm = (PreparedStatement) conn.prepareStatement(sql);
        pstm.setString(1, id);
        result = pstm.executeUpdate();
        return result;
    }

}
