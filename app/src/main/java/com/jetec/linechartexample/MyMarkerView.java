
package com.jetec.linechartexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;


@SuppressLint("ViewConstructor")
public class MyMarkerView extends MarkerView {

    private TextView tvValue, tvTitle;
    private ArrayList<String> customxLable;
    private LineChart chart;

    public MyMarkerView(Context context, int layoutResource, ArrayList<String> customxLable,LineChart chart) {
        super(context, layoutResource);
        this.customxLable = customxLable;
        this.chart = chart;
        tvTitle = findViewById(R.id.textView_Title);
        tvValue = findViewById(R.id.textView_Value);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String label = customxLable.get(Math.round(e.getX())).replace("資料\n","");
        tvTitle.setText(label);
        int line =  chart.getLineData().getDataSets().size();

        StringBuffer value = new StringBuffer();
        for (int i = 0; i <line ; i++) {
            ILineDataSet set = chart.getLineData().getDataSets().get(i);
            String s = set.getLabel()+": "+set.getEntryForIndex(Math.round(e.getX())).getY();
            if (i<line-1) value.append(s+"\n");
            else value.append(s);
        }
        tvValue.setText(value);

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        if (posX < 300) return new MPPointF(-getWidth() / 2f+100f, -getHeight() - 10f);
        else return new MPPointF(-getWidth() / 2f-100f, -getHeight()-10f);
    }
}
