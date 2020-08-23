package com.example.androidtest.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtest.R;
import com.example.androidtest.adapter.DataAdapter;
import com.example.androidtest.adapter.ResponseListener;
import com.example.androidtest.listener.RecyclerViewClickListener;
import com.example.androidtest.model.Data;
import com.example.androidtest.viewmodel.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {

    private SearchView searchView;
    private DataViewModel dataViewModel;
    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private List<Data> dataList;
    private boolean isDataLoaded = false;
    private int PAGE = 1;
    private static final int COUNT = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        initViewModel();

    }

    private void initViewModel() {
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        dataViewModel.callTheApi(PAGE, COUNT);

        dataViewModel.getListLiveData().observe(this, this::loadData);

        dataViewModel.isError.observe(this, aBoolean -> {

        });
    }

    private void loadData(List<Data> data) {
//        if (!isDataLoaded || dataList == null) {
//            dataList = new ArrayList<>();
//        }

        dataAdapter.addDataList(data);
        dataAdapter.notifyDataSetChanged();

//        if (isDataLoaded) {
//            dataAdapter.notifyDataSetChanged();
//        }
//        if (!isDataLoaded) {
//            isDataLoaded = true;
//        } else {
//            dataAdapter.setLoaded();
//        }

        if (dataAdapter != null) {
            dataAdapter.setOnLoadMoreListener(() -> {
                if (dataList == null) {
                    dataList = new ArrayList<>();
                }
                PAGE += 1;
                dataViewModel.callTheApi(PAGE, COUNT);
            });
        }
    }

    private void initView() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        dataAdapter = new DataAdapter(recyclerView, this);
        recyclerView.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.searchItem);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dataAdapter.filter(newText, false, new ResponseListener() {
                    @Override
                    public void onResponse(Object object) {

                    }
                });
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void recyclerViewListClicked(Object object) {
        DataDialog.show(MainActivity.this, (Data) object);
    }
}