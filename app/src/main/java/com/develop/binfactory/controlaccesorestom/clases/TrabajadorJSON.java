package com.develop.binfactory.controlaccesorestom.clases;

import java.util.ArrayList;

import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import com.develop.binfactory.controlaccesorestom.controladores.*;

import android.database.Cursor;
import android.content.Context;

public class TrabajadorJSON {
    public int f0;//ID
    public String f1;//nombre
    public String f2;//rut
    public int f3;//cliente_proveedor_ID
    public int f4;//sincronizacion_ID

    //CONSTRUCTOR
    public TrabajadorJSON(Cursor cursor) {
        try {
            //cursor.getString(11)
            if (cursor.getColumnIndex("_id") > -1)
                f0 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("_id")));
            else
                f0 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("ID")));

            f1 = cursor.getString(cursor.getColumnIndex("nombre"));
            f2 = cursor.getString(cursor.getColumnIndex("rut"));
            f3 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("cliente_proveedor_ID")));
            f4 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("sincronizacion_ID")));
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Trabajador.Constructor");
        }
    }

    public TrabajadorJSON() {
    }

    public int getID() {
        return f0;
    }

    public String getNombre() {
        if (f1 != null)
            return f1;
        else
            return "";
    }

    public String getRut() {
        if (f2 != null)
            return f2;
        else
            return "";
    }

    public int getSincronizacion_ID()
    {
        return f4;
    }

    public int getCliente_proveedor_ID() {
        return f3;
    }

    public void setID(int ID) {
        this.f0 = ID;
    }

    public void setSincronizacion_ID(int sincronizacion_ID) {
        this.f4 = sincronizacion_ID;
    }

    public void setNombre(String nombre) {
        this.f1 = nombre;
    }

    public void setRut(String rut) {
        this.f2 = rut;
    }

    public void setCliente_proveedor_ID(int cliente_proveedor_ID) {
        this.f3 = cliente_proveedor_ID;
    }

    public void actualizar(Context context) {
        try {
            CtrlTrabajador.actualizarJSON(this, context);
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Trabajador.actualizarJSON");
        }
    }

    public int ingresar(Context context) {
        return CtrlTrabajador.ingresarJSON(this, context);
    }

}//fin clase lógica

	
	
