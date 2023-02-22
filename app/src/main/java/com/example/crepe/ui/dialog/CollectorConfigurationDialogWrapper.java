package com.example.crepe.ui.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.crepe.MainActivity;
import com.example.crepe.R;
import com.example.crepe.database.DatabaseManager;
import com.example.crepe.database.Ride;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.List;


public class CollectorConfigurationDialogWrapper {

    private AlertDialog dialog;
    private Context context;
    private String currentScreenState;
    private Ride ride;
    private Runnable refreshCollectorListRunnable;
    private DatabaseManager dbManager;

    CollectorConfigurationDialogWrapper(Context context, AlertDialog dialog, Ride ride, Runnable refreshCollectorListRunnable) {
        this.context = context;
        this.dialog = dialog;
        this.ride = ride;
        this.currentScreenState = "startNewRideInfo";
        this.refreshCollectorListRunnable = refreshCollectorListRunnable;
        this.dbManager = new DatabaseManager(context);
    }

    public void updateCurrentView() {
        View dialogMainView;

        switch (currentScreenState) {
            case "startNewRideInfo":
                dialogMainView = LayoutInflater.from(context).inflate(R.layout.start_new_ride_info, null);
                dialog.setContentView(dialogMainView);
                // buttons
                Button popupCancelBtn = (Button) dialogMainView.findViewById(R.id.startRideDialogCancelButton);
                Button popupNextBtn = (Button) dialogMainView.findViewById(R.id.startRideDialogNextButton);
                // spinners
                Spinner numPeopleDropDown = (Spinner) dialogMainView.findViewById(R.id.numPeopleSpinner);

                // location spinner
                String[] peopleItems = new String[]{"2", "3", "4", "5", "more than 5"};
                ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, peopleItems);
                locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                numPeopleDropDown.setAdapter(locationAdapter);

                // When coming back from later popups using back button, if there's previously a selection made
                if (ride.getPeople() != null) {
                    int i;
                    for (i = 1; i < peopleItems.length; i++) {
                        if (ride.getPeople() == peopleItems[i])
                            break;
                    }
                    numPeopleDropDown.setSelection(i);
                }

                // date picker buttons and textview
                ImageButton startDateCalendarBtn = (ImageButton) dialogMainView.findViewById(R.id.startDateImageButton);
                ImageButton startTimeCalendarBtn = (ImageButton) dialogMainView.findViewById(R.id.startTimeImageButton);
                EditText startDateText = (EditText) dialogMainView.findViewById(R.id.startDateText);
                EditText startTimeText = (EditText) dialogMainView.findViewById(R.id.startTimeText);

                EditText destinationText = (EditText) dialogMainView.findViewById(R.id.destinationText);
                EditText departureText = (EditText) dialogMainView.findViewById(R.id.departureText);

                // update field values based on current collector information, mostly used when coming back from next dialogs
//                if (Long.valueOf(collector.getRideDate()) != 0) {
//                    startDateText.setText(collector.getCollectorStartTimeString());
//                } else {
//                    Calendar c = Calendar.getInstance();
//                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//                    String currentDate = df.format(c.getTime());
//                    startDateText.setText(currentDate);
//                }
//                if (Long.valueOf(collector.getRideTime()) != 0) {
//                    startTimeText.setText(collector.getCollectorEndTimeString());
//                } else {
//                    Calendar c = Calendar.getInstance();
//                    c.add(Calendar.DAY_OF_YEAR, 1);
//                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//                    String currentDate = df.format(c.getTime());
//                    startTimeText.setText(currentDate);
//                }

                startDateCalendarBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                month = month + 1;
                                String date = month + "/" + day + "/" + year;
                                startDateText.setText(date);
                            }
                        };

                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), onDateSetListener, year, month, day);
                        datePickerDialog.setTitle("Select Date");
                        datePickerDialog.show();
                    }
                });

                startTimeCalendarBtn.setOnClickListener(new View.OnClickListener() {
                    int hour;
                    int minute;
                    String format_hour;
                    String format_min;
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                                hour = selectedHour;
                                minute = selectedMinute;
                                if(hour<10){
                                    format_hour = "0" + hour;
                                }
                                else{
                                    format_hour = String.valueOf(hour);
                                }
                                if(minute<10){
                                    format_min = "0" + minute;
                                }
                                else{
                                    format_min = String.valueOf(minute);
                                }
                                startTimeText.setText(format_hour+":"+format_min);
                            }
                        };

                        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), onTimeSetListener, hour, minute, true);
                        timePickerDialog.setTitle("Select Time");
                        timePickerDialog.show();

                    }
                });



                popupCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                popupNextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int blankFlag = 0;
                        ride.setRideDate(startDateText.getText().toString());
                        ride.setRideTime(startTimeText.getText().toString());
                        ride.setCapacity(numPeopleDropDown.getSelectedItem().toString());
                        ride.setDestination(destinationText.getText().toString());
                        ride.setDeparture(departureText.getText().toString());
                        ride.setPeople("1");
                        ride.setCreatorUserId(Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID));


