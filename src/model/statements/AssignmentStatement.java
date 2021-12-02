package model.statements;

import exceptions.StatementException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.IType;
import model.utils.IDictionary;
import model.values.IValue;

public class AssignmentStatement implements IStatement {
    private final IExpression expression;
    private final String name;

    public AssignmentStatement(String name, IExpression expression) {
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

        IValue expressionValue = expression.evaluate(symbolsTable, heapTable);
        IType type = symbolsTable.getValue(name).getType();
        if (!expressionValue.getType().equals(type)) {
            throw new StatementException("declared type of variable " + name + "does not match the type of the assigned expression " + expression + "!");
        }

        symbolsTable.update(name, expressionValue);

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IType nameType = typeEnvironment.getValue(name);
        IType expressionType = expression.typeCheck(typeEnvironment);
        if (!nameType.equals(expressionType)) {
            throw new StatementException("the type of the expression " + expression + " does not match the type of the variable " + name + "!");
        }

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return name + " = " + expression.toString() + ";";
    }

}
