package sample;

import sample.Produkt;

import java.util.Comparator;

public class NameComparator implements Comparator<Produkt> {



        public int compare(Produkt o1, Produkt o2) {
            return o1.getName().compareTo(o2.getName());

        }
    }


