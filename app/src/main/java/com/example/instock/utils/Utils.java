package com.example.instock.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
