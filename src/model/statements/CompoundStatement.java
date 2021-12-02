package model.statements;

import model.ProgramState;
import model.utils.IStack;

public class CompoundStatement implements IStatement {
    private final IStatement firstStatement;
    private final IStatement secondStatement;

    public CompoundStatement(IStatement firstStatement, IStatement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> executionStack = state.getExecutionStack();

        executionStack.push(secondStatement);
        executionStack.push(firstStatement);

        return null;
    }
    @Override
    public String toString() {
        return "(" + firstStatement.toString() + " " + secondStatement.toString() + ")";
    }
}
