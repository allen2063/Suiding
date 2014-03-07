package com.suiding.service;

import com.suiding.model.*;

public class ServiceFactory {

	static ILeSouShopService<User> UserDAO = null;

	public static ILeSouShopService<User> getUserDAO() {
		if (UserDAO == null) {
			UserDAO = new LeSouShopService<User>(User.class);
		}

		return UserDAO;
	}

	static ILeSouShopService<Role> RoleDAO = null;

	public static ILeSouShopService<Role> getRoleDAO() {
		if (RoleDAO == null) {
			RoleDAO = new LeSouShopService<Role>(Role.class);
		}

		return RoleDAO;
	}

	static ILeSouShopService<Address> AddressDAO = null;

	public static ILeSouShopService<Address> getAddressesDAO() {
		if (AddressDAO == null) {
			AddressDAO = new LeSouShopService<Address>(Address.class);
		}

		return AddressDAO;
	}

	static ILeSouShopService<Business> BusinessDAO = null;

	public static ILeSouShopService<Business> getBusinessesDAO() {
		if (BusinessDAO == null) {
			BusinessDAO = new LeSouShopService<Business>(Business.class);
		}

		return BusinessDAO;
	}

	static ILeSouShopService<Comment> CommentDAO = null;

	public static ILeSouShopService<Comment> getCommentDAO() {
		if (CommentDAO == null) {
			CommentDAO = new LeSouShopService<Comment>(Comment.class);
		}

		return CommentDAO;
	}

	static ILeSouShopService<Consumer> ConsumerDAO = null;

	public static ILeSouShopService<Consumer> getConsumerDAO() {
		if (ConsumerDAO == null) {
			ConsumerDAO = new LeSouShopService<Consumer>(Consumer.class);
		}

		return ConsumerDAO;
	}

	static ILeSouShopService<FacForPro> FacForProDAO = null;

	public static ILeSouShopService<FacForPro> getFacForProDAO() {
		if (FacForProDAO == null) {
			FacForProDAO = new LeSouShopService<FacForPro>(FacForPro.class);
		}

		return FacForProDAO;
	}

	static ILeSouShopService<Facility> FacilityDAO = null;

	public static ILeSouShopService<Facility> getFacilityDAO() {
		if (FacilityDAO == null) {
			FacilityDAO = new LeSouShopService<Facility>(Facility.class);
		}

		return FacilityDAO;
	}

	static ILeSouShopService<HotelProduct> HotelProductDAO = null;

	public static ILeSouShopService<HotelProduct> getHotelProductDAO() {
		if (HotelProductDAO == null) {
			HotelProductDAO = new LeSouShopService<HotelProduct>(
					HotelProduct.class);
		}

		return HotelProductDAO;
	}

	static ILeSouShopService<HotelProductTemplate> HotelProductTemplateDAO = null;

	public static ILeSouShopService<HotelProductTemplate> getHotelProductTemplateDAO() {
		if (HotelProductTemplateDAO == null) {
			HotelProductTemplateDAO = new LeSouShopService<HotelProductTemplate>(
					HotelProductTemplate.class);
		}

		return HotelProductTemplateDAO;
	}

	static ILeSouShopService<Hotel> HotelDAO = null;

	public static ILeSouShopService<Hotel> getHotelDAO() {
		if (HotelDAO == null) {
			HotelDAO = new LeSouShopService<Hotel>(Hotel.class);
		}

		return HotelDAO;
	}

	static ILeSouShopService<Order> OrderDAO = null;

	public static ILeSouShopService<Order> getOrderDAO() {
		if (OrderDAO == null) {
			OrderDAO = new LeSouShopService<Order>(Order.class);
		}

		return OrderDAO;
	}

	static ILeSouShopService<Page> PageDAO = null;

	public static ILeSouShopService<Page> getPageDAO() {
		if (PageDAO == null) {
			PageDAO = new LeSouShopService<Page>(Page.class);
		}

		return PageDAO;
	}

