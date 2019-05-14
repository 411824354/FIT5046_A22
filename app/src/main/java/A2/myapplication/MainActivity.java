package A2.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);
        final EditText editText=(EditText) findViewById(R.id.et_keyword) ;
        Button btnSearch = (Button) findViewById(R.id.btnFind);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = editText.getText().toString();
                SearchAsyncTask searchAsyncTask=new SearchAsyncTask();
                searchAsyncTask.execute(keyword);
            }
        });
    }
    private class SearchAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String resualt = SearchGoogleAPI.search(params[0]);
            try {
                return SearchGoogleAPI.getNdno( resualt );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "not found";
        }
        @Override
        protected void onPostExecute(String result) {
            TextView tv= (TextView) findViewById(R.id.tv_searchResualt);
           // tv.setText(SearchGoogleAPI.getSnippet(result));
            tv.setText( result );

        }
    }
}
