package A2.myapplication;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class TrackCalorieFragment extends Fragment {
    TextView tv_Goal, tv_totalConsumpiton;
    View vCalorie;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vCalorie = inflater.inflate(R.layout.fragment_track_calorie_unit, container, false);
        tv_Goal =vCalorie.findViewById( R.id.tv_goal );
        tv_totalConsumpiton = getActivity().findViewById( R.id.tv_totalcon );
        TextView tv_tv_totalConsumpiton;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String goal = getActivity().getSharedPreferences( "Goal",MODE_PRIVATE ).getString( "theGoal","Please set your Goal" );

                return goal;
            }

            @Override
            protected void onPostExecute(String i){
                tv_Goal.setText( "Goal: "+i +"kcal");
            }

        }.execute(  );


        //-------------------------------------------------------------------------------------------------
        //find consumption
       /* SharedPreferences sp = getActivity().getSharedPreferences( "signUpUser", MODE_PRIVATE);
        String userInfo = sp.getString("user","");

            String id = null;
            try {
                JSONArray userlist = new JSONArray( userInfo );
                 id = userlist.getJSONObject( userlist.length()-1 ).getString( "id" );

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Date date = new Date();
            DateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
            final String sdate = df.format( date );


        final String finalId = id;
        new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... strings) {

                    return  CallingRestFul.findTotalConsumption(Integer.parseInt( finalId ), sdate);

                }
                @Override
                protected void onPostExecute(String i){

                    tv_totalConsumpiton.setText( i );

                }


            }.execute();

*/






        return vCalorie;
    }
}
