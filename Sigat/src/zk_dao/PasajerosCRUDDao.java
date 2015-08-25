package zk_dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import controller.PasajerosJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entity.PasajeroEntity;

public class PasajerosCRUDDao {

	public PasajerosCRUDDao() {
	}

	public List<PasajeroEntity> findAll() {

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		PasajerosJpaController pasajerosJPAcontroller = new PasajerosJpaController(
				emf);

		List<PasajeroEntity> pasajerosList = new ArrayList<PasajeroEntity>();
		try {

			// Obtengo todos los eventos de la tabla
			pasajerosList = pasajerosJPAcontroller.findPasajerosEntities();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				emf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return pasajerosList;

	}

	public void delete(PasajeroEntity pasajero) throws IllegalOrphanException {
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		PasajerosJpaController pasajerosJPAcontroller = new PasajerosJpaController(
				emf);
		
		try {
			pasajerosJPAcontroller.destroy(pasajero.getIdPasajeros());
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
		}

	}

	public void insert(PasajeroEntity pasajero) {
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		PasajerosJpaController pasajerosJPAcontroller = new PasajerosJpaController(
				emf);

		pasajerosJPAcontroller.create(pasajero);

	}

	public void update(PasajeroEntity pasajero) {

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		PasajerosJpaController pasajerosJPAcontroller = new PasajerosJpaController(
				emf);

		try {
			pasajerosJPAcontroller.edit(pasajero);
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
