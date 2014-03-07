package com.suiding.service;

import java.util.ArrayList;
import java.util.List;

import com.suiding.domain.IAddressDomain;
import com.suiding.domain.IAreaDomain;
import com.suiding.domain.IBirthFavoriteDomain;
import com.suiding.domain.IBirthInvitationDomain;
import com.suiding.domain.IBusinessDomain;
import com.suiding.domain.IBusinessPromotionDomain;
import com.suiding.domain.IClubDomain;
import com.suiding.domain.IClubProductDomain;
import com.suiding.domain.IClubProductTemplateDomain;
import com.suiding.domain.ICommentDomain;
import com.suiding.domain.IConsumerDomain;
import com.suiding.domain.ICouponDomain;
import com.suiding.domain.ICouponTypeDomain;
import com.suiding.domain.IExceptionHandlerDomain;
import com.suiding.domain.IFacForProDomain;
import com.suiding.domain.IFacilityDomain;
import com.suiding.domain.IFavoriteDomain;
import com.suiding.domain.IFeedbackDomain;
import com.suiding.domain.IHotelDomain;
import com.suiding.domain.IHotelOrderDomain;
import com.suiding.domain.IHotelProductDomain;
import com.suiding.domain.IHotelProductTemplateDomain;
import com.suiding.domain.IKTVDomain;
import com.suiding.domain.IKTVProductDomain;
import com.suiding.domain.IKTVProductTemplateDomain;
import com.suiding.domain.IMenuDomain;
import com.suiding.domain.IOrderDomain;
import com.suiding.domain.IPdtItemDomain;
import com.suiding.domain.IPdtItemTypeDomain;
import com.suiding.domain.IPhotoDomain;
import com.suiding.domain.IProductAccessoriesDomain;
import com.suiding.domain.IProductDomain;
import com.suiding.domain.IRecommendationDomain;
import com.suiding.domain.IRestaurantDomain;
import com.suiding.domain.IRoleDomain;
import com.suiding.domain.IRsrOrderDomain;
import com.suiding.domain.IRsrOrderItemDomain;
import com.suiding.domain.IRsrProductDomain;
import com.suiding.domain.IRsrProductTemplateDomain;
import com.suiding.domain.IStallDomain;
import com.suiding.domain.IStallProductDomain;
import com.suiding.domain.IStallProductTemplateDomain;
import com.suiding.domain.IStoreBaseDomain;
import com.suiding.domain.IStoreTypeDomain;
import com.suiding.domain.ITwitterDomain;
import com.suiding.domain.IUserDomain;
import com.suiding.domain.impl.AddressDomainImpl;
import com.suiding.domain.impl.AreaDomainImpl;
import com.suiding.domain.impl.BirthFavoriteDomainImpl;
import com.suiding.domain.impl.BirthInvitationDomainImpl;
import com.suiding.domain.impl.BusinessDomainImpl;
import com.suiding.domain.impl.BusinessPromotionDomainImpl;
import com.suiding.domain.impl.ClubDomainImpl;
import com.suiding.domain.impl.ClubProductDomainImpl;
import com.suiding.domain.impl.ClubProductTemplateDomainImpl;
import com.suiding.domain.impl.CommentDomainImpl;
import com.suiding.domain.impl.ConsumerDomainImpl;
import com.suiding.domain.impl.CouponDomainImpl;
import com.suiding.domain.impl.CouponTypeDomainImpl;
import com.suiding.domain.impl.ExceptionHandlerDomainImpl;
import com.suiding.domain.impl.FacForProDomainImpl;
import com.suiding.domain.impl.FacilityDomainImpl;
import com.suiding.domain.impl.FavoriteDomainImpl;
import com.suiding.domain.impl.FeedbackDomainImpl;
import com.suiding.domain.impl.HotelDomainImpl;
import com.suiding.domain.impl.HotelOrderDomainImpl;
import com.suiding.domain.impl.HotelProductDomainImpl;
import com.suiding.domain.impl.HotelProductTemplateDomainImpl;
import com.suiding.domain.impl.KTVDomainImpl;
import com.suiding.domain.impl.KTVProductDomainImpl;
import com.suiding.domain.impl.KTVProductTemplateDomainImpl;
import com.suiding.domain.impl.MenuDomainImpl;
import com.suiding.domain.impl.OrderDomainImpl;
import com.suiding.domain.impl.PdtItemDomainImpl;
import com.suiding.domain.impl.PdtItemTypeDomainImpl;
import com.suiding.domain.impl.PhotoDomainImpl;
import com.suiding.domain.impl.ProductAccessoriesDomainImpl;
import com.suiding.domain.impl.ProductDomainImpl;
import com.suiding.domain.impl.RecommendationDomainImpl;
import com.suiding.domain.impl.RestaurantDomainImpl;
import com.suiding.domain.impl.RoleDomainImpl;
import com.suiding.domain.impl.RsrOrderDomainImpl;
import com.suiding.domain.impl.RsrOrderItemDomainImpl;
import com.suiding.domain.impl.RsrProductDomainImpl;
import com.suiding.domain.impl.RsrProductTemplateDomainImpl;
import com.suiding.domain.impl.StallDomainImpl;
import com.suiding.domain.impl.StallProductDomainImpl;
import com.suiding.domain.impl.StallProductTemplateDomainImpl;
import com.suiding.domain.impl.StoreBaseDomainImpl;
import com.suiding.domain.impl.StoreTypeDomainImpl;
import com.suiding.domain.impl.TwitterDomainImpl;
import com.suiding.domain.impl.UserDomainImpl;
import com.suiding.util.LeSouException;

