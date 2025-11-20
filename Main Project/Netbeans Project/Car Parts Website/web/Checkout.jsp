<%-- 
    Document   : Checkout
    Created on : Nov 18, 2025, 10:08:48 PM
    Author     : Grant
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Checkout</title>

<style>
    body {
        margin: 0;
        background: #F4F4F9;
        font-family: Arial, sans-serif;
    }

    h1 {
        margin: 20px;
        font-size: 32px;
        font-weight: bold;
        color: #333;
    }

    .checkout-box {
        width: 640px;
        margin: 20px auto;
        background: white;
        border-radius: 8px;
        padding: 40px 30px;
        box-shadow: 0px 2px 6px rgba(0,0,0,0.1);
    }

    .section-title {
        font-size: 24px;
        font-weight: bold;
        color: #444;
        margin-bottom: 20px;
    }

    label {
        display: block;
        font-size: 16px;
        font-weight: bold;
        margin-top: 15px;
    }

    input[type="text"],
    input[type="number"] {
        width: 100%;
        height: 36px;
        margin-top: 5px;
        border: 1px solid #ccc;
        border-radius: 5px;
        padding: 5px 10px;
        font-size: 16px;
        box-sizing: border-box;
    }

    .checkbox-row {
        display: flex;
        align-items: center;
        margin-top: 20px;
    }

    .checkbox-row input {
        margin-right: 10px;
        transform: scale(1.3);
    }

    .button-row {
        display: flex;
        justify-content: space-between;
        margin-top: 30px;
    }

    .btn {
        padding: 10px 20px;
        border-radius: 6px;
        color: white;
        cursor: pointer;
        font-size: 16px;
        border: none;
    }

    .btn-blue { background: #007BFF; }
    .btn-gray { background: #6C757D; }
    .btn-green { background: #28A745; }
</style>

<script>
    // Toggle shipping fields
    function toggleShippingFields() {
        const checked = document.getElementById("sameAddress").checked;

        const billStreet = document.getElementById("billStreet");
        const billCity   = document.getElementById("billCity");
        const billState  = document.getElementById("billState");
        const billZip    = document.getElementById("billZip");

        const shipStreet = document.getElementById("shipStreet");
        const shipCity   = document.getElementById("shipCity");
        const shipState  = document.getElementById("shipState");
        const shipZip    = document.getElementById("shipZip");

        if (checked) {
            shipStreet.value = billStreet.value;
            shipCity.value = billCity.value;
            shipState.value = billState.value;
            shipZip.value = billZip.value;

            shipStreet.disabled = true;
            shipCity.disabled = true;
            shipState.disabled = true;
            shipZip.disabled = true;
        } else {
            shipStreet.disabled = false;
            shipCity.disabled = false;
            shipState.disabled = false;
            shipZip.disabled = false;

            shipStreet.value = "";
            shipCity.value = "";
            shipState.value = "";
            shipZip.value = "";
        }
    }

    // Credit card formatting (auto dashes)
    function formatCardNumber(field) {
        let val = field.value.replace(/\D/g, '');
        val = val.substring(0,16);
        let formatted = '';
        for (let i=0; i<val.length; i+=4) {
            if (formatted !== '') formatted += '-';
            formatted += val.substring(i,i+4);
        }
        field.value = formatted;
    }

    // Expiration date formatting MM/YY
    function formatExpDate(field) {
        let val = field.value.replace(/\D/g, '');
        val = val.substring(0,4);
        if (val.length >= 3) {
            val = val.substring(0,2) + '/' + val.substring(2,4);
        }
        field.value = val;
    }

    // Only allow numbers for CVV and Exp
    function allowOnlyNumbers(evt) {
        let char = String.fromCharCode(evt.which);
        if (!(/[0-9]/.test(char))) {
            evt.preventDefault();
        }
    }

    window.onload = toggleShippingFields;
</script>
</head>
<body>
<%
    DB_Objects.User user = (DB_Objects.User) session.getAttribute("user");
    DB_Objects.DBManager dbm = (DB_Objects.DBManager) session.getAttribute("dbm");
    String userId = user.getUserId();
    String[][] cart = dbm.selectCartFromUserID(userId);
    String userRole = user.getRole();

    String billStreet = "", billCity = "", billState = "", billZip = "";
    String shipStreet = "", shipCity = "", shipState = "", shipZip = "";

    if (user instanceof DB_Objects.Customer) {
    DB_Objects.Customer cust = (DB_Objects.Customer) user; // cast to Customer

    billStreet = cust.getBillingStreet();
    billCity   = cust.getBillingCity();
    billState  = cust.getBillingState();
    billZip    = cust.getBillingZip();

    shipStreet = cust.getShippingStreet();
    shipCity   = cust.getShippingCity();
    shipState  = cust.getShippingState();
    shipZip    = cust.getShippingZip();
    }
%>

<h1>Checkout</h1>

<form action="OrderServlet" method="post" class="checkout-box">

    <!-- Credit Card -->
    <div class="section-title">Credit Card Information</div>

    <label>Credit Card Number</label>
    <input type="text" id="cardNumber" name="cardNumber" maxlength="19" placeholder="1234-5678-9012-3456" required
           oninput="formatCardNumber(this)">

    <label>CVV</label>
    <input type="text" id="cvv" name="cvv" maxlength="3" placeholder="123" required
           onkeypress="allowOnlyNumbers(event)">

    <label>Expiration Date</label>
    <input type="text" id="expDate" name="expDate" maxlength="5" placeholder="MM/YY" required
           oninput="formatExpDate(this)" onkeypress="allowOnlyNumbers(event)">

    <!-- Billing Address -->
    <div class="section-title" style="margin-top:35px;">Billing Address</div>

    <label>Street</label>
    <input type="text" id="billStreet" name="billStreet" value="<%= billStreet %>" required>

    <label>City</label>
    <input type="text" id="billCity" name="billCity" value="<%= billCity %>" required>

    <label>State (Initials)</label>
    <input type="text" id="billState" name="billState" maxlength="2" value="<%= billState %>" required>

    <label>Zip Code</label>
    <input type="text" id="billZip" name="billZip" maxlength="10" value="<%= billZip %>" required>

    <!-- Shipping Checkbox -->
    <div class="checkbox-row">
        <input type="checkbox" id="sameAddress" onchange="toggleShippingFields()">
        <span style="font-size:16px; font-weight:bold;">Shipping address same as billing address</span>
    </div>

    <!-- Shipping Address -->
    <div class="section-title" style="margin-top:35px;">Shipping Address</div>

    <label>Street</label>
    <input type="text" id="shipStreet" name="shipStreet" value="<%= shipStreet %>" required>

    <label>City</label>
    <input type="text" id="shipCity" name="shipCity" value="<%= shipCity %>" required>

    <label>State (Initials)</label>
    <input type="text" id="shipState" name="shipState" maxlength="2" value="<%= shipState %>" required>

    <label>Zip Code</label>
    <input type="text" id="shipZip" name="shipZip" maxlength="10" value="<%= shipZip %>" required>

    <!-- Buttons -->
    <div class="button-row">
        <form action="ProductsPage.jsp" method="get" style="display:inline;">
            <button type="submit" class="btn btn-blue">Continue Shopping</button>
        </form>

        <form action="CartServlet" method="post" style="display:inline;">
            <button type="submit" class="btn btn-gray">Back to Cart</button>
        </form>

        <button type="submit" class="btn btn-green">Order</button>
    </div>

</form>
</body>
</html>
