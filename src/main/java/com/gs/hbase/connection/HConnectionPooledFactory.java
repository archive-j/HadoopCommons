package com.gs.hbase.connection;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

public class HConnectionPooledFactory extends
		BasePooledObjectFactory<HConnection> {
	
	@Override
	public boolean validateObject(PooledObject<HConnection> p) {
		return !p.getObject().isClosed();
	}

	@Override
	public void destroyObject(PooledObject<HConnection> p) throws Exception {
		super.destroyObject(p);
		p.getObject().close();
	}

	private Configuration cfg;

	public HConnectionPooledFactory(Configuration cfg) {
		this.cfg = cfg;
	}

	@Override
	public HConnection create() throws Exception {
		return HConnectionManager.createConnection(cfg);
	}

	@Override
	public PooledObject<HConnection> wrap(HConnection conn) {
		return new PooledHConnection(conn);
	}

}
