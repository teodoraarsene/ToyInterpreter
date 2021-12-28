package model;

import model.statements.IStatement;
import model.utils.*;
import model.values.IValue;

import java.io.BufferedReader;

public class ProgramState {
    private final IStack<IStatement> executionStack;
    private final IDictionary<String, IValue> symbolsTable;
    private final IList<IValue> output;
    private final IDictionary<String, BufferedReader> fileTable;
    private final IDictionary<Integer, IValue> heapTable;

    private static int count = 1;
    private final int id;

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, IValue> symbolsTable, IList<IValue> output, IDictionary<String, BufferedReader> fileTable, IDictionary<Integer, IValue> heapTable, IStatement program) {
        this.executionStack = executionStack;
        this.symbolsTable = symbolsTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        executionStack.push(program);
        this.id = manageId();
    }

    public ProgramState(IStatement program) {
        executionStack = new MyStack<>();
        symbolsTable = new MyDictionary<>();
        output = new MyList<>();
        fileTable = new MyDictionary<>();
        heapTable = new MyDictionary<>();
        executionStack.push(program);
        id = manageId();
    }

    public IStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public IDictionary<String, IValue> getSymbolsTable() {
        return symbolsTable;
    }

    public IList<IValue> getOutput() {
        return output;
    }

    public IDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IDictionary<Integer, IValue> getHeapTable() {
        return heapTable;
    }

    public int getId() {
        return id;
    }

    private static synchronized int manageId() {
        int newId = count;
        count ++;
        return newId;
    }

    public boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    public ProgramState oneStepExecution() throws Exception {
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public String toString() {
        String representation = "";
        representation += "THREAD ID: " + id + "\n";
        representation += "EXECUTION STACK:\n";
        representation += executionStack.toString();
        representation += "\nSYMBOLS' TABLE:\n";
        representation += symbolsTable.toString();
        representation += "\nFILE TABLE:\n";
        representation += fileTable.toString();
        representation += "\nHEAP TABLE:\n";
        representation += heapTable.toString();
        representation += "\nOUTPUT:\n";
        representation += output.toString();
        representation += "-------------------------------------------------------------------------\n";

        return representation;
    }
}
