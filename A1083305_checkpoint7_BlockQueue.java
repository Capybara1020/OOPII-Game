import java.util.*;

public class A1083305_checkpoint7_BlockQueue implements A1083305_checkpoint7_Fringe {
    Queue<A1083305_checkpoint7_Block> queue;

    public A1083305_checkpoint7_BlockQueue(){
        queue = new LinkedList<A1083305_checkpoint7_Block>();
    }
    //Description : add the input object into Fringe
    public void add(A1083305_checkpoint7_Block block){
        queue.offer(block);
    }
    //Description : return and remove the object from Fringe.
    public A1083305_checkpoint7_Block remove(){
        return queue.poll();
    }
    //Description : Check if the Fringe has a object at least. if it is empty return true, vice versa. 
    public boolean isEmpty(){
        return queue.isEmpty();
    }
}