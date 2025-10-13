package com.misgastos.action;

import com.misgastos.dao.UsuarioDAO;
import com.misgastos.model.Usuario;
import com.opensymphony.xwork2.ActionSupport;

public class RegistroAction extends ActionSupport {

    private Usuario usuario; 
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String execute() throws Exception {
        if (usuarioDAO.correoYaExiste(usuario.getCorreo())) {
            addActionError("El correo electrónico ya está registrado. Por favor, elige otro.");
            return INPUT; 
        }
        
        usuarioDAO.registrarUsuario(usuario);

        addActionMessage("¡Registro exitoso! Ahora puedes iniciar sesión.");
        return SUCCESS;
    }
    
    public String display() {
        return SUCCESS;
    }
}