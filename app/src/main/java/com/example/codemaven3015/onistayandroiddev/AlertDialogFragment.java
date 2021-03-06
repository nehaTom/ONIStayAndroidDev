package com.example.codemaven3015.onistayandroiddev;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.VolleyError;
public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
       // final AlertDialogFragment.AlertDialogCallback callback;
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.error_title)) //change all the text messages to String resources. Steps: 1. Alt+Enter. 2. give the resource a name 3. then hit ESC key when CONTEXT is highlighted in red.
                .setMessage(context.getString(R.string.error_message))
                // .setPositiveButton(context.getString(R.string.error_ok_button_text), null);
                .setPositiveButton(context.getString(R.string.error_ok_button_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //callback.callbackFunction();
                        Intent i = new Intent(getContext(), Home.class);
                        startActivity(i);
                    }
                });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    public interface AlertDialogCallback {
        public void callbackFunction();
    }
}