package agh.ics.oop;


public class Border {
    private final Vector2d bl, tr;

    public Border(int x0, int y0, int x1, int y1) {
        bl = new Vector2d(x0, y0);
        tr = new Vector2d(x1, y1);
    }

    public Border(Vector2d bl, Vector2d tr) {
        this.bl = bl;
        this.tr = tr;
    }


    public Vector2d getBL() {
        return bl;
    }

    public Vector2d getTR() {
        return tr;
    }


    public Border expanses(Vector2d p) {
        return new Border(
                bl.lowerLeft(p),
                tr.upperRight(p)
        );
    }
}