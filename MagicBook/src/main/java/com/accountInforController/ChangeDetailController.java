/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accountInforController;

import dao.AccountDao;
import dao.AccountProfileDAO;
import java.io.File;
import model.AccountProfile;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import error.AccountError;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;

/**
 *
 * @author MSI PC
 */
public class ChangeDetailController extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangeDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangeDetailController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void changeInformation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Account account = (Account) request.getSession().getAttribute("account");
        String email = account.getEmail();
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String dob = request.getParameter("dob");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");

        AccountError accError = new AccountError();
        boolean checkError = false;
        if (!isValidDob(dob)) {
            accError.setDobError("Must be older than 12 years");
            checkError = true;
        }
        if (!isValidName(firstname)) {
            accError.setFirstNameError("Cannot contain special characters!");
            checkError = true;
        }
        if (!isValidName(lastname)) {
            accError.setLastNameError("Cannot contain special characters!");
            checkError = true;
        }
        AccountProfileDAO dao = new AccountProfileDAO();
        if (!checkError) {
            dao.updateInformation(email, firstname, lastname, dob, phone, gender);
        }

        AccountProfile profileDTO = dao.getAccountProfile(email);
        String avatar = profileDTO.getAvatar();
        String background = profileDTO.getBackground();

        //gửi attribute để hiển thị lại sau khi update
        request.setAttribute("firstname", firstname);
        request.setAttribute("lastname", lastname);
        request.setAttribute("dob", dob);
        request.setAttribute("phone", phone);
        request.setAttribute("gender", gender);
        request.setAttribute("avatar", avatar);
        request.setAttribute("background", background);
        request.setAttribute("ACC_ERROR", accError);
        request.getRequestDispatcher("changeDetail.jsp").forward(request, response);

    }

    private void changeBackground(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            Account account = (Account) request.getSession().getAttribute("account");

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("jakarta.servlet.context.tempdir");
            factory.setRepository(repository);
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);

            String background = null;
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String fileName = item.getName();
                    if (fileName != null && !fileName.isEmpty()) {
                        Path path = Paths.get(fileName);
                        String storePath = servletContext.getRealPath("/uploads");
                        File uploadFile = new File(storePath + "/" + path.getFileName());
                        if (uploadFile.exists()) {
                            // Nếu tệp tin đã tồn tại, tạo một tên mới cho tệp tin hoặc ghi đè lên tệp tin hiện có
                            String newFileName = generateNewFileName(path.getFileName().toString());
                            uploadFile = new File(storePath + "/" + newFileName);
                        }
                        item.write(uploadFile);
                        background = "./uploads/" + path.getFileName();
                        break; // Assuming only one file is uploaded
                    }
                }
            }

            AccountProfileDAO dao = new AccountProfileDAO();
            String email = account.getEmail();
            dao.updateBackground(email, background);

            AccountDao accDAO = new AccountDao();
            Account accDTO = accDAO.getByEmail(account);
            AccountProfile profileDTO = dao.getAccountProfile(email);

            request.setAttribute("firstname", accDTO.getFirstName());
            request.setAttribute("lastname", accDTO.getLastName());
            request.setAttribute("dob", accDTO.getDob());
            request.setAttribute("phone", accDTO.getPhone());
            request.setAttribute("gender", accDTO.getGender());
            request.setAttribute("avatar", profileDTO.getAvatar());
            request.setAttribute("background", background);
            request.getRequestDispatcher("changeDetail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeAvatar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            Account account = (Account) request.getSession().getAttribute("account");

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("jakarta.servlet.context.tempdir");
            factory.setRepository(repository);
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);

            String avatar = null;
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String fileName = item.getName();
                    if (fileName != null && !fileName.isEmpty()) {
                        Path path = Paths.get(fileName);
                        String storePath = servletContext.getRealPath("/uploads");
                        File uploadFile = new File(storePath + "/" + path.getFileName());
                        if (uploadFile.exists()) {
                            // Nếu tệp tin đã tồn tại, tạo một tên mới cho tệp tin hoặc ghi đè lên tệp tin hiện có
                            String newFileName = generateNewFileName(path.getFileName().toString());
                            uploadFile = new File(storePath + "/" + newFileName);
                        }
                        item.write(uploadFile);
                        avatar = "./uploads/" + path.getFileName();
                        break; // Assuming only one file is uploaded
                    }
                }
            }

            AccountProfileDAO dao = new AccountProfileDAO();
            dao.updateAvatar(account.getEmail(), avatar);

            String email = account.getEmail();
            AccountDao accDAO = new AccountDao();
            Account accDTO = accDAO.getByEmail(account);
            AccountProfile profileDTO = dao.getAccountProfile(email);

            request.setAttribute("firstname", accDTO.getFirstName());
            request.setAttribute("lastname", accDTO.getLastName());
            request.setAttribute("dob", accDTO.getDob());
            request.setAttribute("phone", accDTO.getPhone());
            request.setAttribute("gender", accDTO.getGender());
            request.setAttribute("avatar", avatar);
            request.setAttribute("background", profileDTO.getBackground());
            request.getRequestDispatcher("changeDetail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidDob(String dateString) {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate inputDate = LocalDate.parse(dateString, formatter);

            LocalDate currentDate = LocalDate.now();
            System.out.println("current: " + currentDate);
            // Tính số năm giữa năm hiện tại và năm được truyền vào
            Period period = Period.between(inputDate, currentDate);
            int age = period.getYears();

            // Kiểm tra nếu tuổi lớn hơn 12
            return age >= 12;
        } catch (DateTimeException e) {
            return false;
        }
    }

    private static boolean isValidName(String name) {
        return name.matches("^[\\p{L}0-9]+$");
    }

    private String generateNewFileName(String fileName) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String extension = FilenameUtils.getExtension(fileName);
        return "new_file_" + timeStamp + "_" + generateUniqueString() + "." + extension;
    }

    private String generateUniqueString() {
        return UUID.randomUUID().toString();
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
        processRequest(request, response);
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

        String action = request.getParameter("action");

        if (action.equals("changeAvatar")) {
            changeAvatar(request, response);
        } else if ("changeBackground".equals(action)) {
            changeBackground(request, response);
        } else if ("changeInformation".equals(action)) {
            changeInformation(request, response);
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
