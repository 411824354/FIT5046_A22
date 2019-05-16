package A2.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class AddNewFood extends AppCompatActivity {
    private TextView tv_back;
    private EditText et_inputWord;
    private List<HashMap<String, String>> foodListArray;
    private SimpleAdapter myListAdapter;
    private ListView foodList;
    private Button btn_search;

    private HashMap<String,String> map = new HashMap<String,String>();
    private String[] colHEAD = new String[] {"name","unit","calorie","fat"};
    int[] dataCell = new int[] {R.id.list_food_name,R.id.list_food_servingUnit,R.id.list_foodCalorie,R.id.list_foodFat};





    //------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_new_food);
        tv_back = findViewById( R.id.tv_back );
        tv_back.setOnClickListener( new TextView.OnClickListener() {


            @Override
            public void onClick(View v) {
                AddNewFood.this.finish();
            }
        } );

        init();
    }

    private void init(){

        btn_search = findViewById( R.id.btn_searchButton );
        btn_search.setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                putTextList();
            }
        } );

    }

    private void putTextList(){
        et_inputWord = findViewById( R.id.et_foodSearch );
        String inputWord = et_inputWord.getText().toString();

        SearchAsynckTask search = new SearchAsynckTask();
        search.execute(inputWord);

        foodListArray = new ArrayList<HashMap<String, String>>();
        foodList = findViewById(R.id.list_searchResualt);


        myListAdapter = new SimpleAdapter(this,foodListArray,R.layout.food_list_view,colHEAD,dataCell);
        foodList.setAdapter(myListAdapter);
    }


    //---------------------------------------------------------------------------
//AsynckTasks
//-----------------------------
//search asynck
    private class SearchAsynckTask extends AsyncTask<String,Void, Dictionary> {

        @Override
        protected Dictionary doInBackground(String... strings) {
            return  FoodSearchAPI.getFoodDetail( strings[0] );

        }

        @Override
        protected void onPostExecute(Dictionary response){

            String name = response.get( "name" ).toString();
            String unit = response.get( "servingUNT" ).toString();
            String calorie = response.get( "calorieAMO" ).toString();
            String fat = response.get( "fatAMO" ).toString();


            map.put("name", name);
            map.put("unit", unit);
            map.put("calorie",calorie);
            map.put("fat",fat);
            foodListArray.add(map);


        }
    }

}
