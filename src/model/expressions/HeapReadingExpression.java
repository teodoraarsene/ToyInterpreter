package model.expressions;

import exceptions.ExpressionException;
import model.types.IType;
import model.types.ReferenceType;
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
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IType type = expression.typeCheck(typeEnvironment);
        if (!(type instanceof ReferenceType)) {
            throw new ExpressionException("expression " + expression + " is not of reference type!");
        }

        return ((ReferenceType) type).getInner();
    }

    @Override
    public String toString() {
        return "*(" + expression.toString() + ")";
    }
}
