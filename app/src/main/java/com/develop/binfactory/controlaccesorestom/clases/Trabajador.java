package com.develop.binfactory.controlaccesorestom.clases;

import java.util.ArrayList;

import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import com.develop.binfactory.controlaccesorestom.controladores.*;

import android.database.Cursor;
import android.content.Context;

public class Trabajador {
    public int fID;
    public String fnombre;
    public String frut;
    public int fcliente_proveedor_ID;
    public int fsincronizacion_ID;

    //CONSTRUCTOR
    public Trabajador(Cursor cursor) {
        try {
            //cursor.getString(11)
            if (cursor.getColumnIndex("_id") > -1)
                fID = Integer.valueOf(cursor.getString(cursor.getColumnIndex("_id")));
            else
                fID = Integer.valueOf(cursor.getString(cursor.getColumnIndex("ID")));

            fnombre = cursor.getString(cursor.getColumnIndex("nombre"));
            frut = cursor.getString(cursor.getColumnIndex("rut"));
            fcliente_proveedor_ID = Integer.valueOf(cursor.getString(cursor.getColumnIndex("cliente_proveedor_ID")));

            if(cursor.getColumnIndex("sincronizacion_ID") > -1){
                fsincronizacion_ID = Integer.valueOf(cursor.getString(cursor.getColumnIndex("sincronizacion_ID")));
            }

        } catch (Exception ex) {
            Utils.escribeLog(ex, "Trabajador.Constructor");
        }
    }

    public Trabajador() {
    }

    public int getID() {
        return fID;
    }

    public String getNombre() {
        if (fnombre != null)
            return fnombre;
        else
            return "";
    }

    public String getRut() {
        if (frut != null)
            return frut;
        else
            return "";
    }

    public int getCliente_proveedor_ID() {
        return fcliente_proveedor_ID;
    }

    public void setID(int ID) {
        this.fID = ID;
    }

    public void setNombre(String nombre) {
        this.fnombre = nombre;
    }

    public void setRut(String rut) {
        this.frut = rut;
    }

    public void setCliente_proveedor_ID(int cliente_proveedor_ID) {
        this.fcliente_proveedor_ID = cliente_proveedor_ID;
    }


    public void actualizar(Context context) {
        try {
            CtrlTrabajador.actualizar(this, context);
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Trabajador.actualizar");
        }
    }

    public int ingresar(Context context) {
        return CtrlTrabajador.ingresar(this, context);
    }

    public void eliminar(Context context) {
        try {
            CtrlTrabajador.eliminar(this.fID, context);
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Trabajador.eliminar");
        }
    }

	
	
	
	
	/*public Cliente_proveedor getCliente_proveedor(Context context)
	{
		return CtrlCliente_proveedor.getCliente_proveedor(this.fcliente_proveedor_ID,context);
	}*/

}//fin clase lógica
	
	
	
