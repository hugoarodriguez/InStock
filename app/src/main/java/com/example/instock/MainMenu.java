package com.example.instock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instock.firebasemanager.FirebaseManager;
import com.example.instock.models.ModalDialogValues;
import com.example.instock.models.Usuario;
import com.example.instock.utils.NoticeDialogFragment;
import com.example.instock.utils.Utils;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    public boolean isFragmentActivo() {
        return fragmentActivo;
    }

    public void setFragmentActivo(boolean fragmentActivo) {
        this.fragmentActivo = fragmentActivo;
    }

    private boolean fragmentActivo;//Variable para evaluar si hay un Fragment secundario activo
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;


    Fragment fAgregarProductos, fInicio,fProductos, fVerVentas, fVerReservas, fAgregarCliente,
            fCategorias, fClientes, fAcercaDe;
    FragmentTransaction transaction;

    Utils utils = new Utils();
    private ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

    /*Variable que permite determinar el método a ejecutar en los métodos
      onDialogPositiveClick y onDialogNegativeClick*/
    private int dialogOptionDisplay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main_menu);

        //Instancia del DrawerLayout
        drawerLayout = (DrawerLayout)findViewById(R.id.dl_main_menu);

        toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //Enlazamos el ActionBar con el DrawerLayout
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Habilitamos el ActionBar y establecemos el ícono de la hamburguesa
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

<<<<<<< HEAD
        //Instancia de los fragments
        fAgregarProductos = new AgregarProductosFragment();
        fInicio = new InicioFragment();
        fVerVentas = new VerVentasFragment();
        fVerReservas = new VerReservasFragment();
        fAgregarCliente = new AgregarClienteFragment();
        fCategorias = new CategoriasFragment();
        fProductos = new ConsultaProductosFragment() ;
        fClientes = new VerClientesFragment();
        fAcercaDe = new AcercaDe();
=======
        instanciarFragments();//Instancia de los fragments

        //Mostramos el FragmentInicio en nuestro contenedor
