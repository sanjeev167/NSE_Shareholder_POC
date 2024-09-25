package com.nseit.shareholder1.modelInterfaces;

import java.sql.Timestamp;

public interface PacEntityInterface {

	public Long getId();

	public String getName();

	public String getGroupName();

	public Float getPercentageHolding();

	public Timestamp getPeCreatedOn();

//	public Long getGroupId();

	public String getAccept();

	public String getActive();

	public String getProposedChange();

	public String getCreatedBy();

	public Timestamp getPgCreatedOn();

}
