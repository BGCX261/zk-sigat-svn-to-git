package zk_data;
/**
 * 
 * Clase que se utiliza en los colores de los bordes
 * 
 * @author Sebastian
 *
 */
public final class Colors {
	public static String[] _colorsEstado = {"purple","blue"};
	public static String[] _typeEstado = {"Reservado", "Ocupado"};
	
	
	
	//Devuelve el color segun es estado de la reserva
	public static String getColorEstado(String estado)
	{
		
		for(int i=0; i<_typeEstado.length; i++) {
			if(estado.equals(_typeEstado[i])) {
				return _colorsEstado[i];
			}
		}
		
		return "";
	}
		
}
