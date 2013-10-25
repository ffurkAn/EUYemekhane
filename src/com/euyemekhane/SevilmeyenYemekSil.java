package com.euyemekhane;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class SevilmeyenYemekSil extends SherlockActivity implements OnItemClickListener {

	private ListView listView;
	private SevilmeyenYemekAdapter adapter;
	private SevilmeyenYemekDAL dalSevilmeyen = new SevilmeyenYemekDAL(this);
	private ActionMode mActionMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sevilmeyen_yemek_sil);

		listView = (ListView) findViewById(R.id.sevilmeyenYemekListView);

		ArrayList<SevilmeyenYemek> sevilmeyenYemekListe = dalSevilmeyen.TumYemekleriGetir();
		adapter = new SevilmeyenYemekAdapter(this, R.id.sevilmeyenYemekListView, sevilmeyenYemekListe);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				onListItemSelect(position);
				return true;
			}
		});


	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		if (mActionMode != null) {
			onListItemSelect(position);
		}
	}

	private void onListItemSelect(int position) {
		adapter.toggleSelection(position);
		boolean hasCheckedItems = adapter.getSelectedCount() > 0;

		if (hasCheckedItems && mActionMode == null)
			// there are some selected items, start the actionMode
			mActionMode = startActionMode(new ActionModeCallback());
		else if (!hasCheckedItems && mActionMode != null)
			// there no selected items, finish the actionMode
			mActionMode.finish();

		if (mActionMode != null)
			mActionMode.setTitle(String.valueOf("Seçilen: " + adapter.getSelectedCount()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.actionbar_add, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.actionbar_add_btn:
			Intent i = new Intent(SevilmeyenYemekSil.this, SevilmeyenYemekEkle.class);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private class ActionModeCallback implements ActionMode.Callback {
		 
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            //inflate contextual menu
            mode.getMenuInflater().inflate(R.menu.actionbar_delete, menu);
            return true;
        }
 
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
 
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
	            case R.id.actionbar_delete_btn:
	                //retrieve selected items and delete them out
	                SparseBooleanArray selected = adapter.getSelectedIds();
	                for (int i = (selected.size() - 1); i >= 0; i--) {
	                    if (selected.valueAt(i)) {
	                        SevilmeyenYemek selectedItem = adapter.getItem(selected.keyAt(i));
	                        adapter.remove(selectedItem);
	                    }
	                }
	                mode.finish(); //Action picked, so close the CAB
	                return true;
	            default:
	                return false;
            }
        }
 
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            //remove selection
            adapter.removeSelection();
            mActionMode = null;
        }
    }

}
