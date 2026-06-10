package br.upf.gamelog.controller;

import br.upf.gamelog.entity.BacklogEntity;
import br.upf.gamelog.entity.UsuarioEntity;
import br.upf.gamelog.enumeration.BacklogStatusEnum;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import org.primefaces.model.ResponsiveOption;

@Named(value = "backlogController")
@SessionScoped
public class BacklogController implements Serializable {

    @EJB
    private br.upf.gamelog.facade.BacklogFacade ejbFacade;

    private BacklogEntity backlog = new BacklogEntity();
    private BacklogEntity selected;

    // Filtros
    private String filtroTitulo;
    private String filtroStatus;

    // Cache da lista para permitir ordenacao no p:dataTable
    private List<BacklogEntity> backlogListCache;

    public BacklogEntity getBacklog() { return backlog; }
    public void setBacklog(BacklogEntity backlog) { this.backlog = backlog; }

    public BacklogEntity getSelected() { return selected; }
    public void setSelected(BacklogEntity selected) { this.selected = selected; }

    public String getFiltroTitulo() { return filtroTitulo; }
    public void setFiltroTitulo(String filtroTitulo) { this.filtroTitulo = filtroTitulo; }

    public String getFiltroStatus() { return filtroStatus; }
    public void setFiltroStatus(String filtroStatus) { this.filtroStatus = filtroStatus; }

    /**
     * Retorna a lista cacheada — necessario para ordenacao funcionar no p:dataTable.
     */
    public List<BacklogEntity> getBacklogList() {
        if (backlogListCache == null) {
            recarregarLista();
        }
        return backlogListCache;
    }

    /** Chamado pelo botao Filtrar no XHTML. */
    public void filtrar() {
        recarregarLista();
    }

    /** Permite que outros controllers invalidem o cache (ex: AvaliacaoController). */
    public void invalidarCache() {
        backlogListCache = null;
    }

    private void recarregarLista() {
        boolean temTitulo = filtroTitulo != null && !filtroTitulo.trim().isEmpty();
        boolean temStatus = filtroStatus != null && !filtroStatus.trim().isEmpty();

        if (temTitulo && temStatus) {
            backlogListCache = ejbFacade.filtrarPorTituloEStatus(filtroTitulo.trim(), filtroStatus.trim());
        } else if (temTitulo) {
            backlogListCache = ejbFacade.filtrarPorTitulo(filtroTitulo.trim());
        } else if (temStatus) {
            backlogListCache = ejbFacade.filtrarPorStatus(filtroStatus.trim());
        } else {
            backlogListCache = ejbFacade.buscarTodos();
        }
    }

    /**
     * Limpa todos os filtros e o cache.
     */
    public void limparFiltros() {
        filtroTitulo = null;
        filtroStatus = null;
        backlogListCache = null;
    }

    /**
     * Opções responsivas para o p:carousel da home.
     */
    public List<ResponsiveOption> getCarouselResponsiveOptions() {
        List<ResponsiveOption> options = new ArrayList<>();
        options.add(new ResponsiveOption("1400px", 5, 2));
        options.add(new ResponsiveOption("1100px", 4, 2));
        options.add(new ResponsiveOption("768px",  3, 1));
        options.add(new ResponsiveOption("480px",  2, 1));
        options.add(new ResponsiveOption("320px",  1, 1));
        return options;
    }

    public BacklogStatusEnum[] getStatusOptions() {
        return BacklogStatusEnum.values();
    }

    /** Total de jogos no backlog. */
    public int getTotalJogos() {
        java.util.List<BacklogEntity> lista = ejbFacade.buscarTodos();
        return lista == null ? 0 : lista.size();
    }

    /** Total de jogos Concluidos. */
    public int getTotalConcluidos() {
        java.util.List<BacklogEntity> lista = ejbFacade.filtrarPorStatus("Concluido");
        return lista == null ? 0 : lista.size();
    }

    /** Total de jogos Platinados. */
    public int getTotalPlatinados() {
        java.util.List<BacklogEntity> lista = ejbFacade.filtrarPorStatus("Platinado");
        return lista == null ? 0 : lista.size();
    }

    /** Soma total de horas jogadas. */
    public int getTotalHoras() {
        java.util.List<BacklogEntity> lista = ejbFacade.buscarTodos();
        if (lista == null) return 0;
        int total = 0;
        for (BacklogEntity b : lista) {
            if (b.getHorasJogadas() != null) total += b.getHorasJogadas();
        }
        return total;
    }

    private UsuarioEntity getUsuarioLogado() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        return (UsuarioEntity) session.getAttribute("usuarioLogado");
    }

    public BacklogEntity prepareAdicionar() {
        backlog = new BacklogEntity();
        return backlog;
    }

    public void adicionarBacklog() {
        backlog.setDatahorareg(new Timestamp(System.currentTimeMillis()));
        backlog.setIdUsuario(getUsuarioLogado());
        // Avaliação só permitida se status for Concluído ou Platinado
        if (!backlog.isAvaliacaoLiberada()) {
            backlog.setAvaliacao(null);
        }
        persist(PersistAction.CREATE, "Jogo adicionado ao backlog com sucesso!");
    }

    public void editarBacklog() {
        // Avaliação só permitida se status for Concluído ou Platinado
        if (selected != null && !selected.isAvaliacaoLiberada()) {
            selected.setAvaliacao(null);
        }
        persist(PersistAction.UPDATE, "Backlog atualizado com sucesso!");
    }

    public void deletarBacklog() {
        persist(PersistAction.DELETE, "Registro removido do backlog com sucesso!");
    }

    /**
     * Texto legível para exibição de horas jogadas.
     */
    public String getHorasExibicao(BacklogEntity item) {
        if (item == null || item.getHorasJogadas() == null) return "-";
        return item.getHorasJogadas() + "h";
    }

    /**
     * Texto legível para avaliação.
     */
    public String getAvaliacaoExibicao(BacklogEntity item) {
        if (item == null || item.getAvaliacao() == null) return "-";
        return item.getAvaliacao() + "/10";
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
                case CREATE: ejbFacade.createReturn(backlog); break;
                case UPDATE: ejbFacade.edit(selected); selected = null; break;
                case DELETE: ejbFacade.remove(selected); selected = null; break;
            }
            addSuccessMessage(successMessage);
            backlogListCache = null; // invalida cache apos alteracao
        } catch (EJBException ex) {
            Throwable cause = ex.getCause();
            String msg = cause != null ? cause.getLocalizedMessage() : ex.getLocalizedMessage();
            addErrorMessage(msg != null && !msg.isEmpty() ? msg : ex.getLocalizedMessage());
        } catch (Exception ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }
}
