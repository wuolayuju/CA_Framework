package uam.eps.es.caframework.model.sensormanager.exceptions;

/**
 * Created by Ari on 01/06/2016.
 */
public class NullAndroidSensorManagerException extends Exception {

    public NullAndroidSensorManagerException() {
        super("No Android Sensor Manager Defined");
    }
}
