package com.gs.hbase.connection;

import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.hadoop.hbase.client.HConnection;

public class PooledHConnection extends DefaultPooledObject<HConnection> {

	public PooledHConnection(HConnection object) {
		super(object);
	}

}
