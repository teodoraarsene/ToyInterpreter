package model.values;

import model.types.IType;

public interface IValue {
    boolean equals(Object another);
    IType getType();

    String toString();
}
