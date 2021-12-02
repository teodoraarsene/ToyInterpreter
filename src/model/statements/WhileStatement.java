package model.statements;

import exceptions.StatementException;
import model.ProgramState;
import model.expressions.IExpression;
import model.utils.IDictionary;
import model.utils.IStack;
import model.values.BooleanValue;
import model.values.IValue;

public class WhileStatement implements IStatement {
    private final IExpression expression;
    private final IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> executionStack = state.getExecutionStack();
        IDictionary<String, IValue> symbolsTable = state.getSymbolsTable();
        IDictionary<Integer, IValue> heapTable = state.getHeapTable();

        IValue expressionValue = expression.evaluate(symbolsTable, heapTable);
        if (!(expressionValue instanceof BooleanValue)) {
            throw new StatementException("expression " + expression + " is not of boolean type!");
        }

        if (((BooleanValue) expressionValue).getValue()) {
            executionStack.push(this);
            executionStack.push(statement);
        }
        return null;
    }

    @Override
    public String toString() {
        return "while (" + expression.toString() + ") { " + statement.toString() + " }";
    }
}
