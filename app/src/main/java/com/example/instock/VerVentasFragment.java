package com.example.instock;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instock.Adapter.VentasAdaptador;
import com.example.instock.BD.VentasManagerDB;
import com.example.instock.models.ListaVentas;
import com.example.instock.utils.Utils;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class VerVentasFragment extends Fragment {
    Utils utils = new Utils();
    VentasAdaptador ventaAdaptador;
    List<ListaVentas> VentasList = new ArrayList<>();
    RecyclerView recyclerVentas;
    EditText dtDesde, dtHasta;
    TextInputLayout tillDesde,tillHasta;
    String FechaDesde = null;
    String FechaHasta = null;
    Button btnFiltro;
    TextView tvTotalVal;
    int Filtro=0;  //1. Semana, 2. Mes, 3. Año
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
              View vista = inflater.inflate(R.layout.fragment_ver_ventas, container, false);

        enlazarVistas(vista);
        //dtHastaClick();
        dtHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC-6"));//Obtenemos la fecha actual

                Date currentTime = calendar.getTime();

                System.out.println("Día actual milisegundos: " + calendar.getTime());

                //Establecemos la restricción de escoger una fecha posterior a la actual
                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Hasta").setCalendarConstraints(constraintsBuilder.build());
                MaterialDatePicker<Long> picker = builder.build();
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {

                        String dateString = utils.millisecondsToDDMMYYYY(selection);
                        // String dateStringValidation = utils.dateToString(calendar.getTime());

                        //   if(dateString.equals(dateStringValidation)){
                        //      tillHasta.setError("Selecciona una fecha posterior a la actual");
                        //  } else {
                        FechaHasta = utils.millisecondsToYYYYMMDD(selection);
                        Toast.makeText(getContext(),FechaHasta,Toast.LENGTH_LONG).show();
                        dtHasta.setText(FechaHasta);
                        //dtHasta.setText(dateString);
                        tillHasta.setError(null);
                        //  }
                    }
                });
                picker.show(getParentFragmentManager(), picker.toString());

            }
        });


        //dtDesdeClick();
        dtDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC-6"));//Obtenemos la fecha actual

                Date currentTime = calendar.getTime();

                System.out.println("Día actual milisegundos: " + calendar.getTime());

                //Establecemos la restricción de escoger una fecha posterior a la actual
                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Desde").setCalendarConstraints(constraintsBuilder.build());
                MaterialDatePicker<Long> picker = builder.build();
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String dateString = utils.millisecondsToDDMMYYYY(selection);
                        FechaDesde = utils.millisecondsToYYYYMMDD(selection);
                        dtDesde.setText(FechaDesde);
                        //dtDesde.setText(dateString);
                        tillDesde.setError(null);

                    }
                });
                picker.show(getParentFragmentManager(), picker.toString());

            }
        });

        btnFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDatos();
            }
        });
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargarDatos();
    }
    public void cargarDatos()
    {
        String FechaDesde = dtDesde.getText().toString();
        String FechaHasta = dtHasta.getText().toString();
        VentasManagerDB ventasManagerDB = new VentasManagerDB(getContext());
        VentasList = ventasManagerDB.obtenerVentas(FechaDesde, FechaHasta);

        recyclerVentas = getView().findViewById(R.id.recyclerVentas);
        recyclerVentas.setLayoutManager(new LinearLayoutManager(getActivity()));

        ventaAdaptador = new VentasAdaptador(VentasList, getActivity());
        recyclerVentas.setAdapter(ventaAdaptador);

        tvTotalVal.setText("$" + ventasManagerDB.obtenerTotalVentas(FechaDesde, FechaHasta));
    }

    public void enlazarVistas(View v)
    {
        dtHasta = v.findViewById(R.id.dtHasta);
        dtDesde = v.findViewById(R.id.dtDesde);
        tillDesde = v.findViewById(R.id.tilDesde);
        tillHasta = v.findViewById(R.id.tilHasta);
        tvTotalVal = v.findViewById(R.id.tvTotalVal);
        btnFiltro =  v.findViewById(R.id.btnFiltrar);
    }
/*
    public void dtHastaClick()
    {
        dtHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC-6"));//Obtenemos la fecha actual

                Date currentTime = calendar.getTime();

                System.out.println("Día actual milisegundos: " + calendar.getTime());

                //Establecemos la restricción de escoger una fecha posterior a la actual
                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Hasta").setCalendarConstraints(constraintsBuilder.build());
                MaterialDatePicker<Long> picker = builder.build();
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {

                        String dateString = utils.millisecondsToDDMMYYYY(selection);
                       // String dateStringValidation = utils.dateToString(calendar.getTime());

                     //   if(dateString.equals(dateStringValidation)){
                      //      tillHasta.setError("Selecciona una fecha posterior a la actual");
                      //  } else {
                            FechaHasta = utils.millisecondsToYYYYMMDD(selection);
                            Toast.makeText(getContext(),FechaHasta,Toast.LENGTH_LONG).show();
                            dtHasta.setText(FechaHasta);
                            //dtHasta.setText(dateString);
                            tillHasta.setError(null);
                      //  }
                    }
                });
                picker.show(getParentFragmentManager(), picker.toString());

            }
        });

    }*/
/*
    public void dtDesdeClick()
    {
        dtDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC-6"));//Obtenemos la fecha actual

                Date currentTime = calendar.getTime();

                System.out.println("Día actual milisegundos: " + calendar.getTime());

                //Establecemos la restricción de escoger una fecha posterior a la actual
                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Desde").setCalendarConstraints(constraintsBuilder.build());
                MaterialDatePicker<Long> picker = builder.build();
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String dateString = utils.millisecondsToDDMMYYYY(selection);
                        FechaDesde = utils.millisecondsToYYYYMMDD(selection);
                        dtDesde.setText(FechaDesde);
                      //dtDesde.setText(dateString);
                        tillDesde.setError(null);

                    }
                });
                picker.show(getParentFragmentManager(), picker.toString());

            }
        });

    }*/
}