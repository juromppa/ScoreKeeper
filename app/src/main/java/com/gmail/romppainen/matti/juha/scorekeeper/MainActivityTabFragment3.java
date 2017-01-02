package com.gmail.romppainen.matti.juha.scorekeeper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Juha on 28.12.2016.
 */

public class MainActivityTabFragment3 extends Fragment {

    private DatabaseOperations mydb;
    private RecyclerView recyclerView;
    private TextView emptyView;

    public MainActivityTabFragment3() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mydb = new DatabaseOperations(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.main_activity_tab_fragment_3, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_main_fragment_3);
        emptyView = (TextView) rootView.findViewById(R.id.rv_main_fragment_3_empty_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList array_list = mydb.getAllPlayers();
        MyAdapter adapter = new MyAdapter(array_list);
        recyclerView.setAdapter(adapter);

        if (array_list.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    public static void NewPlayer(Context context) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_player);

        Button cancelButton = (Button) dialog.findViewById(R.id.dialog_new_player_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button saveButton = (Button) dialog.findViewById(R.id.dialog_new_player_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;
            TextView mTextView2;

            MyViewHolder(View v) {
                super(v);

                mTextView = (TextView) v.findViewById(R.id.label);
                mTextView2 = (TextView) v.findViewById(R.id.selected);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        MyAdapter(ArrayList myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.players_item,
                    parent, false);
            // set the view's size, margins, paddings and layout parameters
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String[] playerData = mydb.getPlayerData(position);
            holder.mTextView.setText(playerData[1]);
            holder.mTextView2.setText(playerData[2]);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}