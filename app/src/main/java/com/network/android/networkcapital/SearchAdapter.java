package com.network.android.networkcapital;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> fullNameList;
    ArrayList<String> userNameList;
    ArrayList<String> profilePicList;
    ArrayList<String> UIDList;
    String uid;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView full_name, user_name;

        public SearchViewHolder(View itemView) {
            super(itemView);
            profileImage = (ImageView) itemView.findViewById(R.id.profileImage);
            full_name = (TextView) itemView.findViewById(R.id.full_name);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
        }
    }

    public SearchAdapter(Context context, ArrayList<String> fullNameList, ArrayList<String> userNameList, ArrayList<String> profilePicList, ArrayList<String> UIDList) {
        this.context = context;
        this.fullNameList = fullNameList;
        this.userNameList = userNameList;
        this.profilePicList = profilePicList;
        this.UIDList = UIDList;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_items, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        holder.full_name.setText(fullNameList.get(position));
        holder.user_name.setText(userNameList.get(position));
        uid=UIDList.get(position);




        Picasso.with(context).load(profilePicList.get(position)).placeholder(R.mipmap.ic_launcher_round).into(holder.profileImage);
        //Glide.with(context).load(profilePicList.get(position)).asBitmap().placeholder(R.mipmap.ic_launcher_round).into(holder.profileImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent profileIntent = new Intent(context,ProfileActivity.class);
                profileIntent.putExtra("user_id", uid);
                context.startActivity(profileIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fullNameList.size();
    }
}