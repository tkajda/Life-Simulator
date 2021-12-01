package agh.ics.oop;


class RectangularMap extends AbstractWorldMap {

    private final Vector2d bottomLeft;
    private final Vector2d topRight;

    public RectangularMap(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException();
        }
        bottomLeft = new Vector2d(0, 0);
        topRight = new Vector2d(width - 1, height - 1);
    }

    @Override
    public String toString() {
        return super.toString();
    }


    @Override
    public Vector2d[] getCorners() {
        return new Vector2d[]{
                bottomLeft,
                topRight};
    }



}