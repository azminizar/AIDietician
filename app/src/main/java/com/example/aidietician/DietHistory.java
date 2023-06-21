    package com.example.aidietician;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import android.os.Bundle;
    import android.util.Log;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.firestore.CollectionReference;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.DocumentSnapshot;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.QueryDocumentSnapshot;
    import com.google.firebase.firestore.QuerySnapshot;

    import java.text.ParseException;
    import java.util.ArrayList;
    import java.util.Date;

    public class DietHistory extends AppCompatActivity {

        FirebaseAuth mAuth;
        FirebaseFirestore fstore;
        RecyclerView recycleDietHistory;
        Adapterdiethistory adapter;
        ArrayList<Diethistorymodel> diethistorymodels;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_diet_history);
            mAuth=FirebaseAuth.getInstance();
            String userID=mAuth.getCurrentUser().getUid();
            recycleDietHistory = findViewById(R.id.recycleDietHistory);
            fstore = FirebaseFirestore.getInstance();
            CollectionReference df=fstore.collection("dietHistory").document(userID).collection("results");
            recycleDietHistory.setHasFixedSize(true);
            recycleDietHistory.setLayoutManager(new LinearLayoutManager(this));
            diethistorymodels = new ArrayList<>();
            adapter = new Adapterdiethistory(this,diethistorymodels);
            df.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot snapshot:queryDocumentSnapshots) {
                        String meal = snapshot.getString("food");
                        String cal = snapshot.get("cal").toString();
                        String date = snapshot.getString("date");
                        String time = snapshot.getString("time");
                        //Toast.makeText(this,String.valueOf(meal),Toast.LENGTH_SHORT).show();
                        diethistorymodels.add(new Diethistorymodel(date,time,meal,cal));
                        Log.d("Success", "onSuccess: "+meal);

                        recycleDietHistory.setAdapter(adapter);
                    }

                }
            });
            try {
                Log.d("msg", "Adapter success ");

            }
            catch (Exception e)
            {
                Log.d("Exception", String.valueOf(e));
            }


        }
    }
