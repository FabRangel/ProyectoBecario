<%-- 
    Document   : perfil
    Created on : 15 oct 2024, 10:10:02
    Author     : fgmrr
--%>
<%
    //Obtener la sesión actual
    HttpSession session_web = request.getSession(false);
    //No crear una nueva sessión si existe

    String curp = null;
    if (session_web != null) {
        //Recuperar el nombre de usuario de la sesión
        curp = (String) session_web.getAttribute("curp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Perfil de Usuario</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .container {
                display: flex;
                background-color: #ffffff;
                border: 1px solid #dee2e6;
                border-radius: 5px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                width: 80%;
                max-width: 1200px;
                overflow: hidden;
            }

            .user-photo {
                flex: 1;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                padding: 20px;
                background-color: #f1f1f1;
            }

            .user-photo img {
                width: 90%;
                height: auto;
                border-radius: 50%;
                object-fit: cover;
                border: 2px solid #007bff;
            }

            .user-photo button {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 15px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
                margin-top: 20px;
            }

            .user-photo button:hover {
                background-color: #0056b3;
            }

            .user-info {
                flex: 2;
                padding: 40px;
                display: flex;
                flex-direction: column;
                justify-content: center;
            }

            h1, h2 {
                color: #333;
                margin-bottom: 20px;
            }

            .user-info p {
                font-size: 18px;
                margin: 10px 0;
            }

            .register-button {
                background-color: #cccccc;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s;
                position: absolute;
                top: 10px;
                right: 10px;
                margin-left: 10px;
            }

            .logout-button {
                background-color: #cccccc;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s;
                position: absolute;
                top: 10px;
                left: 10px;
                margin-right: 10px;
            }

            .register-button:hover, .logout-button:hover {
                background-color: #218838;
            }

            .logout-button {
                left: 120px; /* Ajusta la posición del botón de salir */
                background-color: #dc3545; /* Color rojo para indicar salida */
            }

            .logout-button:hover {
                background-color: #c82333;
            }
            #timer {
                font-family: 'Courier New', Courier, monospace;
                font-size: 30px;
                color: #ffffff;
                background-color: #333;
                border-radius: 10px;
                padding: 10px 20px;
                text-align: center;
                width: 150px;
                position: fixed;
                bottom: 20px;
                right: 20px;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.3);
                z-index: 1000;
            }
        </style>
        <script type="text/javascript">
            window.onload = function () {
                var sessionDuration = <%= session_web.getAttribute("sessionDuration")%>;
                var conteoRegresivo = sessionDuration;

                function actualizarConteo() {
                    var minutos = Math.floor(conteoRegresivo / 60);
                    var segundos = conteoRegresivo % 60;

                    // Agrega un 0 si los segundos son menos de 10
                    segundos = segundos < 10 ? '0' + segundos : segundos;

                    document.getElementById("timer").innerHTML = minutos + ":" + segundos;

                    // Si el contador llega a cero, redirigir al usuario
                    if (conteoRegresivo <= 0) {
                        clearInterval(intervaloTiempo);
                        alert("Tu sesión ha expirado.");
                        window.location.href = "/becario/becario/inicio"; // Redirigir al login
                    } else {
                        conteoRegresivo--; // Reducir el contador
                    }
                }
                const resetTimer = () => {
                    conteoRegresivo = 60 * 2;
                    session_web.setAttribute("sessionDuration", conteoRegresivo);
                };

                // Configurar los eventos para detectar actividad del usuario
                window.onload = resetTimer;
                window.onmousemove = resetTimer;
                window.onkeypress = resetTimer;


                // Actualizar el contador cada segundo
                var intervaloTiempo = setInterval(actualizarConteo, 1000);

                function checkSession() {
                    fetch('checkSession')
                            .then(response => {
                                if (response.redirected) {
                                    window.location.href = response.url; 
                                }
                            })
                            .catch(error => {
                                console.error('Error checking session:', error);
                            });
                }

                setInterval(checkSession, 60*60*15); 
            }
        </script>
    </head>
    <body>
        <span id="timer"></span> 
        <button class="logout-button" onclick="window.location.href = '/becario/sesion';">Salir</button>
        <button class="register-button" onclick="window.location.href = '/becario/becario/vivienda';">Datos de la Vivienda</button>

        <div class="container">
            <!-- Sección de la imagen del usuario -->
            <div class="user-photo">
                <img src="<%= request.getContextPath()%>/${foto}" alt="Foto de becario">
                <form action="becario" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="accion" value="subir_foto">
                    <input type="file" id="fotoUsuario" name="fotoUsuario" accept="image/*" style="display:none;" onchange="this.form.submit();">
                    <button type="button" onclick="document.getElementById('fotoUsuario').click();">Añadir/Modificar Foto</button>
                </form>
            </div>

            <!-- Sección de información del usuario -->
            <div class="user-info">
                <h1>Bienvenido, ${nombre} ${apellidopat} ${apellidomat}!</h1>

                <h2>Datos del Usuario</h2>
                <p><strong>Nombre:</strong> ${nombre}</p>
                <p><strong>CURP:</strong>${curp}</p>
                <p><strong>Fecha de nacimiento:</strong> ${fecha_nac}</p>
                <p><strong>Apellido Paterno:</strong> ${apellidopat}</p>
                <p><strong>Apellido Materno:</strong> ${apellidomat}</p>
                <p><strong>Género:</strong> ${genero == 'F' ? 'Femenino' : 'Masculino'}</p>

                <button class="" onclick="window.location.href = '/becario/becario/datos';">Ver más</button>

            </div>
        </div>

    </body>
</html>