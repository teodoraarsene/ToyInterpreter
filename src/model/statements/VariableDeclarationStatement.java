package model.statements;

import exceptions.StatementException;
import model.ProgramState;
import model.types.IType;
import model.utils.IDictionary;
import model.values.IValue;

public class VariableDeclarationStatement implements IStatement {
    private final String name;
    private final IType type;

    public VariableDeclarationStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symbolsTable = state.getSymbolsTable();

        if (symbolsTable.isDefined(name)) {
            throw new StatementException("variable " + name + " is already defined!");
        }

        symbolsTable.add(name, type.defaultValue());

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        typeEnvironment.add(name, type);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name + ";";
    }
}
