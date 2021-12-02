package model.values;

import model.types.IType;
import model.types.IntegerType;

public class IntegerValue implements IValue {
    private final int value;

    public IntegerValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new IntegerType();
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof IntegerValue && ((IntegerValue) another).getValue() == value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
