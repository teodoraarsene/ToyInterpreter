package model.expressions;

import model.types.IType;
import model.utils.IDictionary;
import model.values.IValue;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> symbolsTable, IDictionary<Integer, IValue> heapTable) throws  Exception;

    IType typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception;

    String toString();
}
