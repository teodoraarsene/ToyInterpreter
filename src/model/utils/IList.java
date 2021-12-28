package model.utils;

import java.util.ArrayList;
import java.util.List;

public interface IList<TElem> {
    void add(TElem v);

    ArrayList<String> getList();

    String toString();
}
