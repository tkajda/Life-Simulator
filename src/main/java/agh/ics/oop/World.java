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

        Direction[] arr = change(args);
        run(arr);
        System.out.print("Stop\n");
    }

    class Vector2d {

        final public int x;
        final public int y;

        public Vector2d (int x, int y) {
            this.x=x;
            this.y=y;
        }

        public boolean precedes(Vector2d other) {
            return this.x <= other.x && this.y <= other.x;
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
