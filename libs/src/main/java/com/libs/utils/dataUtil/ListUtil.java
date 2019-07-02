package com.libs.utils.dataUtil;

import android.text.TextUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @ description: List工具类
 * @ author: mo
 * @ date: 2018/1/16 0016 16:15
 */
public class ListUtil {
    private ListUtil() {
        throw new AssertionError();
    }
    /**
     * default join separator
     **/
    public static final String DEFAULT_JOIN_SEPARATOR = ",";
    /**
     * 判断集合是否为空
     * @param list 集合
     * @return 是否为空
     */
    public static <T> boolean isEmpty(List<T> list) {
        return (list == null || list.size() == 0);
    }

    /**
     * 截取list
     *
     * @param list 原有list
     * @param num  想要前多少位的数据
     * @param <T>  数据类型
     * @return
     */
    public static <T> List<T> getList(List<T> list, int num) {
        List<T> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i < num) {
                list2.add(list.get(i));
            }
        }
        return list2;
    }

    /**
     * 添加指定个数的数据
     *
     * @param list 原有list
     * @param num  添加个数
     * @param <T>  数据类型
     * @return
     */
    public static <T> List<T> addList(List<T> list, int num) {
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
        }
        for (int i = 1; i <= num; i++) {
            list.add(list.size() - 1, list.get(list.size() - 1));
        }
        return list;
    }

    /**
     * 去重
     *
     * @param list
     * @return
     */
    public static <T> void deleteRepeat(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(i).equals(list.get(j))) {
                    list.remove(j);
                }
            }
        }
    }
    /**
     * 反转集合
     *
     * @param list 集合
     * @return 反转的新集合
     */
    public static <T> List<T> invertList(List<T> list) {
        if (isEmpty(list)) {
            return list;
        }
        List<T> invertList = new ArrayList<>(list.size());
        for (int i = list.size() - 1; i >= 0; i--) {
            invertList.add(list.get(i));
        }
        return invertList;
    }
    /**
     * 将指定的集合转成 String 集合, 即将集合的元素转成或抽取成 String
     *
     * @param list            源集合
     * @param action 将元素转成 String 的可执行任务
     * @return 新的 String 集合
     */
    public static <T> List<String> toStringList(List<T> list, Action<String, T> action) {
        List<String> stringList = new ArrayList<>();

        if (list != null && action != null) {
            for (T t : list) {
                String item = action.call(t);
                if (item != null) {
                    stringList.add(item);
                }
            }
        }
        return stringList;
    }

    /**
     * 拼接集合中所有的元素
     *
     * @param list    集合
     * @param divider 元素之间的连接符
     * @return 拼接后的字符串
     */
    public static <T> String joint(List<T> list, String divider) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                builder.append(divider);
            }
            builder.append(list.get(i));
        }
        return builder.toString();
    }
    /**
     * 将集合转化成 int 数组
     *
     * @param list 指定的集合
     * @return 转化后得到的 int 数组
     */
    public static int[] toIntArray(List<Integer> list) {
        if (list == null) {return null;}
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * 将集合转化成 String 数组
     *
     * @param list 指定的集合
     * @return 转化后得到的 String 数组
     */
    public static String[] toStringArray(List<String> list) {
        if (list == null) {return null;}
        String[] array = new String[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * 将集合转化成数组
     *
     * @param list  指定的集合
     * @param <T>   泛型, 集合中的泛型或其父类
     * @return 转化后得到的数组
     */
    public static <T> T[] toArray(List<? extends T> list) {
        if (list == null) {
            return null;
        }
        return (T[]) list.toArray();
    }

    /**
     * 将集合转化成数组
     *
     * @param list  指定的集合
     * @param clazz 指定的数组类型的类
     * @param <T>   泛型, 集合中的泛型或其父类
     * @return 转化后得到的数组
     */
    public static <T> T[] toArray(List<? extends T> list, Class<T> clazz) {
        if (list == null) {
            return null;
        }
        T[] array = (T[]) Array.newInstance(clazz, list.size());
        return list.toArray(array);
    }

    /**
     * 将可迭代对象转为字符串表示, 如 [a, b, c]
     *
     * @param iterable 可迭代对象
     * @return 字符串
     */
    public static String toString(Iterable iterable) {
        return toString(iterable == null ? null : iterable.iterator());
    }

    /**
     * 将迭代器对象转为字符串表示, 如 [a, b, c]
     *
     * @param iterator 迭代器对象
     * @return 字符串
     */
    public static String toString(Iterator iterator) {
        if (iterator == null) {return "null";}
        if (!iterator.hasNext()){ return "[]";}
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (; ; ) {
            Object element = iterator.next();
            builder.append(element);
            if (!iterator.hasNext()){
                return builder.append(']').toString();}
            builder.append(',').append(' ');
        }
    }

    /**
     * byteArr转hexString
     * <p>例如：</p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes byte数组
     * @return 16进制大写字符串
     */
    public static String bytes2HexString(byte[] bytes) {
        char[] ret = new char[bytes.length << 1];
        for (int i = 0, j = 0; i < bytes.length; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * 整型数组求和
     *
     * @param ints
     * @return
     */
    public static int intsGetSum(int[] ints) {
        int sum = 0;

        for (int i = 0, len = ints.length; i < len; i++) {
            sum += ints[i];
        }

        return sum;
    }




    /**
     * get size of list
     * <p>
     * <pre>
     * getSize(null)   =   0;
     * getSize({})     =   0;
     * getSize({1})    =   1;
     * </pre>
     *
     * @param <V>
     * @param sourceList
     * @return if list is null or empty, return 0, else return {@link List#size()}.
     */
    public static <V> int getSize(List<V> sourceList) {
        return sourceList == null ? 0 : sourceList.size();
    }



    /**
     * compare two list
     * <p>
     * <pre>
     * isEquals(null, null) = true;
     * isEquals(new ArrayList&lt;String&gt;(), null) = false;
     * isEquals(null, new ArrayList&lt;String&gt;()) = false;
     * isEquals(new ArrayList&lt;String&gt;(), new ArrayList&lt;String&gt;()) = true;
     * </pre>
     *
     * @param <V>
     * @param actual
     * @param expected
     * @return
     */
    public static <V> boolean isEquals(ArrayList<V> actual, ArrayList<V> expected) {
        if (actual == null) {
            return expected == null;
        }
        if (expected == null) {
            return false;
        }
        if (actual.size() != expected.size()) {
            return false;
        }

        for (int i = 0; i < actual.size(); i++) {
            if (!ObjectUtil.isEquals(actual.get(i), expected.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * join list to string, separator is ","
     * <p>
     * <pre>
     * join(null)      =   "";
     * join({})        =   "";
     * join({a,b})     =   "a,b";
     * </pre>
     *
     * @param list
     * @return join list to string, separator is ",". if list is empty, return ""
     */
    public static String join(List<String> list) {
        return join(list, DEFAULT_JOIN_SEPARATOR);
    }

    /**
     * join list to string
     * <p>
     * <pre>
     * join(null, '#')     =   "";
     * join({}, '#')       =   "";
     * join({a,b,c}, ' ')  =   "abc";
     * join({a,b,c}, '#')  =   "a#b#c";
     * </pre>
     *
     * @param list
     * @param separator
     * @return join list to string. if list is empty, return ""
     */
    public static String join(List<String> list, char separator) {
        return join(list, new String(new char[]{separator}));
    }

    /**
     * join list to string. if separator is null, use {@link #DEFAULT_JOIN_SEPARATOR}
     * <p>
     * <pre>
     * join(null, "#")     =   "";
     * join({}, "#$")      =   "";
     * join({a,b,c}, null) =   "a,b,c";
     * join({a,b,c}, "")   =   "abc";
     * join({a,b,c}, "#")  =   "a#b#c";
     * join({a,b,c}, "#$") =   "a#$b#$c";
     * </pre>
     *
     * @param list
     * @param separator
     * @return join list to string with separator. if list is empty, return ""
     */
    public static String join(List<String> list, String separator) {
        return list == null ? "" : TextUtils.join(separator, list);
    }

    /**
     * add distinct entry to list
     *
     * @param <V>
     * @param sourceList
     * @param entry
     * @return if entry already exist in sourceList, return false, else add it and return true.
     */
    public static <V> boolean addDistinctEntry(List<V> sourceList, V entry) {
        return (sourceList != null && !sourceList.contains(entry)) ? sourceList.add(entry) : false;
    }

    /**
     * add all distinct entry to list1 from list2
     *
     * @param <V>
     * @param sourceList
     * @param entryList
     * @return the count of entries be added
     */
    public static <V> int addDistinctList(List<V> sourceList, List<V> entryList) {
        if (sourceList == null || isEmpty(entryList)) {
            return 0;
        }

        int sourceCount = sourceList.size();
        for (V entry : entryList) {
            if (!sourceList.contains(entry)) {
                sourceList.add(entry);
            }
        }
        return sourceList.size() - sourceCount;
    }

    /**
     * remove duplicate entries in list
     *
     * @param <V>
     * @param sourceList
     * @return the count of entries be removed
     */
    public static <V> int distinctList(List<V> sourceList) {
        if (isEmpty(sourceList)) {
            return 0;
        }

        int sourceCount = sourceList.size();
        int sourceListSize = sourceList.size();
        for (int i = 0; i < sourceListSize; i++) {
            for (int j = (i + 1); j < sourceListSize; j++) {
                if (sourceList.get(i).equals(sourceList.get(j))) {
                    sourceList.remove(j);
                    sourceListSize = sourceList.size();
                    j--;
                }
            }
        }
        return sourceCount - sourceList.size();
    }

    /**
     * add not null entry to list
     *
     * @param sourceList
     * @param value
     * @return <ul>
     * <li>if sourceList is null, return false</li>
     * <li>if value is null, return false</li>
     * <li>return {@link List#add(Object)}</li>
     * </ul>
     */
    public static <V> boolean addListNotNullValue(List<V> sourceList, V value) {
        return (sourceList != null && value != null) ? sourceList.add(value) : false;
    }

    /**
     * @see {@link ArrayUtil#getLast(Object[], Object, Object, boolean)} defaultValue is null, isCircle is true
     */
    @SuppressWarnings("unchecked")
    public static <V> V getLast(List<V> sourceList, V value) {
        return (sourceList == null) ? null : (V) ArrayUtil.getLast(sourceList.toArray(), value, true);
    }

    /**
     * @see {@link ArrayUtil#getNext(Object[], Object, Object, boolean)} defaultValue is null, isCircle is true
     */
    @SuppressWarnings("unchecked")
    public static <V> V getNext(List<V> sourceList, V value) {
        return (sourceList == null) ? null : (V) ArrayUtil.getNext(sourceList.toArray(), value, true);
    }


    static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public interface Action<R, P> {

        R call(P p);
    }
}