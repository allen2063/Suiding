package com.suiding.util;

/**
 * 
 * ͼƬ�ļ��й�����
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
	 * ����ImageFolderEnumͼƬ�ļ�ö��ֵ��ȡͼƬ�洢·��
	 * @param ImageFolderEnum
	 * @return �ɹ����ط�null�ַ���
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
