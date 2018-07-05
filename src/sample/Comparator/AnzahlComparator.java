package sample.Comparator;

import sample.Produkt;

import java.util.Comparator;

public class AnzahlComparator implements Comparator<Produkt> {

    public int compare(Produkt o1, Produkt o2) {
        return (int) o1.getAnzahl().getValue() - (int) o2.getAnzahl().getValue();
    }

}