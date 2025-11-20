<%-- 
    Document   : Sylvania XtraVision Headlight Bulb H11XV
    Created on : Nov 19, 2025, 1:43:27 AM
    Author     : Grant
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Auto Parts Catalog</title>
  <style>
    :root{
      --bg: #F4F6F9;
      --muted: #D9D9D9;
      --text: #1e1e1e;
      --outline: #1E1E1E;
      --app-width: 1400px;
      --app-height: auto;
    }
      
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

    .hero {
      width: 100%;
      height: 243px;
      background: #046A93;
      margin-top: 30px;
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
      
      .canvas-wrapper {
        display: flex;
        justify-content: center;
        margin-top: 60px;
        width: 100%;
      }

      
    .canvas{
      width:var(--app-width);
      background:var(--bg);
      border-radius:6px;
      padding:40px;
      display:grid;
      grid-template-columns: 1fr 1fr;  /* ★ Two columns */
      gap:60px;
    }

    /* LEFT COLUMN */
    .left-col {
        display: flex;
        flex-direction: column;
        gap: 24px;
        align-items: flex-start;   /* ensures image box doesn’t stretch */
    }

    .product-image {
        display: inline-block;      
        background: var(--muted);   
        border-radius: 6px;         
        overflow: hidden;           
        padding: 0;                 
        margin: 0;                  
    }
  
    .product-image img {
        display: block;             
        width: auto;                
        height: auto;               
        max-width: 100%;            
        max-height: 100%;           
    }



    .title{font-size:26px; font-weight:400; line-height:32px;}
    .sku, .price, .stock{font-size:20px; display:flex; align-items:center;}

    .qty-row{
      display:flex;
      align-items:center;
      gap:16px;
      font-size:24px;
    }

    .qty-controls{
      display:flex;
      align-items:center;
      gap:10px;
    }

    .qty-btn{
      width:36px;
      height:36px;
      display:flex;
      align-items:center;
      justify-content:center;
      border-radius:6px;
      border:1px solid #ccc;
      cursor:pointer;
      background:white;
      font-size:20px;
      user-select:none;
    }

    .add-to-cart{
      background:none;
      border:none;
      cursor:pointer;
      font-size:24px;
      text-decoration:underline;
      padding:0;
      align-self:flex-start;
    }

    /* RIGHT COLUMN */
    .right-col{
      padding-top:20px;
      display:flex;
      flex-direction:column;
      gap:20px;
    }

    .section-title{
      font-size:26px;
      font-weight:500;
    }

    .description{
      font-size:20px;
      line-height:32px;
      white-space:pre-wrap;
    }

  </style>
</head>
<body>
    <%
    DB_Objects.User user = (DB_Objects.User) session.getAttribute("user");
    DB_Objects.DBManager dbm = (DB_Objects.DBManager) session.getAttribute("dbm");
    String prodId = "10007";
    String[] productArray = dbm.selectFromInventoryByProdID(prodId);
    String productName = productArray[1];
    String unformattedPrice = productArray[2];
    float priceValue = Float.parseFloat(unformattedPrice);
    String price = String.format("$%.2f", priceValue);

    String category = productArray[3];
    String quantity = productArray[4];
    int qty = Integer.parseInt(quantity);
    String imageLink = productArray[6];
    String description = productArray[7];
    
%>
    <!-- HEADER -->
    <header>
        <a href="<%= request.getContextPath() %>/ProductsPage.jsp" class="logo"></a>

        <form class="search-bar" action="<%= request.getContextPath() %>/SearchServlet" method="post">
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
            <form action="<%= request.getContextPath() %>/index.html" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Login</button>
            </form>

            <!-- Sign Up Form -->
            <form action="<%= request.getContextPath() %>/SignUpServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Sign Up</button>
            </form>
            
            <!-- Cart Form -->
            <form action="<%= request.getContextPath() %>/CartServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Cart (<%= user.getCartQuantity() %>)</button>
            </form>
        <%
          } else if (user instanceof DB_Objects.Customer) {
          
        %>
            <!-- Account Form -->
            <form action="<%= request.getContextPath() %>/AccountServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Account</button>
            </form>

            <!-- Sign Out Form -->
            <form action="<%= request.getContextPath() %>/index.html" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Sign Out</button>
            </form>
            
            <!-- Cart Form -->
            <form action="<%= request.getContextPath() %>/CartServlet" method="post" style="display: inline;">
              <button type="submit" class="nav-btn">Cart (<%= user.getCartQuantity() %>)</button>
            </form>
        <%
          }
        %>


    </div>


  </header>

    <div class="canvas-wrapper">
      <div class="canvas">

      <!-- LEFT COLUMN -->
    <div class="left-col">

        <div class="product-image">
          <img src="<%= request.getContextPath() %>/<%= imageLink %>" alt="Product image">
        </div>

        <div class="title"><%= productName %></div>
        <div class="sku">Category: <strong style="margin-left:6px;"><%= category %></strong></div>
        <div class="price">Price: <strong style="margin-left:6px;"><%= price %></strong></div>
        <% if (qty > 0) { %>
            <!-- Add to Cart Form -->
            <form action="<%= request.getContextPath() %>/AddProductToCartServlet" method="post" id="addToCartForm">
                <!-- Hidden field for product ID -->
                <input type="hidden" name="prodID" value="<%= productArray[0] %>">
                <!-- Hidden field for quantity -->
                <input type="hidden" name="quantity" id="formQtyValue" value="1">

                <div class="qty-row">
                  <span>Qty</span>
                  <div class="qty-controls">
                    <button type="button" class="qty-btn" id="qtyMinus">-</button>
                    <div id="qtyValue" style="min-width:40px; text-align:center;">1</div>
                    <button type="button" class="qty-btn" id="qtyPlus">+</button>
                  </div>
                </div>

                <button type="submit" class="add-to-cart" id="addToCartBtn">Add to Cart</button>


            </form>
        <% } %>
        <div id="toast" 
            style="
                position: fixed;
                top: 33%;                 /* ⬅ top third of the screen */
                left: 50%;                /* center horizontally */
                transform: translateX(-50%);
                background: #046A93;
                color: white;
                padding: 18px 28px;
                border-radius: 6px;
                font-size: 20px;
                opacity: 0;
                pointer-events: none;
                transition: opacity .5s ease;
                z-index: 9999;
            ">
        </div>


       <script>
           function showToast(message) {
               const toast = document.getElementById("toast");
               toast.textContent = message;
               toast.style.opacity = "1";
               setTimeout(() => {
                   toast.style.opacity = "0";
               }, 2500); // Hide after 2.5 seconds
           }
       </script>

        <div class="stock">
            Stock:
            <strong style="margin-left:6px;">
                <%
                    

                    if (qty == 0) {
                        out.print("Out of Stock");
                    } else if (qty > 0 && qty <= 10) {
                        out.print("Only " + qty + " left in stock!");
                    } else {
                        out.print("In Stock");
                    }
                %>
            </strong>
        </div>



    </div>

      <!-- RIGHT COLUMN -->
      <div class="right-col">
        <div class="section-title">PRODUCT DESCRIPTION</div>

        <div class="description">
            <%= description %>
        </div>
      </div>

    </div>
  </div>

  
  <!--This next section will activate only if the user is a guest and tries to leave, and will log the user out.-->
    <script>
        // Convert Java server-side check into JS boolean
        const isGuest = <%= (user instanceof DB_Objects.Guest) ? "true" : "false" %>;

        if (isGuest) {
            navigator.sendBeacon("UserExitServlet");

            window.addEventListener("beforeunload", function () {
                navigator.sendBeacon("UserExitServlet");
                console.log("Leaving Product Page");
            });
        }

        // Quantity controls
        const qtyValueDiv = document.getElementById("qtyValue");
        const qtyHiddenInput = document.getElementById("formQtyValue");

        document.getElementById("qtyPlus").addEventListener("click", function () {
            let q = parseInt(qtyValueDiv.textContent);
            q++;
            qtyValueDiv.textContent = q;
            qtyHiddenInput.value = q; // update hidden input
        });

        document.getElementById("qtyMinus").addEventListener("click", function () {
            let q = parseInt(qtyValueDiv.textContent);
            if (q > 1) q--;
            qtyValueDiv.textContent = q;
            qtyHiddenInput.value = q; // update hidden input
        });
    </script>
<%
    String addItemResult = (String) request.getAttribute("addItemResult");
    boolean success = request.getAttribute("addedToCart") != null;
%>

    <script>
    document.addEventListener("DOMContentLoaded", function () {

        <% if (addItemResult != null && addItemResult.startsWith("ERROR")) { %>
            showToast("<%= addItemResult %>");
        <% } else if (success) { %>
            showToast("Item(s) successfully added to cart!");
        <% } %>

    });
    </script>

</body>
</html>



