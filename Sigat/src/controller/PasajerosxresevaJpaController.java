/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.ReservaEntity;
import entity.PasajeroEntity;
import entity.Pasajerosxreseva;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Clase encargada de controlar las transacciones contra la base de datos 
 * del Entity Pasajerosxreservas
 * 
 * @author Sebastian
 */
public class PasajerosxresevaJpaController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor del JPAController
	 * 
	 * @param EntityManagerFactrory emf
	 */
	public PasajerosxresevaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Metodo encargado de persistir la entidad en la Base de Datos
     * 
     * @param Pasajerosxreserva pasajerosxreseva
     * @throws PreexistingEntityException
     * @throws Exception
     */
    public void create(Pasajerosxreseva pasajerosxreseva) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ReservaEntity reservasIdReserva = pasajerosxreseva.getReservasIdReserva();
            if (reservasIdReserva != null) {
                reservasIdReserva = em.getReference(reservasIdReserva.getClass(), reservasIdReserva.getIdReserva());
                pasajerosxreseva.setReservasIdReserva(reservasIdReserva);
            }
            PasajeroEntity pasajerosIdPasajeros = pasajerosxreseva.getPasajerosIdPasajeros();
            if (pasajerosIdPasajeros != null) {
                pasajerosIdPasajeros = em.getReference(pasajerosIdPasajeros.getClass(), pasajerosIdPasajeros.getIdPasajeros());
                pasajerosxreseva.setPasajerosIdPasajeros(pasajerosIdPasajeros);
            }
            em.persist(pasajerosxreseva);
            if (reservasIdReserva != null) {
                reservasIdReserva.getPasajerosxresevaList().add(pasajerosxreseva);
                reservasIdReserva = em.merge(reservasIdReserva);
            }
            if (pasajerosIdPasajeros != null) {
                pasajerosIdPasajeros.getPasajerosxresevaList().add(pasajerosxreseva);
                pasajerosIdPasajeros = em.merge(pasajerosIdPasajeros);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPasajerosxreseva(pasajerosxreseva.getIdPasajeroReserva()) != null) {
                throw new PreexistingEntityException("Pasajerosxreseva " + pasajerosxreseva + " already exists.", ex);
            }
            throw ex;
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
     * @param Pasajerosxreserva pasajerosxreseva
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(Pasajerosxreseva pasajerosxreseva) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pasajerosxreseva persistentPasajerosxreseva = em.find(Pasajerosxreseva.class, pasajerosxreseva.getIdPasajeroReserva());
            ReservaEntity reservasIdReservaOld = persistentPasajerosxreseva.getReservasIdReserva();
            ReservaEntity reservasIdReservaNew = pasajerosxreseva.getReservasIdReserva();
            PasajeroEntity pasajerosIdPasajerosOld = persistentPasajerosxreseva.getPasajerosIdPasajeros();
            PasajeroEntity pasajerosIdPasajerosNew = pasajerosxreseva.getPasajerosIdPasajeros();
            if (reservasIdReservaNew != null) {
                reservasIdReservaNew = em.getReference(reservasIdReservaNew.getClass(), reservasIdReservaNew.getIdReserva());
                pasajerosxreseva.setReservasIdReserva(reservasIdReservaNew);
            }
            if (pasajerosIdPasajerosNew != null) {
                pasajerosIdPasajerosNew = em.getReference(pasajerosIdPasajerosNew.getClass(), pasajerosIdPasajerosNew.getIdPasajeros());
                pasajerosxreseva.setPasajerosIdPasajeros(pasajerosIdPasajerosNew);
            }
            pasajerosxreseva = em.merge(pasajerosxreseva);
            if (reservasIdReservaOld != null && !reservasIdReservaOld.equals(reservasIdReservaNew)) {
                reservasIdReservaOld.getPasajerosxresevaList().remove(pasajerosxreseva);
                reservasIdReservaOld = em.merge(reservasIdReservaOld);
            }
            if (reservasIdReservaNew != null && !reservasIdReservaNew.equals(reservasIdReservaOld)) {
                reservasIdReservaNew.getPasajerosxresevaList().add(pasajerosxreseva);
                reservasIdReservaNew = em.merge(reservasIdReservaNew);
            }
            if (pasajerosIdPasajerosOld != null && !pasajerosIdPasajerosOld.equals(pasajerosIdPasajerosNew)) {
                pasajerosIdPasajerosOld.getPasajerosxresevaList().remove(pasajerosxreseva);
                pasajerosIdPasajerosOld = em.merge(pasajerosIdPasajerosOld);
            }
            if (pasajerosIdPasajerosNew != null && !pasajerosIdPasajerosNew.equals(pasajerosIdPasajerosOld)) {
                pasajerosIdPasajerosNew.getPasajerosxresevaList().add(pasajerosxreseva);
                pasajerosIdPasajerosNew = em.merge(pasajerosIdPasajerosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pasajerosxreseva.getIdPasajeroReserva();
                if (findPasajerosxreseva(id) == null) {
                    throw new NonexistentEntityException("The pasajerosxreseva with id " + id + " no longer exists.");
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
            Pasajerosxreseva pasajerosxreseva;
            try {
                pasajerosxreseva = em.getReference(Pasajerosxreseva.class, id);
                pasajerosxreseva.getIdPasajeroReserva();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pasajerosxreseva with id " + id + " no longer exists.", enfe);
            }
            ReservaEntity reservasIdReserva = pasajerosxreseva.getReservasIdReserva();
            if (reservasIdReserva != null) {
                reservasIdReserva.getPasajerosxresevaList().remove(pasajerosxreseva);
                reservasIdReserva = em.merge(reservasIdReserva);
            }
            PasajeroEntity pasajerosIdPasajeros = pasajerosxreseva.getPasajerosIdPasajeros();
            if (pasajerosIdPasajeros != null) {
                pasajerosIdPasajeros.getPasajerosxresevaList().remove(pasajerosxreseva);
                pasajerosIdPasajeros = em.merge(pasajerosIdPasajeros);
            }
            em.remove(pasajerosxreseva);
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
     * @return List<Pasajerosxreserva> allPasajerosXreserva
     */
    public List<Pasajerosxreseva> findPasajerosxresevaEntities() {
        return findPasajerosxresevaEntities(true, -1, -1);
    }
    public List<Pasajerosxreseva> findPasajerosReserva(PasajeroEntity pasajero) {
        return getEntityManager().createNamedQuery("Pasajerosxreseva.findByIdPasajeros", Pasajerosxreseva.class).setParameter("idPasajero", pasajero).getResultList();
    	//return findPasajerosxresevaEntities(true, -1, -1);
    }
    public List<Pasajerosxreseva> findReservaPasajerosxresevaEntities(ReservaEntity reserva) {
        List<Pasajerosxreseva> lista = getEntityManager().createNamedQuery("Pasajerosxreseva.findByIdReserva", Pasajerosxreseva.class).setParameter("idReserva", reserva).getResultList();
      
        return lista;
    }

    /**
     * Metodo encargado de realizar una busqueda acotada de Entidades en la 
     * Base de datos segun los parametros ingresados 
     * 
     * @param int maxResults
     * @param int firstResult
     * @return List<Pasajerosxreserva> listaPasajerosXreserva
     */
    public List<Pasajerosxreseva> findPasajerosxresevaEntities(int maxResults, int firstResult) {
        return findPasajerosxresevaEntities(false, maxResults, firstResult);
    }

    /**
     * Metodo general de busqueda acotada de Entidades en la 
     * Base de datos segun los parametros ingresados 
     *
     * @param boolean all
     * @param int maxResults
     * @param int firstResult
     * @return List<Pasajerosxreserva> listaPasajxReservas
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Pasajerosxreseva> findPasajerosxresevaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pasajerosxreseva.class));
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
     * @return Pasajerosxreseva pasajeroXreserva
     */
    public Pasajerosxreseva findPasajerosxreseva(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pasajerosxreseva.class, id);
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
    public int getPasajerosxresevaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pasajerosxreseva> rt = cq.from(Pasajerosxreseva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
