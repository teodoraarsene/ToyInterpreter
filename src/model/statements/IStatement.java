package model.statements;

import model.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state) throws Exception;

    String toString();
}
