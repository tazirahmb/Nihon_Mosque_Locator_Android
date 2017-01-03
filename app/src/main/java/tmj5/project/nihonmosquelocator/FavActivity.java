package tmj5.project.nihonmosquelocator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import tmj5.project.nihonmosquelocator.Adapter.MosqueAdapter;
import tmj5.project.nihonmosquelocator.Model.MosqueModel;

public class FavActivity extends AppCompatActivity {
    MosqueAdapter adapter;
    SQLiteDatabase db;
    private ArrayList<MosqueModel> modelmodel = new ArrayList<>();
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);

        ListView lv = (ListView) findViewById(R.id.list_mosque);
        adapter = new MosqueAdapter(this, R.layout.cardview_mosque);
        lv.setAdapter(adapter);
        bikinData();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String numpangNama = modelmodel.get(position).getmNama();
                getData(numpangNama, view); //edit
            }
        });

    }

    public void bikinData() {

        MosqueModel model = new MosqueModel();

        Cursor cursor = db.query(false, "Fav", new String[]{"name", "prefecture",
                "photo"}, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            model.setmNama(cursor.getString(0));
            model.setmPrefektur(cursor.getString(1));
            model.setmFoto(cursor.getString(2));
            modelmodel.add(model);
        }

        adapter.addAll(modelmodel);
        adapter.notifyDataSetChanged();

    }


    public void getData(String name, final View v) {

        Cursor cursor = db.query(false, "Fav", null, "name=?", new String[]{name}, null, null, null, null);

        if (cursor.moveToNext()) {
            Intent in = new Intent(v.getContext(), FavDetailsActivity.class);
            in.putExtra("nama", cursor.getString(0));
            in.putExtra("prefektur", cursor.getString(1));
            in.putExtra("alamat", cursor.getString(2));
            in.putExtra("email", cursor.getString(3));
            in.putExtra("website", cursor.getString(4));
            in.putExtra("photo", cursor.getString(5));
            in.putExtra("lat", cursor.getString(6));
            in.putExtra("lon", cursor.getString(7));
            startActivity(in);
        }
    }
}
