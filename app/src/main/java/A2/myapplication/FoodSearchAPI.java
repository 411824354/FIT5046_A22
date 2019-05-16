package A2.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Dictionary;
import java.util.Scanner;

public class FoodSearchAPI {

    public static String search(String keyword) {
        String API_KEY = "&sort=n&max=25&offset=0&api_key=CtBfz5VnJPdTnbCXaAmxTrkV2Ubjmcftcqtpm05o";
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



    private String getSnippet(String result){
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

    public static String getNdno(String str) {
        String ndbno = null;
        try {
        JSONObject resualt = new JSONObject( str );
        JSONObject init  = resualt.getJSONObject( "list" );
        JSONArray firstItem = init.getJSONArray( "item" );
        JSONObject offset0 = firstItem.getJSONObject( 0 );
        ndbno = offset0.getString( "ndbno" );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ndbno;

    }

    public Dictionary getFoodDetail(String str){
        String ndbno = getNdno( str );
        Dictionary result = null;
        String foodName = "";
        int fatAMO = 0;
        int calorieAMO = 0;
        String servingUNT = "";
        int servingAMO = 0;
        String apiURl = "https://api.nal.usda.gov/ndb/V2/reports?ndbno=";
        URL url = null;
        String type = "&type=f&format=json&api_key=";
        String apiKey = "CtBfz5VnJPdTnbCXaAmxTrkV2Ubjmcftcqtpm05o";
        HttpURLConnection connection = null;
        String textResult ="";


        try {
            url = new URL(apiURl+ndbno+type+apiKey);
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

        try {
            JSONObject info = new JSONObject( textResult );
            JSONArray foods = info.getJSONArray( "foods" );
            JSONObject food = foods.getJSONObject( 0 );
            JSONArray nutrients = food.getJSONArray( "nutrients" );

            servingUNT = getUnit(food);
            servingAMO = getServingAMO(nutrients);
            foodName = getFoodName(food);
            calorieAMO = getCalorieAMO(nutrients);
            fatAMO =  getFatAMO(nutrients);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        result.put( "name",foodName );
        result.put( "calorieAMO",calorieAMO );
        result.put( "servingUNT",servingUNT );
        result.put( "servingAMO",servingAMO );
        result.put( "fatAMO",fatAMO );

        return result;


    }
//---------------------------------------------------------
// extract food detail methods

    private String getFoodName(JSONObject job){
        try {

            JSONObject desc = job.getJSONObject( "desc" );
            String name = desc.getString( "name" );
            return name;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


    private int getCalorieAMO(JSONArray nutrients){

        int result = 0;

        try {
            JSONObject energy = nutrients.getJSONObject( 0 );
            JSONArray measures = energy.getJSONArray( "measures" );
            JSONObject measure = measures.getJSONObject( 0 );
            String calories = measure.getString( "value" );
            result = Integer.parseInt( calories );
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }

            return 0;

    }

    private String getUnit(JSONObject job){
        try {

            JSONObject desc = job.getJSONObject( "desc" );
            String unit = desc.getString( "ru" );
            return unit;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "not found";
    }


    private int getFatAMO(JSONArray nutrients){

        int result = 0;

        try {
            JSONObject energy = nutrients.getJSONObject( 1 );
            JSONArray measures = energy.getJSONArray( "measures" );
            JSONObject measure = measures.getJSONObject( 0 );
            String calories = measure.getString( "value" );
            result = Integer.parseInt( calories );
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;

    }


    private int getServingAMO(JSONArray nutrients){

        int result = 0;

        try {
            JSONObject energy = nutrients.getJSONObject( 1 );
            JSONArray measures = energy.getJSONArray( "measures" );
            JSONObject measure = measures.getJSONObject( 0 );
            String calories = measure.getString( "eqv" );
            result = Integer.parseInt( calories );
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;

    }

 //----------------------------------------------------------------
}
