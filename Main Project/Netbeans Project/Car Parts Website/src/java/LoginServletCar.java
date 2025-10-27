/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Grant
 */
@WebServlet(urlPatterns = {"/LoginServletCar"})
public class LoginServletCar extends HttpServlet {

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
            out.println("<title>Servlet LoginServletCar</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServletCar at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
            String un, pw;
            un = request.getParameter("uname");
            pw = request.getParameter("pword");
            System.out.println(un + " " + pw);
            
            DBManager dbm = new DBManager();
            if (dbm.testLogin(un, pw)) {
                String[] userArr = dbm.selectUserFromLogin(un);
                if (userArr[7].equals("OPP")){
                    
                    OrderProcessingPerson opp1 = new OrderProcessingPerson();
                    opp1.setOrderProcessingPersonInfoFromArray(userArr);
                    HttpSession ses1;
                    ses1 = request.getSession();
                    ses1.setAttribute("opp1", opp1);
                    
                    System.out.println("Going to OrderInterface as OrderProcessingPerson");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/OrderInterface.jsp");
                    dispatcher.forward(request, response);
                } else {
                    Customer cust1 = new Customer();
                    cust1.setCustomerInfoFromArray(userArr);
                    HttpSession ses1;
                    ses1 = request.getSession();
                    ses1.setAttribute("cust1", cust1);
                    
                    System.out.println("Going to ProductsPage as Customer");
                }
            }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Error</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Username and/or password not accepted. Go back.</h1>");
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
        processRequest(request, response);
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
