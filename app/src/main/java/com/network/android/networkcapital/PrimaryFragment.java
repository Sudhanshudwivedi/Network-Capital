package com.network.android.networkcapital;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import com.googlecode.mp4parser.authoring.Edit;

public class PrimaryFragment extends Fragment implements View.OnClickListener{
    private EditText txt;
    private Button sbtn;

    Button mb,entr,start,busin,fund,proje,mentor,collab,net,secu,market,virtu,product;
    Button next_btn;

    String button_select = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_primary,null);

//        txt = (EditText) v.findViewById(R.id.hlp);
//        sbtn=(Button)v.findViewById(R.id.search);



        mb = (Button) v.findViewById(R.id.mba);
//        entr = (Button) v.findViewById(R.id.entre);
//        start = (Button) v.findViewById(R.id.startup);
        busin = (Button) v.findViewById(R.id.business);
        fund = (Button) v.findViewById(R.id.fund);
        proje = (Button) v.findViewById(R.id.project);
//        mentor = (Button) v.findViewById(R.id.mentor);
        collab = (Button) v.findViewById(R.id.collab);
        net = (Button) v.findViewById(R.id.network);
        secu = (Button) v.findViewById(R.id.security);
        market = (Button) v.findViewById(R.id.marketing);
//        virtu = (Button) v.findViewById(R.id.virtual);
//        product = (Button) v.findViewById(R.id.product);

        next_btn = (Button) v.findViewById(R.id.next);

        mb.setOnClickListener(this);
//        entr.setOnClickListener(this);
//        start.setOnClickListener(this);
        busin.setOnClickListener(this);
        fund.setOnClickListener(this);
        proje.setOnClickListener(this);
//        mentor.setOnClickListener(this);
        collab.setOnClickListener(this);
        net.setOnClickListener(this);
        secu.setOnClickListener(this);
        market.setOnClickListener(this);
//        virtu.setOnClickListener(this);
//        product.setOnClickListener(this);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_select.isEmpty())
                    Toast.makeText(getContext()," Please select an option ",Toast.LENGTH_SHORT).show();
                else {
//                    Toast.makeText(getContext(),button_select,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), HelpUsers.class);
                    intent.putExtra("button_select_text", button_select );
                    startActivity(intent);
                }
            }
        });









