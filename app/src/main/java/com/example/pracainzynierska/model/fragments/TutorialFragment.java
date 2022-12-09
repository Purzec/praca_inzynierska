package com.example.pracainzynierska.model.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.pracainzynierska.MainActivity3;
import com.example.pracainzynierska.MenuActivity;
import com.example.pracainzynierska.R;
import com.example.pracainzynierska.TutorialActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TutorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TutorialFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ViewGroup viewGroup;
    ConstraintLayout constraintLayout;
    Button goToMenu, practice;
    ImageButton left, right;
    int page = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TutorialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TutorialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TutorialFragment newInstance(String param1, String param2) {
        TutorialFragment fragment = new TutorialFragment();
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
        return inflater.inflate(R.layout.fragment_tutorial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewGroup = view.findViewById(R.id.tutorialFragment);
        goToMenu = view.findViewById(R.id.goToMenu);
        goToMenu.setOnClickListener(this);
        practice = view.findViewById(R.id.practice);
        practice.setOnClickListener(this);
        left = view.findViewById(R.id.leftArrow);
        left.setOnClickListener(this);
        right = view.findViewById(R.id.rightArrow);
        right.setOnClickListener(this);

        left.setVisibility(View.GONE);
        constraintLayout = view.findViewById(R.id.tutorialFragment);
        constraintLayout.setBackground(getResources().getDrawable(R.drawable.background_main_menu));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.goToMenu:
                MenuFragment menuFragment = new MenuFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, menuFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.practice:
                startActivity(new Intent(getContext(), TutorialActivity.class));
                break;
            case R.id.leftArrow:
                page--;
                turnPage(page,view);
                break;

            case R.id.rightArrow:
                page++;
                turnPage(page,view);
                break;
        }

    }

    private void turnPage(int page,View view) {
        switch (page){
            case 0:
                left.setVisibility(View.GONE);
                constraintLayout.setBackground(getResources().getDrawable(R.drawable.page1));
                break;
            case 1:
                left.setVisibility(View.VISIBLE);
                constraintLayout.setBackground(getResources().getDrawable(R.drawable.page2));
                break;
            case 2:
                constraintLayout.setBackground(getResources().getDrawable(R.drawable.page3));
                break;
            case 3:
                constraintLayout.setBackground(getResources().getDrawable(R.drawable.page4));
                right.setVisibility(View.VISIBLE);
                break;
            case 4:
                constraintLayout.setBackground(getResources().getDrawable(R.drawable.page5));
                right.setVisibility(View.GONE);
                break;
        }
    }
}