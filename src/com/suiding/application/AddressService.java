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

	// ��ַ��ʽ
	public static final int GENRAL = 0;
	public static final int DEFAULT = 1;
	public static final int LONG = 2;

	// ID �����ݿ��Ǵ�1��ʼ��,�����Ǵ�0��ʼ�� Ϊ��ͬ�� + 1
	private static final int LIST_LENGHT = 45051 + 1;

	// ��λ���
	private static String[] mAddresses = null;
	// ���ڼ���Address��Task
	private static HashMap<String, LoadTask> mLoadingTask = null;

	/**
	 * ��������
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
	 * ��Address�󶨵��ؼ�TextView
	 * 
	 * @param address
	 * @param view
	 */
	public static void bindAddress(Address address, TextView textview) {
		// TODO Auto-generated method stub
		bindAddress(address, textview,"%s", GENRAL);
	}
	/**
	 * ��Address�󶨵��ؼ�TextView
	 * 
	 * @param address
	 * @param view
	 * @param format
	 */
	public static void bindAddress(Address address, TextView text, String format) {
		bindAddress(address, text,format, GENRAL);
	}
	/**
	 * ��Address�󶨵��ؼ�TextView
	 * 
	 * @param address
	 * @param view
	 * @param type
	 */
	public static void bindAddress(Address address, TextView text, int type) {
		bindAddress(address, text,"%s", type);
	}

	/**
	 * ��Address�󶨵��ؼ�TextView
	 * 
	 * @param address
	 * @param view
	 * @param type
	 */
	public static void bindAddress(Address address, TextView text,String format, int type) {
		// TODO Auto-generated method stub
		if (mLoadingTask != null) {
			if (address != null) {
				text.setText("���ڼ��ص�ַ��Ϣ");
				// ��ȡ��Ϣѭ��
				Looper looper = SuidingApp.getLooper();
				// ��������
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
				// ��������ִ����Ϣ ��������
				if (task != null) {
					task.sendEmptyMessage(AddressTask.WORKING);
				}
			} else {
				text.setText(String.format(format, "���ص�ַ��Ϣʧ��"));
			}
		} else {
			text.setText(String.format(format, "��ַ����û������"));
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
			return "��Ч��ַ";
		}
	}

	private static void postTask(int id, AddressTask task) {
		// TODO Auto-generated method stub
		// ��������ڼ����б����Ѿ����ڵ�ǰid
		LoadTask tTask = mLoadingTask.get("" + id);
		if (tTask == null) {
			// ��ȡ��Ϣѭ��
			Looper looper = SuidingApp.getLooper();
			// �½���������
			LoadTask ltask = new LoadTask(looper, id, task);
			// �ѵ�ǰUrl����ӵ����ڼ����б�
			mLoadingTask.put("" + id, ltask);
			// �������͵�App�߳���ȥִ��
			SuidingApp.postTask(ltask);
		} else {
			// �� ������ͬ�� ����һ��
			tTask.incidentallyTake(task);
		}
	}

	/**
	 * AddressTask ��ַ����
	 * 
	 * @author SCWANG �������Address�е�id �� mAddresses �ж�����ַ��Ϣ ���mAddresses��û��id����Ϣ
	 *         ����ͣ������ ����LoadTask������أ�������ɺ���LoadTask���� ��ͣ��AddressTask����
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
				// getAddress ���ؿ� ��postTask �������
				// ������ ����ͣ ֱ��������ؽ��㱻����
			} else {
				mDetailedAddress += address;
				// mTicks ���� switch ��Χ ��ʾ�������
				if (isFinish(mTicks)) {
					sendEmptyMessage(FINISH);
				} else {
					// �������ִ��
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
					address = mDetailedAddress + "...����ʧ��";
				} else {
					address = "��ַ����ʧ��";
				}
				mTextView.setText(String.format(mFormat, address));
			}
		}
	}

	/**
	 * LoadTask ��������
	 * 
	 * @author SCWANG ������� ID=id ��Address ���Ҽ������֮�� ���� ����ͣ�� AddressTask
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
				// ��ӵ�����
				dao.addWithCheckExistID(new AreaEntity(mLoadArea));
			} else {
				mLoadArea = entity.getModel();
			}
		}

		@Override
		public boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if (mResult == RESULT_FINISH) {
				// ����������
				mLoadArea.Name = CityNameUtil.removeNationArea(mLoadArea.Name);
				mAddresses[mLoadAddressId] = mLoadArea.Name;
				for (AddressTask task : mltAddressTask) {
					// ���� �ȴ���ͣ������
					task.sendEmptyMessage(AddressTask.WORKING);
				}
			} else {
				// �������ʧ�� ֪ͨ��ַ����ʧ��
				for (AddressTask task : mltAddressTask) {
					// ֪ͨ�������ʧ��
					task.sendEmptyMessage(AddressTask.FAILED);
				}
			}
			// ������񼯺�
			mltAddressTask.clear();
			// ���Լ������ڼ��ؼ�����ɾ��
			mLoadingTask.remove("" + mLoadAddressId);
			return true;
		}

	}

	/**
	 * Genral ��ʽ Address����
	 * 
	 * @author SCWANG ��Ϫ��ĳĳ��ĳĳ��
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
			// ��������������ͣ�����ڱ���������߼�����ִ�е����
			return null;
		}

		@Override
		protected boolean isFinish(int ticks) {
			// TODO Auto-generated method stub
			return ticks >= 2;
		}
	}

	/**
	 * Default ��ʽ Address����
	 * 
	 * @author SCWANG ����ʡƽ���������ºϴ峡����22��
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
			// ��������������ͣ�����ڱ���������߼�����ִ�е����
			return null;
		}

		@Override
		protected boolean isFinish(int ticks) {
			// TODO Auto-generated method stub
			return ticks >= 4;
		}
	}

	/**
	 * Long ��ʽ Address����
	 * 
	 * @author SCWANG �й�����ʡƽ���������ºϴ峡����22��
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
				return "�й�";
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
			// ��������������ͣ�����ڱ���������߼�����ִ�е����
			return null;
		}

		@Override
		protected boolean isFinish(int ticks) {
			// TODO Auto-generated method stub
			return ticks >= 5;
		}
	}
}
