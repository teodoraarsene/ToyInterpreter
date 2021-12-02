package model.statements;

import model.ProgramState;
import model.expressions.IExpression;
import model.utils.IDictionary;
import model.utils.IList;
import model.values.IValue;

public class PrintStatement implements IStatement {
    private final IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symbolsTable = state.getSymbolsTable();
        IList<IValue> output = state.getOutput();
        IDictionary<Integer, IValue> heapTable = state.getHeapTable();

        output.add(expression.evaluate(symbolsTable, heapTable));

        return null;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ");";
    }
}
