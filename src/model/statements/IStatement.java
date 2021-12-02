package model.statements;

import model.ProgramState;
import model.types.IType;
import model.utils.IDictionary;

public interface IStatement {
    ProgramState execute(ProgramState state) throws Exception;

    IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception;

    String toString();
}
