package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeArrayAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeArrayAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);

        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        double mag = currentEarthquake.getMagnitude();;
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String showMag = decimalFormat.format(mag);
        magnitudeTextView.setText(showMag);



        TextView primaryTextView = (TextView) listItemView.findViewById(R.id.primary_location);
        TextView secondaryTextView = (TextView) listItemView.findViewById(R.id.location_offset);
        String mainStringPlace = currentEarthquake.getPlace();

        final String DIVIDER_STRING = " of ";
        final String NEAR_THE = "Near the";

        if (mainStringPlace != null) {
            if (mainStringPlace.contains(DIVIDER_STRING)){
                String[] parts = mainStringPlace.split(DIVIDER_STRING);
                String secondaryText = parts[0] + DIVIDER_STRING;
                String primaryText = parts[1];

                secondaryTextView.setText(secondaryText);
                primaryTextView.setText(primaryText);
            } else {
                secondaryTextView.setText(NEAR_THE);
                primaryTextView.setText(mainStringPlace);
            }
        }



        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        Date date = new Date(currentEarthquake.getDate());

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        String dateToDisplay = dateFormat.format(date);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String timeToDisplay = timeFormat.format(date);
        String show = dateToDisplay + "\n" + timeToDisplay;
        dateTextView.setText(show);


        return listItemView;
    }

    private int getMagnitudeColor(double magnitude) {

        int magnitudeForSwitch = (int) magnitude;
        int setColor;

        switch (magnitudeForSwitch) {

            case 1:
                setColor = R.color.magnitude1;
                break;
            case 2:
                setColor = R.color.magnitude2;
                break;
            case 3:
                setColor = R.color.magnitude3;
                break;
            case 4:
                setColor = R.color.magnitude4;
                break;
            case 5:
                setColor = R.color.magnitude5;
                break;
            case 6:
                setColor = R.color.magnitude6;
                break;
            case 7:
                setColor = R.color.magnitude7;
                break;
            case 8:
                setColor = R.color.magnitude8;
                break;
            case 9:
                setColor = R.color.magnitude9;
                break;
            case 10:
                setColor = R.color.magnitude10plus;
                break;
            default:
                setColor = R.color.colorPrimaryDark;
                break;
        }

        return ContextCompat.getColor(getContext(), setColor);
    }
}
