package com.suiding.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.suiding.activity.framework.ListViewActivity;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.domain.IProductDomain;
import com.suiding.layoutbind.ItemProduct;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.Page;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.util.ExtraUtil;

public class ListProductActivity extends ListViewActivity {
	
	public static final String EXTRA_STORE = "EXTRA_STORE";
	public static final String EXTRA_LIST = "EXTRA_LIST";

	private StoreBase mStore = null;
	private List<Product> mltProduct = null;

	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate() {
		// TODO Auto-generated method stub
		mStore = (StoreBase)ExtraUtil.getExtra(EXTRA_STORE);
		mltProduct = (List<Product>) ExtraUtil.getExtra(EXTRA_LIST);

		postTask(new ProductTask(ListViewTask.TASK_REFRESH));
	}

	@Override
	protected ListViewTask getTask(int task) {
		// TODO Auto-generated method stub
		return new ProductTask(task);
	}

	@Override
	protected ListAdapterBase getAdapter(List<Object> ltdata) {
		// TODO Auto-generated method stub
		return new ProductListAdapter(this, ltdata);
	}

	@Override
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// TODO Auto-generated method stub
		if (absview == mModuleListView.getRefreshableView()) {
			index = mModuleListView.getIndex(index);
			if (index >= 0) {
				Product product = (Product)mltData.get(index);
				ExtraUtil.putExtra(DetailProductActivity.EXTRA_DETAIL,product);
				ExtraUtil.putExtra(DetailProductActivity.EXTRA_OB_STORE,mStore);
				startActivity(new Intent(this, DetailProductActivity.class));
			}
		}
	}

	private class ProductTask extends ListViewTask {

		public ProductTask(int task) {
			super(new Handler(ListProductActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<? extends Object> onLoad() {
			// TODO Auto-generated method stub
			return mltProduct;
		}

		@Override
		protected List<? extends Object> onRefresh() throws Exception {
			// TODO Auto-generated method stub
			Page page = new Page(PAGESIZE, 0);
			IProductDomain domain = DomainFactory.getProductDomain();
			return domain.GetListBySbID(mStore.getID(), page);
		}

		@Override
		protected List<? extends Object> onMore(Page page) throws Exception {
			// TODO Auto-generated method stub
			page = new Page(PAGESIZE, mltProduct.size());
			IProductDomain domain = DomainFactory.getProductDomain();
			return domain.GetListBySbID(mStore.getID(), page);
		}

	}

	// public static List<Product> getDefaultProduct()
	// {
	// List<Product> mltProduct = new ArrayList<Product>();
	// mltProduct.add(new Product(null, "上品房", null, "", 7, 500));
	// mltProduct.add(new Product(null, "中等房", null, "", 7, 400));
	// mltProduct.add(new Product(null, "套间", null, "", 7, 700));
	// mltProduct.add(new Product(null, "单人间", null, "", 7, 400));
	// mltProduct.add(new Product(null, "浦东间", null, "", 7, 200));
	// mltProduct.add(new Product(null, "廉价房", null, "", 7, 400));
	// return mltProduct;
	// }

	public static class ProductListAdapter extends ListAdapterBase {
		public ProductListAdapter(Context context, List<Object> ltdata) {
			super(context, ltdata);
		}

		@Override
		protected ILayoutItem getItemLayout() {
			// TODO Auto-generated method stub
			return new ItemProduct();
		}

	}

}