package tmj5.project.nihonmosquelocator.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import tmj5.project.nihonmosquelocator.Model.MosqueModel;
import tmj5.project.nihonmosquelocator.R;

/**
 * Created by Zira on 1/1/2017.
 * NIM 4314122014
 */

public class MosqueAdapter extends ArrayAdapter<MosqueModel> {
    Context context;
    int resource;
    record rec;

    public MosqueAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            rec = new record();
            rec.Photo = (ImageView) convertView.findViewById(R.id.GambarMosque);
            rec.Name = (TextView) convertView.findViewById(R.id.namaMosque);
            rec.Prefecture = (TextView) convertView.findViewById(R.id.prefekturMosque);
            convertView.setTag(rec);
        } else {
            rec = (record) convertView.getTag();
        }

        rec.Name.setText(getItem(position).getmNama());
        rec.Prefecture.setText(getItem(position).getmPrefektur());

        Glide
                .with(context)
                .load(getItem(position).getmFoto())
                .crossFade()
                .fitCenter()
                .placeholder(R.mipmap.ic_launcher)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(rec.Photo);

        return convertView;
    }

    static class record {
        ImageView Photo;
        TextView Name, Prefecture;
    }

}
