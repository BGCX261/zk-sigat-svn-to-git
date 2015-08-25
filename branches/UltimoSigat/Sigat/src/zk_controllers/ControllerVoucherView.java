/**
 * 
 */
package zk_controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.api.Window;

import com.sun.org.apache.bcel.internal.classfile.Field;
import com.sun.xml.ws.rx.rm.runtime.sequence.persistent.PersistenceException;

import controller.ReservasJpaController;

/**
 * @author Sebastian
 * 
 */
public class ControllerVoucherView extends GenericForwardComposer {

	private Button btnSalir;
	private Button btnImprimir;
	private Combobox cmbTipAct;
	private Connection con;

	/**
	*
	*
	*/
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// TODO Auto-generated method stub

	}

	public void onClick$btnSalir() {
		// TODO: please check if you have use "self" or zscript functions here.
		
	}

	public void onClick$btnImprimir() throws SQLException {
		generarVoucher();
	}
	// creo el entity manager factory
	EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("calendarPU");
	
	public void generarVoucher() throws SQLException {
		
		try {
			// creo el EntityManager  
			EntityManager em = emf.createEntityManager();
			/*
			 * Inicio una transaccion y luego obtengo la coneccion 
			 */
			em.getTransaction().begin();
			java.sql.Connection con = em.unwrap(java.sql.Connection.class);
			
			System.out.print("Conecion con base de datos" + con);
			//Esta sentencia es para pasarle parametros al reporte, todavia no lo agrege
			//param.put(key, value);
			
			JasperReport jasperReport = JasperCompileManager.compileReport("C://Reportes/voucher.jrxml");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, con);
			JasperExportManager.exportReportToPdfFile(jasperPrint,"C://Documents and Settings/Sebastian/Escritorio/voucher.pdf");
			//JasperViewer.viewReport(jasperPrint);
			
			// Esta parte del codigo se encarga de Abrir el Acrobat Reader con PDF especificado
			Runtime correrPrograma = Runtime.getRuntime();
			
			try {
				correrPrograma.exec("C:\\Archivos de Programa\\Adobe\\Reader 9.0\\Reader\\AcroRd32.exe" + " C:\\Documents and Settings\\Sebastian\\Escritorio\\voucher.pdf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			 * Cierro la transaccion de manera que todo lo anterior se realice de manera atomica
			 */
			em.getTransaction().commit();
			con.close();
		} catch (JRException e) {
			e.printStackTrace();
		}catch (PersistenceException e){
			e.printStackTrace();
		}
	}

}
