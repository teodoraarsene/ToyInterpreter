package model.statements;

import exceptions.StatementException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.StringType;
import model.utils.IDictionary;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenReadFileStatement implements IStatement {
    private final IExpression expression;

    public OpenReadFileStatement(IExpression expression) {
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

        StringValue stringValue = (StringValue) expressionValue;
        if (fileTable.isDefined(stringValue.getValue())) {
            throw new StatementException("file path " + stringValue + " is already defined!");
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(stringValue.getValue()));
            fileTable.add(stringValue.getValue(), bufferedReader);
        }
        catch (Exception e) {
            throw new StatementException("file " + expression + " could not be opened!\n" + e.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return "openFile(" + expression.toString() + ");";
    }
}
