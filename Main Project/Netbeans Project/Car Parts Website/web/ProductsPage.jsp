<%-- 
    Document   : Products
    Created on : Oct 26, 2025, 2:02:52 PM
    Author     : Grant
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Auto Parts Catalog</title>
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
      background: url("https://placehold.co/423x214") no-repeat center;
      background-size: contain;
      display: block; /* ensure the <a> tag behaves like a block for width/height */
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

    /* Product grid */
    .product-grid {
      display: flex;
      justify-content: center;
      gap: 40px;
      flex-wrap: wrap;
      padding: 60px 150px;
    }

    .product {
      width: 303px;
      border: 2px solid #000;
      background: #fff;
      text-align: center;
      padding: 20px;
      box-sizing: border-box;
    }

    .product img {
      width: 100%;
      height: auto;
      max-height: 200px;
      object-fit: contain;
      margin-bottom: 15px;
    }

    .product-name {
      font-size: 24px;
      margin: 10px 0;
    }

    .price {
      font-size: 24px;
      margin-bottom: 15px;
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

    if (user != null) {
        if (user instanceof DB_Objects.Customer) {
            out.println("Welcome, valued customer " + user.getUsername() + "!");
        } else if (user instanceof DB_Objects.Guest) {
            out.println("Welcome, guest!");
        } else {
            out.println("Unknown user type.");
        }
    } else {
        out.println("No user in session.");
    }
%>
    <!-- HEADER -->
    <header>
        <form id="logoForm" action="loginCar" method="post" style="display: inline;">
            <div class="logo" onclick="document.getElementById('logoForm').submit();"></div>
        </form>



        <form class="search-bar" action="SearchServlet" method="post">
          <input type="text" name="query" placeholder="Search for products..." 
                 style="flex: 1; font-size: 24px; padding: 10px; border: none; outline: none;">
          <button type="submit" 
                  style="font-size: 24px; padding: 10px 20px; border: none; background: #046A93; color: #fff; cursor: pointer;">
            Search
          </button>
        </form>


        <div class="nav-links">
            <!-- Login Form -->
            <form action="LoginFromGuestServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Login</button>
            </form>

            <!-- Sign Up Form -->
            <form action="SignUpServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Sign Up</button>
            </form>

            <!-- Cart Form -->
            <form action="CartServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Cart (0)</button>
            </form>
          </div>

  </header>

  <!-- HERO SECTION -->
  <div class="hero"></div>

  <!-- PRODUCT GRID -->
  <section class="product-grid">
    <div class="product">
      <img src="https://placehold.co/196x203" alt="Battery">
      <div class="product-name">Battery</div>
      <div class="price">$172.59</div>
      <div class="btn">View Product</div>
    </div>

    <div class="product">
      <img src="https://placehold.co/174x174" alt="Strut">
      <div class="product-name">Strut</div>
      <div class="price">$299.64</div>
      <div class="btn">View Product</div>
    </div>

    <div class="product">
      <img src="https://placehold.co/234x175" alt="Spark Plug">
      <div class="product-name">Spark Plug</div>
      <div class="price">$13.99</div>
      <div class="btn">View Product</div>
    </div>

    <div class="product">
      <img src="https://placehold.co/171x171" alt="Headlight Bulb">
      <div class="product-name">Headlight Bulb</div>
      <div class="price">$22.99</div>
      <div class="btn">View Product</div>
    </div>
  </section>
</body>
</html>