//                        // update location info
//                        String location = numPeopleDropDown.getSelectedItem().toString();
//                        if (location != " ") {
//                            collector.setMode(location);
//                        } else {
//                            // set the border of spinner to red
//                            Context currentContext = context.getApplicationContext();
//                            Toast.makeText(currentContext, "Please select a location!", Toast.LENGTH_LONG).show();
//                            blankFlag = 1;
//                        }
//
//                        // update date info
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//                        ParsePosition pp1 = new ParsePosition(0);
//                        Date startDate = dateFormat.parse(startDateText.getText().toString(), pp1);
//                        collector.setRideDate(startDate.getTime());
//
//                        ParsePosition pp2 = new ParsePosition(1);
//                        Date endDate = dateFormat.parse(startTimeText.getText().toString(), pp2);
//                        collector.setRideTime(endDate.getTime());
//
//                        // After successfully set the collector's end time, automatically set its status
//                        collector.autoSetCollectorStatus();
//                        // Update in database as well
//                        dbManager.updateCollectorStatus(collector);

                        if (blankFlag == 0) {
                            // update currentScreen String value
                            currentScreenState = "startNewRideDescription";
                            // recursively call itself with new currentScreen String value
                            updateCurrentView();
                        }

                    }
                });
                break;


//            case "buildDialogFromConfigGraphQuery":
//                dialogMainView = LayoutInflater.from(context).inflate(R.layout.dialog_add_collector_from_config_graph_query, null);
//                dialog.setContentView(dialogMainView);
//                Button graphQueryNxtBtn = (Button) dialogMainView.findViewById(R.id.graphQueryNextButton);
//                Button graphQueryBckBtn = (Button) dialogMainView.findViewById(R.id.graphQueryBackButton);
//                ImageButton graphQueryCloseImg = (ImageButton) dialogMainView.findViewById(R.id.closeGraphQueryPopupImageButton);
//                EditText graphQueryEditTxt = (EditText) dialogMainView.findViewById(R.id.graphQueryEditText);
//
//                // update interface elements based on the specified app in the previous popup box
//                Button openAppButton = (Button) dialogMainView.findViewById(R.id.openAppButton);
//                TextView commentOnOpenAppButton = (TextView) dialogMainView.findViewById(R.id.commentOnOpenAppButton);
//                String appName = collector.getDestination();
//                openAppButton.setText("Open " + appName);
//                commentOnOpenAppButton.setText("Demonstrate in the " + appName +" app");
//
//
//                // TODO: finish graph query
//
//                // show graph query info in the collector if available
//                if (collector.getCollectorGraphQuery() != null) {
//                    graphQueryEditTxt.setText(collector.getCollectorGraphQuery());
//                }
//
//                graphQueryBckBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // update currentScreen String value
//                        currentScreenState = "buildDialogFromConfig";
//                        // recursively call itself with new currentScreen String value
//                        updateCurrentView();
//
//                    }
//                });

//                graphQueryNxtBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int blankFlag = 0;
//                        // get graph query
//                        String graphQueryContent = graphQueryEditTxt.getText().toString();
//                        if (graphQueryContent != null) {
//                            collector.setCollectorGraphQuery(graphQueryContent);
//                        } else {
//                            // remind user to add graph query
//                            Context currentContext = context.getApplicationContext();
//                            Toast.makeText(currentContext, "Please type in the graph query!", Toast.LENGTH_LONG).show();
//                            blankFlag = 1;
//                        }
//                        if (blankFlag == 0) {
//                            // update currentScreen String value
//                            currentScreenState = "buildDialogFromConfigDataField";
//                            // recursively call itself with new currentScreen String value
//                            updateCurrentView();
//                        }
//                    }
//                });
//
//                graphQueryCloseImg.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//                break;