public class DomainFactory {

	static List<String> AdressDB = null;

	public static List<String> getAdressDB() {
		if (AdressDB == null) {
			try {
				AdressDB = DomainFactory.AddressDomain.GetAddressDB();
			} catch (LeSouException e) {
				e.printStackTrace();
				AdressDB = new ArrayList<String>();
			}
		}
		return AdressDB;
	}

	static IStoreTypeDomain StoreTypeDomain = null;

	public static IStoreTypeDomain getStoreTypeDomain() {
		if (StoreTypeDomain == null) {
			StoreTypeDomain = new StoreTypeDomainImpl();
		}
		return StoreTypeDomain;
	}

	static IStallDomain StallDomain = null;

	public static IStallDomain getStallDomain() {
		if (StallDomain == null) {
			StallDomain = new StallDomainImpl();
		}
		return StallDomain;
	}

	static IStallProductDomain StallProductDomain = null;

	public static IStallProductDomain getStallProductDomain() {
		if (StallProductDomain == null) {
			StallProductDomain = new StallProductDomainImpl();
		}
		return StallProductDomain;
	}

	static IStallProductTemplateDomain StallProductTemplateDomain = null;

	public static IStallProductTemplateDomain getStallProductTemplateDomain() {
		if (StallProductTemplateDomain == null) {
			StallProductTemplateDomain = new StallProductTemplateDomainImpl();
		}
		return StallProductTemplateDomain;
	}

	static IRsrProductDomain RsrProductDomain = null;

	public static IRsrProductDomain getRsrProductDomain() {
		if (RsrProductDomain == null) {
			RsrProductDomain = new RsrProductDomainImpl();
		}
		return RsrProductDomain;
	}

	static IRoleDomain RoleDomain = null;

	public static IRoleDomain getRoleDomain() {
		if (RoleDomain == null) {
			RoleDomain = new RoleDomainImpl();
		}
		return RoleDomain;
	}

	static IRestaurantDomain RestaurantDomain = null;

	public static IRestaurantDomain getRestaurantDomain() {
		if (RestaurantDomain == null) {
			RestaurantDomain = new RestaurantDomainImpl();
		}
		return RestaurantDomain;
	}

	static IRecommendationDomain RecommendationDomain = null;

	public static IRecommendationDomain getRecommendationDomain() {
		if (RecommendationDomain == null) {
			RecommendationDomain = new RecommendationDomainImpl();
		}
		return RecommendationDomain;
	}

	static IOrderDomain OrderDomain = null;

	public static IOrderDomain getOrderDomain() {
		if (OrderDomain == null) {
			OrderDomain = new OrderDomainImpl();
		}
		return OrderDomain;
	}

	static IHotelProductDomain HotelProductDomain = null;

	public static IHotelProductDomain getHotelProductDomain() {
		if (HotelProductDomain == null) {
			HotelProductDomain = new HotelProductDomainImpl();
		}
		return HotelProductDomain;
	}

	static IHotelDomain HotelDomain = null;

	public static IHotelDomain getHotelDomain() {
		if (HotelDomain == null) {
			HotelDomain = new HotelDomainImpl();
		}
		return HotelDomain;
	}

	static IFacForProDomain FacForProDomain = null;

	public static IFacForProDomain getFacForProDomain() {
		if (FacForProDomain == null) {
			FacForProDomain = new FacForProDomainImpl();
		}
		return FacForProDomain;
	}

	static IConsumerDomain ConsumerDomain = null;

	public static IConsumerDomain getConsumerDomain() {
		if (ConsumerDomain == null) {
			ConsumerDomain = new ConsumerDomainImpl();
		}
		return ConsumerDomain;
	}

