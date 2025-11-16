/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package DB_Objects;

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
@WebServlet(name = "ChangeUsernameServlet", urlPatterns = {"/ChangeUsernameServlet"})
public class ChangeUsernameServlet extends HttpServlet {

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
            System.out.println("Currently on ChangeUsernameServlet.");
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                HttpSession session = request.getSession(false);

                if (session == null) {
                    response.getWriter().println("No session exists.");
                    return;
                }

                Customer user1 = (Customer) session.getAttribute("user");

                if (user1 == null) {
                    response.getWriter().println("User not found in session.");
                    return;
                }

                System.out.println("User retrieved: " + user1.getUsername());



                String un, pw, nun;
                    un = request.getParameter("username");
                    pw = request.getParameter("password");
                    nun = request.getParameter("newUsername");
                    System.out.println(un + " " + pw + nun);

                DBManager dbm = new DBManager(getServletContext());
                
                if (!dbm.testLogin(un, pw)) {
                    System.out.println("Username/Password mismatch. Going to failure page.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangeCredentialsUPWFailed.jsp");
                    dispatcher.forward(request, response);
                    
                } else if (un.equals(nun) || nun.equals("") || dbm.checkForLogin(nun)){
                    System.out.println("Username change failed. Going to failure page.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangeCredentialsUNFailed.jsp");
                    dispatcher.forward(request, response);
                } else{
                
                    user1.setUsername(nun);
                    user1.displayCustomerInfo();
                    
                    dbm.updateUserWithUserID(user1.getUserId(), "Login", nun);
                    
                    System.out.println("Username change successful. Going to success page.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/ChangeCredentialsUNSucceeded.jsp");
                    dispatcher.forward(request, response);
                }
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
