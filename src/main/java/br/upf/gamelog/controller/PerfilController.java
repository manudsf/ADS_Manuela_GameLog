package br.upf.gamelog.controller;

import br.upf.gamelog.entity.UsuarioEntity;
import br.upf.gamelog.facade.UsuarioFacade;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Controller para edição de perfil do usuário logado.
 *
 * O upload de foto é tratado 100% no front-end via FileReader API:
 * o JavaScript lê a imagem como Base64 e grava em um h:inputHidden,
 * que o JSF envia normalmente no submit. Sem Part, sem multipart JSF.
 */
@Named("perfilController")
@SessionScoped
public class PerfilController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioFacade usuarioFacade;

    // Campos editáveis no formulário
    private String apelidoEdit;
    private String bioEdit;

    // Foto em Base64 enviada pelo campo hidden do formulário
    private String fotoBase64;

    // -------------------------------------------------------
    // Inicializa os campos com os dados atuais do usuário
    // -------------------------------------------------------
    public void inicializar() {
        UsuarioEntity u = getUsuarioLogado();
        if (u != null) {
            apelidoEdit = (u.getApelido() != null) ? u.getApelido() : "";
            bioEdit     = (u.getBio()     != null) ? u.getBio()     : "";
            fotoBase64  = ""; // limpa para não reentregar foto antiga desnecessariamente
        }
    }

    // -------------------------------------------------------
    // Salvar perfil
    // -------------------------------------------------------
    public void salvarPerfil() {
        UsuarioEntity u = getUsuarioLogado();
        if (u == null) {
            addMsg(FacesMessage.SEVERITY_ERROR, "Sessão expirada. Faça login novamente.");
            return;
        }

        // Apelido: salva null se vazio (para exibir nome real)
        String apelido = (apelidoEdit != null && !apelidoEdit.trim().isEmpty())
                         ? apelidoEdit.trim() : null;
        u.setApelido(apelido);

        // Bio
        u.setBio((bioEdit != null && !bioEdit.trim().isEmpty()) ? bioEdit.trim() : null);

        // Foto: só atualiza se o JS mandou um Base64 novo (começa com "data:")
        if (fotoBase64 != null && fotoBase64.startsWith("data:image")) {
            // Limita a ~4 MB de Base64 (≈ 3 MB de imagem)
            if (fotoBase64.length() > 4_000_000) {
                addMsg(FacesMessage.SEVERITY_ERROR,
                       "Imagem muito grande. Use uma foto com até 3 MB.");
                return;
            }
            u.setFotoPerfil(fotoBase64);
        }

        try {
            usuarioFacade.edit(u);
            atualizarSessao(u);
            addMsg(FacesMessage.SEVERITY_INFO, "Perfil atualizado com sucesso!");
        } catch (Exception e) {
            addMsg(FacesMessage.SEVERITY_ERROR, "Erro ao salvar. Tente novamente.");
        }
    }

    // -------------------------------------------------------
    // Helpers
    // -------------------------------------------------------
    private void addMsg(FacesMessage.Severity sev, String text) {
        FacesContext.getCurrentInstance()
            .addMessage("formPerfil:msg-perfil", new FacesMessage(sev, text, null));
    }

    private UsuarioEntity getUsuarioLogado() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (ctx == null) return null;
        HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
        if (session == null) return null;
        return (UsuarioEntity) session.getAttribute("usuarioLogado");
    }

    private void atualizarSessao(UsuarioEntity u) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (ctx == null) return;
        HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
        if (session != null) session.setAttribute("usuarioLogado", u);
    }

    // -------------------------------------------------------
    // Getters / Setters
    // -------------------------------------------------------
    public String getApelidoEdit() { return apelidoEdit; }
    public void setApelidoEdit(String v) { this.apelidoEdit = v; }

    public String getBioEdit() { return bioEdit; }
    public void setBioEdit(String v) { this.bioEdit = v; }

    public String getFotoBase64() { return fotoBase64; }
    public void setFotoBase64(String v) { this.fotoBase64 = v; }
}
