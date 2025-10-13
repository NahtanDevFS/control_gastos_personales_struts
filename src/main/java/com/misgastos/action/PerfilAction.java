package com.misgastos.action;

import com.misgastos.dao.UsuarioDAO;
import com.misgastos.model.Usuario;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;

public class PerfilAction extends ActionSupport implements SessionAware {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Map<String, Object> session;
    private Usuario usuario; 
    private boolean profileUpdated;

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setProfileUpdated(boolean profileUpdated) { this.profileUpdated = profileUpdated; }
    @Override
    public void setSession(Map<String, Object> session) { this.session = session; }

    public String display() {
        Usuario usuarioEnSesion = (Usuario) session.get("usuarioLogueado");
        if (usuarioEnSesion == null) {
            return LOGIN;
        }

        if (profileUpdated) {
            addActionMessage("Tu perfil ha sido actualizado correctamente");
        }

        this.usuario = usuarioDAO.buscarUsuarioPorId(usuarioEnSesion.getId());
        return SUCCESS;
    }
    
    public String update() {
        usuarioDAO.actualizarUsuario(this.usuario);

        Usuario usuarioActualizado = usuarioDAO.buscarUsuarioPorId(this.usuario.getId());
        session.put("usuarioLogueado", usuarioActualizado);

        return SUCCESS;
    }
}