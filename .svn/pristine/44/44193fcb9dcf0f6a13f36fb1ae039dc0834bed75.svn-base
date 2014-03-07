package com.suiding.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import com.suiding.dao.AreaEntityDao;
import com.suiding.domain.IAreaDomain;
import com.suiding.entity.AreaEntity;
import com.suiding.model.Address;
import com.suiding.model.Area;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.CityNameUtil;

public class AddressService {

	// 地址格式
	public static final int GENRAL = 0;
	public static final int DEFAULT = 1;
	public static final int LONG = 2;

	// ID 在数据库是从1开始的,数据是从0开始的 为了同步 + 1
	private static final int LIST_LENGHT = 45051 + 1;

	// 定位结果
	private static String[] mAddresses = null;
	// 正在加载Address的Task
	private static HashMap<String, LoadTask> mLoadingTask = null;

	/**
	 * 启动服务
	 */
	public static void startUp(Context context) {
		if (mLoadingTask == null) {
			mAddresses = new String[LIST_LENGHT];
			mLoadingTask = new HashMap<String, LoadTask>();

			AreaEntityDao dao = new AreaEntityDao(context);
			List<AreaEntity> ltaddress = dao.getAllOrderById();

			int index = 0;
			for (AreaEntity entity : ltaddress) {
				while (index < entity.ID) {
					mAddresses[index++] = null;
				}
				entity.Name = CityNameUtil.removeNationArea(entity.Name);
				mAddresses[index++] = entity.Name;
			}
		}
	}

	/**
	 * 将Address绑定到控件TextView
	 * 
	 * @param address
	 * @param view
	 */
	public static void bindAddress(Address address, TextView textview) {
		// TODO Auto-generated method stub
		bindAddress(address, textview,"%s", GENRAL);
	}
	/**
	 * 将Address绑定到控件TextView
	 * 
	 * @param address
	 * @param view
	 * @param format
	 */
	public static void bindAddress(Address address, TextView text, String format) {
		bindAddress(address, text,format, GENRAL);
	}
	/**
	 * 将Address绑定到控件TextView
	 * 
	 * @param address
	 * @param view
	 * @param type
	 */
	public static void bindAddress(Address address, TextView text, int type) {
		bindAddress(address, text,"%s", type);
	}

	/**
	 * 将Address绑定到控件TextView
	 * 
	 * @param address
	 * @param view
	 * @param type
	 */
	public static void bindAddress(Address address, TextView text,String format, int type) {
		// TODO Auto-generated method stub
		if (mLoadingTask != null) {
			if (address != null) {
				text.setText("正在加载地址信息");
				// 获取消息循环
				Looper looper = SuidingApp.getLooper();
				// 创建任务
				AddressTask task = null;
				switch (type) {
				case GENRAL:
					task = new GenralTask(looper, address, text, format);
					break;
				case DEFAULT:
					task = new DefaultTask(looper, address, text, format);
					break;
				case LONG:
					task = new LongTask(looper, address, text, format);
					break;
				}
				// 抛送任务执行消息 激活任务
				if (task != null) {
					task.sendEmptyMessage(AddressTask.WORKING);
				}
			} else {
				text.setText(String.format(format, "加载地址信息失败"));
			}
		} else {
			text.setText(String.format(format, "地址服务没有启动"));
		}
	}

	private static String getAddress(int id, AddressTask task) {
		// TODO Auto-generated method stub
		if (id > 0 && id <= LIST_LENGHT) {
			String address = mAddresses[id];
			if (address != null) {
				return address;
			} else {
				postTask(id, task);
				return null;
			}
		} else {
			return "无效地址";
		}
	}

