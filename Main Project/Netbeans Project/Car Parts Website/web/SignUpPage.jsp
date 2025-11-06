<%-- 
    Document   : SignUpPage
    Created on : Nov 5, 2025, 11:17:47 PM
    Author     : Grant
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>Signup Page</title>
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <style>
    *, *::before, *::after { box-sizing: border-box; }

    html, body {
      height: 100%;
      background-color: #046A93;
      overflow: hidden;
    }
    
    body {
      margin: 0;
      font-family: Roboto, sans-serif;
      color: black;
      overflow: hidden;
    }

    .container {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: #046A93 url("i/BlueCarLogo.png") no-repeat left top;
      background-size: 482px 271px;
      display: flex;
      align-items: center;
      justify-content: center;
      transform: translateZ(0);
    }

    .card {
      width: 700px;
      max-width: calc(100% - 40px);
      background: #ffffff;
      border-radius: 10px;
      padding: 60px 40px;
      box-shadow: 0 6px 18px rgba(0,0,0,0.08);
      text-align: center;
      overflow: visible;
    }

        .form-box {
      position: absolute;
      top: 214px;
      left: 569px;
      width: 652px;
      height: 596px;
      background: white;
      border-radius: 10px;
      padding: 40px;
      box-sizing: border-box;
    }

    .form-title {
      font-size: 57px;
      font-weight: 400;
      line-height: 64px;
      margin-bottom: 40px;
      text-align: center;
    }

    .form-group {
      margin-bottom: 30px;
    }

    .form-label {
      font-size: 22px;
      font-weight: 400;
      line-height: 28px;
      margin-bottom: 8px;
      text-align: left;
      display: block;
      width: 100%;
    }


    .form-input {
      width: 100%;
      height: 81px;
      background: #D9D9D9;
      border: none;
      border-radius: 7px;
      font-size: 20px;
      padding: 0 15px;
      box-sizing: border-box;
    }

    .submit-btn {
      margin-top: 30px;
      font-size: 28px;
      font-weight: 500;
      line-height: 36px;
      text-align: center;
      cursor: pointer;
    }
  </style>
</head>
<body>
    <div class="container">
          <div class="card" role="main" aria-label="Login card">
              <div class="form-title">Sign Up</div>

              <form onsubmit="return validatePasswords()" action="CreateAccountServlet" method="post">
                    <div class="form-group">
                      <div class="form-label">Email</div>
                      <input type="email" class="form-input" placeholder="Enter your email" name="email" required />
                    </div>
                  
                    <div class="form-group">
                        <div class="form-label">Username</div>
                        <input type="text" class="form-input" placeholder="Enter your username" name ="username" id="username" required/>
                    </div>

                    <div class="form-group">
                      <div class="form-label">Password</div>
                      <input type="password" class="form-input" placeholder="Enter your password" name="password" id="password" required />
                    </div>

                    <div class="form-group">
                      <div class="form-label">Confirm Password</div>
                      <input type="password" class="form-input" placeholder="Confirm your password" name="confirmPassword" id="confirmPassword" required />
                    </div>

                    <div id="error-message" style="color: red; font-size: 18px; text-align: right; display: none;">
                      ERROR: Passwords must match.
                    </div>

                    <div style="display: flex; justify-content: flex-end;">
                        <button type="submit" class="submit-btn" style="color: black; background: none; border: none;">
                          Create Account
                        </button>
                    </div>

                  </form>



          </div>
    </div>
<script>
  function validatePasswords() {
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const errorMessage = document.getElementById("error-message");

    if (password !== confirmPassword) {
      errorMessage.style.display = "block";
      return false; // Prevent form submission
    }

    errorMessage.style.display = "none";
    return true; // Allow form submission
  }
</script>
</body>
</html>


