package A2.myapplication;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CallingRestFul {

    private static final String BASE_URL =
            "http://10.0.2.2:8080/WebApplication4/webresources/";


//--------------------------------------------------------------------------
// find all user
    public static String findAllUsers() {
        final String methodPath = "qq.users/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
    //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
    //open the connection
            conn = (HttpURLConnection) url.openConnection();
    //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
    //set the connection method to GET
            conn.setRequestMethod("GET");
    //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
    //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
    //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }



//-----------------------------------------------------------------
//  create new credential

    public static void postUserIfo(String id, String name, String surname, String email, String height, String weight, String gender, String address, String postcode, String loa, String step_per_mile, String dob){
        //initialise

        int iheight = Integer.parseInt( height );
        int iweight = Integer.parseInt( weight );
        int ipostcode = Integer.parseInt( postcode );
        int iloa = Integer.parseInt( loa );
        int isteps = Integer.parseInt( step_per_mile );

        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="qq.users/";
        try {
            Gson gson =new Gson();

            int iid = idGenerator();
            User user = new User(iid, name, surname, email, iheight, iweight, gender, address, ipostcode, iloa, isteps,dob);
            String stringCourseJson=gson.toJson(user);
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


    public static int idGenerator() {
        String userJsonString = findAllUsers();
        try {
            JSONArray ja = new JSONArray( userJsonString );
            JSONObject jo = ja.getJSONObject( ja.length()-1 );
            String uid = jo.getString( "id" );
            int iid  = Integer.parseInt( uid );
            return iid+1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

//------------------------------------------------------
public static int getCurrentId() {
    String userJsonString = findAllUsers();
    try {
        JSONArray ja = new JSONArray( userJsonString );
        JSONObject jo = ja.getJSONObject( ja.length()-1 );
        String uid = jo.getString( "id" );
        int iid  = Integer.parseInt( uid );
        return iid;

    } catch (JSONException e) {
        e.printStackTrace();
    }
    return 0;
}

//-----------------------------------------------------
//post credential
    public static void postCredential(String username,String hashedpwd,String id, String name, String surname, String email, String height, String weight, String gender, String address, String postcode, String loa, String step_per_mile, String dob){
        //initialise

        int iheight = Integer.parseInt( height );
        int iweight = Integer.parseInt( weight );
        int ipostcode = Integer.parseInt( postcode );
        int iloa = Integer.parseInt( loa );
        int isteps = Integer.parseInt( step_per_mile );
        String signUpDate = getCurrentTimeStr();
        int iid = getCurrentId();
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="qq.credential/";
        try {
            Gson gson =new Gson();
            User userId = new User(iid, name, surname, email, iheight, iweight, gender, address, ipostcode, iloa, isteps,dob);
            Credential credential = new Credential( userId, username, hashedpwd, signUpDate );
            String stringCredentialJson=gson.toJson(credential);
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
            conn.setFixedLengthStreamingMode(stringCredentialJson.getBytes().length);
    //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
    //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCredentialJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }
    private static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
    public static String getCurrentTimeStr(){
        long starttime  = System.currentTimeMillis();
        String datetime = df.format(new Date(starttime));
        return datetime;
    }


//--------------------------------------------------------
//         call user first name

    public static String finUserFirstNameByUsername(String username) {
        final String methodPath = "qq.credential/findByUsername/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + username);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    //--------------------------------------------------------------------------
// find food by category
    public static String findFoodByCategory(String category) {
        final String methodPath = "qq.food/findByCategory/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath+category);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

//-----------------------------------------------------------------------------
// find user password by username
    public static String findPassword(String username) {
        final String methodPath = "qq.credential/findByUsername/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath+username);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

//---------------------------------------------------
    //find by userID
    public static String findUserById(int uid) {
        final String methodPath = "qq.users/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + uid);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

//--------------------------------------------------------
//f

    public static String findUserByName(String username) {
        final String methodPath = "qq.users/findByName/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath + username);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findReport(String URLaddress) {
        URL url;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL+URLaddress);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findIdDateReport(int id,String date) {
        final String methodPath = "qq.report/getDailybalance/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL+methodPath+id+"/"+date);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

//----------------------------------------------------------------
    //tracker calorie
    //total consumption

    public static String findTotalConsumption(int id,String date) {
        final String methodPath = "qq.consumption/totalCaloriesConsumed/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL+methodPath+id+"/"+date);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

}



