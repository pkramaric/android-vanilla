package com.flybits.android.samples.vanilla.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flybits.android.samples.vanilla.R;
import com.flybits.android.samples.vanilla.context.BankingData;
import com.flybits.commons.library.api.results.callbacks.BasicResultCallback;
import com.flybits.commons.library.exceptions.FlybitsException;
import com.flybits.context.ContextManager;
import com.flybits.context.models.ContextData;

public class CustomContextDialog extends DialogFragment{

    private EditText edtContext;
    private Spinner spnItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_custom_context, container, false);
        getDialog().setTitle("Uploading Custom Context Data");

        edtContext  = (EditText) rootView.findViewById(R.id.edtEntry);
        spnItems    = (Spinner) rootView.findViewById(R.id.spnItems);
        TextView txtSubmit  =    (TextView) rootView.findViewById(R.id.btnSumbit);
        TextView txtCancel  =    (TextView) rootView.findViewById(R.id.btnCancel);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.context_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnItems.setAdapter(adapter);

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtContext.getText().length() > 0) {
                    BankingData data  = new BankingData();
                    switch (spnItems.getSelectedItemPosition()) {
                        case 0:
                            data.segmentation = edtContext.getText().toString();
                            break;
                        case 1:
                            data.accountBalance = Long.parseLong(edtContext.getText().toString());
                            break;
                        case 2:
                            data.creditCard = edtContext.getText().toString();
                            break;
                    }

                    sendContextData(data);
                }else {
                    Toast.makeText(getContext(), R.string.errorMissingFields, Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    private void sendContextData(ContextData data) {
        data.updateNow(getContext(), new BasicResultCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), R.string.successSent, Toast.LENGTH_SHORT).show();
                dismiss();
            }

            @Override
            public void onException(FlybitsException exception) {

                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
