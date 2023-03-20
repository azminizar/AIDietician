package com.example.aidietician;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.FirestoreGrpc;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView nameView,dietView,activityView,waterView,weightView,sleepView,bmiView;

    FirebaseFirestore fstore;
    FirebaseAuth mAuth;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        String userID;

        mAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        userID=mAuth.getCurrentUser().getUid();
        DocumentReference df = fstore.collection("home").document(userID);

        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> map = document.getData();
                        dietView=v.findViewById(R.id.calText);
                        activityView=v.findViewById(R.id.actText);
                        waterView=v.findViewById(R.id.waterText);
                        weightView=v.findViewById(R.id.weightText);
                        bmiView=v.findViewById(R.id.bmiText);
                        sleepView=v.findViewById(R.id.sleepText);
                        nameView=v.findViewById(R.id.txtViewUser);
                        if (map != null) {
                            String cal=String.valueOf(map.get("cal_intake"));
                            dietView.setText(cal);
                            String sleep = String.valueOf(map.get("sleep"));
                            sleepView.setText(sleep);
                            String activity =String.valueOf(map.get("activity"));
                            activityView.setText(activity);
                            String bmi=String.valueOf(map.get("bmi"));
                            bmiView.setText(bmi);
                            String weight=String.valueOf(map.get("weight"));
                            weightView.setText(weight);
                            String water = String.valueOf(map.get("water"));
                            waterView.setText(water);
                            String name= String.valueOf(map.get("fname"));
                            nameView.setText("Hey "+name+" ...");
                        }
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });





        return v;
    }
}