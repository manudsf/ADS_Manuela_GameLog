package br.upf.gamelog.facade;

import br.upf.gamelog.entity.BacklogEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class BacklogFacade extends AbstractFacade<BacklogEntity> {

    @PersistenceContext(unitName = "GameLogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() { return em; }

    public BacklogFacade() { super(BacklogEntity.class); }

    // ---------------------------------------------------------------
    // CONTAGENS (para paginacao e stats)
    // ---------------------------------------------------------------

    public long contarTodos() {
        try {
            return em.createQuery(
                "SELECT COUNT(b) FROM BacklogEntity b", Long.class)
                .getSingleResult();
        } catch (Exception e) { return 0; }
    }

    public long contarPorStatus(String status) {
        try {
            return em.createQuery(
                "SELECT COUNT(b) FROM BacklogEntity b WHERE b.status = :status", Long.class)
                .setParameter("status", status)
                .getSingleResult();
        } catch (Exception e) { return 0; }
    }

    public long contarPorTitulo(String titulo) {
        try {
            return em.createQuery(
                "SELECT COUNT(b) FROM BacklogEntity b WHERE LOWER(b.tituloJogo) LIKE :titulo", Long.class)
                .setParameter("titulo", "%" + titulo.toLowerCase() + "%")
                .getSingleResult();
        } catch (Exception e) { return 0; }
    }

    public long contarPorTituloEStatus(String titulo, String status) {
        try {
            return em.createQuery(
                "SELECT COUNT(b) FROM BacklogEntity b WHERE LOWER(b.tituloJogo) LIKE :titulo AND b.status = :status", Long.class)
                .setParameter("titulo", "%" + titulo.toLowerCase() + "%")
                .setParameter("status", status)
                .getSingleResult();
        } catch (Exception e) { return 0; }
    }

    // ---------------------------------------------------------------
    // LISTAGENS COM PAGINACAO
    // ---------------------------------------------------------------

    /**
     * Busca todos com paginacao. firstResult = offset, pageSize = limite.
     */
    public List<BacklogEntity> buscarTodosPaginado(int firstResult, int pageSize) {
        List<BacklogEntity> lista = new ArrayList<>();
        try {
            TypedQuery<BacklogEntity> q = em.createQuery(
                "SELECT b FROM BacklogEntity b ORDER BY b.status, b.tituloJogo",
                BacklogEntity.class);
            q.setFirstResult(firstResult);
            q.setMaxResults(pageSize);
            lista = q.getResultList();
        } catch (Exception e) { System.out.println("Erro BacklogFacade.buscarTodosPaginado: " + e); }
        return lista;
    }

    /** Busca todos sem paginacao (usado na home/carrossel e stats). */
    public List<BacklogEntity> buscarTodos() {
        List<BacklogEntity> lista = new ArrayList<>();
        try {
            lista = em.createQuery(
                "SELECT b FROM BacklogEntity b ORDER BY b.status, b.tituloJogo",
                BacklogEntity.class).getResultList();
        } catch (Exception e) { System.out.println("Erro BacklogFacade.buscarTodos: " + e); }
        return lista;
    }

    public List<BacklogEntity> filtrarPorTitulo(String titulo, int firstResult, int pageSize) {
        List<BacklogEntity> lista = new ArrayList<>();
        try {
            TypedQuery<BacklogEntity> q = em.createQuery(
                "SELECT b FROM BacklogEntity b WHERE LOWER(b.tituloJogo) LIKE :titulo ORDER BY b.tituloJogo",
                BacklogEntity.class);
            q.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
            q.setFirstResult(firstResult);
            q.setMaxResults(pageSize);
            lista = q.getResultList();
        } catch (Exception e) { System.out.println("Erro BacklogFacade.filtrarPorTitulo: " + e); }
        return lista;
    }

    public List<BacklogEntity> filtrarPorStatus(String status) {
        List<BacklogEntity> lista = new ArrayList<>();
        try {
            lista = em.createQuery(
                "SELECT b FROM BacklogEntity b WHERE b.status = :status ORDER BY b.tituloJogo",
                BacklogEntity.class)
                .setParameter("status", status)
                .getResultList();
        } catch (Exception e) { System.out.println("Erro BacklogFacade.filtrarPorStatus: " + e); }
        return lista;
    }

    public List<BacklogEntity> filtrarPorStatus(String status, int firstResult, int pageSize) {
        List<BacklogEntity> lista = new ArrayList<>();
        try {
            TypedQuery<BacklogEntity> q = em.createQuery(
                "SELECT b FROM BacklogEntity b WHERE b.status = :status ORDER BY b.tituloJogo",
                BacklogEntity.class);
            q.setParameter("status", status);
            q.setFirstResult(firstResult);
            q.setMaxResults(pageSize);
            lista = q.getResultList();
        } catch (Exception e) { System.out.println("Erro BacklogFacade.filtrarPorStatus paginado: " + e); }
        return lista;
    }

    public List<BacklogEntity> filtrarPorTituloEStatus(String titulo, String status, int firstResult, int pageSize) {
        List<BacklogEntity> lista = new ArrayList<>();
        try {
            TypedQuery<BacklogEntity> q = em.createQuery(
                "SELECT b FROM BacklogEntity b WHERE LOWER(b.tituloJogo) LIKE :titulo AND b.status = :status ORDER BY b.tituloJogo",
                BacklogEntity.class);
            q.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
            q.setParameter("status", status);
            q.setFirstResult(firstResult);
            q.setMaxResults(pageSize);
            lista = q.getResultList();
        } catch (Exception e) { System.out.println("Erro BacklogFacade.filtrarPorTituloEStatus: " + e); }
        return lista;
    }

    /** Busca titulos para autocomplete de emprestimos. */
    public List<String> buscarTitulosBacklog() {
        List<String> lista = new ArrayList<>();
        try {
            lista = em.createQuery(
                "SELECT DISTINCT b.tituloJogo FROM BacklogEntity b ORDER BY b.tituloJogo",
                String.class).getResultList();
        } catch (Exception e) { System.out.println("Erro BacklogFacade.buscarTitulosBacklog: " + e); }
        return lista;
    }

    /** Busca jogos concluidos ou platinados para a tela de avaliacoes. */
    public List<BacklogEntity> buscarConcluidos() {
        List<BacklogEntity> lista = new ArrayList<>();
        try {
            lista = em.createQuery(
                "SELECT b FROM BacklogEntity b WHERE b.status IN ('Concluido', 'Platinado') ORDER BY b.tituloJogo",
                BacklogEntity.class).getResultList();
        } catch (Exception e) { System.out.println("Erro BacklogFacade.buscarConcluidos: " + e); }
        return lista;
    }

    // Mantido para compatibilidade com BacklogController (filtrarPorTitulo sem paginacao)
    public List<BacklogEntity> filtrarPorTitulo(String titulo) {
        return filtrarPorTitulo(titulo, 0, Integer.MAX_VALUE);
    }

    // Mantido para compatibilidade com BacklogController (filtrarPorTituloEStatus sem paginacao)
    public List<BacklogEntity> filtrarPorTituloEStatus(String titulo, String status) {
        return filtrarPorTituloEStatus(titulo, status, 0, Integer.MAX_VALUE);
    }
}