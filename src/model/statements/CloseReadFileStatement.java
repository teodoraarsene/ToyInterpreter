package model.statements;

import exceptions.StatementException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.StringType;
import model.utils.IDictionary;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class CloseReadFileStatement implements IStatement {
    private final IExpression expression;

    public CloseReadFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symbolsTable = state.getSymbolsTable();
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        IDictionary<Integer, IValue> heapTable = state.getHeapTable();

        IValue expressionValue = expression.evaluate(symbolsTable, heapTable);
        if (!expressionValue.getType().equals(new StringType())) {
            throw new StatementException("expression " + expression + " did not evaluate to string type!");
        }

        StringValue stringValue = ((StringValue) expressionValue);
        if (!fileTable.isDefined(stringValue.getValue())) {
            throw new StatementException("file path " + stringValue + " is not defined!");
        }

        try {
            BufferedReader bufferedReader = fileTable.getValue(stringValue.getValue());
            bufferedReader.close();
            fileTable.remove(stringValue.getValue());
        }
        catch (Exception e) {
            throw new StatementException("file " + expression + " could not be closed!\n" + e.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return "closeFile(" + expression.toString() + ");";
    }
}
