package zk_controllers;

import java.text.DecimalFormat;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Chart;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import controller.ClientesJpaController;
import controller.DepartamentosJpaController;
import controller.PasajerosJpaController;

import entity.ClienteEntity;
import entity.DepartamentoEntity;
import entity.PasajeroEntity;
import zk_data.Colors;
import zk_models.MyCalendarModel;
import zk_models.ReservasItem;

public class ReservaCreationController extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Window createMyEntry, cliReservaWin, cliReservaWin1;
	Combobox deptoType;
	Textbox nameText;
	Textbox contactoText;
	Textbox direText;
	Textbox telefText;
	Textbox tarifaText;
	Textbox adelantoText;
	Textbox pasajerosText;
	Label id, nombre, dni, idPas, nomPas, dniPas, telPas;
	Listitem datos;
	Listbox datosPas;
	Bandbox bd;
	int idCliente = 0;
	int idDepto = 0;

	private String colorRetorno;

	private int[] pasajeID = new int[30];
	private static int indice = 0;
	ReservasItem nis = new ReservasItem();
	// Define los estados de ocupacion(Reservado, Ocupado)
	private String estadoOcupacion = "Reservado";

	public void prepareWindow(int left, int top) {
		createMyEntry.setLeft(left + "px");
		createMyEntry.setTop(top + "px");

		nameText.setText("");
		contactoText.setText("");
		direText.setText("");
		telefText.setText("");
		tarifaText.setText("");
		adelantoText.setText("");
		idCliente = 0;
		indice = 0;
		nameText.setReadonly(false);
		telefText.setReadonly(false);
		direText.setReadonly(false);
		deptoType.setValue("");

	}

	public void onClick$btnAddReserva() throws InterruptedException {
		CalendarsEvent evt = ((CalendarsEvent) createMyEntry
				.getAttribute("calevent"));

		Window win = (Window) createMyEntry.getParent();
		org.zkoss.calendar.Calendars cals = (org.zkoss.calendar.Calendars) win
				.getVariable("cal", false);
		Chart piechart = (Chart) win.getVariable("piechart", false);
		/********************** CLientes *******************************/
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ClientesJpaController clienteResController = new ClientesJpaController(
				emf);
		ClienteEntity cliente = new ClienteEntity();
		/***************************************************/
		// Carga datos de los id de pasajeros
		int[] IDpasejero = new int[indice];
		for (int i = 0; i < indice; i++) {
			IDpasejero[i] = pasajeID[i];
			// Remueve los datos para la nueva ventana
			datosPas.removeItemAt(1);
		}

		// Define el color segun el estado de la reserva
		String colorEstado = Colors.getColorEstado(estadoOcupacion);
		ReservasItem ni = new ReservasItem();

		if (MyCalendarModel.dao.superposicion(evt.getBeginDate(),
				evt.getEndDate(), deptoType.getSelectedItem().getLabel())) {
			try {
				Messagebox.show(
						"Superposicion de Departamento en rango de fecha.",
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e) {
				throw UiException.Aide.wrap(e);
			}
			return;
		}

		// Cargo datos en la tabla de clientes como carga rapida
		if (idCliente == 0 && !nameText.getValue().equals("")) {

			cliente.setRazonSocial(nameText.getText());
			cliente.setTelefono(telefText.getText());
			cliente.setDireccion(direText.getText());
			clienteResController.create(cliente);
			// Define id que se va a cargar en tabla reservas
			idCliente = cliente.getIdclientes();
		}

		/********************************************/

		ni.setBeginDate(evt.getBeginDate());
		ni.setEndDate(evt.getEndDate());
		ni.setNombreContacto(contactoText.getValue());
		ni.setDepto(deptoType.getSelectedItem().getLabel());
		ni.setTarifa(Float.parseFloat(tarifaText.getValue()));
		ni.setAdelanto(Float.parseFloat(adelantoText.getValue()));
		ni.setIDCliente(idCliente);
		// Define el borde a mostrar en la reserva
		ni.setHeaderColor(colorEstado);
		ni.setContentColor(colorRetorno);
		ni.setContent("Anticipo: " + ni.getAdelanto() + ", "
				+ ni.getNombreContacto() + ", " + nameText.getText() + " - "
				+ ni.getDepto());
		ni.setLocked(false);
		ni.setIdPasajeros(IDpasejero);

		MyCalendarModel.dao.insertNewsItem(ni);
		MyCalendarModel dcm = new MyCalendarModel();

		cals.setModel(dcm.getSimpleCalendarModel());
		piechart.setModel(dcm.getSimplePieModel());

		evt.clearGhost();
		createMyEntry.setVisible(false);
	}

	public void onClick$btnCancel() {
		CalendarsEvent evt = ((CalendarsEvent) createMyEntry
				.getAttribute("calevent"));
		createMyEntry.setVisible(false);
		evt.clearGhost();
	}

	// Habilita el cambio de cliente
	public void onClick$btnCambioClienteReserva() {
		idCliente = 0;
		nameText.setReadonly(false);
		telefText.setReadonly(false);
		direText.setReadonly(false);
		nameText.setText("");
		telefText.setText("");
		direText.setText("");
	}

	// Carga los datos en ventana con datos de lista de clientes
	public void getDatosCliente(String clienteid) {
		// Recibe ID cliente reserva
		idCliente = Integer.valueOf(clienteid);

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		ClientesJpaController clienteResController = new ClientesJpaController(
				emf);
		ClienteEntity client = clienteResController.findClientes(idCliente);
		// Setea ventana con datos del cliente seleccionado en bandvox
		nameText.setText(client.getRazonSocial());
		direText.setText(client.getDireccion());
		telefText.setText(client.getTelefono());

	}

	public void setDatosPasajeros(String pasajeroID) {
		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		PasajerosJpaController pasajerosJPAcontroller = new PasajerosJpaController(
				emf);
		PasajeroEntity pasajero = pasajerosJPAcontroller.findPasajeros(Integer
				.valueOf(pasajeroID));
		//Carga datos en lista de venta
		datosPas.appendItemApi(pasajero.getApynom(), "");
		pasajeID[indice++] = Integer.valueOf(pasajeroID);

	}

	// Carga los datos en ventana con datos de lista de Departamentos
	public void getDatosDepto(Integer id) {
		final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
		CalendarsEvent evt = ((CalendarsEvent) createMyEntry
				.getAttribute("calevent"));
		idDepto = id;

		// creo el emf
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("calendarPU");

		// instancio el manejador de la Entity
		DepartamentosJpaController deptoController = new DepartamentosJpaController(
				emf);
		DepartamentoEntity depto = deptoController.findDepartamentos(idDepto);

		String color = depto.getColor();
		float tarifa= depto.getTarifa()*((evt.getEndDate().getTime()-evt.getBeginDate().getTime())/MILLSECS_PER_DAY);
		DecimalFormat formateador = new DecimalFormat("########.##");
		tarifaText.setText(String.valueOf(formateador.format(tarifa)));
		System.out.println("Valor que devuelve el comoitem: " + idDepto);
		System.out.println("El color del departamento: " + color);

		if (color.equals("Rojo")) {
			colorRetorno = "red";
		} else if (color.equals("Azul")) {
			colorRetorno = "blue";
		} else if (color.equals("Verde")) {
			colorRetorno = "green";

		} else if (color.equals("Khaki")) {
			colorRetorno = "khaki";
		} else if (color.equals("Violeta")) {
			colorRetorno = "purple";
		}

	}

}
