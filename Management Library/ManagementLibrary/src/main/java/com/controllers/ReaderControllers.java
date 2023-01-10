/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.controllers;

import com.DAO.BookDAO;
import com.DAO.ReaderDAO;
import com.models.Book;
import com.models.Reader;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author 
 */
public class ReaderControllers extends HttpServlet {

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
            out.println("<title>Servlet ReaderControllers</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReaderControllers at " + request.getContextPath() + "</h1>");
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
        if (path.endsWith("/Reader/Add")) {
            if (request.getSession().getAttribute("User") == null) {
                String errorString = "Bạn cần đăng nhập trước";
                request.setAttribute("errorString", errorString);
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            } else {
                // TODO Auto-generated method stub
                String errorString = null;
                ArrayList<Book> list = null;
                try {
                    BookDAO bookDAO = new BookDAO();
                    list = bookDAO.getAllBook();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorString = e.getMessage();
                }
                if (request.getAttribute("errorString") != null) {
                    errorString = (String) request.getAttribute("errorString");
                }
                // Lưu thông tin vào request attribute trước khi forward sang views.
                request.setAttribute("errorString", errorString);
                request.setAttribute("bookList", list);
                request.getSession().setAttribute("Check", "AddReader");
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/add_reader.jsp");
                dispatcher.forward(request, response);
            }
        } else if (path.endsWith("/Reader/Manage")) {
            if (request.getSession().getAttribute("User") == null) {
                String errorString = "Bạn cần đăng nhập trước";
                request.setAttribute("errorString", errorString);
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            } else {
                String status = (String) request.getParameter("status");
                if (status == null) {
                    status = "0";
                    request.getSession().setAttribute("Check", "ManageReader_0");
                } else {
                    status = "1";
                    request.getSession().setAttribute("Check", "ManageReader_1");
                }
                System.out.println(status);
                String errorString = null;
                ArrayList<Reader> list = null;
//		if(status.equals("1")==false) {
//			status="0";
//		}
                try {
                    ReaderDAO readerDAO = new ReaderDAO();
                    list = readerDAO.getListReader(status);
                } catch (Exception e) {
                    e.printStackTrace();
                    errorString = e.getMessage();
                }
                if (request.getAttribute("errorString") != null) {
                    errorString = (String) request.getAttribute("errorString");
                }
                // Lưu thông tin vào request attribute trước khi forward sang views.
                request.setAttribute("readerList", list);
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/manage_reader.jsp");
                dispatcher.forward(request, response);
            }
        } else if (path.endsWith("/Reader/Confirm")) {
            String id = (String) request.getParameter("id");
            try {
                ReaderDAO readerDAO = new ReaderDAO();
                int count = readerDAO.updateStatus(id);
            } catch (ClassNotFoundException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//		response.sendRedirect("/QuanLyThuVien/ManageReader");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Reader/Manage");
            dispatcher.forward(request, response);
        } else {
            if (path.startsWith("/Reader/Search")) {
                if (request.getSession().getAttribute("User") == null) {
                    String errorString = "Bạn cần đăng nhập trước";
                    request.setAttribute("errorString", errorString);
                    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
                    dispatcher.forward(request, response);
                } else {
                    String status = (String) request.getParameter("status");
                    String data_search = (String) request.getParameter("data_search");
                    if (status == null) {
                        status = "0";
                        request.getSession().setAttribute("Check", "ManageReader_0");
                    } else {
                        status = "1";
                        request.getSession().setAttribute("Check", "ManageReader_1");
                    }
                    System.out.println(status);
                    String errorString = null;
                    ArrayList<Reader> list = null;
//		if(status.equals("1")==false) {
//			status="0";
//		}
                    try {
                        ReaderDAO readerDAO = new ReaderDAO();
                        list = readerDAO.getListSearch(data_search, status);
                    } catch (Exception e) {
                        e.printStackTrace();
                        errorString = e.getMessage();
                    }
                    if (request.getAttribute("errorString") != null) {
                        errorString = (String) request.getAttribute("errorString");
                    }
                    // Lưu thông tin vào request attribute trước khi forward sang views.
                    request.setAttribute("readerList", list);
                    RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/manage_reader.jsp");
                    dispatcher.forward(request, response);
                }
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
        if (request.getParameter("btnReaderAdd") != null) {
            if (request.getSession().getAttribute("User") == null) {
                String errorString = "Bạn cần đăng nhập trước";
                request.setAttribute("errorString", errorString);
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            } else {
                String errorString = null;
                request.setCharacterEncoding("UTF-8");
                String name = request.getParameter("name_reader");
                String book_id = request.getParameter("id_book");
                String identify = request.getParameter("identify");
                String end_day = request.getParameter("end_day");
                SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date lFromDate1 = null;
                try {
                    lFromDate1 = datetimeFormatter1.parse(end_day);
                } catch (ParseException ex) {
                    Logger.getLogger(ReaderControllers.class.getName()).log(Level.SEVERE, null, ex);
                }
                Timestamp end = new Timestamp(lFromDate1.getTime());
                try {
                    double ident = Double.parseDouble(identify);
                    ReaderDAO readerDAO = new ReaderDAO();
                    int result = readerDAO.insertReader(name, identify, book_id, end);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    errorString = "Số chứng minh phải là số";
                    response.sendRedirect("/Reader/Add");
                }
                if (errorString == null) {
                    errorString = "Đã thêm thành công";
                    response.sendRedirect("/Reader/Manage");
                }
//                request.setAttribute("errorString", errorString);
//                request.getServletContext().getRequestDispatcher("/Reader/Add").forward(request, response);
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
