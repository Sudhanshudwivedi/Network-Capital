package com.network.android.networkcapital;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;




public class SearchFragment extends Fragment {

    MaterialSearchBar search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> fullNameList;
    ArrayList<String> userNameList;
    ArrayList<String> profilePicList;
    ArrayList<String> UIDList;
    SearchAdapter searchAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_search, container, false);




        recyclerView = (RecyclerView) mview.findViewById(R.id.recyclerView);
        search_edit_text=(MaterialSearchBar)mview.findViewById(R.id.searchBar);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        /*
         * Create a array list for each node you want to use
         * */
        fullNameList = new ArrayList<>();
        userNameList = new ArrayList<>();
        profilePicList = new ArrayList<>();
        UIDList = new ArrayList<>();

        search_edit_text.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    /*
                     * Clear the list when editText is empty
                     * */
                    fullNameList.clear();
                    userNameList.clear();
                    profilePicList.clear();
                    UIDList.clear();

                    recyclerView.removeAllViews();
                }
            }
        });
        return mview;
    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                fullNameList.clear();
                userNameList.clear();
                profilePicList.clear();
                UIDList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                /*
                 * Search all users for matching searched string
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String full_name = snapshot.child("name").getValue(String.class);
                    String user_name = snapshot.child("position").getValue(String.class);
                    String profile_pic = snapshot.child("thumb_image").getValue(String.class);

                    if (full_name.toLowerCase().contains(searchedString.toLowerCase())) {
                        fullNameList.add(full_name);
                        userNameList.add(user_name);
                        profilePicList.add(profile_pic);
                        UIDList.add(uid);
                        counter++;
                    } else if (user_name.toLowerCase().contains(searchedString.toLowerCase())) {
                        fullNameList.add(full_name);
                        userNameList.add(user_name);
                        profilePicList.add(profile_pic);
                        UIDList.add(uid);


                        counter++;
                    }

                    /*
                     * Get maximum of 15 searched results only
                     * */
                    if (counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapter(getContext(), fullNameList, userNameList, profilePicList,UIDList);
                recyclerView.setAdapter(searchAdapter);
            }

            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}



