/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Configuration.ConnectionBD;
import Model.BecarioModel;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author fgmrr
 */
@WebServlet("/becario/*")

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)

public class Becario extends HttpServlet {

    ConnectionBD conexion = new ConnectionBD();
    Connection conn, conn1;
    PreparedStatement ps, ps1;
    ResultSet rs, rs1;

    String nombre = "";
    String apellidopat = "";
    String apellidomat = "";
    String fecha_nac = "";
    String genero = "";
    String foto = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Becario</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Becario at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String action = request.getPathInfo();

        System.out.println("Acción: " + action);


        // Manejo de las diferentes acciones
        switch (action) {
            case "/logout":
                session.invalidate();
                System.out.println("Sesión invalidada.");
                Cookie cookie = new Cookie("curp", null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);

                response.sendRedirect(request.getContextPath() + "/becario/inicio");
                break;
            case "/registro":
                request.getRequestDispatcher("/jsp/registro.jsp").forward(request, response);
                break;
            case "/inicio":
                request.getRequestDispatcher("/jsp/inicio.jsp").forward(request, response);
                break;
            case "/vivienda":
                request.getRequestDispatcher("/jsp/vivienda.jsp").forward(request, response);
                break;
            case "/datos":
            try {
                String curp = (String) session.getAttribute("curp");
                becarioPorCurp(request, curp);
                viviendaPorCurp(request, curp);
                request.getRequestDispatcher("/jsp/datos.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/becario/inicio"); // Manejo de excepciones
            }
            break;
            default:
                // Redirigir a una página por defecto o de error
                response.sendRedirect(request.getContextPath() + "/becario/inicio");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el tipo de acción (registro o login)
        String accion = request.getParameter("accion");

        if ("registro".equals(accion)) {
            registrarUsuario(request, response);
        } else if ("login".equals(accion)) {
            iniciarSesion(request, response);
        } else if ("guardar_vivienda".equalsIgnoreCase(accion)) {
            System.out.println("entre a guardar");
            guardarVivienda(request, response);
        } else if ("subir_foto".equalsIgnoreCase(accion)) {
            System.out.println("entre a subir");
            subirFotoBecario(request, response);
        } else {
            // Manejar acción no válida
            request.setAttribute("mensaje", "Acción no válida.");
            request.getRequestDispatcher("/jsp/registro.jsp").forward(request, response);
        }
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Registro");
        String curp = request.getParameter("curp");
        String apellidopat = request.getParameter("apellidopat");
        String apellidomat = request.getParameter("apellidomat");
        String nombre = request.getParameter("nombre");
        String genero = request.getParameter("genero");
        String contrasenia = request.getParameter("contrasenia");

        boolean curpValido = validaCurp(curp);
        Date fecha = Date.valueOf(obtenerFechaNacimiento(curp));

        if (curpValido) {
            try {
                conn = conexion.getConnectionBD();

                String sql = "INSERT INTO becario (curp, apellidopat, apellidomat, nombre, genero, contrasenia, fecha_nac) VALUES (?, ?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, curp);
                ps.setString(2, apellidopat);
                ps.setString(3, apellidomat);
                ps.setString(4, nombre);
                ps.setString(5, genero);
                ps.setString(6, contrasenia);
                ps.setDate(7, fecha);

                int filasInsertadas = ps.executeUpdate();

                if (filasInsertadas > 0) {
                    request.setAttribute("mensaje", "Registro exitoso.");
                } else {
                    request.setAttribute("mensaje", "Error al registrar.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("mensaje", "Error en la base de datos.");
            }
            /*   if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }*/

        } else {
            request.setAttribute("mensaje", "CURP no válido, por favor verifique.");
        }
        request.getRequestDispatcher("/jsp/registro.jsp").forward(request, response);
    }

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener los parámetros del formulario
        String curp = request.getParameter("curp");
        String contrasenia = request.getParameter("contrasenia");

        try {
            conn = conexion.getConnectionBD();

            String sql = "SELECT * FROM becario WHERE curp = ? AND contrasenia = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, curp);
            ps.setString(2, contrasenia);
            rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("curp", curp);
                session.setAttribute("nombre", rs.getString("nombre"));
                session.setAttribute("apellidopat", rs.getString("apellidopat"));
                session.setAttribute("apellidomat", rs.getString("apellidomat"));
                session.setAttribute("genero", rs.getString("genero"));
                session.setAttribute("foto", rs.getString("foto"));
                session.setAttribute("fecha_nac", rs.getString("fecha_nac"));

                //Sesión
                int sessionDuration = 2 * 60;
                session.setMaxInactiveInterval(sessionDuration);
                session.setAttribute("sessionDuration", sessionDuration);

                //Cookies
                Cookie cookie = new Cookie("curp", curp);
                cookie.setMaxAge(60 * 15);
                //cookie.setMaxAge(15);
                cookie.setPath("/");
                response.addCookie(cookie);

                System.out.println("Yeii");
                request.setAttribute("mensaje", "Inicio de sesión exitoso.");
                becarioPorCurp(request, curp);
                request.getRequestDispatcher("/jsp/perfil.jsp").forward(request, response);
            } else {
                System.out.println("no Yeii :C");
                request.setAttribute("mensaje", "CURP o contraseña incorrectos.");
                request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error en la base de datos.");
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
        /*  if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            
        }*/
    }

    private boolean validaCurp(String curp) {
        if (curp == null || curp.length() != 18 || !curp.matches("^[A-Za-z]{4}[0-9]{6}[HMhm][A-Za-z]{5}[0-9]{2}$")) {
            return false;
        }

        LocalDate fechaNacimiento = obtenerFechaNacimiento(curp);
        return (fechaNacimiento != null && validarFechaNacimiento(fechaNacimiento));
    }

    private boolean validarFechaNacimiento(LocalDate fechaNacimiento) {
        LocalDate hoy = LocalDate.now();
        return !fechaNacimiento.isAfter(hoy) && hoy.getYear() - fechaNacimiento.getYear() <= 120;
    }

    private LocalDate obtenerFechaNacimiento(String curp) {
        try {
            String fechaString = curp.substring(4, 10); // YYMMDD
            int año = Integer.parseInt(fechaString.substring(0, 2));
            int mes = Integer.parseInt(fechaString.substring(2, 4));
            int día = Integer.parseInt(fechaString.substring(4, 6));

            if (mes < 1 || mes > 12) {
                return null;
            }

            año += (año < 50) ? 2000 : 1900;

            if (!validarDiasDelMes(año, mes, día)) {
                return null;
            }

            return LocalDate.of(año, mes, día);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean validarDiasDelMes(int año, int mes, int día) {
        int[] diasEnMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (mes == 2 && esAnioBisiesto(año)) {
            return día >= 1 && día <= 29;
        }

        return día >= 1 && día <= diasEnMes[mes - 1];
    }

    private boolean esAnioBisiesto(int año) {
        return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void guardarVivienda(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String curp = request.getParameter("curp");
        String calle = request.getParameter("calle");
        String col = request.getParameter("col");
        String mun = request.getParameter("mun");
        String cp = request.getParameter("cp");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            System.out.println("Intentando guardar vivienda");
            con = conexion.getConnectionBD();
            // Si ya existe una vivienda para el becario, se actualiza. Si no, se inserta.
            String query = "INSERT INTO vivienda (calle, col, mun, cp, curp) VALUES (?, ?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE calle = VALUES(calle), col = VALUES(col), mun = VALUES(mun), cp = VALUES(cp)";
            ps = con.prepareStatement(query);
            ps.setString(1, calle);
            ps.setString(2, col);
            ps.setString(3, mun);
            ps.setString(4, cp);
            ps.setString(5, curp);
            ps.executeUpdate();

            request.getRequestDispatcher("/jsp/perfil.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar la vivienda.");
        }
    }

    private void becarioPorCurp(HttpServletRequest request, String curp) throws SQLException {
        ConnectionBD connection = new ConnectionBD();
        conn = connection.getConnectionBD();
        String becarioSQL = "SELECT * FROM becario WHERE curp = ?";

        try {
            ps = conn.prepareStatement(becarioSQL);
            ps.setString(1, curp);
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (rs.next()) {
            request.setAttribute("curp", rs.getString("curp"));
            request.setAttribute("nombre", rs.getString("nombre"));
            request.setAttribute("apellidopat", rs.getString("apellidopat"));
            request.setAttribute("apellidomat", rs.getString("apellidomat"));
            request.setAttribute("genero", rs.getString("genero"));
            request.setAttribute("fecha_nac", rs.getDate("fecha_nac"));
            request.setAttribute("fotoURL", rs.getString("foto"));

        }
    }

    private void viviendaPorCurp(HttpServletRequest request, String curp) throws SQLException {
        ConnectionBD connection = new ConnectionBD();
        conn1 = connection.getConnectionBD();
        String viviendaSQL = "SELECT * FROM vivienda WHERE curp = ?";

        try {
            ps1 = conn1.prepareStatement(viviendaSQL);
            ps1.setString(1, curp);
            rs1 = ps1.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (rs1.next()) {
            request.setAttribute("calle", rs1.getString("calle"));
            request.setAttribute("col", rs1.getString("col"));
            request.setAttribute("mun", rs1.getString("mun"));
            request.setAttribute("cp", rs1.getString("cp"));
        }
    }

    private void subirFotoBecario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el CURP del usuario desde la sesión
        String curp = (String) request.getSession().getAttribute("curp");

        try {
            Part filePart = request.getPart("fotoUsuario");

            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = uploadPath + File.separator + fileName;

            filePart.write(filePath);

            String imageUrl = "images/" + fileName;

            guardarUrlFoto(curp, imageUrl);

            request.getSession().setAttribute("foto", imageUrl);
            request.setAttribute("message", "Foto subida correctamente.");
            request.getRequestDispatcher("/jsp/perfil.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Error al subir la foto.");
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
        }
    }

    private void guardarUrlFoto(String curp, String imageUrl) {

        try {
            ConnectionBD connection = new ConnectionBD();
            conn = connection.getConnectionBD();

            String updateSQL = "UPDATE becario SET foto = ? WHERE curp = ?";
            ps = conn.prepareStatement(updateSQL);
            ps.setString(1, imageUrl);
            ps.setString(2, curp);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
