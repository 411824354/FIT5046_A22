package A2.myapplication;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportFragment extends Fragment {
    View vReport;

    public static TextView dateText;
    private Button bTnDate;
    private Button bTnPieChart;
    private Button bTnBarChart;
    private Button bTnStart;
    private Button bTnEnd;
    private TextView showTime;
    private TextView showStart;
    private TextView showEnd;
    Integer userId;
    private float[] yData;
    private String[] xDate = {"Calories Consumed", "Calories Burned", "Remaining Calories"};
    PieChart pieChart;
    BarChart barChart;
    SharedPreferences sharedPreferences;
    JSONObject userobj;
    ArrayList<BarEntry> barBurned;
    ArrayList<BarEntry> barConsumed;
    List<String> date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vReport = inflater.inflate(R.layout.fragment_report_unit, container, false);

        sharedPreferences = getActivity().getSharedPreferences( "signUpUser", Context.MODE_PRIVATE);
        String userInfo = sharedPreferences.getString("user","");

        try {
            JSONArray users = new JSONArray(userInfo);
            userobj = users.getJSONObject( users.length()-1 );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showTime = vReport.findViewById(R.id.showTime);
        bTnDate = vReport.findViewById(R.id.showTimeButton);
        bTnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        showTime.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                DatePicker datePicker = dpd.getDatePicker();
                datePicker.setMaxDate(new Date().getTime());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date minDate = dateFormat.parse("1990-00-00");
                    datePicker.setMinDate(minDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dpd.show();

            }
        });
        pieChart = vReport.findViewById(R.id.PieChart);
        pieChart.setRotationEnabled(true);
        pieChart.getDescription().setText("Day Report");
        pieChart.setHoleRadius(25f);
        pieChart.setCenterTextSize(10);
        pieChart.setCenterText("Day Report");
        pieChart.setEntryLabelColor( Color.BLACK);

        bTnPieChart = vReport.findViewById(R.id.createPieChart);
        bTnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    userId = Integer.parseInt(userobj.getString("id")) ;

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                new CreatePieChartAsyncTask().execute(userId);
            }
        });
        //barChart
        showStart = vReport.findViewById(R.id.startTime);
        bTnStart = vReport.findViewById(R.id.startTimeButton);
        bTnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    userId = Integer.parseInt( userobj.getString( "id" ) );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                int day = c.get( Calendar.DAY_OF_MONTH );
                int month = c.get( Calendar.MONTH );
                int year = c.get( Calendar.YEAR );
                DatePickerDialog dpd = new DatePickerDialog( getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        showStart.setText( year + "-" + (month + 1) + "-" + dayOfMonth );
                    }
                }, year, month, day );
                DatePicker datePicker = dpd.getDatePicker();
                datePicker.setMaxDate( new Date().getTime() );
                SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
                try {
                    Date minDate = dateFormat.parse( "1990-00-00" );
                    datePicker.setMinDate( minDate.getTime() );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dpd.show();
            }
            });
            showEnd = vReport.findViewById(R.id.endTime);
            bTnEnd = vReport.findViewById(R.id.endTimeButton);
        bTnEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        userId = Integer.parseInt(userobj.getString("id"));
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date minDate = null;
                    try {
                        minDate = dateFormat.parse("2018-00-00");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH);
                    int year = c.get(Calendar.YEAR);
                    DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            showEnd.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        }
                    }, year, month, day);
                    DatePicker datePicker = dpd.getDatePicker();
                    datePicker.setMaxDate(new Date().getTime());
                    try {
                        if(!bTnStart.getText().toString().isEmpty()) {
                            String[] newdate = showStart.getText().toString().split("-");
                            String startDate = newdate[2] + "-" + newdate[1] + "-" + newdate[0];
                            minDate = dateFormat.parse(startDate);
                        }
                        datePicker.setMinDate(minDate.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dpd.show();
                }
            });
            barChart = vReport.findViewById(R.id.BarChart);
        barChart.setDrawBarShadow(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
            bTnBarChart= vReport.findViewById(R.id.createBarChart);
        bTnBarChart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dateStart = "";
                    String dateEnd = "";


                    try {
                        userId = Integer.parseInt(userobj.getString("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dateStart = showStart.getText().toString();

                        dateEnd = showEnd.getText().toString();

                    String url = "qq.report/sumerizing/"+ userId +"/" + dateStart+"/" + dateEnd;



                    new CreateBarChartAsyncTask().execute(url);
                }
            });


        return vReport;
    }
    public class CreateBarChartAsyncTask extends AsyncTask<Object,String,String> {
        @Override
        protected String doInBackground(Object... objects) {

            //Log.w("Report","I'm here!");
            String url = (String) objects[0];
            String data = CallingRestFul.findReport(url);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {

            String[] values = s.replace( "{", "" ).replace( "}", "" ).split( "," );
            barBurned = new ArrayList<>();
            barConsumed= new ArrayList<>();
            date = new ArrayList<>();
            if (!s.equals("")){
                JSONArray data = null;



                        barBurned.add(new BarEntry(0,Float.parseFloat(values[0])));
                        barConsumed.add(new BarEntry(1,Float.parseFloat(values[1])));
                        Date dat = new Date();
                        date.add(dat.toString());



            }

            generateBarChart();

        }
    }

    public BarChart generateBarChart(){

        barChart = getActivity().findViewById(R.id.BarChart);
        BarDataSet barDataSet1 = new BarDataSet(barBurned,"Calories Burned");
        barDataSet1.setColor(Color.YELLOW);
        BarDataSet barDataSet2 = new BarDataSet(barConsumed,"Calories Consumed");
        barDataSet2.setColor(Color.BLUE);
        BarData barData = new BarData(barDataSet1,barDataSet2);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(true);
        barChart.setScaleEnabled(false);
        barChart.getData().setHighlightEnabled(false);
        barChart.animateY(1000);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        barChart.setVisibleXRangeMaximum(date.size());
        //(barWidth + barSpace) * num of bar + groupSpace = 1
        float barSpace = 0.0f;
        float groupSpace = 0.2f;
        barData.setBarWidth(0.4f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*date.size());
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();

        return null;

    }

    private class CreatePieChartAsyncTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... integers) {
            String text = "";
            if (!showTime.getText().toString().isEmpty()) {
                String[] dateFormat = showTime.getText().toString().split( "/" );
                String date = dateFormat[2] + "-" +  dateFormat[1] + "-" + dateFormat[0];
                String result = CallingRestFul.findIdDateReport( integers[0], date );

                String[] values = result.replace( "{", "" ).replace( "}", "" ).split( "," );

                yData = new float[3];
                yData[0] = (float) Float.parseFloat( values[0] );
                yData[1] = (float) Float.parseFloat( values[1] );
                yData[2] = (float) Float.parseFloat( values[2] );
                List<PieEntry> pieEntries = new ArrayList<>();
                for (int i = 0; i < yData.length; i++) {
                    pieEntries.add( new PieEntry( yData[i], xDate[i] ) );
                }
                //create the data set
                PieDataSet pieDataSet = new PieDataSet( pieEntries, "Daily Report" );
                PieData pieData = new PieData( pieDataSet );
                pieDataSet.setColors( ColorTemplate.COLORFUL_COLORS );
                //Create pie data object
                pieChart.setData( pieData );
                pieChart.invalidate();

            }
            return text;
        }

        @Override
        protected void onPostExecute(String text) {
//            if (text == "please put the date") {
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
//            }
        }

    }


}
