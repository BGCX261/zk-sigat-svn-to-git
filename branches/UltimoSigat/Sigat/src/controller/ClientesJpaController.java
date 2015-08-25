/*
 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.ReservaEntity;
import java.util.ArrayList;
import java.util.List;
import entity.ClienteEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
 
/**
 *
 * @author Sebastian
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClienteEntity clienteEntity) {
        if (clienteEntity.getReservasList() == null) {
            clienteEntity.setReservasList(new ArrayList<ReservaEntity>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ReservaEntity> attachedReservasList = new ArrayList<ReservaEntity>();
            for (ReservaEntity reservasListReservasToAttach : clienteEntity.getReservasList()) {
                reservasListReservasToAttach = em.getReference(reservasListReservasToAttach.getClass(), reservasListReservasToAttach.getIdReserva());
                attachedReservasList.add(reservasListReservasToAttach);
            }
            clienteEntity.setReservasList(attachedReservasList);
            em.persist(clienteEntity);
            for (ReservaEntity reservasListReservas : clienteEntity.getReservasList()) {
                ClienteEntity oldClientesIdclientes1OfReservasListReservas = reservasListReservas.getClientesIdclientes1();
                reservasListReservas.setClientesIdclientes1(clienteEntity);
                reservasListReservas = em.merge(reservasListReservas);
                if (oldClientesIdclientes1OfReservasListReservas != null) {
                    oldClientesIdclientes1OfReservasListReservas.getReservasList().remove(reservasListReservas);
                    oldClientesIdclientes1OfReservasListReservas = em.merge(oldClientesIdclientes1OfReservasListReservas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClienteEntity clienteEntity) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteEntity persistentClientes = em.find(ClienteEntity.class, clienteEntity.getIdclientes());
            List<ReservaEntity> reservasListOld = persistentClientes.getReservasList();
            List<ReservaEntity> reservasListNew = clienteEntity.getReservasList();
            List<String> illegalOrphanMessages = null;
            for (ReservaEntity reservasListOldReservas : reservasListOld) {
                if (!reservasListNew.contains(reservasListOldReservas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ReservaEntity " + reservasListOldReservas + " since its clientesIdclientes1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ReservaEntity> attachedReservasListNew = new ArrayList<ReservaEntity>();
            for (ReservaEntity reservasListNewReservasToAttach : reservasListNew) {
                reservasListNewReservasToAttach = em.getReference(reservasListNewReservasToAttach.getClass(), reservasListNewReservasToAttach.getIdReserva());
                attachedReservasListNew.add(reservasListNewReservasToAttach);
            }
            reservasListNew = attachedReservasListNew;
            clienteEntity.setReservasList(reservasListNew);
            clienteEntity = em.merge(clienteEntity);
            for (ReservaEntity reservasListNewReservas : reservasListNew) {
                if (!reservasListOld.contains(reservasListNewReservas)) {
                    ClienteEntity oldClientesIdclientes1OfReservasListNewReservas = reservasListNewReservas.getClientesIdclientes1();
                    reservasListNewReservas.setClientesIdclientes1(clienteEntity);
                    reservasListNewReservas = em.merge(reservasListNewReservas);
                    if (oldClientesIdclientes1OfReservasListNewReservas != null && !oldClientesIdclientes1OfReservasListNewReservas.equals(clienteEntity)) {
                        oldClientesIdclientes1OfReservasListNewReservas.getReservasList().remove(reservasListNewReservas);
                        oldClientesIdclientes1OfReservasListNewReservas = em.merge(oldClientesIdclientes1OfReservasListNewReservas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clienteEntity.getIdclientes();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClienteEntity clienteEntity;
            try {
                clienteEntity = em.getReference(ClienteEntity.class, id);
                clienteEntity.getIdclientes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ReservaEntity> reservasListOrphanCheck = clienteEntity.getReservasList();
            for (ReservaEntity reservasListOrphanCheckReservas : reservasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClienteEntity (" + clienteEntity + ") cannot be destroyed since the ReservaEntity " + reservasListOrphanCheckReservas + " in its reservasList field has a non-nullable clientesIdclientes1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(clienteEntity);
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

    private List<ClienteEntity> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
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

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
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
