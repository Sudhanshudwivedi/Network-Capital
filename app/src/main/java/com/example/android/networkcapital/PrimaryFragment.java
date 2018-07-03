package com.example.android.networkcapital;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.mp4parser.authoring.Edit;

public class PrimaryFragment extends Fragment {
    private EditText txt;
    private Button sbtn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_primary,null);

        txt = (EditText) v.findViewById(R.id.hlp);
        sbtn=(Button)v.findViewById(R.id.search);
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=txt.getText().toString();
                Intent intent = new Intent(getContext(), HelpUsers.class);
                intent.putExtra("Search",s);

                startActivity(intent);
            }
        });



        return v;

    }

}
