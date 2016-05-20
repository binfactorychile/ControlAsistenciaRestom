package com.develop.binfactory.controlaccesorestom;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.develop.binfactory.controlaccesorestom.clases.Asistencia_trabajador;
import com.develop.binfactory.controlaccesorestom.clases.Trabajador;
import com.develop.binfactory.controlaccesorestom.controladores.CtrlAsistencia_trabajador;
import com.develop.binfactory.controlaccesorestom.controladores.CtrlTrabajador;
import com.develop.binfactory.controlaccesorestom.logica.soporte.ManagerProviderBD;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import com.github.johnpersano.supertoasts.SuperToast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";


    //FLAGS
    public static int FLAG_DESAYUNO_INICIO = 500;
    public static int FLAG_DESAYUNO_TERMINO = 1100;
    public static int FLAG_ALMUERZO_INICIO = 1101;
    public static int FLAG_ALMUERZO_TERMINO = 1700;
    public static int FLAG_CENA_INICIO = 1701;
    public static int FLAG_CENA_TERMINO = 100;

    private EditText edt_rut;
    private Button btn_uno;
    private Button btn_dos;
    private Button btn_tres;
    private Button btn_cuatro;
    private Button btn_cinco;
    private Button btn_seis;
    private Button btn_siete;
    private Button btn_ocho;
    private Button btn_nueve;
    private Button btn_cero;
    private Button btn_guion;
    private Button btn_ok;
    private Button btn_k;
    private ImageButton btn_delete;
    private onSincronizarListener on_sincronizar_listener;
    private Comunicator c;

    public interface onSincronizarListener {
        public void sicronizarPeriodico();
    }

    public void setOn_sincronizar_listener(onSincronizarListener on_sincronizar_listener) {
        this.on_sincronizar_listener = on_sincronizar_listener;
    }

    public void setComunicator(Comunicator _c)
    {
        c= _c;
    }

    public static TestFragment newInstance(int sectionNumber) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TestFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
        edt_rut = (EditText) rootView.findViewById(R.id.edt_rut);
        btn_uno = (Button) rootView.findViewById(R.id.btn_uno);
        btn_dos = (Button) rootView.findViewById(R.id.btn_dos);
        btn_tres = (Button) rootView.findViewById(R.id.btn_tres);
        btn_cuatro = (Button) rootView.findViewById(R.id.btn_cuatro);
        btn_cinco = (Button) rootView.findViewById(R.id.btn_cinco);
        btn_seis = (Button) rootView.findViewById(R.id.btn_seis);
        btn_siete = (Button) rootView.findViewById(R.id.btn_siete);
        btn_ocho = (Button) rootView.findViewById(R.id.btn_ocho);
        btn_nueve = (Button) rootView.findViewById(R.id.btn_nueve);
        btn_cero = (Button) rootView.findViewById(R.id.btn_cero);
        btn_k = (Button) rootView.findViewById(R.id.btn_k);
        btn_guion = (Button) rootView.findViewById(R.id.btn_guion);
        btn_delete = (ImageButton) rootView.findViewById(R.id.btn_delete);
        btn_ok = (Button) rootView.findViewById(R.id.btn_ok);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.oneClickListener();
    }

    private void oneClickListener() {
        btn_cero.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                edt_rut.setText(edt_rut.getText() + "0");
            }
        });
        btn_uno.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                edt_rut.setText(edt_rut.getText() + "1");
            }
        });

        btn_dos.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                edt_rut.setText(edt_rut.getText() + "2");
            }
        });

        btn_tres.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                edt_rut.setText(edt_rut.getText() + "3");
            }
        });

        btn_cuatro.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                edt_rut.setText(edt_rut.getText() + "4");
            }
        });

        btn_cinco.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                edt_rut.setText(edt_rut.getText() + "5");
            }
        });

        btn_seis.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                edt_rut.setText(edt_rut.getText() + "6");
            }
        });

        btn_siete.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                edt_rut.setText(edt_rut.getText() + "7");
            }
        });

        btn_ocho.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                edt_rut.setText(edt_rut.getText() + "8");
            }
        });

        btn_nueve.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                edt_rut.setText(edt_rut.getText() + "9");
            }
        });
        btn_k.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                edt_rut.setText(edt_rut.getText() + "k");
            }
        });

        btn_guion.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                String texto = edt_rut.getText().toString();
                if (texto.length() >= 7)
                    edt_rut.setText(edt_rut.getText() + "-");
            }
        });

        btn_delete.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] split = edt_rut.getText().toString().split("");
                edt_rut.setText("");
                for (int i = 0; i < split.length; i++) {
                    if (i != split.length - 1) {
                        edt_rut.setText(edt_rut.getText().toString() + split[i]);
                    }

                }
            }
        });

        btn_ok.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                c.seleccionExtras();
                boolean validation = validarRut(edt_rut.getText().toString());
                String query = "select * from trabajador where rut like '" + edt_rut.getText().toString() + "'";
                ArrayList<Trabajador> arrTrabajador = CtrlTrabajador.getListado(query, v.getContext());
                if (validation && (arrTrabajador.size() > 0)) {
                    Trabajador objTrabajador = arrTrabajador.get(0);
                    String horario = getHorario();
                    boolean isChecked = estaCheckeado(objTrabajador.getID(), v, horario);
                    if (isChecked) {
                        edt_rut.setText("");
                        //Toast.makeText(getActivity(), "Trabajador: " + arrTrabajador.get(0).fnombre.toString() + " ya se ha checkeado", Toast.LENGTH_SHORT).show();
                        SuperToast superToast = new SuperToast(getActivity());
                        superToast.setDuration(SuperToast.Duration.LONG);
                        superToast.setText("Trabajador: " + arrTrabajador.get(0).fnombre.toString() + " ya se ha checkeado");
                        superToast.setTextSize(40);
                        superToast.setIcon(SuperToast.Icon.Dark.EXIT, SuperToast.IconPosition.LEFT);
                        superToast.show();
                    } else {
                        int resultado = registrarTrabajador(objTrabajador, v);
                        if (resultado > 0) {

                            boolean resultado2 = registrarSincronizacionAsistencia(resultado, v);
                        }

                        edt_rut.setText("");
                        //Toast.makeText(getActivity(), "Trabajador: " + arrTrabajador.get(0).fnombre.toString() + " checkeado", Toast.LENGTH_SHORT).show();
                        SuperToast superToast = new SuperToast(getActivity());
                        superToast.setDuration(SuperToast.Duration.LONG);
                        superToast.setText("Trabajador: " + arrTrabajador.get(0).fnombre.toString() + " checkeado");
                        superToast.setTextSize(40);
                        superToast.setIcon(SuperToast.Icon.Dark.INFO, SuperToast.IconPosition.LEFT);
                        superToast.show();
                        on_sincronizar_listener.sicronizarPeriodico();

                    }
                } else {
                    //Toast.makeText(getActivity(), "Rut ingresado no existe", Toast.LENGTH_SHORT).show();
                    edt_rut.setText("");
                    SuperToast superToast = new SuperToast(getActivity());
                    superToast.setDuration(SuperToast.Duration.LONG);
                    superToast.setText("Rut ingresado no existe");
                    superToast.setTextSize(40);
                    superToast.setIcon(SuperToast.Icon.Dark.INFO, SuperToast.IconPosition.LEFT);
                    superToast.show();
                }
            }
        });

        btn_delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                edt_rut.setText("");
                return true;
            }
        });
    }


    private int registrarTrabajador(Trabajador objTrabajador, View v) {
        int resultado = 0;
        Asistencia_trabajador objAsistenciaTrabajador = new Asistencia_trabajador();
        objAsistenciaTrabajador.fcliente_proveedor_ID = objTrabajador.fcliente_proveedor_ID;
        objAsistenciaTrabajador.ftrabajador_ID = objTrabajador.fID;
        objAsistenciaTrabajador.ffecha = Utils.getfechaHoraActualAlRevez();
        objAsistenciaTrabajador.fhorario = getHorario();
        resultado = objAsistenciaTrabajador.ingresar(v.getContext());
        return resultado;
    }

    private boolean registrarSincronizacionAsistencia(int trabajador_ID, View v) {

        try {
            ManagerProviderBD bd = new ManagerProviderBD(v.getContext());
            String query = "insert into sincronizacion_asistencia (registro_ID) values('" + trabajador_ID + "')";
            bd.open();
            bd.ejecutaSinRetorno(query);
            bd.close();
        } catch (Exception ex) {
            Utils.escribeLog(ex, "MainActivity.registrarSincronizacionAsistencia");
            return false;
        }
        return true;
    }

    private String getHorario() {
        String horario = "";
        if (Utils.getHorarioComidas(FLAG_DESAYUNO_INICIO, FLAG_DESAYUNO_TERMINO))
            horario = "desayuno";
        else if (Utils.getHorarioComidas(FLAG_ALMUERZO_INICIO, FLAG_ALMUERZO_TERMINO))
            horario = "almuerzo";
        else if (Utils.getHorarioComidas(FLAG_CENA_INICIO, FLAG_CENA_TERMINO))
            horario = "cena";
        return horario;
    }

    private boolean estaCheckeado(int trabajador_ID, View v, String horario) {
        String fecha_actual = Utils.getfechaHoraActualAlRevez();

        //determinar en que horario se encuentra


        Toast.makeText(getActivity(), "Horario:" + horario, Toast.LENGTH_SHORT).show();
        String query = "SELECT * FROM asistencia_trabajador WHERE date('" + fecha_actual + "') LIKE date(fecha) and horario = '" + horario + "' and trabajador_ID = " + trabajador_ID;
        ArrayList<Asistencia_trabajador> arrAsistenciaTrabajador = CtrlAsistencia_trabajador.getListado(query, v.getContext());
        if (arrAsistenciaTrabajador.size() > 0) {
            return true;
        }
        return false;
    }

    private boolean isCheckedToday(int trabajador_ID, View v) {
        ManagerProviderBD bd = new ManagerProviderBD(v.getContext());
        bd.open();
        boolean checked = false;
        String fecha_actual = Utils.getfechaHoraActualAlRevez();
        String query = "SELECT cast((strftime('%s','" + fecha_actual + "') - strftime('%s',fecha)) AS REAL)/60/60 AS diferencia_horas FROM asistencia_trabajador WHERE diferencia_horas <= 3 and trabajador_ID = " + trabajador_ID;
        Cursor cursor = bd.ejecutaConRetorno(query);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            double diferencia_horas = Double.parseDouble(cursor.getString(cursor.getColumnIndex("diferencia_horas")));
            Log.d("test", "Diferencia en horas: " + diferencia_horas);
            if (diferencia_horas <= 3) {
                checked = true;
            }
            cursor.moveToNext();
        }
        bd.close();

        return checked;
    }


    private boolean validarRut(String rut) {

        boolean validation = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validation = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validation;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

}