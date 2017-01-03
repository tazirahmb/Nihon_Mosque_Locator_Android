package tmj5.project.nihonmosquelocator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tmj5.project.nihonmosquelocator.Adapter.MosqueAdapter;
import tmj5.project.nihonmosquelocator.Controller.AppController;
import tmj5.project.nihonmosquelocator.JSONReference.Constant;
import tmj5.project.nihonmosquelocator.Model.MosqueModel;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<MosqueModel> modelmodel = new ArrayList<>();
    MosqueAdapter adapter;
    Toolbar tb;
    private ProgressDialog pDialog;

    String aidi, nama, prefektur, alamat, website, email, photo;
    int lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        tb = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(tb);
        tb.setTitle("Nihon Mosque Locator");
        tb.inflateMenu(R.menu.menu_details);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);

        ListView lv = (ListView) findViewById(R.id.list_mosque);
        adapter = new MosqueAdapter(this, R.layout.cardview_mosque);
        lv.setAdapter(adapter);
        bikinData();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Intent in = new Intent(view.getContext(), DetailsActivity.class);
                in.putExtra("ID", position);
                startActivity(in);
            }
        });

    }

    public void bikinData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Constant.API_URL_MOSQUE, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        String nama, prefektur, foto, id;
                        JSONObject isiObjek = response.getJSONObject(i);

                        //=================================================ngikut JSON
                        nama = isiObjek.getString("Name");
                        prefektur = isiObjek.getString("Prefecture");
                        foto = isiObjek.getString("Photo");
                        //=================================================ngikut JSON

                        MosqueModel model = new MosqueModel();
                        model.setmNama(nama);
                        model.setmPrefektur(prefektur);
                        model.setmFoto(foto);

                        Log.d(Constant.TAG, "data= " + nama);

                        modelmodel.add(model);

                    }

                    adapter.addAll(modelmodel);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //notifikasi error volley
            }
        });

       AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_fav) {
            Intent bin = new Intent(HomeActivity.this, FavActivity.class);
            startActivity(bin);
        }

        return true;
    }
}