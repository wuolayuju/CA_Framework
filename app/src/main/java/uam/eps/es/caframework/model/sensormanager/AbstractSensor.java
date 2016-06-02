package uam.eps.es.caframework.model.sensormanager;

import android.hardware.SensorEventListener;

/**
 * Created by Ari on 29/05/2016.
 */
public class AbstractSensor {

    private int sensorType;
    private int samplingPeriod;
    private SensorEventListener sensorListener;

    public AbstractSensor(int sensor, int samplingPeriod) {
        this.sensorType = sensor;
        this.samplingPeriod = samplingPeriod;
    }

    public SensorEventListener getSensorListener() {
        return sensorListener;
    }

    public int getSamplingPeriod() {
        return samplingPeriod;
    }
}
