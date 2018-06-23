package sample;

import sample.NameComparator;
import sample.Produkt;

import java.util.ArrayList;
import java.util.Comparator;

public class Bubblesort {

    public void namesort(ArrayList<Produkt> list){

        Comparator<Produkt> comparator = new NameComparator();
        bubblesort(list, comparator);


    }


    public void bubblesort(ArrayList<Produkt> list, Comparator<Produkt> comparator) {

        for( int i = 0; i < list.size()-1;  i++) {
            for ( int j = i+ 1; j < list.size(); j++){
                if (comparator.compare(list.get(i), list.get(j)) > 0) {

                    Produkt temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);


                }


            }
        }
    }

}
