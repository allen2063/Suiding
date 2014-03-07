package com.suiding.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.suiding.activity.framework.ListViewActivity;
import com.suiding.adapter.StoreBaseListAdapter;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.SuidingApp;
import com.suiding.dao.FavoriteEntityDao;
import com.suiding.domain.IUserDomain;
import com.suiding.entity.FavoriteEntity;
import com.suiding.layoutbind.ModuleNodata;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.Consumer;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.ExtraUtil;

public class ListFavoriteActivity extends ListViewActivity implements
		OnClickListener {

	private int mIndex = 0;
	private boolean mIsChanged = false;

	@Override
	protected final void onCreate() {
		// TODO Auto-generated method stub
		mTitleOther.setTitle(R.string.title_activity_favorite);
	}

	@Override
	protected final ListViewTask getTask(int task) {
		// TODO Auto-generated method stub
		return new FavoriteTask(task);
	}

	@Override
	protected final void onNodata(ModuleNodata nodata) {
		// TODO Auto-generated method stub
		nodata.setDescription(ModuleNodata.TEXT_NOFAVORITE);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (mIsChanged == true && keyCode == KeyEvent.KEYCODE_BACK) {
			this.setResult(RESULT_OK, new Intent());
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mIsChanged == true && v.getId() == ModuleTitleOther.ID_GOBACK) {
			this.setResult(RESULT_OK, new Intent());
			this.finish();
		} else {
			super.onClick(v);
		}
	}

	@Override
	public final boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		if (super.handleMessage(msg) == false) {
			FavoriteTask task = (FavoriteTask) msg.obj;
			if (task.mResult == TaskBase.RESULT_FINISH) {
				switch (task.mTask) {
				case FavoriteTask.TASK_CANCEL:
					mAdapter.remove(mIndex);
					mIsChanged = true;
					break;
				case FavoriteTask.TASK_CANCELOTHER:
					Object obj = mltData.get(mIndex);
					mltData.clear();
					mltData.add(obj);
					mAdapter.setData(mltData);
					mIsChanged = true;
					break;
				case FavoriteTask.TASK_CANCELALL:
					mltData.clear();
					mAdapter.setData(mltData);
					mIsChanged = true;
					break;
				}
			} else if (task.mResult == TaskBase.RESULT_FAIL) {
				//Toast.makeText(this, task.mErrors, Toast.LENGTH_LONG).show();
			}
			hideProgressDialog();
		}
		return true;
	}

	@Override
	protected final ListAdapterBase getAdapter(List<Object> ltData) {
		// TODO Auto-generated method stub
		return new StoreBaseListAdapter(this, ltData);
	}

	@Override
	public final void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		FavoriteTask task = null;
		switch (which) {
		case 0:
			task = new FavoriteTask(FavoriteTask.TASK_CANCEL);
			break;
		case 1:
			task = new FavoriteTask(FavoriteTask.TASK_CANCELOTHER);
			break;
		case 2:
			task = new FavoriteTask(FavoriteTask.TASK_CANCELALL);
			break;
		}
		if (task != null) {
			task.mltData = mltData;
			postTask(task);
			showProgressDialog("正在取消...");
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> absview, View view,
			int index, long id) {
		// TODO Auto-generated method stub
		Builder dialog = new Builder(this);
		dialog.setTitle("选择操作");
		dialog.setIcon(android.R.drawable.ic_dialog_info);
		dialog.setItems(new String[] { "取消收藏", "取消其他", "取消全部" }, this);
		dialog.show();
		// mIndex = mListView.getIndex(index);
		mIndex = mModuleListView.getIndex(index);
		return super.onItemLongClick(absview, view, index, id);
	}

	@Override
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// TODO Auto-generated method stub
		index = mModuleListView.getIndex(index);
		if (index >= 0) {
			Object store = mltData.get(index);
			Intent intent = new Intent();
			intent.setClass(this, DetailStoreActivity.class);
			ExtraUtil.putExtra(DetailStoreActivity.EXTRA_DETAIL, store);
			startActivity(intent);
		}
		// Object obj = mModuleListView.getData(index);
		// if (obj instanceof StoreBase &&
		// !obj.getClass().equals(StoreBase.class)) {
		// ExtraUtil.putExtra(DetailActivity.EXTRA_DETAIL, obj);
		// if (obj instanceof Hotel) {
		// startActivity(new Intent(this, DetailHotelActivity.class));
		// } else if (obj instanceof KTV) {
		// startActivity(new Intent(this, DetailKTVActivity.class));
		// } else if (obj instanceof Restaurant) {
		// startActivity(new Intent(this, DetailRestaurantActivity.class));
		// } else if (obj instanceof Club) {
		// startActivity(new Intent(this, DetailClubActivity.class));
		// } else if (obj instanceof Stall) {
		// startActivity(new Intent(this, DetailStallActivity.class));
		// }
		// }
	}

	private class FavoriteTask extends ListViewTask {

		public static final int TASK_CANCEL = 10000;
		public static final int TASK_CANCELOTHER = 10001;
		public static final int TASK_CANCELALL = 10002;

		public FavoriteTask(int task) {
			super(new Handler(ListFavoriteActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<? extends Object> onLoad() {
			// TODO Auto-generated method stub
			FavoriteEntityDao dao = new FavoriteEntityDao(getBaseContext());
			return FavoriteEntity.listToModel(dao.getAll());
		}

		@Override
		protected List<? extends Object> onRefresh() throws Exception {
			// TODO Auto-generated method stub
			Consumer user = SuidingApp.getLoginUser();
			if (user == null) {
				Thread.sleep(1000);
				return mltData;
			} else {
				IUserDomain domain = DomainFactory.getUserDomain();
				List<StoreBase> ltData = domain.getFavorateStoreByUserID(user
						.getID());
				// 更新缓存
				if (ltData.size() > 0) {
					FavoriteEntityDao dao = new FavoriteEntityDao(
							getBaseContext());
					dao.updateCache(FavoriteEntity.listFormModel(ltData));
				}
				return ltData;
			}
		}

		@Override
		protected boolean onWorking() throws Exception {
			// TODO Auto-generated method stub
			Consumer user = SuidingApp.getLoginUser();
			List<UUID> ltId = new ArrayList<UUID>();
			List<StoreBase> ltStore = new ArrayList<StoreBase>();
			switch (mTask) {
			case TASK_CANCEL:
				if (mltData.size() > mIndex) {
					StoreBase store = (StoreBase) mltData.get(mIndex);
					if (user != null) {
						IUserDomain domain = DomainFactory.getUserDomain();
						domain.UnFavoriteStoreBase(user.getID(), store.getID());
					}
					FavoriteEntityDao dao = new FavoriteEntityDao(
							getBaseContext());
					dao.remove(new FavoriteEntity(store));
				}
				break;
			case TASK_CANCELOTHER:
				if (mltData.size() > mIndex) {
					for (int i = 0; i < mltData.size(); i++) {
						if(mIndex != i){
							StoreBase store = (StoreBase)mltData.get(i);
							ltId.add(store.getID());
							ltStore.add(store);
						}
					}
					IUserDomain domain = DomainFactory.getUserDomain();
					domain.UnFavoriteStoreBase(user.getID(), ltId);

					FavoriteEntityDao dao = new FavoriteEntityDao(
							getBaseContext());
					for (StoreBase store : ltStore) {
						dao.remove(new FavoriteEntity(store));
					}
				}
				break;
			case TASK_CANCELALL:
				if (user != null) {
					for (Object obj : mltData) {
						StoreBase store = (StoreBase) obj;
						ltId.add(store.getID());
						ltStore.add(store);
					}
					IUserDomain domain = DomainFactory.getUserDomain();
					domain.UnFavoriteStoreBase(user.getID(), ltId);

					FavoriteEntityDao dao = new FavoriteEntityDao(
							getBaseContext());
					for (StoreBase store : ltStore) {
						dao.remove(new FavoriteEntity(store));
					}
				}
				break;
			}
			return false;
		}
	}

}
