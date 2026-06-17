package br.upf.gamelog.facade;

import br.upf.gamelog.entity.WishlistEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class WishlistFacade extends AbstractFacade<WishlistEntity> {

    @PersistenceContext(unitName = "GameLogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() { return em; }

    public WishlistFacade() { super(WishlistEntity.class); }

    public long contarTodos() {
        try {
            return em.createQuery("SELECT COUNT(w) FROM WishlistEntity w", Long.class)
                .getSingleResult();
        } catch (Exception e) { return 0; }
    }

    /** Busca todos sem paginacao, ordenados por prioridade correta (Alta > Media > Baixa). */
    public List<WishlistEntity> buscarTodos() {
        List<WishlistEntity> lista = new ArrayList<>();
        try {
            lista = em.createQuery(
                "SELECT w FROM WishlistEntity w ORDER BY " +
                "CASE w.prioridade WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 WHEN 'Baixa' THEN 3 ELSE 4 END, " +
                "w.titulo", WishlistEntity.class).getResultList();
        } catch (Exception e) { System.out.println("Erro WishlistFacade.buscarTodos: " + e); }
        return lista;
    }

    /** Busca com paginacao. */
    public List<WishlistEntity> buscarTodosPaginado(int firstResult, int pageSize) {
        List<WishlistEntity> lista = new ArrayList<>();
        try {
            TypedQuery<WishlistEntity> q = em.createQuery(
                "SELECT w FROM WishlistEntity w ORDER BY " +
                "CASE w.prioridade WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 WHEN 'Baixa' THEN 3 ELSE 4 END, " +
                "w.titulo", WishlistEntity.class);
            q.setFirstResult(firstResult);
            q.setMaxResults(pageSize);
            lista = q.getResultList();
        } catch (Exception e) { System.out.println("Erro WishlistFacade.buscarTodosPaginado: " + e); }
        return lista;
    }
}
