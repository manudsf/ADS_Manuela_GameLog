package br.upf.gamelog.controller;

import br.upf.gamelog.entity.BacklogEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import jakarta.inject.Inject;
import java.util.List;

@Named(value = "avaliacaoController")
@SessionScoped
public class AvaliacaoController implements Serializable {

    @EJB
    private br.upf.gamelog.facade.BacklogFacade ejbFacade;

    @Inject
    private BacklogController backlogController;

    private BacklogEntity selected;

    public BacklogEntity getSelected() { return selected; }
    public void setSelected(BacklogEntity selected) { this.selected = selected; }

    /**
     * Lista apenas jogos Concluidos ou Platinados.
     */
    public List<BacklogEntity> getAvaliacoesList() {
        return ejbFacade.buscarConcluidos();
    }

    /**
     * Salva avaliacao e review do jogo selecionado.
     */
    public void salvarAvaliacao() {
        if (selected == null) return;
        try {
            ejbFacade.edit(selected);
            backlogController.invalidarCache(); // invalida cache para refletir nota atualizada
            addSuccessMessage("Avaliacao salva com sucesso!");
            selected = null;
        } catch (EJBException ex) {
            Throwable cause = ex.getCause();
            String msg = cause != null ? cause.getLocalizedMessage() : ex.getLocalizedMessage();
            addErrorMessage(msg != null && !msg.isEmpty() ? msg : ex.getLocalizedMessage());
        } catch (Exception ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }

    /**
     * Retorna class CSS do badge conforme status.
     */
    public String getStatusClass(String status) {
        if (status == null) return "";
        switch (status) {
            case "Concluido":  return "gl-badge gl-badge-concluido";
            case "Platinado":  return "gl-badge gl-badge-platinado";
            default:           return "gl-badge";
        }
    }

    /**
     * Estrelas preenchidas para exibicao visual da nota (1-10 mapeado para 1-5 estrelas).
     */
    public int getEstrelasPreenchidas(BacklogEntity item) {
        if (item == null || item.getAvaliacao() == null) return 0;
        return (int) Math.round(item.getAvaliacao() / 2.0);
    }

    public static void addErrorMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
    }

    public static void addSuccessMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage("successInfo",
            new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
    }
}
