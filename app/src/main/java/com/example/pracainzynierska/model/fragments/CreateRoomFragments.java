package com.example.pracainzynierska.model.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pracainzynierska.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateRoomFragments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRoomFragments extends Fragment implements View.OnClickListener {

    Button button, createRoom;
    private TextView textView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateRoomFragments.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateRoomFragments newInstance(String param1, String param2) {
        CreateRoomFragments fragment = new CreateRoomFragments();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public CreateRoomFragments() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_create_room_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.logout);
        button.setOnClickListener(this);
        createRoom = view.findViewById(R.id.createRoom);
        createRoom.setOnClickListener(this);
        textView = view.findViewById(R.id.playerName);
        String displayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        textView.setText(displayName);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                LoginFragment loginFragment = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, loginFragment, "menuFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.createRoom:
                UserProfilesFragment userProfilesFragment = new UserProfilesFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, userProfilesFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}