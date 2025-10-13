package com.misgastos.model;

import java.math.BigDecimal;

public class GastoMensual {
    private int id;
    private int idUsuario;
    private String descripcion;
    private BigDecimal monto;
    private String tipoGasto;
    private String mes;
    private int anio;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public String getTipoGasto() { return tipoGasto; }
    public void setTipoGasto(String tipoGasto) { this.tipoGasto = tipoGasto; }
    public String getMes() { return mes; }
    public void setMes(String mes) { this.mes = mes; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
}