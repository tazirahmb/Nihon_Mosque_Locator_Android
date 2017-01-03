package tmj5.project.nihonmosquelocator;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import tmj5.project.nihonmosquelocator.database.OpenHelperSqlite;

public class FavDetailsActivity extends AppCompatActivity {

    TextView tv_prefektur, tv_address, tv_email, tv_web;
    String nama, prefektur, alamat, email, website, photo;
    int lat, lon;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favdetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_prefektur = (TextView) findViewById(R.id.tv_prefecture);
        tv_web = (TextView) findViewById(R.id.tv_web);

        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            nama = extra.getString("nama");
            prefektur = extra.getString("prefektur");
            alamat = extra.getString("alamat");
            email = extra.getString("email");
            website = extra.getString("website");
            photo = extra.getString("photo");
            lat = extra.getInt("lat");
            lon = extra.getInt("lon");
        }

        toolbar.setTitle(nama);
        //toolbar.setBackground(R.drawable.class);
        tv_prefektur.setText(prefektur);
        tv_web.setText(website);
        tv_address.setText(alamat);
        tv_email.setText(email);

        //========bawahnya kasih fragment maps

    }
}
