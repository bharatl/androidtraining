package com.bharatl.simpletodolist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
	
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		lvItems = (ListView) findViewById(R.id.listView);
		items = new ArrayList<String>();
		readItems();
		itemsAdapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		items.add("First Item");
		items.add("Second Item");
		setupListViewListener();
	}
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		}catch (IOException e){
			
			items = new ArrayList<String>();
			e.printStackTrace();
		}
		
		
	}
	private void setupListViewListener(){
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				return true;
			}});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}
	public void addTodoItem(View v) {
		EditText eText = (EditText) findViewById(R.id.NewItemText);
		if (eText.getText().length() != 0){
		itemsAdapter.add(eText.getText().toString());
		eText.setText("");	
		saveItems();
		}
		
	}
	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, items);
			
			
		}catch (IOException e){
			
			e.printStackTrace();
		}
		
	}

}
