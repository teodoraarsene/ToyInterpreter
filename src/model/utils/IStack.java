package model.utils;

import exceptions.StackException;
import model.statements.IStatement;

import java.util.Deque;

public interface IStack<TElem> {
    void push(TElem elem);
    TElem pop() throws StackException;

    boolean isEmpty();

    Deque<TElem> getStack();

    String toString();
}
