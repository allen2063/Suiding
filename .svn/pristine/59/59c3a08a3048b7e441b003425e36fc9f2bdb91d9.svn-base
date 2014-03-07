package com.suiding.activity;

import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.suiding.activity.framework.ListViewActivity;
import com.suiding.adapter.CouponListAdapter;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.domain.ICouponDomain;
import com.suiding.model.Coupon;
import com.suiding.model.Page;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.util.ExtraUtil;

public class ListCouponActivity extends ListViewActivity {
	
	public static final String EXTRA_STORE = "EXTRA_STORE";
	public static final String EXTRA_LIST = "EXTRA_LIST";

	private StoreBase mStore = null;
	private List<Coupon> mltCoupon = null;

	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate() {
		// TODO Auto-generated method stub
		mStore = (StoreBase)ExtraUtil.getExtra(EXTRA_STORE, null);
		mltCoupon = (List<Coupon>) ExtraUtil.getExtra(EXTRA_LIST);
		
		ListView listview =	mModuleListView.getRefreshableView();
		listview.setDividerHeight(0);

		mTitleOther.setTitle(R.string.title_activity_listcoupon);
		postTask(new CouponTask(ListViewTask.TASK_REFRESH));
	}

	@Override
	protected ListViewTask getTask(int task) {
		// TODO Auto-generated method stub
		return new CouponTask(task);
	}

	@Override
	protected ListAdapterBase getAdapter(List<Object> ltdata) {
		// TODO Auto-generated method stub
		return new CouponListAdapter(this, ltdata);
	}
	
	@Override
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// TODO Auto-generated method stub
		index = mModuleListView.getIndex(index);
		if (index >= 0) {
			Coupon coupon = (Coupon)mltData.get(index);
			if(coupon.Product != null){
				ExtraUtil.putExtra(DetailProductActivity.EXTRA_IT_TYPE,mStore.Type);
				ExtraUtil.putExtra(DetailProductActivity.EXTRA_OB_STORE,mStore);
				ExtraUtil.putExtra(DetailProductActivity.EXTRA_OB_COUPON,coupon);
				ExtraUtil.putExtra(DetailProductActivity.EXTRA_DETAIL,coupon.Product);
				startActivity(new Intent(this, DetailProductActivity.class));
			}
		}
	}
	
	private class CouponTask extends ListViewTask {

		public CouponTask(int task) {
			super(new Handler(ListCouponActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<? extends Object> onLoad() {
			// TODO Auto-generated method stub
			return mltCoupon;
		}

		@Override
		protected List<? extends Object> onRefresh() throws Exception {
			// TODO Auto-generated method stub
			Page page = new Page(PAGESIZE, 0);
			ICouponDomain domain = DomainFactory.getCouponDomain();
			return domain.getListBySbID(mStore.getID(), page);
		}

		@Override
		protected List<? extends Object> onMore(Page page) throws Exception {
			// TODO Auto-generated method stub
			page = new Page(PAGESIZE, mltCoupon.size());
			ICouponDomain domain = DomainFactory.getCouponDomain();
			return domain.getListBySbID(mStore.getID(), page);
		}

	}
}