	static ICommentDomain CommentDomain = null;

	public static ICommentDomain getCommentDomain() {
		if (CommentDomain == null) {
			CommentDomain = new CommentDomainImpl();
		}
		return CommentDomain;
	}

	static IBusinessDomain BusinessDomain = null;

	public static IBusinessDomain getBusinessDomain() {
		if (BusinessDomain == null) {
			BusinessDomain = new BusinessDomainImpl();
		}
		return BusinessDomain;
	}

	static IProductDomain ProductBaseDomain = null;

	public static IProductDomain getProductDomain() {
		if (ProductBaseDomain == null) {
			ProductBaseDomain = new ProductDomainImpl();
		}
		return ProductBaseDomain;
	}

	static IFacilityDomain FacilityDomain = null;

	public static IFacilityDomain getFacilityDomain() {
		if (FacilityDomain == null) {
			FacilityDomain = new FacilityDomainImpl();
		}
		return FacilityDomain;
	}

	static IHotelProductTemplateDomain HotelProductTemplateDomain = null;

	public static IHotelProductTemplateDomain getHotelProductTemplateDomain() {
		if (HotelProductTemplateDomain == null) {
			HotelProductTemplateDomain = new HotelProductTemplateDomainImpl();
		}
		return HotelProductTemplateDomain;
	}

	static IStoreBaseDomain StoreBaseDomain = null;

	public static IStoreBaseDomain getStoreBaseDomain() {
		if (StoreBaseDomain == null) {
			StoreBaseDomain = new StoreBaseDomainImpl();
		}
		return StoreBaseDomain;
	}

	static IAddressDomain AddressDomain = null;

	public static IAddressDomain getAddressDomain() {
		if (AddressDomain == null) {
			AddressDomain = new AddressDomainImpl();
		}
		return AddressDomain;
	}

	static IUserDomain userDomain = null;

	public static IUserDomain getUserDomain() {
		if (userDomain == null) {
			userDomain = new UserDomainImpl();
		}
		return userDomain;
	}

	static IRsrProductTemplateDomain rsrproductTemplateDomain = null;

	public static IRsrProductTemplateDomain getRsrProductTemplateDomain() {
		if (rsrproductTemplateDomain == null) {
			rsrproductTemplateDomain = new RsrProductTemplateDomainImpl();
		}
		return rsrproductTemplateDomain;
	}

	static IKTVDomain KTVDomain = null;

	public static IKTVDomain getKTVDomain() {
		if (KTVDomain == null) {
			KTVDomain = new KTVDomainImpl();
		}
		return KTVDomain;
	}

	static IKTVProductDomain ktvproductDomain = null;

	public static IKTVProductDomain getKTVProductDomain() {
		if (ktvproductDomain == null) {
			ktvproductDomain = new KTVProductDomainImpl();
		}
		return ktvproductDomain;
	}

	static IKTVProductTemplateDomain KTVProductTemplateDomain = null;

	public static IKTVProductTemplateDomain getKTVProductTemplateDomain() {
		if (KTVProductTemplateDomain == null) {
			KTVProductTemplateDomain = new KTVProductTemplateDomainImpl();
		}
		return KTVProductTemplateDomain;
	}

	static IClubDomain ClubDomain = null;

	public static IClubDomain getClubDomain() {
		if (ClubDomain == null) {
			ClubDomain = new ClubDomainImpl();
		}
		return ClubDomain;
	}
	
	static IClubProductDomain ClubProductDomain = null;

	public static IClubProductDomain getClubProductDomain() {
		if (ClubProductDomain == null) {
			ClubProductDomain = new ClubProductDomainImpl();
		}
		return ClubProductDomain;
	}
	
	static IClubProductTemplateDomain ClubProductTemplateDomain = null;

	public static IClubProductTemplateDomain getClubProductTemplateDomain() {
		if (ClubProductTemplateDomain == null) {
			ClubProductTemplateDomain = new ClubProductTemplateDomainImpl();
		}
		return ClubProductTemplateDomain;
	}
	

	static IAreaDomain AreaDomain = null;

	public static IAreaDomain getAreaDomain() {
		if (AreaDomain == null) {
			AreaDomain = new AreaDomainImpl();
		}
		return AreaDomain;
	}
	
	static IBirthFavoriteDomain BirthFavoriteDomain = null;
	public static IBirthFavoriteDomain getBirthFavoriteDomain() {
		if (BirthFavoriteDomain == null) {
			BirthFavoriteDomain = new BirthFavoriteDomainImpl();
		}
		return BirthFavoriteDomain;
	}
	static IBirthInvitationDomain BirthInvitationDomain = null;

