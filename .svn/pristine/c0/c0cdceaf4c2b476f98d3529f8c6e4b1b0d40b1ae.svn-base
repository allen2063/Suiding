package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.Page;
import com.suiding.model.PdtItem;
import com.suiding.util.LeSouException;

public interface IPdtItemDomain extends IDomain<PdtItem> {
	public List<PdtItem> GetListBySbID(UUID sbid, Page page) throws LeSouException;
    public List<PdtItem> GetListByPbID(UUID pbid, Page page) throws LeSouException;
}
