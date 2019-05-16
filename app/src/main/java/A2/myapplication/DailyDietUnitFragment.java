package A2.myapplication;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DailyDietUnitFragment extends Fragment {
    View vEnterUnit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vEnterUnit = inflater.inflate(R.layout.fragment_daily_diet_unit, container, false);


        init();






        return vEnterUnit;




    }

    public void init(){


    }
}
