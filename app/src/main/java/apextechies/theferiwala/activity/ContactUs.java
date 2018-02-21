package apextechies.theferiwala.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import apextechies.theferiwala.R;

/**
 * Created by Shankar on 2/21/2018.
 */

public class ContactUs extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private TextView mail,call;
    private LinearLayout cartLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactus);
        call = (TextView)findViewById(R.id.call);
        mail = (TextView)findViewById(R.id.mail);
        initWidit();
        mapinitialize();

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+"8797404100"));
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(ContactUs.this, "Phone facility not available on your device", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto","contact@theferiwala.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
                catch (Exception e){
                    Toast.makeText(ContactUs.this, "Phone facility not available on your device", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initWidit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        TextView toolbartext = (TextView) findViewById(R.id.toolbartext);
        toolbartext.setText("Contact Us");
        cartLayout = (LinearLayout) findViewById(R.id.cartLayout);
        cartLayout.setVisibility(View.INVISIBLE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mapinitialize() {
        try

        {
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        catch (Exception e)
        {

        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(28.6527171,77.1293584);
       /* mMap.addMarker(new
                MarkerOptions().position(latLng).title("eWebbazar Prv. Ltd"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));*/

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(28.6527171,77.1293584));
        markerOptions.title("TheFeriWala Prv. Ltd");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


    }
}
