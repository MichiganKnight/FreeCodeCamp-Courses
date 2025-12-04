package com.revature.weekSeven;

public class GenericsBasic {
    public static void main(String[] args) {
        System.out.println("=== Basic Generics ===");

        Container<String> container = new Container<>();
        container.setObject("Hello World");

        System.out.println(container);

        System.out.println(container.getObject());

        container.removeObject();
    }
}

class Container<T> {
    private T object;

    /**
     * Sets an Object
     * @param object Object to Set
     */
    public void setObject(T object) {
        this.object = object;
    }

    /**
     * Gets an Object
     * @return Object to Return
     */
    public T getObject() {
        return object;
    }

    public T removeObject() {
        T temp = this.object;
        object = null;

        return temp;
    }

    @Override
    public String toString() {
        return "Container: [" + object + "]";
    }
}
