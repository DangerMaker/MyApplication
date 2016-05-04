package com.example.administrator.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;

import com.example.administrator.myapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class FileUtils {
	
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/formats/";

	public static String getFilePuth(){
		return SDPATH;
	}

	public static void saveBitmap(Bitmap bm, String picName) {
		Log.e("", "保存图片");
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPEG");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
			Log.e("", "已经保存");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveScanBitmap(Bitmap bm,String sacnName){
		File file = new File(SDPATH + sacnName+".PNG");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.PNG,0,fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static void delFile(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

	public static HashMap<String, Integer> emo_map = new HashMap<String, Integer>();
	// public static final String[] EMOS = new String[] { "微笑", "呲牙", "色", "发呆",
	// "得意", "大哭", "害羞", "闭嘴", "睡", "流泪", "尴尬", "发怒", "调皮", "大笑", "惊讶",
	// "委屈", "冷汗", "抓狂", "吐", "偷笑", "傲慢", "困", "憨笑", "敲打", "抠鼻", "鼓掌",
	// "坏笑", "鄙视", "委屈", "阴险", "咖啡", "玫瑰", "嘴唇", "爱心", "菜刀", "月亮", "强",
	// "握手", "拥护", "啤酒", "OK" };
	public static final String[] EMOS = new String[] { "微笑", "呲牙", "色", "发呆",
			"得意", "大哭", "害羞", "闭嘴", "睡", "崇拜", "亲亲", "发怒", "调皮", "大笑", "惊讶",
			"委屈", "冷汗", "抓狂", "吐", "阴险", "傲慢", "困", "憨笑", "敲打", "奋斗", "拥护",
			"晕", "鄙视", "强", "菜刀", "再见", "玫瑰", "嘴唇", "爱心", "咖啡", "月亮", "礼物",
			"握手", "鼓掌", "啤酒", "OK" };
	public static SpannableStringBuilder getEmotion(Context context, String text) {
		if (emo_map.size() == 0) {
			for (int i = 0; i < 41; i++) {
				emo_map.put("[" + EMOS[i] + "]", R.drawable.emoji_01 + i);
			}
		}
		int index1 = text.indexOf("[");
		SpannableStringBuilder sb;
		if (index1 >= 0) {
			sb = new SpannableStringBuilder("");
			//特殊符号前面加一个空格，不会出现不正常的折行现象
			text=text.replace(" [", "[");
			text=text.replace("[", " [");
			replaceString(context, sb, text);
		} else {
			sb = new SpannableStringBuilder(text);
		}

		return sb;
	}
	public static void replaceString(Context context,
									 SpannableStringBuilder sb, String text) {

		int index1 = text.indexOf("[");
		int index2 = text.indexOf("]");
		String remindedString = "";
		String emString = "";
		if (index2 > 0&&index1<index2) {
			// sb.append(text.substring(0, index1));
			remindedString = text.substring(index2 + 1);
			emString = text.substring(index1, index2 + 1);
			if (emString == null) {
				return;
			}
			String cc = emString.substring(1, emString.length() - 1);
			if (cc != null && !"".equals(cc)
					&& emo_map.containsKey("[" + cc + "]")) {
				// int num = Integer.parseInt(cc);
				// if (num < 0 || num > emo_map.size() - 1) {
				// return;
				// } else {

				int id = emo_map.get("[" + cc + "]");
				Drawable d = context.getResources().getDrawable(id);
				//
				d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
				//
				ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);

				SpannableString ss = new SpannableString(text.substring(0,
						index2 + 1));
				ss.setSpan(span, index1, index2 + 1,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

				sb.append(ss);
				replaceString(context, sb, remindedString);
				// }
			}else {
				sb.append(text.substring(0, index2+1));
				replaceString(context, sb, remindedString);
			}

		} else {
			sb.append(text);
		}
	}

}
