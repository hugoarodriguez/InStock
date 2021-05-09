package com.example.instock.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.instock.R;
import com.example.instock.models.ModalDialogValues;

public class NoticeDialogFragment extends DialogFragment {

    ModalDialogValues modalDialogValues = ModalDialogValues.getInstance();

    public interface NoticeDialogListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener listener;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Drawable drawablePositive = getResources().getDrawable(R.drawable.ic_baseline_check_circle_24);
        drawablePositive.setTint(Color.parseColor("#44B846"));
        Drawable drawableNegative = getResources().getDrawable(R.drawable.ic_baseline_cancel_24);
        drawableNegative.setTint(Color.parseColor("#FF0000"));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(modalDialogValues.getTitulo())
                .setMessage(modalDialogValues.getMensaje())
                .setPositiveButton(null, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        listener.onDialogPositiveClick(NoticeDialogFragment.this);
                    }
                }).setPositiveButtonIcon(drawablePositive)
                .setNegativeButton(null, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        listener.onDialogNegativeClick(NoticeDialogFragment.this);
                    }
                }).setNegativeButtonIcon(drawableNegative);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(e.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
