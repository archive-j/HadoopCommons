package com.gs.hbase;

import java.io.IOException;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.junit.Test;

import com.gs.hbase.connection.HConnectionPool;

import junit.framework.TestCase;

public class TestPool extends TestCase {

	@Test
	public void test() throws IOException, Exception{
		Configuration conf = new Configuration();
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set("hbase.zookeeper.quorum", "gs-pc");
		conf.set("hbase.master", "gs-pc:60000");
		GenericObjectPoolConfig cfg = new GenericObjectPoolConfig();
		cfg.setMaxTotal(10);
		cfg.setMaxWaitMillis(1000);
		cfg.setMinIdle(2);
		cfg.setMaxIdle(5);
		HConnectionPool pool = new HConnectionPool(conf, cfg);
		System.out.println(pool.borrowObject().getTable("page").get(new Get("http://3g.163.com/news/10/0104/10/5S66BASN000125LI.html".getBytes())));
	}
}
