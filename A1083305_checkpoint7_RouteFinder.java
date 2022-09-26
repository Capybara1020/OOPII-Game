import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
public class A1083305_checkpoint7_RouteFinder {
    //Description : The target block.
    private A1083305_checkpoint7_Block target;
    //Description : The hashmap that records the parent block.
    private HashMap<A1083305_checkpoint7_Block, A1083305_checkpoint7_Block> ParentBlock;
    //Description : Record which block has been visited.
    private boolean[][] visited ;
    // Description : The root frame.
    private A1083305_checkpoint7_GameFrame parentFrame;
    //Description : the map with all blocks.
    //You can get the location block you want with typing map[x][y].
    private A1083305_checkpoint7_Block[][] map;
    //Description : record the cost if you go on the block.
    private HashMap<A1083305_checkpoint7_Block, Integer> accumulatedCost;
    // Description : The route searching algorithm.
    private int algorithm;
    private A1083305_checkpoint7_Fringe fringe;
    private static final int DFS = 0;
    private static final int BFS = 1;
    private static final int UCS = 2;
    public A1083305_checkpoint7_RouteFinder(A1083305_checkpoint7_GameFrame parentFrame, A1083305_checkpoint7_Block target, A1083305_checkpoint7_Block root,int algorithm, A1083305_checkpoint7_Block[][] map){
        /**********************************The TODO This Time (Checkpoint7)**************************
         * 
         * TODO(1): For the TODO here, you have to implement fringe according "algorithm".
         * 
         * Hint(1): The BFS algorithm needs to use the queue to work, so we make a object named BlockQueue for BFS.
         * Hint(2): The DFS algorithm needs to use the stack to work, so we make a object named BlockStack for DFS.
         * Hint(3): The UCS algorithm needs to use the priority queue  to work, so we make a object named PriorityQueue for UCS.
         * Hint(4): These three objects all implement the fringe, and the detail description can be found 
         *          in the code of Fringe.java, BlockQueue.java, BlockStack.java, BlockPriorityQueue.java.
         * Hint(5): You have to add the root (the player current location) into fringe.
         * Hint(6): To calculate the priority, you have to implement a Comparator<block> object and make 
         *          it as an input in the constructor of BlockPriorityQueue.
         * Hint(7): Before starting the searching, you need to initialize the accumulatedCost and set the root with
         *          its cost.
         **********************************The End of the TODO**************************************/
        this.target = target;
        this.ParentBlock = new HashMap<A1083305_checkpoint7_Block, A1083305_checkpoint7_Block>();
        this.parentFrame = parentFrame;
        this.visited = new boolean[4096 / 256][4096 / 256];
        this.accumulatedCost = new HashMap<A1083305_checkpoint7_Block, Integer>();
        this.algorithm = algorithm;
        this.map = map;
        for(int x = 0 ; x < 4096 / 256; x++ ){
            for(int y = 0 ; y < 4096 / 256; y++ ){
                visited[x][y] = false;
            }
        }
        /********************************************************************************************
         START OF YOUR CODE
        ********************************************************************************************/
        if(algorithm == 0)
            fringe = new A1083305_checkpoint7_BlockStack();
        if(algorithm == 1)
            fringe = new A1083305_checkpoint7_BlockQueue();
        if(algorithm == 2)
        {
            fringe = new A1083305_checkpoint7_BlockPriorityQueue(new Comparator<A1083305_checkpoint7_Block>() {
                @Override
                public int compare(A1083305_checkpoint7_Block b1, A1083305_checkpoint7_Block b2) {
                    return accumulatedCost.get(b1).compareTo(accumulatedCost.get(b2));
                }
            });
        }
        fringe.add(root);
        accumulatedCost.put(root,root.getCost());
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }
    // 找終點
    private A1083305_checkpoint7_Block search(){
        A1083305_checkpoint7_Block currentBlock;
        while(!fringe.isEmpty())
        {
            currentBlock = fringe.remove();
            visited[currentBlock.getX()][currentBlock.getY()] = true;
            System.out.println("Searching at ("+Integer.toString(currentBlock.getX())+", "+Integer.toString(currentBlock.getY())+")");
            if(currentBlock == target)
                return currentBlock;
            else
            {
                for(A1083305_checkpoint7_Block block : expand(currentBlock, ParentBlock, visited))
                {
                    ParentBlock.put(block, currentBlock);
                    fringe.add(block);
                }
            }
        }
        return null;
    }
    private ArrayList<A1083305_checkpoint7_Block> expand(A1083305_checkpoint7_Block currentBlock,HashMap<A1083305_checkpoint7_Block, A1083305_checkpoint7_Block> ParentBlock, boolean[][] visited){

        ArrayList<A1083305_checkpoint7_Block> expandpoint = new ArrayList<A1083305_checkpoint7_Block>();
        int x = currentBlock.getX();
        int y = currentBlock.getY();
        if(y-1 > -1 && !parentFrame.locationVarify(x, y-1, false) && !visited[x][y-1])
        {
            visited[x][y-1] = true;
            ParentBlock.put(map[x][y-1], currentBlock);
            accumulatedCost.put(map[x][y-1], accumulatedCost.get(currentBlock)+map[x][y-1].getCost());
            expandpoint.add(map[x][y-1]);
        }
        if(x-1 > -1 && !parentFrame.locationVarify(x-1, y, false) && !visited[x-1][y])
        {
            visited[x-1][y] = true;
            ParentBlock.put(map[x-1][y], currentBlock);
            accumulatedCost.put(map[x-1][y], accumulatedCost.get(currentBlock)+map[x-1][y].getCost());
            expandpoint.add(map[x-1][y]);
        }
        if(y+1 < 16 && !parentFrame.locationVarify(x, y+1, false) && !visited[x][y+1])
        {
            visited[x][y+1] = true;
            ParentBlock.put(map[x][y+1], currentBlock);
            accumulatedCost.put(map[x][y+1], accumulatedCost.get(currentBlock)+map[x][y+1].getCost());
            expandpoint.add(map[x][y+1]);
        }
        if(x+1 < 16 && !parentFrame.locationVarify(x+1, y, false) && !visited[x+1][y])
        {
            visited[x+1][y] = true;
            ParentBlock.put(map[x+1][y], currentBlock);
            accumulatedCost.put(map[x+1][y], accumulatedCost.get(currentBlock)+map[x+1][y].getCost());
            expandpoint.add(map[x+1][y]);
        }
        return expandpoint;
    }

    public ArrayList<A1083305_checkpoint7_Block> createRoute(){
        A1083305_checkpoint7_Block currentBlock = search();
        ArrayList<A1083305_checkpoint7_Block> route = new ArrayList<A1083305_checkpoint7_Block>();
        while(currentBlock != null)
        {
            route.add(currentBlock);
            currentBlock = ParentBlock.get(currentBlock);
        }
        Collections.reverse(route);
        route.remove(0);
        return route;
    }
}
