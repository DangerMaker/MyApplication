package com.example.administrator.myapplication.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.Config;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.Bimp;
import com.example.administrator.myapplication.model.ImageItem;
import com.example.administrator.myapplication.ui.PhotoActivity1;
import com.example.administrator.myapplication.util.BitmapCache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ImageGridAdapter extends BaseAdapter {

	public static TextCallback textcallback = null;
	Activity act;
	List<ImageItem> dataList;
	public static Map<String, String> map = new HashMap<>();
	public static BitmapCache cache;
	public static Handler mHandler;
	BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap,
							  Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals(imageView.getTag())) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}
	};

	private int selectTotal = 0;

	public  interface TextCallback {
		 void onListen(int count);
	}

	public void setTextCallback(TextCallback listener) {
		textcallback = listener;
	}

	public ImageGridAdapter(Activity act, List<ImageItem> list, Handler mHandler) {
		this.act = act;
		dataList = list;
		cache = new BitmapCache();
		this.mHandler = mHandler;
		map.clear();
		for (ImageItem item : dataList){
			item.isSelected = false;
		}
	}

	@Override
	public int getCount() {
		int count = 0;
		if (dataList != null) {
			count = dataList.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class Holder {
		private ImageView iv;
		private ImageView selected;
		private TextView text;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		final Holder holder;

		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(act, R.layout.item_image_grid, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.image);
			holder.selected = (ImageView) convertView
					.findViewById(R.id.isselected);
			holder.text = (TextView) convertView
					.findViewById(R.id.item_image_grid_text);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final ImageItem item = dataList.get(position);

		holder.iv.setTag(item.imagePath);
		cache.displayBmp(holder.iv, item.thumbnailPath, item.imagePath,
				callback);
		holder.selected.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String path = dataList.get(position).imagePath;
				if ((Bimp.drr.size() + selectTotal) < 9) {
					item.isSelected = !item.isSelected;
					if (item.isSelected) {
						holder.selected	.setImageResource(R.drawable.icon_data_select);
						holder.text.setBackgroundResource(R.drawable.bgd_relatly_line);
						selectTotal++;
						if (textcallback != null)
							textcallback.onListen(selectTotal);
						map.put(path, path);
					} else if (!item.isSelected) {
						holder.selected.setImageResource(R.drawable.music_download_progress_background);
						holder.text.setBackgroundColor(0x00000000);
						selectTotal--;
						if (textcallback != null)
							textcallback.onListen(selectTotal);
						map.remove(path);
					}
				} else if ((Bimp.drr.size() + selectTotal) >= 9) {
					if (item.isSelected == true) {
						item.isSelected = !item.isSelected;
						holder.selected.setImageResource(R.drawable.music_download_progress_background);
						holder.text.setBackgroundColor(0x00000000);
						selectTotal--;
						map.remove(path);
						if (textcallback != null)
							textcallback.onListen(selectTotal);
					} else {
						Message message = Message.obtain(mHandler, 0);
						message.sendToTarget();
					}
				}
			}
		});

		if (item.isSelected) {
			holder.selected.setImageResource(R.drawable.icon_data_select);
			holder.text.setBackgroundResource(R.drawable.bgd_relatly_line);
		} else {
			holder.selected.setImageResource(R.drawable.music_download_progress_background);
			holder.text.setBackgroundColor(0x00000000);
		}
		holder.iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(act, PhotoActivity1.class);
				intent.putExtra("images",(Serializable) dataList);
				intent.putExtra("ID",position);
				act.startActivity(intent);
			}
		});
		return convertView;
	}
}
