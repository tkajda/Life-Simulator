package agh.ics.oop;


class RectangularMap extends AbstractWorldMap {
    private final Border borders;

    public RectangularMap(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException();
        }
        borders = new Border(0, 0, width - 1, height - 1);
    }
    public Border getDrawingBorders() {
        return borders;
    }

}

