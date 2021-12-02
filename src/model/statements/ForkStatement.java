package model.statements;

import model.ProgramState;
import model.types.IType;
import model.utils.IDictionary;
import model.utils.MyStack;

public class ForkStatement implements IStatement {
    private final IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        if (statement == null) {
            return null;
        }

        return new ProgramState(
                new MyStack<>(),
                state.getSymbolsTable().clone(),
                state.getOutput(),
                state.getFileTable(),
                state.getHeapTable(),
                statement);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        statement.typeCheck(typeEnvironment.clone());
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ");";
    }
}
