package model.utils;

import exceptions.DictionaryException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<TKey, TValue> implements IDictionary<TKey, TValue> {
    private HashMap<TKey, TValue> dictionary;

    public MyDictionary() {
        dictionary = new HashMap<>();
    }

    @Override
    public void add(TKey tKey, TValue tValue) throws DictionaryException {
        if (dictionary.containsKey(tKey)) {
            throw new DictionaryException("key " + tKey.toString() + " already existent!");
        }
        try {
            dictionary.put(tKey, tValue);
        }
        catch (Exception e) {
            throw new DictionaryException(e.getMessage());
        }
    }

    @Override
    public TValue update(TKey tKey, TValue newTValue) throws DictionaryException {
        if (!dictionary.containsKey(tKey)) {
            throw new DictionaryException("key " + tKey.toString() + " does not exist!");
        }
        try {
            return dictionary.replace(tKey, newTValue);
        }
        catch (Exception e) {
            throw new DictionaryException(e.getMessage());
        }
    }

    @Override
    public TValue remove(TKey tKey) throws DictionaryException {
        if (!dictionary.containsKey(tKey)) {
            throw new DictionaryException("key " + tKey.toString() + " does not exist!");
        }
        try {
            return dictionary.remove(tKey);
        }
        catch (Exception e) {
            throw new DictionaryException(e.getMessage());
        }
    }

    @Override
    public boolean isDefined(TKey tKey) {
        return dictionary.containsKey(tKey);
    }

    @Override
    public TValue getValue(TKey tKey) throws DictionaryException {
        if (!dictionary.containsKey(tKey)) {
            throw new DictionaryException("key " + tKey.toString() + " does not exist!");
        }
        try {
            return dictionary.get(tKey);
        }
        catch (Exception e) {
            throw new DictionaryException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();
        Collection<TKey> allKeys = dictionary.keySet();
        for (TKey key : allKeys) {
            representation.append(key.toString()).append(" -> ").append(dictionary.get(key).toString()).append("\n");
        }
        return representation.toString();
    }

    @Override
    public HashMap<TKey, TValue> getContent() {
        return dictionary;
    }

    @Override
    public void setContent(HashMap<TKey, TValue> newContent) {
        dictionary = newContent;
    }

    @Override
    public IDictionary<TKey, TValue> clone() {
        IDictionary<TKey, TValue> newDictionary = new MyDictionary<>();
        for (Map.Entry<TKey, TValue> pair  : dictionary.entrySet()) {
            try {
                newDictionary.add(pair.getKey(), pair.getValue());
            }
            catch (DictionaryException e) {
                e.printStackTrace();
            }
        }
        return newDictionary;
    }
}
