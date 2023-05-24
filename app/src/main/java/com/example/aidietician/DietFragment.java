package com.example.aidietician;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DietFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CardView nextMeal;
    private CardView dietChart;
    private CardView dietHistory;
    private CardView dietRecipe;

    private TextView selectMeal;
    private TextView currentCal;
    private TextView recoFood;
    private TextView targetCal,maxIntake;

    private Button addMeal;

    private ArrayList<String> arrayList;
    private Dialog dialog;


    FirebaseFirestore fstore;
    FirebaseAuth mAuth;

    public DietFragment() {
        // Required empty public constructor
    }
    //
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DietFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static DietFragment newInstance(String param1, String param2) {
//        DietFragment fragment = new DietFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        DietFragment dietFragment = new DietFragment();
//        return fragment;
//    }
    private DietFragment(String message) {
//        this.messagege = message;
    }
    private String calG;
    public static DietFragment newInstance(String message) {
        DietFragment fragment = new DietFragment(message);
        return fragment;
    }

    public void buildMealList() {

        fstore=FirebaseFirestore.getInstance();

        arrayList = new ArrayList<>();
        CollectionReference recRef=fstore.collection("food");
        recRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    String food=documentSnapshot.getString("name");
                    String cal=documentSnapshot.getString("cal");

                    arrayList.add(food+"("+cal+" Kcal)");

                }
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fstore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        String userID=mAuth.getCurrentUser().getUid();




        View view = inflater.inflate(R.layout.fragment_diet, container, false);

        DocumentReference homeRef=fstore.collection("home").document(userID);
        DocumentReference recomRef = fstore.collection("recommendation").document(userID);

        recoFood=view.findViewById(R.id.recomFoodText);
        nextMeal = view.findViewById(R.id.cardNextMeal);
        dietChart = view.findViewById(R.id.cardDietChart);
        dietHistory = view.findViewById(R.id.cardDietHistory);
        dietRecipe = view.findViewById(R.id.cardDietRecepie);
        currentCal=view.findViewById(R.id.curCal);
        targetCal=view.findViewById(R.id.targetCalo);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        selectMeal = view.findViewById(R.id.selectMeal);
        buildMealList();
        homeRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentCal.setText("Current Calorie intake: "+documentSnapshot.get("cal_intake").toString());
            }
        });
        recomRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Calendar calendar = Calendar.getInstance();
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

                if (hourOfDay < 12) {
                    recoFood.setText(documentSnapshot.get("breakfast").toString());
                    targetCal.setText("Calories: "+documentSnapshot.get("bf_cal").toString());
                } else if (hourOfDay >= 12 && hourOfDay < 16) {
                    recoFood.setText(documentSnapshot.get("lunch").toString());
                    targetCal.setText("Calories: "+documentSnapshot.get("lun_cal").toString());
                } else {
                    recoFood.setText(documentSnapshot.get("dinner").toString());
                    targetCal.setText("Calories: "+documentSnapshot.get("din_cal").toString());
                }
            }
        });

        selectMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize dialog
                dialog=new Dialog(getContext());

                // set custom dialog
                dialog.setContentView(R.layout.searchable_spinner);
                dialog.getWindow().setLayout((int) (width/1.5),height/2);
                dialog.show();

                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                // Initialize array adapter
                ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,arrayList);

                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectMeal.setText(adapter.getItem(position));
                        dialog.dismiss();
                    }
                });
            }
        });
        dietChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),Dietchart.class);
                startActivity(intent1);
            }
        });

        addMeal = view.findViewById(R.id.btnAddMeal);
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectMeal.getText().toString().equals("Select meal")){
                    Toast.makeText(getContext(), "Add a meal", Toast.LENGTH_SHORT).show();


                }
                else {
                    int cal,nos=1;
                    String scal;
                    String meal=selectMeal.getText().toString();
                    Calendar calendar= Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int year = calendar.get(Calendar.YEAR);
                    String currentDate = day + "" + month + "" + year;
                    String saveDate = day + "-" + month + "-" + year;
                    int hours = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutes = calendar.get(Calendar.MINUTE);
                    int seconds = calendar.get(Calendar.SECOND);
                    int mill=calendar.get(calendar.MILLISECOND);
                    String currentTime = hours +"" +minutes + ""+seconds+""+mill;
                    String savTime=hours +":" +minutes + ":"+seconds;
                    CollectionReference dietRef= fstore.collection("dietHistory").document(userID).collection("results");
                    int startIndex = meal.indexOf("(");
                    int endIndex = meal.indexOf(")");
                    endIndex=endIndex-5;

                    if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
                        scal = meal.substring(startIndex + 1, endIndex);
                        cal = Integer.parseInt(scal);

                        Map<String, Object> map = new HashMap<>();
                        map.put("date",saveDate);
                        map.put("time",savTime);
                        map.put("food", meal);
                        map.put("cal", cal);
                        map.put("nos", nos);
                        homeRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    String hcal = documentSnapshot.getString("cal_intake");
                                    int hocal = Integer.parseInt(hcal);
                                    hocal = hocal + nos * cal;
                                    hcal = String.valueOf(hocal);
                                    Map<String, Object> map1 = new HashMap<>();
                                    map1.put("cal_intake", hcal);
                                    homeRef.set(map1, SetOptions.merge());
                                    currentCal.setText("Current Calorie intake: "+hcal
                                    );

                                }
                            }
                        });
                        dietRef.add(map);
                        Toast.makeText(getContext(), String.valueOf(cal) ,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dietHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DietHistory.class);
                startActivity(intent);
            }
        });

        return view;
    }
}