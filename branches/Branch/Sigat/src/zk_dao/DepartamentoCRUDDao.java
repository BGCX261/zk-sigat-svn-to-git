package zk_dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import controller.DepartamentosJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entity.DepartamentoEntity;


public class DepartamentoCRUDDao {
	
	public DepartamentoCRUDDao() {
	}

	public List<DepartamentoEntity> findAll() {

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		DepartamentosJpaController deptoJPAcontroller = new DepartamentosJpaController(
				emf);

		List<DepartamentoEntity> deptoList = new ArrayList<DepartamentoEntity>();
		try {

			// Obtengo todos los eventos de la tabla
			deptoList = deptoJPAcontroller.findDepartamentosEntities();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				emf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return deptoList;

	}

	public void delete(DepartamentoEntity depto) throws IllegalOrphanException {
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		DepartamentosJpaController deptoJPAcontroller = new DepartamentosJpaController(
				emf);

		
		
		try {
			deptoJPAcontroller.destroy(depto.getIdDepartamento());
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insert(DepartamentoEntity depto) {
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		DepartamentosJpaController deptoJPAcontroller = new DepartamentosJpaController(
				emf);


		deptoJPAcontroller.create(depto);

	}

	public void update(DepartamentoEntity depto) {

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		DepartamentosJpaController deptoJPAcontroller = new DepartamentosJpaController(
				emf);

		try {
			deptoJPAcontroller.edit(depto);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
