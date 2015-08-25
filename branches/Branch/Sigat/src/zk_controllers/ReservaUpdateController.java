package zk_controllers;

import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Chart;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import zk_data.Colors;
import zk_models.MyCalendarModel;
import zk_models.ReservasItem;

public class ReservaUpdateController extends GenericForwardComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Window updateMyEntry;
	Combobox deptoType;
	Textbox nameText;
	Textbox contactoText;
	Textbox direText;
	Textbox telefText;
	Textbox tarifaText;
	Textbox adelantoText;
	//Textbox tbText;
	//Define los estados de ocupacion(Reservado, Ocupado)
		private String estadoOcupacion= "Reservado";
		//Guarda el departamento que llama al update
		private String controlDepto="";
	
	
	public void prepareWindow(int left, int top, ReservasItem ni) {
		updateMyEntry.setLeft(left + "px");
		updateMyEntry.setTop(top + "px");
		controlDepto=ni.getDepto();
		//Busca la posicion del departamento en la que se ubica en al combobox
		int deptoPosition = Colors.getDeptoPosition(controlDepto);
		
		if(deptoPosition == -1)
			deptoPosition = 0;
		
		nameText.setValue(ni.getNombre());
		contactoText.setValue(ni.getNombreContacto());
		direText.setValue(ni.getDireccion());
		telefText.setValue(ni.getTelefono());
		deptoType.setSelectedIndex(deptoPosition);
		tarifaText.setValue(String.valueOf(ni.getTarifa()));
		adelantoText.setValue(String.valueOf(ni.getAdelanto()));
		
}
	
	public void onClick$btnUpdateNews() {	
		CalendarsEvent evt = ((CalendarsEvent)updateMyEntry.getAttribute("calevent"));
		
		Window win = (Window)updateMyEntry.getParent();
		org.zkoss.calendar.Calendars cals = (org.zkoss.calendar.Calendars)win.getVariable("cal", false);
		Chart piechart = (Chart)win.getVariable("piechart", false);
		
		ReservasItem ni = ((ReservasItem)updateMyEntry.getAttribute("ni"));
		
		int selectedColor = deptoType.getSelectedIndex();
		
		if(selectedColor == -1)
			selectedColor = 0;
		//Controla si se modifico en el combobox, en caso caso de modificacion controla que no haya superposicion de dtos.
		if(!controlDepto.equals(deptoType.getSelectedItem().getLabel()))
		{
			if (MyCalendarModel.dao.superposicion(ni.getBeginDate(),
					ni.getEndDate(), deptoType.getSelectedItem().getLabel())) {
				try {
					Messagebox.show(
							"Superposicion de Departamento en rango de fecha.",
							"Error", Messagebox.OK, Messagebox.ERROR);
				} catch (InterruptedException e) {
					throw UiException.Aide.wrap(e);
				}
				return;
			}
		}
		

		ni.setContentColor(Colors._colors[selectedColor]);
		//Define color borde segun estado
		ni.setHeaderColor(Colors.getColorEstado(estadoOcupacion));
			
		ni.setBeginDate(ni.getBeginDate());
		ni.setEndDate(ni.getEndDate());
		ni.setNombre(nameText.getValue());
		ni.setNombreContacto(contactoText.getValue());
		ni.setDireccion(direText.getValue());
		ni.setTelefono(telefText.getValue());
		ni.setDepto(deptoType.getSelectedItem().getLabel());
		ni.setTarifa(Float.parseFloat(tarifaText.getValue()));
		ni.setAdelanto(Float.parseFloat(adelantoText.getValue()));
		ni.setContent("Anticipo: "+ni.getAdelanto()+", "+ni.getNombreContacto() +", "+ ni.getNombre() +" - "+ ni.getDepto());
		ni.setLocked(false);
		
		
		
		
		MyCalendarModel.dao.updateNewsItem(ni);
		MyCalendarModel dcm = new MyCalendarModel();
		
		cals.setModel(dcm.getSimpleCalendarModel());
		piechart.setModel(dcm.getSimplePieModel());
		
		evt.clearGhost();
		
		updateMyEntry.setVisible(false);
	}
	 
	public void onClick$btnDeleteNews() {
		ReservasItem ni = ((ReservasItem)updateMyEntry.getAttribute("ni"));
		
		Window win = (Window)updateMyEntry.getParent();
		org.zkoss.calendar.Calendars cals = (org.zkoss.calendar.Calendars)win.getVariable("cal", false);
		Chart piechart = (Chart)win.getVariable("piechart", false);
		
		MyCalendarModel.dao.deleteNewsItem(ni);
		MyCalendarModel dcm = new MyCalendarModel();
		
		cals.setModel(dcm.getSimpleCalendarModel());
		piechart.setModel(dcm.getSimplePieModel());
		
		updateMyEntry.setVisible(false);
	}
	
	public void onClick$btnCancel() {
		CalendarsEvent evt = ((org.zkoss.calendar.event.CalendarsEvent)updateMyEntry.getAttribute("calevent"));
		updateMyEntry.setVisible(false);
		evt.clearGhost();
	}
}
