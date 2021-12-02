package model.utils;

import exceptions.DictionaryException;

import java.util.HashMap;

public interface IDictionary<TKey, TValue> {
    void add(TKey key, TValue value) throws DictionaryException;
    TValue update(TKey key, TValue newTValue) throws DictionaryException;
    TValue remove(TKey key) throws DictionaryException;

    boolean isDefined(TKey key);
    TValue getValue(TKey key) throws DictionaryException;

    String toString();

    HashMap<TKey, TValue> getContent();
    void setContent(HashMap<TKey, TValue> newContent);

    IDictionary<TKey, TValue> clone();
}
