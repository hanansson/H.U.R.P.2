package sample;

import sample.Comparator.AnzahlComparator;
import sample.Comparator.ArtComparator;
import sample.Comparator.DateComparator;
import sample.Comparator.NameComparator;

import java.util.ArrayList;
import java.util.Comparator;

public class Quicksort {


    public void namequicksort(ArrayList<Produkt> list) {

        Comparator<Produkt> comp = new NameComparator();
        _quicksort(list, 0, list.size() - 1, comp);
    }

    public void datequicksort(ArrayList<Produkt> list) {

        Comparator<Produkt> comp = new DateComparator();
        _quicksort(list, 0, list.size() - 1, comp);
    }

    public void artquicksort(ArrayList<Produkt> list) {

        Comparator<Produkt> comp = new ArtComparator();
        _quicksort(list, 0, list.size() - 1, comp);
    }

    public void anzahlquicksort(ArrayList<Produkt> list) {

        Comparator<Produkt> comp = new AnzahlComparator();
        _quicksort(list, 0, list.size() - 1, comp);
    }


    private static void _quicksort(ArrayList<Produkt> list, int leftIndex, int rightIndex, Comparator<Produkt> comparator) {
        if (leftIndex >= rightIndex) {
            return;
        }

        int i = leftIndex;
        int j = rightIndex - 1;
        Produkt pivot = list.get(rightIndex);

        do {
            while (i < rightIndex && comparator.compare(list.get(i), pivot) <= 0) {
                i++;
            }
            while (j > leftIndex && comparator.compare(list.get(j), pivot) >= 0) {
                j--;
            }
            if (i < j) {
                Produkt temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }

        } while (i < j);

        if (comparator.compare(list.get(i), pivot) > 0) {
            Produkt temp = list.get(i);
            list.set(i, list.get(rightIndex));
            list.set(rightIndex, temp);
        }

        _quicksort(list, leftIndex, i - 1, comparator);
        _quicksort(list, i + 1, rightIndex, comparator);


    }
}
