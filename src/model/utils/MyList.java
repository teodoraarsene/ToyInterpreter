package model.utils;

import java.util.ArrayList;
import java.util.List;

public class MyList<TElem> implements IList<TElem> {
    private final List<TElem> list;

    public MyList() {
        list = new ArrayList<>();
    }

    @Override
    public void add(TElem v) {
        list.add(v);
    }

    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();
        for(TElem elem : list) {
            representation.append(elem.toString()).append("\n");
        }
        return representation.toString();
    }
}
