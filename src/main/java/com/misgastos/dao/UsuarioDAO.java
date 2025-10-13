package com.misgastos.dao;

import com.misgastos.model.Usuario;
import com.misgastos.utils.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public void registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario(nombre_usuario, correo, clave, ingreso_mensual) VALUES(?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getClave());
            pstmt.setBigDecimal(4, usuario.getIngresoMensual());
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Usuario buscarUsuarioPorCorreoYClave(String correo, String clave) {
        String sql = "SELECT * FROM usuario WHERE correo = ? AND clave = ?";
        Usuario usuario = null;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, correo);
            pstmt.setString(2, clave);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setIngresoMensual(rs.getBigDecimal("ingreso_mensual"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public boolean correoYaExiste(String correo) {
        String sql = "SELECT id FROM usuario WHERE correo = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, correo);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Usuario buscarUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        Usuario usuario = null;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setIngresoMensual(rs.getBigDecimal("ingreso_mensual"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public void actualizarUsuario(Usuario usuario) {
        boolean cambiarClave = usuario.getClave() != null && !usuario.getClave().isEmpty();
        
        StringBuilder sql = new StringBuilder("UPDATE usuario SET nombre_usuario = ?, correo = ?, ingreso_mensual = ?");
        if (cambiarClave) {
            sql.append(", clave = ?");
        }
        sql.append(" WHERE id = ?");

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            pstmt.setString(1, usuario.getNombreUsuario());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setBigDecimal(3, usuario.getIngresoMensual());
            
            if (cambiarClave) {
                pstmt.setString(4, usuario.getClave());
                pstmt.setInt(5, usuario.getId());
            } else {
                pstmt.setInt(4, usuario.getId());
            }
            
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}