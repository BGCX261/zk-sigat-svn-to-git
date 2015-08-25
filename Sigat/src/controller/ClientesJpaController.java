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
 * Clase encargada de controlar las transacciones contra la base de datos 
 * del Entity Clientes
 * 
 * @author Sebastian
 */
public class ClientesJpaController implements Serializable {

    
	private static final long serialVersionUID = 1L;
    
	/**
	 * Constructor del JPAController
	 * 
	 * @param EntityManagerFactrory emf
	 */
	public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Metodo encargado de persistir la entidad en la Base de Datos
     * 
     * @param ClienteEntity clientes
     */
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
                ClienteEntity oldClientesIdclientes1OfReservasListReservas = reservasListReservas.getClientesIdclientes1();
                reservasListReservas.setClientesIdclientes1(clientes);
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

    /**
     * Metodo encargado de realizar un Update en la Tabla para la entidad recibida
     * como parametro
     * 
     * @param ClienteEntity clientes
     * @throws NonexistentEntityException
     * @throws Exception
     */
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
                    reservasListOldReservas.setClientesIdclientes1(null);
                    reservasListOldReservas = em.merge(reservasListOldReservas);
                }
            }
            for (ReservaEntity reservasListNewReservas : reservasListNew) {
                if (!reservasListOld.contains(reservasListNewReservas)) {
                    ClienteEntity oldClientesIdclientes1OfReservasListNewReservas = reservasListNewReservas.getClientesIdclientes1();
                    reservasListNewReservas.setClientesIdclientes1(clientes);
                    reservasListNewReservas = em.merge(reservasListNewReservas);
                    if (oldClientesIdclientes1OfReservasListNewReservas != null && !oldClientesIdclientes1OfReservasListNewReservas.equals(clientes)) {
                        oldClientesIdclientes1OfReservasListNewReservas.getReservasList().remove(reservasListNewReservas);
                        oldClientesIdclientes1OfReservasListNewReservas = em.merge(oldClientesIdclientes1OfReservasListNewReservas);
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

    /**
     * Metodo encargado de destruir de la base de datos la entidad con el ID 
     * recibido como parametro 
     * 
     * @param Integer id
     * @throws NonexistentEntityException
     */
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
                reservasListReservas.setClientesIdclientes1(null);
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

    /**
     * Metodo encargado de recolectar de la Base de Datos TODOS las entidades
     * existentes 
     * 
     * @return List<ClienteEntity> listaClientes
     */
    public List<ClienteEntity> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    /**
     * Metodo encargado de realizar una busqueda acotada de Entidades en la 
     * Base de datos segun los parametros ingresados 
     * 
     * @param int maxResults
     * @param int firstResult
     * @return List<ClienteEntity> listaClientes
     */
    public List<ClienteEntity> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    /**
     * Metodo general de busqueda acotada de Entidades en la 
     * Base de datos segun los parametros ingresados 
     * 
     * @param boolean all
     * @param int maxResults
     * @param int firstResult
     * @return List<ClienteEntity> listaClientes
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
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

    /**
     * Metodo encargado de buscar en la Base de Datos la entidad con el ID pasado 
     * por parametros 
     * 
     * @param Integer id
     * @return ClienteEntity cliente
     */
    public ClienteEntity findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClienteEntity.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Metodo encargado de retornar la cantidad de Entidades en tabla
     * 
     * @return int cantidad
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
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
