package uam.eps.es.caframework.model.sensormanager;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;

import com.google.common.eventbus.EventBus;

import java.util.List;

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
