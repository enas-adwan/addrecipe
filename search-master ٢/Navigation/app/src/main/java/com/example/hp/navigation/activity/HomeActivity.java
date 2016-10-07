package com.example.hp.navigation.activity;

import com.example.hp.navigation.adapters.HomeScreenGridViewAdapter;
import com.navigation.drawer.activity.R;

import android.os.Bundle;
import android.widget.GridView;

/**
 * @author dipenp
 *
 */
public class HomeActivity extends BaseActivity {
	
	
	private GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * We will not use setContentView in this activty Rather than we will
		 * use layout inflater to add view in FrameLayout of our base activity
		 * layout
		 */

		/**
		 * Adding our layout to parent class frame layout.
		 */
		getLayoutInflater().inflate(R.layout.home_layout, frameLayout);

		/**
		 * Setting title and itemChecked
		 */
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);
		
		initializeActivity();
	}

	private void initializeActivity() {

		gridview = (GridView)findViewById(R.id.gridview);
		gridview.setAdapter(new HomeScreenGridViewAdapter(this, _items));
	}
}
