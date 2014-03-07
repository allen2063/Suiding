package com.suiding.service;

import java.util.List;

import com.suiding.model.Address;

public class AdressService {

    public static final int LIST_LENGHT = 45051;

    private static List<String> AdressDB = null;
//	static 
//	{
//		if (AdressDB == null) {
//			try {
//				AdressDB = DomainFactory.getAddressDomain().GetAddressDB();
//			} catch (LeSouException e) {
//				e.printStackTrace();
//				AdressDB = new ArrayList<String>();
//			}
//		}
//	}
	
	/**
	 * ���õ�ַ�����ڴ����ݿ�
	 */
	public static void setAddressDB(List<String> db)
	{
		AdressDB = db;
	}
	
	public static List<String> getAdressDB() {
//		if (AdressDB == null) {
//			try {
//				AdressDB = DomainFactory.getAddressDomain().GetAddressDB();
//			} catch (LeSouException e) {
//				e.printStackTrace();
//				AdressDB = new ArrayList<String>();
//			}
//		}
		return AdressDB;
	}
	
	//1����Ϫ��ĳĳ��ĳĳ�� toGeneralString(Adress adress)
	public static String getToGeneralString(Address address)
	{
		String rt = "";
		
		if(AdressDB!=null && AdressDB.size()>0)
		{
			if(address.Xian>0 && address.Xian<=LIST_LENGHT)
			{
				rt = ""+AdressDB.get(address.Xian-1);
			}
			//+=AdrCustom
		}
		
		return rt;
	}
	//2������ʡƽ���������ºϴ峡����22�� toString()
	public static String getToString(Address address)
	{
		String rt = "";

		if(AdressDB!=null && AdressDB.size()>0)
		{
			if(address.Province>0 && address.Province<=LIST_LENGHT)
			{
				rt += AdressDB.get(address.Province-1);
			}
			if(address.City>0 && address.City<=LIST_LENGHT)
			{
				rt += AdressDB.get(address.City-1);
			}
			if(address.Xian>0 && address.Xian<=LIST_LENGHT)
			{
				rt += AdressDB.get(address.Xian-1);
			}
			if(address.Xiang>0 && address.Xiang<=LIST_LENGHT)
			{
				rt += AdressDB.get(address.Xiang-1);
			}
			if(address.Cun>0 && address.Cun<=LIST_LENGHT)
			{
				rt += AdressDB.get(address.Cun-1);
			}
			//+=AdrCustom
		}
		
		return rt;
	}
	//3���й�����ʡƽ���������ºϴ峡����22��+�Զ������� toLongString()
	public static String getToLongString(Address address)
	{
		String rt = "";

		if(AdressDB!=null && AdressDB.size()>0)
		{
			//AdrContry
			rt+="�й�";
			if(address.Province>0 && address.Province<=LIST_LENGHT)
			{
				rt += AdressDB.get(address.Province-1);
			}
			if(address.City>0 && address.City<=LIST_LENGHT)
			{
				rt += AdressDB.get(address.City-1);
			}
			if(address.Xian>0 && address.Xian<=LIST_LENGHT)
			{
				rt += AdressDB.get(address.Xian-1);
			}
			if(address.Xiang>0 && address.Xiang<=LIST_LENGHT)
			{
				rt += AdressDB.get(address.Xiang-1);
			}
			if(address.Cun>0 && address.Cun<=LIST_LENGHT)
			{
				rt += AdressDB.get(address.Cun-1);
			}
			//+=AdrCustom
		}
		
		return rt;
	}
}
