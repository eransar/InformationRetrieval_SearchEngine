package PartA;

import java.io.Serializable;
import java.util.Comparator;

public class DocComperator implements Serializable,Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof Term && o2 instanceof Term) {
            Term t1 = ((Term) o1);
            Term t2 = ((Term) o2);

//            int t1freq = 1;
//            for (Doc d : t1.getDocFrequency().keySet()) {
//                t1freq = t1.getDocFrequency().get(d);
//            }
//            int t2freq = 1;
//
//            for (Doc d : t2.getDocFrequency().keySet()) {
//                t2freq = t2.getDocFrequency().get(d);
//            }
//
//            if (t1freq > t2freq) {
//                return 1;
//            } else
//                return -1;
            if(t1.getTf()>t2.getTf())
                return 1;
            else
                return -1;

        }
        return -5;
    }


}
