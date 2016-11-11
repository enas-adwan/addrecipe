package com.example.hp.navigation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.navigation.drawer.activity.R;

public class HealthAdvicer extends BaseActivity{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getLayoutInflater().inflate(R.layout.main, frameLayout);

		/**
		 * Setting title and itemChecked
		 */
		mDrawerList.setItemChecked(position, true);
//		setTitle(listArray[position]);
		View calculateButton = findViewById(R.id.button1);
		calculateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Double weight = getValue(R.id.weight);
				Double height = getValue(R.id.height);
				Integer age = new Integer(((EditText) findViewById(R.id.age)).getText().toString());

				RadioGroup gender = (RadioGroup) findViewById(R.id.gender);
				RadioButton selectedGender = (RadioButton) findViewById(gender.getCheckedRadioButtonId());

				Double bmr = Gender.valueOf((String)selectedGender.getText()).calculateBMR(weight, height, age);
				Double bmi = weight / (Math.pow(height/100, 2));
				((TextView) findViewById(R.id.bmi_index)).setText(bmi.toString());
				((TextView) findViewById(R.id.bmr_index)).setText(bmr.toString());
				findViewById(R.id.linearLayout3).setVisibility(LinearLayout.VISIBLE);
			}

			private Double getValue(int id) {
				return new Double(((EditText) findViewById(id)).getText()
						.toString());
			}
		});
	}
}