	static ILeSouShopService<Product> ProductDAO = null;

	public static ILeSouShopService<Product> getProductDAO() {
		if (ProductDAO == null) {
			ProductDAO = new LeSouShopService<Product>(Product.class);
		}

		return ProductDAO;
	}

	static ILeSouShopService<Recommendation> RecommendationDAO = null;

	public static ILeSouShopService<Recommendation> getRecommendationDAO() {
		if (RecommendationDAO == null) {
			RecommendationDAO = new LeSouShopService<Recommendation>(
					Recommendation.class);
		}

		return RecommendationDAO;
	}

	static ILeSouShopService<Restaurant> RestaurantDAO = null;

	public static ILeSouShopService<Restaurant> getRestaurantDAO() {
		if (RestaurantDAO == null) {
			RestaurantDAO = new LeSouShopService<Restaurant>(Restaurant.class);
		}

		return RestaurantDAO;
	}

	static ILeSouShopService<RsrProductTemplate> RsrProductTemplateDAO = null;

	public static ILeSouShopService<RsrProductTemplate> getRsrProductTemplateDAO() {
		if (RsrProductTemplateDAO == null) {
			RsrProductTemplateDAO = new LeSouShopService<RsrProductTemplate>(
					RsrProductTemplate.class);
		}

		return RsrProductTemplateDAO;
	}

	static ILeSouShopService<RsrProduct> RsrProductDAO = null;

	public static ILeSouShopService<RsrProduct> getRsrProductDAO() {
		if (RsrProductDAO == null) {
			RsrProductDAO = new LeSouShopService<RsrProduct>(RsrProduct.class);
		}

		return RsrProductDAO;
	}

	static ILeSouShopService<Stall> StallDAO = null;

	public static ILeSouShopService<Stall> getStallDAO() {
		if (StallDAO == null) {
			StallDAO = new LeSouShopService<Stall>(Stall.class);
		}

		return StallDAO;
	}

	static ILeSouShopService<StallProduct> StallProductDAO = null;

	public static ILeSouShopService<StallProduct> getStallProductDAO() {
		if (StallProductDAO == null) {
			StallProductDAO = new LeSouShopService<StallProduct>(
					StallProduct.class);
		}

		return StallProductDAO;
	}

	static ILeSouShopService<StallProductTemplate> StallProductTemplateDAO = null;

	public static ILeSouShopService<StallProductTemplate> getStallProductTemplateDAO() {
		if (StallProductTemplateDAO == null) {
			StallProductTemplateDAO = new LeSouShopService<StallProductTemplate>(
					StallProductTemplate.class);
		}

		return StallProductTemplateDAO;
	}

	static ILeSouShopService<StoreBase> StoreBaseDAO = null;

	public static ILeSouShopService<StoreBase> getStoreBaseDAO() {

		if (StoreBaseDAO == null)

		{
			StoreBaseDAO = new LeSouShopService<StoreBase>(StoreBase.class);

		}

		return StoreBaseDAO;
	}

	static ILeSouShopService<StoreType> StoreTypeDAO = null;

	public static ILeSouShopService<StoreType> getStoreTypeDAO() {
		if (StoreTypeDAO == null) {
			StoreTypeDAO = new LeSouShopService<StoreType>(StoreType.class);
		}

		return StoreTypeDAO;
	}

	static ILeSouShopService<KTV> KTVDAO = null;

	public static ILeSouShopService<KTV> getKTVDAO() {
		if (KTVDAO == null) {
			KTVDAO = new LeSouShopService<KTV>(KTV.class);
		}

		return KTVDAO;
	}

	static ILeSouShopService<KTVProduct> KTVProductDAO = null;

	public static ILeSouShopService<KTVProduct> getKTVProductDAO() {
		if (KTVProductDAO == null) {
			KTVProductDAO = new LeSouShopService<KTVProduct>(KTVProduct.class);
		}

		return KTVProductDAO;
	}

