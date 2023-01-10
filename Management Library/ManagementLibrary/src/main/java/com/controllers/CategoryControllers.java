/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.controllers;

import com.DAO.CategoryDAO;
import com.models.Category;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
public class CategoryControllers extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CategoryControllers</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryControllers at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.endsWith("/Category/Add")) {
            if (request.getSession().getAttribute("User") == null) {
                String errorString = "Bạn cần đăng nhập trước";
                request.setAttribute("errorString", errorString);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/add_category.jsp");
                dispatcher.forward(request, response);
            }
        } else if (path.endsWith("/Category/Manage")) {
            if (request.getSession().getAttribute("User") == null) {
                String errorString = "Bạn cần đăng nhập trước";
                request.setAttribute("errorString", errorString);
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            } else {
                String errorString = null;
                ArrayList<Category> list = null;

                try {
                    CategoryDAO categoryDAO = new CategoryDAO();
                    list = categoryDAO.getAllCategory();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorString = e.getMessage();
                }
                if (request.getAttribute("errorString") != null) {
                    errorString = (String) request.getAttribute("errorString");
                }
                // Lưu thông tin vào request attribute trước khi forward sang views.
                request.setAttribute("errorString", errorString);
                request.setAttribute("categoryList", list);
                request.getSession().setAttribute("Check", "ManageCategory");
                // Forward sang /WEB-INF/views/productListView.jsp
                RequestDispatcher dispatcher = request.getRequestDispatcher("/manage_category.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            if (path.startsWith("/Category/Edit")) {
                // TODO Auto-generated method stub
                if (request.getSession().getAttribute("User") == null) {
                    String errorString = "Bạn cần đăng nhập trước";
                    request.setAttribute("errorString", errorString);
                    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
                    dispatcher.forward(request, response);
                } else {
                    String id = (String) request.getParameter("id");

                    Category category = null;

                    String errorString = null;

                    try {

                        CategoryDAO categoryDAO = new CategoryDAO();
                        try {
                            category = categoryDAO.findCategory(id);
                        } catch (SQLException ex) {
                            Logger.getLogger(CategoryControllers.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    // Không có lỗi.
                    // Sản phẩm không tồn tại để edit.
                    // Redirect sang trang danh sách sản phẩm.
                    if (errorString != null && category == null) {
                        response.sendRedirect("/Category/Manage");
                        return;
                    }
                    // Lưu thông tin vào request attribute trước khi forward sang views.
                    request.setAttribute("errorString", errorString);
                    request.setAttribute("category", category);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/edit_category.jsp");
                    dispatcher.forward(request, response);
                }
            } else if (path.startsWith("/Category/Delete")) {
                String id = (String) request.getParameter("id");
                boolean result;
                int count = 0;
                try {
                    CategoryDAO categoryDAO = new CategoryDAO();
                    count = categoryDAO.deleteCategory(id);
                    if (count != 0) {
                        result = true;
                    } else {
                        result = false;
                    }
                    System.out.println("Ket qua" + result);
                    if (result == true) {
                        request.setAttribute("errorString", "Đã xóa thành công");
                    } else {
                        request.setAttribute("errorString", "Lỗi cơ sở dữ liệu");
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

//		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/ManageCategory");
//		dispatcher.forward(request, response);
                response.sendRedirect("/Category/Manage");
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("btnAddCategory") != null) {
            request.setCharacterEncoding("UTF-8");
            String name_category = request.getParameter("name_category");
            Category category = new Category();
            System.out.println(name_category);
            category.setName(name_category);
            try {
                CategoryDAO categoryDAO = new CategoryDAO();
                int result = categoryDAO.insertCategory(category);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.sendRedirect("/Category/Manage");
        }
        if (request.getParameter("btnUpdateCategory") != null) {
            request.setCharacterEncoding("UTF-8");

            int id = Integer.parseInt(request.getParameter("id"));
            String name = (String) request.getParameter("name_category");
            Category category = new Category(id, name);
            String errorString = null;
            int result = 0;
            try {
                CategoryDAO categoryDAO = new CategoryDAO();
                result = categoryDAO.updateCategory(category);
            } catch (SQLException e) {
                e.printStackTrace();
                errorString = e.getMessage();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (result == 0 && errorString == null) {
                errorString = "Chỉnh sửa thất bại";
            }
            if (result == 1) {
                errorString = "Chỉnh sửa thành công";
            }
            // Lưu thông tin vào request attribute trước khi forward sang views.
            request.setAttribute("errorString", errorString);
            request.setAttribute("category", category);
            // Nếu có lỗi forward sang trang edit.
            if (errorString != null) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/edit_category.jsp");
                dispatcher.forward(request, response);
            } // Nếu mọi thứ tốt đẹp.
            // Redirect sang trang danh sách sản phẩm.
            else {
                response.sendRedirect("/Catagory/Manage");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
