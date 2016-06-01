package uam.eps.es.caframework.model.sensormanager;

import com.google.common.eventbus.Subscribe;

import uam.eps.es.caframework.model.measurements.Measurement;

/**
 * Created by Ari on 30/05/2016.
 */
public class SensorEventHandler {

    @Subscribe
    public void listenForMeasurement(Measurement measurement) {
        System.out.println(measurement);
    }
}
