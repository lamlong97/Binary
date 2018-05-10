package com.ltlstudio.lamlong.binary.Fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ltlstudio.lamlong.binary.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import mehdi.sakout.fancybuttons.FancyButton;

import static android.content.Context.CLIPBOARD_SERVICE;

public class TextConvertFragment extends Fragment {

    private TextView txtResult;
    private MaterialEditText edtText;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    public TextConvertFragment(){

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.fragment_text_convert, container, false);
        // Inflate the layout for this fragment

        txtResult = myFragmentView.findViewById(R.id.txtResult);
        edtText = myFragmentView.findViewById(R.id.edtInputText);
        FancyButton btnCopy = myFragmentView.findViewById(R.id.btnCopy);
        FancyButton btnPaste = myFragmentView.findViewById(R.id.btnPaste);
        FancyButton btnMessage = myFragmentView.findViewById(R.id.btnMessage);

        edtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtResult.setText(Encode(edtText.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        myClipboard = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(CLIPBOARD_SERVICE);
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = txtResult.getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.nothing_to_copy, Toast.LENGTH_SHORT).show();
                }else {
                myClip = ClipData.newPlainText("Binary", text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getActivity().getApplicationContext(), R.string.text_copied, Toast.LENGTH_SHORT).show();}
            }
        });

        btnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myClipboard.hasPrimaryClip()){
                    edtText.setText(myClipboard.getPrimaryClip().getItemAt(0).getText());
                }
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = txtResult.getText().toString();
                myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(myIntent,getString(R.string.send_using)));
            }
        });

        return myFragmentView;
    }


    private String Encode(String input) {
        StringBuilder myString = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            StringBuilder add = new StringBuilder(Integer.toBinaryString(input.charAt(i)));
            while (add.length() != 8) {
                add.insert(0, "0");
            }
            myString.append(add).append(" ");
        }
        return myString.toString();
    }
}
