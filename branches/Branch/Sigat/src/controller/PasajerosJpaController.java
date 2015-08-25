/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import entity.PasajeroEntity;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.ReservaEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sebastian
 */
public class PasajerosJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasajerosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PasajeroEntity pasajeros) {
        if (pasajeros.getReservasList() == null) {
            pasajeros.setReservasList(new ArrayList<ReservaEntity>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ReservaEntity> attachedReservasList = new ArrayList<ReservaEntity>();
            for (ReservaEntity reservasListReservasToAttach : pasajeros.getReservasList()) {
                reservasListReservasToAttach = em.getReference(reservasListReservasToAttach.getClass(), reservasListReservasToAttach.getIdReserva());
                attachedReservasList.add(reservasListReservasToAttach);
            }
            pasajeros.setReservasList(attachedReservasList);
            em.persist(pasajeros);
            for (ReservaEntity reservasListReservas : pasajeros.getReservasList()) {
                reservasListReservas.getPasajerosList().add(pasajeros);
                reservasListReservas = em.merge(reservasListReservas);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PasajeroEntity pasajeros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PasajeroEntity persistentPasajeros = em.find(PasajeroEntity.class, pasajeros.getIdPasajeros());
            List<ReservaEntity> reservasListOld = persistentPasajeros.getReservasList();
            List<ReservaEntity> reservasListNew = pasajeros.getReservasList();
            List<ReservaEntity> attachedReservasListNew = new ArrayList<ReservaEntity>();
            for (ReservaEntity reservasListNewReservasToAttach : reservasListNew) {
                reservasListNewReservasToAttach = em.getReference(reservasListNewReservasToAttach.getClass(), reservasListNewReservasToAttach.getIdReserva());
                attachedReservasListNew.add(reservasListNewReservasToAttach);
            }
            reservasListNew = attachedReservasListNew;
            pasajeros.setReservasList(reservasListNew);
            pasajeros = em.merge(pasajeros);
            for (ReservaEntity reservasListOldReservas : reservasListOld) {
                if (!reservasListNew.contains(reservasListOldReservas)) {
                    reservasListOldReservas.getPasajerosList().remove(pasajeros);
                    reservasListOldReservas = em.merge(reservasListOldReservas);
                }
            }
            for (ReservaEntity reservasListNewReservas : reservasListNew) {
                if (!reservasListOld.contains(reservasListNewReservas)) {
                    reservasListNewReservas.getPasajerosList().add(pasajeros);
                    reservasListNewReservas = em.merge(reservasListNewReservas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pasajeros.getIdPasajeros();
                if (findPasajeros(id) == null) {
                    throw new NonexistentEntityException("The pasajeros with id " + id + " no longer exists.");
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
            PasajeroEntity pasajeros;
            try {
                pasajeros = em.getReference(PasajeroEntity.class, id);
                pasajeros.getIdPasajeros();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pasajeros with id " + id + " no longer exists.", enfe);
            }
            List<ReservaEntity> reservasList = pasajeros.getReservasList();
            for (ReservaEntity reservasListReservas : reservasList) {
                reservasListReservas.getPasajerosList().remove(pasajeros);
                reservasListReservas = em.merge(reservasListReservas);
            }
            em.remove(pasajeros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PasajeroEntity> findPasajerosEntities() {
        return findPasajerosEntities(true, -1, -1);
    }

    public List<PasajeroEntity> findPasajerosEntities(int maxResults, int firstResult) {
        return findPasajerosEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
	private List<PasajeroEntity> findPasajerosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            @SuppressWarnings("rawtypes")
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PasajeroEntity.class));
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

    public PasajeroEntity findPasajeros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PasajeroEntity.class, id);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
	public int getPasajerosCount() {
        EntityManager em = getEntityManager();
        try {
            @SuppressWarnings("rawtypes")
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PasajeroEntity> rt = cq.from(PasajeroEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
