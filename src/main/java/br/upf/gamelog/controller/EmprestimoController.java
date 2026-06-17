package br.upf.gamelog.controller;

import br.upf.gamelog.entity.EmprestimoEntity;
import br.upf.gamelog.entity.UsuarioEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named(value = "emprestimoController")
@SessionScoped
public class EmprestimoController implements Serializable {

    @EJB
    private br.upf.gamelog.facade.EmprestimoFacade ejbFacade;

    @EJB
    private br.upf.gamelog.facade.BacklogFacade backlogFacade;

    private EmprestimoEntity emprestimo = new EmprestimoEntity();
    private EmprestimoEntity selected;

    public EmprestimoEntity getEmprestimo() { return emprestimo; }
    public void setEmprestimo(EmprestimoEntity emprestimo) { this.emprestimo = emprestimo; }

    public EmprestimoEntity getSelected() { return selected; }
    public void setSelected(EmprestimoEntity selected) { this.selected = selected; }

    // Cache da lista para permitir ordenacao no p:dataTable
    private List<EmprestimoEntity> emprestimoListCache;

    public List<EmprestimoEntity> getEmprestimoList() {
        if (emprestimoListCache == null) {
            emprestimoListCache = ejbFacade.buscarTodos();
        }
        return emprestimoListCache;
    }

    /**
     * Autocomplete de títulos: busca jogos do backlog que contenham o texto digitado.
     */
    public List<String> completarJogoDoBacklog(String query) {
        List<String> todos = backlogFacade.buscarTitulosBacklog();
        if (query == null || query.trim().isEmpty()) return todos;
        String q = query.toLowerCase();
        return todos.stream()
            .filter(t -> t.toLowerCase().contains(q))
            .collect(Collectors.toList());
    }

    private UsuarioEntity getUsuarioLogado() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        return (UsuarioEntity) session.getAttribute("usuarioLogado");
    }

    public EmprestimoEntity prepareAdicionar() {
        emprestimo = new EmprestimoEntity();
        emprestimo.setDevolvido(false);
        return emprestimo;
    }

    public void adicionarEmprestimo() {
        emprestimo.setDatahorareg(new Timestamp(System.currentTimeMillis()));
        emprestimo.setIdUsuario(getUsuarioLogado());
        persist(PersistAction.CREATE, "Empréstimo registrado com sucesso!");
    }

    public void editarEmprestimo() {
        persist(PersistAction.UPDATE, "Empréstimo alterado com sucesso!");
    }

    public void deletarEmprestimo() {
        persist(PersistAction.DELETE, "Empréstimo excluído com sucesso!");
    }

    public static void addErrorMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
    }

    public static void addSuccessMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage("successInfo",
            new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
    }

    public static enum PersistAction { CREATE, DELETE, UPDATE }

    private void persist(PersistAction persistAction, String successMessage) {
        try {
            switch (persistAction) {
                case CREATE: ejbFacade.createReturn(emprestimo); break;
                case UPDATE: ejbFacade.edit(selected); selected = null; break;
                case DELETE: ejbFacade.remove(selected); selected = null; break;
            }
            addSuccessMessage(successMessage);
            emprestimoListCache = null; // invalida cache apos alteracao
        } catch (EJBException ex) {
            Throwable cause = ex.getCause();
            String msg = cause != null ? cause.getLocalizedMessage() : ex.getLocalizedMessage();
            addErrorMessage(msg != null && !msg.isEmpty() ? msg : ex.getLocalizedMessage());
        } catch (Exception ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }
}
