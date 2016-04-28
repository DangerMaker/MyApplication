package com.example.administrator.myapplication.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.model.Bimp;
import com.example.administrator.myapplication.ui.view.PopupWindows;
import com.example.administrator.myapplication.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishedActivity extends Activity {

	private GridAdapter adapter;
	private int mPicWidth;
	@Bind(R.id.noScrollgridview)
	GridView mGridView;
	private boolean isKeyShow = true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectimg);
		ButterKnife.bind(this);
		Init();
	}

	public void Init() {
		//获取当前手机频幕高度
		WindowManager manager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
		int screenWidth = manager.getDefaultDisplay().getWidth();
		//获取控件margin值
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mGridView.getLayoutParams();
		int rightMargin = params.rightMargin;
		int leftMargin = params.leftMargin;
		//计算图片排列总宽度
		mPicWidth = screenWidth - rightMargin - leftMargin;
		LinearLayout root = (LinearLayout) findViewById(R.id.root);
		LinearLayout addGroup = (LinearLayout) findViewById(R.id.add_group);


		controlKeyboardLayout(root,addGroup);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {


			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == Bimp.bmp.size()) {
					new PopupWindows(PublishedActivity.this, mGridView, new PopupWindows.OnPopupWindownButton1ClickListener() {
						@Override
						public void onClick() {
							photo();
						}
					});
				} else {
					Intent intent = new Intent(PublishedActivity.this,
							PhotoActivity.class);
					intent.putExtra("ID", position);
					startActivity(intent);
				}
			}
		});
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = new ImageView(PublishedActivity.this);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mPicWidth / 4,mPicWidth/ 4);
				convertView.setLayoutParams(params);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView;
				holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
				if (position == Bimp.bmp.size()) {
					holder.image.setImageBitmap(BitmapFactory.decodeResource(
							getResources(), R.drawable.icon_addpic_unfocused));
					if (position == 9) {
						holder.image.setVisibility(View.GONE);
					}
				} else {
					holder.image.setImageBitmap(Bimp.bmp.get(position));
				}
			return convertView;
		}
		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								String newStr = path.substring(
										path.lastIndexOf("/") + 1,
										path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								++ Bimp.max;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		checkGridView();
		super.onRestart();
	}

	private static final int TAKE_PICTURE = -1;
	private String path = "";

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String sdStatus = Environment.getExternalStorageState();
		if(!TextUtils.equals(sdStatus, Environment.MEDIA_MOUNTED)){
			//检测sdcard
			return;
		}
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/myimage/", System.currentTimeMillis()+ ".jpg");
		path = file.getPath();
		Uri imageUri = Uri.fromFile(file);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == TAKE_PICTURE){
			if (Bimp.drr.size() < 9 && resultCode == RESULT_OK) {
				Bimp.drr.add(path);
			}
		}
	}

	@Override
	protected void onStart() {
		checkGridView();
		super.onStart();
	}

	public void checkGridView(){
		if(Bimp.drr.size() == 0){
			mGridView.setVisibility(View.GONE);
		}else {
			mGridView.setVisibility(View.VISIBLE);
		}
	}

	@OnClick(R.id.add_picature)
	public void addPicature(){
		new PopupWindows(PublishedActivity.this, mGridView, new PopupWindows.OnPopupWindownButton1ClickListener() {
			@Override
			public void onClick() {
				photo();
			}
		});
	}

	@OnClick(R.id.activity_selectimg_send)
	public void send(){
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < Bimp.drr.size(); i++) {
			String Str = Bimp.drr.get(i).substring(
					Bimp.drr.get(i).lastIndexOf("/") + 1,
					Bimp.drr.get(i).lastIndexOf("."));
			list.add(FileUtils.SDPATH+Str+".JPEG");
		}
		// 高清的压缩图片全部就在  list 路径里面了
		// 高清的压缩过的 bmp 对象  都在 Bimp.bmp里面
		// 完成上传服务器后 .........
		FileUtils.deleteDir();
	}

	/**
	 * 根据键盘状态设置布局位置
	 * */
	private void controlKeyboardLayout(final View root, final View scrollToView) {
		root.getViewTreeObserver().addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				Rect rect = new Rect();
				//获取root在窗体的可视区域
				root.getWindowVisibleDisplayFrame(rect);
				//获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
				int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
				//若不可视区域高度大于100，则键盘显示
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollToView.getLayoutParams();
				if (rootInvisibleHeight > 100 && isKeyShow) {
					int[] location = new int[2];
					//获取scrollToView在窗体的坐标
					scrollToView.getLocationInWindow(location);
					//计算root滚动高度，使scrollToView在可见区域
					int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
					params.bottomMargin = srollHeight;
					scrollToView.setLayoutParams(params);
					isKeyShow = false;
				} else if(rootInvisibleHeight <= 100 && !isKeyShow){
					//键盘隐藏
					params.bottomMargin = 0;
					scrollToView.setLayoutParams(params);
					isKeyShow =true;
				}
			}
		});
	}
}
