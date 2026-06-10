package br.upf.gamelog.controller;

import br.upf.gamelog.entity.UsuarioEntity;
import br.upf.gamelog.entity.WishlistEntity;
import br.upf.gamelog.enumeration.PlataformaEnum;
import br.upf.gamelog.enumeration.PrioridadeEnum;
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
import java.util.Date;
import java.util.List;

@Named(value = "wishlistController")
@SessionScoped
public class WishlistController implements Serializable {

    @EJB
    private br.upf.gamelog.facade.WishlistFacade ejbFacade;

    private WishlistEntity wishlist = new WishlistEntity();
    private WishlistEntity selected;
    private List<WishlistEntity> wishlistList = new ArrayList<>();

    public WishlistEntity getWishlist() { return wishlist; }
    public void setWishlist(WishlistEntity wishlist) { this.wishlist = wishlist; }

    public WishlistEntity getSelected() { return selected; }
    public void setSelected(WishlistEntity selected) { this.selected = selected; }

    public List<WishlistEntity> getWishlistList() {
        return ejbFacade.buscarTodos();
    }

    public PlataformaEnum[] getPlataformas() {
        return PlataformaEnum.values();
    }

    public PrioridadeEnum[] getPrioridades() {
        return PrioridadeEnum.values();
    }

    private UsuarioEntity getUsuarioLogado() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        return (UsuarioEntity) session.getAttribute("usuarioLogado");
    }

    public WishlistEntity prepareAdicionar() {
        wishlist = new WishlistEntity();
        return wishlist;
    }

    public void adicionarWishlist() {
        Date datahoraAtual = new Timestamp(System.currentTimeMillis());
        wishlist.setDatahorareg(datahoraAtual);
        wishlist.setIdUsuario(getUsuarioLogado());
        persist(PersistAction.CREATE, "Item adicionado à wishlist com sucesso!");
    }

    public void editarWishlist() {
        persist(PersistAction.UPDATE, "Item da wishlist alterado com sucesso!");
    }

    public void deletarWishlist() {
        persist(PersistAction.DELETE, "Item da wishlist excluído com sucesso!");
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static enum PersistAction {
        CREATE, DELETE, UPDATE
    }

    private void persist(PersistAction persistAction, String successMessage) {
        try {
            if (null != persistAction) {
                switch (persistAction) {
                    case CREATE:
                        ejbFacade.createReturn(wishlist);
                        break;
                    case UPDATE:
                        ejbFacade.edit(selected);
                        selected = null;
                        break;
                    case DELETE:
                        ejbFacade.remove(selected);
                        selected = null;
                        break;
                    default:
                        break;
                }
            }
            addSuccessMessage(successMessage);
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                addErrorMessage(msg);
            } else {
                addErrorMessage(ex.getLocalizedMessage());
            }
        } catch (Exception ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }
}
