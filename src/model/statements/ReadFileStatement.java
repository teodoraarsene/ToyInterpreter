package model.statements;

import exceptions.StatementException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.IType;
import model.types.IntegerType;
import model.types.StringType;
import model.utils.IDictionary;
import model.values.IValue;
import model.values.IntegerValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class ReadFileStatement implements IStatement {
    private final IExpression expression;
    private final String name;

    public ReadFileStatement(IExpression expression, String name) {
        this.expression = expression;
        this.name = name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symbolsTable = state.getSymbolsTable();

        if (!symbolsTable.isDefined(name)) {
            throw new StatementException("variable " + name + " is not defined!");
        }
        if (!symbolsTable.getValue(name).getType().equals(new IntegerType())) {
            throw new StatementException("variable " + name + " is not an integer!");
        }

        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        IDictionary<Integer, IValue> heapTable = state.getHeapTable();

        IValue expressionValue = expression.evaluate(symbolsTable, heapTable);
        if (!expressionValue.getType().equals(new StringType())) {
            throw new StatementException("expression " + expression + " does not evaluate to string type!");
        }

        StringValue stringValue = (StringValue) expressionValue;
        if (!fileTable.isDefined(stringValue.getValue())) {
            throw new StatementException("file path " + stringValue + " is not defined!");
        }

        try {
            BufferedReader bufferedReader = fileTable.getValue(stringValue.getValue());
            String line = bufferedReader.readLine();
            if (line == null) {
                symbolsTable.update(name, new IntegerValue(0));
            } else {
                symbolsTable.update(name, new IntegerValue(Integer.parseInt(line)));
            }
        }
        catch (Exception e) {
            throw new StatementException("file " + expression + " could not be updated!\n" + e.getMessage());
        }

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        if (!typeEnvironment.getValue(name).equals(new IntegerType())) {
            throw new StatementException("variable " + name + " does not evaluate to integer type!");
        }
        if (!expression.typeCheck(typeEnvironment).equals(new StringType())) {
            throw new StatementException("expression " + expression + " does not evaluate to string type!");
        }

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ");";
    }
}
