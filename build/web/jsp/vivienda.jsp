<%-- 
    Document   : vivienda
    Created on : 15 oct 2024, 15:59:14
    Author     : fgmrr
--%>

<%@page import="Configuration.ConnectionBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<%@page import="javax.servlet.http.*" %>

<%
    String curp = (String) session.getAttribute("curp");

    String calle = "";
    String col = "";
    String mun = "";
    String cp = "";

    ConnectionBD conexion = new ConnectionBD();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = conexion.getConnectionBD();

        String query = "SELECT calle, col, mun, cp FROM vivienda WHERE curp = ?";
        ps = con.prepareStatement(query);
        ps.setString(1, curp);
        rs = ps.executeQuery();

        if (rs.next()) {
            calle = rs.getString("calle");
            col = rs.getString("col");
            mun = rs.getString("mun");
            cp = rs.getString("cp");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Registro de Vivienda</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 20px;
            }

            .form-container {
                background-color: #ffffff;
                padding: 20px;
                border: 1px solid #dee2e6;
                border-radius: 5px;
                width: 50%;
                margin: auto;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }

            .form-container h2 {
                text-align: center;
                color: #333;
            }

            .form-group {
                margin-bottom: 15px;
            }

            .form-group label {
                display: block;
                margin-bottom: 5px;
            }

            .form-group input[type="text"], .form-group input[type="number"] {
                width: 100%;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            .form-group button {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
                width: 100%;
            }

            .form-group button:hover {
                background-color: #0056b3;
            }

        </style>

    </head>
    <body>
        <span id="timer"></span> 
        <div class="form-container">
            <h2>Registrar/Actualizar Vivienda</h2>
            <form action="becario" method="post">
                <input type="hidden" name="accion" value="guardar_vivienda">
                <div class="form-group">
                    <label for="calle">Calle:</label>
                    <input type="text" id="calle" name="calle" value="<%= calle%>" required>
                </div>
                <div class="form-group">
                    <label for="col">Colonia:</label>
                    <input type="text" id="col" name="col" value="<%= col%>" required>
                </div>
                <div class="form-group">
                    <label for="mun">Municipio:</label>
                    <input type="text" id="mun" name="mun" value="<%= mun%>" required>
                </div>
                <div class="form-group">
                    <label for="cp">Código Postal:</label>
                    <input type="text" id="cp" name="cp" maxlength="5" value="<%= cp%>" required>
                </div>
                <input type="hidden" name="curp" value="<%= curp%>">

                <div class="form-group">
                    <button type="submit">Guardar Vivienda</button>
                </div>
            </form>
        </div>

    </body>
</html>