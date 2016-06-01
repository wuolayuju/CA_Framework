package uam.eps.es.caframework.model.measurements;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.google.common.eventbus.EventBus;

import uam.eps.es.caframework.model.measurements.Measurement;

import static uam.eps.es.caframework.model.measurements.MeasurementEvent.*;

/**
 * Created by Ari on 29/05/2016.
 */
public class MeasurementListener implements SensorEventListener {

    EventBus eventBus;
    private Measurement lastMeasurement;
    private int[] representativeAxes;

    public MeasurementListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public MeasurementListener() {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Measurement measurement = new Measurement(event.sensor.getName(), event.values, representativeAxes, event.timestamp);
        MeasurementSmoothedEvent smoothedEvent = new MeasurementSmoothedEvent(measurement);
        lastMeasurement = measurement;
        eventBus.post(smoothedEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setRepresentativeAxes(int[] representativeAxes) {
        this.representativeAxes = representativeAxes;
    }

    public int[] getRepresentativeAxes() {
        return representativeAxes;
    }
}
