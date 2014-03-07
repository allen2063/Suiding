package com.suiding.model;

/**
 * 分页查询类 
 * 分页查询开始索引、开始分页等从 1 开始索引
 * @author Administrator
 *
 */
public class Page {
    public boolean IsASC = true;
    public String Order = "";
    int FirstResult = 0;
    int MaxResult = 0;
    public static final int PAGEMODE_PAGING = 1;
    public static final int PAGEMODE_ADDITIAONAL = 2;
    

    public Page(){}

    /**
     * 构造一个新的分页查询示例索引是从0开始
     * 
     * @param sizeOrmax 分页大小 或 追加查询大小
     * @param curOrbegin 分页起始页 或 追加起始页
     * 默认 追加：PAGEMODE_ADDITIAONAL 
     */
    public Page(int max,int begin)
    {
        this.FirstResult = begin;
        this.MaxResult = max;
    }
    /**
     * 构造一个新的分页查询示例，索引是从0开始
     * 
     * @param sizeOrmax 分页大小 或 追加查询大小
     * @param curOrbegin 分页起始页 或 追加起始页
     * @param pageMode 分页模式 分页：PAGEMODE_PAGING 或者 追加：PAGEMODE_ADDITIAONAL 
     */
    public Page(int sizeOrmax,int curOrbegin,int pageMode)
    {
    	curOrbegin = curOrbegin<0?0:curOrbegin;
    	
    	if(pageMode == Page.PAGEMODE_PAGING)
    	{
        	this.FirstResult = sizeOrmax*(curOrbegin);
        	this.MaxResult = sizeOrmax; 
    	}
    	else if(pageMode == Page.PAGEMODE_ADDITIAONAL)
    	{
    		this.FirstResult = curOrbegin;
    		this.MaxResult = sizeOrmax;
    	}
    }
    /**
     * 构造一个新的分页查询示例
     * 
     * @param sizeOrmax 分页大小 或 追加查询大小
     * @param curOrbegin 分页起始页 或 追加起始页
     * @param pageMode 分页模式 分页：PAGEMODE_PAGING 或者 追加：PAGEMODE_ADDITIAONAL 
     * @param order 排序列
     */
    public Page(int sizeOrmax,int curOrbegin,int pageMode,String order)
    {
    	this(sizeOrmax,curOrbegin,pageMode);
    	this.Order = order;
    }

    /**
     * 构造一个新的分页查询示例
     * 
     * @param sizeOrmax 分页大小 或 追加查询大小
     * @param curOrbegin 分页起始页 或 追加起始页
     * @param pageMode 分页模式 分页：PAGEMODE_PAGING 或者 追加：PAGEMODE_ADDITIAONAL 
     * @param isAsc 是否升序
     */
    public Page(int sizeOrmax,int curOrbegin,int pageMode,boolean isAsc)
    {
    	this(sizeOrmax,curOrbegin,pageMode);
    	this.IsASC = isAsc;
    }

