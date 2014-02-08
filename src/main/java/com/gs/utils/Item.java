package com.gs.utils;

public class Item<K extends Object, V extends Object> {
	public K k;
	public V v;

	/**
	 * @param k
	 * @param v
	 */
	public Item(K k, V v) {
		this.k = k;
		this.v = v;
	}

	@Override
	public String toString() {
		return "Item [k=" + k + ", v=" + v + "]";
	}
}
