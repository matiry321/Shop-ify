package com.example.theonlinegrocerystore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class licence extends DialogFragment {
    private Button button;
   /* protected licence(Context context) {
        super(context);
        View view = LayoutInflater.inflate.fr(R.layout.licence_layout,context,false)

    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.licence_layout,null);
        button= view.findViewById(R.id.button);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();

    }
}
