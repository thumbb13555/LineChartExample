package com.jetec.linechartexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName()+"My";
    LineChart chart;
    ArrayList<String> customxLable = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = (LineChart)findViewById(R.id.linechart);
        setChart();
        Button btReset = findViewById(R.id.button_reSet);
        btReset.setOnClickListener((v)->{
            chart.clear();
            setChart();
        });

    }

    private void setChart() {

        ArrayList<HashMap<String,String>> mData = makeFakeData();
        for (int i=0;i<mData.size();i++){//自定義X軸標籤(一般為時間)
            customxLable.add("第"+(i+1)+"筆");
        }

        /**設定圖表框架↓*/
        YAxis leftAxis = chart.getAxisLeft();//設置Y軸(左)
        YAxis rightAxis = chart.getAxisRight();//設置Y軸(右)
        rightAxis.setEnabled(false);//讓右邊Y消失
        XAxis xAxis = chart.getXAxis();//設定X軸
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//將x軸表示字移到底下
        xAxis.setLabelCount(3,false);//設定X軸上要有幾個標籤
        chart.getDescription().setEnabled(false);//讓右下角文字消失
//        xAxis.setEnabled(false);//去掉X軸數值
        xAxis.setDrawGridLines(false);//將X軸格子消失掉
        xAxis.setValueFormatter(new MyValueFormatter());//設置X軸
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);//設置點擊標籤
        chart.setMarker(mv);//設置點擊標籤
        /**設定圖表框架↑*/

        /**載入資料↓*/

        ArrayList<Entry> yValues1 = new ArrayList<>();
        ArrayList<Entry> yValues2 = new ArrayList<>();

        for (int i=0;i<mData.size();i++){
            float getFirst = Float.parseFloat(mData.get(i).get("FirstData"));
            float getSecond = Float.parseFloat(mData.get(i).get("SecondData"));
            yValues1.add(new Entry(i,getFirst));
            yValues2.add(new Entry(i,getSecond));

        }
        LineDataSet set1 = new LineDataSet(yValues1, "溫度");
        LineDataSet set2 = new LineDataSet(yValues2, "濕度");
        setChartImage(set1,1);//設置圖表線1
        setChartImage(set2,2);//設置圖表線2

        chart.animateX(2000);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        leftAxis.setAxisMaximum(100f);//設置上限
        leftAxis.setAxisMinimum(0f);//設置下限
        //其實上下限可以不用刻意設置，圖表會自動依數值調整到最適合的範圍
        dataSets.add(set1);
        dataSets.add(set2);
        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);
        /**載入資料↑*/}

    private void setChartImage(LineDataSet set,int i){//設置圖表線
        if (i == 1){
            set.setColor(Color.parseColor("#F85353"));//紅色
            set.setCircleColor(Color.parseColor("#F85353"));//調整小圈圈的顏色
        }else if (i==2){
            set.setColor(Color.parseColor("#1E88A8"));//藍色
            set.setCircleColor(Color.parseColor("#1E88A8"));//調整小圈圈的顏色
        }
        set.setCircleRadius(0.5f);
        set.setLineWidth(1.5f);//條粗細
        set.setValueTextSize(9f);

        LineData dataText = new LineData(set);
        dataText.setDrawValues(false);//不要再點上顯示值
    }

    private class MyValueFormatter extends ValueFormatter{//設置Ｘ軸
        @Override
        public String getFormattedValue(float value) {
            return customxLable.get((int) value);
        }
    }

    private ArrayList<HashMap<String,String>> makeFakeData(){//製造假資料
        ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
        for (int i=0;i<100;i++){
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("FirstData", "25.6");
            hashMap.put("SecondData", "50.2");
            arrayList.add(hashMap);

        }
        for (int i=0;i<100;i++){
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("FirstData", "26.3");
            hashMap.put("SecondData", "50.9");
            arrayList.add(hashMap);

        }
        for (int i=0;i<100;i++){
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("FirstData", "28.1");
            hashMap.put("SecondData", "62.2");
            arrayList.add(hashMap);

        }
        for (int i=0;i<100;i++){
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("FirstData", "30.4");
            hashMap.put("SecondData", "60.0");
            arrayList.add(hashMap);

        }
        for (int i=0;i<100;i++){
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("FirstData", "30.0");
            hashMap.put("SecondData", "58.3");
            arrayList.add(hashMap);

        }
        return arrayList;
    }
}
