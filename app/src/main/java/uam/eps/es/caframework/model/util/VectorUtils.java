package uam.eps.es.caframework.model.util;

import java.util.Arrays;

/**
 * Created by Ari on 01/06/2016.
 */
public class VectorUtils {

    public static double module(float[] values) {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += Math.pow(values[i], 2);
        }
        return Math.sqrt(sum);
    }

    public static double distance(float[] from, float[] to) {
        double sum = 0.0d;
        for (int i = 0; i < from.length; i++) {
            sum += Math.sqrt(to[i] - from[i]);
        }
        return Math.sqrt(sum);
    }

    public static float[] smooth(float[] input, float [] output, float smoothFactor) {
        if (output == null) return input;
        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + smoothFactor * (input[i] - output[i]);
        }
        return output;
    }

    public static String stringFromVector(int[] input) {
        return Arrays.toString(input);
    }

    public static int[] vectorFromString(String input) {
        String[] strings = input.replace("[", "").replace("]", "").split(", ");
        int result[] = new int[strings.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(strings[i]);
        }
        return result;
    }
}
