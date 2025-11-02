<%-- 
    Document   : OrderInterface
    Created on : Oct 26, 2025, 4:30:12 PM
    Author     : Grant
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="DB_Objects.DBManager" %>
<%
    DBManager dbm = new DBManager(getServletContext());
    
    String[][] fulfilledOrders = dbm.selectAllCompleteOrdersClean();
    String[][] unfulfilledOrders = dbm.selectAllIncompleteOrdersClean();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Order Interface</title>
<style>
  body {
    margin: 0;
    font-family: Arial, sans-serif;
    background: #F4F4F9;
    color: #000;
  }

  .container {
    max-width: 1920px;
    margin: 0 auto;
    padding: 20px;
  }

  h1 {
    font-size: 32px;
    font-weight: 700;
    margin-bottom: 20px;
  }

  h2 {
    font-size: 24px;
    font-weight: 700;
    margin: 30px 0 10px 0;
  }

  table {
    width: 100%;
    border-collapse: collapse;
    background: #fff;
    box-shadow: 0px 2px 5px rgba(0,0,0,0.1);
    border-radius: 8px;
    overflow: hidden;
    margin-bottom: 40px;
  }

  thead {
    background: #333333;
    color: white;
  }

  th, td {
    padding: 12px;
    text-align: left;
    font-size: 16px;
    font-weight: 400;
    border-bottom: 1px solid #DDDDDD;
  }

  th {
    font-weight: 700;
  }

  .logout-btn {
    display: inline-block;
    background: #D9534F;
    color: white;
    padding: 10px 20px;
    border-radius: 6px;
    font-size: 16px;
    cursor: pointer;
    margin-top: 20px;
  }
  
  .unfulfilled-btn {
  background: #5BC0DE; /* Light blue */
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 400;
  cursor: pointer;
  transition: background 0.3s ease;
}

.unfulfilled-btn:hover {
  background: #31B0D5;
}

</style>

<script>
  function Unfulfilled_Press(productOrderID) {
    
    location.href = "OrderInterface.jsp?unfulfilled=" + encodeURIComponent(productOrderID);
  }
</script>
</head>
<body>
<div class="container">
  <h1>Order Interface</h1>

    <h2>Fulfilled Orders</h2>
    <table>
      <thead>
        <tr>
          <th>Product/Order ID</th>
          <th>Order ID</th>
          <th>Product ID</th>
          <th>Product Name</th>
          <th>Category</th>
          <th>Quantity</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <%
            for (int i = 0; i < fulfilledOrders.length; i++) {
        %>
            <tr>
                <td><%= fulfilledOrders[i][0] %></td>
                <td><%= fulfilledOrders[i][1] %></td>
                <td><%= fulfilledOrders[i][2] %></td>
                <td><%= fulfilledOrders[i][3] %></td>
                <td><%= fulfilledOrders[i][4] %></td>
                <td><%= fulfilledOrders[i][5] %></td>
                <td>
                    <form method="post" action="ToggleFulfilledOrderServlet">
                        <input type="hidden" name="productOrderId" value="<%= fulfilledOrders[i][0] %>">
                        <button type="submit" class="unfulfilled-btn">Open</button>
                    </form>

                </td>
            </tr>

        <%
            }
        %>
        </tbody>

    </table>



  <h2>Unfulfilled Orders</h2>
    <table>
      <thead>
        <tr>
          <th>Product/Order ID</th>
          <th>Order ID</th>
          <th>Product ID</th>
          <th>Product Name</th>
          <th>Category</th>
          <th>Quantity</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <%
            for (int i = 0; i < unfulfilledOrders.length; i++) {
        %>
            <tr>
                <td><%= unfulfilledOrders[i][0] %></td>
                <td><%= unfulfilledOrders[i][1] %></td>
                <td><%= unfulfilledOrders[i][2] %></td>
                <td><%= unfulfilledOrders[i][3] %></td>
                <td><%= unfulfilledOrders[i][4] %></td>
                <td><%= unfulfilledOrders[i][5] %></td>
                <td>
                    <form method="post" action="ToggleFulfilledOrderServlet">
                        <input type="hidden" name="productOrderId" value="<%= unfulfilledOrders[i][0] %>">
                        <button type="submit" class="unfulfilled-btn">Shipped</button>
                    </form>
                </td>
            </tr>
        <%
            }
        %>
      </tbody>
    </table>

  <a href="index.html" class="logout-btn">Logout</a>

</div>
</body>
</html>
