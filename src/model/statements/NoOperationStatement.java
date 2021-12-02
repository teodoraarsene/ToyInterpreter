package model.statements;

import model.ProgramState;

public class NoOperationStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return null;
    }

    @Override
    public String toString() {
        return "noOperation();";
    }
}
