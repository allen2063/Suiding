package com.suiding.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class LocationUtil
{
    private static LocationManager mManager = null;
    
    private static List<String> ltDisableProvider = new ArrayList<String>();

    private static String mProvider = null;
    
    private static Date mlastdate = new Date(0);
    private static Location mLocation = null;

    /**
     * ��λ��ȡ Location
     * @param context
     * @return Location
     * @throws WaitException �ȴ�֪ͨ
     * @throws Exception ������ʾ
     */
    public static Location getLocation(Context context) throws WaitException, Exception
    {
        //ʵ����һ��LocationManager����  
        if(mManager==null){
            mManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        }
        
        //Provider������  
        if(mProvider == null){
            //ʹ�ñ�׼���ϣ���ϵͳ�Զ�ѡ����õ����λ���ṩ�����ṩλ��
            Criteria tCriteria = new Criteria();
            tCriteria.setAccuracy(Criteria.ACCURACY_FINE);   //�߾���  
            tCriteria.setAltitudeRequired(false);    //��Ҫ�󺣰�  
            tCriteria.setBearingRequired(false); //��Ҫ��λ  
            tCriteria.setCostAllowed(false); //�������л���  
            tCriteria.setPowerRequirement(Criteria.POWER_LOW);   //�͹��� 
            
            mProvider = mManager.getBestProvider(tCriteria,true);
            mProvider = mProvider == null?LocationManager.NETWORK_PROVIDER:mProvider;
        }
        /* 
         * �ڶ���������ʾ���µ����ڣ���λΪ���룻�����������ĺ����ʾ��С����������λ���� 
         * �趨ÿ30�����һ���Զ���λ 
         */
        mManager.requestLocationUpdates(mProvider, 30000, 50,mListener);

        //ͨ�����һ�εĵ���λ�������Location����  
        mLocation = mManager.getLastKnownLocation(mProvider);
        if(mLocation == null){
            if(ltDisableProvider.size() >= 2){
                throw new Exception("��λ�����ã�������GPS�����߲鿴�Ƿ񱻹���������ƶ�λ��");
            }
            throw new WaitException("�ȴ�һ������");
        }else{
            if(TimeSpan.FromDate(mlastdate, new Date()).getTotalMinutes() > 30){
                mLocation = null;
                mlastdate = new Date(0);
                throw new WaitException("�ȴ�һ������");
            }
        }
        
        return mLocation;
    }

    /**
     * ���� Location ��ȡ ��ַ��Ϣ
     * @param location
     * @param context
     * @return Address �б�
     * @throws IOException �����쳣
     */
    public static List<Address> getAddressByLocation(Location location,Context context) throws IOException
    {
        //��ȡ��γ��
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        
        //�˶�����ͨ����γ������ȡ��Ӧ�ĳ��е���Ϣ  
        Geocoder tGeocoder = new Geocoder(context,Locale.CHINA);

        //������γ�� 
        List<Address> ltAdress = tGeocoder.getFromLocation(lat, lng, 1); 
        
        return ltAdress;
    }

    /**
     * ���� Adress ��ȡ ����������Ϣ
     * @param ltAdress Adress�б�
     */
    public static String getCityNameByAddress(List<Address> ltAdress)
    {
        String tCityName = "";

        //�˶�����ͨ����γ������ȡ��Ӧ�ĳ��е���Ϣ  
        if (ltAdress != null && ltAdress.size() > 0)
        {
            for (Address address : ltAdress)
            {
                tCityName += address.getLocality();
            }
        }
        return tCityName;
    }
    /**
     * ͨ����γ�Ȼ�ȡ��ַ��Ϣ����һ�ַ���
     * @param latitude
     * @param longitude
     * @return ������
     */
    public static String GetCityNameByTude(Location location)
    {
        String addr = "";
        /* 
         * Ҳ������http://maps.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s������������������Ӣ�ĵ�ַ 
         * ��Կ�������дһ��key=abc 
         * output=csv,Ҳ������xml��json������ʹ��csv���ص��������෽�����     
         */
        String url = String.format(
                "http://ditu.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s",
                String.valueOf(location.getLatitude()), 
                String.valueOf(location.getLongitude()));
        URL myURL = null;
        URLConnection httpsConn = null;
        try
        {

            myURL = new URL(url);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            return null;
        }

        try
        {

            httpsConn = (URLConnection) myURL.openConnection();

            if (httpsConn != null)
            {
                InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null)
                {
                    String[] retList = data.split(",");
                    if (retList.length > 2 && ("200".equals(retList[0])))
                    {
                        addr = retList[2];
                    }
                    else
                    {
                        addr = "";
                    }
                }
                insr.close();
            }
        }
        catch (IOException e)
        {

            e.printStackTrace();
            return null;
        }
        return addr;
    }

    /**
     * ��λ�ı�ʱ���������е���
     */
    private final static LocationListener mListener = new LocationListener()
    {
        public void onLocationChanged(Location location)
        {
            mLocation = location;
            mlastdate = new Date();
            //�Ƴ�����������ֻ��һ��widget��ʱ������������õ�  
            mManager.removeUpdates(mListener);
        }

        public void onProviderDisabled(String provider)
        {
            Boolean isHasDisable = false;
            for (int i = 0 ; i < ltDisableProvider.size() ; i++)
            {
                String tProvider = ltDisableProvider.get(i);
                if(tProvider.endsWith(provider)){
                    isHasDisable = true;
                }
            }
            if(isHasDisable == false){
                ltDisableProvider.add(provider);
            }
            this.setBestProvider();
        }


        @Override
        public void onProviderEnabled(String provider)
        {
            // TODO Auto-generated method stub
            for (int i = 0 ; i < ltDisableProvider.size() ; i++)
            {
                String tProvider = ltDisableProvider.get(i);
                if(tProvider.endsWith(provider)){
                    ltDisableProvider.remove(i);
                    break;
                }
            }
            this.setBestProvider();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            // TODO Auto-generated method stub
            
        }

        private void setBestProvider()
        {
            // TODO Auto-generated method stub
            Boolean isDisableNet = false;
            Boolean isDisableGps = false;
            for (int i = 0 ; i < ltDisableProvider.size() ; i++)
            {
                String tProvider = ltDisableProvider.get(i);
                if(tProvider.endsWith(LocationManager.NETWORK_PROVIDER)){
                    isDisableNet = true;
                }
                if(tProvider.endsWith(LocationManager.GPS_PROVIDER)){
                    isDisableGps = true;
                }
            }
            if(isDisableNet == false){
                mProvider = LocationManager.NETWORK_PROVIDER;
            }else if(isDisableGps == false){
                mProvider = LocationManager.GPS_PROVIDER;
            }else{
                mProvider = null;
            }
        }
    };

    /**
     * ����ȴ� �쳣
     * @author SCWANG
     *  ������յ��쳣 �������� �Ե�Ƭ���ٵ���һ�νӿ�
     */
    public static class WaitException extends Throwable{

        private static final long serialVersionUID = 1L;
        
        private String Message;
        
        public WaitException(String msg){
            Message = msg;
        }

        public String getMessage()
        {
            return Message;
        }
        
    }

}
