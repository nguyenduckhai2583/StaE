package com.example.stapemotion;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stapemotion.adapter.LocationAdapter;
import com.example.stapemotion.model.Location;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Spinner spinnerProvince;
    ArrayList<String> listProvince;

    RecyclerView homeRecyler_listLocation;
    LocationAdapter adapter;
    ArrayList<Location> list;
    ArrayList<Integer> listImg;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        addContent();
        addEvent();
    }

    private void addEvent() {
    }

    private void addContent() {
        spinnerProvince = findViewById(R.id.homeSpinner_spinnerLocation);

        list = new ArrayList<>();
        listImg = new ArrayList<>();

        initImg();

        initSpinner();

        homeRecyler_listLocation = findViewById(R.id.homeRecyler_listLocation);
        adapter = new LocationAdapter(list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        homeRecyler_listLocation.setLayoutManager(linearLayoutManager);
        homeRecyler_listLocation.setHasFixedSize(true);
        homeRecyler_listLocation.setAdapter(adapter);
    }

    private void initImg() {
        listImg.add(R.drawable.img_tdt);
        listImg.add(R.drawable.img_dbp);
        listImg.add(R.drawable.img_hd);
        listImg.add(R.drawable.img_oik);
        listImg.add(R.drawable.img_ld);
    }

    private void initSpinner() {
        listProvince = new ArrayList<>();
        listProvince.add("Đà Nẵng");
        listProvince.add("Hà Nội");
        listProvince.add("TP Hồ Chí Minh");
        arrayAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner, listProvince);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(arrayAdapter);

        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        initLocation();
                        break;
                    case 1:
                        initLocationHaNoi();
                        break;
                    case 2:
                        initLocationSG();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initLocationHaNoi() {
        list.clear();
        list.add(new Location("Vinmart Phố Huế", "25 Phố Huế, Quận Hoàn Kiếm, TP Hà Nội", listImg.get(0)));
        list.add(new Location("Vinmart Trần Phú", "16 Trần Phú, Quận Ba Đình, TP Hà Nội", listImg.get(1)));
        list.add(new Location("Vinmart Lê Lơi", "448 Lê Lợi, Quận Ba ĐÌnh, TP Hà Nội", listImg.get(2)));
        adapter.notifyDataSetChanged();
    }

    private void initLocationSG () {
        list.clear();
        list.add(new Location("Vinmart Ngô Gia Tự", "466 Ngô Gia Tự, Quận 10, TP Hồ Chí Minh", listImg.get(2)));
        list.add(new Location("Vinmart Lý Thái Tổ", "45 Lý Thái Tổ, Quận 10, TP Đà Nẵng", listImg.get(3)));
        list.add(new Location("Vinmart Trường Sơn", "343 Trường Sơn, Quận 3, TP Đà Nẵng", listImg.get(4)));
        adapter.notifyDataSetChanged();
    }

    private void initLocation() {
        list.clear();
        list.add(new Location("Vinmart Ông Ích Khiêm", "25 Ông Ích Khiêm, Thanh Khê, TP Đà Nẵng", listImg.get(4)));
        list.add(new Location("Vinmart Hoàng Diệu", "237 Hoàng Diệu, Hải Châu, TP Đà Nẵng", listImg.get(3)));
        list.add(new Location("Vinmart Nguyễn Lương Bằng", "486 Nguyễn Lương Bằng, Liên Chiểu, TP Đà Nẵng", listImg.get(2)));
        list.add(new Location("Vinmart Hải Phòng", "322 Hải Phòng, Thanh Khê, TP Đà Nẵng", listImg.get(1)));
        adapter.notifyDataSetChanged();
    }
}
