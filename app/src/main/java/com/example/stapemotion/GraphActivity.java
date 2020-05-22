package com.example.stapemotion;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stapemotion.model.DayEmotion;
import com.example.stapemotion.model.Emotion;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private static final int HAPPY_CHECK = 0;

    private static final int UNHAPPY_CHECK = 1;

    private static final int NORMAL = 2;

    String date;

    String dateEnd;

    int numNormal;
    int numHappy;
    int numUnHappy;

    int key;

    Calendar calen = Calendar.getInstance();
    Calendar calenEnd = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    FirebaseFirestore db;

    ArrayList<Emotion> allList;

    ArrayList<Emotion> listEmotion;

    ArrayList<Integer> listHappy;
    ArrayList<Integer> listUnHappy;
    ArrayList<Integer> listNormal;

    LinearLayout graph_contrainer1, graph_contrainer2;

    TextView txtEndDate;

    TextView graphTv_NumNormal, graphTv_NumHappy, graphTv_NumUnHappy, graph_timepicker, graph_timepickerEnd ,graph_title_chooseDate;
    Spinner graph_spinner;
    ArrayAdapter<String> arrayAdapter;
    Button graph_showGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        addContent();
        addEvent();
    }

    private void addEvent() {

        graph_timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTime();
            }
        });

        graph_timepickerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTimeEnd();
            }
        });

        graph_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                key = i;

                if(i == 2) {
                    graph_timepickerEnd.setVisibility(View.VISIBLE);
                    graph_contrainer1.setVisibility(View.VISIBLE);
                    graph_contrainer2.setVisibility(View.VISIBLE);
                } else {
                    graph_timepickerEnd.setVisibility(View.GONE);
                    graph_contrainer1.setVisibility(View.GONE);
                    graph_contrainer2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        graph_showGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(key == 2) {

                    String startDateTemp = graph_timepicker.getText().toString();
                    String endDateTemp = graph_timepickerEnd.getText().toString();

                    Date dateStar, dateEnd, currentDate;

                    try {
                        dateStar = dateFormat.parse(startDateTemp);
                        dateEnd = dateFormat.parse(endDateTemp);

                        String currentDateString = dateFormat.format(Calendar.getInstance().getTime());
                        currentDate = dateFormat.parse(currentDateString);

                        if(dateStar.compareTo(dateEnd) > 0) {
                            Toast.makeText(GraphActivity.this, "Vui lòng chọn ngày bắt đầu trước ngày " +graph_timepickerEnd.getText().toString(), Toast.LENGTH_SHORT).show();
                        } else if (dateStar.compareTo(dateEnd) == 0) {
                            Toast.makeText(GraphActivity.this, "Xin vui lòng chọn hai ngày khác nhau", Toast.LENGTH_SHORT).show();
                        } else if(dateStar.compareTo(currentDate) >= 0 || dateEnd.compareTo(currentDate) >= 0) {
                            Toast.makeText(GraphActivity.this,"Dữ liệu chưa có, vui lòng chọn ngày kết thúc trước ngày "+currentDateString, Toast.LENGTH_SHORT ).show();
                        } else {
                            ArrayList<DayEmotion> list = new ArrayList<>();
                            list = getListEmotion(getDateBetween(startDateTemp, endDateTemp));
                            Intent intentLine = new Intent(GraphActivity.this, ShowGraphActivity.class);
                            intentLine.putExtra("list", list);
                            intentLine.putExtra("type", key);
                            Log.d("TAG", String.valueOf(key));
                            startActivity(intentLine);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {
                    Intent intent = new Intent(GraphActivity.this, ShowGraphActivity.class);

                    int total = numNormal + numHappy + numUnHappy;

                    float fragNormal = (float) numNormal / total * 100;
                    float fragHappy = (float) numHappy / total * 100;
                    float fragUnhappy = (float) numUnHappy / total * 100;

                    String chooseDate =graph_timepicker.getText().toString();

                    Date currentDate_DateFormat, dateStar_DateFormat;
                    String currentDateString = dateFormat.format(Calendar.getInstance().getTime());
                    try {
                        currentDate_DateFormat = dateFormat.parse(currentDateString);
                        dateStar_DateFormat = dateFormat.parse(chooseDate);

                        if(dateStar_DateFormat.compareTo(currentDate_DateFormat) >= 0) {
                            Toast.makeText(GraphActivity.this,"Dữ liệu chưa có, vui lòng chọn ngày trước "+currentDateString, Toast.LENGTH_SHORT ).show();
                        } else {
                            intent.putExtra("normal", fragNormal);
                            intent.putExtra("happy", fragHappy);
                            intent.putExtra("unhappy", fragUnhappy);
                            intent.putExtra("date", chooseDate);
                            intent.putExtra("type", key);
                            startActivity(intent);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void addContent() {

        graphTv_NumNormal = findViewById(R.id.graphTv_NumNormal);
        graphTv_NumHappy = findViewById(R.id.graphTv_NumHappy);
        graphTv_NumUnHappy = findViewById(R.id.graphTv_NumUnHappy);
        graph_spinner = findViewById(R.id.graph_spinner);
        graph_showGraph = findViewById(R.id.graph_showGraph);
        graph_timepicker = findViewById(R.id.graph_timepicker);
        graph_timepickerEnd = findViewById(R.id.graph_timepickerEnd);
        graph_title_chooseDate = findViewById(R.id.graph_title_chooseDate);

        graph_contrainer1 = findViewById(R.id.graph_contrainer1);
        graph_contrainer2 = findViewById(R.id.grap_container2);

        txtEndDate = findViewById(R.id.txtEndDate);

        String currentday = dateFormat.format(Calendar.getInstance().getTime());
        graph_timepicker.setText(currentday);
        graph_timepickerEnd.setText(currentday);
        txtEndDate.setText(currentday);

        allList = new ArrayList<>();
        listEmotion = new ArrayList<>();
        listHappy = new ArrayList<>();
        listUnHappy = new ArrayList<>();
        listNormal = new ArrayList<>();

        List<String> listtype = new ArrayList<>();
        listtype.add("Biểu đồ hình tròn");
        listtype.add("Biểu đồ hình cột");
        listtype.add("Biểu đồ đường");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner, listtype);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        graph_spinner.setAdapter(arrayAdapter);

        db = FirebaseFirestore.getInstance();

        CollectionReference reference = db.collection("Emotion");
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {

                    date = graph_timepicker.getText().toString();

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot item : list) {
                        Emotion emotion = item.toObject(Emotion.class);
                        allList.add(emotion);
                        if(emotion.getDate_created().equals(date)) {
                            listEmotion.add(emotion);
                        }
                    }

                    for (Emotion item : listEmotion) {
                        if(item.getDate_created().equals(date)) {
                            if (item.getEmotion() == HAPPY_CHECK) {
                                listHappy.add(item.getEmotion());
                            } else if (item.getEmotion() == UNHAPPY_CHECK) {
                                listUnHappy.add(item.getEmotion());
                            } else {
                                listNormal.add(item.getEmotion());
                            }
                        }
                    }
                    initText();
                }
            }
        });
    }

    private void getEmotion(final String dateGet) {


        listHappy.clear();
        listNormal.clear();
        listUnHappy.clear();

        for (Emotion item : allList) {
            if(item.getDate_created().equals(dateGet)) {
                if (item.getEmotion() == HAPPY_CHECK) {
                    listHappy.add(item.getEmotion());
                } else if (item.getEmotion() == UNHAPPY_CHECK) {
                    listUnHappy.add(item.getEmotion());
                } else {
                    listNormal.add(item.getEmotion());
                }
            }
        }
        initText();
    }

    private DayEmotion getThongKeEmotion( String dateGet) {

        listHappy.clear();
        listNormal.clear();
        listUnHappy.clear();

        for (Emotion item : allList) {
            if(item.getDate_created().equals(dateGet)) {
                if (item.getEmotion() == HAPPY_CHECK) {
                    listHappy.add(item.getEmotion());
                } else if (item.getEmotion() == UNHAPPY_CHECK) {
                    listUnHappy.add(item.getEmotion());
                } else {
                    listNormal.add(item.getEmotion());
                }
            }
        }

        int total = listHappy.size() + listUnHappy.size() + listNormal.size();

        float fragNormal = (float) listNormal.size() / total * 100;
        float fragHappy = (float) listHappy.size() / total * 100;
        float fragUnhappy = (float) listUnHappy.size() / total * 100;

        return new DayEmotion(dateGet, fragHappy, fragUnhappy , fragNormal);
    }

    private ArrayList<DayEmotion> getListEmotion (ArrayList<String> listDate) {

        ArrayList<DayEmotion> list = new ArrayList<>();

        for (String item : listDate) {
            list.add(getThongKeEmotion(item));
        }

        return list;
    }

    private void getTime() {

        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calen.set(Calendar.YEAR, i);
                calen.set(Calendar.MONTH, i1);
                calen.set(Calendar.DAY_OF_MONTH, i2);

                graph_timepicker.setText(dateFormat.format(calen.getTime()));
                date = dateFormat.format(calen.getTime());

                txtEndDate.setText(dateFormat.format(calen.getTime()));

                getEmotion(date);
            }
        };

        DatePickerDialog dateDialog = new DatePickerDialog(GraphActivity.this,
                callback,
                calen.get(Calendar.YEAR),
                calen.get(Calendar.MONTH),
                calen.get(Calendar.DAY_OF_MONTH));

        dateDialog.show();
    }

    private void getTimeEnd() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calenEnd.set(Calendar.YEAR, i);
                calenEnd.set(Calendar.MONTH, i1);
                calenEnd.set(Calendar.DAY_OF_MONTH, i2);

                graph_timepickerEnd.setText(dateFormat.format(calenEnd.getTime()));
                dateEnd = dateFormat.format(calenEnd.getTime());
            }
        };

        DatePickerDialog dateDialog = new DatePickerDialog(GraphActivity.this,
                callback,
                calenEnd.get(Calendar.YEAR),
                calenEnd.get(Calendar.MONTH),
                calenEnd.get(Calendar.DAY_OF_MONTH));

        dateDialog.show();
    }

    private ArrayList<String> getDateBetween(String startDate, String endDate) {
        ArrayList<String> dateList = new ArrayList<>();
        Date star, end;
        try {
            star = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
            end = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(star);

            while (calendar.getTime().before(end)) {
                Date result = calendar.getTime();
                dateList.add(dateFormat.format(result));
                calendar.add(Calendar.DATE, 1);
            }
            dateList.add(dateFormat.format(end));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateList;
    }

    private void initText() {
        numNormal = listNormal.size();
        numHappy = listHappy.size();
        numUnHappy = listUnHappy.size();

        int total = numNormal + numHappy + numUnHappy;

        float fragNormal = (float) numNormal / total * 100;
        float fragHappy = (float) numHappy / total * 100;
        float fragUnhappy = (float) numUnHappy / total * 100;

        graphTv_NumNormal.setText(String.valueOf(numNormal)+" ("+String.format("%.2f", fragNormal)+"%)");
        graphTv_NumHappy.setText(String.valueOf(numHappy)+" ("+String.format("%.2f",fragHappy)+"%)");
        graphTv_NumUnHappy.setText(String.valueOf(numUnHappy)+" ("+String.format("%.2f", fragUnhappy)+"%)");

    }
}
