package zk_dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import zk_models.ReservasItem;
import controller.PasajerosJpaController;
import controller.ReservasJpaController;
import controller.exceptions.PreexistingEntityException;
import entity.PasajeroEntity;
import entity.Pasajerosxreseva;
import entity.ReservaEntity;
 
public class ReservaDAO {
	
	public ReservaDAO() {
	}

	// METODO SELECT ALL IMPLEMENTADO EN JPA
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List selectAll() {

		// Lista que utilizo para contener los ReservasItem
		List allObjects = new ArrayList();

		// creo el entity manager factory
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ReservasJpaController tableNController = new ReservasJpaController(emf);

		try {

			// Obtengo todos los eventos de la tabla
			List<ReservaEntity> tblNList = tableNController.findReservasEntities();

			// Estos son los Items que voy a mostrar por pantalla que luego meto en allObject
			ReservasItem ni;

			// Itero sobre la lista de ReservaEntity tblNList
			for (Iterator iterator = tblNList.iterator(); iterator.hasNext();) {

				ReservaEntity tblnewsiteration = (ReservaEntity) iterator.next();
				
				ni = new ReservasItem();
				Date dt = new Date();

				ni.setNews_item(tblnewsiteration.getIdReserva());
				dt.setTime(tblnewsiteration.getFechaInicio().longValue());
				ni.setBeginDate((Date) dt.clone());
				dt.setTime(tblnewsiteration.getFechaFin().longValue());
				ni.setEndDate((Date) dt.clone());

				ni.setTitle(tblnewsiteration.getTitulo());
				ni.setContent(tblnewsiteration.getContenido());
				ni.setHeaderColor(tblnewsiteration.getColorBorde());
				ni.setContentColor(tblnewsiteration.getColorBarra());
				ni.setNombreContacto(tblnewsiteration.getNombreContacto());
				ni.setDepto(tblnewsiteration.getDepto());
				ni.setTarifa(tblnewsiteration.getTarifa());
				ni.setAdelanto(tblnewsiteration.getAdelanto());
				ni.setIDCliente(tblnewsiteration.getClientesIdclientes());
				
				

				allObjects.add(ni);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				emf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return allObjects;
	}

	// Metodo updateNewsItem Implementado en JPA

	public boolean updateNewsItem(ReservasItem ni) {
		boolean result = false;

		// creo el entity manager factory
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ReservasJpaController tableNController = new ReservasJpaController(emf);

		try {
			ReservaEntity reservaEntity = new ReservaEntity();

			reservaEntity = tableNController.findReservas(ni.getNews_item());

			reservaEntity.setContenido(ni.getContent());
			reservaEntity.setColorBarra(ni.getContentColor());
			reservaEntity.setFechaInicio(BigInteger.valueOf(ni.getBeginDate().getTime()));
			reservaEntity.setFechaFin(BigInteger.valueOf(ni.getEndDate().getTime()));
			reservaEntity.setColorBorde(ni.getHeaderColor());
			reservaEntity.setTitulo(ni.getTitle());
			
			reservaEntity.setNombreContacto(ni.getNombreContacto());
			reservaEntity.setDepto(ni.getDepto());
			reservaEntity.setTarifa(ni.getTarifa());
			reservaEntity.setAdelanto(ni.getAdelanto());
			reservaEntity.setClientesIdclientes(ni.getIDCliente());
			
			// ejecuto el servicio del controller para modificar el evento
			tableNController.edit(reservaEntity);
			
			// aviso que la modificacion fue exitosa
			result = true;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				emf.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return result;
	}

	// metodo insertNewItem implementado en JPA
	public boolean insertNewsItem(ReservasItem ni) {
		
		// resultado de la transaccion (se realizo o no)
		boolean result = false;
		// creo el entity manager factory
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		try {
			
			//***********************************************************
			PasajerosJpaController pasajeroJpa = new PasajerosJpaController(emf);
			PasajeroEntity pasajero;
			//***********************************************************
			// instancio el manejador de la Entity
			ReservasJpaController tableNController = new ReservasJpaController(	emf);

			// Creo un nuevo objeto del Entity
			ReservaEntity reservaEntity = new ReservaEntity();

			// Casteo el ReservasItem recibido al Entity
			reservaEntity.setTitulo(ni.getTitle());
			reservaEntity.setContenido(ni.getContent());
			reservaEntity.setColorBarra(ni.getContentColor());
			Long begin = new Long(ni.getBeginDate().getTime());
			reservaEntity.setFechaInicio(BigInteger.valueOf(begin));
			Long end = new Long(ni.getEndDate().getTime());
			reservaEntity.setFechaFin(BigInteger.valueOf(end));
			reservaEntity.setColorBorde(ni.getHeaderColor());
			
			reservaEntity.setNombreContacto(ni.getNombreContacto());
			reservaEntity.setDepto(ni.getDepto());
			reservaEntity.setTarifa(ni.getTarifa());
			reservaEntity.setAdelanto(ni.getAdelanto());
			reservaEntity.setClientesIdclientes(ni.getIDCliente());
			

			// inserto en la base de datos
			try {
				tableNController.create(reservaEntity);
				result = true;
				//Inserta datos en reservaPasajeros
				int[] listaPasajero= ni.getIdPasajeros();
				for(int i=0; i<listaPasajero.length; i++)
				{
					//Llama pasajero con ID
					pasajero=pasajeroJpa.findPasajeros(listaPasajero[i]);
					//metodo para insertar en tabla
					crearPasajeroReserva(pasajero, reservaEntity);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {

			ex.printStackTrace();
			
		} finally {
			try {
				// cierro el emf
				emf.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		return result;
	}

	// Metodo Delete Implementado en JPA
	public boolean deleteNewsItem(ReservasItem ni) {

		boolean result = false;

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		ReservasJpaController tblcontroller = new ReservasJpaController(emf);
		try {

			tblcontroller.destroy(ni.getNews_item());
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				emf.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return result;

	}

	@SuppressWarnings("rawtypes")
	public boolean superposicion(Date beginDate, Date endDate, String depto) {
		java.util.List ListaRes = this.selectAll();

		for (Iterator iterator = ListaRes.iterator(); iterator.hasNext();) {

			ReservasItem res = (ReservasItem) iterator.next();

			if ((beginDate.compareTo(res.getEndDate()) < 0)
					&& ((endDate.compareTo(res.getBeginDate()) > 0))
					&& (depto.equals(res
							.getDepto()))) {
				return true;
			}
		}
		return false;
	}
	//Carga datos en tabla reservapasajeros
	public void crearPasajeroReserva(PasajeroEntity pasajero, ReservaEntity reserva)
	{
		
		Pasajerosxreseva parsRes= new Pasajerosxreseva();
		parsRes.setReservasIdReserva(reserva);			
		parsRes.setPasajerosIdPasajeros(pasajero);
		try {
			PasajeroxReservaDAO resPasJPA= new PasajeroxReservaDAO();
			resPasJPA.insert(parsRes);
		} catch (PreexistingEntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
		
		}
		
	}

}
