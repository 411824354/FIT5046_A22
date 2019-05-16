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
import java.util.Arrays;
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
    private String[] colHEAD = new String[] {"result","detail"};
    int[] dataCell = new int[] {R.id.tv_list_items,R.id.tv_list_detail};





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


        myListAdapter = new SimpleAdapter(this,foodListArray,R.layout.search_resualt_list,colHEAD,dataCell);
        foodList.setAdapter(myListAdapter);
    }


    //---------------------------------------------------------------------------
//AsynckTasks
//-----------------------------
//search asynck
    private class SearchAsynckTask extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return  FoodSearchAPI.getFoodDetail( strings[0] );

        }

        @Override
        protected void onPostExecute(String response){
            List<String> list = Arrays.asList(response.split( "," ));
            String name = list.get( 0 );
            String unit = list.get( 1 );
            String calorie = list.get( 2 );
            String fat = list.get( 3 );
            /*String name = response.get( "name" ).toString();
            String unit = response.get( "servingUNT" ).toString();
            String calorie = response.get( "calorieAMO" ).toString();
            String fat = response.get( "fatAMO" ).toString();*/

            String nameIs = "name: " + "";
            String detail = "uint: "+unit+"|"+"calories: "+calorie+"|"+"fat:"+fat;
            map.put("result", nameIs);
            map.put( "detail",detail );

            foodListArray.add(map);


        }
    }

}
