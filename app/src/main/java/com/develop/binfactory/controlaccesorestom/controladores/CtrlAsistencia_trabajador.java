package com.develop.binfactory.controlaccesorestom.controladores;

import java.util.ArrayList;
import com.develop.binfactory.controlaccesorestom.clases.Asistencia_trabajador;
import com.develop.binfactory.controlaccesorestom.clases.Asistencia_trabajadorJSON;
import com.develop.binfactory.controlaccesorestom.fachadas.FachadaAsistencia_trabajador;
import com.develop.binfactory.controlaccesorestom.controladores.CtrlAsistencia_trabajador;
import com.develop.binfactory.controlaccesorestom.logica.soporte.ManagerProviderBD;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import android.content.Context;
import android.database.Cursor;


public class CtrlAsistencia_trabajador {
public static  ArrayList<Asistencia_trabajador> getListado(String query,Context context)
	{
		try
	{
		ManagerProviderBD bd=new ManagerProviderBD(context);
		bd.open();
		Cursor cursor=FachadaAsistencia_trabajador.getListado(query, bd);
		cursor.moveToFirst();
		ArrayList<Asistencia_trabajador> arregloAsistencia_trabajador=new ArrayList<Asistencia_trabajador>();
		Asistencia_trabajador asistencia_trabajador;
		while(cursor.isAfterLast()==false)
	{
		asistencia_trabajador=new Asistencia_trabajador(cursor);
		arregloAsistencia_trabajador.add(asistencia_trabajador);
		cursor.moveToNext();
	}
	bd.close();
	return arregloAsistencia_trabajador;
	
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"CtrlAsistencia_trabajador.getListado");
		return null;
	}
	}
	public static  ArrayList<Asistencia_trabajadorJSON> getListadoJSON(String query,Context context)
	{
		try
	{
		ManagerProviderBD bd=new ManagerProviderBD(context);
		bd.open();
		Cursor cursor=FachadaAsistencia_trabajador.getListado(query, bd);
		cursor.moveToFirst();
		ArrayList<Asistencia_trabajadorJSON> arregloAsistencia_trabajador=new ArrayList<Asistencia_trabajadorJSON>();
		Asistencia_trabajadorJSON asistencia_trabajador;
		while(cursor.isAfterLast()==false)
	{
		asistencia_trabajador=new Asistencia_trabajadorJSON(cursor);
		arregloAsistencia_trabajador.add(asistencia_trabajador);
		cursor.moveToNext();
	}
	bd.close();
	return arregloAsistencia_trabajador;
	
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"CtrlAsistencia_trabajador.getListadoJSON");
		return null;
	}
	}
	public static Asistencia_trabajador getAsistencia_trabajador(int ID,Context context)
	{
		try
	{
		String query="SELECT ID as _id" +
		",fecha" +
		",trabajador_ID" +
		",cliente_proveedor_ID" +
		" FROM Asistencia_trabajador WHERE ID=" + ID;
		ManagerProviderBD bd=new ManagerProviderBD(context);
		bd.open();
		Cursor cursor=FachadaAsistencia_trabajador.getListado(query, bd);
		cursor.moveToFirst();
		Asistencia_trabajador objeto=new Asistencia_trabajador(cursor);
		bd.close();
		return objeto;
		
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"CtrlAsistencia_trabajador.getAsistencia_trabajador");
		return null;
	}
	}
	public static int ingresar(Asistencia_trabajador objeto, Context context)
	{
		ManagerProviderBD bd=new ManagerProviderBD(context);
		bd.open();
		int ultima_ID=FachadaAsistencia_trabajador.ingresar(objeto, bd);
		bd.close();
		return ultima_ID;
		
	}
	public static int ingresarJSON(Asistencia_trabajadorJSON objeto, Context context)
	{
		ManagerProviderBD bd=new ManagerProviderBD(context);
		bd.open();
		int ultima_ID=FachadaAsistencia_trabajador.ingresarJSON(objeto, bd);
		bd.close();
		return ultima_ID;
		
	}
	public static void actualizar(Asistencia_trabajador objeto, Context context)
	{
		ManagerProviderBD bd=new ManagerProviderBD(context);
		bd.open();
		FachadaAsistencia_trabajador.actualizar(objeto, bd);
		bd.close();
	}
	
	public static void actualizarJSON(Asistencia_trabajadorJSON objeto, Context context)
	{
		ManagerProviderBD bd=new ManagerProviderBD(context);
		bd.open();
		FachadaAsistencia_trabajador.actualizarJSON(objeto, bd);
		bd.close();
	}
	
	public static void eliminar(int ID,Context context)
	{
		ManagerProviderBD bd=new ManagerProviderBD(context);
		bd.open();
		FachadaAsistencia_trabajador.eliminar(ID,bd);
		bd.close();
	}
	public static boolean existe(int ID, Context context) {
	try {
		String query = "SELECT ID as _id" + " FROM Asistencia_trabajador WHERE ID=" + ID;
		ManagerProviderBD bd = new ManagerProviderBD(context);
		bd.open();
		Cursor cursor = FachadaAsistencia_trabajador.getListado(query, bd);
		cursor.moveToFirst();
		boolean retorna = false;
	if (cursor.getCount() > 0) {
		if (!cursor.isNull(0))
		retorna = true;
		
	}
	cursor.close();
	bd.close();
	return retorna;
	
	} catch (Exception ex) {
	Utils.escribeLog(ex, "CtrlAsistencia_trabajador.existe");
	return false;
	}
	}
	}
	
//------------------------------------------------------------------------------
	//	FIN CONTROLADOR
//------------------------------------------------------------------------------
