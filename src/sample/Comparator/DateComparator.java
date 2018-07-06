package sample.Comparator;

import sample.Produkt;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Produkt> {

    @Override
    public int compare(Produkt o1, Produkt o2) {
        if (o1.getDatum().getValue() == null || o2.getDatum().getValue() == null) {
            return 0;
            //asdsadas
        } else {
            return o1.getDatum().getValue().compareTo(o2.getDatum().getValue());
        }
    }
}