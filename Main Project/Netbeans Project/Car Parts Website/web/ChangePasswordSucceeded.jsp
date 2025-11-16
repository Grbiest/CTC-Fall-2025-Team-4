<%-- 
    Document   : ChangePasswordSucceeded
    Created on : Nov 15, 2025, 7:50:24 PM
    Author     : Grant
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Account Info</title>
  <style>
    body {
      margin: 0;
      background: #F4F6F9;
      font-family: Roboto, sans-serif;
      color: #000;
    }

    header {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 40px 80px;
      border-bottom: 1.5px solid #000;
      background: #F4F6F9;
    }

    .logo {
      width: 423px;
      height: 214px;
      background: url("i/WhiteCarLogo.png") no-repeat center;
      background-size: 100% 100%; /* Stretch to fill */
      display: block;
    }



    .search-bar {
      display: flex;
      align-items: center;
      border: 1px solid #000;
      height: 77px;
      width: 514px;
      justify-content: space-between;
      font-size: 24px;
      padding: 0 10px;
      box-sizing: border-box;
    }


    .nav-links {
      display: flex;
      align-items: center;
      gap: 40px;
      font-size: 22px;
    }

    .hero {
      width: 100%;
      height: 243px;
      background: #046A93;
      margin-top: 30px;
    }

    main {
      display: flex;
      justify-content: center;
      padding: 40px;
    }

    .account-card {
      background: white;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      padding: 40px;
      width: 450px;
    }

    .account-card h2 {
      text-align: center;
      margin-bottom: 30px;
      font-size: 24px;
    }

    label {
      display: block;
      font-weight: bold;
      margin-top: 15px;
      margin-bottom: 5px;
    }

    input {
      width: 100%;
      padding: 8px 10px;
      border-radius: 6px;
      border: 1px solid #ccc;
      font-size: 14px;
    }

    .checkbox-row {
      display: flex;
      align-items: center;
      margin-top: 10px;
    }

    .checkbox-row input {
      margin-right: 10px;
    }

    .section-title {
      font-size: 18px;
      font-weight: bold;
      margin-top: 25px;
    }

    .logout-btn {
      display: block;
      width: 100%;
      margin-top: 30px;
      padding: 10px;
      background-color: #D9534F;
      color: white;
      border: none;
      border-radius: 8px;
      font-size: 16px;
      cursor: pointer;
    }

    .btn {
      display: inline-block;
      background: #D9D9D9;
      padding: 8px 20px;
      border-radius: 4px;
      font-size: 20px;
      cursor: pointer;
    }
    
    .nav-btn {
        background: none;
        border: none;
        font-size: 22px;
        cursor: pointer;
        color: #000;
        padding: 0;
        margin: 0 5px; /* spacing between items */
        font-family: Roboto, sans-serif;
      }

      .nav-btn:hover {
        text-decoration: underline;
      }
      
    .submit-btn {
      margin-top: 30px;
      font-size: 28px;
      font-weight: 500;
      line-height: 36px;
      text-align: center;
      cursor: pointer;
    }
    
    .update-label {
      color: #22C55E;
      font-size: 20px;
      text-align: center;
      margin-top: -10px;
      margin-bottom: 20px;
    }


  </style>
</head>
<body>
    <%
    DB_Objects.Customer user = (DB_Objects.Customer) session.getAttribute("user");
    
%>
    <!-- HEADER -->
    <header>
        <a href="ProductsPage.jsp" class="logo"></a>




        <form class="search-bar" action="SearchServlet" method="post">
          <input type="text" name="query" placeholder="Search for products..." 
                 style="flex: 1; font-size: 24px; padding: 10px; border: none; outline: none;">
          <button type="submit" 
                  style="font-size: 24px; padding: 10px 20px; border: none; background: #046A93; color: #fff; cursor: pointer;">
            Search
          </button>
        </form>


    <div class="nav-links">
        <!-- Account Form -->
        <form action="AccountServlet" method="post" style="display: inline;">
          <button type="submit" class="nav-btn">Account</button>
        </form>

        <!-- Sign Out Form -->
        <form action="index.html" method="post" style="display: inline;">
          <button type="submit" class="nav-btn">Sign Out</button>
        </form>
        <!-- Cart Form -->
        <form action="CartServlet" method="post" style="display: inline;">
          <button type="submit" class="nav-btn">Cart (<%= user.getCartQuantity() %>)</button>
        </form>
    </div>


  </header>

<!--   HERO SECTION (not needed for account page)
  <div class="hero"></div>-->

  <!-- Account form -->
    <main>
      <form class="account-card" onsubmit="return validatePasswords()" action="ConfirmPasswordServlet" method="post">
        <!--Update message-->
        <div class="update-label">Password successfully updated.</div>
        <h2>Change Password</h2>
                    
                    <label for="password">New Password</label>
                    <input type="password" class="form-input" placeholder="Enter your password" name ="password" id="password" required />
                    
                    <label for="confirmPassword">Confirm New Password</label>
                    <input type="password" class="form-input" placeholder="Confirm your password" name ="confirmPassword" id="confirmPassword" required />
                    
                    <div id="error-message" style="color: red; font-size: 18px; text-align: right; display: none;">
                      ERROR: Passwords must match.
                    </div>

                    <button type="submit" class="logout-btn">
                        Change to new password
                    </button>


                  </form>
    </main>
    

<script>
  function validatePasswords() {
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const errorMessage = document.getElementById("error-message");

    if (password !== confirmPassword) {
      errorMessage.style.display = "block";
      return false; // Prevent form submission
    }

    errorMessage.style.display = "none";
    return true; // Allow form submission
  }
</script>
</body>
</html>
