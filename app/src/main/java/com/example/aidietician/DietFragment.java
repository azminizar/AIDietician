package com.example.aidietician;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
    private EditText edit_text;
    private ListView list_view;

    private Button addMeal;

    private ArrayList<String> arrayList;
    private Dialog dialog;


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

    public static DietFragment newInstance(String message) {
        DietFragment fragment = new DietFragment(message);
        return fragment;
    }


    public void buildMealList() {


        arrayList = new ArrayList<>();

        arrayList.add("Rice");
        arrayList.add("Chapati");
        arrayList.add("Salad");
        arrayList.add("Oats");
        arrayList.add("Salad");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet, container, false);

        nextMeal = view.findViewById(R.id.cardNextMeal);
        dietChart = view.findViewById(R.id.cardDietChart);
        dietHistory = view.findViewById(R.id.cardDietHistory);
        dietRecipe = view.findViewById(R.id.cardDietRecepie);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        selectMeal = view.findViewById(R.id.selectMeal);
        buildMealList();

        selectMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize dialog
                dialog=new Dialog(getContext());

                // set custom dialog
                dialog.setContentView(R.layout.searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(width-50,height/2);
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
                if(TextUtils.isEmpty(selectMeal.getText())){
                    Toast.makeText(getContext(), "Add a meal", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Meal added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}