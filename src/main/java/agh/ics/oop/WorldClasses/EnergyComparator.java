package agh.ics.oop.WorldClasses;

import java.util.Comparator;

public class EnergyComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal a1, Animal a2) {
        if(a1.getEnergy()>a2.getEnergy()) {
            return 1;
        }
        else if (a2.getEnergy()<a2.getEnergy()) {
            return -1;
        }
        return 0;
    }
}
