package com.example.surakchya;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AlertDialog extends AppCompatDialogFragment {
    private AlertDiaglogListner alertDiaglogListner;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Accident Detected")
                .setMessage("Have you been in an accident recently? ")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       alertDiaglogListner.onNoClicked();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*
                        new CountDownTimer(10000,1000){
                            @Override
                            public void onTick(long millisUntilFinished) {
                                Log.d("Alert Box","Ticking...");
                            }

                            @Override
                            public void onFinish() {
                                Log.d("Alert Box","Finished");
                                alertDiaglogListner.onYesClicked();
                            }
                        }.start();
                         */
                        alertDiaglogListner.onYesClicked();
                    }
                });
        return  builder.create();

        //  return super.onCreateDialog(savedInstanceState);
    }

    public interface AlertDiaglogListner{
        void onYesClicked();
        void onNoClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            alertDiaglogListner = (AlertDiaglogListner) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
            + "must implement AlertDialogListener");
        }
    }
}
