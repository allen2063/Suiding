package com.suiding.util;

/**
 * 
 * 图片文件夹工具类
 * 
 * @author ytt
 *
 */
public class ImageFolderUtil {
	
	private static String folders[] = new String[]{
		"Images\\head\\user",
		"Images\\head\\store",
		"Images\\head\\product",
		"Images\\head\\other",
		"Images\\photo\\user",
		"Images\\photo\\store",
		"Images\\photo\\product",
		"Images\\photo\\other"
	};
	
	private static String size[] = new String[]{
		"",
		"/er",
		"/est",
		"/er"
	};
	
	/**
	 * 根据ImageFolderEnum图片文件枚举值获取图片存储路径
	 * @param ImageFolderEnum
	 * @return 成功返回非null字符串
	 */
	public static String getPathfrom(int ImageFolderEnum)
	{
		try
		{
			return folders[ImageFolderEnum];
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public static String getSizefrom(int ImageSize)
	{
		try
		{
			return size[ImageSize];
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}	

}
