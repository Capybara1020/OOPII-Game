import java.util.*;

public class A1083305_checkpoint7_BlockPriorityQueue implements A1083305_checkpoint7_Fringe {
    PriorityQueue<A1083305_checkpoint7_Block> priorityQueue;
    
    public A1083305_checkpoint7_BlockPriorityQueue(Comparator c){
        priorityQueue = new PriorityQueue<>(c);
    }
    public void add(A1083305_checkpoint7_Block block){
        priorityQueue.offer(block);
    }
    public A1083305_checkpoint7_Block remove(){
        return priorityQueue.poll();
    }
    public boolean isEmpty(){
        return priorityQueue.isEmpty();
    }
}