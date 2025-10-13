<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Iniciar Sesión - Control de Gastos</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body class="body-forms">
    <div class="form-container">
        <h1>Iniciar Sesión</h1>

        <s:if test="hasActionErrors()">
            <div class="error-message">
                <s:actionerror/>
            </div>
        </s:if>
        <s:if test="hasActionMessages()">
            <div class="success-message">
                <s:actionmessage/>
            </div>
        </s:if>
        
        <s:form action="login" method="post" theme="simple">
            
            <div class="form-group">
                <label for="correo">Correo Electrónico:</label>
                <s:textfield name="correo" cssClass="form-control" id="correo"/>
            </div>
            
            <div class="form-group">
                <label for="clave">Contraseña:</label>
                <s:password name="clave" cssClass="form-control" id="clave"/>
            </div>
            
            <div class="text-center">
                <s:submit value="Entrar" cssClass="btn-primary" />
            </div>
        </s:form>
        
        <div class="form-link">
            <p>¿No tienes una cuenta? <a href="vistaRegistro.action">Regístrate aquí</a></p>
        </div>
    </div>
</body>
</html>