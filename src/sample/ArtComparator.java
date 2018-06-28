package sample;

import sample.Produkt;

import java.util.Comparator;

public class ArtComparator implements Comparator<Produkt> {

    @Override
    public int compare(Produkt o1, Produkt o2) {
        return o1.getArt().compareTo(o2.getArt());
    }
}