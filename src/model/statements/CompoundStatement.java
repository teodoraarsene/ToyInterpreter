package model.statements;

import model.ProgramState;
import model.types.IType;
import model.utils.IDictionary;
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
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        return secondStatement.typeCheck(firstStatement.typeCheck(typeEnvironment));
    }

    @Override
    public String toString() {
        return "(" + firstStatement.toString() + " " + secondStatement.toString() + ")";
    }
}
