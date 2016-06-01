package uam.eps.es.caframework.model.measurements;

import uam.eps.es.caframework.model.util.VectorUtils;

/**
 * Created by Ari on 29/05/2016.
 */
public class Measurement implements Cloneable {


    private final int[] representativeAxes;
    private float[] values;
    private final String sensorName;
    private final long timeStamp;
    private final int numberOfCoordinates;
    private final double vectorModule;

    public Measurement(String name, float[] values, int[] representativeAxes, long timeStamp) {
        this.sensorName = name;
        this.values = values;
        this.representativeAxes = representativeAxes;
        this.timeStamp = timeStamp;
        this.numberOfCoordinates = values.length;
        this.vectorModule = VectorUtils.module(values);
    }

    public float[] getValues() {
        return values.clone();
    }

    public void setValues(float[] newValues) {
        this.values = newValues;
    }

    public float getValue(int coordinate) {
        if (coordinate < numberOfCoordinates)
            return values[coordinate];
        return 0;
    }

    public int[] getRepresentativeAxes() { return this.representativeAxes; }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    @Override
    public String toString() {
        String toString = "sensorName : " + sensorName;
        toString += ", values : [";
        for (int i = 0; i < numberOfCoordinates - 1; i++) {
            toString += values[i] + ",";
        }
        toString += values[numberOfCoordinates-1] + "]";
        toString += ", module : " + vectorModule;
        return toString;
    }

    @Override
    protected Measurement clone() throws CloneNotSupportedException {
        Measurement clone = (Measurement) super.clone();
        clone.values = values.clone();
        return clone;
    }
}
