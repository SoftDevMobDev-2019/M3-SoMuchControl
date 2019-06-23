package au.edu.swin.sdmd.somuchcontrol;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseUI();
    }

    private void initialiseUI() {
        switchBackground();
        switchGravity();
    }


    /** Handles the switch for changing background colour of the view. */
    private void switchBackground() {
        Switch sBackground = (Switch) findViewById(R.id.sDark);
        final TextView tvText = findViewById(R.id.textView);

        /** Need to use CompoundButton.OnCheckedChangeListener() */
        sBackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvText.setTextColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.primaryDarkTextColor));
                    tvText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.colorPrimaryDark));
                    // TODO #2 Add snackbar to announce background colour (need design library)
                    // TODO #3a Refactor snackbar to undo change
                } else {
                    tvText.setTextColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.textColor));
                    tvText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.colorPrimaryLight));
                }
            }
        });
    }

    // TODO #3b Add click listener for snackbar action to undo background change

    /** Handles the switch for changing the view alignment. */
    private void switchGravity() {
        Switch sGravity = (Switch) findViewById(R.id.sCentre);
        final TextView tvText = findViewById(R.id.textView);
        sGravity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // TODO #4 show the dialog here
                    tvText.setGravity(Gravity.CENTER);
                } else {
                    tvText.setGravity(Gravity.LEFT);
                }
            }
        });
    }


    /** Handles radio button clicks. Note these need to be set up as a RadioGroup in the layout
     * file. This method is set in the layout file.
     */
    public void onRadioClicked(View view) {
        TextView tvText = findViewById(R.id.textView);
        boolean checked = ((RadioButton) view).isChecked();

        /* If one is clicked, work out which button is now on.*/
        switch(view.getId()) {
            case R.id.rbPostgrad:
                if (checked) tvText.setText("COS80019");
                break;
            case R.id.rbUndergrad:
                if (checked) tvText.setText("COS30017");
                break;
        }
        // TODO #1 add toast to alert that text has changed

    }


    /** Handles checkbox clicks. While these are independent controls, they usually have some
     * relation to each other and so need to be handled together. This method is set in the
     * layout file.
     */
    public void onCheckboxClicked(View view) {
        TextView tvText = findViewById(R.id.textView);
        boolean checked = ((CheckBox) view).isChecked();
        boolean bold = ((CheckBox)findViewById(R.id.cbBold)).isChecked();
        boolean serif = ((CheckBox)findViewById(R.id.cbSerif)).isChecked();

        switch(view.getId()) {
            case R.id.cbBold:
                if (checked) bold = true;
                else bold = false;
                break;
            case R.id.cbSerif:
                if (checked) serif = true;
                else serif = false;
                break;
        }
        if (bold && serif) tvText.setTypeface(Typeface.create("serif", Typeface.BOLD));
        else if (bold) tvText.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
        else if (serif) tvText.setTypeface(Typeface.create("serif", Typeface.NORMAL));
        else tvText.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
    }


    // A dialog for checking whether text should be centered.
    public Dialog getDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Switch sGravity = (Switch) findViewById(R.id.sCentre);
                sGravity.setChecked(true);
                TextView tvText = findViewById(R.id.textView);
                tvText.setGravity(Gravity.CENTER);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Switch sGravity = (Switch) findViewById(R.id.sCentre);
                sGravity.setChecked(false);
            }
        });
        builder.setMessage("Are you sure you want to centre the text?")
                .setTitle("Gravity");

        return builder.create();
    }




}
