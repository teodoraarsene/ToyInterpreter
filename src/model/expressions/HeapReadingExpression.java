package model.expressions;

import exceptions.ExpressionException;
import model.utils.IDictionary;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapReadingExpression implements IExpression {
    private final IExpression expression;

    public HeapReadingExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolsTable, IDictionary<Integer, IValue> heapTable) throws Exception {
        IValue expressionValue = expression.evaluate(symbolsTable, heapTable);

        int heapAddress = ((ReferenceValue) expressionValue).getHeapAddress();
        if (!heapTable.isDefined(heapAddress)) {
            throw new ExpressionException("address " + heapAddress + " is invalid!");
        }
        return heapTable.getValue(heapAddress);
    }

    @Override
    public String toString() {
        return "*(" + expression.toString() + ")";
    }
}
