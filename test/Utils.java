
import java.util.Vector;

public class Utils {

    public static String[] split(String str, char delimiter) {
        Vector v = new Vector();
        int start = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == delimiter) {
                v.addElement(str.substring(start, i));
                start = i + 1;
            }
        }
        v.addElement(str.substring(start));
        String[] result = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            result[i] = (String) v.elementAt(i);
        }
        return result;
    }

    public static Object[] reverse(Object[] arr) {
        Object[] result = new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[arr.length - i - 1];
        }
        return result;
    }

    public static int seed = 311107;

    public static double random() {
        seed ^= seed << 21;
        seed ^= seed >>> 35;
        seed ^= seed << 4;
        return Math.abs(((double) seed) / 2147483648.0);
    }
}
