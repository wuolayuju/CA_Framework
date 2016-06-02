package uam.eps.es.caframework.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import uam.eps.es.caframework.R;
import uam.eps.es.caframework.model.measurements.MeasurementSmoothedEvent;

/**
 * Created by Ari on 01/06/2016.
 */
public class SmoothingControlsFragment extends Fragment {

    private SeekBar smoothFactorSeekbar;
    private TextView currentSmoothFactorTextView;
    private CheckBox setYAxisToRangeCheckbox;

    private OnClutchAxesChanged clutchAxesCallback;

    public interface OnClutchAxesChanged {
        void toggleClutchDataToRange(boolean clutchIt);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            clutchAxesCallback = (OnClutchAxesChanged) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnClutchAxesChanged.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_smoothing_control, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeLayoutVariables();

        smoothFactorSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float newSmoothFactor = ((float)progress / 100.0f);
                currentSmoothFactorTextView.setText(String.valueOf(newSmoothFactor));
                MeasurementSmoothedEvent.SMOOTH_FACTOR = newSmoothFactor;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setYAxisToRangeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clutchAxesCallback.toggleClutchDataToRange(isChecked);
            }
        });
    }

    private void initializeLayoutVariables() {
        smoothFactorSeekbar = (SeekBar) getActivity().findViewById(R.id.smooth_factor_seekbar);
        smoothFactorSeekbar.setProgress(Math.round(MeasurementSmoothedEvent.SMOOTH_FACTOR * 100.0f));
        currentSmoothFactorTextView = (TextView) getActivity().findViewById(R.id.current_smooth_factor_textview);
        currentSmoothFactorTextView.setText(String.valueOf(MeasurementSmoothedEvent.SMOOTH_FACTOR));
        setYAxisToRangeCheckbox = (CheckBox) getActivity().findViewById(R.id.clutch_range_checkbox);
        setYAxisToRangeCheckbox.setChecked(false);
    }

    @Override
    public void onDetach() {
        clutchAxesCallback = null;
        super.onDetach();
    }
}
