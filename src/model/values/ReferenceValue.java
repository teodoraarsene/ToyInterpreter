package model.values;

import model.types.IType;
import model.types.ReferenceType;

public class ReferenceValue implements IValue {
    private final int heapAddress;
    private final IType locationType;

    public ReferenceValue(int heapAddress, IType locationType) {
        this.heapAddress = heapAddress;
        this.locationType = locationType;
    }

    public int getHeapAddress() {
        return heapAddress;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public IType getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof ReferenceValue && ((ReferenceValue) another).getHeapAddress() == heapAddress);
    }

    @Override
    public String toString() {
        return String.format("reference_value{%d -> %s}", heapAddress, locationType);
    }
}
