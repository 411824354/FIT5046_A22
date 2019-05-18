package A2.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class Home_drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//----------------------------------------------------------------------------------------------
// files
        TextView tv_time,tv_welcome;


//----------------------------------------------------------------------------------------------
//  MAIN PROGRESS


        TextView tvDate= (TextView) findViewById(R.id.tv_time);
        //实时更新时间（1秒更新一次）
        TimeThread timeThread = new TimeThread(tvDate);//tvDate 是显示时间的控件TextView
        timeThread.start();//启动线程
        getFirstName();


//----------------------------------------------------------------------------------------------
// welcome by first name





//-----------------------------------------------------------------------------------------------
//drawer layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView)
                findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Calorie Tracker");
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new
                MainFragment()).commit();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment nextFragment = null;
        FragmentActivity mapACT = null;
        switch (id) {
            case R.id.nav_Home_unit:
                nextFragment = new MainFragment();
                break;
            case R.id.nav_steps_unit:
                nextFragment = new StepsUnitFragment();
                break;
            case R.id.nav_dailyDiet_unit:
                nextFragment = new DailyDietUnitFragment();
                break;
            case R.id.nav_calorie_unit:
                nextFragment = new TrackCalorieFragment();
                break;
            case R.id.nav_report_unit:
                nextFragment = new ReportFragment();
                break;
            case R.id.nav_map_unit:
                startActivity( new Intent( Home_drawer.this,MapsActivity.class ) );
                return true;

        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                nextFragment).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer( GravityCompat.START);
        return true;
    }

//-----------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------
// methods
//---------------------------------------------------------------------------
// get user first name method

    public void getFirstName(){
        SharedPreferences sp = getSharedPreferences( "signUpInfo",MODE_PRIVATE );
        String firstname = sp.getString( "firstName","noname" );
        TextView tv_welcome = findViewById( R.id.tv_home_welcome );
        tv_welcome.setText( "welcome "+firstname+" !" );
    }

//----------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------
// Asynck Tasks
//------------------------------------------------------------------------------------------------------
//----------------------------------------------
//



}