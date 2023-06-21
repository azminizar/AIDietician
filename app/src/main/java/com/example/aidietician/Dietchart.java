package com.example.aidietician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Dietchart extends AppCompatActivity {

    private TextView lunchText, lunchCalText, bfText, bfCalText;
    private TextView dinText, dinCalText,calTargetText;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    private FirebaseFunctions mFunctions;
    private Button recoBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietchart);
        Button recoBtn = findViewById(R.id.buttonRecommend);
        mFunctions = FirebaseFunctions.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        lunchText = findViewById(R.id.lunchName);
        lunchCalText = findViewById(R.id.calorieLunch);
        bfText = findViewById(R.id.breakFastName);
        bfCalText = findViewById(R.id.calorieBreakfast);
        dinText = findViewById(R.id.dinnerName);
        dinCalText = findViewById(R.id.calorieDinner);
        calTargetText=findViewById(R.id.calorieTarget);

        DocumentReference df = fstore.collection("recommendation").document(userID);

        DocumentReference df2=fstore.collection("users").document(userID);
        df2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String,Object> values= documentSnapshot.getData();
                calTargetText.setText("Calorie Target: "+  values.get("cal_target").toString()+" cal");
            }
        });
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String breakfast = documentSnapshot.getString("breakfast");
                String lunch = documentSnapshot.getString("lunch");
                String dinner = documentSnapshot.getString("dinner");
                String bf_cal = documentSnapshot.getString("bf_cal");
                String lun_cal = documentSnapshot.getString("lun_cal");
                String din_cal = documentSnapshot.getString("din_cal");


                lunchText.setText(lunch);
                lunchCalText.setText(lun_cal);
                bfText.setText(breakfast);
                bfCalText.setText(bf_cal);
                dinText.setText(dinner);
                dinCalText.setText(din_cal);

            }
        });
        recoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                df2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String,Object> values= documentSnapshot.getData();
                        calTargetText.setText("Calorie Target: "+  values.get("cal_target").toString()+" cal");
                        String cal_target=values.get("cal_target").toString();
                        String nveg="Non-veg";
                        if(values.get("foodpref").toString().equals("Vegetarian")||values.get("foodpref").toString().equals("Strictly Vegetarian")||values.get("foodpref").toString().equals("Vegan")){
                                nveg="veg";
                        }
                        recDiet(cal_target,nveg).addOnCompleteListener(new OnCompleteListener<RecoReceive>() {
                            @Override
                            public void onComplete(@NonNull Task<RecoReceive> task) {
                                if(task.isSuccessful()){
                                    Log.d("succes", task.getResult().toString());
                                    lunchText.setText(task.getResult().getLun());
                                    lunchCalText.setText((String.valueOf(task.getResult().getLuncal())));
                                    bfText.setText(task.getResult().getBf());
                                    bfCalText.setText((String.valueOf(task.getResult().getBfcal())));
                                    dinText.setText(task.getResult().getDin());
                                    dinCalText.setText((String.valueOf(task.getResult().getDincal())));
                                    Map<String,Object> recoms=new HashMap<>();
                                    recoms.put("breakfast",task.getResult().getBf());
                                    recoms.put("lunch",task.getResult().getLun());
                                    recoms.put("dinner",task.getResult().getDin());
                                    recoms.put("bf_cal",String.valueOf(task.getResult().getBfcal()));
                                    recoms.put("lun_cal",String.valueOf(task.getResult().getLuncal()));
                                    recoms.put("din_cal",String.valueOf(task.getResult().getDincal()));
                                    df.set(recoms, SetOptions.merge());
                                }
                                else {
                                    Toast.makeText(Dietchart.this,"Fail",Toast.LENGTH_SHORT);
                                }
                            }
                        });

                    }
                });


            }
        });

    }
    private Task<RecoReceive> recDiet(String cal_target,String nveg) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("cal_target", cal_target);
        data.put("nveg", nveg);
        data.put("push", true);

        return mFunctions
                .getHttpsCallable("recDiet")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, RecoReceive>() {
                    @Override
                    public RecoReceive then(@NonNull Task<HttpsCallableResult> task) throws Exception {

                        HashMap<String,Object> result = (HashMap <String,Object> ) task.getResult().getData();
                        if(result!=null) {
                            return new RecoReceive((String) result.get("bf"), (String) result.get("lun"), (String) result.get("din"), (int) result.get("bfcal"), (int) result.get("luncal"), (int) result.get("dincal"));
                        }
                        else
                        {
                            return null;
                        }
                    }
                });
    }
}
