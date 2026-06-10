package br.upf.gamelog.facade;

import br.upf.gamelog.entity.EmprestimoEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class EmprestimoFacade extends AbstractFacade<EmprestimoEntity> {

    @PersistenceContext(unitName = "GameLogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() { return em; }

    public EmprestimoFacade() { super(EmprestimoEntity.class); }

    public long contarTodos() {
        try {
            return em.createQuery("SELECT COUNT(e) FROM EmprestimoEntity e", Long.class)
                .getSingleResult();
        } catch (Exception e) { return 0; }
    }

    /** Busca todos sem paginacao. */
    public List<EmprestimoEntity> buscarTodos() {
        List<EmprestimoEntity> lista = new ArrayList<>();
        try {
            lista = em.createQuery(
                "SELECT e FROM EmprestimoEntity e ORDER BY e.devolvido ASC, e.dataEmprestimo DESC",
                EmprestimoEntity.class).getResultList();
        } catch (Exception e) { System.out.println("Erro EmprestimoFacade.buscarTodos: " + e); }
        return lista;
    }

    /** Busca com paginacao. */
    public List<EmprestimoEntity> buscarTodosPaginado(int firstResult, int pageSize) {
        List<EmprestimoEntity> lista = new ArrayList<>();
        try {
            TypedQuery<EmprestimoEntity> q = em.createQuery(
                "SELECT e FROM EmprestimoEntity e ORDER BY e.devolvido ASC, e.dataEmprestimo DESC",
                EmprestimoEntity.class);
            q.setFirstResult(firstResult);
            q.setMaxResults(pageSize);
            lista = q.getResultList();
        } catch (Exception e) { System.out.println("Erro EmprestimoFacade.buscarTodosPaginado: " + e); }
        return lista;
    }

    /** Emprestimos em aberto (nao devolvidos) — para notificacoes. */
    public List<EmprestimoEntity> buscarEmAberto() {
        List<EmprestimoEntity> lista = new ArrayList<>();
        try {
            lista = em.createQuery(
                "SELECT e FROM EmprestimoEntity e WHERE e.devolvido = false ORDER BY e.previsaoDevolucao ASC",
                EmprestimoEntity.class).getResultList();
        } catch (Exception e) { System.out.println("Erro EmprestimoFacade.buscarEmAberto: " + e); }
        return lista;
    }
}
