package A2.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TrackCalorieFragment extends Fragment {
    View vCalorie;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vCalorie = inflater.inflate(R.layout.fragment_track_calorie_unit, container, false);
        return vCalorie;
    }
}
