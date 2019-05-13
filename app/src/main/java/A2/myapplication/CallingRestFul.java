package A2.myapplication;

import android.util.Log;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallingRestFul {

    private static final String BASE_URL =
            "http://10.0.2.2:8080/WebApplication4/webresources/";


//--------------------------------------------------------------------------



//-----------------------------------------------------------------
//  create new credential

    public static void createCredential(User course){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="/qq.users/";
        try {
            Gson gson =new Gson();
            String stringCourseJson=gson.toJson(course);
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
//add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
//Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }



}



