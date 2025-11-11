<%-- 
    Document   : AccountPage
    Created on : Nov 6, 2025, 12:52:31 AM
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

    input[type="text"] {
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
          <button type="submit" class="nav-btn">Cart (0)</button>
        </form>
    </div>


  </header>

  <!-- HERO SECTION -->
  <div class="hero"></div>

  <!-- Account form -->
    <main>
      <form class="account-card" action="UpdateAccountServlet" method="post">
        <h2>Account Information</h2>

        <label for="firstname">First Name</label>
        <input type="text" id="firstname" name="firstname" value="<%= user.getFirstName() %>">

        <label for="lastname">Last Name</label>
        <input type="text" id="lastname" name="lastname" value="<%= user.getLastName() %>">

        <label for="email">Email</label>
        <input type="text" id="email" name="email" value="<%= user.getEmail() %>">
        
        <label for="phone">Phone</label>
        <%
        String rawPhone = user.getPhoneNumber();
        String formattedPhone = rawPhone.length() == 10
            ? rawPhone.substring(0, 3) + "-" + rawPhone.substring(3, 6) + "-" + rawPhone.substring(6)
            : rawPhone; // fallback if not 10 digits
%>
        <input type="text" id="phone" name="phone" value="<%= formattedPhone %>"
               pattern="\d{3}-\d{3}-\d{4}" 
               placeholder="123-456-7890" 
               title="Format: 123-456-7890">

        <div class="section-title">Billing Address</div>
        <label for="street">Street</label>
        <input type="text" id="street" name="street" value="<%= user.getBillingStreet() %>">

        <label for="city">City</label>
        <input type="text" id="city" name="city" value="<%= user.getBillingCity() %>">

        <label for="state">State (Initials)</label>
        <input type="text" id="state" name="state" maxlength="2" pattern="[A-Za-z]{2}" value="<%= user.getBillingState() %>">

        <label for="zip">Zip Code</label>
        <input type="text" id="zip" name="zip" pattern="\d{5}" value="<%= user.getBillingZip() %>">

        <div class="checkbox-row">
          <input type="checkbox" id="sameAddress" name="sameAddress">
          <label for="sameAddress">Shipping address same as billing address</label>
        </div>

        <div class="section-title">Shipping Address</div>
        <label for="sStreet">Street</label>
        <input type="text" id="sStreet" name="sStreet" value="<%= user.getShippingStreet() %>">

        <label for="sCity">City</label>
        <input type="text" id="sCity" name="sCity" value="<%= user.getShippingCity() %>">

        <label for="sState">State (Initials)</label>
        <input type="text" id="sState" name="sState" maxlength="2" pattern="[A-Za-z]{2}" value="<%= user.getShippingState() %>">

        <label for="sZip">Zip Code</label>
        <input type="text" id="sZip" name="sZip" pattern="\d{5}" value="<%= user.getShippingZip() %>">
        
        <button type="button" class="logout-btn" style="background-color: #000;" onclick="window.location.href='ChangeCredentialsServlet'">
          Change username/password
        </button>

        <button type="submit" class="logout-btn">Change Account Info</button>
      </form>
    </main>
    <script>
        const checkbox = document.getElementById('sameAddress');

        const billingFields = {
          street: document.getElementById('street'),
          city: document.getElementById('city'),
          state: document.getElementById('state'),
          zip: document.getElementById('zip')
        };

        const shippingFields = {
          street: document.getElementById('sStreet'),
          city: document.getElementById('sCity'),
          state: document.getElementById('sState'),
          zip: document.getElementById('sZip')
        };

        // Store original shipping values
        const originalShipping = {
          street: shippingFields.street.value,
          city: shippingFields.city.value,
          state: shippingFields.state.value,
          zip: shippingFields.zip.value
        };

        checkbox.addEventListener('change', function () {
          if (this.checked) {
            // Copy billing to shipping
            shippingFields.street.value = billingFields.street.value;
            shippingFields.city.value = billingFields.city.value;
            shippingFields.state.value = billingFields.state.value;
            shippingFields.zip.value = billingFields.zip.value;

            // Optionally disable shipping fields
            for (let key in shippingFields) {
              shippingFields[key].readOnly = true;
            }
          } else {
            // Restore original shipping values
            shippingFields.street.value = originalShipping.street;
            shippingFields.city.value = originalShipping.city;
            shippingFields.state.value = originalShipping.state;
            shippingFields.zip.value = originalShipping.zip;

            // Re-enable shipping fields
            for (let key in shippingFields) {
              shippingFields[key].readOnly = false;
            }
          }
        });
    </script>


</body>
</html>
