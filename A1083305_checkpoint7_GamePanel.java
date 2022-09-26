
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class A1083305_checkpoint7_GamePanel extends JPanel {
    // Description : the obstacle location set in GUI index version.
    private ArrayList<Integer[]> obstacleList;
    // Description : the obstacle images set. bar_id -> obstacle image
    private HashMap<Integer, Image> obstacleImg = new HashMap<>();
    // Description : the image object of the map.
    private Image mapImg = new ImageIcon("Resource/map.png").getImage();
    // Description : the displaysize of the map
    private int scaler;
    private ArrayList<A1083305_checkpoint7_House> houseList = new ArrayList<A1083305_checkpoint7_House>();
    private ArrayList<A1083305_checkpoint7_Barrack> barrackList = new ArrayList<A1083305_checkpoint7_Barrack>();
    private ArrayList<A1083305_checkpoint7_Pyramid> pyramidList = new ArrayList<A1083305_checkpoint7_Pyramid>();
    private ArrayList<A1083305_checkpoint7_Soldier> soldierList = new ArrayList<A1083305_checkpoint7_Soldier>();
    // Description : the normal image size.
    // Hint : while the mapsize is not normal size, you have to think of the
    // displaysize.
    private int originalGridLen = 256;
    // Description : the image displaysize.
    private int gridLen;
    // Description : the map center point x-axis location.
    // Hint : While dragging the map, you may need to set the map location via this.
    private Integer centerX = 0;
    // Description : the map center point y-axis location.
    // Hint : While dragging the map, you may need to set the map location via this.
    private Integer centerY = 0;

    public A1083305_checkpoint7_GamePanel(ArrayList<A1083305_checkpoint7_House> houseList, ArrayList<A1083305_checkpoint7_Barrack> barrackList,
            ArrayList<A1083305_checkpoint7_Pyramid> pyramidList,ArrayList<Integer[]> obstacleList, HashMap<Integer, Image> obstacleImg, int scaler) {
        this.obstacleList = obstacleList;
        this.scaler = scaler;
        this.obstacleImg = obstacleImg;
        this.houseList = houseList;
        this.barrackList = barrackList;
        this.pyramidList = pyramidList;
        gridLen = originalGridLen / scaler;
        centerX = (4096 / scaler) / 2;
        centerY = (4096 / scaler) / 2;
    }

    // Description : While painting this JPanel, we draw map on the given location and other obstacles.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 地圖起始點
        int mapX = 250-centerX;
        int mapY = 250-centerY;
        // 繪製地圖背景
        g.drawImage(mapImg, mapX, mapY, 4096/scaler, 4096/scaler, null);
        // 繪製障礙物
        for(Integer[] singleObstacle : obstacleList)
            g.drawImage(obstacleImg.get(singleObstacle[2]), mapX+singleObstacle[0]*gridLen, mapY+singleObstacle[1]*gridLen, gridLen, gridLen, null);
        
        // 設置建築物位置setLocation, getlocationX(). mapX. scaler
        for(A1083305_checkpoint7_House house : houseList)
            house.setLocation(mapX + house.getlocationX()*gridLen, mapY + house.getlocationY()*gridLen);
        for(A1083305_checkpoint7_Barrack barrack : barrackList)
            barrack.setLocation(mapX + barrack.getlocationX()*gridLen, mapY + barrack.getlocationY()*gridLen);
        for(A1083305_checkpoint7_Pyramid pyramid : pyramidList)
            pyramid.setLocation(mapX + pyramid.getlocationX()*gridLen, mapY + pyramid.getlocationY()*gridLen);
        for(A1083305_checkpoint7_Soldier soldier : soldierList)
            soldier.setLocation(mapX + soldier.getlocationX()*gridLen, mapY + soldier.getlocationY()*gridLen);
    }

    public Integer getCenterX() {
        return this.centerX;
    }

    public void setCenterX(Integer centerX) {
        this.centerX = centerX;
    }

    public Integer getCenterY() {
        return this.centerY;
    }

    public void setCenterY(Integer centerY) {
        this.centerY = centerY;
    }

    public Integer getGridLen() {
        return this.gridLen;
    }

    public void setHouseList(ArrayList<A1083305_checkpoint7_House> houseList) {
        this.houseList = houseList;
    }

    public void setBarrackList(ArrayList<A1083305_checkpoint7_Barrack> barrackList) {
        this.barrackList = barrackList;
    }

    public void setPyramidList(ArrayList<A1083305_checkpoint7_Pyramid> pyramidList) {
        this.pyramidList = pyramidList;
    }

    public void addToSoldierList(A1083305_checkpoint7_Soldier soldier){
        this.soldierList.add(soldier);
    }
    public ArrayList<A1083305_checkpoint7_Soldier> getSoldierList(){
        return soldierList;
    }

}
