package model.expressions;

import exceptions.ExpressionException;
import model.types.IntegerType;
import model.utils.IDictionary;
import model.values.BooleanValue;
import model.values.IValue;
import model.values.IntegerValue;

public class ArithmeticExpression implements IExpression {
    private final IExpression firstExpression;
    private final IExpression secondExpression;
    private final String operator;

    public ArithmeticExpression(String operator, IExpression firstExpression, IExpression secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symbolsTable, IDictionary<Integer, IValue> heapTable) throws Exception {
        IValue firstValue = firstExpression.evaluate(symbolsTable, heapTable);
        IValue secondValue = secondExpression.evaluate(symbolsTable, heapTable);

        if (!firstValue.getType().equals(new IntegerType())) {
            throw new ExpressionException("first operand  " + firstExpression + " is not of integer type!");
        }
        if (!secondValue.getType().equals(new IntegerType())) {
            throw new ExpressionException("second operand  " + secondExpression + " is not of integer type!");
        }

        int first = ((IntegerValue) firstValue).getValue();
        int second = ((IntegerValue) secondValue).getValue();
        switch (operator) {
            case "+": return new IntegerValue(first + second);
            case "-": return new IntegerValue(first - second);
            case "*": return new IntegerValue(first * second);
            case "/": {
                if (second == 0) {
                    throw new ExpressionException("division by 0!");
                }
                return new IntegerValue(first / second);
            }
            default: throw new ExpressionException(operator + " is an invalid operator!");
        }
    }

    @Override
    public String toString() {
        return firstExpression.toString() + " " + operator + " " + secondExpression.toString();
    }
}
