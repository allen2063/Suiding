package com.suiding.activity;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.suiding.activity.framework.ListViewActivity;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.ImageService;
import com.suiding.constant.ImageFolderEnum;
import com.suiding.constant.ImageSize;
import com.suiding.layoutbind.framework.ILayoutItem;
import com.suiding.model.Page;
import com.suiding.model.PdtItem;
import com.suiding.model.StoreBase;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.util.ExtraUtil;

public class ListPdtItemActivity extends ListViewActivity {

	public static final String EXTRA_OB_STORE = "EXTRA_OB_STORE";
	public static final String EXTRA_LT_LIST = "EXTRA_LT_LIST";
	public static final String EXTRA_BL_SELECTOR = "EXTRA_BL_SELECTOR";

	public static final String RESULT_OB_SELECTOR = "RESULT_OB_SELECTOR";

	private boolean mIsSelector = false;
	private StoreBase mStore = null;
	private List<PdtItem> mltRsrMenu = null;

	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate() {
		// TODO Auto-generated method stub
		mIsSelector = ExtraUtil.getExtraBoolean(EXTRA_BL_SELECTOR, false);
		mStore = (StoreBase) ExtraUtil.getExtra(EXTRA_OB_STORE);
		mltRsrMenu = (List<PdtItem>) ExtraUtil.getExtra(EXTRA_LT_LIST);

		postTask(new RsrMenuTask(ListViewTask.TASK_REFRESH));
	}

	@Override
	protected ListViewTask getTask(int task) {
		// TODO Auto-generated method stub
		return new RsrMenuTask(task);
	}

	@Override
	protected ListAdapterBase getAdapter(List<Object> ltdata) {
		// TODO Auto-generated method stub
		return new RsrMenuListAdapter(this, ltdata);
	}

	@Override
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// TODO Auto-generated method stub
		if (absview == mModuleListView.getRefreshableView()) {
			index = mModuleListView.getIndex(index);
			if (index >= 0) {
				PdtItem product = (PdtItem) mltData.get(index);
				if (mIsSelector == false) {
					// ExtraUtil.putExtra(DetailRsrMenuActivity.EXTRA_DETAIL,product);
					// ExtraUtil.putExtra(DetailRsrMenuActivity.EXTRA_STORE,mStore);
					// startActivity(new Intent(this,
					// DetailRsrMenuActivity.class));
				} else {
					ExtraUtil.putExtra(RESULT_OB_SELECTOR, product);
					setResult(RESULT_OK);
					finish();
				}
			}
		}
	}

	private class RsrMenuTask extends ListViewTask {

		public RsrMenuTask(int task) {
			super(new Handler(ListPdtItemActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<? extends Object> onLoad() {
			// TODO Auto-generated method stub
			return mltRsrMenu;
		}

		@Override
		protected List<? extends Object> onRefresh() throws Exception {
			// TODO Auto-generated method stub
			mStore.toString();
			// Page page = new Page(PAGESIZE, 0);
			// IRsrMenuDomain domain = DomainFactory.getRsrMenuDomain();
			// return domain.GetListBySbID(mStore.getID(), page);
			return mltRsrMenu;
		}

		@Override
		protected List<? extends Object> onMore(Page page) throws Exception {
			// TODO Auto-generated method stub
			// page = new Page(PAGESIZE, mltRsrMenu.size());
			// IRsrMenuDomain domain = DomainFactory.getRsrMenuDomain();
			// return domain.GetListBySbID(mStore.getID(), page);
			return mltRsrMenu;
		}

	}

	public static class RsrMenuListAdapter extends ListAdapterBase {
		public RsrMenuListAdapter(Context context, List<Object> ltdata) {
			super(context, ltdata);
		}

		@Override
		protected ILayoutItem getItemLayout() {
			// TODO Auto-generated method stub
			return new ItemPdtItem();
		}

	}

	public static class ItemPdtItem implements ILayoutItem {

		public PdtItem mRsrMenu;
		public TextView mTvName = null;
		public TextView mTvMoney = null;
		public TextView mTvStatus = null;
		public TextView mTvDiscard = null;
		public TextView mTvDetail = null;
		public ImageView mTvHeadimg = null;

		public ItemPdtItem() {
		}

		/**
		 * 从视图中取出控件
		 * 
		 * @param view
		 */
		@Override
		public void Handle(View view) {
			mTvName = (TextView) view.findViewById(R.id.listitem_product_name);
			mTvMoney = (TextView) view
					.findViewById(R.id.listitem_product_price);
			mTvStatus = (TextView) view
					.findViewById(R.id.listitem_product_status);
			mTvDiscard = (TextView) view
					.findViewById(R.id.listitem_product_discard);
			mTvDetail = (TextView) view
					.findViewById(R.id.listitem_product_detail);
			mTvHeadimg = (ImageView) view
					.findViewById(R.id.listitem_product_headimg);
			mTvDiscard.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 删除线
		}

		/**
		 * 将数据绑定到控件显示
		 * 
		 * @param review
		 */
		@Override
		public void Binding(Object data) {
			// TODO Auto-generated method stub
			if (data instanceof PdtItem) {
				String format = mTvName.getContext().getString(
						R.string.listitem_product_money_format);
				mRsrMenu = (PdtItem) data;
				mTvName.setText(mRsrMenu.Name);
				mTvDetail.setText(mRsrMenu.Remark);
				mTvMoney.setText(String.format(format, mRsrMenu.NowPrice));
				mTvDiscard.setText(String.format(format, mRsrMenu.Price));

				mTvStatus.setVisibility(View.GONE);

				ImageService.bindImage(mRsrMenu.HeadImg, mTvHeadimg,
						ImageFolderEnum.HEAD_PRODUCT, ImageSize.SMALLEST);
			}
		}

		@Override
		public int getLayoutId() {
			// TODO Auto-generated method stub
			return R.layout.listitem_product;
		}
	}
}