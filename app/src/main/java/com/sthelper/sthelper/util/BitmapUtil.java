package com.sthelper.sthelper.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * 加载大图片工具类：解决Android加载大图片时报OOM异常 解决原理：先设置缩放选项，再读取缩放的图片数据到内存，规避了内存引起的OOM
 */
public class BitmapUtil {

	public static final int UNCONSTRAINED = -1;

	/*
     * 获得设置信息
	 */
	public static Options getOptions(String path) {
		Options options = new Options();
		options.inJustDecodeBounds = true;// 只描边，不读取数据
		BitmapFactory.decodeFile(path, options);
		return options;
	}

	/**
	 * 根据文件路径等比压缩图片
	 * 
	 * @param path
	 *            图片路径
	 * @param options
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Bitmap getBitmapByPath(String path, Options options, int screenWidth, int screenHeight)
	        throws FileNotFoundException {
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		FileInputStream in = null;
		in = new FileInputStream(file);
		if (options != null) {
			Rect r = getScreenRegion(screenWidth, screenHeight);
			int w = r.width();
			int h = r.height();
			int maxSize = w > h ? w : h;
			int inSimpleSize = computeSampleSize(options, maxSize, w * h);
			options.inSampleSize = inSimpleSize; // 设置缩放比例
			options.inJustDecodeBounds = false;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
		}
		Bitmap b = BitmapFactory.decodeStream(in, null, options);
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}
	public static Bitmap getBitmapByPath(String path)
	        throws FileNotFoundException {
		Options options = new Options();
		options.inJustDecodeBounds = true;// 只描边，不读取数据
		BitmapFactory.decodeFile(path, options);
		
		File file = new File(path);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		FileInputStream in = null;
		in = new FileInputStream(file);
		if (options != null) {
			options.inSampleSize = 10; // 设置缩放比例
			options.inJustDecodeBounds = false;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
		}
		Bitmap b = BitmapFactory.decodeStream(in, null, options);
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}

	private static Rect getScreenRegion(int width, int height) {
		return new Rect(0, 0, width, height);
	}

	/**
	 * 获取需要进行缩放的比例，即options.inSampleSize
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == UNCONSTRAINED) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
		        Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == UNCONSTRAINED) && (minSideLength == UNCONSTRAINED)) {
			return 1;
		} else if (minSideLength == UNCONSTRAINED) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 
	 * @param source
	 *            源图片的路径
	 * @param destination
	 *            处理后的图片路径
	 */
	public static void setExif(String source, String destination) {
		ExifInterface exifInterface1 = null;
		ExifInterface exifInterface2 = null;
		try {
			exifInterface1 = new ExifInterface(source);
			exifInterface2 = new ExifInterface(destination);

			//方向
			exifInterface2.setAttribute(ExifInterface.TAG_ORIENTATION,
			        exifInterface1.getAttribute(ExifInterface.TAG_ORIENTATION));
			exifInterface2.saveAttributes();
			//经度
			exifInterface2.setAttribute(ExifInterface.TAG_GPS_LATITUDE,
			        exifInterface1.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
			exifInterface2.saveAttributes();
			//纬度
			exifInterface2.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,
			        exifInterface1.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
			exifInterface2.saveAttributes();
			//时间
			exifInterface2.setAttribute(ExifInterface.TAG_DATETIME,
			        exifInterface1.getAttribute(ExifInterface.TAG_DATETIME));
			exifInterface2.saveAttributes();
			//长 
			exifInterface2.setAttribute(ExifInterface.TAG_IMAGE_LENGTH,
			        exifInterface1.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));
			exifInterface2.saveAttributes();
			//宽
			exifInterface2.setAttribute(ExifInterface.TAG_IMAGE_WIDTH,
			        exifInterface1.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
			exifInterface2.saveAttributes();
			
//			//纬度参考
//			exifInterface2.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF,
//			        exifInterface1.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
//			exifInterface2.saveAttributes();
//			//经度参考
//			exifInterface2.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF,
//			        exifInterface1.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));
//			exifInterface2.saveAttributes();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过uri获取图片的实际路径
	 * 
	 * @param context
	 * @param contentURI
	 * @return
	 */
	@TargetApi(19)
	public static String getRealPathFromURI(Context context, Uri contentURI) {
		String result = "";
		String stringUri = contentURI.toString();
		if (stringUri.startsWith("file://")) {
			result = contentURI.getPath();
		} else if (stringUri.startsWith("content://")) {
			if (android.os.Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context, contentURI)) {
				String wholeID = DocumentsContract.getDocumentId(contentURI);
				String id = wholeID.split(":")[1];
				String[] column = { MediaStore.Images.Media.DATA };
				String sel = MediaStore.Images.Media._ID + "=?";
				Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				        column, sel, new String[] { id }, null);
				int columnIndex = cursor.getColumnIndex(column[0]);
				if (cursor.moveToFirst()) {
					result = cursor.getString(columnIndex);
				}
				cursor.close();
			} else {
				String[] projection = { MediaStore.Images.Media.DATA };
				Cursor cursor = context.getContentResolver().query(contentURI, projection, null, null, null);
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				result = cursor.getString(column_index);
			}
		}
		return result;
	}
	
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}
	
	public static int readPictureDegree(String path) {
		int degree  = 0;
		try
		{
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return degree;
	}
	
	public static void rotation(String path, int degree) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		options.inSampleSize = 10;
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		bitmap = BitmapFactory.decodeFile(path, options);
		// 获取旋转后的bitmap
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		// Setting post rotate to 90
		Matrix mtx = new Matrix();
		mtx.postRotate(degree);
		// Rotating Bitmap
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);

		// bitmap写入文件
		FileOutputStream m_fileOutPutStream = null;
		try {
			m_fileOutPutStream = new FileOutputStream(path);// 写入的文件路径
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bitmap.compress(CompressFormat.JPEG, 100, m_fileOutPutStream);
		try {
			m_fileOutPutStream.flush();
			m_fileOutPutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		bitmap.recycle();
	}
	
	/**
	 * 保存bitmap到相册
	 * @param context
	 * @param bmp
	 */
	public static boolean saveImageToGallery(Context context, Bitmap bmp) {
		// 首先保存图片
		File appDir = new File(Environment.getExternalStorageDirectory(),"/sports");
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// 其次把文件插入到系统图库
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		// 最后通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.parse("file://" + file.getAbsolutePath())));
		return true;
	}

}