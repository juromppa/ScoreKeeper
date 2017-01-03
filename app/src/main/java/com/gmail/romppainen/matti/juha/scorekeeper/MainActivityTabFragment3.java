package com.gmail.romppainen.matti.juha.scorekeeper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Juha on 28.12.2016.
 */

public class MainActivityTabFragment3 extends Fragment {

    private static DatabaseOperations mydb;
    private static RecyclerView recyclerView;
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
        List<Player> players = mydb.getAllPlayers();
        MyAdapter adapter = new MyAdapter(players);
        recyclerView.setAdapter(adapter);

        if (players.isEmpty()) {
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
                EditText mEdit = (EditText) dialog.findViewById(R.id.dialog_new_player_edit);
                Player player = new Player(mEdit.getText().toString());
                mydb.addPlayer(player);
                List<Player> players = mydb.getAllPlayers();
                MyAdapter adapter = new MyAdapter(players);
                recyclerView.setAdapter(adapter);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<Player> players;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView favorite;
            TextView name;
            TextView created;

            MyViewHolder(View v) {
                super(v);

                favorite = (ImageView) v.findViewById(R.id.player_favorite);
                name = (TextView) v.findViewById(R.id.player_name);
                created = (TextView) v.findViewById(R.id.player_created);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        MyAdapter(List data) {
            players = data;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.players_item,
                    parent, false);
            // set the view's size, margins, paddings and layout parameters
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = recyclerView.getChildLayoutPosition(v);
                    Log.i(TAG, players.get(itemPosition).getName());
                }
            });
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            if (players.get(position).getFavorite()) {
                holder.favorite.setImageResource(R.drawable.ic_favorite);
            } else {
                holder.favorite.setImageResource(R.drawable.ic_favorite_disabled);
            }
            holder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    players.get(position).setFavorite(!players.get(position).getFavorite());
                    mydb.updatePlayer(players.get(position));
                    List<Player> players = mydb.getAllPlayers();
                    MyAdapter adapter = new MyAdapter(players);
                    recyclerView.setAdapter(adapter);
                }
            });

            holder.name.setText(players.get(position).getName());
            Date date = new Date(players.get(position).getCreated() * 1000L);
            SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
            String dateText = df2.format(date);
            holder.created.setText(dateText);
        }

        @Override
        public int getItemCount() {
            return players.size();
        }
    }
}