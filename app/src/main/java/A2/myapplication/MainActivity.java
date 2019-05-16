package A2.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //---------------------------------------------
    List<HashMap<String, String>> foodListArray;
    SimpleAdapter myListAdapter;
    ListView foodList;

    HashMap<String,String> map = new HashMap<String,String>();
    String[] colHEAD = new String[] {"name","unit","calorie","fat"};
    int[] dataCell = new int[] {R.id.list_food_name,R.id.list_food_servingUnit,R.id.list_foodCalorie,R.id.list_foodFat};
//----------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_daily_diet_unit);

        foodList = this.findViewById(R.id.tv_foodList);
        foodListArray = new ArrayList<HashMap<String, String>>();

        map.put("name", "FIT5046");
        map.put("unit", "Mobile and distributed Computing");
        map.put("calorie","Sem1 2019");
        map.put("fat","123");
        foodListArray.add(map);
        myListAdapter = new SimpleAdapter(this,foodListArray,R.layout.food_list_view,colHEAD,dataCell);
        foodList.setAdapter(myListAdapter);


    }




    private void setUpList(){



    }
}

