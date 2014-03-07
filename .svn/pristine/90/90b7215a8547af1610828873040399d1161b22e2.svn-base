package com.suiding.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.UUID;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.suiding.constant.UPFileType;
import com.suiding.util.ImageFolderUtil;

/**
 * 
 * 文件上传下载服务类
 * 
 * @author Administrator
 * 
 */
public class FileService {

	static boolean debug = false;

	// static final String uploadUrl =
	// "http://192.168.2.111:8089/upload_file_service/UploadServlet";
	// static final String downloadUrl =
	// "http://192.168.2.111:8089/upload_file_service/DownloadServlet";
	// static final String webRoot =
	// "http://192.168.2.111:8089/upload_file_service/";

//	static final String uploadUrl = "http://127.0.0.1:8080/upload_file_service/UploadServlet";
//	static final String downloadUrl = "http://127.0.0.1:8080/upload_file_service/DownloadServlet";
//	static final String webRoot = "http://127.0.0.1:8080/upload_file_service/";
//	static final String uploadFileUrl = "http://127.0.0.1:8080/upload_file_service/UpFileServlet";
//	static final String VersionUrl = "http://127.0.0.1:8080/upload_file_service/VersionServlet";
	
	static final String uploadUrl = "http://210.40.16.55:8089/upload_file_service/UploadServlet";
	static final String downloadUrl = "http://210.40.16.55:8089/upload_file_service/DownloadServlet";
	static final String webRoot = "http://210.40.16.55:8089/upload_file_service/";
	static final String uploadFileUrl = "http://210.40.16.55:8089/upload_file_service/UpFileServlet";
	static final String VersionUrl = "http://210.40.16.55:8089/upload_file_service/VersionServlet";

	/**
	 * 
	 * 图片上传
	 * 
	 * @param path
	 *            文件路径
	 * @param ImageFolderEnum
	 *            上传文件的服务器存储位置枚举
	 * @return 上传成功返回服务器文件名
	 */
	public static String uploadImage(String path, int ImageFolderEnum)
			throws Exception {
		String rt = null;
		try {
			String suffix = path.substring(path.lastIndexOf("."));
			String filename = UUID.randomUUID().toString() + suffix;

			// ?key=filename&&dir=ImageFolderEnum
			String Url = uploadUrl + "?key=" + filename + "&&dir="
					+ ImageFolderEnum;
			if (uploadFile(Url, path)) {
				rt = filename;
			}
		} catch (Exception ex) {
			throw ex;
		}
		return rt;
	}

	/**
	 * 上传头像，如果该头像存在，先删除后更新
	 * 
	 * @param oldurl
	 * @param path
	 * @param ImageFolderEnum
	 * @return
	 * @throws Exception
	 */
	public static String uploadHeadImage(String oldurl, String path,
			int ImageFolderEnum) throws Exception {
		String rt = null;
		try {
			String suffix = path.substring(path.lastIndexOf("."));
			String filename = UUID.randomUUID().toString() + suffix;

			// ?key=filename&&dir=ImageFolderEnum
			String Url = uploadUrl + "?key=" + filename + "&&dir="
					+ ImageFolderEnum + "&&oldurl=" + oldurl;
			if (uploadFile(Url, path)) {
				rt = filename;
			}
		} catch (Exception ex) {
			throw ex;
		}
		return rt;
	}

	/**
	 * 
	 * 获取图片服务器地址
	 * 
	 * @param url
	 *            图片存储地址
	 * @param ImageFolderEnum
	 *            服务器存储位置枚举
	 * @param ImageSize
	 *            图片大小枚举
	 * @return
	 */
	public static String getUrl(String url, int ImageFolderEnum, int ImageSize)
			throws Exception {
		String rt = url;
		try {
			if (url.toLowerCase(Locale.ENGLISH).startsWith("http://"))
				return url;
			// ?key=filename && dir=ImageFolderEnum && type=ImageSize
			String Url = downloadUrl + "?key=" + url + "&&dir="
					+ ImageFolderEnum + "&&type=" + ImageSize;
			if (ImageSize == com.suiding.constant.ImageSize.ORIGINAL || getImage(Url)) {
				String path = ImageFolderUtil.getPathfrom(ImageFolderEnum)
						.replace('\\', '/');
				String path1 = ImageFolderUtil.getSizefrom(ImageSize);
				if (path1 == null)
					return rt;
				rt = webRoot + path + path1 + "/" + url;
			}
		} catch (Exception ex) {
			throw ex;
		}
		return rt;
	}

	/**
	 * 获取直接拼接的图片URl
	 * 
	 * @param url
	 * @param ImageFolderEnum
	 * @param ImageSize
	 * @return
	 */
	public static String getLocalUrl(String url, int ImageFolderEnum,
			int ImageSize) {
		if (url.toLowerCase(Locale.ENGLISH).startsWith("http://"))
			return url;
		// ?key=filename && dir=ImageFolderEnum && type=ImageSize
		String path = ImageFolderUtil.getPathfrom(ImageFolderEnum).replace(
				'\\', '/');
		String path1 = ImageFolderUtil.getSizefrom(ImageSize);
		if (path1 == null)
			return url;
		return webRoot + path + path1 + "/" + url;
	}

