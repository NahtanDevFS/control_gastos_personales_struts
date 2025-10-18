package com.misgastos.action;

import com.misgastos.dao.GastoMensualDAO;
import com.misgastos.model.GastoMensual;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;

import com.misgastos.model.Usuario; 
import org.apache.struts2.interceptor.SessionAware; 
import java.util.Collections;
import java.util.Map;
import java.time.Year;
import java.util.Arrays;
import java.math.BigDecimal;

public class RegistroGastoAction extends ActionSupport implements SessionAware {

    private GastoMensual gasto = new GastoMensual();
    private GastoMensualDAO gastoDAO = new GastoMensualDAO();
    private Map<String, Object> session;
    private String filtroTipo;
    private String filtroMes;
    private Integer filtroAnio;
    
    public List<String> getTiposDeGasto() {
        return Arrays.asList("Comida", "Transporte", "Necesidad", "Recreativo", "Otro");
    }
    
    public List<String> getMeses() {
        return Arrays.asList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                             "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
    }
    
    public String getFiltroTipo() { return filtroTipo; }
    public void setFiltroTipo(String filtroTipo) { this.filtroTipo = filtroTipo; }
    public String getFiltroMes() { return filtroMes; }
    public void setFiltroMes(String filtroMes) { this.filtroMes = filtroMes; }
    public Integer getFiltroAnio() { return filtroAnio; }
    public void setFiltroAnio(Integer filtroAnio) { this.filtroAnio = filtroAnio; }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public GastoMensual getGasto() { return gasto; }
    public void setGasto(GastoMensual gasto) { this.gasto = gasto; }

    public List<GastoMensual> getListaGastos() {
        Usuario usuario = (Usuario) session.get("usuarioLogueado");
        if (usuario != null) {
        	return gastoDAO.obtenerGastosFiltrados(usuario.getId(), filtroTipo, filtroMes, filtroAnio);
        }
        return Collections.emptyList(); 
    }
    
    public BigDecimal getGastoTotalCalculado() {
        Usuario usuario = (Usuario) session.get("usuarioLogueado");
        if (usuario != null) {
            return gastoDAO.calcularTotalGastos(usuario.getId(), filtroTipo, filtroMes, filtroAnio);
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getIngresoMensualUsuario() {
        Usuario usuario = (Usuario) session.get("usuarioLogueado");
        if (usuario != null && usuario.getIngresoMensual() != null) {
            return usuario.getIngresoMensual();
        }
        return BigDecimal.ZERO;
    }
    
    public BigDecimal getSaldoRestante() {
        BigDecimal ingreso = getIngresoMensualUsuario();
        BigDecimal gasto = getGastoTotalCalculado();
        
        return ingreso.subtract(gasto);
    }

    @Override
    public String execute() throws Exception {
        Usuario usuario = (Usuario) session.get("usuarioLogueado");
        if (usuario == null) {
            return LOGIN; 
        }

        if (gasto.getDescripcion() != null && !gasto.getDescripcion().trim().isEmpty() && gasto.getMonto() != null) {
            gasto.setIdUsuario(usuario.getId()); 
            gasto.setAnio(Year.now().getValue());
            gastoDAO.agregarGasto(gasto);
        }
        return SUCCESS;
    }
    
    @Override
    public String input() throws Exception {
        return INPUT;
    }
}