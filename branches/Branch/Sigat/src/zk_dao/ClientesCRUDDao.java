package zk_dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import controller.ClientesJpaController;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entity.ClienteEntity;


public class ClientesCRUDDao {

	public ClientesCRUDDao() {
	}

	public List<ClienteEntity> findAll() {

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ClientesJpaController pasajerosJPAcontroller = new ClientesJpaController(
				emf);

		List<ClienteEntity> clientesList = new ArrayList<ClienteEntity>();
		try {

			// Obtengo todos los eventos de la tabla
			clientesList = pasajerosJPAcontroller.findClientesEntities();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				emf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return clientesList;

	}

	public void delete(ClienteEntity cliente) {
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ClientesJpaController clientesJPAcontroller = new ClientesJpaController(
				emf);
		
		
			try {
				clientesJPAcontroller.destroy(cliente.getIdclientes());
			} catch (NonexistentEntityException e) {
				
				e.printStackTrace();
			}
		

	}

	public void insert(ClienteEntity cliente) {
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ClientesJpaController pasajerosJPAcontroller = new ClientesJpaController(
				emf);

		pasajerosJPAcontroller.create(cliente);

	}

	public void update(ClienteEntity cliente) {

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ClientesJpaController clientesJPAcontroller = new ClientesJpaController(
				emf);

		
			try {
				clientesJPAcontroller.edit(cliente);
		} catch (IllegalOrphanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NonexistentEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}

}
