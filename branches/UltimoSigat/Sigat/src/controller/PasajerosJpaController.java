/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
 
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.ReservaEntity;
import java.util.ArrayList;
import java.util.List;
import entity.PasajeroEntity;
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

    public void create(PasajeroEntity pasajeroEntity) {
        if (pasajeroEntity.getReservasList() == null) {
            pasajeroEntity.setReservasList(new ArrayList<ReservaEntity>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ReservaEntity> attachedReservasList = new ArrayList<ReservaEntity>();
            for (ReservaEntity reservasListReservasToAttach : pasajeroEntity.getReservasList()) {
                reservasListReservasToAttach = em.getReference(reservasListReservasToAttach.getClass(), reservasListReservasToAttach.getIdReserva());
                attachedReservasList.add(reservasListReservasToAttach);
            }
            pasajeroEntity.setReservasList(attachedReservasList);
            em.persist(pasajeroEntity);
            for (ReservaEntity reservasListReservas : pasajeroEntity.getReservasList()) {
                reservasListReservas.getPasajerosList().add(pasajeroEntity);
                reservasListReservas = em.merge(reservasListReservas);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PasajeroEntity pasajeroEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PasajeroEntity persistentPasajeros = em.find(PasajeroEntity.class, pasajeroEntity.getIdPasajeros());
            List<ReservaEntity> reservasListOld = persistentPasajeros.getReservasList();
            List<ReservaEntity> reservasListNew = pasajeroEntity.getReservasList();
            List<ReservaEntity> attachedReservasListNew = new ArrayList<ReservaEntity>();
            for (ReservaEntity reservasListNewReservasToAttach : reservasListNew) {
                reservasListNewReservasToAttach = em.getReference(reservasListNewReservasToAttach.getClass(), reservasListNewReservasToAttach.getIdReserva());
                attachedReservasListNew.add(reservasListNewReservasToAttach);
            }
            reservasListNew = attachedReservasListNew;
            pasajeroEntity.setReservasList(reservasListNew);
            pasajeroEntity = em.merge(pasajeroEntity);
            for (ReservaEntity reservasListOldReservas : reservasListOld) {
                if (!reservasListNew.contains(reservasListOldReservas)) {
                    reservasListOldReservas.getPasajerosList().remove(pasajeroEntity);
                    reservasListOldReservas = em.merge(reservasListOldReservas);
                }
            }
            for (ReservaEntity reservasListNewReservas : reservasListNew) {
                if (!reservasListOld.contains(reservasListNewReservas)) {
                    reservasListNewReservas.getPasajerosList().add(pasajeroEntity);
                    reservasListNewReservas = em.merge(reservasListNewReservas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pasajeroEntity.getIdPasajeros();
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
            PasajeroEntity pasajeroEntity;
            try {
                pasajeroEntity = em.getReference(PasajeroEntity.class, id);
                pasajeroEntity.getIdPasajeros();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pasajeros with id " + id + " no longer exists.", enfe);
            }
            List<ReservaEntity> reservasList = pasajeroEntity.getReservasList();
            for (ReservaEntity reservasListReservas : reservasList) {
                reservasListReservas.getPasajerosList().remove(pasajeroEntity);
                reservasListReservas = em.merge(reservasListReservas);
            }
            em.remove(pasajeroEntity);
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

    private List<PasajeroEntity> findPasajerosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
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

    public int getPasajerosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<PasajeroEntity> rt = cq.from(PasajeroEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
