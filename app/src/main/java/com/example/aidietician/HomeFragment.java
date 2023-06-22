package com.example.aidietician;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firestore.v1.Document;
import com.google.firestore.v1.FirestoreGrpc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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
    private TextView nameView, dietView, activityView, waterView, weightView, sleepView, bmiView,wellnessView;
    Dialog dialog;
    private ProgressBar pgBar;

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
    // Constants for the weights
    private static final double BMI_WEIGHT = 0.2;
    private static final double CALORIE_INTAKE_WEIGHT = 0.15;
    private static final double CALORIE_TARGET_WEIGHT = 0.15;
    private static final double WATER_INTAKE_WEIGHT = 0.15;

    // Function to calculate the wellness level score
    public int calculateWellnessLevelScore(double bmi, double calorieIntake, double calorieTarget, int waterIntake) {
        // Normalize BMI to a scale of 0 to 100
        double bmiScore = (bmi - 18.5) / (24.9 - 18.5) * 100;
        bmiScore = Math.max(0, Math.min(bmiScore, 100));

        // Calculate the percentage of calorie target achieved
        if(calorieTarget==0)
            calorieTarget=1500;
        double calorieTargetAchieved = calorieIntake / calorieTarget * 100;
        calorieTargetAchieved = Math.max(0, Math.min(calorieTargetAchieved, 100));

        // Calculate water intake score (8 glasses of water for a perfect score)
        int waterIntakeScore = waterIntake >= 8 ? 100 : waterIntake / 8 * 100;

        // Adjust the weights to reflect the desired emphasis on calorie target achievement
        double bmiWeight = 0.3; // Adjust the weight as needed
        double calorieTargetAchievedWeight = 0.5; // Adjust the weight as needed
        double waterIntakeWeight = 0.2; // Adjust the weight as needed

        // Calculate the weighted average score
        double score = bmiWeight * bmiScore +
                calorieTargetAchievedWeight * calorieTargetAchieved +
                waterIntakeWeight * waterIntakeScore;

        // Round the score and return as an integer
        return (int) Math.round(score);
    }
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

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        CardView cardViewWater = v.findViewById(R.id.cardViewWater);
        waterView = v.findViewById(R.id.waterText);
        sleepView = v.findViewById(R.id.sleepText);
        wellnessView=v.findViewById(R.id.txtviewWellness);
        pgBar=v.findViewById(R.id.pgWellness);
        dialog = new Dialog(getContext());

        cardViewWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogWater();
            }
        });

//        CardView cardViewSleep = v.findViewById(R.id.cardViewSleep);
//        cardViewSleep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openDialogSleep();
//            }
//        });

        userID = mAuth.getCurrentUser().getUid();
        DocumentReference df = fstore.collection("home").document(userID);

        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        int wellsc;
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> map = document.getData();
                        dietView = v.findViewById(R.id.calText);
                        activityView = v.findViewById(R.id.actText);
                        waterView = v.findViewById(R.id.waterText);
                        weightView = v.findViewById(R.id.weightText);
                        bmiView = v.findViewById(R.id.bmiText);
                        sleepView = v.findViewById(R.id.sleepText);
                        nameView = v.findViewById(R.id.txtViewUser);
                        if (map != null) {
                            String cal = String.valueOf(map.get("cal_intake"));
                            dietView.setText(cal);

                            String sleep = String.valueOf(map.get("sleep"));
                            sleepView.setText(sleep);
                            String activity = String.valueOf(map.get("activity"));
                            activityView.setText(activity);
                            String bmi = String.valueOf(map.get("bmi"));
                            bmiView.setText(bmi);

                            String weight = String.valueOf(map.get("weight"));
                            weightView.setText(weight);
                            String water = String.valueOf(map.get("water"));

                            waterView.setText(water);
                            String name = String.valueOf(map.get("fname"));
                            nameView.setText("Hey " + name + " ...");
                            String tar_cal= String.valueOf(map.get("cal_target"));
                            double dcal=Double.valueOf(cal);
                            double dbmi=Double.valueOf(bmi);
                            int dwater=Integer.valueOf(water);
                            double dtar=Double.valueOf(tar_cal);
                            wellsc= calculateWellnessLevelScore(dbmi,dcal,dtar,dwater);
                            wellnessView.setText("Wellness Level:"+String.valueOf(wellsc));
                            pgBar.setProgress(wellsc);
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

//    private void openDialogSleep() {
//        EditText sleeptext, waketext;
//        Button btnOk;
//
//        String userID = mAuth.getCurrentUser().getUid();
//        DocumentReference df = fstore.collection("home").document(userID);
//
//        dialog.setContentView(R.layout.sleep_dialogue);
//        sleeptext = dialog.findViewById(R.id.sleepText);
//        waketext = dialog.findViewById(R.id.waterText);
//
//        String startTimeString = sleeptext.getText().toString();
//        String finishTimeString = waketext.getText().toString();
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//
//        try {
//            Date startTime = format.parse(startTimeString);
//            Date finishTime = format.parse(finishTimeString);
//
//            long durationInMillis = finishTime.getTime() - startTime.getTime();
//            long durationInMinutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis);
//            long durationInHours = TimeUnit.MINUTES.toHours(durationInMinutes);
//
//            // Do something with the duration, e.g., display it in a TextView
//            // textView.setText("Duration: " + durationInMinutes + " minutes");
//            sleepView.setText(String.valueOf(durationInHours));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        dialog.show();
//    }

    private void openDialogWater() {
        TextView textViewWater, textViewPlus, textViewMinus;
        Button buttonOk;
        String userID = mAuth.getCurrentUser().getUid();
        DocumentReference df = fstore.collection("home").document(userID);

        dialog.setContentView(R.layout.water_dialog);
        textViewWater = dialog.findViewById(R.id.editTextWater);
        textViewPlus = dialog.findViewById(R.id.textViewPlus);
        textViewMinus = dialog.findViewById(R.id.textViewMinus);
        buttonOk = dialog.findViewById(R.id.buttonOk);
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> data = document.getData();
                        textViewWater.setText(data.get("water").toString());
                    }
                }
            }
        });

        textViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentVal = textViewWater.getText().toString();
                int value = Integer.parseInt(currentVal);
                value++;
                textViewWater.setText(String.valueOf(value));
            }
        });

        textViewMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentVal = textViewWater.getText().toString();
                int value = Integer.parseInt(currentVal);
                value--;
                textViewWater.setText(String.valueOf(value));
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterView.setText(textViewWater.getText().toString());
                String userID = mAuth.getCurrentUser().getUid();

                Map<String, Object> map = new HashMap<>();
                map.put("water", textViewWater.getText().toString());
                DocumentReference df = fstore.collection("home").document(userID);
                df.set(map, SetOptions.merge());
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
