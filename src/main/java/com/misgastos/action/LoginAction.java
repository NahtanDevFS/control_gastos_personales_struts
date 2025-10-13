package com.misgastos.action;

import com.misgastos.dao.UsuarioDAO;
import com.misgastos.model.Usuario;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;

public class LoginAction extends ActionSupport implements SessionAware {

    private String correo;
    private String clave;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Map<String, Object> session; 

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public String execute() throws Exception {
        Usuario usuario = usuarioDAO.buscarUsuarioPorCorreoYClave(correo, clave);

        if (usuario != null) {
            session.put("usuarioLogueado", usuario);
            return SUCCESS;
        } else {
            addActionError("Correo o contraseña incorrectos.");
            return INPUT;
        }
    }
}