package com.gs.hbase.session;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Table;

import com.gs.utils.Item;

public class HSession {
	private static Logger LOG = LoggerFactory.getLogger(HSession.class);
	private HConnection conn;

	public HSession(HConnection connection) {
		this.conn = connection;
	}

	public void save(Object obj) throws IOException {
		Method[] m = obj.getClass().getDeclaredMethods();
		Set<Item<String, Object>> items = new HashSet<Item<String, Object>>();
		String tablename = null;
		if(obj.getClass().isAnnotationPresent(Table.class)){
			tablename = obj.getClass().getAnnotation(Table.class).name();
		}else{
			tablename = obj.getClass().getSimpleName().toLowerCase();
		}
		String pkName = null;
		for (int j = 0; j < m.length; j++) {
			if (m[j].getName().startsWith("get")) {
				String fieldName = m[j].getName().split("get")[1].toLowerCase();
				if (m[j].isAnnotationPresent(PK.class)) {
					pkName = fieldName;
					continue;
				}
				try {
					items.add(new Item<String, Object>(fieldName, m[j]
							.invoke(obj)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
			}
		}
		if (pkName == null) {
			LOG.error("必须含有PK标记的字段");
			throw new IllegalArgumentException("必须含有PK标记的字段");
		}
		Put put = new Put(pkName.getBytes());
		for (Item<String, Object> item : items) {
			put.add(item.k.getBytes(), null, item.v.toString().getBytes());
		}
		HTableInterface table = conn.getTable(tablename);
		try {
			table.put(put);
			table.close();
		} catch (IOException e) {
			LOG.error("存入数据时发生错误 错误信息： " + e.getMessage());
			throw e;
		}
	}

	public void delete(Object obj) {

	}

	public Object loadById(Object id) {
		return id;
	}
}
