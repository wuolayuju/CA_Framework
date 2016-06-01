package uam.eps.es.caframework.model.measurements;

import android.util.Log;

import uam.eps.es.caframework.model.measurements.Measurement;

/**
 * Created by Ari on 01/06/2016.
 */
public abstract class MeasurementEvent {

    private final String LOG_TAG = MeasurementEvent.class.getSimpleName();

    private Measurement mMeasurament;

    public MeasurementEvent(Measurement measurement) {
        try {
            this.mMeasurament = measurement.clone();
        } catch (CloneNotSupportedException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());
        }
    }

    protected MeasurementEvent() {
    }

    public Measurement getmMeasurament() { return mMeasurament; }

    protected void setMeasurament(Measurement measurament) {
        this.mMeasurament = measurament;
    }
}

