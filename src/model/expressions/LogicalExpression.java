package model.expressions;

import exceptions.ExpressionException;
import model.types.BooleanType;
import model.utils.IDictionary;
import model.values.BooleanValue;
import model.values.IValue;

public class LogicalExpression implements IExpression {
    private final IExpression firstExpression;
    private final IExpression secondExpression;
    private final String operator;

    public LogicalExpression(IExpression firstExpression, IExpression secondExpression, String operator) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolsTable, IDictionary<Integer, IValue> heapTable) throws Exception {
        IValue firstValue = firstExpression.evaluate(symbolsTable, heapTable);
        IValue secondValue = secondExpression.evaluate(symbolsTable, heapTable);

        if (!firstValue.getType().equals(new BooleanType())) {
            throw new ExpressionException("first operand  " + firstExpression + " is not of boolean type!");
        }
        if (!secondValue.getType().equals(new BooleanType())) {
            throw new ExpressionException("second operand  " + secondExpression + " is not of boolean type!");
        }

        boolean first = ((BooleanValue) firstValue).getValue();
        boolean second = ((BooleanValue) secondValue).getValue();
        return switch (operator) {
            case "&&" -> new BooleanValue(first && second);
            case "||" -> new BooleanValue(first || second);
            default -> throw new ExpressionException(operator + " is an invalid operator!");
        };
    }

    @Override
    public String toString() {
        return firstExpression.toString() + " " + operator + " " + secondExpression.toString();
    }
}
