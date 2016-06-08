package com.develop.binfactory.controlaccesorestom.clases;

import java.util.ArrayList;

import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import com.develop.binfactory.controlaccesorestom.controladores.*;

import android.database.Cursor;
import android.content.Context;

public class Cliente_producto_compuestoJSON {
    public int f0;//ID
    public int f1;//producto_compuesto_ID
    public int f2;//cliente_proveedor_ID
    public int f3;//precio
    public String f4;//nombre_producto
    public String f98;//accion
    public int f99;//sincronizacion_ID


    //CONSTRUCTOR
    public Cliente_producto_compuestoJSON(Cursor cursor) {
        try {
            //cursor.getString(11)
            if (cursor.getColumnIndex("_id") > -1)
                f0 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("_id")));
            else
                f0 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("ID")));

            f1 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("producto_compuesto_ID")));
            f2 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("cliente_proveedor_ID")));
            f3 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("precio")));
            f4 = cursor.getString(cursor.getColumnIndex("nombre_producto"));
            f98 = cursor.getString(cursor.getColumnIndex("accion"));
            f99 = Integer.valueOf(cursor.getString(cursor.getColumnIndex("sincronizacion_ID")));
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Cliente_producto_compuesto.Constructor");
        }
    }

    public Cliente_producto_compuestoJSON() {
    }

    public int getID() {
        return f0;
    }

    public int getProducto_compuesto_ID() {
        return f1;
    }

    public int getCliente_proveedor_ID() {
        return f2;
    }

    public int getPrecio() {
        return f3;
    }

    public String getNombre_producto() {
        if (f4 != null)
            return f4;
        else
            return "";
    }

    public void setID(int ID) {
        this.f0 = ID;
    }

    public void setProducto_compuesto_ID(int producto_compuesto_ID) {
        this.f1 = producto_compuesto_ID;
    }

    public void setCliente_proveedor_ID(int cliente_proveedor_ID) {
        this.f2 = cliente_proveedor_ID;
    }
    public int getSincronizacion_ID(){
        return f99;
    }
    public void setPrecio(int precio) {
        this.f3 = precio;
    }

    public void setNombre_producto(String nombre_producto) {
        this.f4 = nombre_producto;
    }

    public void actualizar(Context context) {
        try {
            CtrlCliente_producto_compuesto.actualizarJSON(this, context);
        } catch (Exception ex) {
            Utils.escribeLog(ex, "Cliente_producto_compuesto.actualizarJSON");
        }
    }

    public int ingresar(Context context) {
        return CtrlCliente_producto_compuesto.ingresarJSON(this, context);
    }

}//fin clase lógica

	
	
