package A2.myapplication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class GoogleService {
    private static final String API_KEY = "AIzaSyCV4H1WZYmzZHVXz_RbfVuapEKAlBjo2NQ";

    public static String getLocation(String address) {
        URL url = null;
        HttpsURLConnection connection = null;
        StringBuilder textResult = new StringBuilder();
        String query_parameter = "";

        if (address != null && !address.trim().equals("")) {
            query_parameter = "address=" + address.replace(' ', '+') + ",+VIC,+AU";
            try {
                String urlString = "https://maps.googleapis.com/maps/api/geocode/json?" + query_parameter + "&key=" + API_KEY;
                url = new URL(urlString);
                Log.i("address", urlString);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setReadTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content_Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                Log.i("geolocation", Integer.valueOf(connection.getResponseCode()).toString());
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNextLine()) {
                    textResult.append(scanner.nextLine());
                }
                scanner.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                assert connection != null;
                connection.disconnect();
            }
        }
        Log.i("geoposition", textResult.toString());
        return textResult.toString();
    }

    public static String getPlaces(LatLng latLng) {

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("&location=" + latLng.latitude + "," + latLng.longitude);
        sb.append("&radius=" + 6000);
        sb.append("&types=" + "park");
        sb.append("&key=" + API_KEY);
        URL url = null;
        HttpsURLConnection connection = null;
        StringBuilder textResult = new StringBuilder();
        try {
            String urlString = sb.toString();
            url = new URL(urlString);
            Log.i("address", urlString);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content_Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Log.i("geolocation", Integer.valueOf(connection.getResponseCode()).toString());
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult.append(scanner.nextLine());
            }
            scanner.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert connection != null;
            connection.disconnect();
        }

        Log.i("geoposition", textResult.toString());
        return textResult.toString();
    }




    @NonNull
    public static double[] getLatLng(String result) {
        double lat = 0d;
        double lng = 0d;
        try {
            JSONObject obj = new JSONObject(result);
            JSONArray results = obj.getJSONArray("results");
            JSONObject geometry = results.getJSONObject(0).getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            lat = location.getDouble("lat");
            lng = location.getDouble("lng");
            Log.i("latlng",""+lat+","+lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new double[]{lat, lng};
    }
}
