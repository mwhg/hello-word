package org.apache.inspiration.util;

import java.util.Iterator;

public class ConcatUtil {

    public static <T> String toString(Iterable<T> iterable) {
        Iterator<T> it = iterable.iterator();
        if (!it.hasNext()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        while (true) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(',');
            } else {
                return sb.append(']').toString();
            }
        }
    }

    public static <T> String concat(Iterable<T> iterable, String totalPrefix, String prefix, String split, String tail,
            String totalTail) {
        Iterator<T> iterator = iterable.iterator();
        return concat(iterator, totalPrefix, prefix, split, tail, totalTail);
    }

    public static <T> String concat(Iterator<T> iterator, String totalPrefix, String prefix, String split, String tail,
            String totalTail) {
        StringBuilder sb = new StringBuilder();
        sb.append(totalPrefix);
        if (!iterator.hasNext()) {
            return sb.append(totalTail).toString();
        }
        while (true) {
            sb.append(prefix).append(iterator.next()).append(tail);
            if (iterator.hasNext()) {
                sb.append(split);
            } else {
                return sb.append(totalTail).toString();
            }
        }
    }

    public static <T> String concat(Iterator<T> iterator, char totalPrefix, char prefix, char split, char tail,
            char totalTail) {
        StringBuilder sb = new StringBuilder();
        sb.append(totalPrefix);
        if (!iterator.hasNext()) {
            return sb.append(totalTail).toString();
        }
        while (true) {
            sb.append(prefix).append(iterator.next()).append(tail);
            if (iterator.hasNext()) {
                sb.append(split);
            } else {
                return sb.append(totalTail).toString();
            }
        }
    }

    public static <T> String concat(Iterator<T> iterator, String totalPrefix, String split, String totalTail) {
        StringBuilder sb = new StringBuilder();
        sb.append(totalPrefix);
        if (!iterator.hasNext()) {
            return sb.append(totalTail).toString();
        }
        while (true) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(split);
            } else {
                return sb.append(totalTail).toString();
            }
        }
    }

    public static <T> String concat(Iterator<T> iterator, char totalPrefix, char split, char totalTail) {
        StringBuilder sb = new StringBuilder();
        sb.append(totalPrefix);
        if (!iterator.hasNext()) {
            return sb.append(totalTail).toString();
        }
        for (;;) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(split);
            } else {
                return sb.append(totalTail).toString();
            }
        }
    }
}
