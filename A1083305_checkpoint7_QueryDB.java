import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class A1083305_checkpoint7_QueryDB {
    
    // Description : the obstacle set queried from database.
    private static ArrayList<Integer[]> data = new ArrayList<Integer[]>();
    // Description : the filename of obstacle image queried from database.
    private static HashMap<Integer, String> typeChar = new HashMap<Integer, String>();
    // Description : the primary key of map in database.
    private static String mapID = "0";

    public static void queryData(ArrayList<Integer[]> data, HashMap<Integer, String> typeChar)
    {
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://140.127.220.226:5432/oopiickp";
        String user = "fallckp";
        String passwd = "2021OOPIIpwd";
        String QUERY = "SELECT oi.x_coordinate, oi.y_coordinate, os.obstacle_type, os.filename "+
                       "FROM obstacle_style os, obstacle_info oi "+
                       "WHERE oi.map_id = "+ mapID +" AND os.obstacle_type = oi.obstacle_type";
        // load the driver
        try
        {
            Class.forName(driver).newInstance();
        }
        catch(Exception err)
        {
            System.out.println("Unable to load the driver.");
            err.printStackTrace(System.err);
            System.exit(0);
        }
        // Query
        try
        {
            Connection connection = DriverManager.getConnection(url,user,passwd);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY);

            while(resultSet.next())
            {
                Integer[] coordinate = {resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3)};
                data.add(coordinate);
                typeChar.put(resultSet.getInt(3), resultSet.getString(4));
            }
            connection.close();
        }
        catch(SQLException err)
        {
            System.err.println("SQL error.");
            err.printStackTrace(System.err);
            System.exit(0);
        }
    }

    public ArrayList getObstacle() {
        return this.data;
    }

    public void setObstacle(ArrayList<Integer[]> data) {
        this.data = data;
    }

    public String getMapID() {
        return this.mapID;
    }

    public void setMapID(String mapID) {
        this.mapID = mapID;
    }

    public HashMap getObstacleImg() {
        return this.typeChar;
    }

    public void setObstacleImg(HashMap<Integer, String> typeChar) {
        this.typeChar = typeChar;
    }
}
