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
import uam.eps.es.caframework.ui.SmoothingControlsFragment;

public class MainActivity extends AppCompatActivity implements SensorSelectionSpinnerFragment.SensorDisplayChanged, SmoothingControlsFragment.OnClutchAxesChanged {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private MySensorManager mySensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager androidSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mySensorManager = MySensorManager.getInstance();
        mySensorManager.initAndroidSensorManager(androidSensorManager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        RealtimeSensorGraphFragment realtimeSensorGraphFragment = (RealtimeSensorGraphFragment)
                fragmentManager.findFragmentById(R.id.graph_fragment);

        mySensorManager.registerListener(realtimeSensorGraphFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mySensorManager.destroyListeners();
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

    @Override
    public void toggleClutchDataToRange(boolean clutchIt) {
        FragmentManager fm = getSupportFragmentManager();
        SensorSelectionSpinnerFragment sensorSelectionFragment = (SensorSelectionSpinnerFragment)
                fm.findFragmentById(R.id.sensor_selection_fragment);
        Float currentSensorRange = sensorSelectionFragment.getCurrentSensorRange();
        RealtimeSensorGraphFragment graphFragment = (RealtimeSensorGraphFragment)
                fm.findFragmentById(R.id.graph_fragment);
        graphFragment.setYAxisRange(clutchIt ? currentSensorRange : null);
    }
}
