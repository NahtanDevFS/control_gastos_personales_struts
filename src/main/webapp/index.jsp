<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Control de Gastos</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <s:if test="#session.usuarioLogueado != null">
        <div class="welcome-bar">
            <span>
                Bienvenido, <strong><s:property value="#session.usuarioLogueado.nombreUsuario"/></strong>!
            </span>
            &nbsp;|&nbsp;
            <a href="vistaPerfil.action">Mi Perfil</a>
            &nbsp;|&nbsp;
            <a href="logout.action">Cerrar Sesión</a>
        </div>
    </s:if>

    <div class="app-container">
        <h1>Mi Control de Gastos Personales</h1>

        <div class="summary-card">
            <div class="summary-item">
                <strong>Ingreso Mensual</strong>
                <span class="income"><s:text name="format.currency"><s:param name="value" value="ingresoMensualUsuario"/></s:text></span>
            </div>
            <div class="summary-item">
                <strong>Gasto Total (Filtrado)</strong>
                <span class="expense"><s:text name="format.currency"><s:param name="value" value="gastoTotalCalculado"/></s:text></span>
            </div>
            <div class="summary-item">
                <strong>Saldo Restante</strong>
                <span class="balance"><s:text name="format.currency"><s:param name="value" value="saldoRestante"/></s:text></span>
            </div>
        </div>
        
        <div class="form-section">
            <h2>Registrar Nuevo Gasto</h2>
            <s:form action="registrarGasto">
                <s:textfield name="gasto.descripcion" label="Descripción del Gasto" />
                <s:textfield name="gasto.monto" label="Monto (Q)" />
                <s:select list="tiposDeGasto" name="gasto.tipoGasto" label="Tipo de Gasto"/>
                <s:select list="meses" name="gasto.mes" label="Mes"/>
                <s:submit value="Agregar Gasto" cssClass="btn-primary"/>
            </s:form>
        </div>

        <div class="form-section">
            <h2>Lista de Gastos</h2>
            <s:form action="inicio" method="get" theme="simple" cssClass="filter-form">
                <table style="width:100%; margin-bottom: 20px; text-align:center;">
                    <tr>
                        <td>Tipo: <s:select list="tiposDeGasto" name="filtroTipo" headerKey="" headerValue="Todos"/></td>
                        <td>Mes: <s:select list="meses" name="filtroMes" headerKey="" headerValue="Todos"/></td>
                        <td>Año: <s:textfield name="filtroAnio" /></td>
                        <td><s:submit value="Buscar" cssClass="btn-primary"/></td>
                    </tr>
                </table>
            </s:form>

            <table class="expense-table">
                <tr>
                    <th>Descripción</th><th>Tipo</th><th>Mes</th><th>Año</th><th>Monto (Q)</th><th>Acciones</th>
                </tr>
                <s:iterator value="listaGastos">
                    <tr>
                        <td><s:property value="descripcion" /></td>
                        <td><s:property value="tipoGasto" /></td>
                        <td><s:property value="mes" /></td>
                        <td><s:property value="anio" /></td>
                        <td style="text-align:right;"><s:text name="format.currency"><s:param name="value" value="monto"/></s:text></td>
                        <td style="text-align:center;">
                             <a href="<s:url action='editarGasto'><s:param name='idGasto' value='id'/></s:url>">Editar</a>
                             <a href="<s:url action='eliminarGasto'><s:param name='idGasto' value='id'/></s:url>" onclick="return confirm('¿Estás seguro?');">Eliminar</a>
                        </td>
                    </tr>
                </s:iterator>
            </table>
            
            <div style="text-align:center; margin-top:20px; font-size: 1.1em;">
                 <a href="<s:url action='exportarPdf'><s:param name='filtroTipo' value='filtroTipo'/><s:param name='filtroMes' value='filtroMes'/><s:param name='filtroAnio' value='filtroAnio'/></s:url>">Exportar a PDF</a>
                 |
                 <a href="<s:url action='exportarExcel'><s:param name='filtroTipo' value='filtroTipo'/><s:param name='filtroMes' value='filtroMes'/><s:param name='filtroAnio' value='filtroAnio'/></s:url>">Exportar a Excel</a>
            </div>
        </div>
    </div>
</body>
</html>