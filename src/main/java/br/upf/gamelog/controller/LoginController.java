package br.upf.gamelog.controller;

import br.upf.gamelog.entity.UsuarioEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private br.upf.gamelog.facade.UsuarioFacade ejbFacade;

    private UsuarioEntity usuario;

    public LoginController() {}

    @PostConstruct
    public void init() {
        prepareAutenticar();
    }

    public void prepareAutenticar() {
        usuario = new UsuarioEntity();
    }

    public String validarLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        UsuarioEntity usuarioDB = ejbFacade.buscarPorEmail(usuario.getEmail(), usuario.getSenha());
        if (usuarioDB != null && usuarioDB.getId() != null) {
            session.setAttribute("usuarioLogado", usuarioDB);
            return "/admin/home.xhtml?faces-redirect=true";
        } else {
            FacesMessage fm = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Falha no Login!",
                    "Email ou senha incorreto!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session != null) session.invalidate();
        return "/login.xhtml?faces-redirect=true";
    }

    /**
     * Retorna o usuario atualmente logado na sessao (para exibir na topbar).
     */
    public UsuarioEntity getUsuarioLogado() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session == null) return null;
        return (UsuarioEntity) session.getAttribute("usuarioLogado");
    }

    public UsuarioEntity getUsuario() { return usuario; }
    public void setUsuario(UsuarioEntity usuario) { this.usuario = usuario; }
}
