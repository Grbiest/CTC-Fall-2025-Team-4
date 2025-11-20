<%-- 
    Document   : CartPage
    Created on : Nov 18, 2025, 4:04:08 PM
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
    
    .item-link {
        color: #000;
        text-decoration: none;
    }

    .item-link:hover {
        text-decoration: underline;
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
        padding: 10px 20px; /* add top/bottom + left/right padding */
        margin-left: 40px;  /* <-- pushes the button away from the price */
        background: none;
        border: none;
    }
    
    .delete-btn:hover {
        text-decoration: underline; /* underline on hover */
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
    
    .continue-shopping-btn {
        font-size: 26px;
        background: none;
        border: none;
        cursor: pointer;
        text-decoration: underline;
    }
    .continue-shopping-btn:hover {
        opacity: 0.7;
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
    DB_Objects.User user = (DB_Objects.User) session.getAttribute("user");
    DB_Objects.DBManager dbm = (DB_Objects.DBManager) session.getAttribute("dbm");
    String userId = user.getUserId();
    String[][] cart = dbm.selectCartFromUserID(userId);
    
    
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
            <!-- Login Form -->
            <form action="index.html" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Login</button>
            </form>

            <!-- Sign Up Form -->
            <form action="SignUpServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Sign Up</button>
            </form>
            
            <!-- Cart Form -->
            <form action="CartServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Cart (<%= user.getCartQuantity() %>)</button>
            </form>
        <%
          } else if (user instanceof DB_Objects.Customer) {
          
        %>
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
        <%
          }
        %>


    </div>


  </header>

<!-- Page Title -->
<div class="page-title">Cart</div>

<!-- Cart Container -->
<div class="cart-container">

<div class="cart-container">

    <%
        double subtotal = 0.0;

        if (cart != null && cart.length > 0) {
            for (int i = 0; i < cart.length; i++) {

                String prodName = cart[i][3];
                double price = Double.parseDouble(cart[i][4]);
                int quantity = Integer.parseInt(cart[i][6]);
                String prodId = cart[i][2];

                String[] inventoryItem = dbm.selectFromInventoryByProdID(prodId);
                String imageUrl = inventoryItem[6];
                String url = inventoryItem[5];

                // accumulate subtotal
                subtotal += price * quantity;

    %>

        <div class="cart-item">

            <img src="<%= imageUrl %>" alt="<%= prodName %>">

            <div class="item-info">
                <a href="<%= url %>" class="item-link"><%= prodName %></a>
            </div>


            <div class="qty-box">Qty <%= quantity %></div>

            <div class="price-box">$<%= price %></div>

            <!-- Delete button links to CartServlet with ?delete=CartItemID -->
            <form action="RemoveFromCartServlet" method="post">
                <input type="hidden" name="itemID" value="<%= prodId %>">
                <input type="hidden" name="quantity" value="<%= quantity %>">
                <button type="submit" class="delete-btn">Remove from cart</button>
            </form>


        </div>

    <%
            } // end loop
        } else {
    %>

    <p style="text-align:center; font-size:24px; margin-top:40px;">
        Your cart is empty.
    </p>

    <%
        }
    %>

    </div> <!-- end cart-container -->


    </div>


    </div>

    <!-- Summary -->
    <%
        double shipping = 9.99;
        double total = subtotal + shipping;
    %>

    <% if (cart != null && cart.length > 0) { %>
    <div class="summary-box">
        Subtotal: $<%= String.format("%.2f", subtotal) %><br>
        Shipping: $<%= String.format("%.2f", shipping) %><br>
        Total: <strong>$<%= String.format("%.2f", total) %></strong><br>

        <form action="CheckoutServlet" method="get">
            <button type="submit" class="checkout-btn">Proceed to Checkout</button>
        </form>
    </div>
<% } %>


    <form action="ProductsPage.jsp" method="get" style="text-align: right; margin-right: 80px;">
        <button type="submit" class="continue-shopping-btn">Continue Shopping</button>
    </form>

    </body>
</html>

