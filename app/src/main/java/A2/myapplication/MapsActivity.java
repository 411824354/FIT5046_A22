package A2.myapplication;

import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapView mapView;
    private GoogleMap mMap;
    int userId;
    String address;

    JSONObject userobj;
    LatLng latlng;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SharedPreferences sp = getSharedPreferences( "signUpUser", MODE_PRIVATE);
        String userInfo = sp.getString("user","");

        try {
            JSONArray users = new JSONArray(userInfo);
            userobj = users.getJSONObject( 0 );
        } catch (JSONException e) {
            e.printStackTrace();
        }

            Object data = new Object[1];
        try {

             data = userobj.getString("address");
            new GetGeocoding().execute(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady (GoogleMap googleMap){
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //go
        // mMap.addMarker(new MarkerOptions().position(melbourne).title("2/20 Goode St Malvern Eest 3145"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(melbourne,10F));


        // Add a marker in Sydney and move the camera
          /* LatLng melbourne = new LatLng(-37.8837227, 145.0677848);

            LatLng currentLocation = new LatLng(latLng[0], latLng[1]);
            mMap.clear();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14.7f));
            LatLng location = new LatLng(latLngs.get(i)[0], latLngs.get(i)[1]);
            mMap.addMarker(new MarkerOptions().position(location).title(address)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN )));*/
    }

    private class MapAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            try {
                SharedPreferences sp = getSharedPreferences( "signUpUser",MODE_PRIVATE );
                String user = sp.getString( "user","" );
                JSONObject obj = new JSONObject(user);
                address = obj.getString("address");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

   /* @Override

    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    private CameraUpdate getZoom (LatLng latLng,double distance){

        LatLng position1 = SphericalUtil.computeOffset(latLng, distance, 136);
        LatLng position2 = SphericalUtil.computeOffset(latLng, distance,-44);
        LatLngBounds sBounds = new LatLngBounds(new LatLng(position1.latitude,position2.longitude), new LatLng(position2.latitude,position1.longitude));
        return CameraUpdateFactory.newLatLngBounds(sBounds,0);

    }

    private class GetGeocoding extends AsyncTask<Object, String, String>{
        @Override
        protected String doInBackground(Object... objects) {
            String result = GoogleService.getLocation((String)objects[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject location = null;

            try {
                location = new JSONObject(s)
                        .getJSONArray("results")
                        .getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location");
                String latitude = location.getString("lat");
                String longitude = location.getString("lng");
                double lat = Double.parseDouble(latitude);
                double lng = Double.parseDouble(longitude);
                latlng = new LatLng(lat, lng);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById( R.id.map);
            mapFragment.getMapAsync(MapsActivity.this);

            MarkerOptions center = new MarkerOptions().position(latlng);
            mMap.addMarker(center);
            mMap.moveCamera(getZoom(latlng,6000));

            Object data = new Object[1];
            data  = latlng;

            new GetParks().execute(data);



        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mMap == null){
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(MapsActivity.this);
        }
    }

    public class GetParks extends AsyncTask<Object, String, String>{

        @Override
        protected String doInBackground(Object... objects) {
            String result = GoogleService.getPlaces((LatLng) objects[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //JSONObject resultObj = new JSONObject(s);
            JSONArray resultArray = null;
            try {
                resultArray = new JSONObject(s).getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i< resultArray.length();i++){
                try {
                    JSONObject result = resultArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");

                    LatLng latLng = new LatLng(Double.parseDouble(result.getString("lat")),Double.parseDouble(result.getString("lng")));

                    MarkerOptions parks = new MarkerOptions().position(latLng);
                    mMap.addMarker(parks).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
/*            for (int z = 0; z < resultArray.length(); z++){
                try{
                    JSONObject park = resultArray.getJSONObject(z).getJSONObject("geometry").getJSONObject("name");
                    park.getString("name"))
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/

        }
    }
}
