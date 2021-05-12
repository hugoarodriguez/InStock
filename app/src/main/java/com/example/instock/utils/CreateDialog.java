package com.example.instock.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.instock.R;
import com.example.instock.models.ModalDialogValues;

public class CreateDialog {

    ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AlertDialog.Builder dialog(Context context){

        Drawable drawablePositive = context.getDrawable(R.drawable.ic_baseline_check_circle_24);
        drawablePositive.setTint(Color.parseColor("#44B846"));
        Drawable drawableNegative = context.getDrawable(R.drawable.ic_baseline_cancel_24);
        drawableNegative.setTint(Color.parseColor("#FF0000"));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(modalDialogValues.getTitulo())
                .setMessage(modalDialogValues.getMensaje())
                .setPositiveButtonIcon(drawablePositive)
                .setNegativeButtonIcon(drawableNegative);

        return builder;
    }
}