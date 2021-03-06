package com.example.tiagoc.festafimdeano.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tiagoc.festafimdeano.R;
import com.example.tiagoc.festafimdeano.constant.FimdeAnoConstants;
import com.example.tiagoc.festafimdeano.util.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        this.mViewHolder.textToday = (TextView) findViewById(R.id.text_today);
        this.mViewHolder.textDaysLeft = (TextView) findViewById(R.id.text_days_left);
        this.mViewHolder.buttonConfirm = (Button) findViewById(R.id.button_confirm);

        this.mViewHolder.buttonConfirm.setOnClickListener(this);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.textToday.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));
        this.mViewHolder.textDaysLeft.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));

        String daysleft = String.format("%s %s", String.valueOf(this.getDaysLeftToDaysOfYears()), getString(R.string.dias));

        this.mViewHolder.textDaysLeft.setText(daysleft);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.verifyPreference();
    }

    private int getDaysLeftToDaysOfYears() {
        Calendar calendarToday = Calendar.getInstance();
        int today = calendarToday.get(Calendar.DAY_OF_YEAR);

        Calendar calendarLastDay = Calendar.getInstance();
        int day31december = calendarLastDay.getActualMaximum(Calendar.DAY_OF_YEAR);

        return day31december - today;
    }


    private void verifyPreference() {
        String presence = this.mSecurityPreferences.getStoreString(FimdeAnoConstants.PRESENCE);

        if (presence.equals("")) {
            this.mViewHolder.buttonConfirm.setText(R.string.nao_confirmado);
        } else if (presence.equals(FimdeAnoConstants.CONFIRM_WILL_GO)) {
            this.mViewHolder.buttonConfirm.setText(R.string.vai_participar);
        } else if (presence.equals(FimdeAnoConstants.CONFIRM_WONT_GO)) {
            this.mViewHolder.buttonConfirm.setText(R.string.nao_vai_participar);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_confirm) {

            String presence = this.mSecurityPreferences.getStoreString(FimdeAnoConstants.PRESENCE);

            Intent intent = new Intent(this, DetailActivity.class);

            intent.putExtra(FimdeAnoConstants.PRESENCE, presence);

            startActivity(intent);

        }
    }

    private static class ViewHolder {
        TextView textToday;
        TextView textDaysLeft;
        Button buttonConfirm;
    }
}
