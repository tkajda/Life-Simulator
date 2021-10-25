package agh.ics.oop;


public class World {

    public static void main(String[] args) {

        int cnt =0;
        System.out.print("Start\n");
        for(String i: args) {
            if(cnt<args.length-1) {
                System.out.print(i + ',');
            }
            cnt++;
        }
        System.out.println(args[args.length-1]);


        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        Direction[] arr = change(args);
        run(arr);
        System.out.print("Stop\n");
    }

    static class Vector2d {

        final public int x;
        final public int y;

        public Vector2d (int x, int y) {
            this.x=x;
            this.y=y;
        }

        boolean precedes(Vector2d other) {
            return this.x <= other.x && this.y <= other.y;
        }

        boolean follows(Vector2d other) {
            return this.x >= other.x && this.y >= other.y;
        }

        Vector2d upperRight(Vector2d other) {
            return new Vector2d(Math.max(this.x, other.x), Math.max(this.x, other.x));
        }

        Vector2d lowerLeft(Vector2d other) {
            return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
        }

        Vector2d add(Vector2d other) {
            return new Vector2d(this.x+other.x,this.y+other.y);
        }

        Vector2d substract(Vector2d other) {
            return new Vector2d(this.x-other.x,this.y-other.y);
        }

        public boolean equals(Object other) {
            if (this == other)
                return true;
            if (!(other instanceof Vector2d))
                return false;
            Vector2d that = (Vector2d) other;
            return that.x==this.x && that.y==this.y;
        }

        Vector2d opposite() {
            return new Vector2d(-this.x, -this.y);
        }

    }




    public static void run(Direction[] args) {
        for(int i = 0; i < args.length;i++) {

            if (args[i] != null) {
                switch (args[i]) {
                    case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                    case BACKWARD-> System.out.println("Zwierzak idzie do tyłu");
                    case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                    case LEFT -> System.out.println("Zwierzak skręca w lewo");
                }
            }
        }
    }


    public static Direction[] change(String[] args){
        Direction[] enumargs = new Direction[args.length];
        int cnt=0;
        for(int i =0; i<args.length;i++) {
            enumargs[i] = switch(args[i]) {
                case "f" -> Direction.FORWARD;
                case "b" -> Direction.BACKWARD;
                case "r" -> Direction.RIGHT;
                case "l" -> Direction.LEFT;
                default -> null;

            };
        }
        return enumargs;
    }


}
