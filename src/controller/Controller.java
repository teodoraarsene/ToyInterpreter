package controller;

import model.ProgramState;
import model.values.IValue;
import model.values.ReferenceValue;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private final IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void addProgramState(ProgramState state) {
        repository.addProgramState(state);
    }

    public void allStepsExecution() throws Exception {
        repository.clearLogFile();

        executor = Executors.newFixedThreadPool(2);

        List<ProgramState> programStatesList = removeCompletedPrograms(repository.getProgramStatesList());

        while (programStatesList.size() > 0) {
            programStatesList.get(0).getHeapTable().setContent((HashMap<Integer, IValue>) safeGarbageCollector(
                    getAddressFromSymbolsTable(programStatesList.get(0).getSymbolsTable().getContent().values(),
                    programStatesList.get(0).getHeapTable().getContent().values()),
                    programStatesList.get(0).getHeapTable().getContent()));
            oneStepForAllProgrames(programStatesList);
            programStatesList = removeCompletedPrograms(repository.getProgramStatesList());
        }

        executor.shutdownNow();

        repository.setProgramStatesList(programStatesList);
    }


    private void oneStepForAllProgrames(List<ProgramState> programStatesList) throws Exception {
        programStatesList.forEach(programState -> {
            try {
                repository.logProgramStateExecution(programState);
                System.out.println(programState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        List<Callable<ProgramState>> callableList = programStatesList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStepExecution))
                .collect(Collectors.toList());

        List<ProgramState> newProgramStatesList = executor.invokeAll(callableList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        programStatesList.addAll(newProgramStatesList);

        programStatesList.forEach(programState -> {
            try {
                repository.logProgramStateExecution(programState);
                System.out.println(programState);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        repository.setProgramStatesList(programStatesList);
    }

    private List<Integer> getAddressFromSymbolsTable(Collection<IValue> symbols, Collection<IValue> heap) {
        return Stream.concat(
                heap.stream()
                        .filter(v -> v instanceof ReferenceValue)
                        .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getHeapAddress();}),
                symbols.stream()
                        .filter(v -> v instanceof ReferenceValue)
                        .map(v -> {ReferenceValue v1 = (ReferenceValue) v; return v1.getHeapAddress();})
        ).collect(Collectors.toList());
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> symbolsTable, Map<Integer, IValue> heapTable) {
        return heapTable.entrySet().stream()
                .filter(e -> symbolsTable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<ProgramState> removeCompletedPrograms(List<ProgramState> programStatesList) {
        return programStatesList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }
}
