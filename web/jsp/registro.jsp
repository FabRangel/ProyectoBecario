<%-- 
    Document   : registro
    Created on : 13 oct 2024, 21:50:26
    Author     : fgmrr
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Registro de Becario</title>
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
            }

            form {
                background-color: #fff;
                border: 1px solid #ddd;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 400px;
            }

            form input[type="text"],
            form input[type="password"],
            form input[type="submit"] {
                width: 100%;
                padding: 10px;
                margin: 5px 0 15px 0;
                border-radius: 4px;
                border: 1px solid #ddd;
                box-sizing: border-box;
            }

            form input[type="submit"] {
                background-color: #4CAF50;
                color: white;
                cursor: pointer;
                border: none;
                font-size: 16px;
                transition: background-color 0.3s ease;
            }

            form input[type="submit"]:hover {
                background-color: #45a049;
            }

            form label {
                margin-right: 10px;
                font-size: 14px;
                color: #555;
            }

            p {
                color: red;
                font-size: 14px;
                text-align: center;
            }

        </style>
    </head>
    <body>
       <!-- <h2>Formulario de Registro de Becario</h2> -->

        <form action="registro" method="POST">
            <input type="hidden" name="accion" value="registro">
            CURP: <input type="text" name="curp" required><br><br>

            Apellido Paterno: <input type="text" name="apellidopat" required><br><br>
            Apellido Materno: <input type="text" name="apellidomat" required><br><br>
            Nombre: <input type="text" name="nombre" required><br><br>
            Género:
            <label><input type="radio" name="genero" value="M" required> Masculino</label>
            <label><input type="radio" name="genero" value="F" required> Femenino</label><br><br>
            Contraseña: <input type="password" name="contrasenia" required><br><br>
            <input type="submit" value="Registrar">
        </form>

    <c:if test="${not empty mensaje}">
        <p>${mensaje}</p>
    </c:if>
</body>
</html>