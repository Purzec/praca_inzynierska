package com.example.pracainzynierska.model.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pracainzynierska.GameActivity;
import com.example.pracainzynierska.MainActivity3;
import com.example.pracainzynierska.R;
import com.example.pracainzynierska.model.gameStatus.Board;
import com.example.pracainzynierska.model.gameStatus.Player;
import com.example.pracainzynierska.model.view.HexBoard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomList extends Fragment {

    ListView listView;
    Button button;

    List<String> roomsList;
    String playerName;
    String roomName;

    FirebaseDatabase database;
    DatabaseReference roomRef;
    DatabaseReference boardRef;
    DatabaseReference messRef;

    public RoomList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoomList.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomList newInstance(String param1, String param2) {
        RoomList fragment = new RoomList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_list, container, false);
    }
    Player player = new Player();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //pobierz gracza i przypisz pokoj do jego loginu
        database = FirebaseDatabase.getInstance();
        SharedPreferences preferences = this.getActivity().getSharedPreferences("PREFS", 0);
        player.setNick(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        roomName = player.getNick();

        listView = view.findViewById(R.id.ListView);
        button = view.findViewById(R.id.button2);

        //wszystkie dostepne pokoje
        roomsList = new ArrayList<>();

        //przycisk tworzący pokój
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a room i dodanie siebei jako jednego z graczy w nim
                button.setText("CREATING ROOM");
                button.setEnabled(false);
                messRef = database.getReference("rooms/" + roomName +"/message");
                messRef.setValue("host");
                messRef = database.getReference("rooms/" + roomName +"/lastRound");
                messRef.setValue(false);
                roomName = player.getNick();
                roomRef = database.getReference("rooms/" + roomName + "/p1info");
                addRoomEventListener();
                player.setId(1);
                player.setEtap(1);
                roomRef.setValue(player);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //dolacz do istniejacego pokoju jako gracz 2
                roomName = roomsList.get(i);
                messRef = database.getReference("rooms/" + roomName +"/message");
                messRef.setValue("quest");
                roomRef = database.getReference("rooms/" + roomName + "/p2info");
                addRoomEventListener();
                player.setId(2);
                player.setEtap(1);
                roomRef.setValue(player);
            }
        });

        //show if new room is avaliable
        addRoomsEventListener();
    }

    private void addRoomEventListener() {
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //join the room
                button.setText("CREATE ROOM");
                button.setEnabled(true);
                // dołaczenie do pokoju
                Intent intent = new Intent(getContext(), GameActivity.class);
                intent.putExtra("roomName", roomName);
                intent.putExtra("player",player.getNick());
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                button.setText("CREATE ROOM");
                button.setEnabled(true);
                Toast.makeText(getContext(), "ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addRoomsEventListener() {
        roomRef = database.getReference("rooms");
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //show list of rooms
                roomsList.clear();
                Iterable<DataSnapshot> rooms = dataSnapshot.getChildren();
                for (DataSnapshot snapshot : rooms) {
                    roomsList.add(snapshot.getKey());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, roomsList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//nothing
            }
        });
    }
}