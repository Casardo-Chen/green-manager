package com.example.crepe.ui.chart;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.crepe.R;
import com.example.crepe.database.DatabaseManager;
import com.example.crepe.database.Ride;


import java.util.Map;

public class CollectorInfoLayoutBuilder {
    Context context;


    public static final String AVAIL = "available";
    public static final String FULL = "full";
    private DatabaseManager dbManager;


    // we will use the following constructor more often, because we initialize
    public CollectorInfoLayoutBuilder(Context context, DatabaseManager dbManager) {
        this.context = context;
        this.dbManager = dbManager;
    }

    public ConstraintLayout buildCollectorInfoView(Ride ride, ViewGroup containerLayout) {
        ConstraintLayout collectorInfoLayout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.collector_info_for_data_fragment, containerLayout, false);
        // get the app name text field from the card and populate it with app name
        TextView appNameTextView = (TextView) collectorInfoLayout.findViewById(R.id.destinationText);
        appNameTextView.setText(ride.getDestination());

        TextView collectorDescriptionTextView = (TextView) collectorInfoLayout.findViewById(R.id.departureLocation);
        collectorDescriptionTextView.setText(ride.getDeparture());

        TextView scheduleStartTextView = (TextView) collectorInfoLayout.findViewById(R.id.startTime);
        scheduleStartTextView.setText("Time: "+ ride.getRideDate() + " "+ride.getRideTime());
        TextView organizerNameView = (TextView) collectorInfoLayout.findViewById(R.id.organizerNameInfo);
        organizerNameView.setText("Organizer: "+ dbManager.getUsername(ride.getCreatorUserId()));

        // get the app status and display it
        ImageView collectorStatusImg = (ImageView) collectorInfoLayout.findViewById(R.id.statusImageView);
        collectorStatusImg.setImageResource(R.drawable.ic_baseline_circle_24_green);
        TextView collectorStatusTxt = (TextView) collectorInfoLayout.findViewById(R.id.statusText);
        if (ride.getRideId().equals("1")){
            collectorStatusTxt.setText("3.85g");
        } else if (ride.getRideId().equals("2")) {
            collectorStatusTxt.setText("4.43g");
        }
        else if (ride.getRideId().equals("3")) {
            collectorStatusTxt.setText("0.12g");
        }
        else if (ride.getRideId().equals("4")) {
            collectorStatusTxt.setText("6.79g");
        }
        else if (ride.getRideId().equals("5")) {
            collectorStatusTxt.setText("2.43g");
        }
        else if (ride.getRideId().equals("6")) {
            collectorStatusTxt.setText("8.40g");
        }
        else if (ride.getRideId().equals("7")) {
            collectorStatusTxt.setText("1.25g");
        }
//        } else if (ride.getCollectorStatus().equals(EXPIRED)){
//            collectorStatusTxt.setText("Expired");
//            collectorStatusImg.setImageResource(R.drawable.ic_baseline_circle_12_grey);
//        } else {
//            collectorStatusTxt.setText("Not yet started");
//            collectorStatusImg.setImageResource(R.drawable.ic_baseline_circle_12_yellow);
//        }

        // get photo
//        ImageView organizerHeadshot = (ImageView) collectorInfoLayout.findViewById(R.id.userImg);
//        String photoName = (String) dbManager.getOneUserByID(ride.getCreatorUserId()).getPhoto();
//        int id = context.getResources().getIdentifier(photoName, "drawable", context.getPackageName());
//        Drawable userImage =  context.getDrawable(id);
//        if (userImage == null){
//            organizerHeadshot.setImageResource(R.drawable.nd_logo);
//        } else {
//            organizerHeadshot.setImageDrawable(userImage);
//        }

        return collectorInfoLayout;
    }

    // the parameter width is the screen width, used to position the chart properly
//    public LinearLayout buildChart(Ride ride) {
//        LineChart lineChart;
//        lineChart = new LineChart(context);
//
//        // fake some data
//        Integer dataPointNum = 10;
//        List<Entry> entries = new ArrayList<Entry>();
//
//        float max = 5;
//        float min = 0.5F;
//
//        for (Integer i = 0; i < dataPointNum; i++) {
//            // turn data into Entry objects
//            Random rand = new Random();
//
//            float randomValue = rand.nextFloat() * (max - min) + min;
//
//            entries.add(new Entry( (float) i, randomValue));
//        }
//
//        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
//        dataSet.setDrawCircles(false);
//        dataSet.setLineWidth(2f);
//        dataSet.setColor(Color.parseColor("#223651"));
//
//        // set attributes for x axis
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setLabelCount(5);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextSize(10f);
//        xAxis.setTextColor(Color.parseColor("#1C2B34"));
//        xAxis.setDrawGridLines(false);
//        xAxis.setGridLineWidth(0);
//        xAxis.setGranularity(1f); // only intervals of 1 day
//
//        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(lineChart);
//        xAxis.setValueFormatter(xAxisFormatter);
//
//        // set attributes for y axis
//        YAxis yAxisLeft = lineChart.getAxisLeft();
//        YAxis yAxisRight = lineChart.getAxisRight();
//        yAxisLeft.setGridLineWidth(1f);
//        yAxisLeft.setGridColor(Color.parseColor("#DADADA"));
//        yAxisRight.setDrawLabels(false);
//        yAxisLeft.setDrawAxisLine(false);
//        yAxisRight.setDrawAxisLine(false);
//        yAxisLeft.setTextSize(10f);
//        yAxisLeft.setTextColor(Color.parseColor("#1C2B34"));
//
//
//        // set the min height of the chart
//        lineChart.setMinimumHeight(500);
//        // clear the description, use a textLayout for the title
//        lineChart.setDescription(null);
//        // disable interaction with the chart
//        lineChart.setTouchEnabled(false);
//        lineChart.setDragEnabled(false);
//        lineChart.setScaleEnabled(false);
//
//        lineChart.setNoDataText("There's not data to be displayed currently.");
//
//        LineData lineData = new LineData(dataSet);
//        lineChart.setData(lineData);
//        lineChart.setExtraOffsets(40, 0, 40, 20);
//
//        lineChart.getLegend().setEnabled(false);
//
//        lineChart.invalidate(); // refresh
//
//
//        LinearLayout linearLayout = new LinearLayout(context);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//
//        if(lineChart.getParent() != null) {
//            ((ViewGroup) lineChart.getParent()).removeView(lineChart);
//        }
//        linearLayout.addView(lineChart);
//
//        return linearLayout;
//    }