//        sbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String s=txt.getText().toString();
//                Intent intent = new Intent(getContext(), HelpUsers.class);
//                intent.putExtra("Search",s);
//
//                startActivity(intent);
//            }
//        });



        return v;

    }


    public void onClick(View v){

        switch (v.getId()){
            case R.id.mba:
                mb.setBackgroundResource(R.drawable.button_pressed);
                mb.setTextColor(Color.WHITE);
//                entr.setBackgroundResource(R.drawable.button_not_pressed);
//                entr.setTextColor(v.getResources().getColor(R.color.basecolor));
//                start.setBackgroundResource(R.drawable.button_not_pressed);
//                start.setTextColor(v.getResources().getColor(R.color.basecolor));
                busin.setBackgroundResource(R.drawable.button_not_pressed);
                busin.setTextColor(v.getResources().getColor(R.color.basecolor));
                fund.setBackgroundResource(R.drawable.button_not_pressed);
                fund.setTextColor(v.getResources().getColor(R.color.basecolor));
                proje.setBackgroundResource(R.drawable.button_not_pressed);
                proje.setTextColor(v.getResources().getColor(R.color.basecolor));
//                mentor.setBackgroundResource(R.drawable.button_not_pressed);
//                mentor.setTextColor(v.getResources().getColor(R.color.basecolor));
                collab.setBackgroundResource(R.drawable.button_not_pressed);
                collab.setTextColor(v.getResources().getColor(R.color.basecolor));
                net.setBackgroundResource(R.drawable.button_not_pressed);
                net.setTextColor(v.getResources().getColor(R.color.basecolor));
                secu.setBackgroundResource(R.drawable.button_not_pressed);
                secu.setTextColor(v.getResources().getColor(R.color.basecolor));
                market.setBackgroundResource(R.drawable.button_not_pressed);
                market.setTextColor(v.getResources().getColor(R.color.basecolor));
//                virtu.setBackgroundResource(R.drawable.button_not_pressed);
//                virtu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                product.setBackgroundResource(R.drawable.button_not_pressed);
//                product.setTextColor(v.getResources().getColor(R.color.basecolor));

                button_select ="MBA Further Studies";

                break;
//            case R.id.entre:
//                entr.setBackgroundResource(R.drawable.button_pressed);
//                entr.setTextColor(Color.WHITE);
//                mb.setBackgroundResource(R.drawable.button_not_pressed);
//                mb.setTextColor(v.getResources().getColor(R.color.basecolor));
//                start.setBackgroundResource(R.drawable.button_not_pressed);
//                start.setTextColor(v.getResources().getColor(R.color.basecolor));
//                busin.setBackgroundResource(R.drawable.button_not_pressed);
//                busin.setTextColor(v.getResources().getColor(R.color.basecolor));
//                fund.setBackgroundResource(R.drawable.button_not_pressed);
//                fund.setTextColor(v.getResources().getColor(R.color.basecolor));
//                proje.setBackgroundResource(R.drawable.button_not_pressed);
//                proje.setTextColor(v.getResources().getColor(R.color.basecolor));
//                mentor.setBackgroundResource(R.drawable.button_not_pressed);
//                mentor.setTextColor(v.getResources().getColor(R.color.basecolor));
//                collab.setBackgroundResource(R.drawable.button_not_pressed);
//                collab.setTextColor(v.getResources().getColor(R.color.basecolor));
//                net.setBackgroundResource(R.drawable.button_not_pressed);
//                net.setTextColor(v.getResources().getColor(R.color.basecolor));
//                secu.setBackgroundResource(R.drawable.button_not_pressed);
//                secu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                market.setBackgroundResource(R.drawable.button_not_pressed);
//                market.setTextColor(v.getResources().getColor(R.color.basecolor));
//                virtu.setBackgroundResource(R.drawable.button_not_pressed);
//                virtu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                product.setBackgroundResource(R.drawable.button_not_pressed);
//                product.setTextColor(v.getResources().getColor(R.color.basecolor));
//                button_select = "Entrepreneurship";
//
//                break;
//            case R.id.startup:
//                start.setBackgroundResource(R.drawable.button_pressed);
//                start.setTextColor(Color.WHITE);
//                mb.setBackgroundResource(R.drawable.button_not_pressed);
//                mb.setTextColor(v.getResources().getColor(R.color.basecolor));
//                entr.setBackgroundResource(R.drawable.button_not_pressed);
//                entr.setTextColor(v.getResources().getColor(R.color.basecolor));
//                busin.setBackgroundResource(R.drawable.button_not_pressed);
//                busin.setTextColor(v.getResources().getColor(R.color.basecolor));
//                fund.setBackgroundResource(R.drawable.button_not_pressed);
//                fund.setTextColor(v.getResources().getColor(R.color.basecolor));
//                proje.setBackgroundResource(R.drawable.button_not_pressed);
//                proje.setTextColor(v.getResources().getColor(R.color.basecolor));
//                mentor.setBackgroundResource(R.drawable.button_not_pressed);
//                mentor.setTextColor(v.getResources().getColor(R.color.basecolor));
//                collab.setBackgroundResource(R.drawable.button_not_pressed);
//                collab.setTextColor(v.getResources().getColor(R.color.basecolor));
//                net.setBackgroundResource(R.drawable.button_not_pressed);
//                net.setTextColor(v.getResources().getColor(R.color.basecolor));
//                secu.setBackgroundResource(R.drawable.button_not_pressed);
//                secu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                market.setBackgroundResource(R.drawable.button_not_pressed);
//                market.setTextColor(v.getResources().getColor(R.color.basecolor));
//                virtu.setBackgroundResource(R.drawable.button_not_pressed);
//                virtu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                product.setBackgroundResource(R.drawable.button_not_pressed);
//                product.setTextColor(v.getResources().getColor(R.color.basecolor));
//                button_select = "Startups";
//                break;
            case R.id.business:
                busin.setBackgroundResource(R.drawable.button_pressed);
                busin.setTextColor(Color.WHITE);
                mb.setBackgroundResource(R.drawable.button_not_pressed);
                mb.setTextColor(v.getResources().getColor(R.color.basecolor));
//                start.setBackgroundResource(R.drawable.button_not_pressed);
//                start.setTextColor(v.getResources().getColor(R.color.basecolor));
//                entr.setBackgroundResource(R.drawable.button_not_pressed);
//                entr.setTextColor(v.getResources().getColor(R.color.basecolor));
                fund.setBackgroundResource(R.drawable.button_not_pressed);
                fund.setTextColor(v.getResources().getColor(R.color.basecolor));
                proje.setBackgroundResource(R.drawable.button_not_pressed);
                proje.setTextColor(v.getResources().getColor(R.color.basecolor));
//                mentor.setBackgroundResource(R.drawable.button_not_pressed);
//                mentor.setTextColor(v.getResources().getColor(R.color.basecolor));
                collab.setBackgroundResource(R.drawable.button_not_pressed);
                collab.setTextColor(v.getResources().getColor(R.color.basecolor));
                net.setBackgroundResource(R.drawable.button_not_pressed);
                net.setTextColor(v.getResources().getColor(R.color.basecolor));
                secu.setBackgroundResource(R.drawable.button_not_pressed);
                secu.setTextColor(v.getResources().getColor(R.color.basecolor));
                market.setBackgroundResource(R.drawable.button_not_pressed);
                market.setTextColor(v.getResources().getColor(R.color.basecolor));
//                virtu.setBackgroundResource(R.drawable.button_not_pressed);
//                virtu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                product.setBackgroundResource(R.drawable.button_not_pressed);
//                product.setTextColor(v.getResources().getColor(R.color.basecolor));
                button_select ="Changing Careers";
                break;
            case R.id.fund:
                fund.setBackgroundResource(R.drawable.button_pressed);
                fund.setTextColor(Color.WHITE);
                mb.setBackgroundResource(R.drawable.button_not_pressed);
                mb.setTextColor(v.getResources().getColor(R.color.basecolor));
//                start.setBackgroundResource(R.drawable.button_not_pressed);
//                start.setTextColor(v.getResources().getColor(R.color.basecolor));
                busin.setBackgroundResource(R.drawable.button_not_pressed);
                busin.setTextColor(v.getResources().getColor(R.color.basecolor));
//                entr.setBackgroundResource(R.drawable.button_not_pressed);
//                entr.setTextColor(v.getResources().getColor(R.color.basecolor));
                proje.setBackgroundResource(R.drawable.button_not_pressed);
                proje.setTextColor(v.getResources().getColor(R.color.basecolor));
//                mentor.setBackgroundResource(R.drawable.button_not_pressed);
//                mentor.setTextColor(v.getResources().getColor(R.color.basecolor));
                collab.setBackgroundResource(R.drawable.button_not_pressed);
                collab.setTextColor(v.getResources().getColor(R.color.basecolor));
                net.setBackgroundResource(R.drawable.button_not_pressed);
                net.setTextColor(v.getResources().getColor(R.color.basecolor));
                secu.setBackgroundResource(R.drawable.button_not_pressed);
                secu.setTextColor(v.getResources().getColor(R.color.basecolor));
                market.setBackgroundResource(R.drawable.button_not_pressed);
                market.setTextColor(v.getResources().getColor(R.color.basecolor));
//                virtu.setBackgroundResource(R.drawable.button_not_pressed);
//                virtu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                product.setBackgroundResource(R.drawable.button_not_pressed);
//                product.setTextColor(v.getResources().getColor(R.color.basecolor));
                button_select ="Resume Advice";
                break;
            case R.id.project:
                proje.setBackgroundResource(R.drawable.button_pressed);
                proje.setTextColor(Color.WHITE);
                mb.setBackgroundResource(R.drawable.button_not_pressed);
                mb.setTextColor(v.getResources().getColor(R.color.basecolor));
//                start.setBackgroundResource(R.drawable.button_not_pressed);
//                start.setTextColor(v.getResources().getColor(R.color.basecolor));
                busin.setBackgroundResource(R.drawable.button_not_pressed);
                busin.setTextColor(v.getResources().getColor(R.color.basecolor));
                fund.setBackgroundResource(R.drawable.button_not_pressed);
                fund.setTextColor(v.getResources().getColor(R.color.basecolor));
//                entr.setBackgroundResource(R.drawable.button_not_pressed);
//                entr.setTextColor(v.getResources().getColor(R.color.basecolor));
//                mentor.setBackgroundResource(R.drawable.button_not_pressed);
//                mentor.setTextColor(v.getResources().getColor(R.color.basecolor));
                collab.setBackgroundResource(R.drawable.button_not_pressed);
                collab.setTextColor(v.getResources().getColor(R.color.basecolor));
                net.setBackgroundResource(R.drawable.button_not_pressed);
                net.setTextColor(v.getResources().getColor(R.color.basecolor));
                secu.setBackgroundResource(R.drawable.button_not_pressed);
                secu.setTextColor(v.getResources().getColor(R.color.basecolor));
                market.setBackgroundResource(R.drawable.button_not_pressed);
                market.setTextColor(v.getResources().getColor(R.color.basecolor));
//                virtu.setBackgroundResource(R.drawable.button_not_pressed);
//                virtu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                product.setBackgroundResource(R.drawable.button_not_pressed);
//                product.setTextColor(v.getResources().getColor(R.color.basecolor));
                button_select ="Finding Right Career";
                break;
//            case R.id.mentor:
//                mentor.setBackgroundResource(R.drawable.button_pressed);
//                mentor.setTextColor(Color.WHITE);
//                mb.setBackgroundResource(R.drawable.button_not_pressed);
//                mb.setTextColor(v.getResources().getColor(R.color.basecolor));
//                start.setBackgroundResource(R.drawable.button_not_pressed);
//                start.setTextColor(v.getResources().getColor(R.color.basecolor));
//                busin.setBackgroundResource(R.drawable.button_not_pressed);
//                busin.setTextColor(v.getResources().getColor(R.color.basecolor));
//                fund.setBackgroundResource(R.drawable.button_not_pressed);
//                fund.setTextColor(v.getResources().getColor(R.color.basecolor));
//                proje.setBackgroundResource(R.drawable.button_not_pressed);
//                proje.setTextColor(v.getResources().getColor(R.color.basecolor));
//                entr.setBackgroundResource(R.drawable.button_not_pressed);
//                entr.setTextColor(v.getResources().getColor(R.color.basecolor));
//                collab.setBackgroundResource(R.drawable.button_not_pressed);
//                collab.setTextColor(v.getResources().getColor(R.color.basecolor));
//                net.setBackgroundResource(R.drawable.button_not_pressed);
//                net.setTextColor(v.getResources().getColor(R.color.basecolor));
//                secu.setBackgroundResource(R.drawable.button_not_pressed);
//                secu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                market.setBackgroundResource(R.drawable.button_not_pressed);
//                market.setTextColor(v.getResources().getColor(R.color.basecolor));
//                virtu.setBackgroundResource(R.drawable.button_not_pressed);
//                virtu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                product.setBackgroundResource(R.drawable.button_not_pressed);
//                product.setTextColor(v.getResources().getColor(R.color.basecolor));
//                button_select = "Mentorship";
//                break;
            case R.id.collab:
                collab.setBackgroundResource(R.drawable.button_pressed);
                collab.setTextColor(Color.WHITE);
                mb.setBackgroundResource(R.drawable.button_not_pressed);
                mb.setTextColor(v.getResources().getColor(R.color.basecolor));
//                start.setBackgroundResource(R.drawable.button_not_pressed);
//                start.setTextColor(v.getResources().getColor(R.color.basecolor));
                busin.setBackgroundResource(R.drawable.button_not_pressed);
                busin.setTextColor(v.getResources().getColor(R.color.basecolor));
                fund.setBackgroundResource(R.drawable.button_not_pressed);
                fund.setTextColor(v.getResources().getColor(R.color.basecolor));
                proje.setBackgroundResource(R.drawable.button_not_pressed);
                proje.setTextColor(v.getResources().getColor(R.color.basecolor));
//                mentor.setBackgroundResource(R.drawable.button_not_pressed);
//                mentor.setTextColor(v.getResources().getColor(R.color.basecolor));
//                entr.setBackgroundResource(R.drawable.button_not_pressed);
//                entr.setTextColor(v.getResources().getColor(R.color.basecolor));
                net.setBackgroundResource(R.drawable.button_not_pressed);
                net.setTextColor(v.getResources().getColor(R.color.basecolor));
                secu.setBackgroundResource(R.drawable.button_not_pressed);
                secu.setTextColor(v.getResources().getColor(R.color.basecolor));
                market.setBackgroundResource(R.drawable.button_not_pressed);
                market.setTextColor(v.getResources().getColor(R.color.basecolor));
//                virtu.setBackgroundResource(R.drawable.button_not_pressed);
//                virtu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                product.setBackgroundResource(R.drawable.button_not_pressed);
//                product.setTextColor(v.getResources().getColor(R.color.basecolor));
                button_select ="Scaling Startups";
                break;
            case R.id.network:
                net.setBackgroundResource(R.drawable.button_pressed);
                net.setTextColor(Color.WHITE);
                mb.setBackgroundResource(R.drawable.button_not_pressed);
                mb.setTextColor(v.getResources().getColor(R.color.basecolor));
//                start.setBackgroundResource(R.drawable.button_not_pressed);
//                start.setTextColor(v.getResources().getColor(R.color.basecolor));
                busin.setBackgroundResource(R.drawable.button_not_pressed);
                busin.setTextColor(v.getResources().getColor(R.color.basecolor));
                fund.setBackgroundResource(R.drawable.button_not_pressed);
                fund.setTextColor(v.getResources().getColor(R.color.basecolor));
                proje.setBackgroundResource(R.drawable.button_not_pressed);
                proje.setTextColor(v.getResources().getColor(R.color.basecolor));
//                mentor.setBackgroundResource(R.drawable.button_not_pressed);
//                mentor.setTextColor(v.getResources().getColor(R.color.basecolor));
                collab.setBackgroundResource(R.drawable.button_not_pressed);
                collab.setTextColor(v.getResources().getColor(R.color.basecolor));
//                entr.setBackgroundResource(R.drawable.button_not_pressed);
//                entr.setTextColor(v.getResources().getColor(R.color.basecolor));
                secu.setBackgroundResource(R.drawable.button_not_pressed);
                secu.setTextColor(v.getResources().getColor(R.color.basecolor));
                market.setBackgroundResource(R.drawable.button_not_pressed);
                market.setTextColor(v.getResources().getColor(R.color.basecolor));
//                virtu.setBackgroundResource(R.drawable.button_not_pressed);
//                virtu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                product.setBackgroundResource(R.drawable.button_not_pressed);
//                product.setTextColor(v.getResources().getColor(R.color.basecolor));
                button_select ="Raising Capitals";
                break;
            case R.id.security:
                secu.setBackgroundResource(R.drawable.button_pressed);
                secu.setTextColor(Color.WHITE);
                mb.setBackgroundResource(R.drawable.button_not_pressed);
                mb.setTextColor(v.getResources().getColor(R.color.basecolor));
//                start.setBackgroundResource(R.drawable.button_not_pressed);
//                start.setTextColor(v.getResources().getColor(R.color.basecolor));
                busin.setBackgroundResource(R.drawable.button_not_pressed);
                busin.setTextColor(v.getResources().getColor(R.color.basecolor));
                fund.setBackgroundResource(R.drawable.button_not_pressed);
                fund.setTextColor(v.getResources().getColor(R.color.basecolor));
                proje.setBackgroundResource(R.drawable.button_not_pressed);
                proje.setTextColor(v.getResources().getColor(R.color.basecolor));
//                mentor.setBackgroundResource(R.drawable.button_not_pressed);
//                mentor.setTextColor(v.getResources().getColor(R.color.basecolor));
                collab.setBackgroundResource(R.drawable.button_not_pressed);
                collab.setTextColor(v.getResources().getColor(R.color.basecolor));
                net.setBackgroundResource(R.drawable.button_not_pressed);
                net.setTextColor(v.getResources().getColor(R.color.basecolor));
//                entr.setBackgroundResource(R.drawable.button_not_pressed);
//                entr.setTextColor(v.getResources().getColor(R.color.basecolor));
                market.setBackgroundResource(R.drawable.button_not_pressed);
                market.setTextColor(v.getResources().getColor(R.color.basecolor));
//                virtu.setBackgroundResource(R.drawable.button_not_pressed);
//                virtu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                product.setBackgroundResource(R.drawable.button_not_pressed);
//                product.setTextColor(v.getResources().getColor(R.color.basecolor));
                button_select ="Leadership Coaching";
                break;
            case R.id.marketing:
                market.setBackgroundResource(R.drawable.button_pressed);
                market.setTextColor(Color.WHITE);
                mb.setBackgroundResource(R.drawable.button_not_pressed);
                mb.setTextColor(v.getResources().getColor(R.color.basecolor));
//                start.setBackgroundResource(R.drawable.button_not_pressed);
//                start.setTextColor(v.getResources().getColor(R.color.basecolor));
                busin.setBackgroundResource(R.drawable.button_not_pressed);
                busin.setTextColor(v.getResources().getColor(R.color.basecolor));
                fund.setBackgroundResource(R.drawable.button_not_pressed);
                fund.setTextColor(v.getResources().getColor(R.color.basecolor));
                proje.setBackgroundResource(R.drawable.button_not_pressed);
                proje.setTextColor(v.getResources().getColor(R.color.basecolor));
//                mentor.setBackgroundResource(R.drawable.button_not_pressed);
//                mentor.setTextColor(v.getResources().getColor(R.color.basecolor));
                collab.setBackgroundResource(R.drawable.button_not_pressed);
                collab.setTextColor(v.getResources().getColor(R.color.basecolor));
                net.setBackgroundResource(R.drawable.button_not_pressed);
                net.setTextColor(v.getResources().getColor(R.color.basecolor));
                secu.setBackgroundResource(R.drawable.button_not_pressed);
                secu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                entr.setBackgroundResource(R.drawable.button_not_pressed);
//                entr.setTextColor(v.getResources().getColor(R.color.basecolor));
//                virtu.setBackgroundResource(R.drawable.button_not_pressed);
//                virtu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                product.setBackgroundResource(R.drawable.button_not_pressed);
//                product.setTextColor(v.getResources().getColor(R.color.basecolor));
                button_select ="Raising Support for Cause";
                break;
//            case R.id.virtual:
//                virtu.setBackgroundResource(R.drawable.button_pressed);
//                virtu.setTextColor(Color.WHITE);
//                mb.setBackgroundResource(R.drawable.button_not_pressed);
//                mb.setTextColor(v.getResources().getColor(R.color.basecolor));
//                start.setBackgroundResource(R.drawable.button_not_pressed);
//                start.setTextColor(v.getResources().getColor(R.color.basecolor));
//                busin.setBackgroundResource(R.drawable.button_not_pressed);
//                busin.setTextColor(v.getResources().getColor(R.color.basecolor));
//                fund.setBackgroundResource(R.drawable.button_not_pressed);
//                fund.setTextColor(v.getResources().getColor(R.color.basecolor));
//                proje.setBackgroundResource(R.drawable.button_not_pressed);
//                proje.setTextColor(v.getResources().getColor(R.color.basecolor));
//                mentor.setBackgroundResource(R.drawable.button_not_pressed);
//                mentor.setTextColor(v.getResources().getColor(R.color.basecolor));
//                collab.setBackgroundResource(R.drawable.button_not_pressed);
//                collab.setTextColor(v.getResources().getColor(R.color.basecolor));
//                net.setBackgroundResource(R.drawable.button_not_pressed);
//                net.setTextColor(v.getResources().getColor(R.color.basecolor));
//                secu.setBackgroundResource(R.drawable.button_not_pressed);
//                secu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                market.setBackgroundResource(R.drawable.button_not_pressed);
//                market.setTextColor(v.getResources().getColor(R.color.basecolor));
//                entr.setBackgroundResource(R.drawable.button_not_pressed);
//                entr.setTextColor(v.getResources().getColor(R.color.basecolor));
//                product.setBackgroundResource(R.drawable.button_not_pressed);
//                product.setTextColor(v.getResources().getColor(R.color.basecolor));
//                button_select = "Virtual Reality";
//                break;
//            case R.id.product:
//                product.setBackgroundResource(R.drawable.button_pressed);
//                product.setTextColor(Color.WHITE);
//                mb.setBackgroundResource(R.drawable.button_not_pressed);
//                mb.setTextColor(v.getResources().getColor(R.color.basecolor));
//                start.setBackgroundResource(R.drawable.button_not_pressed);
//                start.setTextColor(v.getResources().getColor(R.color.basecolor));
//                busin.setBackgroundResource(R.drawable.button_not_pressed);
//                busin.setTextColor(v.getResources().getColor(R.color.basecolor));
//                fund.setBackgroundResource(R.drawable.button_not_pressed);
//                fund.setTextColor(v.getResources().getColor(R.color.basecolor));
//                proje.setBackgroundResource(R.drawable.button_not_pressed);
//                proje.setTextColor(v.getResources().getColor(R.color.basecolor));
//                mentor.setBackgroundResource(R.drawable.button_not_pressed);
//                mentor.setTextColor(v.getResources().getColor(R.color.basecolor));
//                collab.setBackgroundResource(R.drawable.button_not_pressed);
//                collab.setTextColor(v.getResources().getColor(R.color.basecolor));
//                net.setBackgroundResource(R.drawable.button_not_pressed);
//                net.setTextColor(v.getResources().getColor(R.color.basecolor));
//                secu.setBackgroundResource(R.drawable.button_not_pressed);
//                secu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                market.setBackgroundResource(R.drawable.button_not_pressed);
//                market.setTextColor(v.getResources().getColor(R.color.basecolor));
//                virtu.setBackgroundResource(R.drawable.button_not_pressed);
//                virtu.setTextColor(v.getResources().getColor(R.color.basecolor));
//                entr.setBackgroundResource(R.drawable.button_not_pressed);
//                entr.setTextColor(v.getResources().getColor(R.color.basecolor));
//                button_select = "Product Development";
//                break;

        }

    }

}