	private static void postTask(int id, AddressTask task) {
		// TODO Auto-generated method stub
		// 如果在正在加载列表中已经存在当前id
		LoadTask tTask = mLoadingTask.get("" + id);
		if (tTask == null) {
			// 获取消息循环
			Looper looper = SuidingApp.getLooper();
			// 新建加载任务
			LoadTask ltask = new LoadTask(looper, id, task);
			// 把当前Url从添加到正在加载列表
			mLoadingTask.put("" + id, ltask);
			// 把任务发送到App线程中去执行
			SuidingApp.postTask(ltask);
		} else {
			// 让 任务相同的 绑定在一起
			tTask.incidentallyTake(task);
		}
	}

	/**
	 * AddressTask 地址任务
	 * 
	 * @author SCWANG 负责根据Address中的id 从 mAddresses 中读出地址信息 如果mAddresses中没有id的信息
	 *         这暂停本任务 启动LoadTask网络加载，加载完成后由LoadTask激活 暂停的AddressTask任务
	 */
	private static abstract class AddressTask extends Handler {

		private static final int WORKING = 0;
		private static final int FINISH = 1;
		private static final int FAILED = 2;

		private int mTicks = 0;
		private TextView mTextView;
		private String mFormat = "%s";
		private String mDetailedAddress = "";

		protected Address mAddress;

		public AddressTask(Looper looper, Address address, TextView textview,String format) {
			super(looper);
			// TODO Auto-generated method stub
			mFormat = format;
			mAddress = address;
			mTextView = textview;
			mTextView.setTag(this);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case WORKING:
				this.working();
				break;
			case FINISH:
				this.finish();
				break;
			case FAILED:
				this.failed();
				break;
			}
		}

		protected abstract String getAddressTicks(int ticks);

		protected abstract boolean isFinish(int ticks);

		private void working() {
			// TODO Auto-generated method stub
			String address = getAddressTicks(mTicks);

			if (address == null) {
				// getAddress 返回空 并postTask 网络加载
				// 本任务 被暂停 直到网络加载结算被激活
			} else {
				mDetailedAddress += address;
				// mTicks 超过 switch 范围 表示任务完成
				if (isFinish(mTicks)) {
					sendEmptyMessage(FINISH);
				} else {
					// 否则继续执行
					mTicks++;
					sendEmptyMessage(WORKING);
				}
			}
		}

		private void finish() {
			// TODO Auto-generated method stub
			if (mTextView.getTag() == this) {
				mTextView.setText(mDetailedAddress);
				mTextView.setText(String.format(mFormat, mDetailedAddress));
			}
		}

