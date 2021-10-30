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

        Animal a = new Animal();
        System.out.println(a);

        Direction[] arr = change(args);
        MoveDirection[] t = OptionParser.parse(args);

        int i = 0;

        for(MoveDirection dir: t) {
            run(arr, i);
            i++;
            a.move(dir);
        }
        System.out.println(a);
        System.out.print("Stop\n");
    }






    public static void run(Direction[] args, int i) {

        if (args[i] != null) {
            switch (args[i]) {
                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD-> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case LEFT -> System.out.println("Zwierzak skręca w lewo");
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
