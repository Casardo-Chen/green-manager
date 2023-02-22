package com.example.crepe.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;

import com.example.crepe.database.DatabaseManager;
import com.example.crepe.database.Ride;

public class CreateCollectorFromConfigDialogBuilder {

    private Context c;
    private AlertDialog.Builder builder;
    private  Runnable refreshCollectorListRunnable;

    private CollectorConfigurationDialogWrapper collectorConfigurationDialogWrapper;

    public CreateCollectorFromConfigDialogBuilder(Context c, Runnable refreshCollectorListRunnable) {
        this.c = c;
        this.builder = new AlertDialog.Builder(c);
        this.refreshCollectorListRunnable = refreshCollectorListRunnable;
    }

    public CollectorConfigurationDialogWrapper buildDialogWrapperWithNewCollector() {
        AlertDialog dialog = builder.create();

        DatabaseManager dbManager = new DatabaseManager(c);
        Integer collectorQuantity = dbManager.getAllRides().size();
        String collectorId = String.valueOf(collectorQuantity + 1);

        collectorConfigurationDialogWrapper = new CollectorConfigurationDialogWrapper(c, dialog,  new Ride(collectorId), refreshCollectorListRunnable);
        return collectorConfigurationDialogWrapper;
    }

    public CollectorConfigurationDialogWrapper buildDialogWrapperWithExistingCollector(Ride ride) {
        AlertDialog dialog = builder.create();
        collectorConfigurationDialogWrapper = new CollectorConfigurationDialogWrapper(c, dialog, ride, refreshCollectorListRunnable);
        return collectorConfigurationDialogWrapper;
    }


}
