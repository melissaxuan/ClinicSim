package util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Array-based implementation of "List" data structure to hold list of objects of any type.
 *
 * @author Melissa Xuan
 *
 */
public class List<E> implements Iterable<E> {
    private final int INITIAL_ARRAY_LENGTH = 4;
    private final int INITIAL_SIZE = 0;
    private final int NOT_FOUND = -1;

    private E[] objects;
    private int size;

    /**
     * Default List constructor: initializes new objects array to size four, and size to 0.
     */
    public List() {
        this.objects = (E[]) new Object[INITIAL_ARRAY_LENGTH];
        this.size = INITIAL_SIZE;
    }

    /**
     * Finds input object in objects array.
     * @param e object to be searched for
     * @return index of object in objects array, -1 if not found
     */
    private int find(E e) {
        for (int index = 0; index < this.size; index++) {
            if (this.objects[index].equals(e)) {
                return index;
            }
        }

        return NOT_FOUND;
    }

    /**
     * Increases size of object array by four.
     */
    private void grow() {
        E[] newObjects = (E[]) new Object[this.size + INITIAL_ARRAY_LENGTH];
        for (int index = 0; index < this.size; index++) {
            newObjects[index] = this.objects[index];
        }
        this.objects = newObjects;
    }

    /**
     * Checks if object array contains input object.
     * @param e object to be searched for
     * @return true if object was found in object array, false if object was not found in object array
     */
    public boolean contains(E e) {
        return find(e) != NOT_FOUND;
    }

    /**
     * Adds input object to object array if not already there.
     * @param e object to be added to object array
     */
    public void add(E e) {
        if (!contains(e)) {
            if (this.objects.length == this.size) {
                grow();
            }
            this.objects[size] = e;
            this.size++;
        }
    }

    /**
     * Removes input object from object array.
     * @param e object to be removed
     */
    public void remove(E e) {
        if (this.size != 0 && contains(e)) {
            int index = find(e);
            this.objects[index] = null;
            for (int i = index; i < this.size - 1; i++) {
                this.objects[i] = this.objects[i + 1];
            }
            this.size--;
        }
    }

    /**
     * Checks if object array is empty.
     * @return true if size of object array is zero, false if not
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Getter method to return size of object array.
     * @return size of object array
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns an iterator over object array of any type.
     * @return an iterator over object array of any type.
     */
    @Override
    public Iterator<E> iterator() {
        return new ListIterator<E>();

    }

    /**
     * Returns object in List at specified index.
     * @param index index of object to be retrieved in List
     * @return the object at the specified index in List
     */
    public E get(int index) {
        return this.objects[index];
    }

    /**
     * Sets object at specified index in List to input object.
     * @param index index to set object to
     * @param e object to be inserted in List
     */
    public void set(int index, E e) {
        if (index < 0) return;

        if (index >= this.objects.length) {
            grow();
        }

        this.objects[index] = e;

        if (index == this.size()) {
            this.size++;
        }
    }

    /**
     * Finds the index of input object in List.
     * @param e object to be searched for
     * @return index of input object in List, if not found return -1
     */
    public int indexOf(E e) {
        for (int index = 0; index < this.size(); index++) {
            if (this.objects[index].equals(e)) {
                return index;
            }
        }

        return NOT_FOUND;
    }

    /**
     * Class to implement Iterator for List class.
     * @param <E> Generic type for objects in List
     */
    private class ListIterator<E> implements Iterator<E> {
        private int currIndex = 0;

        /**
         * Checks if there is an element after the current index.
         * @return true if there is an element after the current index, false if not
         */
        @Override
        public boolean hasNext() {
            return currIndex < size() && objects[currIndex] != null;
        }

        /**
         * Returns object at current index and increments current index by one.
         * @return object at current index in list of objects
         */
        @Override
        public E next() {
            return (E) objects[currIndex++];
        }
    }
}
