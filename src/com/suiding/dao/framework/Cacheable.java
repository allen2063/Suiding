package com.suiding.dao.framework;

public interface Cacheable<Target>
{
    /**
     * �����ض��ӿ� ���»���
     * @param ltEntity
     */
    public void updateCache(Target ltEntity);
    /**
     * �����ض��ӿ� ׷�ӻ���
     * @param ltEntity
     */
    public void appendCache(Target ltEntity);

}
