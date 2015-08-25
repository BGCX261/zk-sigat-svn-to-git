package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;

import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import entity.ClienteEntity;
import entity.DepartamentoEntity;
import entity.Pasajerosxreseva;
import entity.ReservaEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Clase encargada de controlar las transacciones contra la base de datos del
 * Entity Reservas
 * 
 * @author Sebastian
 */
public class ReservasJpaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor del JPAController
	 * 
	 * @param EntityManagerFactrory emf
	 */
	public ReservasJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Metodo encargado de persistir la entidad en la Base de Datos
	 * 
	 * @param ReservaEntity reservas
	 */
	public void create(ReservaEntity reservas) {
		if (reservas.getPasajerosxresevaList() == null) {
			reservas.setPasajerosxresevaList(new ArrayList<Pasajerosxreseva>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			DepartamentoEntity departamentosIdDepartamento1 = reservas
					.getDepartamentosIdDepartamento1();
			if (departamentosIdDepartamento1 != null) {
				departamentosIdDepartamento1 = em.getReference(
						departamentosIdDepartamento1.getClass(),
						departamentosIdDepartamento1.getIdDepartamento());
				reservas.setDepartamentosIdDepartamento1(departamentosIdDepartamento1);
			}
			ClienteEntity clientesIdclientes1 = reservas
					.getClientesIdclientes1();
			if (clientesIdclientes1 != null) {
				clientesIdclientes1 = em.getReference(
						clientesIdclientes1.getClass(),
						clientesIdclientes1.getIdclientes());
				reservas.setClientesIdclientes1(clientesIdclientes1);
			}
			List<Pasajerosxreseva> attachedPasajerosxresevaList = new ArrayList<Pasajerosxreseva>();
			for (Pasajerosxreseva pasajerosxresevaListPasajerosxresevaToAttach : reservas
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
			reservas.setPasajerosxresevaList(attachedPasajerosxresevaList);
			em.persist(reservas);
			if (departamentosIdDepartamento1 != null) {
				departamentosIdDepartamento1.getReservasList().add(reservas);
				departamentosIdDepartamento1 = em
						.merge(departamentosIdDepartamento1);
			}
			if (clientesIdclientes1 != null) {
				clientesIdclientes1.getReservasList().add(reservas);
				clientesIdclientes1 = em.merge(clientesIdclientes1);
			}
			for (Pasajerosxreseva pasajerosxresevaListPasajerosxreseva : reservas
					.getPasajerosxresevaList()) {
				ReservaEntity oldReservasIdReservaOfPasajerosxresevaListPasajerosxreseva = pasajerosxresevaListPasajerosxreseva
						.getReservasIdReserva();
				pasajerosxresevaListPasajerosxreseva
						.setReservasIdReserva(reservas);
				pasajerosxresevaListPasajerosxreseva = em
						.merge(pasajerosxresevaListPasajerosxreseva);
				if (oldReservasIdReservaOfPasajerosxresevaListPasajerosxreseva != null) {
					oldReservasIdReservaOfPasajerosxresevaListPasajerosxreseva
							.getPasajerosxresevaList().remove(
									pasajerosxresevaListPasajerosxreseva);
					oldReservasIdReservaOfPasajerosxresevaListPasajerosxreseva = em
							.merge(oldReservasIdReservaOfPasajerosxresevaListPasajerosxreseva);
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
	 * Metodo encargado de realizar un Update en la Tabla para la entidad
	 * recibida como parametro
	 * 
	 * @param ReservaEntity
	 *            reservas
	 * @throws IllegalOrphanException
	 * @throws NonexistentEntityException
	 * @throws Exception
	 */
	public void edit(ReservaEntity reservas) throws IllegalOrphanException,
			NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			ReservaEntity persistentReservas = em.find(ReservaEntity.class,
					reservas.getIdReserva());
			DepartamentoEntity departamentosIdDepartamento1Old = persistentReservas
					.getDepartamentosIdDepartamento1();
			DepartamentoEntity departamentosIdDepartamento1New = reservas
					.getDepartamentosIdDepartamento1();
			ClienteEntity clientesIdclientes1Old = persistentReservas
					.getClientesIdclientes1();
			ClienteEntity clientesIdclientes1New = reservas
					.getClientesIdclientes1();
			List<Pasajerosxreseva> pasajerosxresevaListOld = persistentReservas
					.getPasajerosxresevaList();
			List<Pasajerosxreseva> pasajerosxresevaListNew = reservas
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
									+ " since its reservasIdReserva field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			if (departamentosIdDepartamento1New != null) {
				departamentosIdDepartamento1New = em.getReference(
						departamentosIdDepartamento1New.getClass(),
						departamentosIdDepartamento1New.getIdDepartamento());
				reservas.setDepartamentosIdDepartamento1(departamentosIdDepartamento1New);
			}
			if (clientesIdclientes1New != null) {
				clientesIdclientes1New = em.getReference(
						clientesIdclientes1New.getClass(),
						clientesIdclientes1New.getIdclientes());
				reservas.setClientesIdclientes1(clientesIdclientes1New);
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
			reservas.setPasajerosxresevaList(pasajerosxresevaListNew);
			reservas = em.merge(reservas);
			if (departamentosIdDepartamento1Old != null
					&& !departamentosIdDepartamento1Old
							.equals(departamentosIdDepartamento1New)) {
				departamentosIdDepartamento1Old.getReservasList().remove(
						reservas);
				departamentosIdDepartamento1Old = em
						.merge(departamentosIdDepartamento1Old);
			}
			if (departamentosIdDepartamento1New != null
					&& !departamentosIdDepartamento1New
							.equals(departamentosIdDepartamento1Old)) {
				departamentosIdDepartamento1New.getReservasList().add(reservas);
				departamentosIdDepartamento1New = em
						.merge(departamentosIdDepartamento1New);
			}
			if (clientesIdclientes1Old != null
					&& !clientesIdclientes1Old.equals(clientesIdclientes1New)) {
				clientesIdclientes1Old.getReservasList().remove(reservas);
				clientesIdclientes1Old = em.merge(clientesIdclientes1Old);
			}
			if (clientesIdclientes1New != null
					&& !clientesIdclientes1New.equals(clientesIdclientes1Old)) {
				clientesIdclientes1New.getReservasList().add(reservas);
				clientesIdclientes1New = em.merge(clientesIdclientes1New);
			}
			for (Pasajerosxreseva pasajerosxresevaListNewPasajerosxreseva : pasajerosxresevaListNew) {
				if (!pasajerosxresevaListOld
						.contains(pasajerosxresevaListNewPasajerosxreseva)) {
					ReservaEntity oldReservasIdReservaOfPasajerosxresevaListNewPasajerosxreseva = pasajerosxresevaListNewPasajerosxreseva
							.getReservasIdReserva();
					pasajerosxresevaListNewPasajerosxreseva
							.setReservasIdReserva(reservas);
					pasajerosxresevaListNewPasajerosxreseva = em
							.merge(pasajerosxresevaListNewPasajerosxreseva);
					if (oldReservasIdReservaOfPasajerosxresevaListNewPasajerosxreseva != null
							&& !oldReservasIdReservaOfPasajerosxresevaListNewPasajerosxreseva
									.equals(reservas)) {
						oldReservasIdReservaOfPasajerosxresevaListNewPasajerosxreseva
								.getPasajerosxresevaList()
								.remove(pasajerosxresevaListNewPasajerosxreseva);
						oldReservasIdReservaOfPasajerosxresevaListNewPasajerosxreseva = em
								.merge(oldReservasIdReservaOfPasajerosxresevaListNewPasajerosxreseva);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = reservas.getIdReserva();
				if (findReservas(id) == null) {
					throw new NonexistentEntityException(
							"The reservas with id " + id + " no longer exists.");
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

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ReservasJpaController reservaJPA = new ReservasJpaController(emf);
		// Busca una resrva en particular
		ReservaEntity reserva = reservaJPA.findReservas(id);

		PasajerosxresevaJpaController pasajerosJPAcontroller = new PasajerosxresevaJpaController(
				emf);
		// Busca lista de pasajeros de una reserva por codigo de reserva
		List<Pasajerosxreseva> pasajero = pasajerosJPAcontroller
				.findReservaPasajerosxresevaEntities(reserva);

		// Manda a destruir una fila segun codigo de la tabla
		for (int i = 0; i < pasajero.size(); i++) {

			try {
				pasajerosJPAcontroller.destroy(pasajero.get(i)
						.getIdPasajeroReserva());
			} catch (NumberFormatException e) {
				
				e.printStackTrace();
			} catch (NonexistentEntityException e) {
				
				e.printStackTrace();
			}
		}
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			ReservaEntity reservas;
			try {
				reservas = em.getReference(ReservaEntity.class, id);
				reservas.getIdReserva();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The reservas with id "
						+ id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			List<Pasajerosxreseva> pasajerosxresevaListOrphanCheck = reservas
					.getPasajerosxresevaList();
			for (Pasajerosxreseva pasajerosxresevaListOrphanCheckPasajerosxreseva : pasajerosxresevaListOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages
						.add("This Reservas ("
								+ reservas
								+ ") cannot be destroyed since the Pasajerosxreseva "
								+ pasajerosxresevaListOrphanCheckPasajerosxreseva
								+ " in its pasajerosxresevaList field has a non-nullable reservasIdReserva field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			DepartamentoEntity departamentosIdDepartamento1 = reservas
					.getDepartamentosIdDepartamento1();
			if (departamentosIdDepartamento1 != null) {
				departamentosIdDepartamento1.getReservasList().remove(reservas);
				departamentosIdDepartamento1 = em
						.merge(departamentosIdDepartamento1);
			}
			ClienteEntity clientesIdclientes1 = reservas
					.getClientesIdclientes1();
			if (clientesIdclientes1 != null) {
				clientesIdclientes1.getReservasList().remove(reservas);
				clientesIdclientes1 = em.merge(clientesIdclientes1);
			}
			em.remove(reservas);
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
	 * @return List<ReservaEntity> allReserva
	 */
	public List<ReservaEntity> findReservasEntities() {
		return findReservasEntities(true, -1, -1);
	}

	/**
	 * Metodo encargado de realizar una busqueda acotada de Entidades en la Base
	 * de datos segun los parametros ingresados
	 * 
	 * @param int maxResults
	 * @param int firstResult
	 * @return List<ReservaEntity> listaReservas
	 */
	public List<ReservaEntity> findReservasEntities(int maxResults,
			int firstResult) {
		return findReservasEntities(false, maxResults, firstResult);
	}

	/**
	 * Metodo general de busqueda acotada de Entidades en la Base de datos segun
	 * los parametros ingresados
	 * 
	 * @param boolean all
	 * @param int maxResults
	 * @param int firstResult
	 * @return List<ReservaEntity> listaReservas
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<ReservaEntity> findReservasEntities(boolean all,
			int maxResults, int firstResult) {
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

	/**
	 * Metodo encargado de buscar en la Base de Datos la entidad con el ID
	 * pasado por parametros
	 * 
	 * @param Integer
	 *            id
	 * @return ReservaEntity reserva
	 */
	public ReservaEntity findReservas(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(ReservaEntity.class, id);
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
	public int getReservasCount() {
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
