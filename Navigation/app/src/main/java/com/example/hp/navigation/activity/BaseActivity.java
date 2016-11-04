package com.example.hp.navigation.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.navigation.drawer.activity.R;
import com.example.hp.navigation.adapters.NavigationDrawerListAdapter;
import com.example.hp.navigation.models.Items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author dipenp
 *
 * This activity will add Navigation Drawer for our application and all the code related to navigation drawer.
 * We are going to extend all our other activites from this BaseActivity so that every activity will have Navigation Drawer in it.
 * This activity layout contain one frame layout in which we will add our child activity layout.    
 */
public class BaseActivity extends ActionBarActivity {

	/**
	 *  Frame layout: Which is going to be used as parent layout for child activity layout.
	 *  This layout is protected so that child activity can access this  
	 *  */
	protected FrameLayout frameLayout;
	private SimpleCursorAdapter myAdapter;

	SearchView searchView = null;
	private String[] strArrData = {"No Suggestions"};
	/**
	 * ListView to add navigation drawer item in it.
	 * We have made it protected to access it in child class. We will just use it in child class to make item selected according to activity opened.  
	 */
	
	protected ListView mDrawerList;
	
	/**
	 * List item array for navigation drawer items. 
	 * */
	protected String[] listArray = { "Show recipe", "", "Add recipe", "Show favourite", "Item 4", "Item 5" ,"Item 6"};
	protected ArrayList<Items> _items;
	
	/**
	 * Static variable for selected item position. Which can be used in child activity to know which item is selected from the list.  
	 * */
	protected static int position;
	
	/**
	 *  This flag is used just to check that launcher activity is called first time 
	 *  so that we can open appropriate Activity on launch and make list item position selected accordingly.    
	 * */
	private static boolean isLaunch = true;
	
	/**
	 *  Base layout node of this Activity.    
	 * */
	private DrawerLayout mDrawerLayout;
	
	/**
	 * Drawer listner class for drawer open, close etc.
	 */
	private ActionBarDrawerToggle actionBarDrawerToggle;


