package repository;

import exceptions.RepositoryException;
import model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<ProgramState> programStates;
    private final String logFilePath;

    public Repository(String logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

//    @Override
//    public ProgramState getCurrentProgramState() {
//        return programStates.get(programStates.size() - 1);
//    }

    @Override
    public void addProgramState(ProgramState state) {
        programStates.add(state);
    }

    @Override
    public void logProgramStateExecution(ProgramState state) throws RepositoryException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.append(state.toString());
            logFile.close();
        }
        catch (IOException e) {
            throw new RepositoryException("error on log file " + logFilePath + "!");
        }
    }

    @Override
    public void clearLogFile() throws RepositoryException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.print("");
            logFile.close();
        }
        catch (IOException e) {
            throw new RepositoryException("error on clearing the log file " + logFilePath + "!");
        }
    }

    @Override
    public List<ProgramState> getProgramStatesList() {
        return programStates;
    }

    @Override
    public void setProgramStatesList(List<ProgramState> newList) {
        programStates.clear();
        programStates.addAll(newList);
    }
}
