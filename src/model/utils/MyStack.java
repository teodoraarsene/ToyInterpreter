package model.utils;

import exceptions.StackException;

import java.util.ArrayDeque;
import java.util.Deque;

public class MyStack<TElem> implements IStack<TElem> {
    private final Deque<TElem> stack;

    public MyStack() {
        stack = new ArrayDeque<>();
    }

    @Override
    public void push(TElem tElem) {
        stack.push(tElem);
    }

    @Override
    public TElem pop() throws StackException {
        if (stack.isEmpty()) {
            throw new StackException("empty stack!");
        }
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Deque<TElem> getStack() {
        return stack;
    }

    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();
        for (TElem crtElem : this.stack) {
            representation.append(crtElem.toString());
        }

        return representation.toString();
    }
}
