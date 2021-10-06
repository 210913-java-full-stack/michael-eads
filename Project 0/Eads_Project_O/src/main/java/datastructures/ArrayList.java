package datastructures;

import java.util.Arrays;

public class ArrayList<E> implements ArrayListInterface<E>{
    private int size = 0;
    private Object items[];
    private static int default_amnt = 5;

    public ArrayList()
    {
        items = new Object[default_amnt];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(E e) {
    //this is for if we are full in the arraylist
        if(size >= items.length){
            doubleSize();
        }
        items[size++] = e;       //adds the element to the end if not full
    }

    private void doubleSize() {
        int newSize = items.length *2;
        //Don't forget to copy the array
        items = Arrays.copyOf(items, newSize);
    }

    @Override
    public void add(E e, int index){
        //check the size and set up the exception
        if(index >= size || index <0){
            throw new IndexOutOfBoundsException(index + " is out of bonds.");
        }
        //if array is already full, make it bigger
        if((size+1)>= items.length){
            doubleSize();
        }
        //shift the items down to add new item at the chosen spot
        for(int i = size;i >= index; i--){
            items[i+1] = items[i];
        }
        //putting the item here
        items[index] = e;
        size++;
    }


    @Override
    public E get(int index) {
        //get the index of current location
        return (E) items[index];
    }

    @Override
    public void remove(int index) {
        //readjusting the list by moving everything up to the point the user chose
        for(int i=index;i<size;i++){
            items[i] = items[i+1];
        }
        size--;
    }

    @Override
    public void clear() {
        //creating an empty array at the same size as the old one
        Object newArray[] = new Object[size];
        //replacing old array with new, clearing the old one
        items = Arrays.copyOf(newArray, size);
        size=0;
    }

    @Override
    public int contains(E e) {
        //sorting through the items, returning the index of any matches
        for(int i=0;i<size;i++){
            if(items[i] == e){
                return i;
            }
            else return -1;
        }
        return -1;
    }
}
