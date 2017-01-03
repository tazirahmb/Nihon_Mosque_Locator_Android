package tmj5.project.nihonmosquelocator;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tmj5.project.nihonmosquelocator.Controller.AppController;
import tmj5.project.nihonmosquelocator.JSONReference.Constant;
import tmj5.project.nihonmosquelocator.database.OpenHelperSqlite;

public class DetailsActivity extends AppCompatActivity {

    TextView tv_prefektur, tv_address, tv_email, tv_web;
    String nama, prefektur, alamat, email, website, photo, id;
    int lat, lon, posisi;
    SQLiteDatabase db;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);

        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_prefektur = (TextView) findViewById(R.id.tv_prefecture);
        tv_web = (TextView) findViewById(R.id.tv_web);

        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            posisi = extra.getInt("ID", 0);
        }

        getData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenHelperSqlite helper = new OpenHelperSqlite(getApplicationContext());
                db = helper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", id);
                contentValues.put("name", nama);
                contentValues.put("prefecture", prefektur);
                contentValues.put("address", alamat);
                contentValues.put("email", email);
                contentValues.put("website", website);
                contentValues.put("photo", photo);
                contentValues.put("lat", lat);
                contentValues.put("lon", lon);

                db.insert("Fav", null, contentValues);

                    //fab.setImageResource(android.support.design.R.drawable.);
                    Snackbar.make(view, "Added to Favourite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                db.close();

                //===================Tambahin cara ngapus dari db
            }
        });
    }

    public void getData() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constant.API_URL_MOSQUE, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject ngobjek = null;

                try {
                    ngobjek = response.getJSONObject(posisi);

                    id = String.valueOf(ngobjek.getInt("id"));
                    nama = ngobjek.getString("Name");
                    prefektur = ngobjek.getString("Prefecture");
                    alamat = ngobjek.getString("Address");
                    email = ngobjek.getString("E-Mail");
                    website = ngobjek.getString("Website");
                    photo = ngobjek.getString("Photo");
                    lat = ngobjek.getInt(ngobjek.getJSONObject("Location").getString("Latitude"));
                    lon = ngobjek.getInt(ngobjek.getJSONObject("Location").getString("Longitude"));

                    Bundle bun = new Bundle();
                    bun.putInt("lat", lat);
                    bun.putInt("lon", lon);

                    /*MapsFragment lokasi = new MapsFragment();
                    FragmentTransaction transaction = DetailsActivity().getSupportFragmentManager().beginTransaction();
                    lokasi.setArguments(bun); //buat naro data argumen/bundle ke si fragment2
                    transaction.commit();*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

            tb.setTitle(nama);

        /*Glide
                .with(getApplicationContext())
                .load(photo)
                .crossFade()
                .fitCenter()
                .placeholder(R.color.colorPrimaryDark)
                .into(tb.getBackground());*/

            tv_prefektur.setText(prefektur);
            tv_web.setText(website);
            tv_address.setText(alamat);
            tv_email.setText(email);

            //========bawahnya kasih fragment maps

    }
}
