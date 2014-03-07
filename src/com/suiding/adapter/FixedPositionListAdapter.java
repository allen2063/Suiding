package com.suiding.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.suiding.activity.R;
import com.suiding.entity.AreaEntity;
import com.suiding.layoutbind.framework.ILayoutItem;


/**
 * @author zuo
 *         列表适配器
 */
public class FixedPositionListAdapter extends BaseAdapter
{
    private List<AreaEntity> mltCity;
    private LayoutInflater mInflater;

    public FixedPositionListAdapter(Context context, List<AreaEntity> ltCity)
    {
        mltCity = ltCity;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount()
    {
        return mltCity == null ? 0 : mltCity.size();
    }

    public Object getItem(int position)
    {
        return mltCity.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

	public View getView(int position, View view, ViewGroup parent)
    {
	    CityItem tCityItem = null;
        if (view == null)
        {
            view = mInflater.inflate(R.layout.listitem_fixedcity, null);
            view.setTag(new CityItem(view,position,mltCity.get(position)));
        }
        else
        {
            tCityItem = (CityItem) view.getTag();
            tCityItem.Binding(position,mltCity.get(position));
        }
        return view;
    }

	public static final class CityItem implements ILayoutItem, OnClickListener
    {
		public interface CityItemListener{
			void onCityItemClick(CityItem item);
		}

        public View mLayout;
        public TextView mTvHint;
        public TextView mTvName;
        public AreaEntity mAreaEntity;
		private CityItemListener mListener;
		public int gary = 0;

        public CityItem(View view,int index, AreaEntity area)
        {
            Handle(view);
            Binding(index,area);
        }
        
        public CityItem()
        {
        }
        
        public void setCityItemListener(CityItemListener mListener) {
			this.mListener = mListener;
		}
        /**
         * 从视图中取出控件
         * @param view
         */
        public void Handle(View view)
        {
            mTvHint = (TextView)view.findViewById(R.id.fixed_list_item_first_char);
            mTvName = (TextView)view.findViewById(R.id.fixed_list_item_name);
            mLayout = (View)mTvName.getParent();
            gary = view.getResources().getColor(R.color.gray);
        }

        /**
         * 将数据绑定到控件显示
         * @param area 
         * @param tData
         */
        public void Binding(int index, AreaEntity area)
        {
            //AreaEntity tData = mltCity.get(index);
        	mAreaEntity = area;
            mTvName.setText(area.Name);
            mTvHint.setVisibility(View.GONE);
            mTvName.setTextColor(gary);
//            try
//            {
//                int idx = index - 1;
//                char curchar = mltCity.get(index).Sell.charAt(0); //当前字符
//                char prechar = idx >= 0 ? mltCity.get(idx).Sell.charAt(0): ' '; //前一个字符
//                if (curchar != prechar)
//                { //如果不相等时显示
//                    if (SpellUtil.isWord(curchar))
//                    {
//                        mTvHint.setVisibility(View.VISIBLE);
//                        mTvHint.setText(String.valueOf(curchar).toUpperCase(Locale.ENGLISH));
//                    }
//                    else if (index == 0)
//                    {
//                        mTvHint.setVisibility(View.VISIBLE);
//                        mTvHint.setText("#");
//                    }
//                    else
//                    {
//                        mTvHint.setVisibility(View.GONE);
//                    }
//                }
//                else
//                {
//                    mTvHint.setVisibility(View.GONE);
//                }
//            }
//            catch (Exception e)
//            {
//                // TODO: handle exception
//            }
        }

		@Override
		public void Binding(Object data) {
			// TODO Auto-generated method stub
			if(data instanceof AreaEntity){
				mAreaEntity = (AreaEntity)data;
	            mTvName.setText(mAreaEntity.Name);
	            mLayout.setOnClickListener(this);
	            mTvHint.setVisibility(View.GONE);
			}
		}

		@Override
		public int getLayoutId() {
			// TODO Auto-generated method stub
			return R.layout.listitem_fixedcity;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mListener != null){
				mListener.onCityItemClick(this);
			}
		}
    }

}
