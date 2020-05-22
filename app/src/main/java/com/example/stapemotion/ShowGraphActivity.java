package com.example.stapemotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.stapemotion.model.DayEmotion;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ShowGraphActivity extends AppCompatActivity {

    PieChart pieChart;

    BarChart barChart;

    LineChart lineChart;

    LineDataSet lineDataSetHappy, lineDataSetUnHappy, lineDataSetNormal;

    ArrayList<DayEmotion> list;

    TextView showGraph_time;

    float numNormal, numHappy, numUnhappy;

    int key;

    String date;

    CheckBox chkb_happy, chkb_unHappy, chkb_normal;

    RelativeLayout container_chooseline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_graph_actiity);

        addContent();
        addEvent();

    }

    private void addEvent() {
        Intent intent = getIntent();
        numNormal = intent.getFloatExtra("normal", 0);
        numHappy = intent.getFloatExtra("happy", 0);
        numUnhappy = intent.getFloatExtra("unhappy", 0);
        key = intent.getIntExtra("type", -1);
        date = intent.getStringExtra("date");
        list = intent.getParcelableArrayListExtra("list");

        switch (key) {
            case 0:
                PaintPieChar();
                barChart.setVisibility(View.GONE);
                lineChart.setVisibility(View.GONE);
                container_chooseline.setVisibility(View.GONE);
                showGraph_time.setText("BIỂU ĐỒ NGÀY " + date);
                break;
            case 1:
                PaintBarChar();
                pieChart.setVisibility(View.GONE);
                lineChart.setVisibility(View.GONE);
                container_chooseline.setVisibility(View.GONE);
                showGraph_time.setText("BIỂU ĐỒ NGÀY " + date);
                break;
            case 2:
                PaintLineChar(list);
                container_chooseline.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
                barChart.setVisibility(View.GONE);
                break;
        }

        chkb_happy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    lineDataSetHappy.setVisible(true);
                    lineChart.invalidate();
                } else {
                    lineDataSetHappy.setVisible(false);
                    lineChart.invalidate();
                }
            }
        });

        chkb_unHappy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    lineDataSetUnHappy.setVisible(true);
                    lineChart.invalidate();
                } else {
                    lineDataSetUnHappy.setVisible(false);
                    lineChart.invalidate();
                }
            }
        });

        chkb_normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    lineDataSetNormal.setVisible(true);
                    lineChart.invalidate();
                } else {
                    lineDataSetNormal.setVisible(false);
                    lineChart.invalidate();
                }
            }
        });

    }

    private void PaintLineChar(ArrayList<DayEmotion> listPaint) {

        ArrayList<Entry> lineHappy = new ArrayList<>();
        for (int i = 0; i < listPaint.size(); i++) {
            lineHappy.add(new Entry(i, listPaint.get(i).getHappy()));
            Log.d("TAG", i + " space " + listPaint.get(i).getHappy());
        }

        ArrayList<Entry> lineUnHappy = new ArrayList<>();
        for (int i = 0; i < listPaint.size(); i++) {
            lineUnHappy.add(new Entry(i, listPaint.get(i).getUnhappy()));
        }

        ArrayList<Entry> lineNormal = new ArrayList<>();
        for (int i = 0; i < listPaint.size(); i++) {
            lineNormal.add(new Entry(i, listPaint.get(i).getNormal()));
        }

        lineDataSetHappy = new LineDataSet(lineHappy, "Vui vẻ");
        lineDataSetHappy.setColor(Color.RED);

        lineDataSetUnHappy = new LineDataSet(lineUnHappy, "Không vui");
        lineDataSetUnHappy.setColor(Color.BLUE);

        lineDataSetNormal = new LineDataSet(lineNormal, "Bình thường");
        lineDataSetNormal.setColor(Color.GREEN);

        LineData lineData = new LineData(lineDataSetHappy, lineDataSetUnHappy, lineDataSetNormal);


        lineChart.getDescription().setEnabled(false);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.animateX(1500);
        lineChart.setExtraOffsets(20, 20, 20, 20);

        final ArrayList<String> type = new ArrayList<>();
        for(DayEmotion item : listPaint) {
            type.add(item.getDate().substring(0, 5));
            Log.d("TAG",item.getDate().substring(0, 5));
        }

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(type));

        lineChart.setData(lineData);
    }

    private void addContent() {
        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChar);
        lineChart = findViewById(R.id.lineChart);
        showGraph_time = findViewById(R.id.showGraph_time);
        list = new ArrayList<>();

        chkb_happy = findViewById(R.id.chkb_happy);
        chkb_unHappy = findViewById(R.id.chkb_unHappy);
        chkb_normal = findViewById(R.id.chkb_normal);
        container_chooseline = findViewById(R.id.container_chooseline);
    }

    private void PaintPieChar() {
        pieChart.setRotationEnabled(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleRadius(35f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterTextSize(20);

        pieChart.setEntryLabelColor(Color.WHITE);

        pieChart.animateY(1400);

        List<PieEntry> pieEntries = new ArrayList<>();
        Log.d("TAG", String.valueOf(numHappy));
        pieEntries.add(new PieEntry(numHappy, "Vui vẻ"));
        pieEntries.add(new PieEntry(numUnhappy, "Không vui"));
        pieEntries.add(new PieEntry(numNormal, "Bình thường"));


        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Cảm xúc");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(18);
        pieDataSet.setValueTextColor(Color.WHITE);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.parseColor("#0F2043"));
        colors.add(Color.parseColor("#79CEDC"));
        colors.add(Color.parseColor("#D5A458"));
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void PaintBarChar() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, numHappy));
        barEntries.add(new BarEntry(2, numUnhappy));
        barEntries.add(new BarEntry(3, numNormal));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Cảm xúc");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16);


        final ArrayList<String> type = new ArrayList<>();
        type.add("");
        type.add("Vui vẻ");
        type.add("Không vui");
        type.add("Bình thường");

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(type));

        BarData barData = new BarData(barDataSet);
        barChart.getAxisLeft().setStartAtZero(true);
        barChart.getAxisRight().setStartAtZero(true);
        barChart.getDescription().setEnabled(false);
        barChart.setData(barData);
    }
}
