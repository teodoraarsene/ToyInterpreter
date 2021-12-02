package model.statements;

import exceptions.StatementException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.IType;
import model.types.ReferenceType;
import model.utils.IDictionary;
import model.utils.MyHeap;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapAllocationStatement implements IStatement {
    private final IExpression expression;
    private final String name;

    public HeapAllocationStatement(String name, IExpression expression) {
        this.expression = expression;
        this.name = name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symbolsTable = state.getSymbolsTable();
        IDictionary<Integer, IValue> heapTable = state.getHeapTable();

        if (!symbolsTable.isDefined(name)) {
            throw new StatementException("variable " + name + " is not defined!");
        }

        IValue variableValue = symbolsTable.getValue(name);
        if (!(variableValue.getType() instanceof ReferenceType)) {
            throw new StatementException("variable " + name + " is not of reference type!");
        }

        IValue expressionValue = expression.evaluate(symbolsTable, heapTable);
        IType expressionType = expressionValue.getType();
        IType referencedType = ((ReferenceValue) variableValue).getLocationType();
        if (!referencedType.equals(expressionType)) {
            throw new StatementException("expression " + expression + " cannot be evaluated to " + referencedType);
        }

        int newHeapPosition = ((MyHeap<Integer, IValue>) heapTable).getFirstFreeLocation();
        heapTable.add(newHeapPosition, expressionValue);

        symbolsTable.update(name, new ReferenceValue(newHeapPosition, referencedType));

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IType nameType = typeEnvironment.getValue(name);
        IType expressionType = expression.typeCheck(typeEnvironment);

        if (!nameType.equals(new ReferenceType(expressionType))) {
            throw new StatementException("the type of expression " + expression + " does not match the type of the variable " + name);
        }

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "new(" + name + ", " + expression.toString() + ");";
    }
}
