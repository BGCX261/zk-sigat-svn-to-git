package zk_dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import controller.ComodidadesJpaController;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.Comodidades;

/*
 * Clase se encarga de manejar la Entidad Comodidades a traves de 
 * su jpaController, para la obtencion de los datos de la base
 * de Datos
 */

public class ComodidadesCrudDao {

	public ComodidadesCrudDao() {
	}

	public List<Comodidades> findAll() {

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ComodidadesJpaController comodidadesJPAcontroller = new ComodidadesJpaController(
				emf);

		List<Comodidades> comodidadesList = new ArrayList<Comodidades>();
		try {

			// Obtengo todos los eventos de la tabla
			comodidadesList = comodidadesJPAcontroller
					.findComodidadesEntities();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				emf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return comodidadesList;

	}

	public void delete(Comodidades comodidades) {
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ComodidadesJpaController comodidadesJPAcontroller = new ComodidadesJpaController(
				emf);

		try {
			comodidadesJPAcontroller.destroy(comodidades.getIdcomodidades());
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
		}

	}

	public void insert(Comodidades comodidades)
			throws PreexistingEntityException, Exception {
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ComodidadesJpaController comodidadesJPAcontroller = new ComodidadesJpaController(
				emf);

		comodidadesJPAcontroller.create(comodidades);

	}

	public void update(Comodidades comodidades) {

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ComodidadesJpaController comodidadesJPAcontroller = new ComodidadesJpaController(
				emf);

		try {
			comodidadesJPAcontroller.edit(comodidades);
		} catch (IllegalOrphanException e) {
			e.printStackTrace();
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
