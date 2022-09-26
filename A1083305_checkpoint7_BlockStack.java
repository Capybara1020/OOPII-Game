import java.util.*;

public class A1083305_checkpoint7_BlockStack implements A1083305_checkpoint7_Fringe {
    Stack<A1083305_checkpoint7_Block> stack;
    //Description : the constuctor of BlockQueue.
    public A1083305_checkpoint7_BlockStack(){
        //The TODO(3) This Time (Checkpoint7) : Initialize the stack.
        stack = new Stack<A1083305_checkpoint7_Block>();
    }
    public void add(A1083305_checkpoint7_Block block){
        //The TODO(3) This Time (Checkpoint7) : add block into the stack.
        stack.push(block);
    }
    public A1083305_checkpoint7_Block remove(){
        //The TODO(3) This Time (Checkpoint7) :First check the stack is empty or not and return and remove the object from the stack.
        // If stack is empty return null.
        return stack.pop();
    }
    public boolean isEmpty(){
        //The TODO(3) This Time (Checkpoint7) :Check the stack is empty or not.
        return stack.empty();
    }
}