package agh.ics.oop;

class Animal {
    private MapDirection orient = MapDirection.NORTH;
    private Vector2d v = new Vector2d(2,2);

    public String toString() {
        return v.toString() + ' ' + orient.toString();
    }

    boolean isAt(Vector2d position) {return getPos().equals(position);}

    public MapDirection getDirection() {return orient;}

    public Vector2d getPos() {return v;}

    public void move(MoveDirection direction) {

        switch (direction) {
            case RIGHT -> orient = orient.next();
            case LEFT -> orient = orient.previous();
            case FORWARD -> {
                Vector2d unitVector = orient.toUnitVector();
                if ((v.x + unitVector.x <= 4) && (v.y + unitVector.y <= 4) && (v.y + unitVector.y >= 0) && v.x + unitVector.x>=0) {
                    v = v.add(unitVector);
                }
            }
            case BACKWARD -> {
                Vector2d unitVector1 = orient.toUnitVector().opposite();
                if ((v.x + unitVector1.x <= 4) && (v.y + unitVector1.y <= 4) && (v.y + unitVector1.y >= 0) && (v.x + unitVector1.x>=0)) {
                    v = v.add(unitVector1);
            }
        }
        }
    }


}
