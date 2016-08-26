package com.mahya.maisonier;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.mahya.maisonier.utils.Constants;

public class GalleryImageAdapter extends BaseAdapter {

	private Activity context;

	private static ImageView imageView;

	private List<String> plotsImages;

	private static ViewHolder holder;
	int type;

	public GalleryImageAdapter(Activity context, List<String> plotsImages,int type) {

		this.context = context;
		this.plotsImages = plotsImages;
		this.type=type;

	}

	@Override
	public int getCount() {
		return plotsImages.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			holder = new ViewHolder();

			imageView = new ImageView(this.context);

			imageView.setPadding(3, 3, 3, 3);

			convertView = imageView;

			holder.imageView = imageView;

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		if (type==1){
			holder.imageView.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch+"batiments/"+  plotsImages.get(position)));

		}else if (type==2){
			holder.imageView.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch +"logements/"+ plotsImages.get(position)));

		}

		holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		holder.imageView.setLayoutParams(new Gallery.LayoutParams(150, 90));

		return imageView;
	}

	private static class ViewHolder {
		ImageView imageView;
	}

}
