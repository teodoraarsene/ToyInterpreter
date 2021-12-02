package model.expressions;

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
    public String toString() {
        return value.toString();
    }
}
