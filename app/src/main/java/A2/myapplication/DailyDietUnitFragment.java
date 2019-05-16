package A2.myapplication;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DailyDietUnitFragment extends Fragment {
    //---------------------------------------------
    List<HashMap<String, String>> foodListArray;
    SimpleAdapter myListAdapter;
    ListView foodList;

    HashMap<String,String> map = new HashMap<String,String>();
    String[] colHEAD = new String[] {"name","unit","calorie","fat"};
    int[] dataCell = new int[] {R.id.list_food_name,R.id.list_food_servingUnit,R.id.list_foodCalorie,R.id.list_foodFat};

    String category;
    Button searchFood;

    //----------------------------------------------------
    View vEnterUnit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle
            savedInstanceState) {
        vEnterUnit = inflater.inflate(R.layout.fragment_daily_diet_unit, container, false);
        searchFood = vEnterUnit.findViewById( R.id.btn_searchFood );
        searchFood.setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddNewFood.class);
                startActivity(intent);

            }
        } );

        init();

        return vEnterUnit;




    }

    public void init(){



        foodListArray = new ArrayList<HashMap<String, String>>();
        foodList = vEnterUnit.findViewById(R.id.tv_foodList);

        map.put("name", "FIT5046");
        map.put("unit", "Mobile and distributed Computing");
        map.put("calorie","Sem1 2019");
        map.put("fat","123");
        foodListArray.add(map);
        myListAdapter = new SimpleAdapter(getActivity(),foodListArray,R.layout.food_list_view,colHEAD,dataCell);
        foodList.setAdapter(myListAdapter);


        //spinner
        final Spinner sp_loa = vEnterUnit.findViewById( R.id.sp_foodCate_daily );
        List<String> loaList = new ArrayList<String>(  );

        loaList.add( "Drink" );
        loaList.add( "Meal" );
        loaList.add( "Meat" );
        loaList.add( "Snack" );
        loaList.add( "Bread" );
        loaList.add( "Cake" );
        loaList.add( "Fruit" );
        loaList.add( "Vegies" );
        loaList.add( "Other" );



        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity() , android.R.layout.simple_spinner_item, loaList);




        sp_loa.setAdapter( spinnerAdapter );

        sp_loa.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedCategory = parent.getItemAtPosition( position ).toString();
                if(selectedCategory != null){

                    category = selectedCategory;
                    Toast.makeText(parent.getContext(), "level of activity selected is " + selectedCategory,
                            Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});





    }
//--------------------------------------------------------------------------
//put text view

    


}
