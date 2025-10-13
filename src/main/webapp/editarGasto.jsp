<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Gasto</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body class="body-forms">
    <div class="form-container">
        <h1>Editar Gasto</h1>
        
        <s:form action="actualizarGasto" method="post" theme="simple">
            <s:hidden name="gasto.id" />
            
            <div class="form-group">
                <label for="descripcion">Descripción:</label>
                <s:textfield name="gasto.descripcion" cssClass="form-control" id="descripcion"/>
            </div>

            <div class="form-group">
                <label for="monto">Monto (Q):</label>
                <s:textfield name="gasto.monto" cssClass="form-control" id="monto"/>
            </div>

            <div class="form-group">
                <label for="tipoGasto">Tipo de Gasto:</label>
                <s:select list="tiposDeGasto" name="gasto.tipoGasto" cssClass="form-control" id="tipoGasto"/>
            </div>

            <div class="form-group">
                <label for="mes">Mes:</label>
                <s:select list="meses" name="gasto.mes" cssClass="form-control" id="mes"/>
            </div>
            
            <div class="text-center">
                <s:submit value="Guardar Cambios" cssClass="btn-primary" />
            </div>
        </s:form>
        
        <div class="form-link">
            <a href="inicio.action">Volver a mis gastos</a>
        </div>
    </div>
</body>
</html>