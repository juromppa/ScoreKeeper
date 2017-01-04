package com.gmail.romppainen.matti.juha.scorekeeper;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Juha on 4.1.2017.
 */

public class PlayerActivity extends AppCompatActivity  {

    private DatabaseOperations mydb;
    private Player player;
    private TextView player_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent i = getIntent();
        player = i.getParcelableExtra("Player");

        mydb = new DatabaseOperations(this);

        player_name = (TextView) findViewById(R.id.player_activity_name);
        player_name.setText(player.getName());
        player_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(PlayerActivity.this);
                dialog.setContentView(R.layout.dialog_player_name);

                TextView title = (TextView) dialog.findViewById(R.id.dialog_player_name_title);
                title.setText(R.string.dialog_player_name_title_edit);

                final EditText mEdit = (EditText) dialog.findViewById(R.id.dialog_player_name_edit_text);
                mEdit.setText(player.getName());
                mEdit.requestFocus();
                if(mEdit.requestFocus()) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams
                            .SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }

                Button cancelButton = (Button) dialog.findViewById(R.id.dialog_player_name_button_cancel);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button saveButton = (Button) dialog.findViewById(R.id.dialog_player_name_button_save);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = mEdit.getText().toString();
                        name = name.trim();
                        if (name.length() > 0) {
                            player.setName(name);
                            mydb.updatePlayer(player);
                            player_name.setText(name);
                            dialog.dismiss();
                        } else {
                            TextView help = (TextView) dialog.findViewById(R.id.dialog_player_name_help);
                            help.setVisibility(View.VISIBLE);
                        }
                    }
                });

                dialog.show();
            }
        });

        TextView created = (TextView) findViewById(R.id.player_activity_created);
        created.setText(Utils.ConvertDate(player.getCreated()));

        int last_game = player.getLastGame();
        if (last_game > 0) {
            TextView last_game_text = (TextView) findViewById(R.id.player_activity_last_game);
            last_game_text.setText(Utils.ConvertDate(last_game));
        }
    }
}