	/**
	 * 下载图片
	 * 
	 * 注意，此方法仅供用于Android程序
	 * 
	 * @param url
	 *            图片存储地址
	 * @param ImageFolderEnum
	 *            服务器存储位置枚举
	 * @param ImageSize
	 *            图片大小枚举
	 * @return 如果成功，返回Bitmap对象
	 * @throws Exception
	 */
	public static Bitmap downloadImage(String url, int ImageFolderEnum,
			int ImageSize) throws Exception {
		return downloadImage(getUrl(url, ImageFolderEnum, ImageSize));
	}

	/**
	 * 
	 * 下载图片
	 * 
	 * 注意，此方法仅供用于Android程序
	 * 
	 * @param context
	 *            显示窗口对象
	 * @param url
	 *            图片存储地址
	 * @param ImageFolderEnum
	 *            服务器存储位置枚举
	 * @param ImageSize
	 *            图片大小枚举
	 * @return 如果成功，返回Bitmap对象
	 * @throws Exception
	 */
	public static BitmapDrawable downloadImage(Context context, String url,
			int ImageFolderEnum, int ImageSize) throws Exception {
		return getImageFromURL(context, getUrl(url, ImageFolderEnum, ImageSize));
	}

	public static BitmapDrawable getImageFromURL(Context context, String turl)
			throws Exception {
		BitmapDrawable bd = null;
		try {
			URL url = new URL(turl);
			// 创建连接
			HttpURLConnection hc = (HttpURLConnection) url.openConnection();
			// 获取数据
			bd = new BitmapDrawable(context.getResources(), hc.getInputStream());
			// 关闭连接
			hc.disconnect();
		} catch (Exception e) {
			throw e;
		}
		return bd;
	}

	/**
	 * 根据URL下图片 注意，此方法仅供用于Android程序
	 * 
	 * @param strUrl
	 *            图片网络地址
	 * @return 如果成功，返回Bitmap对象
	 * @throws Exception
	 */
	public static Bitmap downloadImage(String strUrl) throws Exception {
		Bitmap bm = null;
		try {
			// 实例化url
			URL url = new URL(strUrl);
			// 1.使用URL获得输入流
			InputStream in = url.openStream();
			// 通过BitmapFactory获得实例
			bm = BitmapFactory.decodeStream(in);
		} catch (Exception ex) {
			throw ex;
		}
		return bm;
	}

	public static boolean uploadApk(String version, String path) {
		String url = uploadFileUrl + "?uptype=" + UPFileType.TYPE_APK
				+ "&&version=" + version;
		try {
			return uploadFile(url, path);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean downloadApk(String version, String outpath)
			throws Exception {
		String strurl = webRoot + UPFileType.getPathfrom(UPFileType.TYPE_APK).replace('\\', '/')
				+"/"+ version + ".apk";
		return downloadFile(strurl, outpath);
	}

	public static String getLastApkVersion() throws Exception
	{
		String Url = VersionUrl + "?vstype=" + UPFileType.TYPE_APK;

		String boundary = "******";

		URL url = new URL(Url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
		httpURLConnection.setRequestProperty("Charset", "UTF-8");
		httpURLConnection.setRequestProperty("Content-Type",
				"multipart/form-data;boundary=" + boundary);


		InputStream is = httpURLConnection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		return br.readLine();
	}
	
	/**
	 * 或者Apk的下载地址
	 * @param version
	 * @return
	 */
	public static String getApkUrl(String version)
	{
		return webRoot + UPFileType.getPathfrom(UPFileType.TYPE_APK).replace('\\', '/')
				+"/"+ version + ".apk";
	}
	
	private static boolean getImage(String Url) throws Exception {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";

		URL url = new URL(Url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
		httpURLConnection.setRequestProperty("Charset", "UTF-8");
		httpURLConnection.setRequestProperty("Content-Type",
				"multipart/form-data;boundary=" + boundary);

		DataOutputStream dos = new DataOutputStream(
				httpURLConnection.getOutputStream());
		dos.writeBytes(twoHyphens + boundary + end);
		dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
		dos.flush();

		InputStream is = httpURLConnection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String result = br.readLine();

		dos.close();
		is.close();
		if ("true".equals(result)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 从服务器下载文件
	 * @param Url
	 * @param outpath
	 * @return
	 * @throws Exception
	 */
	private static boolean downloadFile(String Url, String outpath)
			throws Exception {
		int byteread = 0;

		URL url = new URL(Url);

		try {
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			FileOutputStream fs = new FileOutputStream(outpath);

			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			fs.close();
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	/* 上传文件至Server的方法 */
	private static boolean uploadFile(String Url, String srcPath)
			throws Exception {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";

		URL url = new URL(Url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
		httpURLConnection.setRequestProperty("Charset", "UTF-8");
		httpURLConnection.setRequestProperty("Content-Type",
				"multipart/form-data;boundary=" + boundary);

		DataOutputStream dos = new DataOutputStream(
				httpURLConnection.getOutputStream());
		dos.writeBytes(twoHyphens + boundary + end);
		dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
				+ srcPath.substring(srcPath.lastIndexOf("/") + 1) + "\"" + end);
		dos.writeBytes(end);

		FileInputStream fis = new FileInputStream(srcPath);
		byte[] buffer = new byte[8192]; // 8k
		int count = 0;
		while ((count = fis.read(buffer)) != -1) {
			dos.write(buffer, 0, count);

		}
		fis.close();

		dos.writeBytes(end);
		dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
		dos.flush();

		InputStream is = httpURLConnection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String result = br.readLine();

		dos.close();
		is.close();
		if ("true".equals(result)) {
			return true;
		} else {
			return false;
		}
	}

}
