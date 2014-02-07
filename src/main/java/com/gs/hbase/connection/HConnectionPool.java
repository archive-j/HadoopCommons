package com.gs.hbase.connection;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnection;

/**
 * @author gaoshen
 * HBase连接池
 */
public final class HConnectionPool extends GenericObjectPool<HConnection>{
	
	/**
	 * @param hbaseCfg  HBase配置
	 * @param poolCfg	连接池配置
	 */
	public HConnectionPool(Configuration hbaseCfg,GenericObjectPoolConfig poolCfg){
		super(new HConnectionPooledFactory(hbaseCfg), poolCfg);
	}
}
