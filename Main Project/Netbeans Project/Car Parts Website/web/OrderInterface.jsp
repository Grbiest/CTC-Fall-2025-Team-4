<%-- 
    Document   : OrderInterface
    Created on : Oct 26, 2025, 4:30:12 PM
    Author     : Grant
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

</style>
</head>
<body>
<div class="container">
  <h1>Order Interface</h1>

  <h2>Fulfilled Orders</h2>
  <table>
    <thead>
      <tr>
        <th>Order ID</th>
        <th>Customer</th>
        <th>Date</th>
        <th>Total</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>#1001</td>
        <td>Jane Smith</td>
        <td>2025-10-01</td>
        <td>$120.00</td>
      </tr>
      <tr>
        <td>#1002</td>
        <td>John Doe</td>
        <td>2025-09-30</td>
        <td>$85.50</td>
      </tr>
    </tbody>
  </table>

  <h2>Unfulfilled Orders</h2>
  <table>
    <thead>
      <tr>
        <th>Order ID</th>
        <th>Customer</th>
        <th>Date</th>
        <th>Total</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>#1003</td>
        <td>Mary Johnson</td>
        <td>2025-10-02</td>
        <td>$65.00</td>
      </tr>
      <tr>
        <td>#1004</td>
        <td>Robert Brown</td>
        <td>2025-10-02</td>
        <td>$250.00</td>
      </tr>
    </tbody>
  </table>

  <a href="index.html" class="logout-btn">Logout</a>

</div>
</body>
</html>
