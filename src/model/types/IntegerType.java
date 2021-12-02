package model.types;

import model.values.IValue;
import model.values.IntegerValue;

public class IntegerType implements IType {
    @Override
    public boolean equals(Object another) {
        return another instanceof IntegerType;
    }

    @Override
    public IValue defaultValue() {
        return new IntegerValue(0);
    }

    @Override
    public String toString() {
        return "int";
    }
}
