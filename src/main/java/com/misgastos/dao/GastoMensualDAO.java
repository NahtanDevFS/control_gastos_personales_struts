package com.misgastos.dao;

import com.misgastos.model.GastoMensual;
import com.misgastos.utils.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class GastoMensualDAO {

    public void agregarGasto(GastoMensual gasto) {
        String sql = "INSERT INTO gasto_mensual(id_usuario, descripcion, monto, tipo_gasto, mes, anio) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, gasto.getIdUsuario());
            pstmt.setString(2, gasto.getDescripcion());
            pstmt.setBigDecimal(3, gasto.getMonto());
            pstmt.setString(4, gasto.getTipoGasto());
            pstmt.setString(5, gasto.getMes());
            pstmt.setInt(6, gasto.getAnio());
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<GastoMensual> obtenerGastosPorUsuario(int idUsuario) {
        List<GastoMensual> lista = new ArrayList<>();
        String sql = "SELECT * FROM gasto_mensual WHERE id_usuario = ? ORDER BY id DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                GastoMensual gasto = new GastoMensual();
                gasto.setId(rs.getInt("id"));
                gasto.setIdUsuario(rs.getInt("id_usuario"));
                gasto.setDescripcion(rs.getString("descripcion"));
                gasto.setMonto(rs.getBigDecimal("monto"));
                gasto.setTipoGasto(rs.getString("tipo_gasto"));
                gasto.setMes(rs.getString("mes"));
                gasto.setAnio(rs.getInt("anio"));
                lista.add(gasto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public GastoMensual obtenerGastoPorId(int idGasto) {
        String sql = "SELECT * FROM gasto_mensual WHERE id = ?";
        GastoMensual gasto = null;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idGasto);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    gasto = new GastoMensual();
                    gasto.setId(rs.getInt("id"));
                    gasto.setIdUsuario(rs.getInt("id_usuario"));
                    gasto.setDescripcion(rs.getString("descripcion"));
                    gasto.setMonto(rs.getBigDecimal("monto"));
                    gasto.setTipoGasto(rs.getString("tipo_gasto"));
                    gasto.setMes(rs.getString("mes"));
                    gasto.setAnio(rs.getInt("anio"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gasto;
    }

    public void actualizarGasto(GastoMensual gasto) {
        String sql = "UPDATE gasto_mensual SET descripcion = ?, monto = ?, tipo_gasto = ?, mes = ?, anio = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, gasto.getDescripcion());
            pstmt.setBigDecimal(2, gasto.getMonto());
            pstmt.setString(3, gasto.getTipoGasto());
            pstmt.setString(4, gasto.getMes());
            pstmt.setInt(5, gasto.getAnio());
            pstmt.setInt(6, gasto.getId());
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarGasto(int idGasto) {
        String sql = "DELETE FROM gasto_mensual WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idGasto);
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public List<GastoMensual> obtenerGastosFiltrados(int idUsuario, String tipo, String mes, Integer anio) {
        List<GastoMensual> lista = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder("SELECT * FROM gasto_mensual WHERE id_usuario = ?");
        List<Object> params = new ArrayList<>();
        params.add(idUsuario);

        if (tipo != null && !tipo.isEmpty()) {
            sql.append(" AND tipo_gasto = ?");
            params.add(tipo);
        }
        if (mes != null && !mes.isEmpty()) {
            sql.append(" AND mes = ?");
            params.add(mes);
        }
        if (anio != null && anio > 0) {
            sql.append(" AND anio = ?");
            params.add(anio);
        }
        sql.append(" ORDER BY id DESC");

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	GastoMensual gasto = new GastoMensual();
                gasto.setId(rs.getInt("id"));
                gasto.setIdUsuario(rs.getInt("id_usuario"));
                gasto.setDescripcion(rs.getString("descripcion"));
                gasto.setMonto(rs.getBigDecimal("monto"));
                gasto.setTipoGasto(rs.getString("tipo_gasto"));
                gasto.setMes(rs.getString("mes"));
                gasto.setAnio(rs.getInt("anio"));
                lista.add(gasto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    
    public BigDecimal calcularTotalGastos(int idUsuario, String tipo, String mes, Integer anio) {
        StringBuilder sql = new StringBuilder("SELECT SUM(monto) FROM gasto_mensual WHERE id_usuario = ?");
        List<Object> params = new ArrayList<>();
        params.add(idUsuario);

        if (tipo != null && !tipo.isEmpty()) {
            sql.append(" AND tipo_gasto = ?");
            params.add(tipo);
        }
        if (mes != null && !mes.isEmpty()) {
            sql.append(" AND mes = ?");
            params.add(mes);
        }
        if (anio != null && anio > 0) {
            sql.append(" AND anio = ?");
            params.add(anio);
        }

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal(1);
                return total != null ? total : BigDecimal.ZERO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }
}