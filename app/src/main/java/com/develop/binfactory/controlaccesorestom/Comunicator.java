package com.develop.binfactory.controlaccesorestom;

import com.develop.binfactory.controlaccesorestom.clases.Trabajador;

/**
 * Created by Victor on 20-05-16.
 */
public interface Comunicator {

    public void seleccionExtras(int cliente_proveedor_ID, Trabajador objTrabajador);
    public boolean existeConectividad();
}
