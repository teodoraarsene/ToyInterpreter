package model.utils;

import exceptions.StackException;

public interface IStack<TElem> {
    void push(TElem elem);
    TElem pop() throws StackException;

    boolean isEmpty();

    String toString();
}
