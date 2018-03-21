package com.develop.binfactory.controlaccesorestom;

import com.develop.binfactory.controlaccesorestom.clases.Trabajador;

/**
 * Created by Victor on 20-05-16.
 */
public interface Comunicator {

    public void seleccionExtras(int cliente_proveedor_ID,String rut, String horario);

    public boolean existeConectividad();

    public String consultaTrabajador(String rut_trabajador, String horario);

}