	static ILeSouShopService<KTVProductTemplate> KTVProductTemplateDAO = null;

	public static ILeSouShopService<KTVProductTemplate> getKTVProductTemplateDAO() {
		if (KTVProductTemplateDAO == null) {
			KTVProductTemplateDAO = new LeSouShopService<KTVProductTemplate>(
					KTVProductTemplate.class);
		}

		return KTVProductTemplateDAO;
	}

	static ILeSouShopService<Club> ClubDAO = null;

	public static ILeSouShopService<Club> getClubDAO() {
		if (ClubDAO == null) {
			ClubDAO = new LeSouShopService<Club>(Club.class);
		}

		return ClubDAO;
	}

	static ILeSouShopService<ClubProduct> ClubProductDAO = null;

	public static ILeSouShopService<ClubProduct> getClubProductDAO() {
		if (ClubProductDAO == null) {
			ClubProductDAO = new LeSouShopService<ClubProduct>(
					ClubProduct.class);
		}

		return ClubProductDAO;
	}

	static ILeSouShopService<ClubProductTemplate> ClubProductTemplateDAO = null;

	public static ILeSouShopService<ClubProductTemplate> getClubProductTemplateDAO() {
		if (ClubProductTemplateDAO == null) {
			ClubProductTemplateDAO = new LeSouShopService<ClubProductTemplate>(
					ClubProductTemplate.class);
		}

		return ClubProductTemplateDAO;
	}

	static ILeSouShopService<Area> AreaDAO = null;

	public static ILeSouShopService<Area> getAreaDAO() {
		if (AreaDAO == null) {
			AreaDAO = new LeSouShopService<Area>(Area.class);
		}

		return AreaDAO;
	}

	static ILeSouShopService<BirthFavorite> BirthFavoriteDAO = null;

	public static ILeSouShopService<BirthFavorite> getBirthFavoriteDAO() {
		if (BirthFavoriteDAO == null) {
			BirthFavoriteDAO = new LeSouShopService<BirthFavorite>(
					BirthFavorite.class);
		}

		return BirthFavoriteDAO;
	}

	static ILeSouShopService<BirthInvitation> BirthInvitationDAO = null;

	public static ILeSouShopService<BirthInvitation> getBirthInvitationDAO() {
		if (BirthInvitationDAO == null) {
			BirthInvitationDAO = new LeSouShopService<BirthInvitation>(
					BirthInvitation.class);
		}

		return BirthInvitationDAO;
	}

	static ILeSouShopService<Twitter> BssADDAO = null;

	public static ILeSouShopService<Twitter> getBssADDAO() {
		if (BssADDAO == null) {
			BssADDAO = new LeSouShopService<Twitter>(Twitter.class);
		}

		return BssADDAO;
	}

	static ILeSouShopService<Coupon> CouponDAO = null;

	public static ILeSouShopService<Coupon> getCouponDAO() {
		if (CouponDAO == null) {
			CouponDAO = new LeSouShopService<Coupon>(Coupon.class);
		}

		return CouponDAO;
	}

	static ILeSouShopService<Favorite> FavoriteDAO = null;

	public static ILeSouShopService<Favorite> getFavoriteDAO() {
		if (FavoriteDAO == null) {
			FavoriteDAO = new LeSouShopService<Favorite>(Favorite.class);
		}

		return FavoriteDAO;
	}

	static ILeSouShopService<Photo> PhotoDAO = null;

	public static ILeSouShopService<Photo> getPhotoDAO() {
		if (PhotoDAO == null) {
			PhotoDAO = new LeSouShopService<Photo>(Photo.class);
		}

		return PhotoDAO;
	}
	
	static ILeSouShopService<Feedback> FeedbackDAO = null;

