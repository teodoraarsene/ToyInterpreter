package model.expressions;

import model.types.IType;
import model.utils.IDictionary;
import model.values.IValue;

public class ValueExpression implements IExpression {
    private final IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolsTable, IDictionary<Integer, IValue> heapTable) throws Exception {
        return value;
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
