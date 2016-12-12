package com.example.hp.navigation.activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import android.content.Intent;

import com.navigation.drawer.activity.R;

//import android.asynctask.library.JSONParser;
//import android.support.v4.view.MenuItemCompat;
//import android.support.v7.widget.SearchView;


public class HealthAdvicer extends BaseActivity {
	/** Called when the activity is first created. */
	private Spinner spinner;
	private Spinner spinner1;
	String goal;
	String b;
	double active;
	recipeDbHelper userDbHelper3;
	SQLiteDatabase sqLiteDatabase;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getLayoutInflater().inflate(R.layout.main, frameLayout);

		/**
		 * Setting title and itemChecked
		 */
		mDrawerList.setItemChecked(position, true);
		setTitle("Calorie Management");
//		setTitle(listArray[position]);
		View calculateButton = findViewById(R.id.button1);

		calculateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Double weight = getValue(R.id.weight);
				Double height = getValue(R.id.height);
				Integer age = new Integer(((EditText) findViewById(R.id.age)).getText().toString());
				spinner = (Spinner) findViewById(R.id.spinner);
				String quintity= String.valueOf(spinner.getSelectedItem());
				switch(quintity){

					case "Weight loss":
						goal="loss";
						break;
					case "Maintain my current weight":
						goal="equal";
						break;
					case "Weight gain":
						goal="gain";
						break;


				}
				spinner1 = (Spinner) findViewById(R.id.spinner1);
				String quintity1= String.valueOf(spinner1.getSelectedItem());
				switch(quintity1){

					case "Low Active":
						active=1.375;
						break;
					case "Active":
						active=1.55;
						break;
					case "Very Active":
						active=1.725;
						break;


				}

				RadioGroup gender = (RadioGroup) findViewById(R.id.gender);
				RadioButton selectedGender = (RadioButton) findViewById(gender.getCheckedRadioButtonId());

				Double bmr = Gender.valueOf((String)selectedGender.getText()).calculateBMR(weight, height, age,active);
				Double bmi = weight / (Math.pow(height/100, 2));
				if(bmi<18.5){

					b=" Under Wieght";
				}else if (bmi >18.5 && bmi <= 24.9){

					b=" Normal weight";
				}else if(bmi > 25 && bmi <= 29.9)

				{

					b=" Overweight";

				}else {
					b=" Obesity";
				}
				if(b.equalsIgnoreCase(" Overweight")&&goal.equalsIgnoreCase("gain")){

					Toast.makeText(getApplicationContext(), "Based on your height and weight ,your goal weight is extremly highe and you should seek the advice of midical doctor. ", Toast.LENGTH_LONG).show();

				} 	else if(b.equalsIgnoreCase(" Under Wieght")&&goal.equalsIgnoreCase("loss")) {

					Toast.makeText(getApplicationContext(), "Based on your height and weight ,your goal weight is extremly low and you should seek the advice of midical doctor. ", Toast.LENGTH_LONG).show();


				}



				else {
					userDbHelper3=new recipeDbHelper(getApplicationContext());
					sqLiteDatabase=userDbHelper3.getWritableDatabase();

					userDbHelper3.addinnformationtracking( bmr.toString(),sqLiteDatabase);
					//myDataa = userDbHelper3.SelectAll();
					//	Intent i = new Intent(Intent.ACTION_VIEW);
					//i.setData(Uri.parse(REGISTER_URL+s));
					Intent i = new Intent(getApplicationContext(), trackingdailycalory.class);
					startActivity(i);
					//((TextView) findViewById(R.id.bmi_index)).setText(bmi.toString()+b);
					//((TextView) findViewById(R.id.bmr_index)).setText(bmr.toString());
					//findViewById(R.id.linearLayout3).setVisibility(LinearLayout.VISIBLE);
				}}

			private Double getValue(int id) {
				return new Double(((EditText) findViewById(id)).getText()
						.toString());
			}
		});
	}
}