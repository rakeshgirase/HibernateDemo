package com.jpmc.tre.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MultivalueMap<K, V> {
	
	private Map<K, Collection<V>> data = new HashMap<>();
	
	public void put(K key, V value){
		if(data.containsKey(key)){
			Collection<V> values = data.get(key);
			values.add(value);
		}else{
			Collection<V> values = new HashSet<>();
			values.add(value);
			data.put(key, values);
		}
	}
	
	public Collection<V> get(K key){
		if(data.containsKey(key)){
			return data.get(key);
			
		}
		return Collections.emptyList();
	}
	
	public Set<K> keySet(){
		return data.keySet();
	}
	
	public Set<Entry<K, Collection<V>>> entrySet(){
		return data.entrySet();
	}
	

}
