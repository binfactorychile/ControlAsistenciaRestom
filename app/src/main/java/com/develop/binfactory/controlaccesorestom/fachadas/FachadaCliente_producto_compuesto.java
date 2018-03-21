package com.develop.binfactory.controlaccesorestom.fachadas;

import com.develop.binfactory.controlaccesorestom.clases.*;
import com.develop.binfactory.controlaccesorestom.logica.soporte.ManagerProviderBD;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;

import android.content.Context;
import android.database.Cursor;


public class FachadaCliente_producto_compuesto {

    public static Cursor getListado(String query, ManagerProviderBD bd) {
        try {

            Cursor cursor = bd.ejecutaConRetorno(query);
            return cursor;

        } catch (Exception ex) {
            Utils.escribeLog(ex, "FachadaCliente_producto_compuesto.getListado");
            return null;
        }
    }

    public static int ingresarJSON(Cliente_producto_compuestoJSON objeto, ManagerProviderBD bd) {
        try {

            String query = "INSERT INTO Cliente_producto_compuesto (" +
                    "producto_compuesto_ID" +
                    ",cliente_proveedor_ID" +
                    ",precio" +
                    ",nombre_producto" +
                    ",ID" +
                    ") VALUES (" +
                    "'" + objeto.getProducto_compuesto_ID() + "'" +
                    ",'" + objeto.getCliente_proveedor_ID() + "'" +
                    ",'" + objeto.getPrecio() + "'" +
                    ",'" + objeto.getNombre_producto() + "'" +
                    ",'" + objeto.getID() + "'" +
                    ")";

            bd.ejecutaSinRetorno(query);
            Cursor cursor = bd.ejecutaConRetorno("SELECT MAX(ID) maximo FROM Cliente_producto_compuesto");
            cursor.moveToPosition(0);
            return Integer.valueOf(cursor.getString(0));

        } catch (Exception ex) {
            Utils.escribeLog(ex, "FachadaCliente_producto_compuesto.ingresar");
            return 0;
        }
    }

    public static int ingresar(Cliente_producto_compuesto objeto, ManagerProviderBD bd) {
        try {

            String query = "INSERT INTO Cliente_producto_compuesto (" +
                    "producto_compuesto_ID" +
                    ",cliente_proveedor_ID" +
                    ",precio" +
                    ",nombre_producto" +
                    ") VALUES (" +
                    "'" + objeto.getProducto_compuesto_ID() + "'" +
                    ",'" + objeto.getCliente_proveedor_ID() + "'" +
                    ",'" + objeto.getPrecio() + "'" +
                    ",'" + objeto.getNombre_producto() + "'" +
                    ")";

            bd.ejecutaSinRetorno(query);
            Cursor cursor = bd.ejecutaConRetorno("SELECT MAX(ID) maximo FROM Cliente_producto_compuesto");
            cursor.moveToPosition(0);
            return Integer.valueOf(cursor.getString(0));

        } catch (Exception ex) {
            Utils.escribeLog(ex, "FachadaCliente_producto_compuesto.ingresar");
            return 0;
        }
    }

    public static void actualizar(Cliente_producto_compuesto objeto, ManagerProviderBD bd) {
        try {
            String query = "UPDATE Cliente_producto_compuesto SET " +
                    "producto_compuesto_ID='" + objeto.getProducto_compuesto_ID() + "'" +
                    ",cliente_proveedor_ID='" + objeto.getCliente_proveedor_ID() + "'" +
                    ",precio='" + objeto.getPrecio() + "'" +
                    ",nombre_producto='" + objeto.getNombre_producto() + "'" +
                    " WHERE ID=" + objeto.getID();

            bd.ejecutaSinRetorno(query);
        } catch (Exception ex) {
            Utils.escribeLog(ex, "FachadaCliente_producto_compuesto.actualizar");
        }
    }

    public static void actualizarJSON(Cliente_producto_compuestoJSON objeto, ManagerProviderBD bd) {
        try {
            String query = "UPDATE Cliente_producto_compuesto SET " +
                    "producto_compuesto_ID='" + objeto.getProducto_compuesto_ID() + "'" +
                    ",cliente_proveedor_ID='" + objeto.getCliente_proveedor_ID() + "'" +
                    ",precio='" + objeto.getPrecio() + "'" +
                    ",nombre_producto='" + objeto.getNombre_producto() + "'" +
                    " WHERE ID=" + objeto.getID();

            bd.ejecutaSinRetorno(query);
        } catch (Exception ex) {
            Utils.escribeLog(ex, "FachadaCliente_producto_compuesto.actualizar");
        }
    }


    public static void eliminar(int ID, ManagerProviderBD bd) {
        try {
            String query = "DELETE FROM Cliente_producto_compuesto WHERE ID=" + ID;
            bd.ejecutaSinRetorno(query);
        } catch (Exception ex) {
            Utils.escribeLog(ex, "FachadaCliente_producto_compuesto.eliminar");
        }
    }

}//Fin Clase

