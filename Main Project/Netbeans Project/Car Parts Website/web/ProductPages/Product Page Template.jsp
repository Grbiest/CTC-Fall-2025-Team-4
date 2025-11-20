<%-- 
    Document   : Product Page Template
    Created on : Nov 17, 2025, 12:57:32 AM
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
      background: url("<%= request.getContextPath() %>/i/WhiteCarLogo.png") no-repeat center;
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

   /* Page Title */
    .page-title {
        text-align: center;
        margin: 40px 0;
        font-size: 32px;
        text-decoration: underline;
    }

    /* Cart Items */
    .cart-container {
        width: 85%;
        margin: auto;
    }

    .cart-item {
        display: flex;
        align-items: center;
        padding: 20px 0;
        border-bottom: 1px solid #ccc;
    }

    .cart-item img {
        width: 180px;
        height: 180px;
        object-fit: cover;
        margin-right: 30px;
    }

    .item-info {
        flex: 1;
        font-size: 22px;
    }

    .qty-box {
        font-size: 22px;
        padding: 0 20px;
    }

    .price-box {
        font-size: 22px;
        width: 150px;
        text-align: right;
    }

    .delete-btn {
        color: #C20E0E;
        font-size: 22px;
        cursor: pointer;
        padding-left: 20px;
    }

    /* Summary */
    .summary-box {
        margin: 40px 80px;
        font-size: 24px;
        line-height: 40px;
    }

    .checkout-btn {
        font-size: 28px;
        text-decoration: underline;
        margin-top: 20px;
        cursor: pointer;
    }

    .continue-shopping {
        text-align: right;
        font-size: 26px;
        margin-top: 40px;
        margin-right: 80px;
        cursor: pointer;
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

<!-- Page Title -->
<div class="page-title">Cart</div>

<!-- Cart Container -->
<div class="cart-container">

    <!-- Item 1 -->
    <div class="cart-item">
        <img src="https://placehold.co/196x203" alt="Battery">

        <div class="item-info">SLI65 Duracell Ultra Car Battery | Group 65 | 675 CCA</div>

        <div class="qty-box">Qty [ - 1 + ]</div>

        <div class="price-box">$172.59</div>

        <div class="delete-btn">del</div>
    </div>

    <!-- Item 2 -->
    <div class="cart-item">
        <img src="https://placehold.co/234x175" alt="Spark Plug">

        <div class="item-info">NGK Iridium IX Iridium Spark Plug 93501</div>

        <div class="qty-box">Qty [ - 1 + ]</div>

        <div class="price-box">$13.99</div>

        <div class="delete-btn">del</div>
    </div>

</div>

<!-- Summary -->
<div class="summary-box">
    Subtotal: $186.58<br>
    Shipping: $3.99<br>
    Total: <strong>$190.57</strong><br>
    <div class="checkout-btn">Proceed to Checkout</div>
</div>

<div class="continue-shopping">Continue Shopping</div>
</body>
</html>