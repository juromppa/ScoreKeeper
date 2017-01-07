package com.gmail.romppainen.matti.juha.scorekeeper;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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

        setHasOptionsMenu(true);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_new_player:
                DialogNewPlayer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void DialogNewPlayer() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_player_name);

        final TextView title = (TextView) dialog.findViewById(R.id.dialog_player_name_title);
        title.setText(R.string.dialog_player_name_title_new);

        final EditText mEdit = (EditText) dialog.findViewById(R.id.dialog_player_name_edit_text);
        mEdit.requestFocus();
        if(mEdit.requestFocus()) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams
                    .SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        Button cancelButton = (Button) dialog.findViewById(R.id.dialog_player_name_button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        Button saveButton = (Button) dialog.findViewById(R.id.dialog_player_name_button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEdit.getText().toString();
                name = name.trim();
                if (name.length() > 0) {
                    Player player = new Player();
                    player.setName(name);
                    mydb.addPlayer(player);
                    List<Player> players = mydb.getAllPlayers();
                    MyAdapter adapter = new MyAdapter(players);
                    recyclerView.setAdapter(adapter);
                    dialog.dismiss();
                } else {
                    TextView help = (TextView) dialog.findViewById(R.id.dialog_player_name_help);
                    help.setVisibility(View.VISIBLE);
                }
            }
        });

        dialog.show();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
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
                    Intent i = new Intent(getActivity(), PlayerActivity.class);
                    i.putExtra("Player", players.get(itemPosition));
                    startActivity(i);
                }
            });
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            if (players.get(position).getFavorite()) {
                holder.favorite.setImageResource(R.drawable.ic_favorite);
            } else {
                holder.favorite.setImageResource(R.drawable.ic_favorite_disabled);
            }
            holder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    players.get(holder.getAdapterPosition()).setFavorite(!players.get(holder
                            .getAdapterPosition()).getFavorite());
                    mydb.updatePlayer(players.get(holder.getAdapterPosition()));
                    List<Player> players = mydb.getAllPlayers();
                    MyAdapter adapter = new MyAdapter(players);
                    recyclerView.setAdapter(adapter);
                }
            });

            holder.name.setText(players.get(position).getName());
            holder.created.setText(Utils.ConvertDate(players.get(position).getCreated()));
        }

        @Override
        public int getItemCount() {
            return players.size();
        }
    }
}