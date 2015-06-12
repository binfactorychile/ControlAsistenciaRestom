package com.develop.binfactory.controlaccesorestom.fachadas;
import com.develop.binfactory.controlaccesorestom.clases.*;
import com.develop.binfactory.controlaccesorestom.logica.soporte.ManagerProviderBD;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import android.content.Context;
import android.database.Cursor;


public class FachadaAsistencia_trabajador {

public static Cursor getListado(String query, ManagerProviderBD bd)
	{
		try
	{
		
		Cursor cursor=bd.ejecutaConRetorno(query);
		return cursor;
		
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"FachadaAsistencia_trabajador.getListado");
		return null;
	}
	}
	
	public static int ingresarJSON(Asistencia_trabajadorJSON objeto,ManagerProviderBD bd)
	{
		try
	{
		
		String query="INSERT INTO Asistencia_trabajador (" +
		"fecha" +
		",trabajador_ID" +
		",cliente_proveedor_ID" +
		") VALUES (" +
		"'" + objeto.getFecha() + "'" +
		",'" + objeto.getTrabajador_ID() + "'" +
		",'" + objeto.getCliente_proveedor_ID() + "'" +
		")";
		
		bd.ejecutaSinRetorno(query);
		Cursor cursor=bd.ejecutaConRetorno("SELECT MAX(ID) maximo FROM Asistencia_trabajador");
		cursor.moveToPosition(0);
		return Integer.valueOf(cursor.getString(0));
		
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"FachadaAsistencia_trabajador.ingresar");
		return 0;
	}
	}
	
	public static int ingresar(Asistencia_trabajador objeto,ManagerProviderBD bd)
	{
		try
	{
		
		String query="INSERT INTO Asistencia_trabajador (" +
		"fecha" +
		",trabajador_ID" +
		",cliente_proveedor_ID" +
		") VALUES (" +
		"'" + objeto.getFecha() + "'" +
		",'" + objeto.getTrabajador_ID() + "'" +
		",'" + objeto.getCliente_proveedor_ID() + "'" +
		")";
		
		bd.ejecutaSinRetorno(query);
		Cursor cursor=bd.ejecutaConRetorno("SELECT MAX(ID) maximo FROM Asistencia_trabajador");
		cursor.moveToPosition(0);
		return Integer.valueOf(cursor.getString(0));
		
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"FachadaAsistencia_trabajador.ingresar");
		return 0;
	}
	}
	public static void actualizar(Asistencia_trabajador objeto, ManagerProviderBD bd)
	{
		try
	{
		String query="UPDATE Asistencia_trabajador SET " +
		"fecha='" + objeto.getFecha() + "'" +
		",trabajador_ID='" + objeto.getTrabajador_ID() + "'" +
		",cliente_proveedor_ID='" + objeto.getCliente_proveedor_ID() + "'" +
		" WHERE ID=" + objeto.getID();
		
		bd.ejecutaSinRetorno(query);
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"FachadaAsistencia_trabajador.actualizar");
	}
	}
	
	public static void actualizarJSON(Asistencia_trabajadorJSON objeto, ManagerProviderBD bd)
	{
		try
	{
		String query="UPDATE Asistencia_trabajador SET " +
		"fecha='" + objeto.getFecha() + "'" +
		",trabajador_ID='" + objeto.getTrabajador_ID() + "'" +
		",cliente_proveedor_ID='" + objeto.getCliente_proveedor_ID() + "'" +
		" WHERE ID=" + objeto.getID();
		
		bd.ejecutaSinRetorno(query);
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"FachadaAsistencia_trabajador.actualizar");
	}
	}
	
	
	public static void eliminar(int ID, ManagerProviderBD bd)
	{
		try
	{
		String query="DELETE FROM Asistencia_trabajador WHERE ID=" + ID;
		bd.ejecutaSinRetorno(query);
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"FachadaAsistencia_trabajador.eliminar");
	}
	}
	
	}//Fin Clase
	
