package model.utils;

public class MyHeap<TKey, TValue> extends MyDictionary<TKey, TValue> {
    private int firstFreeLocation;

    public MyHeap() {
        super();
        firstFreeLocation = 1;
    }

    public int getFirstFreeLocation() {
        int freeLocation = firstFreeLocation;
        firstFreeLocation++;
        return freeLocation;
    }
}
