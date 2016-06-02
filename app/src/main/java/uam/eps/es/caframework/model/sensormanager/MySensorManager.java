package uam.eps.es.caframework.model.sensormanager;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.google.common.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import uam.eps.es.caframework.model.measurements.MeasurementListener;
import uam.eps.es.caframework.model.sensormanager.exceptions.NullAndroidSensorManagerException;
import uam.eps.es.caframework.ui.RealtimeSensorGraphFragment;

/**
 * Created by Ari on 29/05/2016.
 */
public class MySensorManager {

    private static MySensorManager instance = null;

    private Map<Integer, MeasurementListener> registeredSensorListeners;

    //private final MeasurementListener measurementListener;
    private final EventBus eventBus;
    private static SensorManager androidSensorManager;

    public static MySensorManager getInstance() {
        if (instance == null)
            instance = new MySensorManager();
        return instance;
    }

    protected MySensorManager() {
        this.eventBus = new EventBus("test");
        eventBus.register(new SensorEventHandler());
        registeredSensorListeners = new HashMap<>();
        //this.measurementListener = new MeasurementListener(eventBus);
    }

    public SensorManager getAndroidSensorManager() { return androidSensorManager; }

    public void initAndroidSensorManager(SensorManager androidSensorManager) {
        this.androidSensorManager = androidSensorManager;
    }

    public void registerSensor(int sensorType, int[] representativeAxes, int sensorDelay) throws NullAndroidSensorManagerException {
        checkAndroidSensorManager();
        Sensor defaultSensor = androidSensorManager.getDefaultSensor(sensorType);
        MeasurementListener measurementListener = new MeasurementListener(eventBus);
        measurementListener.setRepresentativeAxes(representativeAxes);
        registeredSensorListeners.put(sensorType, measurementListener);
        //androidSensorManager.unregisterListener(measurementListener);
        androidSensorManager.registerListener(measurementListener, defaultSensor, sensorDelay);
    }

    public void unregisterSensor(int sensorType) throws NullAndroidSensorManagerException {
        checkAndroidSensorManager();
        Sensor defaultSensor = androidSensorManager.getDefaultSensor(sensorType);
        MeasurementListener measurementListener = registeredSensorListeners.get(sensorType);
        registeredSensorListeners.remove(sensorType);
        androidSensorManager.unregisterListener(measurementListener, defaultSensor);
    }

    public float getSensorRange(int sensorType) {
        return androidSensorManager.getDefaultSensor(sensorType).getMaximumRange();
    }

    public void registerListener(Object listener) {
        eventBus.register(listener);
    }

    public void unregisterListener(Object listener) {
        eventBus.unregister(listener);
    }

    private void checkAndroidSensorManager() throws NullAndroidSensorManagerException {
        if (androidSensorManager == null)
            throw new NullAndroidSensorManagerException();
    }
}
