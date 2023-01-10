/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.controllers;

import com.DAO.BookDAO;
import com.DAO.CategoryDAO;
import com.models.Book;
import com.models.Category;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
@MultipartConfig
public class BookControllers extends HttpServlet {

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
            out.println("<title>Servlet BookControllers</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookControllers at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        if (path.endsWith("/Book/Add")) {
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
                request.getSession().setAttribute("Check", "AddBook");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/add_book.jsp");
                dispatcher.forward(request, response);
            }
        } else if (path.endsWith("/Book/Manage")) {
            if (request.getSession().getAttribute("User") == null) {
                String errorString = "Bạn cần đăng nhập trước";
                request.setAttribute("errorString", errorString);
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            } else {
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
                request.getSession().setAttribute("Check", "ManageBook");
                // Forward sang /WEB-INF/views/productListView.jsp
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/manage_book.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            if (path.startsWith("/Book/Delete")) {
                String id = (String) request.getParameter("id");
                BookDAO bookDAO = new BookDAO();
                if (id == null) {
                    boolean result;
                    int count = 0;

                    try {
                        count = bookDAO.deleteAllBook();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(BookControllers.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(BookControllers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (count != 0) {
                        result = true;
                    } else {
                        result = false;
                    }

                } else {
                    boolean result;
                    int count = 0;
                    try {
                        count = bookDAO.deleteBook(id);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(BookControllers.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(BookControllers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (count != 0) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
                request.setAttribute("errorString", "Đã xóa thành công");
//                String str = "Đã xóa thành công";
//                response.sendRedirect("/Book/Manage?errorString="+str);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/Book/Manage");
                dispatcher.forward(request, response);

            } else if (path.startsWith("/Book/Edit")) {
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
                    Book book = null;
                    ArrayList<Category> list = null;
                    BookDAO bookDAO = new BookDAO();
                    try {
                        book = bookDAO.findBook(id);
                    } catch (SQLException ex) {
                        Logger.getLogger(BookControllers.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(BookControllers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    CategoryDAO categoryDAO = new CategoryDAO();
                    try {
                        list = categoryDAO.getAllCategory();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(BookControllers.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(BookControllers.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (request.getParameter("errorString") != null) {
                        errorString = (String) request.getAttribute("errorString");
                        response.sendRedirect("/Book/Manage");
                    } else {
                        request.setAttribute("book", book);
                        request.setAttribute("categoryList", list);
                        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/edit_book.jsp");
                        dispatcher.forward(request, response);
                    }

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
        if (request.getParameter("btnEdit") != null) {
            request.setCharacterEncoding("UTF-8");
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String category_id = request.getParameter("category");
            String count = request.getParameter("count");
            Part file = request.getPart("fileImage");
            String fileName = request.getParameter("image_str");
            if (!getFilename(file).equals("")) {
                String savePath = getServletContext().getRealPath("/") + "\\Resources\\img\\products";
                File fileSaveDir = new File(savePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }
                fileName = extractfilename(file);
                file.write(savePath + File.separator + fileName);
            }
//		String filePath = savePath + File.separator + fileName;

            CategoryDAO categoryDAO = new CategoryDAO();
            Category category = null;
            try {
                category = categoryDAO.findCategory(category_id);
            } catch (ClassNotFoundException | SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            Book book = new Book();
            book.setId(id);
            book.setName(name);
            book.setCategory(category);
            book.setAmount(count);
            book.setImage(fileName);
            int result = 0;
            String errorString = null;
            try {
                BookDAO bookDAO = new BookDAO();
                result = bookDAO.updateBook(book);
            } catch (ClassNotFoundException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                errorString = e.getMessage();
            }
            if (result == 0 && errorString == null) {
                errorString = "Chỉnh sửa thất bại";
            }
            if (result == 1) {
                errorString = "Chỉnh sửa thành công";
            }
            // Lưu thông tin vào request attribute trước khi forward sang views.
            request.setAttribute("errorString", errorString);
//            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/Book/Edit");
//            dispatcher.forward(request, response);
            response.sendRedirect("/Book/Edit?errorString=" + errorString);

        }
        if (request.getParameter("btnSearch") != null) {
            if (request.getSession().getAttribute("User") == null) {
                String errorString = "Bạn cần đăng nhập trước";
                request.setAttribute("errorString", errorString);
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setCharacterEncoding("UTF-8");
                String data_search = request.getParameter("data_search");
                String errorString = null;
                ArrayList<Book> list = null;

                try {
                    BookDAO bookDAO = new BookDAO();
                    list = bookDAO.getSearchBook(data_search);
                } catch (Exception e) {
                    e.printStackTrace();
                    errorString = e.getMessage();
                }
                if (request.getAttribute("errorString") != null) {
                    errorString = (String) request.getAttribute("errorString");
                }
                errorString = "Kết quả tìm kiếm cho từ khóa '" + data_search + "'";
                // Lưu thông tin vào request attribute trước khi forward sang views.
                request.setAttribute("errorString", errorString);
                request.setAttribute("bookList", list);

                // Forward sang /WEB-INF/views/productListView.jsp
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/manage_book.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    private String extractfilename(Part file) {
        String cd = file.getHeader("content-disposition");
        String[] items = cd.split(";");
        for (String string : items) {
            if (string.trim().startsWith("filename")) {
                return string.substring(string.indexOf("=") + 2, string.length() - 1);
            }
        }
        return "";
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";
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
