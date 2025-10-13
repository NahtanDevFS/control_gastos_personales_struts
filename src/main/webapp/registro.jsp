<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registro - Control de Gastos</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body class="body-forms">
    <div class="form-container">
        <h1>Crear Nueva Cuenta</h1>

        <s:if test="hasActionErrors()">
            <div class="error-message">
                <s:actionerror/>
            </div>
        </s:if>
        
        <s:form action="registrarUsuario" method="post" theme="simple">
            
            <div class="form-group">
                <label for="nombreUsuario">Nombre de Usuario:</label>
                <s:textfield name="usuario.nombreUsuario" cssClass="form-control" id="nombreUsuario"/>
            </div>

            <div class="form-group">
                <label for="correo">Correo Electrónico:</label>
                <s:textfield name="usuario.correo" cssClass="form-control" id="correo"/>
            </div>
            
            <div class="form-group">
                <label for="clave">Contraseña:</label>
                <s:password name="usuario.clave" cssClass="form-control" id="clave"/>
            </div>

            <div class="form-group">
                <label for="ingresoMensual">Ingreso Mensual (Q):</label>
                <s:textfield name="usuario.ingresoMensual" cssClass="form-control" id="ingresoMensual"/>
            </div>
            
            <div class="text-center">
                <s:submit value="Registrarse" cssClass="btn-primary" />
            </div>
        </s:form>
        
        <div class="form-link">
            <p>¿Ya tienes una cuenta? <a href="login.jsp">Inicia sesión</a></p>
        </div>
    </div>
</body>
</html>