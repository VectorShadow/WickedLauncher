package vsdl.wlaunch.ui.in;

import java.util.Stack;

public class InputModeStack {
    private final Stack<InputMode> STACK;

    public InputModeStack() {
        STACK = new Stack<>();
        STACK.push(new BaseInputMode());
    }

    public InputMode peek() {
        return STACK.peek();
    }

    public InputMode pop() {
        if (STACK.size() <= 1) {
            throw new IllegalStateException("Popping the base input mode is not permitted.");
        }
        return STACK.pop();
    }

    public void push(InputMode im) {
        STACK.push(im);
    }
}
