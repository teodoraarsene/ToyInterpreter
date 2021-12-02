package model.statements;

import model.ProgramState;
import model.types.IType;
import model.utils.IDictionary;

public class NoOperationStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "noOperation();";
    }
}
