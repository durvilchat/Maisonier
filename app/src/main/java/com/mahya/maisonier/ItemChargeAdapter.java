package com.mahya.maisonier;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mahya.maisonier.entites.Occupation;

public class ItemChargeAdapter extends BaseAdapter {

    private List<Occupation> objects = new ArrayList<Occupation>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemChargeAdapter(Context context, List<Occupation> objects) {
        this.context = context;
        this.objects=objects;
        this.layoutInflater = LayoutInflater.from(context);
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
        initializeViews((Occupation)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(Occupation object, ViewHolder holder) {

        holder.tvDescr.setText(object.toString());
    }

    ArrayList<Occupation> getBox() {
        ArrayList<Occupation> box = new ArrayList<Occupation>();
        for (Occupation p : objects) {
            if (p!=null)
                box.add(p);
        }
        return box;
    }

    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
           // getProduct((Integer) buttonView.getTag()).box = isChecked;
        }
    };

    protected class ViewHolder {
        private CheckBox cbBox;
    private LinearLayout linearLayout1;
    private TextView tvDescr;

        public ViewHolder(View view) {
            cbBox = (CheckBox) view.findViewById(R.id.cbBox);
            linearLayout1 = (LinearLayout) view.findViewById(R.id.linearLayout1);
            tvDescr = (TextView) view.findViewById(R.id.occupation); 
        }
    }
}
