package agh.ics.oop.WorldClasses;

public class Jungle{
    private Vector2d jungleBL;
    private Vector2d jungleTR;
    private int mapHeight;
    private int mapWidth;
    private double jungleRatio;


    public Jungle(int mapHeight, int mapWidth, double jungleRatio) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.jungleRatio=jungleRatio;
        setJungle();
    }

    public int[] findRectangle(int width, int height, int surface) {
        int indexI=0, indexJ=0;
        double best = Integer.MAX_VALUE;
        for (int i = 1; i< width; i++) {
            for(int j =1;j<height;j++) {
                if (Math.abs((double)i*j/surface-jungleRatio)<=best) {
                    best = Math.abs((double)i*j/surface-jungleRatio);
                    indexI=i;
                    indexJ=j;
                }
            }
        }


        return new int[]{indexI, indexJ};
    }


    public void setJungle() {
        int surface = mapHeight*mapWidth;
        int[] whArr = findRectangle(mapWidth,mapHeight,surface);
        int jungleWidth = whArr[0];
        int jungleHeight = whArr[1];

        int midHeight = mapHeight/2;
        int midWidth = mapWidth/2;


        this.jungleBL = new Vector2d(midWidth-Math.floorDiv(jungleWidth-1, 2), midHeight-Math.floorDiv(jungleHeight-1, 2));
        this.jungleTR = new Vector2d(midWidth+Math.floorDiv(jungleWidth, 2), midHeight+Math.floorDiv(jungleHeight, 2));
    }



    public Vector2d getJungleBL() {
        return jungleBL;
    }
    public Vector2d getJungleTR() {
        return jungleTR;
    }
}
