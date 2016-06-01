package uam.eps.es.caframework.ui;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uam.eps.es.caframework.R;
import uam.eps.es.caframework.model.sensormanager.MySensorManager;
import uam.eps.es.caframework.model.sensormanager.exceptions.NullAndroidSensorManagerException;
import uam.eps.es.caframework.model.util.VectorUtils;

/**
 * Created by Ari on 01/06/2016.
 */
public class SensorSelectionSpinnerFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private static final String LOG_TAG = SensorSelectionSpinnerFragment.class.getSimpleName();
    private Spinner sensorSelectorSpinner;
    private int lastSelectedItemPosition;

    private SensorDisplayChanged sensorDisplayCallback;

    public interface SensorDisplayChanged {
        void resetDisplayedData(Float sensorRange, int[] representativeAxes);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            sensorDisplayCallback = (SensorDisplayChanged) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement SensorDisplayChanged");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sensor_selection, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeLayoutVariables();

        List<Map<String, String>> sensorList = loadSensorList();
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getActivity(),
                sensorList,
                android.R.layout.simple_list_item_1,
                new String[]{"sensor_name"},
                new int[]{android.R.id.text1});

        sensorSelectorSpinner.setAdapter(simpleAdapter);
        sensorSelectorSpinner.setOnItemSelectedListener(this);
        lastSelectedItemPosition = sensorSelectorSpinner.getSelectedItemPosition();
    }

    private List<Map<String, String>> loadSensorList() {

        ArrayList<Map<String, String>> sensorList = new ArrayList<>();
        SensorManager androidSensorManager = MySensorManager.getInstance().getAndroidSensorManager();

        Sensor sensor = androidSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorList.add(putSensorData(sensor, new int[]{0, 1, 2}));

        sensor = androidSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorList.add(putSensorData(sensor, new int[]{0, 1, 2}));

        sensor = androidSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorList.add(putSensorData(sensor, new int[]{0}));

        sensor = androidSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorList.add(putSensorData(sensor, new int[]{0, 1, 2}));

        return sensorList;
    }

    private Map<String, String> putSensorData(Sensor sensor, int[] representativeAxes) {
        HashMap<String, String> sensorItem = new HashMap<>();
        sensorItem.put("sensor_name", sensor.getName());
        sensorItem.put("sensor_type", Integer.toString(sensor.getType()));
        sensorItem.put("sensor_axes", VectorUtils.stringFromVector(representativeAxes));
        return sensorItem;
    }

    private void initializeLayoutVariables() {
        sensorSelectorSpinner = (Spinner) getActivity().findViewById(R.id.sensor_selector_spinner);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            Adapter adapter = parent.getAdapter();
            MySensorManager mySensorManager = MySensorManager.getInstance();

            Map<String, String> currentSensorDisplayed = (Map<String, String>) adapter.getItem(lastSelectedItemPosition);
            mySensorManager.unregisterSensor(Integer.parseInt(currentSensorDisplayed.get("sensor_type")));

            Map<String, String> selectedSensor = (Map<String, String>) adapter.getItem(position);
            int selectedSensorType = Integer.parseInt(selectedSensor.get("sensor_type"));
            int[] sensorAxes = VectorUtils.vectorFromString(selectedSensor.get("sensor_axes"));

            mySensorManager.registerSensor(selectedSensorType, sensorAxes, SensorManager.SENSOR_DELAY_NORMAL);
            sensorDisplayCallback.resetDisplayedData(null, sensorAxes);
        } catch (NullAndroidSensorManagerException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());
        }

        lastSelectedItemPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onDetach() {
        sensorDisplayCallback = null;
        super.onDetach();
    }
}
