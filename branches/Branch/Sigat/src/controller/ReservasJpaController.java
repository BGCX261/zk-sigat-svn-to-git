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
import entity.ClienteEntity;
import entity.PasajeroEntity;
import entity.ReservaEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sebastian
 */
public class ReservasJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReservasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ReservaEntity reservas) {
        if (reservas.getPasajerosList() == null) {
            reservas.setPasajerosList(new ArrayList<PasajeroEntity>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteEntity clientesIdclientes = reservas.getClientesIdclientes();
            if (clientesIdclientes != null) {
                clientesIdclientes = em.getReference(clientesIdclientes.getClass(), clientesIdclientes.getIdclientes());
                reservas.setClientesIdclientes(clientesIdclientes);
            }
            List<PasajeroEntity> attachedPasajerosList = new ArrayList<PasajeroEntity>();
            for (PasajeroEntity pasajerosListPasajerosToAttach : reservas.getPasajerosList()) {
                pasajerosListPasajerosToAttach = em.getReference(pasajerosListPasajerosToAttach.getClass(), pasajerosListPasajerosToAttach.getIdPasajeros());
                attachedPasajerosList.add(pasajerosListPasajerosToAttach);
            }
            reservas.setPasajerosList(attachedPasajerosList);
            em.persist(reservas);
            if (clientesIdclientes != null) {
                clientesIdclientes.getReservasList().add(reservas);
                clientesIdclientes = em.merge(clientesIdclientes);
            }
            for (PasajeroEntity pasajerosListPasajeros : reservas.getPasajerosList()) {
                pasajerosListPasajeros.getReservasList().add(reservas);
                pasajerosListPasajeros = em.merge(pasajerosListPasajeros);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ReservaEntity reservas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReservaEntity persistentReservas = em.find(ReservaEntity.class, reservas.getIdReserva());
            ClienteEntity clientesIdclientesOld = persistentReservas.getClientesIdclientes();
            ClienteEntity clientesIdclientesNew = reservas.getClientesIdclientes();
            List<PasajeroEntity> pasajerosListOld = persistentReservas.getPasajerosList();
            List<PasajeroEntity> pasajerosListNew = reservas.getPasajerosList();
            if (clientesIdclientesNew != null) {
                clientesIdclientesNew = em.getReference(clientesIdclientesNew.getClass(), clientesIdclientesNew.getIdclientes());
                reservas.setClientesIdclientes(clientesIdclientesNew);
            }
            List<PasajeroEntity> attachedPasajerosListNew = new ArrayList<PasajeroEntity>();
            for (PasajeroEntity pasajerosListNewPasajerosToAttach : pasajerosListNew) {
                pasajerosListNewPasajerosToAttach = em.getReference(pasajerosListNewPasajerosToAttach.getClass(), pasajerosListNewPasajerosToAttach.getIdPasajeros());
                attachedPasajerosListNew.add(pasajerosListNewPasajerosToAttach);
            }
            pasajerosListNew = attachedPasajerosListNew;
            reservas.setPasajerosList(pasajerosListNew);
            reservas = em.merge(reservas);
            if (clientesIdclientesOld != null && !clientesIdclientesOld.equals(clientesIdclientesNew)) {
                clientesIdclientesOld.getReservasList().remove(reservas);
                clientesIdclientesOld = em.merge(clientesIdclientesOld);
            }
            if (clientesIdclientesNew != null && !clientesIdclientesNew.equals(clientesIdclientesOld)) {
                clientesIdclientesNew.getReservasList().add(reservas);
                clientesIdclientesNew = em.merge(clientesIdclientesNew);
            }
            for (PasajeroEntity pasajerosListOldPasajeros : pasajerosListOld) {
                if (!pasajerosListNew.contains(pasajerosListOldPasajeros)) {
                    pasajerosListOldPasajeros.getReservasList().remove(reservas);
                    pasajerosListOldPasajeros = em.merge(pasajerosListOldPasajeros);
                }
            }
            for (PasajeroEntity pasajerosListNewPasajeros : pasajerosListNew) {
                if (!pasajerosListOld.contains(pasajerosListNewPasajeros)) {
                    pasajerosListNewPasajeros.getReservasList().add(reservas);
                    pasajerosListNewPasajeros = em.merge(pasajerosListNewPasajeros);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservas.getIdReserva();
                if (findReservas(id) == null) {
                    throw new NonexistentEntityException("The reservas with id " + id + " no longer exists.");
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
            ReservaEntity reservas;
            try {
                reservas = em.getReference(ReservaEntity.class, id);
                reservas.getIdReserva();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservas with id " + id + " no longer exists.", enfe);
            }
            ClienteEntity clientesIdclientes = reservas.getClientesIdclientes();
            if (clientesIdclientes != null) {
                clientesIdclientes.getReservasList().remove(reservas);
                clientesIdclientes = em.merge(clientesIdclientes);
            }
            List<PasajeroEntity> pasajerosList = reservas.getPasajerosList();
            for (PasajeroEntity pasajerosListPasajeros : pasajerosList) {
                pasajerosListPasajeros.getReservasList().remove(reservas);
                pasajerosListPasajeros = em.merge(pasajerosListPasajeros);
            }
            em.remove(reservas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ReservaEntity> findReservasEntities() {
        return findReservasEntities(true, -1, -1);
    }

    public List<ReservaEntity> findReservasEntities(int maxResults, int firstResult) {
        return findReservasEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
	private List<ReservaEntity> findReservasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            @SuppressWarnings("rawtypes")
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

    public ReservaEntity findReservas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReservaEntity.class, id);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
	public int getReservasCount() {
        EntityManager em = getEntityManager();
        try {
            @SuppressWarnings("rawtypes")
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
