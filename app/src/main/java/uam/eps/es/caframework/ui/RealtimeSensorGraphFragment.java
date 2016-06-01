package uam.eps.es.caframework.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.eventbus.Subscribe;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import uam.eps.es.caframework.R;
import uam.eps.es.caframework.model.measurements.Measurement;
import uam.eps.es.caframework.model.measurements.MeasurementSmoothedEvent;

/**
 * Created by Ari on 31/05/2016.
 */
public class RealtimeSensorGraphFragment extends Fragment {

    private int currentXValue = 0;
    private LineGraphSeries<DataPoint> mSeriesSmoothedDataX;
    private LineGraphSeries<DataPoint> mSeriesSmoothedDataY;
    private LineGraphSeries<DataPoint> mSeriesSmoothedDataZ;
    private GraphView graph;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph, container, false);

        graph = (GraphView) rootView.findViewById(R.id.graph);
        recreateSeries(null, null);

        return rootView;
    }

    @Subscribe
    public void listenToSmoothedMeasurements(MeasurementSmoothedEvent measurementEvent) {

        Measurement measurement = measurementEvent.getmMeasurament();
        int[] representativeAxes = measurement.getRepresentativeAxes();
        // discard old metrics
        if (representativeAxes.length != graph.getSeries().size())
            return;
        for (int i = 0; i < representativeAxes.length; i++) {
            float value = 0.0f;
            DataPoint dataPoint = new DataPoint(0, 0);
            switch (i) {
                case 0:
                    value = measurement.getValue(i);
                    dataPoint = new DataPoint(currentXValue, value);
                    mSeriesSmoothedDataX.appendData(dataPoint, true, 40);
                    break;
                case 1:
                    value = measurement.getValue(i);
                    dataPoint = new DataPoint(currentXValue, value);
                    mSeriesSmoothedDataY.appendData(dataPoint, true, 40);
                    break;
                case 2:
                    value = measurement.getValue(i);
                    dataPoint = new DataPoint(currentXValue, value);
                    mSeriesSmoothedDataZ.appendData(dataPoint, true, 40);
                    break;
                default:
                    break;
            }
        }
        currentXValue++;
    }

    public void resetGraphData(Float sensorRange, int[] sensorAxes) {
        currentXValue = 0;
        recreateSeries(sensorRange, sensorAxes);
    }

    private void recreateSeries(Float sensorRange, int[] sensorAxes) {
        graph.getViewport().setXAxisBoundsManual(true);
        if(sensorRange != null) {
            graph.getViewport().setMinY(-sensorRange);
            graph.getViewport().setMaxY(sensorRange);
            graph.getViewport().setYAxisBoundsManual(true);
        }
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.removeAllSeries();
        // Just for initialization
        if (sensorAxes == null)
            return;

        for (int i = 0; i < sensorAxes.length; i++) {
            switch (i) {
                case 0:
                    mSeriesSmoothedDataX = new LineGraphSeries<>();
                    mSeriesSmoothedDataX.setColor(Color.GREEN);
                    graph.addSeries(mSeriesSmoothedDataX);
                    break;
                case 1:
                    mSeriesSmoothedDataY = new LineGraphSeries<>();
                    mSeriesSmoothedDataY.setColor(Color.BLUE);
                    graph.addSeries(mSeriesSmoothedDataY);
                    break;
                case 2:
                    mSeriesSmoothedDataZ = new LineGraphSeries<>();
                    mSeriesSmoothedDataZ.setColor(Color.RED);
                    graph.addSeries(mSeriesSmoothedDataZ);
                    break;
                default:
                    break;
            }
        }
    }
}
