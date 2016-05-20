package com.develop.binfactory.controlaccesorestom;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.WindowManager;

import com.develop.binfactory.controlaccesorestom.clases.Sincronizador;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import com.develop.binfactory.controlaccesorestom.logica.soporte.clsMantenimiento;

public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,TestFragment.onSincronizarListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;



    private ProgressDialog dialog;
    String resultado_sincro;//
    String mensaje_respuesta_sincro;
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
            TestFragment test = TestFragment.newInstance(position+1);
            test.setOn_sincronizar_listener(this);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, test)
                    .commit();

        } else if (position == 1) {
            clsMantenimiento mantenimiento = new clsMantenimiento(this);
            String resultado = mantenimiento.respaldarBD();
            sincronormal.origen = Integer.toString(position);
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

    private void realizarSincronizacion() {
//        int cantidad_registros = CtrlAsistencia_trabajador.getCantidadRegistrosSincronizacionAsistencia(this);
//
//        if (cantidad_registros >= 5) {
//            SincronizadorPeriodico sincroPeriodico = new SincronizadorPeriodico();
//            sincroPeriodico.execute("HOLI", "JKJK");
//        }
        SincronizadorPeriodico sincroPeriodico = new SincronizadorPeriodico();
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
    public void sicronizarPeriodico() {
        realizarSincronizacion();
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
                        mensaje_respuesta_sincro = sincro
                                .enviarAsistenciasTrabajador();
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


        protected void onPreExecute() {
        }

        protected Integer doInBackground(String[] arrString) {

            Sincronizador sincro = new Sincronizador(MainActivity.this,
                    "1");// ((VariablesSesion)getApplication()).acces_usuario_ID);
            if (!sincro.mac_address.equalsIgnoreCase("no")) {
                if (sincro.existeConectividad(MainActivity.this)) {
                    resultado_sincro = "correcto";

                    mensaje_respuesta_sincro = sincro
                            .enviarAsistenciasTrabajador();
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






}
