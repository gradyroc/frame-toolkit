package cn.grady.hbase.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author grady
 * @version 1.0, on 1:43 2022/1/19.
 */
public class ResultPrinter {

    public static final int COLLECTION_PRINTER_MAX_SIZE = 5;

    public static String printResult(Object result) {
        if (null == result) {
            return null;
        }

        if (result instanceof Collection) {
            Collection collection = (Collection) result;

            int size = collection.size();
            if (size > COLLECTION_PRINTER_MAX_SIZE) {
                return printIterator(size, collection.iterator());
            } else {
                return collection.toString();
            }
        } else if (result instanceof Map) {
            Map map = (Map) result;

            int size = map.size();
            if (size > COLLECTION_PRINTER_MAX_SIZE) {
                return printIterator(size, map.entrySet().iterator());
            } else {
               return map.toString();
            }
        } else {
            return result.toString();
        }

    }

    private static String printIterator(int size, Iterator iterator) {
        return "Size=" + size + ",First=" + printFirst(iterator) + ",Last=" + printLast(iterator);
    }

    private static String printLast(Iterator iterator) {
        if (null == iterator){
            return null;
        }else {
            String  last = null;
            while (iterator.hasNext()){
                last=  iterator.next().toString();
            }
            return last;
        }
    }

    private static String printFirst(Iterator iterator) {
        if (null == iterator){
            return null;
        }else {
            boolean hasNext = iterator.hasNext();
            if (hasNext){
                Object next = iterator.next();
                if (null != next){
                    return next.toString();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Collection collection = new ArrayList();
        collection.add(1);
        collection.add(2);
        collection.add(3);
        collection.add(4);
        collection.add(5);
        collection.add(6);
        String s = printResult(collection);
        System.out.println(s);
    }

}
