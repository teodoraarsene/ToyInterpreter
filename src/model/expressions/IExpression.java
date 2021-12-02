package model.expressions;

import model.utils.IDictionary;
import model.values.IValue;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> symbolsTable, IDictionary<Integer, IValue> heapTable) throws  Exception;

    String toString();
}
