package model.values;

import model.types.BooleanType;
import model.types.IType;

public class BooleanValue implements IValue {
    private final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new BooleanType();
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof BooleanValue && ((BooleanValue) another).getValue() == value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
