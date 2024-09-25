package com.nseit.shareholder1.modelInterfaces;

import java.sql.Timestamp;

public interface BrokerDetails {

	public Long getBrokerId();

	public String getUsername();

	public String getName();

	public String getEMAIL();

	public String getPHONE();

	public String getAuthorisedPersonName();

	public String getOtpVerified();

	public Timestamp getBrokerCreated();

	public Timestamp getBrokerModified();

	public Long getAuthorisedId();

	public String getFileName();

	public Timestamp getAuthorisedLetterCreated();

	public Long getBrokerMapppingId();

	public String getClientId();

	public Timestamp getBrokerClientMappingCreated();

	public String getAPPROVED();

	public Timestamp getBrokerClientModified();

}
