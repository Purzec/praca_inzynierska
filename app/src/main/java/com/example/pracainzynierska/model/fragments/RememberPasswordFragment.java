package com.example.pracainzynierska.model.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pracainzynierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RememberPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RememberPasswordFragment extends Fragment {

private EditText emailEditTExt;
private Button resetPasswordButton;
private ProgressBar progressBar;

FirebaseAuth firebaseAuth;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RememberPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RememberPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RememberPasswordFragment newInstance(String param1, String param2) {
        RememberPasswordFragment fragment = new RememberPasswordFragment();
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
        return inflater.inflate(R.layout.fragment_remember_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailEditTExt = view.findViewById(R.id.editTextTextEmailAddress);
        resetPasswordButton = view.findViewById(R.id.resetPassword);
        progressBar = view.findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = emailEditTExt.getText().toString().trim();

        if (email.isEmpty()){
            emailEditTExt.setError("Email jest wymagany");
            emailEditTExt.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditTExt.setError("Nieprawidłowy adres email");
            emailEditTExt.requestFocus();
        }
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               if (task.isSuccessful()){
                   Toast.makeText(getContext(),"SPRAWDŹ SKRZYNKĘ POCZTOWĄ",Toast.LENGTH_LONG).show();

               }else {
                   Toast.makeText(getContext(),"COŚ POSZŁO NIE TAK SPRÓBOJ PONOWNIE",Toast.LENGTH_LONG).show();
               }
            }
        });
    }
}