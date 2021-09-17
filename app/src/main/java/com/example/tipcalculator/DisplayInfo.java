package com.example.tipcalculator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import java.text.DecimalFormat;

public class DisplayInfo extends Fragment {

    //declare fields
    private int roundingSelection;
    private int tip;
    private float price;

    private TextView actualTotalCost;
    private TextView actualTipAmount;
    private TextView actualOrigBill;
    private Button recalculate;
    private recalculateButtonListener recalculateButtonListener;

    public DisplayInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.fragment_display_info, container, false);

        //instantiate fields from xml
        recalculate = theView.findViewById(R.id.recalculate);
        actualTotalCost = theView.findViewById(R.id.actualTotalCost);
        actualTipAmount = theView.findViewById(R.id.actualTipAmount);
        actualOrigBill = theView.findViewById(R.id.actualOrigBill);

        //get info from calculate fragment
        roundingSelection = getArguments().getInt("roundingSelection");
        tip = this.getArguments().getInt("tip");
        price = this.getArguments().getFloat("price");

//        Log.v("DTB", "round: " + roundingSelection);
//        Log.v("DTB", "tip: " + tip);
//        Log.v("DTB", "price: " + price);

        //calculate totals
        DecimalFormat format = new DecimalFormat("0.00");
        float calcTip = (float) tip;
        calcTip = ((calcTip / 100) * price);
            // annoying conversions to round to dollars
        Double conversion = Math.round(calcTip * 100.0) / 100.0;
        calcTip = conversion.floatValue();

        float entireBill = calcTip + price;

        //this will be the same no matter rounding method
        actualOrigBill.setText("$" + price);

        //check which rounding method was used
        if(roundingSelection == 1){
            String displayTip = String.format("%.02f", calcTip);
            String displayCost = String.format("%.02f", entireBill);
            actualTotalCost.setText("$" + displayCost);
            actualTipAmount.setText("$" + displayTip);

        }else if(roundingSelection == 2){
            //round tip up
            calcTip = Math.round(calcTip);
            entireBill = calcTip + price;

            String displayTip = String.format("%.02f", calcTip);
            String displayCost = String.format("%.02f", entireBill);
            actualTotalCost.setText("$" + displayCost);
            actualTipAmount.setText("$" + displayTip + " (rounded)");
        }else{
            //round bill up
            entireBill = Math.round(entireBill);

            String displayTip = String.format("%.02f", calcTip);
            String displayCost = String.format("%.02f", entireBill);
            actualTotalCost.setText("$" + displayCost + " (rounded)");
            actualTipAmount.setText("$" + displayTip);
        }

        //listener for recalculate button
        recalculate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(recalculateButtonListener != null){
                    recalculateButtonListener.onRecalculateButtonListener();
                }
            }
        });

        return theView;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Activity activity = getActivity();
        try{
            recalculateButtonListener = (DisplayInfo.recalculateButtonListener) activity;
        } catch(ClassCastException e){
            throw new ClassCastException("Activity must be DisplayInfo");
        }
    }

    @Override
    public void onDetach( ){
        super.onDetach();
        recalculateButtonListener = null;
    }

    public interface recalculateButtonListener{
        public void onRecalculateButtonListener();
    }
}