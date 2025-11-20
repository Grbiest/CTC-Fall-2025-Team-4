<%-- 
    Document   : SearchResults
    Created on : Nov 19, 2025, 4:01:19 PM
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

      text-decoration: none;   /* Remove underline */
      color: #000;             /* Text color black */
    }

    .btn:hover {
      text-decoration: underline;
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
    String[][] searchResults = (String[][]) session.getAttribute("searchResults");
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

  <!-- HERO SECTION -->
  <div class="hero"></div>

  <!-- PRODUCT GRID -->
    <section class="product-grid">
    <%
        if (searchResults == null || searchResults.length == 0) {
    %>
            <div style="font-size: 28px; color: #555; text-align: center; width: 100%; padding: 60px;">
                Unable to find any items matching your search.
            </div>
    <%
        } else {
            for (int i = 0; i < searchResults.length; i++) {
                String imageUrl = searchResults[i][6]; 
                String productName = searchResults[i][1];
                String price = searchResults[i][2];
                String productLink = searchResults[i][5];
    %>
                <div class="product">
                    <img src="<%= request.getContextPath() %>/<%= imageUrl %>" alt="<%= productName %>">
                    <div class="product-name"><%= productName %></div>
                    <div class="price">$<%= String.format("%.2f", Double.parseDouble(price)) %></div>
                    <a href="<%= request.getContextPath() %>/<%= productLink %>" class="btn">View Product</a>
                </div>
    <%
            }
        }
    %>
    </section>


  
  <!--This next section will activate only if the user is a guest and tries to leave, and will log the user out.-->
  <script>
      if (user instanceof DB_Objects.Guest) {
            navigator.sendBeacon("UserExitServlet", data);
        window.addEventListener("beforeunload", function (e) {
          // Optional: send a request to the server or log activity
          navigator.sendBeacon("UserExitServlet");
          Console.log("Leaving ProductsPage");

          // Optional: show a confirmation dialog (not supported in all browsers)
          // e.preventDefault();
          // e.returnValue = '';
        });
      }
</script>
</body>
</html>
