/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import controller.exceptions.NonexistentEntityException;
import entity.ReservaEntity;
 
/**
 *
 * @author Sebastian
 */
public class TblnewsJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TblnewsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ReservaEntity reservaEntity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(reservaEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ReservaEntity reservaEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            reservaEntity = em.merge(reservaEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservaEntity.getIdReserva();
                if (findTblnews(id) == null) {
                    throw new NonexistentEntityException("The tblnews with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReservaEntity reservaEntity;
            try {
                reservaEntity = em.getReference(ReservaEntity.class, id);
                reservaEntity.getIdReserva();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblnews with id " + id + " no longer exists.", enfe);
            }
            em.remove(reservaEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ReservaEntity> findTblnewsEntities() {
        return findTblnewsEntities(true, -1, -1);
    }

    public List<ReservaEntity> findTblnewsEntities(int maxResults, int firstResult) {
        return findTblnewsEntities(false, maxResults, firstResult);
    }

    private List<ReservaEntity> findTblnewsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ReservaEntity.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ReservaEntity findTblnews(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReservaEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblnewsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ReservaEntity> rt = cq.from(ReservaEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
