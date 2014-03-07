package com.suiding.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.suiding.entity.framework.EntityBase;

import android.location.Address;


public class AddressEntity implements EntityBase<Address>
{
    public double Latitude;
    public double Longitude;
    
    public String AdminArea;
    public String CountryCode;
    public String CountryName;
    public String FeatureName;
    public String Locality;
    public String Phone;
    public String PostalCode;
    public String Premises;
    public String SubAdminArea;
    public String SubLocality;
    public String SubThoroughfare;
    public String Thoroughfare;
    public String Url;

    public AddressEntity(){
        
    }
    
    public AddressEntity(Address address)
    {
        Latitude = address.getLatitude();
        Longitude = address.getLongitude();
        
        AdminArea = address.getAdminArea();
        CountryCode = address.getCountryCode();
        CountryName = address.getCountryName();
        FeatureName = address.getFeatureName();
        Locality = address.getLocality();
        Phone = address.getPhone();
        PostalCode = address.getPostalCode();
        Premises = address.getPremises();
        SubAdminArea = address.getSubAdminArea();
        SubLocality = address.getSubLocality();
        SubThoroughfare= address.getSubThoroughfare();
        Thoroughfare = address.getThoroughfare();
        Url = address.getUrl();
    }

    @Override
    public Address getModel()
    {
        // TODO Auto-generated method stub
      Address tAddress = new Address(Locale.CHINESE);
      
      tAddress.setLatitude(Latitude);
      tAddress.setLongitude(Longitude);
      
      tAddress.setAdminArea(AdminArea);
      tAddress.setCountryCode(CountryCode);
      tAddress.setCountryName(CountryName);
      tAddress.setFeatureName(FeatureName);
      tAddress.setLocality(Locality);
      tAddress.setPhone(Phone);
      tAddress.setPostalCode(PostalCode);
      tAddress.setPremises(Premises);
      tAddress.setSubAdminArea(SubAdminArea);
      tAddress.setSubLocality(SubLocality);
      tAddress.setSubThoroughfare(SubThoroughfare);
      tAddress.setThoroughfare(Thoroughfare);
      tAddress.setUrl(Url);
      
      return tAddress;
    }
    
    public static List<AddressEntity> listFormAddress(List<Address> ltAddress)
    {
        // TODO Auto-generated method stub
        return listFormModel(ltAddress);
    }
    
    public static List<Address> toListAddress(List<AddressEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        return listToModel(ltEntity);
    }

    /**
     * List<Model> ×ª»» List<Entity> 
     * @param ltEntity
     * @return
     */
    public static List<AddressEntity> listFormModel(List<Address> ltModel)
    {
        // TODO Auto-generated method stub
        List<AddressEntity> ltEntity = new ArrayList<AddressEntity>();
        for (Address model : ltModel)
        {
            ltEntity.add(new AddressEntity(model));
        }
        return ltEntity;
    }
    
    /**
     * List<Entity> ×ª»» List<Model> 
     * @param ltEntity
     * @return
     */
    public static List<Address> listToModel(List<AddressEntity> ltEntity)
    {
        // TODO Auto-generated method stub
        List<Address> ltModel = new ArrayList<Address>();
        for (AddressEntity entity : ltEntity)
        {
            ltModel.add(entity.getModel());
        }
        return ltModel;
    }

}
