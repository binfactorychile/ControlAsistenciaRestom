package com.develop.binfactory.controlaccesorestom;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDButton;
import com.develop.binfactory.controlaccesorestom.clases.Cliente_producto_compuesto;
import com.develop.binfactory.controlaccesorestom.clases.Sincronizador;
import com.develop.binfactory.controlaccesorestom.clases.Trabajador;
import com.develop.binfactory.controlaccesorestom.controladores.CtrlCliente_producto_compuesto;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import com.develop.binfactory.controlaccesorestom.logica.soporte.clsMantenimiento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,TestFragment.onSincronizarListener,Comunicator {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;


    TestFragment test;
    private ProgressDialog dialog;
    String resultado_sincro;//
    String mensaje_respuesta_sincro;
    List<Integer> auxlista_ids;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE );

        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        dialog = new ProgressDialog(this);//
        dialog.setMessage("Sincronizando datos. Por favor espere...");
        dialog.setTitle("Sincronizando");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        if (!prefs.getBoolean("firstTime", false)) {
//            // run your one time code
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putBoolean("firstTime", true);
//            editor.commit();
//
//            CtrlCliente_producto_compuesto.ingresar(new Cliente_producto_compuesto(3,1,2000,"Bebida 500cc"),this);
//            CtrlCliente_producto_compuesto.ingresar(new Cliente_producto_compuesto(5,1,3000,"Cerveza 1lt"),this);
//            CtrlCliente_producto_compuesto.ingresar(new Cliente_producto_compuesto(6,1,2000,"Agua 500cc"),this);
//            CtrlCliente_producto_compuesto.ingresar(new Cliente_producto_compuesto(9,1,2000,"Té"),this);
//            CtrlCliente_producto_compuesto.ingresar(new Cliente_producto_compuesto(7,1,2000,"Café"),this);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        SincronizadorNormal sincronormal = new SincronizadorNormal();
        if (position == 0) {
            test = TestFragment.newInstance(position+1);
            test.setOn_sincronizar_listener(this);
            test.setComunicator(this);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, test)
                    .commit();
        } else if (position == 1) {
            clsMantenimiento mantenimiento = new clsMantenimiento(this);
            String resultado = mantenimiento.respaldarBD();
            sincronormal.origen = Integer.toString(2);
            sincronormal.execute("HOLI", "JKJK");

        } else if (position == 2) {
            clsMantenimiento mantenimiento = new clsMantenimiento(this);
            String resultado = mantenimiento.respaldarBD();
            sincronormal.origen = Integer.toString(position);
            sincronormal.execute("HOLI", "JKJK");
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "Registro Asistencia";
                break;
            case 2:
                mTitle = "Sincronizar Trabajadores";
                break;
            case 3:
                mTitle = "Sincronizar Asistencias";
                break;
        }
    }

    private void realizarSincronizacion(String []cliente_producto_compuesto_ids) {
//        int cantidad_registros = CtrlAsistencia_trabajador.getCantidadRegistrosSincronizacionAsistencia(this);
//
//        if (cantidad_registros >= 5) {
//            SincronizadorPeriodico sincroPeriodico = new SincronizadorPeriodico();
//            sincroPeriodico.execute("HOLI", "JKJK");
//        }
        SincronizadorPeriodico sincroPeriodico = new SincronizadorPeriodico(cliente_producto_compuesto_ids);
        sincroPeriodico.execute("HOLI", "JKJK");

    }


    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    ////////sincronizacion de los trabajadores y sus asistencias::::+
    public void enviarAsistencias() {

    }

    @Override
    public void seleccionExtras(int cliente_proveedor_ID, final String rut, final String horario) {
        String query = "select * from cliente_producto_compuesto where cliente_proveedor_ID = "+cliente_proveedor_ID;
        List<Cliente_producto_compuesto> arrClienteProdComp = CtrlCliente_producto_compuesto.getListado(query,this);
        List<String> lista = new ArrayList<String>();
        auxlista_ids = new ArrayList<Integer>();
        for (Cliente_producto_compuesto auxClienteProdComp : arrClienteProdComp) {
            lista.add(auxClienteProdComp.toString());
            auxlista_ids.add((auxClienteProdComp.getProducto_compuesto_ID()));
        }

        if (arrClienteProdComp != null && arrClienteProdComp.size() > 0) {
            MaterialDialog mDialog = new MaterialDialog.Builder(this)
                    .title("SELECCIONE EXTRAS")
                    .items(lista)
                    .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                            /**
                             * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                             * returning false here won't allow the newly selected check box to actually be selected.
                             * See the limited multi choice dialog example in the sample project for details.
                             **/
                            String[] cliente_producto_compuesto_ids = new String[which.length];
                            for (int i = 0; i < which.length; i++) {
                                cliente_producto_compuesto_ids[i] = String.valueOf(auxlista_ids.get(which[i]));
                            }
                            registrarAsistenciaTrabajador(rut, horario, cliente_producto_compuesto_ids);
                            //test.registraAsistencia(dialog.getContext(), objTrabajador, cliente_producto_compuesto_ids);
                            return true;
                        }
                    })
                    .positiveColorRes(R.color.colorCheckBoxExtras)
                    .widgetColorRes(R.color.colorCheckBoxExtras)
                    .positiveText("ACEPTAR")
                    .show();
            MDButton btnPositive = mDialog.getActionButton(DialogAction.POSITIVE);
            btnPositive.setHeight(60);
            btnPositive.setWidth(150);
            btnPositive.setTextSize(25);
            btnPositive.setTextColor(getResources().getColor(R.color.white));
            btnPositive.setBackgroundColor(getResources().getColor(R.color.colorCheckBoxExtras));
        }

    }

    @Override
    public void sicronizarPeriodico(String []cliente_producto_compuesto_ids) {
        realizarSincronizacion(cliente_producto_compuesto_ids);
    }

    @Override
    public boolean existeConectividad() {
        SincronizadorConectividad sincronizadorConectividad = new SincronizadorConectividad();
        sincronizadorConectividad.origen = "conectividad";
        try {
            String resultado = sincronizadorConectividad.execute("HOLI", "JKJK").get();
            if(resultado.equals("1"))
                return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public String consultaTrabajador(String rut_trabajador, String horario) {
        SincronizadorConectividad sincronizadorConectividad = new SincronizadorConectividad();
        sincronizadorConectividad.origen = "consulta";
        sincronizadorConectividad.rut = rut_trabajador;
        sincronizadorConectividad.horario = horario;
        String resultado ="error";
        try {
            resultado = sincronizadorConectividad.execute("HOLI", "JKJK").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public String registrarAsistenciaTrabajador(String rut, String horario, String[] cliente_producto_compuesto_ids) {
        SincronizadorConectividad sincronizadorConectividad = new SincronizadorConectividad();
        sincronizadorConectividad.origen = "registrar";
        sincronizadorConectividad.rut = rut;
        sincronizadorConectividad.horario = horario;
        sincronizadorConectividad.cliente_producto_compuesto_ids = cliente_producto_compuesto_ids;
        String resultado ="error";
        try {
            resultado = sincronizadorConectividad.execute("HOLI", "JKJK").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    private class SincronizadorNormal extends AsyncTask<String, Float, Integer> {
        public String origen = "";

        protected void onPreExecute() {
            dialog.setProgress(0);
            dialog.setMax(100);
            dialog.show(); // Mostramos el di�logo antes de comenzar
        }

        protected Integer doInBackground(String[] arrString) {

            Sincronizador sincro = new Sincronizador(MainActivity.this,
                    "1");// ((VariablesSesion)getApplication()).acces_usuario_ID);
            if (!sincro.mac_address.equalsIgnoreCase("no")) {
                if (sincro.existeConectividad(MainActivity.this)) {
                    resultado_sincro = "correcto";
                    if (origen.equals("1")) {
//                        mensaje_respuesta_sincro = sincro
//                                .enviarAsistenciasTrabajador();
                        if (mensaje_respuesta_sincro.equals("ok")) {

                            dialog.dismiss();
                        } else {
                            resultado_sincro = "error_envio";

                        }
                    } else if (origen.equals("2")) {
                        mensaje_respuesta_sincro = sincro.traerDatosServidorPrincipal();
                        if (mensaje_respuesta_sincro.equals("ok")) {

                            dialog.dismiss();
                        } else {
                            resultado_sincro = "error_envio";

                        }
                    }


                } else {
                    dialog.dismiss();
                    resultado_sincro = "error_conexion";
                    // Toast.makeText(getApplicationContext(),"No existe conectividad",
                    // Toast.LENGTH_SHORT).show();

                }
            } else {
                resultado_sincro = "error_no_conectada";
            }
            return 1;
        }

        protected void onProgressUpdate(Float[] valores) {
            int p = Math.round(100 * valores[0]);
            dialog.setProgress(p);
        }

        protected void onPostExecute(Integer bytes) {

            dialog.dismiss();

            if (resultado_sincro.equals("error_conexion")) {
                Utils.muestraMensaje(
                        "Error al sincronizar, no se pudo conectar con el servidor. Por favor intente mas tarde",
                        MainActivity.this);
            } else if (resultado_sincro.equals("error_envio")) {
                Utils.muestraMensaje(mensaje_respuesta_sincro,
                        MainActivity.this);
            } else if (resultado_sincro.equals("error")) {
                Utils.muestraMensaje(
                        "Error al sincronizar, no se pudo conectar con el servidor. Por favor intente mas tarde",
                        MainActivity.this);
            } else if (resultado_sincro.equals("ok_admin")) {
                Utils.muestraMensaje(
                        "Ahora debe ingresar con un vendedor y sincronizar",
                        MainActivity.this);
            } else if (resultado_sincro.equals("error_no_conectada")) {
                Utils.muestraMensaje(
                        "La tablet no se encuentra conectada a la misma red WIFI",
                        MainActivity.this);
            }

        }

        private void sincronizarDatos(View v) {

        }
    }

    private class SincronizadorPeriodico extends AsyncTask<String, Float, Integer> {
        private String []cliente_producto_compuesto_ids;
        public SincronizadorPeriodico(String []cliente_producto_compuesto_ids) {
            this.cliente_producto_compuesto_ids = cliente_producto_compuesto_ids;
        }

        protected void onPreExecute() {
        }

        protected Integer doInBackground(String[] arrString) {

            Sincronizador sincro = new Sincronizador(MainActivity.this,
                    "1");// ((VariablesSesion)getApplication()).acces_usuario_ID);
            if (!sincro.mac_address.equalsIgnoreCase("no")) {
                if (sincro.existeConectividad(MainActivity.this)) {
                    resultado_sincro = "correcto";

                    mensaje_respuesta_sincro = sincro
                            .enviarAsistenciasTrabajador(cliente_producto_compuesto_ids);
                    if (mensaje_respuesta_sincro.equals("ok")) {

                        dialog.dismiss();
                    } else {
                        resultado_sincro = "error_envio";

                    }
                } else {
                    dialog.dismiss();
                    resultado_sincro = "error_conexion";
                    // Toast.makeText(getApplicationContext(),"No existe conectividad",
                    // Toast.LENGTH_SHORT).show();

                }
            } else {
                resultado_sincro = "error_no_conectada";
            }
            return 1;
        }

        protected void onProgressUpdate(Float[] valores) {

        }

        protected void onPostExecute(Integer bytes) {

            dialog.dismiss();

            if (resultado_sincro.equals("error_conexion")) {
                Utils.muestraMensaje(
                        "Error al sincronizar, no se pudo conectar con el servidor. Por favor intente mas tarde",
                        MainActivity.this);
            } else if (resultado_sincro.equals("error_envio")) {
                Utils.muestraMensaje(mensaje_respuesta_sincro,
                        MainActivity.this);
            } else if (resultado_sincro.equals("error")) {
                Utils.muestraMensaje(
                        "Error al sincronizar, no se pudo conectar con el servidor. Por favor intente mas tarde",
                        MainActivity.this);
            } else if (resultado_sincro.equals("ok_admin")) {
                Utils.muestraMensaje(
                        "Ahora debe ingresar con un vendedor y sincronizar",
                        MainActivity.this);
            } else if (resultado_sincro.equals("error_no_conectada")) {
                Utils.muestraMensaje(
                        "La tablet no se encuentra conectada a la misma red WIFI",
                        MainActivity.this);
            }

        }
    }

    private class SincronizadorConectividad extends AsyncTask<String, Float, String> {
        public String origen = "";
        public String rut= "";
        public String horario = "";
        public String []cliente_producto_compuesto_ids;
        protected void onPreExecute() {
            dialog.setProgress(0);
            dialog.setMax(100);
            dialog.show(); // Mostramos el di�logo antes de comenzar
        }

        protected String doInBackground(String[] arrString) {

            Sincronizador sincro = new Sincronizador(MainActivity.this,
                    "1");// ((VariablesSesion)getApplication()).acces_usuario_ID);
            if (origen.equals("conectividad")) {
                if (!sincro.mac_address.equalsIgnoreCase("no")) {
                    if (sincro.existeConectividad(MainActivity.this)) {
                        resultado_sincro = "correcto";
                        dialog.dismiss();
                        return "1";
                    }
                    else{
                        dialog.dismiss();
                        resultado_sincro = "error_conexion";
                    }
                }
                else{
                    dialog.dismiss();
                    resultado_sincro = "error_no_conectada";
                }

            }
            if (origen.equals("consulta")) {
                resultado_sincro = sincro.consultaTrabajador("consultaTrabajador",rut,horario,null);
                dialog.dismiss();
                return resultado_sincro;
            }
            if (origen.equals("registrar")) {
                resultado_sincro = sincro.consultaTrabajador("registrarAsistenciaTrabajador",rut,horario,cliente_producto_compuesto_ids);
                dialog.dismiss();
                return resultado_sincro;
            }
            dialog.dismiss();
            return "0";

        }

        protected void onProgressUpdate(Float[] valores) {
            int p = Math.round(100 * valores[0]);
            dialog.setProgress(p);
        }

        protected void onPostExecute(Integer bytes) {

            dialog.dismiss();

            if (resultado_sincro.equals("error_conexion")) {
                Utils.muestraMensaje(
                        "Error al sincronizar, no se pudo conectar con el servidor. Por favor intente mas tarde",
                        MainActivity.this);
            } else if (resultado_sincro.equals("error_envio")) {
                Utils.muestraMensaje(mensaje_respuesta_sincro,
                        MainActivity.this);
            } else if (resultado_sincro.equals("error")) {
                Utils.muestraMensaje(
                        "Error al sincronizar, no se pudo conectar con el servidor. Por favor intente mas tarde",
                        MainActivity.this);
            } else if (resultado_sincro.equals("ok_admin")) {
                Utils.muestraMensaje(
                        "Ahora debe ingresar con un vendedor y sincronizar",
                        MainActivity.this);
            } else if (resultado_sincro.equals("error_no_conectada")) {
                Utils.muestraMensaje(
                        "La tablet no se encuentra conectada a la misma red WIFI",
                        MainActivity.this);
            }

        }

        private void sincronizarDatos(View v) {

        }
    }






}
