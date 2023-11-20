package com.me.mseotsanyana.mande.application.structures;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CIndexedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private final ArrayList<K> keysList = new ArrayList<>();

    public void add(K key, V val) {
        super.put(key, val);
        keysList.add(key);
    }

    public void update(K key, V val) {
        super.put(key, val);
    }

    public void removeItemByKey(K key) {
        super.remove(key);
        keysList.remove(key);
    }

    public void removeItemByIndex(int index) {
        super.remove(keysList.get(index));
        keysList.remove(index);
    }

    public V getItemByIndex(int index) {
        return (V) super.get(keysList.get(index));
    }

    public K getKeyByIndex(int index) {
        return (K) keysList.get(index);
    }

    public int getIndexByKey(K key) {
        return keysList.indexOf(key);
    }
}
