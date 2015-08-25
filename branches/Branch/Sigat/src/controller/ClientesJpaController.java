/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import entity.ClienteEntity;
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
public class ClientesJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClienteEntity clientes) {
        if (clientes.getReservasList() == null) {
            clientes.setReservasList(new ArrayList<ReservaEntity>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ReservaEntity> attachedReservasList = new ArrayList<ReservaEntity>();
            for (ReservaEntity reservasListReservasToAttach : clientes.getReservasList()) {
                reservasListReservasToAttach = em.getReference(reservasListReservasToAttach.getClass(), reservasListReservasToAttach.getIdReserva());
                attachedReservasList.add(reservasListReservasToAttach);
            }
            clientes.setReservasList(attachedReservasList);
            em.persist(clientes);
            for (ReservaEntity reservasListReservas : clientes.getReservasList()) {
                ClienteEntity oldClientesIdclientesOfReservasListReservas = reservasListReservas.getClientesIdclientes();
                reservasListReservas.setClientesIdclientes(clientes);
                reservasListReservas = em.merge(reservasListReservas);
                if (oldClientesIdclientesOfReservasListReservas != null) {
                    oldClientesIdclientesOfReservasListReservas.getReservasList().remove(reservasListReservas);
                    oldClientesIdclientesOfReservasListReservas = em.merge(oldClientesIdclientesOfReservasListReservas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClienteEntity clientes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteEntity persistentClientes = em.find(ClienteEntity.class, clientes.getIdclientes());
            List<ReservaEntity> reservasListOld = persistentClientes.getReservasList();
            List<ReservaEntity> reservasListNew = clientes.getReservasList();
            List<ReservaEntity> attachedReservasListNew = new ArrayList<ReservaEntity>();
            for (ReservaEntity reservasListNewReservasToAttach : reservasListNew) {
                reservasListNewReservasToAttach = em.getReference(reservasListNewReservasToAttach.getClass(), reservasListNewReservasToAttach.getIdReserva());
                attachedReservasListNew.add(reservasListNewReservasToAttach);
            }
            reservasListNew = attachedReservasListNew;
            clientes.setReservasList(reservasListNew);
            clientes = em.merge(clientes);
            for (ReservaEntity reservasListOldReservas : reservasListOld) {
                if (!reservasListNew.contains(reservasListOldReservas)) {
                    reservasListOldReservas.setClientesIdclientes(null);
                    reservasListOldReservas = em.merge(reservasListOldReservas);
                }
            }
            for (ReservaEntity reservasListNewReservas : reservasListNew) {
                if (!reservasListOld.contains(reservasListNewReservas)) {
                    ClienteEntity oldClientesIdclientesOfReservasListNewReservas = reservasListNewReservas.getClientesIdclientes();
                    reservasListNewReservas.setClientesIdclientes(clientes);
                    reservasListNewReservas = em.merge(reservasListNewReservas);
                    if (oldClientesIdclientesOfReservasListNewReservas != null && !oldClientesIdclientesOfReservasListNewReservas.equals(clientes)) {
                        oldClientesIdclientesOfReservasListNewReservas.getReservasList().remove(reservasListNewReservas);
                        oldClientesIdclientesOfReservasListNewReservas = em.merge(oldClientesIdclientesOfReservasListNewReservas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientes.getIdclientes();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
            ClienteEntity clientes;
            try {
                clientes = em.getReference(ClienteEntity.class, id);
                clientes.getIdclientes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<ReservaEntity> reservasList = clientes.getReservasList();
            for (ReservaEntity reservasListReservas : reservasList) {
                reservasListReservas.setClientesIdclientes(null);
                reservasListReservas = em.merge(reservasListReservas);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClienteEntity> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<ClienteEntity> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
	private List<ClienteEntity> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            @SuppressWarnings("rawtypes")
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClienteEntity.class));
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

    public ClienteEntity findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClienteEntity.class, id);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
	public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            @SuppressWarnings("rawtypes")
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClienteEntity> rt = cq.from(ClienteEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
