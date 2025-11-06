/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package DB_Objects;

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
@WebServlet("/UserExitServlet")
public class UserExitServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the current session, don't create a new one if it doesn't exist
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Retrieve the user object from session
            User user = (User) session.getAttribute("user");

            if (user != null) {
                String userId = user.getUserId();
                System.out.println("User exiting: " + userId);

                // Optional: invalidate session if needed
                // session.invalidate();
            } else {
                System.out.println("No user found in session.");
            }
        } else {
            System.out.println("No active session.");
        }
    }
}
