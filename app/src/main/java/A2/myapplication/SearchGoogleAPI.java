package A2.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SearchGoogleAPI {
    private static final String API_KEY = "&sort=n&max=25&offset=0&api_key=CtBfz5VnJPdTnbCXaAmxTrkV2Ubjmcftcqtpm05o";
    public static String search(String keyword) {
        keyword = keyword.trim().replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";


        try {
            url = new URL("https://api.nal.usda.gov/ndb/search/?format=json&q="+
                    keyword + API_KEY);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }



    public static String getSnippet(String result){
        String snippet = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                snippet =jsonArray.getJSONObject(0).getString("snippet");
            }
        }catch (Exception e){
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }

    public static String getNdno(String str) throws JSONException {
        JSONObject resualt = new JSONObject( str );
        JSONObject init  = resualt.getJSONObject( "list" );
        JSONArray firstItem = init.getJSONArray( "item" );
        JSONObject offset0 = firstItem.getJSONObject( 0 );
        String ndbno = offset0.getString( "ndbno" );
        return ndbno;

    }

}
