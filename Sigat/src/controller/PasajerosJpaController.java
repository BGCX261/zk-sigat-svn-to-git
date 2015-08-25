/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entity.PasajeroEntity;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Pasajerosxreseva;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Clase encargada de controlar las transacciones contra la base de datos del
 * Entity Pasajeros
 * 
 * @author Sebastian
 */
public class PasajerosJpaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor del JPAController
	 * 
	 * @param EntityManagerFactrory
	 *            emf
	 */
	public PasajerosJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Metodo encargado de persistir la entidad en la Base de Datos
	 * 
	 * @param PasajeroEntity pasajeros
	 */
	public void create(PasajeroEntity pasajeros) {
		if (pasajeros.getPasajerosxresevaList() == null) {
			pasajeros
					.setPasajerosxresevaList(new ArrayList<Pasajerosxreseva>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			List<Pasajerosxreseva> attachedPasajerosxresevaList = new ArrayList<Pasajerosxreseva>();
			for (Pasajerosxreseva pasajerosxresevaListPasajerosxresevaToAttach : pasajeros
					.getPasajerosxresevaList()) {
				pasajerosxresevaListPasajerosxresevaToAttach = em
						.getReference(
								pasajerosxresevaListPasajerosxresevaToAttach
										.getClass(),
								pasajerosxresevaListPasajerosxresevaToAttach
										.getIdPasajeroReserva());
				attachedPasajerosxresevaList
						.add(pasajerosxresevaListPasajerosxresevaToAttach);
			}
			pasajeros.setPasajerosxresevaList(attachedPasajerosxresevaList);
			em.persist(pasajeros);
			for (Pasajerosxreseva pasajerosxresevaListPasajerosxreseva : pasajeros
					.getPasajerosxresevaList()) {
				PasajeroEntity oldPasajerosIdPasajerosOfPasajerosxresevaListPasajerosxreseva = pasajerosxresevaListPasajerosxreseva
						.getPasajerosIdPasajeros();
				pasajerosxresevaListPasajerosxreseva
						.setPasajerosIdPasajeros(pasajeros);
				pasajerosxresevaListPasajerosxreseva = em
						.merge(pasajerosxresevaListPasajerosxreseva);
				if (oldPasajerosIdPasajerosOfPasajerosxresevaListPasajerosxreseva != null) {
					oldPasajerosIdPasajerosOfPasajerosxresevaListPasajerosxreseva
							.getPasajerosxresevaList().remove(
									pasajerosxresevaListPasajerosxreseva);
					oldPasajerosIdPasajerosOfPasajerosxresevaListPasajerosxreseva = em
							.merge(oldPasajerosIdPasajerosOfPasajerosxresevaListPasajerosxreseva);
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
	 * @param PasajeroEntity pasajeros
	 * @throws IllegalOrphanException
	 * @throws NonexistentEntityException
	 * @throws Exception
	 */
	public void edit(PasajeroEntity pasajeros) throws IllegalOrphanException,
			NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			PasajeroEntity persistentPasajeros = em.find(PasajeroEntity.class,
					pasajeros.getIdPasajeros());
			List<Pasajerosxreseva> pasajerosxresevaListOld = persistentPasajeros
					.getPasajerosxresevaList();
			List<Pasajerosxreseva> pasajerosxresevaListNew = pasajeros
					.getPasajerosxresevaList();
			List<String> illegalOrphanMessages = null;
			for (Pasajerosxreseva pasajerosxresevaListOldPasajerosxreseva : pasajerosxresevaListOld) {
				if (!pasajerosxresevaListNew
						.contains(pasajerosxresevaListOldPasajerosxreseva)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages
							.add("You must retain Pasajerosxreseva "
									+ pasajerosxresevaListOldPasajerosxreseva
									+ " since its pasajerosIdPasajeros field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			List<Pasajerosxreseva> attachedPasajerosxresevaListNew = new ArrayList<Pasajerosxreseva>();
			for (Pasajerosxreseva pasajerosxresevaListNewPasajerosxresevaToAttach : pasajerosxresevaListNew) {
				pasajerosxresevaListNewPasajerosxresevaToAttach = em
						.getReference(
								pasajerosxresevaListNewPasajerosxresevaToAttach
										.getClass(),
								pasajerosxresevaListNewPasajerosxresevaToAttach
										.getIdPasajeroReserva());
				attachedPasajerosxresevaListNew
						.add(pasajerosxresevaListNewPasajerosxresevaToAttach);
			}
			pasajerosxresevaListNew = attachedPasajerosxresevaListNew;
			pasajeros.setPasajerosxresevaList(pasajerosxresevaListNew);
			pasajeros = em.merge(pasajeros);
			for (Pasajerosxreseva pasajerosxresevaListNewPasajerosxreseva : pasajerosxresevaListNew) {
				if (!pasajerosxresevaListOld
						.contains(pasajerosxresevaListNewPasajerosxreseva)) {
					PasajeroEntity oldPasajerosIdPasajerosOfPasajerosxresevaListNewPasajerosxreseva = pasajerosxresevaListNewPasajerosxreseva
							.getPasajerosIdPasajeros();
					pasajerosxresevaListNewPasajerosxreseva
							.setPasajerosIdPasajeros(pasajeros);
					pasajerosxresevaListNewPasajerosxreseva = em
							.merge(pasajerosxresevaListNewPasajerosxreseva);
					if (oldPasajerosIdPasajerosOfPasajerosxresevaListNewPasajerosxreseva != null
							&& !oldPasajerosIdPasajerosOfPasajerosxresevaListNewPasajerosxreseva
									.equals(pasajeros)) {
						oldPasajerosIdPasajerosOfPasajerosxresevaListNewPasajerosxreseva
								.getPasajerosxresevaList()
								.remove(pasajerosxresevaListNewPasajerosxreseva);
						oldPasajerosIdPasajerosOfPasajerosxresevaListNewPasajerosxreseva = em
								.merge(oldPasajerosIdPasajerosOfPasajerosxresevaListNewPasajerosxreseva);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = pasajeros.getIdPasajeros();
				if (findPasajeros(id) == null) {
					throw new NonexistentEntityException(
							"The pasajeros with id " + id
									+ " no longer exists.");
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
	 * @throws IllegalOrphanException
	 * @throws NonexistentEntityException
	 */
	public void destroy(Integer id) throws IllegalOrphanException,
			NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			PasajeroEntity pasajeros;
			try {
				pasajeros = em.getReference(PasajeroEntity.class, id);
				pasajeros.getIdPasajeros();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The pasajeros with id "
						+ id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			List<Pasajerosxreseva> pasajerosxresevaListOrphanCheck = pasajeros
					.getPasajerosxresevaList();
			for (Pasajerosxreseva pasajerosxresevaListOrphanCheckPasajerosxreseva : pasajerosxresevaListOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages
						.add("This Pasajeros ("
								+ pasajeros
								+ ") cannot be destroyed since the Pasajerosxreseva "
								+ pasajerosxresevaListOrphanCheckPasajerosxreseva
								+ " in its pasajerosxresevaList field has a non-nullable pasajerosIdPasajeros field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(pasajeros);
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
	 * @return List<PasajeroEntity> allPasajeros
	 */
	public List<PasajeroEntity> findPasajerosEntities() {
		return findPasajerosEntities(true, -1, -1);
	}

	/**
	 * Metodo encargado de realizar una busqueda acotada de Entidades en la 
     * Base de datos segun los parametros ingresados 
     * 
	 * @param int maxResults
	 * @param int firstResult
	 * @return List<PasajeroEntity> listaPasajeros
	 */
	public List<PasajeroEntity> findPasajerosEntities(int maxResults,
			int firstResult) {
		return findPasajerosEntities(false, maxResults, firstResult);
	}

	/**
	 * Metodo general de busqueda acotada de Entidades en la 
     * Base de datos segun los parametros ingresados 
     *
	 * @param boolean all
	 * @param int maxResults
	 * @param int firstResult
	 * @return List<PasajeroEntity> listaPasajeros
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<PasajeroEntity> findPasajerosEntities(boolean all,
			int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
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

	/**
	 * Metodo encargado de buscar en la Base de Datos la entidad con el ID pasado 
     * por parametros 
     * 
     * @param Integer id
     * @return PasajeroEntity pasajero
	 */
	public PasajeroEntity findPasajeros(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(PasajeroEntity.class, id);
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
	public int getPasajerosCount() {
		EntityManager em = getEntityManager();
		try {
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
