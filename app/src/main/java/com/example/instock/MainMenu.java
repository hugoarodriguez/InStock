package com.example.instock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainMenu extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Fragment fAgregarProductos, fInicio,fProductos;
    //Fragment Ver Ventas
    Fragment fVerVentas;
    //Fragment Ver Resevas
    Fragment fVerReservas;
    // Fragment de agregar_cliente
    Fragment fAgregarCliente;
    // Fragment de gestionar categorias
    Fragment fCategorias;
    FragmentTransaction transaction;
    //FRAGMENT PARA LISTADO DE CLIENTES
    Fragment fClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Instancia del DrawerLayout
        drawerLayout = (DrawerLayout)findViewById(R.id.dl_main_menu);

        //Enlazamos el ActionBar con el DrawerLayout
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.email, R.string.email);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Habilitamos el ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Cambiamos el color del Título del ActionBar
        //getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name) + "</font>")));

        //Instancia de los fragments
        fAgregarProductos = new AgregarProductosFragment();
        fInicio = new InicioFragment();
        //Intancia de fragmento ver ventas
        fVerVentas = new VerVentasFragment();
        //Intancia de fragmento ver reservas
        fVerReservas = new VerReservasFragment();
        //Intancia de fragmento agregar_cliente
        fAgregarCliente = new AgregarClienteFragment();
        //Instancia de fragmento Categorias
        fCategorias = new CategoriasFragment();
        fProductos = new ConsultaProductosFragment() ;
        fClientes = new VerClientesFragment();

        //Agregamos el Fragment que se presentará en la pantalla principal
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, fInicio).commit();
        //Instanciamos el NavigationView
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        //Invocamos el método que enlaza la navegación de los fragments con los items del menú
        navegacionDeFragments();
    }

    //Método para actualizar contraseña
    //Se ejecuta al hacer click en el tvUserName del NavHeader
    public void actualizarPassword(View v){
        Fragment fActualizarPassword = new ActualizarPasswordFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_view, fActualizarPassword);
        transaction.addToBackStack(null);
        drawerLayout.closeDrawers();
        transaction.commit();
        drawerLayout.closeDrawers();

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
            switch (item.getItemId()){

                case R.id.opc_inicio:
                    transaction.replace(R.id.fragment_container_view, fInicio);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;

                case R.id.opc_agg_productos:
                    transaction.replace(R.id.fragment_container_view, fAgregarProductos);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;

                case R.id.opc_productos:
                    transaction.replace(R.id.fragment_container_view, fProductos);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;
                case R.id.opc_ventas:
                    transaction.replace(R.id.fragment_container_view, fVerVentas);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;
                case R.id.opc_reservas:
                    transaction.replace(R.id.fragment_container_view, fVerReservas);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;
                case R.id.opc_categorias:
                    // Caso para abrir el fragmento Categorias
                    transaction.replace(R.id.fragment_container_view, fCategorias);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;
                case R.id.opc_agg_clientes:
                    // Caso para abrir el fragment Agregar_cliente
                    transaction.replace(R.id.fragment_container_view, fAgregarCliente);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;
                case R.id.opc_clientes:
                    // Caso para abrir el fragment Clientes
                    transaction.replace(R.id.fragment_container_view, fClientes);
                    transaction.addToBackStack(null);
                    drawerLayout.closeDrawers();
                    transaction.commit();
                    break;

                case R.id.opc_cerrar_sesion:
                    drawerLayout.closeDrawers();
                    finish();
                    break;
            }
            return true;
        });
    }
}