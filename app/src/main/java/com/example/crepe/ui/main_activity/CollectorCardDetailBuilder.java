package com.example.crepe.ui.main_activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crepe.MainActivity;
import com.example.crepe.R;
import com.example.crepe.database.DatabaseManager;
import com.example.crepe.database.Ride;

public class CollectorCardDetailBuilder {
    private Context c;
    private AlertDialog.Builder dialogBuilder;
    private Ride ride;
    private Runnable refreshCollectorListRunnable;
    private DatabaseManager dbManager;

    public CollectorCardDetailBuilder(Context c, Ride ride, Runnable refreshCollectorListRunnable) {
        this.c = c;
        this.dialogBuilder = new AlertDialog.Builder(c);
        this.ride = ride;
        this.refreshCollectorListRunnable = refreshCollectorListRunnable;
        this.dbManager = new DatabaseManager(c);
    }

    public Dialog build() {
        final View popupView = LayoutInflater.from(c).inflate(R.layout.ride_detail_info, null);
        dialogBuilder.setView(popupView);
        Dialog dialog = dialogBuilder.create();

        TextView destinationField = (TextView) popupView.findViewById(R.id.destinationDetail);
        TextView departureField = (TextView) popupView.findViewById(R.id.departureDetail);
        TextView capacityField = (TextView) popupView.findViewById(R.id.capacityDetail);
        TextView timeField = (TextView) popupView.findViewById(R.id.timeDetail);
        TextView organizerField = (TextView) popupView.findViewById(R.id.organizerDetail);
        TextView descriptionField = (TextView) popupView.findViewById(R.id.descriptionDetail);
        Button joinBtn = (Button) popupView.findViewById(R.id.rideDetailJoin);
        Button closeBtn = (Button) popupView.findViewById(R.id.rideDetailCancel);

        DatabaseManager db = new DatabaseManager(c);
        destinationField.setText(ride.getDestination());
        departureField.setText(ride.getDeparture());
        capacityField.setText(ride.getPeople()+"/"+ride.getCapacity());
        timeField.setText(ride.getRideDate()+" "+ride.getRideTime());                   //need work on time
        organizerField.setText(db.getUsername(ride.getCreatorUserId()));
        descriptionField.setText(ride.getDescription());


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ride.getCollectorStatus().equals("available")){
                    ride.setPeople(String.valueOf(Integer.parseInt(ride.getPeople()) + 1));
                    ride.autoSetCollectorStatus();
                    dialog.dismiss();
                    CollectorCardConfirmationBuilder cardDetailBuilder = new CollectorCardConfirmationBuilder(c, ride, refreshCollectorListRunnable);
                    Dialog newDialog = cardDetailBuilder.build();
                    newDialog.show();
                } else {
                    Toast.makeText(c, "It's full!!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return dialog;
    }

}








