package model.expressions;

import exceptions.ExpressionException;
import model.types.IType;
import model.utils.IDictionary;
import model.values.IValue;

public class VariableNameExpression implements IExpression {
    private final String name;

    public VariableNameExpression(String name) {
        this.name = name;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolsTable, IDictionary<Integer, IValue> heapTable) throws Exception {
        if (!symbolsTable.isDefined(name)) {
            throw new ExpressionException("variable " + name + " is not defined!");
        }

        return symbolsTable.getValue(name);
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        return typeEnvironment.getValue(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
