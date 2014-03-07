package com.suiding.domain;

import java.util.List;
import java.util.UUID;

import com.suiding.model.BirthInvitation;
import com.suiding.model.Page;
import com.suiding.util.LeSouException;

public interface IBirthInvitationDomain extends IDomain<BirthInvitation> {
	List<BirthInvitation> getListByUserID(UUID userid,Page page) throws LeSouException;
	List<BirthInvitation> getListBySbID(UUID sbid,Page page) throws LeSouException;
}