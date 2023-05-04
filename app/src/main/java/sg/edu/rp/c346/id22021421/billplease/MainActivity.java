package sg.edu.rp.c346.id22021421.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText disInput;
    private TextInputEditText PAXInput;
    private TextInputEditText amountInput;

    Button splitBtn;
    ToggleButton GSTSVCTB;
    ToggleButton GSTSVCTB1;
    RadioGroup rgPayoptions;
    TextView totalbillDisplay;
    TextView totalpaxDisplay;
    RadioButton cashRadioButton;
    RadioButton paynowRadioButton;
    Button reseting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalbillDisplay = findViewById(R.id.totalbill);
        totalpaxDisplay = findViewById(R.id.eachperson);
        splitBtn = findViewById(R.id.split);
        GSTSVCTB = findViewById(R.id.gst);
        GSTSVCTB1 = findViewById(R.id.svc);
        disInput = findViewById(R.id.discount);
        PAXInput = findViewById(R.id.pax);
        amountInput = findViewById(R.id.amt);
        rgPayoptions = findViewById(R.id.paymentopt);
        cashRadioButton = findViewById(R.id.cash);
        paynowRadioButton = findViewById(R.id.paynow);

        splitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = 0.0;
                int pax = 0;
                double discount = 0.0;

                if (amountInput.getText().toString().isEmpty() || PAXInput.getText().toString().isEmpty() || disInput.getText().toString().isEmpty()) {
                    totalbillDisplay.setText("ERROR NO INPUT DETECTED");
                    totalpaxDisplay.setText("ERROR NO INPUT DETECTED");
                    return;
                } else {
                    amount = Double.parseDouble(amountInput.getText().toString());
                    pax = Integer.parseInt(PAXInput.getText().toString());
                    discount = Double.parseDouble(disInput.getText().toString());
                }

                if (pax > 0) {
                    if (GSTSVCTB.isChecked()&&GSTSVCTB1.isChecked()) {
                        amount = amount * 1.17;
                    }
                    else if(GSTSVCTB.isChecked()){
                        amount = amount * 1.07;
                    }

                    if (discount > 0) {
                        amount = amount * (1 - (discount / 100));
                    }

                    double eachPerson = amount / pax;

                    totalbillDisplay.setText(String.format("Total Bill: $%.2f", amount));
                    if (cashRadioButton.isChecked()) {
                        totalpaxDisplay.setText(String.format("Each Pays: $%.2f in cash", eachPerson));
                    } else {
                        totalpaxDisplay.setText(String.format("Each Pays: $%.2f via PayNow to 912345678", eachPerson));
                    }
                }
            }
        });

        rgPayoptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.cash) {
                    paynowRadioButton.setChecked(false);
                } else if (checkedId == R.id.paynow) {
                    cashRadioButton.setChecked(false);
                }
            }
        });

        reseting = findViewById(R.id.reset);
        reseting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountInput.setText("");
                PAXInput.setText("");
                disInput.setText("");
                totalbillDisplay.setText("");
                totalpaxDisplay.setText("");
                GSTSVCTB.setChecked(false);
                GSTSVCTB1.setChecked(false);
                cashRadioButton.setChecked(true);
                paynowRadioButton.setChecked(false);
            }
        });
    }
}