	public static ILeSouShopService<Feedback> getFeedbackDAO() {
		if (FeedbackDAO == null) {
			FeedbackDAO = new LeSouShopService<Feedback>(Feedback.class);
		}

		return FeedbackDAO;
	}
	
	static ILeSouShopService<CouponType> CouponTypeDAO = null;

	public static ILeSouShopService<CouponType> getCouponTypeDAO() {
		if (CouponTypeDAO == null) {
			CouponTypeDAO = new LeSouShopService<CouponType>(CouponType.class);
		}

		return CouponTypeDAO;
	}
	static ILeSouShopService<PdtItemType> PdtItemTypeDAO = null;

	public static ILeSouShopService<PdtItemType> getPdtItemTypeDAO() {
		if (PdtItemTypeDAO == null) {
			PdtItemTypeDAO = new LeSouShopService<PdtItemType>(PdtItemType.class);
		}

		return PdtItemTypeDAO;
	}
	
	static ILeSouShopService<PdtItem> PdtItemDAO = null;

	public static ILeSouShopService<PdtItem> getPdtItemDAO() {
		if (PdtItemDAO == null) {
			PdtItemDAO = new LeSouShopService<PdtItem>(PdtItem.class);
		}

		return PdtItemDAO;
	}
	
	static ILeSouShopService<RsrMenu> MenuDAO = null;

	public static ILeSouShopService<RsrMenu> getMenuDAO() {
		if (MenuDAO == null) {
			MenuDAO = new LeSouShopService<RsrMenu>(RsrMenu.class);
		}

		return MenuDAO;
	}
	
	static ILeSouShopService<HotelOrder> HotelOrderDAO = null;

	public static ILeSouShopService<HotelOrder> getHotelOrderDAO() {
		if (HotelOrderDAO == null) {
			HotelOrderDAO = new LeSouShopService<HotelOrder>(HotelOrder.class);
		}

		return HotelOrderDAO;
	}
	
	static ILeSouShopService<RsrOrder> RsrOrderDAO = null;

	public static ILeSouShopService<RsrOrder> getRsrOrderDAO() {
		if (RsrOrderDAO == null) {
			RsrOrderDAO = new LeSouShopService<RsrOrder>(RsrOrder.class);
		}

		return RsrOrderDAO;
	}
	
	static ILeSouShopService<RsrOrderItem> RsrOrderItemDAO = null;

	public static ILeSouShopService<RsrOrderItem> getRsrOrderItemDAO() {
		if (RsrOrderItemDAO == null) {
			RsrOrderItemDAO = new LeSouShopService<RsrOrderItem>(RsrOrderItem.class);
		}

		return RsrOrderItemDAO;
	}
	
	static ILeSouShopService<ProductAccessories> ProductAccessoriesDAO = null;

	public static ILeSouShopService<ProductAccessories> getProductAccessoriesDAO() {
		if (ProductAccessoriesDAO == null) {
			ProductAccessoriesDAO = new LeSouShopService<ProductAccessories>(ProductAccessories.class);
		}

		return ProductAccessoriesDAO;
	}

	static ILeSouShopService<ExceptionHandler> ExceptionHandlerDAO = null;
	public static ILeSouShopService<ExceptionHandler> getExceptionHandlerDAO() {
		// TODO Auto-generated method stub
		if (ExceptionHandlerDAO == null) {
			ExceptionHandlerDAO = new LeSouShopService<ExceptionHandler>(ExceptionHandler.class);
		}

		return ExceptionHandlerDAO;
	}

	static ILeSouShopService<BusinessPromotion> BusinessPromotionDAO = null;
	public static ILeSouShopService<BusinessPromotion> getBusinessPromotionDAO() {
		// TODO Auto-generated method stub
		if (BusinessPromotionDAO == null) {
			BusinessPromotionDAO = new LeSouShopService<BusinessPromotion>(BusinessPromotion.class);
		}

		return BusinessPromotionDAO;
	}
}
