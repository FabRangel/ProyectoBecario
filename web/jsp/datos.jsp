<%-- 
    Document   : datos
    Created on : 15 oct 2024, 16:54:10
    Author     : fgmrr
--%>
<%@ page import="Model.BecarioModel" %>
<%@ page import="Model.ViviendaModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Perfil del Becario</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 20px;
            }
            .container {
                background-color: #ffffff;
                border: 1px solid #dee2e6;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            h1, h2 {
                color: #333;
            }
            p {
                font-size: 18px;
                margin: 10px 0;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Perfil del Becario</h1>

            <h2>Datos del Becario</h2>
            <p><strong>CURP:</strong> ${curp}</p>
            <p><strong>Nombre:</strong> ${nombre}</p>
            <p><strong>Fecha de nacimiento:</strong> ${fecha_nac}</p>
            <p><strong>Apellido Paterno:</strong> ${apellidopat}</p>
            <p><strong>Apellido Materno:</strong> ${apellidomat}</p>
            <p><strong>Género:</strong> ${genero=='F' ? 'Femenino':'Masculino' }</p>

            <h2>Datos de la Vivienda</h2>
            <p><strong>Calle:</strong> ${calle}</p>
            <p><strong>Colonia:</strong> ${col}</p>
            <p><strong>Municipio:</strong> ${mun}</p>
            <p><strong>Código Postal:</strong> ${cp}</p>
        </div>
    </body>
</html>