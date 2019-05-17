package A2.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private TextView tv_back,btnSingUp,btnFindPsw;
    private Button btn_login;
    private EditText et_user_name,et_psw;//编辑框

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnSingUp = findViewById( R.id.btn_SignUp );
        btnSingUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( LoginActivity.this,RegisterActivity.class ) );

            }

        } );

        //------------------
        //log in
        btn_login = findViewById( R.id.btn_login );
        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                et_psw = findViewById( R.id.et_psw );
                et_user_name = findViewById( R.id.et_user_name );
                final String password = et_psw.getText().toString().trim();
                final String username = et_user_name.getText().toString().trim();

                //---------------
                //validation
                final String md5Password = MD5Utils.md5( password );

                GetFirstNameAsynckTask getFirstASK = new GetFirstNameAsynckTask();
                getFirstASK.execute(username);

                new AsyncTask<String,Void,String>(){

                    @Override
                    protected String doInBackground(String... Void) {
                        return CallingRestFul.findPassword(username);
                    }

                    @Override
                    protected void onPostExecute(String restfulPassword){


                        try {
                            JSONArray credentialList = new JSONArray( restfulPassword );
                            JSONObject credential = credentialList.getJSONObject( 0 );
                            restfulPassword = credential.getString( "passwordHash" );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(TextUtils.isEmpty(username)){
                            Toast.makeText(LoginActivity.this, "input username", Toast.LENGTH_SHORT).show();
                            return;
                        }else if(TextUtils.isEmpty(password)){
                            Toast.makeText(LoginActivity.this, "input password", Toast.LENGTH_SHORT).show();
                            return;
                            // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                        }else if(md5Password.equals(restfulPassword)){
                            //一致登录成功

                            Toast.makeText(LoginActivity.this, "login successfully", Toast.LENGTH_SHORT).show();

                            //登录成功后关闭此页面进入主页
                            Intent data=new Intent();
                            //datad.putExtra( ); name , value ;
                            data.putExtra("isLogin",true);
                            //RESULT_OK为Activity系统常量，状态码为-1
                            // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                            setResult(RESULT_OK,data);
                            //销毁登录界面
                            LoginActivity.this.finish();
                            //跳转到主界面，登录成功的状态传递到 MainActivity 中
                            startActivity(new Intent(LoginActivity.this, Home_drawer.class));

                            return;
                        }else if((restfulPassword!=null&&!TextUtils.isEmpty(restfulPassword)&&!md5Password.equals(restfulPassword))){
                            Toast.makeText(LoginActivity.this, "input password incorrect", Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            Toast.makeText(LoginActivity.this, "user not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute( );



            }
        } );
    }
    //--------------------------------------
    //find first name
    //--------------------------------------------------------------------------------------------------------
//ASK
    private class GetFirstNameAsynckTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return CallingRestFul.finUserFirstNameByUsername( strings[0] );

        }
        @Override
        protected void onPostExecute(String result){


            try {
                JSONArray ja = new JSONArray( result );
                JSONObject ja0 = ja.getJSONObject(0 );
                JSONObject theUser = ja0.getJSONObject( "userId" );
                String firtName = theUser.getString( "name" );
                SharedPreferences sp = getSharedPreferences( "signUpInfo",MODE_PRIVATE );
                SharedPreferences.Editor edt = sp.edit();
                edt.putString( "firstName",firtName );
                edt.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }


}
