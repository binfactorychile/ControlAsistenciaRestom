package com.develop.binfactory.controlaccesorestom.clases;

import java.util.ArrayList;

import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import com.develop.binfactory.controlaccesorestom.controladores.*;

import android.database.Cursor;
import android.content.Context;

public class Sincronizar_tabletJSON {
    public int f0;//ID
    public int f1;//registro_ID
    public int f2;//tablet_ID
    public String f3;//nombre_tabla
    public String f4;//accion

    //CONSTRUCTOR
    public Sincronizar_tabletJSON(Cursor cursor) {
        try {
            //cursor.getString(11)
            if (cursor.getColumnIndex("_id") > -1)
                f0 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("_id")));
            else
                f0 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("ID")));

            f1 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("registro_ID")));
            f2 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("tablet_ID")));
            f3 = cursor.getString(cursor.getColumnIndex("nombre_tabla"));
            f4 = cursor.getString(cursor.getColumnIndex("accion"));
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Sincronizar_tablet.Constructor");
        }
    }

    public Sincronizar_tabletJSON() {
    }

    public int getID() {
        return f0;
    }

    public int getRegistro_ID() {
        return f1;
    }

    public int getTablet_ID() {
        return f2;
    }

    public String getNombre_tabla() {
        if (f3 != null)
            return f3;
        else
            return "";
    }

    public String getAccion() {
        if (f4 != null)
            return f4;
        else
            return "";
    }

    public void setID(int ID) {
        this.f0 = ID;
    }

    public void setRegistro_ID(int registro_ID) {
        this.f1 = registro_ID;
    }

    public void setTablet_ID(int tablet_ID) {
        this.f2 = tablet_ID;
    }

    public void setNombre_tabla(String nombre_tabla) {
        this.f3 = nombre_tabla;
    }

    public void setAccion(String accion) {
        this.f4 = accion;
    }

    /*public void actualizar(Context context) {
        try {
            CtrlSincronizar_tablet.actualizarJSON(this, context);
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Sincronizar_tablet.actualizarJSON");
        }
    }

    public int ingresar(Context context) {
        return CtrlSincronizar_tablet.ingresarJSON(this, context);
    }*/

}//fin clase lógica

	
	
