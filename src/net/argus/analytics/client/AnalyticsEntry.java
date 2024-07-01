package net.argus.analytics.client;

import java.util.Map.Entry;

public class AnalyticsEntry<K, V> implements Entry<K, V> {
	
	private K key;
	private V value;
	
	public AnalyticsEntry(K key) {
		this.key = key;
	}
	
	public AnalyticsEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		this.value = value;
		return this.value;
	}
	
	@Override
	public String toString() {
		return key + "=" + value;
	}

}
