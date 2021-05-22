package com.example.instock.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.FragmentManager;

import com.example.instock.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    String currentPhotoPath;

    //Método para crear un archivo de imagen con nombre único
    public File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        System.out.println("Direccion de la foto:" + currentPhotoPath);
        return image;
    }

    //Método para asignar el grado de rotación a la Foto o Imagen según el valor "exifOrientation" proporcionado
    public int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90 || exifOrientation == 90) {
            return 90;
        } else if (exifOrientation ==   ExifInterface.ORIENTATION_ROTATE_180 || exifOrientation == 180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270 || exifOrientation == 270) {
            return 270;
        }
        return 0;
    }

    //Método para obtener el grado de rotación de una imagen seleccionada de la galería
    public int getRotationFromGallery(Context context, Uri imageUri) {
        int result = 0;
        String[] columns = {MediaStore.Images.Media.ORIENTATION};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(imageUri, columns, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int orientationColumnIndex = cursor.getColumnIndex(columns[0]);
                result = cursor.getInt(orientationColumnIndex);
            }
        } catch (Exception e) {
            //Do nothing
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }//End of try-catch block
        return result;
    }

    //Método para rotar y crear imagen en formato Bitmap
    public Bitmap rotateAndBitmapConvert(int rotation, int rotationInDegrees, Bitmap bitmap){
        Matrix matrix = new Matrix();
        if (rotation != 0) {
            matrix.postRotate(rotationInDegrees);
        }

        Bitmap adjustedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        return  adjustedBitmap;
    }

    public byte[] convertBitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); //PNG format is lossless and will ignore the quality setting!
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public Bitmap convertByteArrayToBitmap(byte[] bitmapbytes){
        Bitmap bitmap = null;

        if(bitmapbytes != null){
            bitmap = BitmapFactory.decodeByteArray(bitmapbytes , 0, bitmapbytes.length);
        }

        return bitmap;
    }

    //Colocar el ícono de hamburguesa y desbloquear el menú lateral
    public void displayHamburger(DrawerLayout drawerLayout, ActionBar actionBar, Toolbar toolbar){
        //Desbloqueamos el Menú
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        //Mostramos la hamburguesa
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        //Asignamos las funciones de moestrar y ocultar el Memú
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    //Método para cambiar el título del ActionBar/ToolBar
    public void changeActionBarTitle(String titulo, ActionBar actionBar){
        actionBar.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + titulo + "</font>")));
    }

    //Método que permite comprobar si el valor del Spinner es diferente de "Seleccione"
    public boolean validateSpinner(Context context, ArrayAdapter<String> arrayAdapter,
                                    Spinner spinner, int stringResourceValue){
        boolean r = false;
        String primerValorSpinner = context.getResources().getString(stringResourceValue);
        String valorSeleccionado = spinner.getSelectedItem().toString();

        for(int i = 0; i < arrayAdapter.getCount(); i++){
            if(primerValorSpinner.trim().equals(valorSeleccionado)){
                r = true;
                break;
            }
        }

        return r;
    }

    //Convierte millisegundos a formato de fecha "dd/MM/yyyy"
    public String millisecondsToDDMMYYYY(long millisecondsDate){
        String stringDate = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        stringDate = simpleDateFormat.format(new Date(millisecondsDate));

        return stringDate;
    }

    //Convierte millisegundos a formato de fecha "yyyy-MM-yy"
    public String millisecondsToYYYYMMDD(long millisecondsDate){
        String stringDate = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        stringDate = simpleDateFormat.format(new Date(millisecondsDate));

        return stringDate;
    }


    //Conviete un campo de tipo Date al formato "dd/MM/yyyy"
    public String dateToString(Date date){
        String stringDate = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        stringDate = simpleDateFormat.format(date);

        return stringDate;
    }

    //Conviete un campo de tipo Date al formato "dd/MM/yyyy"
    public String fromYYYYMMDDtoDDMMYYYY(String stringDateOrigin){
        String stringDate = null;

        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inputFormat.parse(stringDateOrigin);
            stringDate = outputFormat.format(date);
        } catch (Exception e){

        }

        return stringDate;
    }

    public String getFechaActual(){
        String fechaActual = null;

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC-6"));//Obtenemos la fecha actual
        Date currentDate = calendar.getTime();
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        fechaActual = simpleDateFormat.format(currentDate);

        return fechaActual;
    }
}
