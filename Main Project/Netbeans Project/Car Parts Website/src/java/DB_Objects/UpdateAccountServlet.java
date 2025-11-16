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
@WebServlet(name = "UpdateAccountServlet", urlPatterns = {"/UpdateAccountServlet"})
public class UpdateAccountServlet extends HttpServlet {

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

            Customer user1 = (Customer) session.getAttribute("user");

            if (user1 == null) {
                response.getWriter().println("User not found in session.");
                return;
            }

            System.out.println("User retrieved: " + user1.getUsername());
            
            String firstName;
            String lastName;
            String email;
            String dirtyPhone;
            String phone;
            String street;
            String city;
            String state;
            String zip;
            String sStreet;
            String sCity;
            String sState;
            String sZip;
            
            firstName = request.getParameter("firstname");
            lastName = request.getParameter("lastname");
            email = request.getParameter("email");
            dirtyPhone = request.getParameter("phone");
            street = request.getParameter("street");
            city = request.getParameter("city");
            state = request.getParameter("state");
            zip = request.getParameter("zip");
            sStreet = request.getParameter("sStreet");
            sCity = request.getParameter("sCity");
            sState = request.getParameter("sState");
            sZip = request.getParameter("sZip");
            
            //Turn phone into a 10 digit number
            phone = dirtyPhone.replace("-", "");
            
            System.out.println("First Name: " + firstName +
                    "\tLast Name: " + lastName +
                    "\tEmail: " + email +
                    "\tPhone: " + phone +
                    "\tStreet: " + street +
                    "\tCity: " + city +
                    "\tState: " + state +
                    "\tZip: " + zip +
                    "\tShipping Street: " + sStreet +
                    "\tShipping City: " + sCity + 
                    "\tShipping State: " + sState+
                    "\tShipping Zip: " + sZip);
            
            user1.setFirstName(firstName);
            user1.setLastName(lastName);
            user1.setEmail(email);
            user1.setPhoneNumber(phone);
            user1.setBillingStreet(street);
            user1.setBillingCity(city);
            user1.setBillingState(state);
            user1.setBillingZip(zip);
            user1.setShippingStreet(sStreet);
            user1.setShippingCity(sCity);
            user1.setShippingState(sState);
            user1.setShippingZip(sZip);
            
            String userId = user1.getUserId();
            DBManager dbm = new DBManager(getServletContext());
            String[] row = {user1.getUsername(), user1.getPassword(), firstName, lastName, email, phone, sStreet, sCity, sState, sZip, street, city, state, zip};
            dbm.replaceUserWithUserWithUserID(userId, row);
            

            System.out.println("Updating account info");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/UpdatedAccountPage.jsp");
            dispatcher.forward(request, response);
//            un = request.getParameter("uname");
//            pw = request.getParameter("pword");
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
