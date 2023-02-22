package com.example.crepe.ui.main_activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.crepe.R;
import com.example.crepe.database.DatabaseManager;
import com.example.crepe.database.Ride;
import com.example.crepe.database.User;

public class CollectorCardConfirmationBuilder {
    private Context c;
    private AlertDialog.Builder dialogBuilder;
    private Ride ride;
    private Runnable refreshCollectorListRunnable;
    private DatabaseManager dbManager;

    public CollectorCardConfirmationBuilder(Context c, Ride ride, Runnable refreshCollectorListRunnable) {
        this.c = c;
        this.dialogBuilder = new AlertDialog.Builder(c);
        this.ride = ride;
        this.refreshCollectorListRunnable = refreshCollectorListRunnable;
        this.dbManager = new DatabaseManager(c);
    }

    public Dialog build() {
        final View popupView = LayoutInflater.from(c).inflate(R.layout.join_confirmation, null);
        dialogBuilder.setView(popupView);
        Dialog dialog = dialogBuilder.create();

        ImageView organizerHeadshot = (ImageView) popupView.findViewById(R.id.organizerHeadshot);
        String photoName = (String) dbManager.getOneUserByID(ride.getCreatorUserId()).getPhoto();
        int id = c.getResources().getIdentifier(photoName, "drawable", c.getPackageName());
        Drawable userImage =  c.getDrawable(id);
        if (userImage == null){
            organizerHeadshot.setImageResource(R.drawable.nd_logo);
        } else {
            organizerHeadshot.setImageDrawable(userImage);
        }

        TextView organizerField = (TextView) popupView.findViewById(R.id.organizerSuccessDetail);
        TextView telField = (TextView) popupView.findViewById(R.id.telInfo);

        Button closeBtn = (Button) popupView.findViewById(R.id.collectorCloseButton);

        Switch remindSwitch = (Switch) popupView.findViewById(R.id.remindMeSwitch);

        organizerField.setText(dbManager.getOneUserByID(ride.getCreatorUserId()).getName());
        telField.setText(dbManager.getOneUserByID(ride.getCreatorUserId()).getTel());

        // organizerHeadshot.

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //refreshCollectorListRunnable.run();
            }
        });


        return dialog;
    }

}








