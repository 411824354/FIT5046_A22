package A2.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//---------------------------------------------
        List<HashMap<String, String>> unitListArray;
        SimpleAdapter myListAdapter;
        ListView foodList;
        HashMap<String,String> map = new HashMap<String,String>();
        String[] colHEAD = new String[] {"name","unit","calorie","fat"};
        int[] dataCell = new int[] {R.id.list_food_name,R.id.list_food_servingUnit,R.id.list_foodCalorie,R.id.list_foodFat};
//----------------------------------------------------
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list_view);

        foodList = this.findViewById(R.id.tv_foodList);
        unitListArray = new ArrayList<HashMap<String, String>>();

        map.put("name", "FIT5046");
        map.put("unit", "Mobile and distributed Computing");
        map.put("calorie","Sem1 2019");
        map.put("fat","123");
        unitListArray.add(map);


    }




    private void setUpList(){



    }
}

