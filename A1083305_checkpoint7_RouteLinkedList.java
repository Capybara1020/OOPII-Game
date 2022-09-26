public class A1083305_checkpoint7_RouteLinkedList{
    private A1083305_checkpoint7_Node head;
    //Description : the constructor of leading the head Node as null.
    public A1083305_checkpoint7_RouteLinkedList(){
        this.head = null;
    }
    //Description : the constructor of input a Node as the head node.
    public A1083305_checkpoint7_RouteLinkedList(A1083305_checkpoint7_Node head){
        this.head = head;
    }
    public void delete(int axis, int direction){ 
        //TODO(7):Input value of Node as the reference Node,
        A1083305_checkpoint7_Node current = head;
        A1083305_checkpoint7_Node prev = head;
        while(current != null)
        {
            if(current.getAxis() == axis && current.getDirection() == direction)
            {
                prev.setNext(current.getNext());
                current.setNext(null);
                break;
            }
            prev = current;
            current = current.getNext();
        }
    }

    public A1083305_checkpoint7_Node search(int axis, int direction){ 
        //TODO(8):Input value of Node as the reference Node,
        //        you have to find the first Node that is same as the reference Node,
        //        and return it.
        A1083305_checkpoint7_Node current = head;
        while(current != null)
        {
            if(current.getAxis() == axis && current.getDirection() == direction)
                return current;
            current = current.getNext();
        }
        return current;
    }
    /*
    public void insert(int referenceAxis, int referenceDirection, int axis, int direction){ 
        //TODO(9):Input value of Node as the reference Node,
        //        and insert a Node BEFORE the first Node same as the reference Node,
        //        and connect the following one and the previous one.
        //Hint:The value of the Node is int variable axis and dirsction.
        //Hint2:If there is no reference node in linkedlist, print "Insertion null".
        
    }*/
    public int length(){
        //TODO(10):return how long the LinkedList is.
        A1083305_checkpoint7_Node current = head;
        int count = 0;
        while(current != null)
        {
            count++;
            current = current.getNext();
        }
        return count;
    }
    public void append(int axis, int direction){
        A1083305_checkpoint7_Node current = head;
        A1083305_checkpoint7_Node newNode = new A1083305_checkpoint7_Node(direction,axis);
        if(head == null){
            head = newNode;
        }else {
            while(current.getNext() != null){
                current=current.getNext();
            }
            current.setNext(newNode);
        }
    }
    public A1083305_checkpoint7_Node getHead(){
        return this.head;
    }
    public void setHead(A1083305_checkpoint7_Node head){
        this.head = head;
    }
}
    

