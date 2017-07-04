package WidthFirst;

import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * Created by xdcao on 2017/7/4.
 */
public class Queue {

    private LinkedList queue=new LinkedList();

    public void enqueue(Object t){
        queue.addLast(t);
    }

    public Object dequeue(){
        return queue.removeFirst();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public boolean contains(Object t){
        return queue.contains(t);
    }



}