//    public Pair<TextView, LinearLayout.LayoutParams> buildChartYAxisLabels() {
//        TextView yAxisLabel = new TextView(context);
//        yAxisLabel.setText("Daily Data \n (MB)");
//        yAxisLabel.setTextSize(10f);
//        yAxisLabel.setTextColor(Color.parseColor("#1C2B34"));
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
//        params.setMargins(70, 100, 0, 0);
//
//        return new Pair<>(yAxisLabel, params);
//    }

//    public Pair<TextView, LinearLayout.LayoutParams> buildChartTitle() {
//        TextView chartTitle = new TextView(context);
//        chartTitle.setText("Daily Volume of Collected Data");
//        chartTitle.setTextSize(14f);
//        chartTitle.setTypeface(null, Typeface.BOLD);
//        chartTitle.setTextColor(Color.parseColor("#1C2B34"));
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
//        params.setMargins(0, 120, 0, -50);
//
//        return new Pair<>(chartTitle, params);
//
//    }

//    public Pair<TextView, LinearLayout.LayoutParams> buildSampleDataPieceTitle() {
//        TextView sampleDataTitle = new TextView(context);
//        sampleDataTitle.setText("Sample of Collected Data");
//        sampleDataTitle.setTextColor(Color.parseColor("#1C2B34"));
//        sampleDataTitle.setTypeface(null, Typeface.BOLD);
//        LinearLayout.LayoutParams sampleDataTitleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        sampleDataTitleParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
//        sampleDataTitleParams.setMargins(0, 120, 0, 0);
//        return new Pair<>(sampleDataTitle, sampleDataTitleParams);
//    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public Pair<TextView, LinearLayout.LayoutParams> buildSampleDataPiece(Ride ride) {
//        String sampleData = "{\n" +
//                "\t\tu_id: 0038291,\n" +
//                "\t\ttime: 01012021, \n" +
//                "\t\tprice: 2.09, \n" +
//                "\t\tdestination: \"2098 Murray Ave\",\n" +
//                "\t\tstart: \"220 Fifth Ave\", \n" +
//                "\t\tduration: 10 min \n}";
//        TextView sampleDataText = new TextView(context);
//        sampleDataText.setText((CharSequence) sampleData);
//        sampleDataText.setTypeface(context.getResources().getFont(R.font.courier_prime_regular));
//        LinearLayout.LayoutParams sampleDataContentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        sampleDataContentParams.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
//        sampleDataContentParams.setMargins(70, 20, 0, 0);
//
//        return new Pair<>(sampleDataText, sampleDataContentParams);
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LinearLayout build(Ride ride) {

//        // if the collector is in deleted status, don't display anything
//        if(ride.isDeleted()) {
//            return null;
//        }

        LinearLayout containerLayout = new LinearLayout(context);
        containerLayout.setOrientation(LinearLayout.VERTICAL);

        ConstraintLayout collectorInfoViewLayout = buildCollectorInfoView(ride, containerLayout);
        collectorInfoViewLayout.setId(View.generateViewId());
        containerLayout.addView(collectorInfoViewLayout);

//        Pair<TextView, LinearLayout.LayoutParams> chartTitlePair = buildChartTitle();
//        chartTitlePair.first.setId(View.generateViewId());
//        containerLayout.addView(chartTitlePair.first, chartTitlePair.second);


//        Pair<TextView, LinearLayout.LayoutParams> chartYAxisLabelPair = buildChartYAxisLabels();
//        chartYAxisLabelPair.first.setId(View.generateViewId());
//        containerLayout.addView(chartYAxisLabelPair.first, chartYAxisLabelPair.second);
//
//        LinearLayout lineChart = buildChart(ride);

//        lineChart.setId(View.generateViewId());
//        containerLayout.addView(lineChart);
//
//
//        Pair<TextView, LinearLayout.LayoutParams> sampleDataTitlePair = buildSampleDataPieceTitle();
//        sampleDataTitlePair.first.setId(View.generateViewId());
//        containerLayout.addView(sampleDataTitlePair.first, sampleDataTitlePair.second);
//
//        Pair<TextView, LinearLayout.LayoutParams> sampleDataPiecePair = buildSampleDataPiece(ride);
//        sampleDataPiecePair.first.setId(View.generateViewId());
//        containerLayout.addView(sampleDataPiecePair.first, sampleDataPiecePair.second);



        return containerLayout;
    }

//    public class DayAxisValueFormatter extends ValueFormatter {
//        private final LineChart chart;
//        public DayAxisValueFormatter(LineChart chart) {
//            this.chart = chart;
//        }
//        @Override
//        public String getFormattedValue(float value) {
//            return "Jan " + String.format("%.0f", value + 1);
//        }
//    }
}
