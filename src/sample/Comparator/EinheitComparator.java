package sample.Comparator;

import sample.Produkt;

import java.util.Comparator;

public class EinheitComparator implements Comparator<Produkt> {

    public int compare(Produkt o1, Produkt o2) {
        if(o1.getEinheit() == null){ o1.setEinheit("x");}
        if(o2.getEinheit() == null){ o2.setEinheit("x");}
        return o1.getEinheit().compareTo(o2.getEinheit());

    }
}
