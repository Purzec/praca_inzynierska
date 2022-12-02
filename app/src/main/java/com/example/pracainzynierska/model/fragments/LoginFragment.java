package com.example.pracainzynierska.model.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pracainzynierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView register, logo;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logo = view.findViewById(R.id.gameName);
        logo.setOnClickListener(this);

        register = view.findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = view.findViewById(R.id.singin);
        signIn.setOnClickListener(this);
        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = view.findViewById(R.id.editTextTextPassword);
        progressBar = view.findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
               // startActivity(new Intent(this, RegistryUser.class));
                RegisterFragment registerFragment = new RegisterFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, registerFragment, "menuFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.singin:
                //zabezpieczenie czy pola są wypełnione dodac
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser userBeforeVerification = FirebaseAuth.getInstance().getCurrentUser();
                    if (userBeforeVerification.isEmailVerified()) {
                   //     startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                        CreateRoomFragments createRoomFragments = new CreateRoomFragments();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView, createRoomFragments, "loginFragment")
                                .addToBackStack(null)
                                .commit();
                    } else {
                       // Toast.makeText(LoginFragment.this, "Aktywuj swoje konto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                //    Toast.makeText(LoginActivity.this, "BAD CREDINTIAL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}