package model.statements;

import exceptions.StatementException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.BooleanType;
import model.types.IType;
import model.utils.IDictionary;
import model.utils.IStack;
import model.values.BooleanValue;
import model.values.IValue;

public class IfStatement implements IStatement {
    private final IExpression expression;
    private final IStatement thenStatement;
    private final IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> executionStack = state.getExecutionStack();
        IDictionary<String, IValue> symbolsTable = state.getSymbolsTable();
        IDictionary<Integer, IValue> heapTable = state.getHeapTable();

        IValue expressionValue = expression.evaluate(symbolsTable, heapTable);
        if (!expressionValue.getType().equals(new BooleanType())) {
            throw new StatementException("expression " + expression + "did not evaluate to a boolean type!");
        }

        if (((BooleanValue) expressionValue).getValue()) {
            executionStack.push(thenStatement);
        }
        else {
            executionStack.push(elseStatement);
        }

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IType expressionType = expression.typeCheck(typeEnvironment);
        if (!expressionType.equals(new BooleanType())) {
            throw new StatementException("expression " + expression + "did not evaluate to a boolean type!");
        }

        thenStatement.typeCheck(typeEnvironment.clone());
        elseStatement.typeCheck(typeEnvironment.clone());

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "if (" + expression + ") { " + thenStatement + " } else { " + elseStatement + " }";
    }
}
