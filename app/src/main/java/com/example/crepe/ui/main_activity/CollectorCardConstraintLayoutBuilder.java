package com.example.crepe.ui.main_activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.crepe.R;
import com.example.crepe.database.DatabaseManager;
import com.example.crepe.database.Ride;

import java.util.Map;

public class CollectorCardConstraintLayoutBuilder {
    private Context c;
    private TextView destinationTextView;
    private TextView departureTextView;
    private TextView scheduleStartTextView;
    private TextView organizerTextView;
    private ConstraintLayout collectorLayout;
    private TextView rideStatusTxt;
    private ImageView rideStatusImg;
    private Runnable refreshCollectorListRunnable;
    private DatabaseManager dbManager;
    private Map<String, Drawable> apps;


    // some constants for collector status
    public static final String AVAIL = "available";
    public static final String FULL = "full";



    public CollectorCardConstraintLayoutBuilder(Context c, Runnable refreshCollectorListRunnable, Map<String,Drawable> apps) {
        this.c = c;
        this.refreshCollectorListRunnable = refreshCollectorListRunnable;
        this.dbManager = new DatabaseManager(c);
        this.apps = apps;
    }

    public ConstraintLayout build(Ride ride, ViewGroup rootView, String layoutType) {

        // if the collector is deleted, don't display anything.
        // We handle deletion in our app in this way so database manipulation can be easier
//        if (ride.getCollectorStatus().equals(DELETED)) {
//            Log.e("collector", "This collector for " + ride.getDestination() + " is deleted, thus will not be displayed.");
//            return null;
//        }

        // else
        if(layoutType == "cardLayout") {
            // if for home fragment, build a card layout
            collectorLayout = (ConstraintLayout) LayoutInflater.from(c).inflate(R.layout.ride_card, rootView, false);
        } else {
            // if for data fragment, build a info layout without a card appearance
            collectorLayout = (ConstraintLayout) LayoutInflater.from(c).inflate(R.layout.collector_info_for_data_fragment, rootView, false);
        }

        // get the app name text field from the card and populate it with app name
        destinationTextView = (TextView) collectorLayout.findViewById(R.id.destinationText);
        destinationTextView.setText(ride.getDestination());

        departureTextView = (TextView) collectorLayout.findViewById(R.id.departureLocation);
        if (ride.getDescription() != null) {
            departureTextView.setText("From: "+ride.getDeparture());
        }

        scheduleStartTextView = (TextView) collectorLayout.findViewById(R.id.startTime);
        scheduleStartTextView.setText("Start Time: "+ ride.getRideDate() + " "+ride.getRideTime()); //TODO: start time
        organizerTextView = (TextView) collectorLayout.findViewById(R.id.organizerName);
        organizerTextView.setText("Organizer: "+ dbManager.getUsername(ride.getCreatorUserId()));

        // get the app status and display it
        rideStatusImg = (ImageView) collectorLayout.findViewById(R.id.runningLightImageView);
        rideStatusTxt = (TextView) collectorLayout.findViewById(R.id.rideStatusText);

        // if the collector is disabled:
        if (ride.getCollectorStatus().equals(FULL)){
            rideStatusTxt.setText("Full");
            rideStatusImg.setImageResource(R.drawable.ic_baseline_circle_12_yellow);
        } else {
            // if the collector is neither deleted nor disabled, refresh its status based on current time
            // ride.autoSetCollectorStatus();
            // also update in the database
            dbManager.updateCollectorStatus(ride);
            if (ride.getCollectorStatus().equals(AVAIL)) {
                rideStatusTxt.setText("Available");
                rideStatusImg.setImageResource(R.drawable.ic_baseline_circle_24_green);
            }
//            } else if (ride.getCollectorStatus().equals(EXPIRED)){
//                rideStatusTxt.setText("Expired");
//                rideStatusImg.setImageResource(R.drawable.ic_baseline_circle_12_grey);
//            } else {
//                rideStatusTxt.setText("Not yet started");
//                rideStatusImg.setImageResource(R.drawable.ic_baseline_circle_12_yellow);
//            }
        }

        // get App logo
        ImageView userImg = (ImageView) collectorLayout.findViewById(R.id.userImg);
        String photoName = (String) dbManager.getOneUserByID(ride.getCreatorUserId()).getPhoto();
        int id = c.getResources().getIdentifier(photoName, "drawable", c.getPackageName());
        Drawable userImage =  c.getDrawable(id);
        if (userImage == null){
            userImg.setImageResource(R.drawable.nd_logo);
        } else {
            userImg.setImageDrawable(userImage);
        }


        Button detailBtn = (Button) collectorLayout.findViewById(R.id.detailButton);

        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectorCardDetailBuilder cardDetailBuilder = new CollectorCardDetailBuilder(c, ride, refreshCollectorListRunnable);
                Dialog newDialog = cardDetailBuilder.build();
                newDialog.show();
            }
        });

        return collectorLayout;
    }

//    public Drawable getAppImage(String appName) throws PackageManager.NameNotFoundException {
//        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//
//        // get list of all the apps installed
//        List<ResolveInfo> ril = c.getPackageManager().queryIntentActivities(mainIntent, 0);
////        List<String> componentList = new ArrayList<String>();
//        String name = null;
//        Drawable image = null;
//        String packageName = "com.example.crepe";
//
//
//        // get size of ril and create a list
//        Map<String, Drawable> apps = new HashMap<String, Drawable>();
//        for (ResolveInfo ri : ril) {
//            if (ri.activityInfo != null) {
//                // get package
//                Resources res = c.getPackageManager().getResourcesForApplication(ri.activityInfo.applicationInfo);
//                // if activity label res is found
//                if (ri.activityInfo.labelRes != 0) {
//                    name = res.getString(ri.activityInfo.labelRes);
//                } else {
//                    name = ri.activityInfo.applicationInfo.loadLabel(
//                            c.getPackageManager()).toString();
//
//                }
//                packageName = ri.activityInfo.packageName;
//                image = c.getPackageManager().getApplicationIcon(packageName);
//                apps.put(name,image);
//            }
//        }
//        return apps.get(appName);
//    }

}
