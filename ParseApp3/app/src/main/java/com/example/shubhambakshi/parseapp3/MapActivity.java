package com.example.shubhambakshi.parseapp3;

        import android.os.Bundle;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.parse.FindCallback;
        import com.parse.Parse;
        import com.parse.ParseException;
        import com.parse.ParseObject;
        import android.app.Activity;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Iterator;
        import java.util.List;

        import android.location.Location;
        import android.location.LocationManager;
        import android.location.LocationListener;
        import android.widget.Toast;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.CameraUpdate;
        import com.parse.ParseQuery;

public class MapActivity extends Activity implements LocationListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    LocationManager locationManager;
    static double Current_lon;
    static double Current_lat;
    String provider;
    Location location;
    ArrayList<ParseObject> locationList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final ParseQuery<ParseObject> query1 = ParseQuery.getQuery("LOCATION");

        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                //Searchoperate obj=new Searchoperate();
                locationList = (ArrayList<ParseObject>) checkAndCreateList(list, Searchoperate.validList);
                //mMap.addMarker(new MarkerOptions().position(new LatLng(13, 77)).title("hello"));

                setUpMapIfNeeded();
            }

        });
    }

    private List<ParseObject> checkAndCreateList(List<ParseObject> list1, List<ParseObject> list2){
        List<ParseObject> validList = new ArrayList<>();
        Iterator<ParseObject> itr1 = list1.iterator();
        Iterator<ParseObject> itr2 = list2.iterator();
        String id = itr1.next().get("ID").toString();
        while(itr2.hasNext()){
            ParseObject object2 = itr2.next();
            String id1 = object2.get("ID").toString();
            itr1 = list1.listIterator(0);
            while(itr1.hasNext()){
                ParseObject object1 = itr1.next();

                if((object1.get("ID").toString()).equals(object2.get("ID").toString())){
                    validList.add(object1);
                    String long1 = object1.get("lat").toString();
                    Double longD = Double.valueOf(long1);

                    String long2 = object1.get("lang").toString();
                    Double long2D = Double.valueOf(long2);

                    String details = object2.get("FirstName").toString()+" "+object2.get("LastName").toString()+"\n-"+object2.get("Address").toString();

                    //  Toast.makeText(getApplicationContext(),object1.get("lang").toString(), Toast.LENGTH_LONG).show();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(longD,long2D)).title(details));
                    break;
                }

            }

        }
        //Toast.makeText(getApplicationContext(), "No Match found", Toast.LENGTH_LONG).show();
        //mMap.addMarker(new MarkerOptions().position(new LatLng(14, 77)).title("funny"));
        Searchoperate.validList.clear();
        SearchResultsActivity.searchString="";
        return validList;
    }



    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link MapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            MapFragment mapFrag=((MapFragment)getFragmentManager().findFragmentById(R.id.map));
            mMap=mapFrag.getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Searchoperate.curlong,Searchoperate.curlat), 5);
        mMap.animateCamera(cameraUpdate);
      /*    Iterator<ParseObject> itr = Searchoperate.validList.iterator();
          Iterator<ParseObject> itr1 = Searchoperate.validList1.iterator();
            while (itr1.hasNext())
            {
                ParseObject testObject = itr.next();
                ParseObject testObject1 = itr1.next();
                String long1 = testObject1.get("lat").toString();
                Double longD = Double.valueOf(long1);
                String long2 = testObject1.get("lang").toString();
                Double long2D = Double.valueOf(long2);
                String details = testObject.get("FirstName").toString()+" "+testObject.get("LastName").toString()+"\n-"+testObject.get("Address").toString();
                mMap.addMarker(new MarkerOptions().position(new LatLng(longD, long2D)).title(details));
            } */

      /*  Iterator<ParseObject> itr4 = locationList.iterator();
        while(itr4.hasNext()){
            Toast.makeText(getApplicationContext(), "itr problem", Toast.LENGTH_LONG).show();

            ParseObject testObject = itr4.next();
            String long1 = testObject.get("lat").toString();
            Double longD = Double.valueOf(long1);

            String long2 = testObject.get("lang").toString();
            Double long2D = Double.valueOf(long2);

            String details = testObject.get("FirstName").toString()+" "+testObject.get("LastName").toString()+"\n-"+testObject.get("Address").toString();
            mMap.addMarker(new MarkerOptions().position(new LatLng(longD, long2D)).title(details));
        }*/
    }
    @Override
    public void onLocationChanged(Location location) {
        Current_lon=location.getLongitude();
        Current_lat=location.getLatitude();
    }
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onBackPressed(){
        Searchoperate.validList=new ArrayList<ParseObject>();
        SearchResultsActivity.searchString=null;
        super.onBackPressed();
    }

}

