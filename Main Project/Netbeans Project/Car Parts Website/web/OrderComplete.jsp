<%-- 
    Document   : OrderComplete
    Created on : Nov 18, 2025, 11:51:21 PM
    Author     : Grant
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Order Complete</title>
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
      background-size: 100% 100%;
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
      margin: 0 5px;
      font-family: Roboto, sans-serif;
    }

    .nav-btn:hover {
      text-decoration: underline;
    }

    /* Order Complete Card */
    .order-card {
      width: 580px;
      background: white;
      box-shadow: 0px 2px 6px rgba(0,0,0,0.1);
      border-radius: 10px;
      margin: 40px auto; /* center horizontally with margin */
      text-align: center;
      padding: 40px 20px;
    }

    .order-card h1 {
      color: #28A745;
      font-size: 32px;
      font-weight: 700;
      margin: 0 0 20px;
    }

    .order-card p {
      color: black;
      font-size: 16px;
      margin: 0 0 30px;
      line-height: 1.5;
    }

    .continue-btn {
      display: inline-block;
      width: 200px;
      height: 42px;
      background: #007BFF;
      color: white;
      border-radius: 6px;
      line-height: 42px;
      text-align: center;
      font-size: 16px;
      text-decoration: none;
      cursor: pointer;
    }

  </style>
</head>
<body>
<%
    DB_Objects.User user = (DB_Objects.User) session.getAttribute("user");
    DB_Objects.DBManager dbm = (DB_Objects.DBManager) session.getAttribute("dbm");
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
      <%
        if (user instanceof DB_Objects.Guest) {
      %>
            <form action="index.html" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Login</button>
            </form>
            <form action="SignUpServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Sign Up</button>
            </form>
            <form action="CartServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Cart (<%= user.getCartQuantity() %>)</button>
            </form>
        <%
          } else if (user instanceof DB_Objects.Customer) {
        %>
            <form action="AccountServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Account</button>
            </form>
            <form action="index.html" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Sign Out</button>
            </form>
            <form action="CartServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Cart (<%= user.getCartQuantity() %>)</button>
            </form>
        <%
          }
        %>
    </div>
</header>

<!-- ORDER COMPLETE CARD -->
<div class="order-card">
    <h1>✅ Order Complete!</h1>
    <p>Thank you for your purchase. Your order has been successfully placed.</p>
    <a href="ProductsPage.jsp" class="continue-btn">Continue Shopping</a>
</div>

</body>
</html>