//            case "buildDialogFromConfigDataField":
//                dialogMainView = LayoutInflater.from(context).inflate(R.layout.dialog_add_collector_from_config_data_field, null);
//                dialog.setContentView(dialogMainView);
//
//                Button dataFieldNxtBtn = (Button) dialogMainView.findViewById(R.id.dataFieldNextButton);
//                Button dataFieldBckBtn = (Button) dialogMainView.findViewById(R.id.dataFieldBackButton);
//                ImageButton dataFieldCloseImg = (ImageButton) dialogMainView.findViewById(R.id.closeDataFieldImageButton);
//                EditText dataFieldEditText = (EditText) dialogMainView.findViewById(R.id.dataFieldEditText);
//
//                // show data fields info in the collector if available
//                if (collector.getDeparture() != null) {
//                    dataFieldEditText.setText(collector.getDeparture());
//                }
//
//                dataFieldBckBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // update currentScreen String value
//                        currentScreenState = "buildDialogFromConfigGraphQuery";
//                        // recursively call itself with new currentScreen String value
//                        updateCurrentView();
//                    }
//                });
//
//                dataFieldNxtBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // write data field into collector
//                        String dataFieldContent = dataFieldEditText.getText().toString();
//                        collector.setDeparture(dataFieldContent);
//                        // update currentScreen String value
//                        currentScreenState = "buildDialogFromConfigDescription";
//
//                        // recursively call itself with new currentScreen String value
//                        updateCurrentView();
//                    }
//                });
//
//                dataFieldCloseImg.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//                break;


            case "startNewRideDescription":
                dialogMainView = LayoutInflater.from(context).inflate(R.layout.start_new_rido_description, null);
                dialog.setContentView(dialogMainView);

                Button descriptionCreateBtn = (Button) dialogMainView.findViewById(R.id.descriptionCreateButton);
                Button descriptionBckBtn = (Button) dialogMainView.findViewById(R.id.descriptionBackButton);
                ImageButton descriptionCloseImg = (ImageButton) dialogMainView.findViewById(R.id.closeDescriptionImageButton);
                EditText descriptionEditText = (EditText) dialogMainView.findViewById(R.id.descriptionEditText);

                // show description in the collector if available
//                if (collector.getDescription() != null) {
//                    descriptionEditText.setText(collector.getDescription());
//                }

                descriptionBckBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // update currentScreen String value
                        currentScreenState = "startNewRideInfo";
                        // recursively call itself with new currentScreen String value
                        updateCurrentView();
                    }
                });

                descriptionCreateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // write description into collector
//                        String descriptionText = descriptionEditText.getText().toString();
//                        collector.setDescription(descriptionText);

                        // save the collector to database
                        // add a callback to refresh homepage every time
                        //DatabaseManager dbManager = new DatabaseManager(context);
                        // update currentScreen String value
                        ride.setDescription(descriptionEditText.getText().toString());
                        dbManager.addOneRide(ride);

                        currentScreenState = "startNewRideSuccessMessage";
                        // recursively call itself with new currentScreen String value
                        refreshCollectorListRunnable.run();
                        updateCurrentView();
                    }
                });

                descriptionCloseImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                break;


            case "startNewRideSuccessMessage":
                dialogMainView = LayoutInflater.from(context).inflate(R.layout.start_new_ride_success_message, null);
                dialog.setContentView(dialogMainView);
                // Create url for current collector
//                Gson gson = new Gson();
//                String collectorJson = gson.toJson(collector);
//                try {
//                    //byte[] collectorURL = Base64.getEncoder().encode(collectorJson.getBytes(StandardCharsets.UTF_8));
//                    String collectorURL = Base64.getEncoder().encodeToString(collectorJson.getBytes(StandardCharsets.UTF_8));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }

                Button closeSuccessMessage = (Button) dialogMainView.findViewById(R.id.closeSuccessMessagePopupButton);
                ImageButton shareUrlLinkButton = (ImageButton) dialogMainView.findViewById(R.id.shareUrlImageButton);
                ImageButton shareEmailLinkButton = (ImageButton) dialogMainView.findViewById(R.id.shareUrlImageButton);

                shareEmailLinkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // share email link

                    }
                });

                shareUrlLinkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // share url link
//                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                        ClipData clip = ClipData.newPlainText("share URL", collectorURL);
//                        clipboard.setPrimaryClip(clip);
//                        Toast.makeText(context," Copied URL to clipboard", Toast.LENGTH_LONG).show();
                    }
                });


                closeSuccessMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // update currentScreen String value
                        currentScreenState = "buildDialogFromConfigSuccessMessage";
                        // recursively call itself with new currentScreen String value
                        dialog.dismiss();

                    }
                });
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + currentScreenState);

        }
    }

    public String[] getAllInstalledAppNames() throws PackageManager.NameNotFoundException {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        // get list of all the apps installed
        // ril stands for ResolveInfoList
        List<ResolveInfo> ril = context.getPackageManager().queryIntentActivities(mainIntent, 0);
//        List<String> componentList = new ArrayList<String>();
        String name = null;
        int i = 0;

        // get size of ril and create a list
        String[] apps = new String[ril.size()];
        for (ResolveInfo ri : ril) {
            if (ri.activityInfo != null) {
                // get package
                Resources res = context.getPackageManager().getResourcesForApplication(ri.activityInfo.applicationInfo);
                // if activity label res is found
                if (ri.activityInfo.labelRes != 0) {
                    name = res.getString(ri.activityInfo.labelRes);
                } else {
                    name = ri.activityInfo.applicationInfo.loadLabel(
                            context.getPackageManager()).toString();
                }
                apps[i] = name;
                i++;
            }
        }
//        Toast.makeText(context, ril.size() + " apps are installed on this phone", Toast.LENGTH_LONG).show();
        return apps;
    }

    public void show() {
        dialog.show();
        updateCurrentView();
    }
}
