package uam.eps.es.caframework;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import uam.eps.es.caframework.model.sensormanager.MySensorManager;
import uam.eps.es.caframework.model.sensormanager.exceptions.NullAndroidSensorManagerException;
import uam.eps.es.caframework.ui.RealtimeSensorGraphFragment;
import uam.eps.es.caframework.ui.SensorSelectionSpinnerFragment;

public class MainActivity extends AppCompatActivity implements SensorSelectionSpinnerFragment.SensorDisplayChanged{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public MySensorManager mySensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager androidSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mySensorManager = MySensorManager.getInstance();
        mySensorManager.initAndroidSensorManager(androidSensorManager);
        try {
            mySensorManager.registerSensor(Sensor.TYPE_GYROSCOPE, new int[]{0, 1, 2}, SensorManager.SENSOR_DELAY_NORMAL);
        } catch (NullAndroidSensorManagerException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        RealtimeSensorGraphFragment realtimeSensorGraphFragment = (RealtimeSensorGraphFragment)
                fragmentManager.findFragmentById(R.id.graph_fragment);

        mySensorManager.registerListener(realtimeSensorGraphFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mySensorManager.unregisterSensor(Sensor.TYPE_ACCELEROMETER);
        } catch (NullAndroidSensorManagerException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public void resetDisplayedData(Float sensorRange, int[] sensorAxes) {
        RealtimeSensorGraphFragment graphFragment = (RealtimeSensorGraphFragment)
                getSupportFragmentManager().findFragmentById(R.id.graph_fragment);
        mySensorManager.unregisterListener(graphFragment);
        mySensorManager.registerListener(graphFragment);
        graphFragment.resetGraphData(sensorRange, sensorAxes);
    }
}
