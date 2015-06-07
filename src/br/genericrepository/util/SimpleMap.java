/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.genericrepository.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @param <K>
 * @param <V>
 * @brief Classe SimpleMap
 * @author Kaynan
 * @date 07/06/2015
 */
public class SimpleMap<K, V> implements Map<K, V>, Serializable {

    private static final long serialVersionUID = 1L;

    private Map<K, V> realMap;

    public SimpleMap(Object... entries) {
        putValues(entries);
    }

    @SuppressWarnings("unchecked")
    public SimpleMap<K, V> putValues(Object... entries) {
        if (entries.length % 2 != 0) {
            throw new IllegalArgumentException("An even number of objects must be given: " + entries.length);
        }

        if (this.realMap == null) {
            this.realMap = new HashMap<K, V>(entries.length / 2);
        }

        for (int i = 0; i < entries.length; i += 2) {
            Object key = entries[i];

            Object value = (i + 1 < entries.length ? entries[i + 1] : null);
            realMap.put((K) key, (V) value);
        }

        return this;
    }

    @Override
    public void clear() {
        realMap.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return realMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return realMap.containsValue(value);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return realMap.entrySet();
    }

    @Override
    public V get(Object key) {
        return realMap.get(key);
    }

    @Override
    public boolean isEmpty() {
        return realMap.isEmpty();
    }

    @Override
    public Set<K> keySet() {
        return realMap.keySet();
    }

    @Override
    public V put(K key, V value) {
        return realMap.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> t) {
        realMap.putAll(t);
    }

    @Override
    public V remove(Object key) {
        return realMap.remove(key);
    }

    @Override
    public int size() {
        return realMap.size();
    }

    @Override
    public Collection<V> values() {
        return realMap.values();
    }

    @Override
    public String toString() {
        return realMap.toString();
    }
}
