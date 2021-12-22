package agh.ics.oop.WorldClasses;

import agh.ics.oop.WorldClasses.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Gene {
    List<Integer> genes = new ArrayList<>();
    public static int SIZE = 32;
    int i = 0;


    //if animal has no parents/placed on the map at day 0 - gets random genes
    public void setRandomGenes() {
        Random rng = new Random();
        for (int i =0; i< SIZE; i++) {
            this.genes.add(rng.nextInt(8));
        }
        Collections.sort(this.genes);
    }

    //animal is born
    //choose dominant genotype
    public void setGenes(Animal parent1, Animal parent2) {
        if (parent1.getEnergy() == parent2.getEnergy()) {
            for(int i =0; i<SIZE/2;i++) {
                this.genes.add(parent1.getAnimalGenes().get(i));
            }
            for(int i =SIZE/2; i<SIZE;i++){
                this.genes.add(parent2.getAnimalGenes().get(i));
            }
        }
        else if(parent1.getEnergy()> parent2.getEnergy()) {
            setGenesBasedOnParents(parent1,parent2);
        }
        else {
            setGenesBasedOnParents(parent2,parent1);
        }
    }

    //choose a side for dominant parent and set genes lamenting both parents' genes
    public void setGenesBasedOnParents(Animal parent1, Animal parent2) {
        Random rndGenerator = new Random();
        int side = rndGenerator.nextInt(2);

        int proportion = (int) (parent1.energy/parent2.energy)*100;

        if (side == 0) {
            for (int i =0; i<proportion; i++) {
                this.genes.add(parent1.getAnimalGenes().get(i));
            }
            for (int i = proportion; i< SIZE;i++) {
                this.genes.add(parent2.getAnimalGenes().get(i));
            }
        }

        else {
            for (int i = 0; i < SIZE - proportion; i++) {
                this.genes.add(parent2.getAnimalGenes().get(i));
            }
            for (int i = SIZE - proportion; i < SIZE; i++) {
                this.genes.add(parent2.getAnimalGenes().get(i));
                }
            }
    }



    public List<Integer> getGenes() {
        return this.genes;
    }


}
