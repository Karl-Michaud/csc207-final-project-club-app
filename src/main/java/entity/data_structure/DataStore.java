package entity.data_structure;

/**
 * An abstract data structure.
 * @param <T> an object for the data structure.
 */
public interface DataStore<T> {
    /**
     * Adds element to data structure.
     * @param element value to add to list
     */
    void add(T element);

    /**
     * Removes element from data structure and returns its value.
     * @param element element to remove.
     * @return returns the element that was removed.
     */
    T remove(T element);

    /**
     * Gets element from data structure.
     * @param element element to get.
     * @return returns the element.
     */
    T get(T element);

    /**
     * Returns boolean if an element exists in the data structure.
     * @param element element to check if contained.
     * @return Boolean value if contained or not.
     */
    Boolean contains(T element);

    /**
     * Returns size of data structure.
     * @return integer size.
     */
    Integer size();

    /**
     * Deletes and returns the last element of data structure.
     * An order must be established in implementation to insure the idea
     * of "last".
     * @return the last element.
     */
    T pop();

    /**
     * Returns the intersection between this DataStore and another.
     * @param other the other DataStore data structure to intersect.
     * @return the intersection.
     */
    DataStore<T> intersection(DataStore<T> other);

    /**
     * Returns the complement between this DataStore and another.
     * @param other the other DataStore data structure to find complement.
     * @return the complement.
     */
    DataStore<T> complement(DataStore<T> other);
}
