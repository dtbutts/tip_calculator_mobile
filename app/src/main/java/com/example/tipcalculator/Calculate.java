package com.example.tipcalculator;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;


public class Calculate extends Fragment {

        //declare fields
    //text displays
    //private TextView billDisplay;
    private TextView tipDisplay;
    //private TextView roundingDisplay;

    //inputs
    private TextInputEditText enterBill;
    private SeekBar percentTip;
    private RadioGroup roundingOption;
        //need the individual radioButton?
    private Button calculateButton;

    //save values to send to main
    private int toSendTip = 20;

    //listeners
    private calculateButtonListener calculateButtonListener;


    public Calculate() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.fragment_calculate, container, false);

        //instantiate fields from layout
        //billDisplay = theView.findViewById(R.id.billDisplay);
        tipDisplay = theView.findViewById(R.id.tipDisplay);
        //roundingDisplay = theView.findViewById(R.id.roundingDisplay);

        enterBill = theView.findViewById(R.id.enterBill);
        percentTip = theView.findViewById(R.id.percentTip);
        roundingOption = theView.findViewById(R.id.roundingOption);
        calculateButton = theView.findViewById(R.id.calculateButton);

        //listener for button
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calculateButtonListener != null){

                    //get total bill price
                    float price = 0;
                    try{
                        //FOR SOME REASON THROWS EXCEPTION
                         price = Float.parseFloat(enterBill.getText().toString());
                    }catch(Exception e){
                        Log.v("DTB", "Nothing entered");

                    }

                    //get tip value
                    int tip = toSendTip;

                    //switch for radio button selection
                    int roundingSelection = -1;
                    switch(roundingOption.getCheckedRadioButtonId()){
                        case R.id.roundingNo:
                            roundingSelection = 1;
                            break;
                        case R.id.roundingTip:
                            roundingSelection = 2;
                            break;
                        case R.id.roundingBill:
                            roundingSelection = 3;
                            break;
                    }

                    //send to mainActivity
                    calculateButtonListener.onCalculateButtonListener(roundingSelection, tip, price);
                }
            }
        });

        //listener for seekbar, updates display of percent
        percentTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sk, int progress, boolean fromUser){
                tipDisplay.setText("Tip Amount:   " + progress + "%");
                toSendTip = progress;
            }

            //need these to use the seekbar listener, unused
            @Override
            public void onStartTrackingTouch(SeekBar sk){}
            @Override
            public void onStopTrackingTouch(SeekBar sk){}
        });

        return theView;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Activity activity = getActivity();
        try{
            calculateButtonListener = (calculateButtonListener) activity;
        } catch(ClassCastException e){
            throw new ClassCastException("Activity must be Calculate");
        }
    }

    @Override
    public void onDetach( ){
        super.onDetach();
        calculateButtonListener = null;
    }

    //interface used for calculate button press
    public interface calculateButtonListener{
        public void onCalculateButtonListener(int roundingSelection, int tip, float price);
    }

}