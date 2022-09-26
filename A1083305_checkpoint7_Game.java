public class A1083305_checkpoint7_Game
{
    public static void main(String[] args)
    {
        String map_id = args[0];
        int algorithm = Integer.parseInt(args[2]);
        int jfScaler = Integer.parseInt(args[1]);
        A1083305_checkpoint7_GameFrame gameFrame = new A1083305_checkpoint7_GameFrame(500, 500, map_id, jfScaler, algorithm);
        gameFrame.setVisible(true);
    }
}
