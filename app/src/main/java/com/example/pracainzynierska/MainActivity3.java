package com.example.pracainzynierska;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity3 extends AppCompatActivity {

    Button button;
    String playerName = "";
    String roomName = "";
    String role = "";
    String message = "";
    FirebaseDatabase database;
    DatabaseReference messageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        button = findViewById(R.id.test);
        button.setEnabled(false);

        database = FirebaseDatabase.getInstance();
        Bundle extras = getIntent().getExtras();
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = extras.getString("player");


        if (extras != null) {
            roomName = extras.getString("roomName");
            if (roomName.equals(playerName)) {
                role = "host";
            } else {
                role = "quest";
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send message
                button.setEnabled(false);
                message = role + ":POKED!";
                messageRef.setValue(message);
            }
        });
        //nasłuchiwanie porzed nadchodząca wiadomościa
        messageRef = database.getReference("rooms/" + roomName + "/message");
        message = role + ":POKED!";
        messageRef.setValue(message);
        addRoomEventListener();
    }

    private void addRoomEventListener() {
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //wiadomosc odebrana
                if (role.equals("host")){
                    if (snapshot.getValue(String.class).contains("quest:")){
                        button.setEnabled(true);
                        Toast.makeText(MainActivity3.this, ""+
                                snapshot.getValue(String.class).replace("quest:",""), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (snapshot.getValue(String.class).contains("host:")){
                        button.setEnabled(true);
                        Toast.makeText(MainActivity3.this, ""+
                                snapshot.getValue(String.class).replace("host:",""), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error - rettry
                messageRef.setValue(message);
            }
        });
    }
}