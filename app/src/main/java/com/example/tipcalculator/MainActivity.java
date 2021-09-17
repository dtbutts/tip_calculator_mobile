package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Calculate.calculateButtonListener,
    DisplayInfo.recalculateButtonListener{

    private Calculate calculate;
    private DisplayInfo displayInfo;

    private int roundingSelection;
    private int tip;
    private float price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculate = new Calculate();
        displayInfo = new DisplayInfo();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, calculate).commit();
    }

    //abstract functions here
    @Override
    public void onCalculateButtonListener(int roundingSelection, int tip, float price){
        this.roundingSelection = roundingSelection;
        this.tip = tip;
        this.price = price;

        Bundle bundle = new Bundle();
        bundle.putInt("roundingSelection", roundingSelection);
        bundle.putInt("tip", tip);
        bundle.putFloat("price", price);
        displayInfo.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container_main, displayInfo).commit();
    }

    @Override
    public void onRecalculateButtonListener(){
        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container_main, calculate).commit();
    }
}
