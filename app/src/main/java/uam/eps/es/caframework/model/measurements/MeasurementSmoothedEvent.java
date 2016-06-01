package uam.eps.es.caframework.model.measurements;

import uam.eps.es.caframework.model.util.VectorUtils;

public class MeasurementSmoothedEvent extends MeasurementEvent {

    public static float SMOOTH_FACTOR = 0.3f;
    private static float[] smoothedValues;

    public MeasurementSmoothedEvent(Measurement measurement) {
        smoothedValues = VectorUtils.smooth(measurement.getValues(), smoothedValues, SMOOTH_FACTOR);
        measurement.setValues(smoothedValues);
        this.setMeasurament(measurement);
    }
}
