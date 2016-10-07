package com.example.hp.navigation.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.navigation.drawer.activity.R;

/**
 * @author dipenp
 *
 */
public class Item5Activity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/**
		 * Adding our layout to parent class frame layout.
		 */
		getLayoutInflater().inflate(R.layout.item_5_layout, frameLayout);
		
		/**
		 * Setting title and itemChecked  
		 */
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);
		
		((ImageView)findViewById(R.id.image_view)).setBackgroundResource(R.drawable.image5);
	}
}