>>>>>>> c348a937d34181bcfefbf321a9d98a723ed7a6c6
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, fInicio).commit();

        //Instanciamos el NavigationView
        navigationView = (NavigationView)findViewById(R.id.nav_view);

        loadUserProfile(navigationView);//Cargamos los datos del perfil del usuario

        //Invocamos el método que enlaza la navegación de los fragments con los items del menú
        navegacionDeFragments();
    }

    private void instanciarFragments(){
        fAgregarProductos = new AgregarProductosFragment();
        fInicio = new InicioFragment();
        fVerVentas = new VerVentasFragment();
        fVerReservas = new VerReservasFragment();
        fAgregarCliente = new AgregarClienteFragment();
        fCategorias = new CategoriasFragment();
        fProductos = new ConsultaProductosFragment() ;
        fClientes = new VerClientesFragment();
    }

    //Método para actualizar contraseña
    private void actualizarPassword(TextView tvUserName){
        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.changeActionBarTitle("Actualizar Contraseña", getSupportActionBar());
                Fragment fActualizarPassword = new ActualizarPasswordFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_view, fActualizarPassword);
                transaction.addToBackStack(null);
                drawerLayout.closeDrawers();
                transaction.commit();
                drawerLayout.closeDrawers();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Agregar los Fragments restantes
    public void navegacionDeFragments(){
        navigationView.setNavigationItemSelectedListener(item -> {

            transaction = getSupportFragmentManager().beginTransaction();
            int itemID = item.getItemId();
            switch (itemID){

                case R.id.opc_inicio:
                    utils.changeActionBarTitle("Inicio", getSupportActionBar());
                    transaction.replace(R.id.fragment_container_view, fInicio);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;

                case R.id.opc_agg_productos:
                    utils.changeActionBarTitle("Agregar Productos", getSupportActionBar());
                    transaction.replace(R.id.fragment_container_view, fAgregarProductos);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();

                    break;

                case R.id.opc_productos:
                    utils.changeActionBarTitle("Ver Productos", getSupportActionBar());
                    transaction.replace(R.id.fragment_container_view, fProductos);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;
                case R.id.opc_ventas:
                    utils.changeActionBarTitle("Ver Ventas", getSupportActionBar());
                    transaction.replace(R.id.fragment_container_view, fVerVentas);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;
                case R.id.opc_reservas:
                    utils.changeActionBarTitle("Ver Reservas", getSupportActionBar());
                    transaction.replace(R.id.fragment_container_view, fVerReservas);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;
                case R.id.opc_categorias:
                    utils.changeActionBarTitle("Categorías", getSupportActionBar());
                    transaction.replace(R.id.fragment_container_view, fCategorias);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;
                case R.id.opc_agg_clientes:
                    utils.changeActionBarTitle("Agregar Cliente", getSupportActionBar());
                    transaction.replace(R.id.fragment_container_view, fAgregarCliente);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;
                case R.id.opc_clientes:
                    utils.changeActionBarTitle("Ver Clientes", getSupportActionBar());
                    transaction.replace(R.id.fragment_container_view, fClientes);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;

                case R.id.opc_acerca_de:
                    utils.changeActionBarTitle("Acerca De", getSupportActionBar());
                    transaction.replace(R.id.fragment_container_view, fAcercaDe);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;

                case R.id.opc_cerrar_sesion:
                    drawerLayout.closeDrawers();
                    cerrarSesionDialog();
                    break;
            }

            return true;
        });
    }

    //Método para controlar la función del botón de retroceso del dispositivo
    @Override
    public void onBackPressed() {
        //Al quitar el super método se deshabilita el botón
        //super.onBackPressed();
    }

    //Método que se invoca en el OnCreate de las pantallas de Editar/Modificar
    public void displayBackArrowOrHamburger(Context context, int dialogType){

        if(fragmentActivo){
            //Cambiamos la hamburguesa por la flecha de "regresar"
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24);

            //Bloqueamos Menú
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            //Asignamos el Listener al evento Click del botón del Toolbar
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backButtonDialog(dialogType);
                }
            });
            setFragmentActivo(false);//Indicamos que se salió del Fragment
        }
    }

    //dialogOptionDisplay = 1
    private void cerrarSesionDialog(){
        dialogOptionDisplay = 1;
        modalDialogValues.modalDialogValues(getResources().getString(R.string.cerrar_sesion_title),
                getResources().getString(R.string.cerrar_sesion_message));

        DialogFragment dialogFragment = new NoticeDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "NoticeDialogFramgent");
    }

    //dialogOptionDisplay = 2
    private void backButtonDialog(int dialogType){
        dialogOptionDisplay = 2;

        if(dialogType == 1){
            modalDialogValues.modalDialogValues(getResources().getString(R.string.back_button_title1),
                    getResources().getString(R.string.back_button_message1));
        } else if(dialogType == 2){
            modalDialogValues.modalDialogValues(getResources().getString(R.string.back_button_title2),
                    getResources().getString(R.string.back_button_message2));

        }

        DialogFragment dialogFragment = new NoticeDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "NoticeDialogFramgent");
    }

    public void displayHamburguer(){
        utils.displayHamburger(drawerLayout, getSupportActionBar(), toolbar);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(dialogOptionDisplay == 1){

            //Cerramos la sesión si la respuesta del usuario fue "Si"
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent intent = new Intent(MainMenu.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        } else if(dialogOptionDisplay == 2){
            getSupportFragmentManager().popBackStackImmediate();//Cerramos el Fragment
            utils.displayHamburger(drawerLayout, getSupportActionBar(), toolbar);
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    //Método que carga los datos del perfil del Usuario
    private void loadUserProfile(NavigationView navigationView){
        //Accedemos al headerView
        View headerView = navigationView.getHeaderView(0);
        //Obtenemos los TextView correspondientes al NavHeader
        TextView tvUserName = (TextView)headerView.findViewById(R.id.tvUserName);
        TextView tvUserEmail = (TextView)headerView.findViewById(R.id.tvUserEmail);

        actualizarPassword(tvUserName);
        //Obtenemos los datos almacenados en Firebase
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();

        //Solicitamos los datos
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String url = "https://instock-e0d6f-default-rtdb.firebaseio.com/users/" + userId;
        DatabaseReference ref = database.getReferenceFromUrl(url);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                tvUserName.setText(usuario.getNombresUsuario() + " " + usuario.getApellidosUsuario());
                tvUserEmail.setText(usuario.getCorreoUsuario());
                //Asignamos los valores al textView de FragmentInicio
                try{
                    Bundle datos = new Bundle();
                    datos.putString("nombreUsuario", usuario.getNombresUsuario());
                    fInicio.setArguments(datos);
                    View viewFragmentInicio = fInicio.getView();
                    TextView tvSaludo = (TextView)viewFragmentInicio.findViewById(R.id.tvSaludo);
                    tvSaludo.setText("¡Hola " + usuario.getNombresUsuario() + "!\nBienvenido a InStock");
                } catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}