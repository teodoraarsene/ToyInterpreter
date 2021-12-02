package model.expressions;

import exceptions.ExpressionException;
import model.types.BooleanType;
import model.types.IType;
import model.types.IntegerType;
import model.utils.IDictionary;
import model.values.BooleanValue;
import model.values.IValue;
import model.values.IntegerValue;

public class RelationalExpression implements IExpression {
    private final IExpression firstExpression;
    private final IExpression secondExpression;
    private final String operator;

    public RelationalExpression(String operator, IExpression firstExpression, IExpression secondExpression) {
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
        return switch (operator) {
            case "<" -> new BooleanValue(first < second);
            case "<=" -> new BooleanValue(first <= second);
            case "==" -> new BooleanValue(first == second);
            case "!=" -> new BooleanValue(first != second);
            case ">" -> new BooleanValue(first > second);
            case ">=" -> new BooleanValue(first >= second);
            default -> throw new ExpressionException(operator + " is an invalid operator!");
        };
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IType firstType = firstExpression.typeCheck(typeEnvironment);
        IType secondType = secondExpression.typeCheck(typeEnvironment);
        if (!firstType.equals(new IntegerType())) {
            throw new ExpressionException("first operand  " + firstExpression + " is not of integer type!");
        }
        if (!secondType.equals(new IntegerType())) {
            throw new ExpressionException("second operand  " + secondExpression + " is not of integer type!");
        }

        return new BooleanType();
    }

    @Override
    public String toString() {
        return firstExpression.toString() + " " + operator + " " + secondExpression.toString();
    }
}
