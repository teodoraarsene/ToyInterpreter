package view;

import controller.Controller;
import model.ProgramState;
import model.expressions.*;
import model.statements.*;
import model.types.BooleanType;
import model.types.IType;
import model.types.IntegerType;
import model.types.ReferenceType;
import model.utils.*;
import model.values.BooleanValue;
import model.values.IntegerValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import view.commands.ExitCommand;
import view.commands.RunExampleCommand;

public class Interpreter {
    private static IStatement buildStatement(IStatement... statements) {
        if (statements.length == 0) {
            return new NoOperationStatement();
        }
        if (statements.length == 1) {
            return statements[0];
        }
        IStatement finalStatement = new CompoundStatement(statements[0], statements[1]);
        for (int i=2; i<statements.length; i++) {
            finalStatement = new CompoundStatement(finalStatement, statements[i]);
        }

        return finalStatement;
    }

    private static ProgramState initializeDataStructures(IStatement example) {
        IDictionary<String, IType> typeEnvironment = new MyDictionary<>();
        try {
            example.typeCheck(typeEnvironment);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
       return new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), example);
    }


    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        // int v; v=2; print(v);
        IStatement ex1 = buildStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                new PrintStatement(new VariableNameExpression("v"))
        );
        ProgramState state1 = initializeDataStructures(ex1);
        IRepository repo1 = new Repository("src/logFiles/log1.txt");
        Controller controller1 = new Controller(repo1);
        controller1.addProgramState(state1);
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), controller1));

        // int a; int b; a=2+3*5; b=a+1; print(b);
        IStatement ex2 = buildStatement(
                new VariableDeclarationStatement("a", new IntegerType()),
                new VariableDeclarationStatement("b", new IntegerType()),
                new AssignmentStatement("a", new ArithmeticExpression("+",
                        new ValueExpression(new IntegerValue(2)),
                        new ArithmeticExpression("*",
                                new ValueExpression(new IntegerValue(3)),
                                new ValueExpression(new IntegerValue(5))))),
                new AssignmentStatement("b", new ArithmeticExpression("+",
                        new VariableNameExpression("a"),
                        new ValueExpression(new IntegerValue(1)))),
                new PrintStatement(new VariableNameExpression("b"))
        );

        ProgramState state2 = initializeDataStructures(ex2);
        IRepository repo2 = new Repository("src/logFiles/log2.txt");
        Controller controller2 = new Controller(repo2);
        controller2.addProgramState(state2);
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), controller2));

        // bool a; int v; a=true; (if a then v=2 else v=3); print(v);
        IStatement ex3 = buildStatement(
                new VariableDeclarationStatement("a", new BooleanType()),
                new VariableDeclarationStatement("v", new IntegerType()),
                new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                new IfStatement(
                        new VariableNameExpression("a"),
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))
                ),
                new PrintStatement(new VariableNameExpression("v"))
        );

        ProgramState state3 = initializeDataStructures(ex3);
        IRepository repo3 = new Repository("src/logFiles/log3.txt");
        Controller controller3 = new Controller(repo3);
        controller3.addProgramState(state3);
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), controller3));

        // int a; bool b; int c; a=8; c=11; (if c>a then b=true else b=false); print(b);
        IStatement ex4 = buildStatement(
                new VariableDeclarationStatement("a", new IntegerType()),
                new VariableDeclarationStatement("b", new BooleanType()),
                new VariableDeclarationStatement("c", new IntegerType()),
                new AssignmentStatement("a", new ValueExpression(new IntegerValue(8))),
                new AssignmentStatement("c", new ValueExpression(new IntegerValue(11))),
                new IfStatement(
                        new RelationalExpression(
                                ">",
                                new VariableNameExpression("c"),
                                new VariableNameExpression("a")),
                        new AssignmentStatement("b", new ValueExpression(new BooleanValue(true))),
                        new AssignmentStatement("b", new ValueExpression(new BooleanValue(false)))
                ),
                new PrintStatement(new VariableNameExpression("b"))
        );

        ProgramState state4 = initializeDataStructures(ex4);
        IRepository repo4 = new Repository("src/logFiles/log4.txt");
        Controller controller4 = new Controller(repo4);
        controller4.addProgramState(state4);
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), controller4));

        // string varf; varf = 'test.in'; openReadFile(varf); int varc; readFile(varf, varc); print(varc); readFile(varf, varc); print(varc); closeReadFile(varf);
        IExpression filename = new ValueExpression(new StringValue("src/test.in"));
        IStatement ex5 = buildStatement(
                new OpenReadFileStatement(filename),
                new VariableDeclarationStatement("varc", new IntegerType()),
                new ReadFileStatement(filename, "varc"),
                new PrintStatement(new VariableNameExpression("varc")),
                new ReadFileStatement(filename, "varc"),
                new PrintStatement(new VariableNameExpression("varc")),
                new CloseReadFileStatement(filename)
        );

        ProgramState state5 = initializeDataStructures(ex5);
        IRepository repo5 = new Repository("src/logFiles/log5.txt");
        Controller controller5 = new Controller(repo5);
        controller5.addProgramState(state5);
        menu.addCommand(new RunExampleCommand("5", ex5.toString(), controller5));

        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement ex6 = buildStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new AssignmentStatement("v", new ValueExpression(new IntegerValue(4))),
                new WhileStatement(
                        new RelationalExpression(
                                ">",
                                new VariableNameExpression("v"),
                                new ValueExpression(new IntegerValue(0))
                        ),
                        buildStatement(
                                new PrintStatement(new VariableNameExpression("v")),
                                new AssignmentStatement(
                                        "v",
                                        new ArithmeticExpression(
                                                "-",
                                                new VariableNameExpression("v"),
                                                new ValueExpression(new IntegerValue(1))
                                        )
                                )
                        )
                ),
                new PrintStatement(new VariableNameExpression("v"))
        );

        ProgramState state6 = initializeDataStructures(ex6);
        IRepository repo6 = new Repository("src/logFiles/log6.txt");
        Controller controller6 = new Controller(repo6);
        controller6.addProgramState(state6);
        menu.addCommand(new RunExampleCommand("6", ex6.toString(), controller6));

        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStatement ex7 = buildStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())),
                new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))),
                new HeapAllocationStatement("a", new VariableNameExpression("v")),
                new PrintStatement(new VariableNameExpression("v")),
                new PrintStatement(new VariableNameExpression("a"))
        );

        ProgramState state7 = initializeDataStructures(ex7);
        IRepository repo7 = new Repository("src/logFiles/log7.txt");
        Controller controller7 = new Controller(repo7);
        controller7.addProgramState(state7);
        menu.addCommand(new RunExampleCommand("7", ex7.toString(), controller7));

        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStatement ex8 = buildStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())),
                new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))),
                new HeapAllocationStatement("a", new VariableNameExpression("v")),
                new PrintStatement(new HeapReadingExpression(new VariableNameExpression("v"))),
                new PrintStatement(
                        new ArithmeticExpression(
                                "+",
                                new HeapReadingExpression(new HeapReadingExpression(new VariableNameExpression("a"))),
                                new ValueExpression(new IntegerValue(5))
                        )
                )
        );

        ProgramState state8 = initializeDataStructures(ex8);
        IRepository repo8 = new Repository("src/logFiles/log8.txt");
        Controller controller8 = new Controller(repo8);
        controller8.addProgramState(state8);
        menu.addCommand(new RunExampleCommand("8", ex8.toString(), controller8));

        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStatement ex9 = buildStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())),
                new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                new PrintStatement(new HeapReadingExpression(new VariableNameExpression("v"))),
                new HeapWritingStatement("v", new ValueExpression(new IntegerValue(30))),
                new PrintStatement(new ArithmeticExpression(
                        "+",
                        new HeapReadingExpression(new VariableNameExpression("v")),
                        new ValueExpression(new IntegerValue(5))
                ))
        );

        ProgramState state9 = initializeDataStructures(ex9);
        IRepository repo9 = new Repository("src/logFiles/log9.txt");
        Controller controller9 = new Controller(repo9);
        controller9.addProgramState(state9);
        menu.addCommand(new RunExampleCommand("9", ex9.toString(), controller9));

        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStatement ex10 = buildStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())),
                new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20))),
                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))),
                new HeapAllocationStatement("a", new VariableNameExpression("v")),
                new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(30))),
                new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableNameExpression("a"))))
        );

        ProgramState state10 = initializeDataStructures(ex10);
        IRepository repo10 = new Repository("src/logFiles/log10.txt");
        Controller controller10 = new Controller(repo10);
        controller10.addProgramState(state10);
        menu.addCommand(new RunExampleCommand("10", ex10.toString(), controller10));

        // int v; Ref int a; v=10;new(a,22);
        // fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
        IStatement ex11 = buildStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())),
                new AssignmentStatement("v", new ValueExpression(new IntegerValue(10))),
                new HeapAllocationStatement("a", new ValueExpression(new IntegerValue(22))),
                new ForkStatement(
                        buildStatement(
                                new HeapWritingStatement("a", new ValueExpression(new IntegerValue(30))),
                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))),
                                new PrintStatement(new VariableNameExpression("v")),
                                new PrintStatement(new HeapReadingExpression(new VariableNameExpression("a")))
                        )
                ),
                new PrintStatement(new VariableNameExpression("v")),
                new PrintStatement(new HeapReadingExpression(new VariableNameExpression("a")))
        );

        ProgramState state11 = initializeDataStructures(ex11);
        IRepository repo11 = new Repository("src/logFiles/log11.txt");
        Controller controller11 = new Controller(repo11);
        controller11.addProgramState(state11);
        menu.addCommand(new RunExampleCommand("11", ex11.toString(), controller11));

        // int v; v=err; print(v);
        IStatement ex12 = buildStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new AssignmentStatement("v", new ValueExpression(new StringValue("err"))),
                new PrintStatement(new VariableNameExpression("v"))
        );
        ProgramState state12 = initializeDataStructures(ex12);
        IRepository repo12 = new Repository("src/logFiles/log12.txt");
        Controller controller12 = new Controller(repo12);
        controller12.addProgramState(state12);
        menu.addCommand(new RunExampleCommand("12", ex12.toString(), controller12));

        menu.show();
    }
}
