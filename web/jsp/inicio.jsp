<%-- 
    Document   : inicio
    Created on : 15 oct 2024, 09:46:38
    Author     : fgmrr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iniciar Sesión</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                margin: 0;
                padding: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            h2 {
                text-align: center;
                color: #333;
                margin-bottom: 20px;
            }
            form {
                background-color: #fff;
                border: 1px solid #ddd;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 400px;
                text-align: center;
            }

            form label {
                display: block;
                margin-bottom: 8px;
                font-size: 14px;
                color: #555;
            }
            form input[type="text"],
            form input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border-radius: 4px;
                border: 1px solid #ddd;
                box-sizing: border-box;
            }

            form button {
                width: 100%;
                padding: 10px;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s ease;
            }

            form button:hover {
                background-color: #45a049;
            }

            p {
                color: red;
                font-size: 14px;
            }
        </style>
    </head>
    <body>
       <!-- <h2>Iniciar Sesión</h2> -->
        <form action="becario" method="post">
            <input type="hidden" name="accion" value="login">
            <label for="username">CURP:</label>
            <input type="text" id="curp" name="curp" required> <br>
            <label for="password">Contraseña:</label>
            <input type="password" id="contrasenia" name="contrasenia" required> <br>
            <button type="submit">Iniciar Sesión</button>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <p style="color:red;"><%=error%></p>
            <% }%>
        </form>
    </body>
</html>