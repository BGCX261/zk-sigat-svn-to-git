package zk_controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.calendar.impl.SimpleCalendarModel;
import org.zkoss.util.Locales;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Chart;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import zk_models.MyCalendarModel;
import zk_models.ReservasItem;

public class ReservaController extends GenericForwardComposer {
	
	Textbox irAFechaText;
	
	private static final long serialVersionUID = 1L;
	Window win;
	Calendars cal;
	Chart piechart;

	Window creationDialog = null;
	Window updateDialog = null;

	public SimpleCalendarModel getCalendarModel() {
		MyCalendarModel dcm = new MyCalendarModel();
		piechart.setModel(dcm.getSimplePieModel());
		return dcm.getSimpleCalendarModel();
	}

	public void onEventCreate$cal(ForwardEvent event) {
		CalendarsEvent evt = (CalendarsEvent) event.getOrigin();

		int left = evt.getX();
		int top = evt.getY();

		if (top + 245 > evt.getDesktopHeight())
			top = evt.getDesktopHeight() - 245;
		if (left + 410 > evt.getDesktopWidth())
			left = evt.getDesktopWidth() - 410;

		if (creationDialog == null) {
			creationDialog = (Window) Executions.createComponents(
					"createEntry.zul", win, null);
		}

		ReservaCreationController c = (ReservaCreationController) creationDialog
				.getVariable("createMyEntry$composer", false);
		c.prepareWindow(left, top);

		creationDialog.setAttribute("calevent", evt);
		creationDialog.setVisible(true);

		evt.stopClearGhost();
	}

	public void onEventEdit$cal(ForwardEvent event) {
		CalendarsEvent evt = (CalendarsEvent) event.getOrigin();

		int left = evt.getX();
		int top = evt.getY();

		if (top + 245 > evt.getDesktopHeight())
			top = evt.getDesktopHeight() - 245;
		if (left + 410 > evt.getDesktopWidth())
			left = evt.getDesktopWidth() - 410;

		ReservasItem ni = (ReservasItem) evt.getCalendarEvent();

		if (updateDialog == null) {
			updateDialog = (Window) Executions.createComponents(
					"updateEntry.zul", win, null);
		}

		ReservaUpdateController c = (ReservaUpdateController) updateDialog
				.getVariable("updateMyEntry$composer", false);
		c.prepareWindow(left, top, ni);

		updateDialog.setAttribute("calevent", evt);
		updateDialog.setAttribute("ni", ni);
		updateDialog.setVisible(true);

		evt.stopClearGhost();
	}

	public void onEventUpdate$cal(ForwardEvent event) {
		CalendarsEvent evt = (CalendarsEvent) event.getOrigin();
		ReservasItem ni = (ReservasItem) evt.getCalendarEvent();

		ni.setBeginDate(evt.getBeginDate());
		ni.setEndDate(evt.getEndDate());

		MyCalendarModel.dao.updateNewsItem(ni);
		MyCalendarModel dm = new MyCalendarModel();
		cal.setModel(dm.getSimpleCalendarModel());
	}

	
	
	private Label label;

	@Override
    public void doAfterCompose(Component comp) throws Exception {
            super.doAfterCompose(comp);
            updateDateLabel();
    }
	


	public void onToday(ForwardEvent event) {
		cal.setCurrentDate(Calendar.getInstance(cal.getDefaultTimeZone())
				.getTime());
		updateDateLabel();
		
	}


	public void updateDateLabel() {
		int dia = (24 * 60 * 60 * 1000);
		long endDate;
		Date b = cal.getBeginDate();
		Date e = cal.getEndDate();
		endDate = e.getTime();
		endDate = endDate - dia;
		e =  new Date(endDate);
		
		SimpleDateFormat sdfV = new SimpleDateFormat("dd/MMM/yyyy",
				Locales.getCurrent());
		sdfV.setTimeZone(cal.getDefaultTimeZone());
		label.setValue(sdfV.format(b) + " - " + sdfV.format(e));
		irAFechaText.setText("");
	}

	
	
	public void onClick$btnIrAFecha() {
					
	    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
	    String strFecha = irAFechaText.getText();
	    Date fecha = null;
	    try {

	        fecha = formatoDelTexto.parse(strFecha);
	        //System.out.println(fecha.toString());
	        cal.setCurrentDate(fecha);

	    } catch (Exception ex) {
	    	
			try {
				Messagebox.show(
						"El formato de entrada es incorrecto. debe ser dd/mm/aaaa",
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        ex.printStackTrace();
	    }
	    updateDateLabel();
	}


	
	public void onClick$btnPPage() {
		cal.previousPage();
	    updateDateLabel();
	}

	
	public void onClick$btnNPage() {
		cal.nextPage();
	    updateDateLabel();
	}
	
	
	
}
