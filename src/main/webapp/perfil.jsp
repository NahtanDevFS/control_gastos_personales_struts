<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mi Perfil</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body class="body-forms">
    <div class="form-container">
        <h1>Editar Mi Perfil</h1>
        
        <s:if test="hasActionMessages()">
            <div class="success-message">
                <s:actionmessage/>
            </div>
        </s:if>

        <s:form action="actualizarPerfil" method="post" theme="simple">
            <s:hidden name="usuario.id"/>
            
            <div class="form-group">
                <label for="nombreUsuario">Nombre de Usuario:</label>
                <s:textfield name="usuario.nombreUsuario" cssClass="form-control" id="nombreUsuario"/>
            </div>

            <div class="form-group">
                <label for="correo">Correo Electrónico:</label>
                <s:textfield name="usuario.correo" cssClass="form-control" id="correo"/>
            </div>

            <div class="form-group">
                <label for="ingresoMensual">Ingreso Mensual (Q):</label>
                <s:textfield name="usuario.ingresoMensual" cssClass="form-control" id="ingresoMensual"/>
            </div>

            <hr/>
            <p style="font-size: 0.9em; color: #666;">Deja el campo de contraseña en blanco si no deseas cambiarla.</p>
            
            <div class="form-group">
                <label for="nuevaClave">Nueva Contraseña:</label>
                <s:password name="usuario.clave" cssClass="form-control" id="nuevaClave"/>
            </div>
            
            <div class="text-center">
                <s:submit value="Guardar Cambios" cssClass="btn-primary"/>
            </div>
        </s:form>
        
        <div class="form-link">
            <a href="inicio.action">Volver a mis gastos</a>
        </div>
    </div>
</body>
</html>