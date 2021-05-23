package com.example.instock;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.instock.Adapter.VentasAdaptador;
import com.example.instock.BD.VentasManagerDB;
import com.example.instock.models.ListaVentas;
import com.example.instock.utils.Utils;
import com.google.android.material.datepicker.CalendarConstraints;
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
    String fechaDesde = "";
    String fechaHasta = "";
    Button btnFiltro, btnLimpiar;
    TextView tvTotalVal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_ver_ventas, container, false);

        enlazarVistas(vista);
        dtDesdeClickListener();
        dtHastaClickListener();
        btnFiltroClickListener();
        btnLimpiarClickListener();

        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargarDatos();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        limpiarDatos();
        super.onViewStateRestored(savedInstanceState);
    }

    private void enlazarVistas(View v)
    {
        dtHasta = v.findViewById(R.id.dtHasta);
        dtDesde = v.findViewById(R.id.dtDesde);
        tillDesde = v.findViewById(R.id.tilDesde);
        tillHasta = v.findViewById(R.id.tilHasta);
        tvTotalVal = v.findViewById(R.id.tvTotalVal);
        btnFiltro =  v.findViewById(R.id.btnFiltrar);
        btnLimpiar =  v.findViewById(R.id.btnLimpiar);
    }

    private void cargarDatos()
    {
        VentasManagerDB ventasManagerDB = new VentasManagerDB(getContext());
        VentasList = ventasManagerDB.obtenerVentas(fechaDesde, fechaHasta);

        recyclerVentas = getView().findViewById(R.id.recyclerVentas);
        recyclerVentas.setLayoutManager(new LinearLayoutManager(getActivity()));

        ventaAdaptador = new VentasAdaptador(VentasList, getActivity());
        recyclerVentas.setAdapter(ventaAdaptador);

        tvTotalVal.setText("$" + ventasManagerDB.obtenerTotalVentas(fechaDesde, fechaHasta));
    }

    //dtDesdeClick();
    private void dtDesdeClickListener(){
        dtDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Desde");
                MaterialDatePicker<Long> picker = builder.build();
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String dateString = utils.millisecondsToDDMMYYYY(selection);
                        fechaDesde = utils.millisecondsToYYYYMMDD(selection);
                        dtDesde.setText(dateString);
                        tillDesde.setError(null);

                    }
                });
                picker.show(getParentFragmentManager(), picker.toString());
            }
        });
    }

    //dtHastaClick();
    private void dtHastaClickListener(){
        dtHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Hasta");
                MaterialDatePicker<Long> picker = builder.build();
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {

                        String dateString = utils.millisecondsToDDMMYYYY(selection);

                        fechaHasta = utils.millisecondsToYYYYMMDD(selection);
                        dtHasta.setText(dateString);
                        tillHasta.setError(null);
                    }
                });
                picker.show(getParentFragmentManager(), picker.toString());

            }
        });
    }

    private void btnFiltroClickListener(){
        btnFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDatos();
            }
        });
    }

    private void btnLimpiarClickListener(){
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarDatos();
            }
        });
    }

    private void limpiarDatos(){
        //Limpiamos los filtros
        dtDesde.setText(null);
        dtHasta.setText(null);
        fechaDesde = "";
        fechaHasta = "";
        cargarDatos();
    }
}