	private Toolbar toolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer_base_layout);
		final String[] from = new String[] {"title"};
		final int[] to = new int[] {android.R.id.text1};

		// setup SimpleCursorAdapter
		myAdapter = new SimpleCursorAdapter(BaseActivity.this, android.R.layout.simple_spinner_dropdown_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		// Fetch data from mysql table using AsyncTask
		new AsyncFetch().execute();
		frameLayout = (FrameLayout)findViewById(R.id.content_frame);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
		setSupportActionBar(toolbar);
		// set a custom shadow that overlays the main content when the drawer opens
		//mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
		_items = new ArrayList<Items>();
		_items.add(new Items("Show Recipe", "Item One Description", R.drawable.item_1));
		_items.add(new Items("Add recipe", "Item Two Description", R.drawable.item_2));
		_items.add(new Items("show favourite", "Item Three Description", R.drawable.item_3));
		_items.add(new Items("profile ", "Item Four Description", R.drawable.item_4));
		_items.add(new Items("Logout", "Item Five Description", R.drawable.item_5));
		_items.add(new Items("edit profile", "Item six Description", R.drawable.item_5));
		_items.add(new Items("search filter", "Item six Description", R.drawable.item_5));
		
		//Adding header on list view 
		View header = (View)getLayoutInflater().inflate(R.layout.list_view_header_layout, null);
		mDrawerList.addHeaderView(header);
		
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new NavigationDrawerListAdapter(this, _items));
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				openActivity(position);
			}
		});
		
		// enable ActionBar app icon to behave as action to toggle nav drawer
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
		actionBarDrawerToggle = new ActionBarDrawerToggle(
				this,						/* host Activity */
				mDrawerLayout, 				/* DrawerLayout object */
				toolbar,     /* nav drawer image to replace 'Up' caret */
				R.string.open_drawer,       /* "open drawer" description for accessibility */
				R.string.close_drawer)      /* "close drawer" description for accessibility */ 
		{ 
			@Override
			public void onDrawerClosed(View drawerView) {

				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {

				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				super.onDrawerStateChanged(newState);
			}
		};
		mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
		
actionBarDrawerToggle.syncState();

	}
	
	/**
	 * @param position
	 * 
	 * Launching activity when any list item is clicked. 
	 */
	protected void openActivity(int position) {
		
		/**
		 * We can set title & itemChecked here but as this BaseActivity is parent for other activity, 
		 * So whenever any activity is going to launch this BaseActivity is also going to be called and 
		 * it will reset this value because of initialization in onCreate method.
		 * So that we are setting this in child activity.    
		 */
//		mDrawerList.setItemChecked(position, true);
//		setTitle(listArray[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
		BaseActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities. 
		final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
		switch (position) {
		case 0:
			startActivity(new Intent(this, ShowRecipe.class));
			break;
		case 1:
			startActivity(new Intent(this, ShowRecipe.class));
			break;
		case 2:
			startActivity(new Intent(this, AddRecipe.class));
			break;
		case 3:
			startActivity(new Intent(this, Showfav.class));
			break;
		case 4:
			startActivity(new Intent(this, profile.class));
			break;
		case 5:

			LoginManager.getInstance().logOut();
			pref.edit().clear().commit();
			if(AccessToken.getCurrentAccessToken()!=null)
			{
				Log.v("User is login","YES");

			}
			else
			{
				Log.v("User is not login","OK");
				Intent intent = new Intent(this, MainActivity.class);
				intent.putExtra("finish", true); // if you are checking for this in your other Activities
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
						Intent.FLAG_ACTIVITY_CLEAR_TASK |
						Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();

			}
			break;
			case 6:
				SharedPreferences type_user = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
				String user=type_user.getString("user_type","");

				if(user.equals("app")) {
					Intent intent = new Intent(this, editAppProfile.class);
					startActivity(intent);
				}
				else {  Intent intent = new Intent(this, editFaceProfile.class);
					startActivity(intent);
				}

				break;
			case 7:
				startActivity(new Intent(this, searchfilter.class));
				break;
		default:
			break;
		}
		
//		Toast.makeText(this, "Selected Item Position::"+position, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// adds item to action bar
		getMenuInflater().inflate(R.menu.search_main, menu);

		// Get Search item from action bar and Get Search service
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchManager searchManager = (SearchManager) BaseActivity.this.getSystemService(Context.SEARCH_SERVICE);
		if (searchItem != null) {
			searchView = (SearchView) searchItem.getActionView();
		}
		if (searchView != null) {
			searchView.setSearchableInfo(searchManager.getSearchableInfo(BaseActivity.this.getComponentName()));
			searchView.setIconified(false);
			searchView.setSuggestionsAdapter(myAdapter);
			// Getting selected (clicked) item suggestion
			searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
				@Override
				public boolean onSuggestionClick(int position) {
//here go to recipe
					// Add clicked text to search box
					CursorAdapter ca = searchView.getSuggestionsAdapter();
					Cursor cursor = ca.getCursor();
					cursor.moveToPosition(position);
					searchView.setQuery(cursor.getString(cursor.getColumnIndex("title")),false);
					return true;
				}

				@Override
				public boolean onSuggestionSelect(int position) {
					return true;
				}
			});
			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String s) {
					return false;
				}

				@Override
				public boolean onQueryTextChange(String s) {

					// Filter data
					final MatrixCursor mc = new MatrixCursor(new String[]{ BaseColumns._ID, "title" });
					for (int i=0; i<strArrData.length; i++) {
						if (strArrData[i].toLowerCase().startsWith(s.toLowerCase()))
							mc.addRow(new Object[] {i, strArrData[i]});
					}
					myAdapter.changeCursor(mc);
					return false;
				}
			});
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}


	// Every time when you press search button on keypad an Activity is recreated which in turn calls this function
	@Override
	protected void onNewIntent(Intent intent) {

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			if (searchView != null) {
				searchView.clearFocus();
			}

			// User entered text and pressed search button. Perform task ex: fetching data from database and display

		}
	}
	
	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view

        return super.onPrepareOptionsMenu(menu);
    }
    
    /* We can override onBackPressed method to toggle navigation drawer*/
	@Override
	public void onBackPressed() {
		if(mDrawerLayout.isDrawerOpen(mDrawerList)){
			mDrawerLayout.closeDrawer(mDrawerList);
		}else {
			mDrawerLayout.openDrawer(mDrawerList);
		}
	}

	// Create class AsyncFetch
	private class AsyncFetch extends AsyncTask<String, String, String> {

		ProgressDialog pdLoading = new ProgressDialog(BaseActivity.this);
		HttpURLConnection conn;
		URL url = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("wait","the result");
			//this method will be running on UI thread
			pdLoading.setMessage("\tLoading...");
			pdLoading.setCancelable(false);
			pdLoading.show();

		}

		@Override
		protected String doInBackground(String... params) {
			try {

				// Enter URL address where your php file resides or your JSON file address
				url = new URL("http://10.0.2.2/fetch_all.php");

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return e.toString();
			}
			try {

				// Setup HttpURLConnection class to send and receive data from php and mysql
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");

				// setDoOutput to true as we receive data
				conn.setDoOutput(true);
				conn.connect();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return e1.toString();
			}

			try {

				int response_code = conn.getResponseCode();

				// Check if successful connection made
				if (response_code == HttpURLConnection.HTTP_OK) {

					// Read data sent from server
					InputStream input = conn.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(input));
					StringBuilder result = new StringBuilder();
					String line;

					while ((line = reader.readLine()) != null) {
						result.append(line);
					}

					// Pass data to onPostExecute method
					return (result.toString());

				} else {
					return("Connection error");
				}

			} catch (IOException e) {
				e.printStackTrace();
				return e.toString();
			} finally {
				conn.disconnect();
			}


		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("finish","the result");
			//this method will be running on UI thread
			ArrayList<String> dataList = new ArrayList<String>();
			pdLoading.dismiss();


			if(result.equals("no rows")) {

				// Do some action if no data from database

			}else{

				try {

					JSONArray jArray = new JSONArray(result);

					// Extract data from json and store into ArrayList
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject json_data = jArray.getJSONObject(i);
						dataList.add(json_data.getString("title"));
					}

					strArrData = dataList.toArray(new String[dataList.size()]);

				} catch (JSONException e) {
					// You to understand what actually error is and handle it appropriately
					Toast.makeText(BaseActivity.this, e.toString(), Toast.LENGTH_LONG).show();
					Toast.makeText(BaseActivity.this, result.toString(), Toast.LENGTH_LONG).show();
				}

			}

		}

	}
}