    /**
     * 构造一个新的分页查询示例
     * 
     * @param sizeOrmax 分页大小 或 追加查询大小
     * @param curOrbegin 分页起始页 或 追加起始页
     * @param pageMode 分页模式 分页：PAGEMODE_PAGING 或者 追加：PAGEMODE_ADDITIAONAL 
     * @param order 排序列
     * @param isAsc 是否升序
     */
    public Page(int sizeOrmax,int curOrbegin,int pageMode,String order,boolean isAsc)
    {
    	this(sizeOrmax,curOrbegin,pageMode);
    	this.Order = order;
    	this.IsASC = isAsc;
    }
    
//    
//    int CurPage = 0;
//    int PageSize = 0;
//    int QueryMode = 0;
//    long ItemCount = 0;
//    int BegItem = 0;
//    int EndItem = 0;
//    long TotalSize = 0; 
//    /**
//     * 新建Page新实例 通过指定开始和结束条件查询
//     * @param itemCount 总条目
//     * @param begItem 开始条目
//     * @param endItem 结束条目
//     */
//    public Page(long itemCount,int begItem,int endItem){
//    	this.QueryByScroll(itemCount, begItem, endItem);
//    }
//
//    /**
//     * 新建Page新实例 通过分页查询
//     * @param pageSize 分页大小
//     * @param curPage 查询页码
//     * @param itemCount 总条目 可以不指定
//     */
//    public Page(int pageSize,int curPage,long itemCount)
//    {
//    	this.QueryByPage(itemCount, pageSize, curPage);
//    }
//    
//    /**
//     * 通过指定开始和结束条件查询
//     * @param itemCount 总条目
//     * @param begItem 开始条目
//     * @param endItem 结束条目
//     */
//    public void QueryByScroll(long itemCount,int begItem,int endItem)
//    {
//    	this.ItemCount = itemCount;
//    	this.BegItem = begItem;
//    	this.EndItem = endItem;
//    	this.setQueryMode(1);
//    }
//    
//    /**
//     * 根据指定开始和结束项来获取内容
//     * 索引从 1 开始 且开始索引应该大于结束索引
//     * 如果索引为负值 或者 开始索引大于结束索引将会抛出Exception异常
//     * @param begItem 开始索引
//     * @param endItem 结束索引
//     */
//    public Page QueryByScroll(int begItem,int endItem) throws Exception
//    {
//    	if(begItem<0 || endItem< 0)
//    	{
//    		throw new Exception("索引值不能为负值");
//    	}
//    	
//    	if(begItem>endItem)
//    	{
//    		throw new Exception("开始索引不能大于结束索引");
//    	}
//    	
//    	this.BegItem = begItem;
//    	this.EndItem = endItem;
//    	//设置内部约定查询值
//    	this.QueryMode = 2;
//    	return this;
//    }
//    /**
//     * 通过分页查询
//     * @param itemCount 总条目 可以不指定
//     * @param pageSize 分页大小
//     * @param curPage 查询页码
//     */
//    public void QueryByPage(long itemCount,int pageSize,int curPage)
//    {
//    	this.ItemCount = itemCount;
//    	this.PageSize = pageSize;
//    	this.CurPage = curPage;
//    	this.setQueryMode(0);
//    }
//    
//	public long getTotalSize() {
//		return TotalSize;
//	}
//
//	public void setTotalSize(int totalSize) {
//		TotalSize = totalSize;
//	}
//
//	/**
//	 * 获取查询模式
//	 * @return
//	 * 0 : 表示默认的使用分页查询
//     * 1 : 表示使用指定开始结束查询
//     * 2 : 表示根据指定开始结束查询 ，该过程是由通过QueryByScroll转换过来,内部使用,不可以自己设定
//     * 3 : 表示通过分页查询 ，该过程是由通过0、1模式转换过来,内部使用,不可以自己设定
//	 */
//	public int getQueryMode() {
//		return QueryMode;
//	}
//	
//	/**
//	 * 设置查询模式
//	 * 该项设置需要在设置完ItemCoun、BegItem、EndItem后调用
//	 * @param queryMode 
//	 * 
//	 * 0 : 表示默认的使用分页查询
//     * 1 : 表示使用指定开始结束查询
//     * 2 : 表示根据指定开始结束查询 ，该过程是由通过QueryByScroll转换过来,内部使用,不可以自己设定
//     * 3 : 表示通过分页查询 ，该过程是由通过0、1模式转换过来,内部使用,不可以自己设定
//	 */
//	public void setQueryMode(int queryMode) {
//		
//		if(this.ItemCount<=0 || QueryMode >= 2) return;
//		
//		QueryMode = queryMode;		
//		switch(QueryMode)
//		{
//		case 0:
//			this.TotalSize = this.ItemCount/this.PageSize;
//			this.QueryMode = 3;
//			break;
//		case 1:
//			if(this.BegItem>this.ItemCount || 
//					this.EndItem >this.ItemCount || this.EndItem<=this.BegItem)return;
//			this.PageSize = this.EndItem-this.BegItem;
//			this.TotalSize = this.ItemCount/this.PageSize;
//			if(this.PageSize == 1)
//			{
//				this.CurPage = this.BegItem;
//			}
//			else
//			{
//				this.CurPage = (int)(this.BegItem/this.TotalSize);
//			}
//			this.QueryMode = 3;
//			break;
//		default:
//			break;
//		}
//	}
//	public long getItemCount() {
//		return ItemCount;
//	}
//	public void setItemCount(long itemCount) {
//		ItemCount = itemCount;
//	}
//	public int getBegItem() {
//		return BegItem;
//	}
//	/**
//	 * 设置查询的开始条目 
//	 * 注意：如果要使该项设置有效， 在设置该项之后设置QueryMode为1
//	 * @param begItem
//	 */
//	public void setBegItem(int begItem) {
//		BegItem = begItem;
//	}
//	public int getEndItem() {
//		return EndItem;
//	}
//	/**
//	 * 设置查询的结束条目
//	 * 注意：如果要使该项设置有效， 在设置该项之后设置QueryMode为1
//	 * @param endItem
//	 */
//	public void setEndItem(int endItem) {
//		EndItem = endItem;
//	}
//	public boolean isIsASC() {
//		return IsASC;
//	}
//	public void setIsASC(boolean isASC) {
//		IsASC = isASC;
//	}
//	public int getCurPage() {
//		return CurPage;
//	}
//	public void setCurPage(int curPage) {
//		CurPage = curPage;
//	}
//	public int getPageSize() {
//		return PageSize;
//	}
//	public void setPageSize(int pageSize) {
//		PageSize = pageSize;
//	}
//    
}
