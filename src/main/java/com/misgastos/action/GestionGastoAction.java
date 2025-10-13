package com.misgastos.action;

import com.misgastos.dao.GastoMensualDAO;
import com.misgastos.model.GastoMensual;
import com.opensymphony.xwork2.ActionSupport;
import java.time.Year;
import java.util.List;
import java.util.Arrays;

public class GestionGastoAction extends ActionSupport {

    private GastoMensualDAO gastoDAO = new GastoMensualDAO();
    private GastoMensual gasto; 
    private int idGasto;     
    
    public GastoMensual getGasto() {
        return gasto;
    }
    public void setGasto(GastoMensual gasto) {
        this.gasto = gasto;
    }
    public int getIdGasto() {
        return idGasto;
    }
    public void setIdGasto(int idGasto) {
        this.idGasto = idGasto;
    }
    
    public List<String> getTiposDeGasto() {
        return Arrays.asList("Comida", "Transporte", "Necesidad", "Recreativo", "Otro");
    }

    public List<String> getMeses() {
        return Arrays.asList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                             "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
    }

    public String editar() {
        gasto = gastoDAO.obtenerGastoPorId(idGasto);
        return SUCCESS;
    }

    public String actualizar() {
    	gasto.setAnio(Year.now().getValue());
        gastoDAO.actualizarGasto(gasto);
        return SUCCESS;
    }

    public String eliminar() {
        gastoDAO.eliminarGasto(idGasto);
        return SUCCESS;
    }
}