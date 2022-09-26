import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class A1083305_checkpoint7_GameFrame extends JFrame {
    // Description : Width of Frame
    private int FWidth;
    // Description : Height of Frame
    private int FHeight;
    // Description : the displaysize of the map
    public int jfScaler = 2;
    // Description : the obstacle images set. bar_id -> obstacle image
    private HashMap<Integer, Image> obstacleImg = new HashMap<>();
    // Description : the filenames of the obstacle image set. bar_id -> filename
    private HashMap<Integer, String> typeChar = new HashMap<Integer, String>();
    // Description : the obstacle location set queryed from database
    private ArrayList<Integer[]> obstacleDataStructure;
    // Description : the obstacle location set in GUI index version.
    private ArrayList<Integer[]> obstacleList;
    // Description : the object to query data.
    private A1083305_checkpoint7_QueryDB querydb;
    private static ArrayList<A1083305_checkpoint7_House> houseList = new ArrayList<A1083305_checkpoint7_House>();
    private static ArrayList<A1083305_checkpoint7_Barrack> barrackList = new ArrayList<A1083305_checkpoint7_Barrack>();
    private static ArrayList<A1083305_checkpoint7_Pyramid> pyramidList = new ArrayList<A1083305_checkpoint7_Pyramid>();
    private static int PressedX = 0;
    private static int PressedY = 0;
    private static int ReleasedX = 0;
    private static int ReleasedY = 0;
    private static int ClickedX = 0;
    private static int ClickedY = 0;
    private static int keytype = 1;
    // 升序編號
    private static int count1 = 0;
    private static int count2 = 0;
    private static int count3 = 0;
    //Description : the cost of sand weight;
    private final int GRASSWEIGHT = 3;
    //Description : the cost of space weight;
    private final int SAPCEWEIGHT = 1;
    // Description : The main panel.
    public A1083305_checkpoint7_GamePanel gamePanel;
    // Description : The UI panel of spawnMenu.
    public A1083305_checkpoint7_SpawnMenu spawnMenu;
    // Description : The soldier that is selected.
    public A1083305_checkpoint7_Soldier selectedSoldier;
    //Description : the map with all blocks.
    //You can get the location block you want with typing map[x][y].
    private A1083305_checkpoint7_Block[][] map;
    // Description : The route searching algorithm.
    public  int algorithm;
    public A1083305_checkpoint7_GameFrame(int FWidth, int FHeight, String mapID, int jfScaler, int algorithm) throws HeadlessException {
        this.FWidth = FWidth;
        this.FHeight = FHeight;
        this.setTitle("Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FWidth, FHeight);
        this.jfScaler = jfScaler;
        this.obstacleList = new ArrayList<Integer[]>();
        this.obstacleDataStructure = new ArrayList<Integer[]>();
        this.querydb = new A1083305_checkpoint7_QueryDB();
        this.querydb.setMapID(mapID);
        this.algorithm = algorithm;
        /********************************** The TODO (Checkpoint7) ********************************
         * 
         * TODO(1): This time you need to create a map  recording  the info. of every blocks.   
         * Hint 1: You could use "createMap" after using "toGUIIdx" to create the map. 
         * 
        ********************************** The End of the TODO **************************************/
        // TODO(Past): You need to get the obstacle from database and transform it into
        // GUI index version and set your map(panel) on the frame.
        // Hint: In order to build Hashmap obstacleImg, key means the bar_type from
        // database and value equals the Image class that load from the corresponding
        // filepath.
        // Hint2: To get the obstacle set from database, we need you to realize the
        // queryData() in the object QueryDB and get the result.
        // Hint3: obstacle is transformed by obstacleDataStructure via toGUIIdx() in
        // order to let the location transformed from database to panel location.(GUI
        // index version)
        // Hint4: ObstacleDataStructure is a Integer array ([row, column, bartype]) like
        // ArrayList.
        // Obstacle is a Integer array ([x_coordinate, y_coordinate, bartype]) like
        // ArrayList.
        // TODO(Past): This time you need to add a spawnMenu at the bottom of main frame, and set the parent frame.
        // Hint 1: You could use "BorderLayout.SOUTH" to add something at the bottom of main frame.

        spawnMenu = new A1083305_checkpoint7_SpawnMenu();
        this.add(spawnMenu, BorderLayout.SOUTH);
        setFocusable(true);

        querydb.queryData(obstacleDataStructure, typeChar);

        // HashMap(Integer, Image)
        for(Integer i : typeChar.keySet())
        {
            String path = "Resource/" + typeChar.get(i);
            Image img = new ImageIcon(path).getImage();
            obstacleImg.put(i, img);
        }
        // to GUI index version (障礙物座標,GUI座標) ArrayList<Integer[]>
        toGUIIdx(obstacleDataStructure, obstacleList);

        //TODO(1)
        map = createMap(16,16);
        
        gamePanel = new A1083305_checkpoint7_GamePanel(houseList, barrackList, pyramidList, obstacleList, obstacleImg, jfScaler);
        add(gamePanel);

        /********************************************************************************************
         * END OF YOUR CODE
         ********************************************************************************************/

        this.addComponentListener(new ComponentAdapter() {
            @Override
            // Description : While resizing the windows, the evnet will be happenned(Reset
            // the location of player).
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (houseList.size() != 0) {
                    int x = gamePanel.getWidth() / 2 - gamePanel.getCenterX();
                    int y = gamePanel.getHeight() / 2 - gamePanel.getCenterY();
                    for (A1083305_checkpoint7_House house : houseList) {
                        house.setLocation(x + house.getlocationX() * gamePanel.getGridLen(),
                                y + house.getlocationX() * gamePanel.getGridLen());
                    }
                }
            }
        });
        // TODO(Past): For key event here, you should implement key pressed here.
        // Hint1: For example, after pressing 'b', the building should change to barrack
        // when mouse clicked.
        // Hint2: You should get keyChar and set it into keytype.
        // Hint3: 'h' represents to house, 'b' represents to barrack, and others
        // represent to pyramid.
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                requestFocusInWindow();
                char key = e.getKeyChar();
                if (key == 'h') {
                    keytype = 1;
                } else if (key == 'b') {
                    keytype = 2;
                } else {
                    keytype = 3;
                }
            }

        });
        // TODO(Past): For mouse event here, you should implement map drag here.
        // Hint: For example, if you click on the top and release in the bottom, the map
        // should be dragged from up to down.
        // Hint: You should got both pressed location and release location and than
        // calculate the moving.
        gamePanel.addMouseListener(new MouseAdapter() {
            // Description : the event happenned while mouse be pressed.
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                requestFocusInWindow();
                super.mousePressed(e);
                PressedX = e.getX();
                PressedY = e.getY();
            }

            // Description : the event happenned while mouse be released
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                requestFocusInWindow();
                super.mouseReleased(e);
                ReleasedX = e.getX();
                ReleasedY = e.getY();
                int displacementX = ReleasedX - PressedX;
                int displacementY = ReleasedY - PressedY;
                gamePanel.setCenterX(gamePanel.getCenterX()-displacementX);
                gamePanel.setCenterY(gamePanel.getCenterY()-displacementY);
                gamePanel.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                requestFocusInWindow();
                ClickedX = e.getX();
                ClickedY = e.getY();
                int locationx = 0;
                int locationy = 0;
                
                if((ClickedX-250+gamePanel.getCenterX()) < 0)
                    locationx = -1;
                if((ClickedY-250+gamePanel.getCenterY()) < 0)
                    locationy = -1;
                if((ClickedX-250+gamePanel.getCenterX()) >= 0)
                    locationx = (ClickedX-250+gamePanel.getCenterX())/gamePanel.getGridLen();
                if((ClickedY-250+gamePanel.getCenterY()) >= 0)
                    locationy = (ClickedY-250+gamePanel.getCenterY())/gamePanel.getGridLen();
                // 士兵
                if(selectedSoldier != null)
                {
                    if( locationx > 15 || locationy > 15 || locationx < 0 || locationy < 0)
                    {
                        selectedSoldier.isSelected = false;
                        selectedSoldier.repaint();
                        selectedSoldier = null;
                        return;
                    }
                    synchronized (selectedSoldier)
                    {
                        selectedSoldier.setDestination(locationx, locationy);
                        selectedSoldier.notify();
                        selectedSoldier = null;
                    }
                }
                else
                {
                    // 建築物
                    if(locationVarify(locationx,locationy,true) == false)
                    {
                        if(keytype == 1)
                        {
                            A1083305_checkpoint7_House house = new A1083305_checkpoint7_House(locationx, locationy, String.valueOf(count1), jfScaler, SwingConstants.CENTER);
                            Thread t = new Thread(house);
                            t.start();
                            houseList.add(house);
                            gamePanel.add(house);
                            gamePanel.setHouseList(houseList);
                            gamePanel.revalidate();
                            count1++;
                        
                        }
                        else if(keytype == 2)
                        {
                            A1083305_checkpoint7_Barrack barrack = new A1083305_checkpoint7_Barrack(locationx, locationy, String.valueOf(count2), jfScaler, SwingConstants.CENTER);
                            Thread t = new Thread(barrack);
                            t.start();
                            barrackList.add(barrack);
                            gamePanel.add(barrack);
                            gamePanel.setBarrackList(barrackList);
                            gamePanel.revalidate();
                            count2++;
                        }
                        else
                        {
                            A1083305_checkpoint7_Pyramid pyramid = new A1083305_checkpoint7_Pyramid(locationx, locationy, String.valueOf(count3), jfScaler, SwingConstants.CENTER);
                            Thread t = new Thread(pyramid);
                            t.start();
                            pyramidList.add(pyramid);
                            gamePanel.add(pyramid);
                            gamePanel.setPyramidList(pyramidList);
                            gamePanel.revalidate();
                            count3++;
                        }
                    }
                    repaint();
                }
            }
        });
        gamePanel.addMouseMotionListener(new MouseAdapter() {
            // Description : the event happenned while mouse be dragged.
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                int displacementX = e.getX()-PressedX;
                int displacementY = e.getY()-PressedY;
                gamePanel.setCenterX(gamePanel.getCenterX()-displacementX);
                gamePanel.setCenterY(gamePanel.getCenterY()-displacementY);
                PressedX = e.getX();
                PressedY = e.getY();
                gamePanel.repaint();
            }

        });
        this.setFocusable(true);
        this.requestFocusInWindow();

    }

    // Description : transform the obstacle location from database version to GUI
    // index version
    // data is the database one, and the other.
    public static void toGUIIdx(ArrayList<Integer[]> data, ArrayList<Integer[]> dataGui) {
        for (Integer[] x : data) {
            dataGui.add(new Integer[] { x[0] - 1, x[1] - 1, x[2] });
        }
    }
    /********************************** The TODO (Checkpoint7) ********************************
    * TODO(2): At this time, grass isn't an obstacles, so you have to return false at the situation.
    * 
    /********************************** The TODO (Past) ********************************
    * TODO(Past): Here you will implement the method to check if the grid location passed in is empty.
    * If the location is empty, return false, else return true. The variable "isBuilding" is to check 
    * if you need to take building's  construction scope into consideration. If the "isBuilding" is true,
    * it means that now it's going to build a building, you need to take this building's construction scope
    * into consideration. On the other hand, you only need to check that grid location is empty or not, if
    * "isBuilding" is false.
    * Rules: There are several situations that will cause the location is not empty.
    * 1. There are obstacles on the location.
    * 2. There are buildings on the location.
    * 3. There are soldiers on the location.
    * Hint 1: There are diffirent construction scope for diffirent types of building.
    * Houses are 1*1 grid; Barracks are 1*1 grid; Pyramids are 2*2 grid.
    * Hint 2: You should consider about diffirent types of buildings' situation
    * while checking if there exists obstacle or building in buildings' construction scope.
    * For example, pyramid construction scope is 2*2, In other words, there should
    * be empty in pyramid construction scope.
    ***************************************** The End of the TODO**************************************/
    public boolean smallBuildingCheck(int locationx, int locationy, int x_axis, int y_axis)
    {
        if(keytype != 3 && (locationx == x_axis && locationy == y_axis))
            return true;
        else if(keytype == 3 && ((locationx == 15) || (locationy == 15) || (locationx == x_axis && locationy == y_axis) || (locationx == x_axis-1 && locationy == y_axis) || (locationx == x_axis && locationy == y_axis-1) || (locationx == x_axis-1 && locationy == y_axis -1)))
            return true;
        return false;
    }
    public boolean locationVarify(int locationx,int locationy,boolean isBuilding)
    {
        if( locationx > 15 || locationy > 15 || locationx < 0 || locationy < 0)
            return true;

        if(isBuilding)
        {
            for(Integer[] singleObstacle : obstacleList)
                if(smallBuildingCheck(locationx, locationy, singleObstacle[0], singleObstacle[1]) == true && singleObstacle[2] !=2 )
                    return true;

            for(A1083305_checkpoint7_House singlehouse : houseList)
                if(smallBuildingCheck(locationx, locationy, singlehouse.getlocationX(), singlehouse.getlocationY()) == true)
                    return true;

            for(A1083305_checkpoint7_Barrack singlebarrack : barrackList)
                if(smallBuildingCheck(locationx, locationy, singlebarrack.getlocationX(), singlebarrack.getlocationY()) == true)
                    return true;
        
            for(A1083305_checkpoint7_Soldier singleSoldier : gamePanel.getSoldierList())
                if(smallBuildingCheck(locationx, locationy, singleSoldier.getlocationX(), singleSoldier.getlocationY()) == true)
                    return true;

            for(A1083305_checkpoint7_Pyramid singlepyramid : pyramidList)
            {
                if(keytype != 3 && ((locationx == singlepyramid.getlocationX() && locationy == singlepyramid.getlocationY()) || (locationx-1 == singlepyramid.getlocationX() && locationy == singlepyramid.getlocationY()) || (locationx-1 == singlepyramid.getlocationX() && locationy-1 ==singlepyramid.getlocationY()) || (locationx == singlepyramid.getlocationX() && locationy-1 == singlepyramid.getlocationY())))
                    return true;
                else if(keytype == 3 && ((locationx == 15) || (locationy == 15) || (locationx == singlepyramid.getlocationX() && locationy == singlepyramid.getlocationY()) || (locationx == singlepyramid.getlocationX()-1 && locationy == singlepyramid.getlocationY()) || (locationx == singlepyramid.getlocationX() && locationy == singlepyramid.getlocationY()-1) || (locationx == singlepyramid.getlocationX()-1 && locationy == singlepyramid.getlocationY() -1) || (locationx+1 == singlepyramid.getlocationX() && locationy-1 == singlepyramid.getlocationY()) || (locationx == singlepyramid.getlocationX() && locationy-1 == singlepyramid.getlocationY()) || (locationx-1 == singlepyramid.getlocationX() && locationy-1 == singlepyramid.getlocationY()) || (locationx-1 == singlepyramid.getlocationX() && locationy == singlepyramid.getlocationY()) || (locationx-1 == singlepyramid.getlocationX() && locationy+1 == singlepyramid.getlocationY())))
                    return true;
            }
        }
        else
        {
            for(Integer[] singleObstacle : obstacleList)
                if(locationx == singleObstacle[0] && locationy == singleObstacle[1] && singleObstacle[2] != 2)
                    return true;

            for(A1083305_checkpoint7_House singlehouse : houseList)
                if(locationx == singlehouse.getlocationX() && locationy == singlehouse.getlocationY())
                    return true;

            for(A1083305_checkpoint7_Barrack singlebarrack : barrackList)
                if(locationx == singlebarrack.getlocationX() && locationy == singlebarrack.getlocationY())
                    return true;
        
            for(A1083305_checkpoint7_Soldier singleSoldier : gamePanel.getSoldierList())
                if(locationx == singleSoldier.getlocationX() && locationy == singleSoldier.getlocationY())
                    return true;
            for(A1083305_checkpoint7_Pyramid singlepyramid : pyramidList)
                if((locationx == singlepyramid.getlocationX() && locationy == singlepyramid.getlocationY()) || (locationx == singlepyramid.getlocationX()+1 && locationy == singlepyramid.getlocationY()) || (locationx == singlepyramid.getlocationX() && locationy == singlepyramid.getlocationY()+1) || (locationx == singlepyramid.getlocationX()+1 && locationy == singlepyramid.getlocationY()+1))
                    return true;
        }
        return false;
    }
    //Description : create the map, if each of the loaction will be tag as "grass", "obstacle" or "space".
    public A1083305_checkpoint7_Block[][] createMap(int height,int width){
        A1083305_checkpoint7_Block[][] map = new A1083305_checkpoint7_Block[width][height];
        for (Integer[] block: obstacleList){
            int cost = block[2] == 2 ? GRASSWEIGHT : 100;
            String type = block[2] == 2 ? "grass" : "obstacle";
            map[block[0]][block[1]] = new A1083305_checkpoint7_Block(block[0], block[1], type, cost);
        }
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                if(map[x][y] == null){
                    map[x][y] = new A1083305_checkpoint7_Block(x,y,"space",SAPCEWEIGHT);
                }
            }
        }
        return map;
    }
    public A1083305_checkpoint7_Block[][] getMap(){
        return this.map;
    }
}
