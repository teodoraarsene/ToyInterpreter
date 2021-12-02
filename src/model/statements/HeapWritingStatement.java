package model.statements;

import exceptions.StatementException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.IType;
import model.types.ReferenceType;
import model.utils.IDictionary;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapWritingStatement implements IStatement {
    private final IExpression expression;
    private final String name;

    public HeapWritingStatement(String name, IExpression expression) {
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

        int heapAddress = ((ReferenceValue) variableValue).getHeapAddress();
        if (!heapTable.isDefined(heapAddress)) {
            throw new StatementException("address " + heapAddress + " is invalid!");
        }

        IValue expressionValue = expression.evaluate(symbolsTable, heapTable);
        IType expressionType = expressionValue.getType();
        IValue referencedValue = heapTable.getValue(heapAddress);
        IType referencedType = referencedValue.getType();
        if (!expressionType.equals(referencedType)) {
            throw new StatementException("the type referenced by " + name + " does not match the " + expression + " expression!");
        }

        heapTable.update(heapAddress, expressionValue);

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IType expressionType = expression.typeCheck(typeEnvironment);

        if (!typeEnvironment.getValue(name).equals(new ReferenceType(expressionType))) {
            throw new StatementException("the type of expression " + expression + " does not match the type of the variable " + name);
        }

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "*(" + name + ") = " + expression.toString() + ";";
    }
}