		private void failed() {
			// TODO Auto-generated method stub
			if (mTextView.getTag() == this) {
				String address = "";
				if (mDetailedAddress.length() > 0) {
					address = mDetailedAddress + "...加载失败";
				} else {
					address = "地址加载失败";
				}
				mTextView.setText(String.format(mFormat, address));
			}
		}
	}

	/**
	 * LoadTask 加载任务
	 * 
	 * @author SCWANG 负责加载 ID=id 的Address 并且加载完成之后 激活 被暂停的 AddressTask
	 */
	private static class LoadTask extends TaskBase {

		public int mLoadAddressId = 0;
		public List<AddressTask> mltAddressTask = new ArrayList<AddressTask>();
		public Area mLoadArea = null;

		public LoadTask(Looper looper, int id, AddressTask task) {
			super(looper);
			// TODO Auto-generated constructor stub
			mLoadAddressId = id;
			mltAddressTask.add(task);
		}

		public void incidentallyTake(AddressTask task) {
			// TODO Auto-generated method stub
			mltAddressTask.add(task);
		}

		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			AreaEntityDao dao = new AreaEntityDao(SuidingApp.getApp());
			AreaEntity entity = dao.getById(mLoadAddressId);
			if (entity == null) {
				IAreaDomain domain = DomainFactory.getAreaDomain();
				mLoadArea = domain.getAreaByID(mLoadAddressId);
				// 添加到缓存
				dao.addWithCheckExistID(new AreaEntity(mLoadArea));
			} else {
				mLoadArea = entity.getModel();
			}
		}

		@Override
		public boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if (mResult == RESULT_FINISH) {
				// 网络加载完成
				mLoadArea.Name = CityNameUtil.removeNationArea(mLoadArea.Name);
				mAddresses[mLoadAddressId] = mLoadArea.Name;
				for (AddressTask task : mltAddressTask) {
					// 激活 等待暂停的任务
					task.sendEmptyMessage(AddressTask.WORKING);
				}
			} else {
				// 网络加载失败 通知地址加载失败
				for (AddressTask task : mltAddressTask) {
					// 通知网络加载失败
					task.sendEmptyMessage(AddressTask.FAILED);
				}
			}
			// 清空任务集合
			mltAddressTask.clear();
			// 把自己从正在加载集合中删除
			mLoadingTask.remove("" + mLoadAddressId);
			return true;
		}

	}

	/**
	 * Genral 格式 Address任务
	 * 
	 * @author SCWANG 花溪区某某街某某号
	 */
	private static class GenralTask extends AddressTask {

		public GenralTask(Looper looper, Address address, TextView textview,String format) {
			super(looper, address, textview, format);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected String getAddressTicks(int ticks) {
			// TODO Auto-generated method stub
			switch (ticks) {
			case 0:
				return getAddress(mAddress.Xian, this);
			case 1:
				return getAddress(mAddress.Xiang, this);
			case 2:
				return mAddress.Custom == null?"":mAddress.Custom;
//				return getAddress(mAddress.Cun, this);
			}
			// 发生错误，任务被暂停不会在被激活（正常逻辑不会执行到这里）
			return null;
		}

		@Override
		protected boolean isFinish(int ticks) {
			// TODO Auto-generated method stub
			return ticks >= 2;
		}
	}

	/**
	 * Default 格式 Address任务
	 * 
	 * @author SCWANG 贵州省平塘县鼠场乡新合村场坝组22号
	 */
	private static class DefaultTask extends AddressTask {

		public DefaultTask(Looper looper, Address address, TextView textview,String format) {
			super(looper, address, textview, format);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected String getAddressTicks(int ticks) {
			// TODO Auto-generated method stub
			switch (ticks) {
			case 0:
				return getAddress(mAddress.Province, this);
			case 1:
				return getAddress(mAddress.City, this);
			case 2:
				return getAddress(mAddress.Xian, this);
			case 3:
				return getAddress(mAddress.Xiang, this);
			case 4:
				return mAddress.Custom == null?"":mAddress.Custom;
//				return getAddress(mAddress.Cun, this);
			}
			// 发生错误，任务被暂停不会在被激活（正常逻辑不会执行到这里）
			return null;
		}

		@Override
		protected boolean isFinish(int ticks) {
			// TODO Auto-generated method stub
			return ticks >= 4;
		}
	}

	/**
	 * Long 格式 Address任务
	 * 
	 * @author SCWANG 中国贵州省平塘县鼠场乡新合村场坝组22号
	 */
	private static class LongTask extends AddressTask {

		public LongTask(Looper looper, Address address, TextView textview,String format) {
			super(looper, address, textview, format);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected String getAddressTicks(int ticks) {
			// TODO Auto-generated method stub
			switch (ticks) {
			case 0:
				return "中国";
			case 1:
				return getAddress(mAddress.Province, this);
			case 2:
				return getAddress(mAddress.City, this);
			case 3:
				return getAddress(mAddress.Xian, this);
			case 4:
				return getAddress(mAddress.Xiang, this);
			case 5:
				return mAddress.Custom == null?"":mAddress.Custom;
//				return getAddress(mAddress.Cun, this);
			}
			// 发生错误，任务被暂停不会在被激活（正常逻辑不会执行到这里）
			return null;
		}

		@Override
		protected boolean isFinish(int ticks) {
			// TODO Auto-generated method stub
			return ticks >= 5;
		}
	}
}
