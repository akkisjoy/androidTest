package com.example.androidtest.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.androidtest.R;
import com.example.androidtest.model.Data;
import com.squareup.picasso.Picasso;

public class DataDialog {

    public static void show(AppCompatActivity context, Data data) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, R.style.FullScreenDialogTheme);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_data, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();

        Window alertWindow = alertDialog.getWindow();
        if (alertWindow != null) {
            alertWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            alertWindow.setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            alertWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        ImageView imageView = dialogView.findViewById(R.id.imageView);
        ImageView goBack = dialogView.findViewById(R.id.goBack);
        TextView titleText = dialogView.findViewById(R.id.titleText);
        TextView pleadgeText = dialogView.findViewById(R.id.pleadgeText);
        TextView descriptionText = dialogView.findViewById(R.id.descriptionText);
        TextView byText = dialogView.findViewById(R.id.byText);
        TextView countryText = dialogView.findViewById(R.id.countryText);
        TextView locationText = dialogView.findViewById(R.id.locationText);
        TextView backersText = dialogView.findViewById(R.id.backersText);

        Picasso.get().load("https://dummyimage.com/16:9x270/").into(imageView);
        titleText.setText(data.getTitle());
        pleadgeText.setText(String.format("Amount Pledge -%s", data.getAmtPledged()));
        descriptionText.setText(String.format("Blurb - %s", data.getBlurb()));
        byText.setText(String.format("By - %s", data.getBy()));
        countryText.setText(String.format("Country - %s", data.getCountry()));
        locationText.setText(String.format("Location - %s", data.getLocation()));
        backersText.setText(String.format("Number of Backers - %s", data.getNumBackers()));

        goBack.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();

    }
}
