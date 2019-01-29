package ir.sweetsoft.ges;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import common.MinMaxInputFilter;

public class RowView extends RelativeLayout {


    public TextView getLabel() {
        return Label;
    }

    public EditText getInput() {
        return Input;
    }

    private TextView Label;
    private EditText Input;

    public void setFilters(int maxInputLength,int minInputValue,int maxInputValue) {
        Input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxInputLength),new MinMaxInputFilter(minInputValue, maxInputValue)});
    }
    public RowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RowView, 0, 0);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_row, this, true);
        Label = (TextView) getChildAt(0);
        Input = (EditText) getChildAt(1);

//        Input.setText("10");
        Input.setId(View.generateViewId());
        Label.setId(View.generateViewId());
        Label.setText(a.getString(R.styleable.RowView_title_text));
        ((RelativeLayout.LayoutParams)Input.getLayoutParams()).addRule(RelativeLayout.RIGHT_OF, Label.getId());
        RelativeLayout.LayoutParams LabelParams=(RelativeLayout.LayoutParams)Label.getLayoutParams();
        RelativeLayout.LayoutParams InputParams=(RelativeLayout.LayoutParams)Input.getLayoutParams();
        a.recycle();


    }

}
