package zk_dao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import controller.PasajerosxresevaJpaController;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.Pasajerosxreseva;
/*
 * Clase se encarga de manejar la Entidad Comodidades a traves de 
 * su jpaController, para la obtencion de los datos de la base
 * de Datos
 */

public class PasajeroxReservaDAO {

	public PasajeroxReservaDAO() {
	}

	public List<Pasajerosxreseva> findAll() {

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		PasajerosxresevaJpaController pasajeroReservaJPAcontroller = new PasajerosxresevaJpaController(
				emf);

		List<Pasajerosxreseva> pasajerosreservaList = new ArrayList<Pasajerosxreseva>();
		try {

			// Obtengo todos los eventos de la tabla
			pasajerosreservaList = pasajeroReservaJPAcontroller.findPasajerosxresevaEntities(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				emf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return pasajerosreservaList;

	}

	public void delete(Pasajerosxreseva pasajeros) {
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		PasajerosxresevaJpaController pasajeroReservaJPAcontroller = new PasajerosxresevaJpaController(
				emf);
		 
		
			try {
				pasajeroReservaJPAcontroller.destroy(pasajeros.getIdPasajeroReserva());
			} catch (NonexistentEntityException e) {
				e.printStackTrace();
			}
		

	}

	public void insert(Pasajerosxreseva pasajeros) throws PreexistingEntityException, Exception {
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		PasajerosxresevaJpaController pasajeroReservaJPAcontroller = new PasajerosxresevaJpaController(
				emf);

		pasajeroReservaJPAcontroller.create(pasajeros);
 
	}

	public void update(Pasajerosxreseva pasajeros) { 

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		PasajerosxresevaJpaController pasajeroReservaJPAcontroller = new PasajerosxresevaJpaController(
				emf);

		 
			try {
				pasajeroReservaJPAcontroller.edit(pasajeros);
		} catch (IllegalOrphanException e) {
				e.printStackTrace();
			} catch (NonexistentEntityException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		

	}

}


