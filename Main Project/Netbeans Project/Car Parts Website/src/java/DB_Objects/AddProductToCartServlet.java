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
@WebServlet(name = "AddProductToCartServlet", urlPatterns = {"/AddProductToCartServlet"})
public class AddProductToCartServlet extends HttpServlet {

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
            HttpSession session = request.getSession(false);

            if (session == null) {
                response.getWriter().println("No session exists.");
                return;
            }

            User user1 = (User) session.getAttribute("user");

            if (user1 == null) {
                response.getWriter().println("User not found in session.");
                return;
            }
            
            String qty, pi;
            qty = request.getParameter("quantity");
            pi = request.getParameter("prodID");
            System.out.println(qty + " " + pi);

            System.out.println("User retrieved: " + user1.getUsername() + "\tUserID: " + user1.getUserId());

            DBManager dbm = new DBManager(getServletContext());
            String UserID = user1.getUserId();
            
            String addItemResult = dbm.addItemToCartFromInventory(pi, UserID, qty);
            System.out.println(addItemResult);
            
            


            
            String productLink = dbm.selectFromInventoryByProdID(pi)[5];
            String productName = dbm.selectFromInventoryByProdID(pi)[1];

            System.out.println("Going to " + productName + " page");
            RequestDispatcher dispatcher = request.getRequestDispatcher(productLink);
            // Pass message to JSP
            request.setAttribute("addItemResult", addItemResult);

            // Set success flag ONLY when successful
            if (!addItemResult.startsWith("ERROR")) {
                request.setAttribute("addedToCart", true);
            }
            request.setAttribute("addItemResult", addItemResult);
            int cartQuantity = dbm.getCartTotalFromUserID(UserID);
            user1.setCartQuantity(cartQuantity);
            dispatcher.forward(request, response);
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