	public static IBirthInvitationDomain getBirthInvitationDomain() {
		if (BirthInvitationDomain == null) {
			BirthInvitationDomain = new BirthInvitationDomainImpl();
		}
		return BirthInvitationDomain;
	}
	

	static ITwitterDomain TwitterDomain = null;

	public static ITwitterDomain getTwitterDomain() {
		if (TwitterDomain == null) {
			TwitterDomain = new TwitterDomainImpl();
		}
		return TwitterDomain;
	}
	

	static ICouponDomain CouponDomain = null;

	public static ICouponDomain getCouponDomain() {
		if (CouponDomain == null) {
			CouponDomain = new CouponDomainImpl();
		}
		return CouponDomain;
	}
	
	static IFavoriteDomain FavoriteDomain = null;

	public static IFavoriteDomain getFavoriteDomain() {
		if (FavoriteDomain == null) {
			FavoriteDomain = new FavoriteDomainImpl();
		}
		return FavoriteDomain;
	}
	
	static IPhotoDomain PhotoDomain = null;

	public static IPhotoDomain getPhotoDomain() {
		if (PhotoDomain == null) {
			PhotoDomain = new PhotoDomainImpl();
		}
		return PhotoDomain;
	}
	
	static IFeedbackDomain FeedbackDomain = null;

	public static IFeedbackDomain getFeedbackDomain() {
		if (FeedbackDomain == null) {
			FeedbackDomain = new FeedbackDomainImpl();
		}
		return FeedbackDomain;
	}
	
	static ICouponTypeDomain CouponTypeDomain = null;

	public static ICouponTypeDomain getCouponTypeDomain() {
		if (CouponTypeDomain == null) {
			CouponTypeDomain = new CouponTypeDomainImpl();
		}
		return CouponTypeDomain;
	}
	
	static IPdtItemTypeDomain PdtItemTypeDomain = null;

	public static IPdtItemTypeDomain getPdtItemTypeDomain() {
		if (PdtItemTypeDomain == null) {
			PdtItemTypeDomain = new PdtItemTypeDomainImpl();
		}
		return PdtItemTypeDomain;
	}
	
	static IPdtItemDomain PdtItemDomain = null;

	public static IPdtItemDomain getPdtItemDomain() {
		if (PdtItemDomain == null) {
			PdtItemDomain = new PdtItemDomainImpl();
		}
		return PdtItemDomain;
	}
	
	static IMenuDomain MenuDomain = null;

	public static IMenuDomain getMenuDomain() {
		if (MenuDomain == null) {
			MenuDomain = new MenuDomainImpl();
		}
		return MenuDomain;
	}
	
	static IHotelOrderDomain HotelOrderDomain = null;

	public static IHotelOrderDomain getHotelOrderDomain() {
		if (HotelOrderDomain == null) {
			HotelOrderDomain = new HotelOrderDomainImpl();
		}
		return HotelOrderDomain;
	}
	
	static IRsrOrderDomain RsrOrderDomain = null;

	public static IRsrOrderDomain getRsrOrderDomain() {
		if (RsrOrderDomain == null) {
			RsrOrderDomain = new RsrOrderDomainImpl();
		}
		return RsrOrderDomain;
	}
	
	static IRsrOrderItemDomain RsrOrderItemDomain = null;

	public static IRsrOrderItemDomain getRsrOrderItemDomain() {
		if (RsrOrderItemDomain == null) {
			RsrOrderItemDomain = new RsrOrderItemDomainImpl();
		}
		return RsrOrderItemDomain;
	}
	
	static IProductAccessoriesDomain ProductAccessoriesDomain = null;

	public static IProductAccessoriesDomain getProductAccessoriesDomain() {
		if (ProductAccessoriesDomain == null) {
			ProductAccessoriesDomain = new ProductAccessoriesDomainImpl();
		}
		return ProductAccessoriesDomain;
	}

	static IExceptionHandlerDomain ExceptionHandlerDomain = null;
	public static IExceptionHandlerDomain getExceptionHandlerDomain() {
		// TODO Auto-generated method stub
		if (ExceptionHandlerDomain == null) {
			ExceptionHandlerDomain = new ExceptionHandlerDomainImpl();
		}
		return ExceptionHandlerDomain;
	}
	
	static IBusinessPromotionDomain BusinessPromotionDomain = null;
	public static IBusinessPromotionDomain getBusinessPromotionDomain() {
		// TODO Auto-generated method stub
		if (BusinessPromotionDomain == null) {
			BusinessPromotionDomain = new BusinessPromotionDomainImpl();
		}
		return BusinessPromotionDomain;
	}
}
