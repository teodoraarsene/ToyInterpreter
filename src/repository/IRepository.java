package repository;

import exceptions.RepositoryException;
import model.ProgramState;

import java.util.List;

public interface IRepository {
//    ProgramState getCurrentProgramState();

    void addProgramState(ProgramState state);

    void logProgramStateExecution(ProgramState state) throws RepositoryException;
    void clearLogFile() throws RepositoryException;

    List<ProgramState> getProgramStatesList();
    void setProgramStatesList(List<ProgramState> newList);
}
