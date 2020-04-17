package com.yaroslav_f.rts_genalg;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class GenAlgNetwork {
    private static final int POPULATION_SIZE = 1024;
    private static final int MAX_ITER = Integer.MAX_VALUE;
    private static final int MUTATION_CHANCE = 10;
    private static final double ELIT_RATE = 0.1;

    protected long iter;
    protected Individual solution;
    private int A, B, C, D, Y;
    private ArrayList<Individual> population = new ArrayList<>();

    public boolean Train(int a, int b, int c, int d, int y, int mutation_growth) {
        Random random = new Random(System.currentTimeMillis());

        A = a;
        B = b;
        C = c;
        D = d;
        Y = y;

        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new Individual(
                    random.nextInt(Y / 2),
                    random.nextInt(Y / 2),
                    random.nextInt(Y / 2),
                    random.nextInt(Y / 2)));
        }

        iter = 0;
        while (iter++ < MAX_ITER) {
            for (int i = 0; i < POPULATION_SIZE; i++)
                population.get(i).Fitness = Math.abs(Y - Equation(population.get(i)));

            quickSort(population, 0, POPULATION_SIZE-1);;

            if (population.get(0).Fitness == 0) {
                solution = population.get(0);
                return true;
            }
            else {
                int elSize = (int) (POPULATION_SIZE * ELIT_RATE);

                for (int i = elSize; i < POPULATION_SIZE; i+=2) {
                    Individual p1 = population.get(random.nextInt(elSize));
                    Individual p2 = population.get(random.nextInt(elSize));
                    // crossover
                    switch (random.nextInt(4)) {
                        case 0:
                            population.get(i).X1 = p1.X1;
                            population.get(i).X2 = p1.X2;
                            population.get(i).X3 = p1.X3;
                            population.get(i).X4 = p1.X4;
                            population.get(i+1).X1 = p2.X1;
                            population.get(i+1).X2 = p2.X2;
                            population.get(i+1).X3 = p2.X3;
                            population.get(i+1).X4 = p2.X4;
                            break;
                        case 1:
                            population.get(i).X1 = p2.X1;
                            population.get(i).X2 = p1.X2;
                            population.get(i).X3 = p1.X3;
                            population.get(i).X4 = p1.X4;
                            population.get(i+1).X1 = p1.X1;
                            population.get(i+1).X2 = p2.X2;
                            population.get(i+1).X3 = p2.X3;
                            population.get(i+1).X4 = p2.X4;
                            break;
                        case 2:
                            population.get(i).X1 = p2.X1;
                            population.get(i).X2 = p2.X2;
                            population.get(i).X3 = p1.X3;
                            population.get(i).X4 = p1.X4;
                            population.get(i+1).X1 = p1.X1;
                            population.get(i+1).X2 = p1.X2;
                            population.get(i+1).X3 = p2.X3;
                            population.get(i+1).X4 = p2.X4;
                            break;
                        case 3:
                            population.get(i).X1 = p2.X1;
                            population.get(i).X2 = p2.X2;
                            population.get(i).X3 = p2.X3;
                            population.get(i).X4 = p1.X4;
                            population.get(i+1).X1 = p1.X1;
                            population.get(i+1).X2 = p1.X2;
                            population.get(i+1).X3 = p1.X3;
                            population.get(i+1).X4 = p2.X4;
                            break;
                    }
                    // mutation
                    if (random.nextInt(100) <= MUTATION_CHANCE + mutation_growth)
                        population.get(i).X1 = random.nextInt(Y / 2);
                    if (random.nextInt(100) <= MUTATION_CHANCE + mutation_growth)
                        population.get(i).X2 = random.nextInt(Y / 2);
                    if (random.nextInt(100) <= MUTATION_CHANCE + mutation_growth)
                        population.get(i).X3 = random.nextInt(Y / 2);
                    if (random.nextInt(100) <= MUTATION_CHANCE + mutation_growth)
                        population.get(i).X4 = random.nextInt(Y / 2);
                    if (random.nextInt(100) <= MUTATION_CHANCE + mutation_growth)
                        population.get(i+1).X1 = random.nextInt(Y / 2);
                    if (random.nextInt(100) <= MUTATION_CHANCE + mutation_growth)
                        population.get(i+1).X2 = random.nextInt(Y / 2);
                    if (random.nextInt(100) <= MUTATION_CHANCE + mutation_growth)
                        population.get(i+1).X3 = random.nextInt(Y / 2);
                    if (random.nextInt(100) <= MUTATION_CHANCE + mutation_growth)
                        population.get(i+1).X4 = random.nextInt(Y / 2);
                }
            }
        }
        return false;
    }

    private void quickSort(ArrayList<Individual>arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
    }

    private int partition(ArrayList<Individual>arr, int begin, int end) {
        int pivot = arr.get(end).Fitness;
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr.get(j).Fitness <= pivot) {
                i++;

                Individual swapTemp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, swapTemp);
            }
        }

        Individual swapTemp = arr.get(i+1);
        arr.set(i+1, arr.get(end));
        arr.set(end, swapTemp);

        return i+1;
    }

    private int Equation(Individual i) {
        return A * i.X1 + B * i.X2 + C * i.X3 + D * i.X4;
    }

}
