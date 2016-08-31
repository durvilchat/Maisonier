package com.mahya.maisonier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mahya.maisonier.R;
import com.mahya.maisonier.entites.Occupation;

import java.util.ArrayList;
import java.util.List;

public class ItemChargeAdapter extends BaseAdapter {

    List<Occupation> items = new ArrayList<Occupation>();
    private List<Occupation> objects = new ArrayList<Occupation>();
    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getOccupation((Integer) buttonView.getTag()).select = isChecked;
        }
    };
    private int count;
    private boolean[] thumbnailsselection;
    private Context context;
    private LayoutInflater layoutInflater;

    public ItemChargeAdapter(Context context, List<Occupation> objects) {
        this.context = context;
        this.objects = objects;
        items = objects;
        this.layoutInflater = LayoutInflater.from(context);
        count = items.size();
        thumbnailsselection = new boolean[count];
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Occupation getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_charge, null);
            convertView.setTag(new ViewHolder(convertView));

        }
        initializeViews((Occupation) getItem(position), (ViewHolder) convertView.getTag());
        CheckBox cbBuy = (CheckBox) convertView.findViewById(R.id.cbBox);
        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(getItem(position).select);
        return convertView;
    }

    private void initializeViews(Occupation object, ViewHolder holder) {

        holder.tvDescr.setText(object.toString());
        holder.idItem.setText(String.valueOf(object.getId()));



    }

    public ArrayList<Occupation> getBox() {
        ArrayList<Occupation> box = new ArrayList<Occupation>();
        for (Occupation p : objects) {
            if (p.select)
                box.add(p);
        }
        return box;
    }

    Occupation getOccupation(int position) {
        return ((Occupation) getItem(position));
    }


    protected class ViewHolder {
        private LinearLayout linearLayout1;
        private TextView tvDescr;
        private TextView idItem;

        public ViewHolder(View view) {
            linearLayout1 = (LinearLayout) view.findViewById(R.id.linearLayout1);
            tvDescr = (TextView) view.findViewById(R.id.occupation);
            idItem = (TextView) view.findViewById(R.id.idOcc);
        }
    }


}
