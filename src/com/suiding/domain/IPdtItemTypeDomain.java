package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.Page;
import com.suiding.model.PdtItemType;
import com.suiding.util.LeSouException;

public interface IPdtItemTypeDomain extends IDomain<PdtItemType> {

	List<PdtItemType> GetListBySbID(UUID id, Page page) throws LeSouException;
}
