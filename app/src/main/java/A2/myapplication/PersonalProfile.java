package A2.myapplication;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PersonalProfile extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView tv_main_title;//标题
    private TextView tv_back,btnSingUp,btnFindPsw;//返回键,显示的注册，找回密码
    private Button btn_submit;
    private EditText et_dob;
    private String email,dob,gender,address,firstname,surname;
    private int height,loa,postCode,stepPer,weight;

    private EditText et_address,et_email,et_height,et_weight,et_stepPer,et_postCode,et_surname,et_firstname;//编辑框
    private Spinner sp_loa;
    private RadioButton rb_gd_m,rb_gd_f;
    private RadioGroup rg_gender;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional_profile);

        init();

    }



    private void init(){
        tv_main_title = findViewById( R.id.tv_main_title );
        tv_back = findViewById( R.id.tv_back );
        et_email = findViewById( R.id.et_email );
        et_height = findViewById( R.id.et_height );
        et_weight = findViewById( R.id.et_weight );
        et_dob = findViewById( R.id.et_dob );
        rb_gd_f = findViewById( R.id.rb_gd_f );
        rb_gd_m = findViewById( R.id.rb_gd_m );
        rg_gender = findViewById( R.id.rg_gender );
        et_stepPer = findViewById( R.id.et_step_mile );
        et_address = findViewById( R.id.et_address );
        et_stepPer = findViewById( R.id.et_step_mile );
        et_postCode = findViewById( R.id.et_postCode );
        et_firstname = findViewById( R.id.et_firstName );
        et_surname = findViewById( R.id.et_lastName );
 //----------------------------------------------------------------------------------
 //submit button
//register
    btn_submit.setOnClickListener( new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            String username = readUsername();
            String pasword = readPsw();
            email = et_email.getText().toString();
            height = Integer.parseInt(  et_height.getText().toString());
            weight = Integer.parseInt( et_weight.getText().toString() );
            address = et_address.getText().toString();
            dob = et_dob.getText().toString();

            stepPer = Integer.parseInt( et_stepPer.getText().toString() );
            postCode = Integer.parseInt( et_postCode.getText().toString() );
            int userid = 1;
            firstname =et_firstname.getText().toString();
            surname = et_firstname.getText().toString();


            User user = new User(userid, firstname, surname, email, height, weight, gender, address, postCode, loa, stepPer,dob);
            UserPostAsynckTask postUser = new UserPostAsynckTask();
            postUser.execute( user );


        }
    } );



//-----------------------------------------------------------------------------------
        //spinner
        final Spinner sp_loa = findViewById( R.id.sp_loa );
        List<String> loaList = new ArrayList<String>(  );
        for (int i = 1; i<=5;i++){
            String j = String.valueOf( i );
            loaList.add( j );
        }

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item, loaList);




        sp_loa.setAdapter( spinnerAdapter );

        sp_loa.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedLevel = parent.getItemAtPosition( position ).toString();
                if(selectedLevel != null){

                    loa = Integer.parseInt(selectedLevel);
                    Toast.makeText(parent.getContext(), "level of activity selected is " + selectedLevel,
                            Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});

    //-------------------------------------------------------
    // INT ICON SIZE
        Drawable drawable_hight=getResources().getDrawable(R.drawable.ic_hight);
        drawable_hight.setBounds(0,0,72,72);//第一0是距左边距离，第二0是距上边距离，30、35分别是长宽
        et_height.setCompoundDrawables(drawable_hight,null,null,null);

        Drawable drawable_weight=getResources().getDrawable(R.drawable.ic_weight1);
        drawable_weight.setBounds(0,0,72,72);//第一0是距左边距离，第二0是距上边距离，30、35分别是长宽
        et_weight.setCompoundDrawables(drawable_weight,null,null,null);
         //--------------------------------------
        //DATETIME PICKER
        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");

            }
        });

        //------------------------------------------------
        //RadioButtons
        rb_gd_f.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "F";
            }
        } );

        rb_gd_m.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "M";
            }
        } );


    }



    @Override
    public void onDateSet(DatePicker view,int year,int month, int dayOfMonth){
        Calendar c = Calendar.getInstance();
        c.set( Calendar.YEAR,year );
        c.set( Calendar.MONTH,month );
        c.set( Calendar.DAY_OF_MONTH,dayOfMonth );
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format( c.getTime() );
        et_dob.setText( currentDateString );


    }


//-----------------------------------------------------------------------------------------------------
    private String readPsw(){
        //添加restful接口
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp=getSharedPreferences("signUpUser", MODE_PRIVATE);

        //sp.getString() userName, "";
        return sp.getString("password" , "");
}

    private String readUsername(){
        SharedPreferences sp=getSharedPreferences( "signUpUser",MODE_PRIVATE );
        return sp.getString( "username","" );
    }


//------------------------------------------------------------------------------------------------
// AsynckTasks
//------------------------------------------------------------------------------------------------
// UserPostAsynckTask
    private class UserPostAsynckTask extends AsyncTask<User, Void, String>{

    @Override
    protected String doInBackground(User... users) {
       CallingRestFul.createCredential(users[0]);
        return null;
    }
    @Override
    protected void onPostExecute(String response) {
        Toast.makeText(PersonalProfile.this, "create successfully", Toast.LENGTH_SHORT).show();
    }

}




}

