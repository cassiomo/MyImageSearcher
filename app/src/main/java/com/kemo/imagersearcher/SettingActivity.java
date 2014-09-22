package com.kemo.imagersearcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kemo.imagersearcher.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends Activity {

    private Spinner spImageType;
    private Spinner spColorFilter;
    private Spinner spImageSize;
    private EditText etSite;
    private Button btSave;
    private Setting setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setUpView();
        addItemsOnImageTypeSpinner();
        addItemsOnColorFilterSpinner();
        addItemsOnImageSizeSpinner();

        Intent intent = getIntent();
        setting = (Setting)intent.getSerializableExtra("setting");

        if (!StringUtils.isEmpty(setting.imageType)) {
            String selectedImageType = spImageType.getSelectedItem().toString();
            spImageType.setSelection(getIndex(spImageType, setting.imageType));
        }

        if (!StringUtils.isEmpty(setting.imageSize)) {
            String selectedImageSize = spImageSize.getSelectedItem().toString();
            spImageSize.setSelection(getIndex(spImageSize, setting.imageSize));
        }

        if (!StringUtils.isEmpty(setting.colorFilter)) {
            String selectedImageColorFilter = spColorFilter.getSelectedItem().toString();
            spColorFilter.setSelection(getIndex(spColorFilter, setting.colorFilter));
        }

        if (!StringUtils.isEmpty(setting.colorFilter)) {
            etSite.setText(setting.siteFilter);
        }
    }

    private int getIndex(Spinner spinner,String string){
        int index = 0;
        for (int i = 0; i < spinner.getAdapter().getCount(); i++){
            if (spinner.getItemAtPosition(i).equals(string)){
                index = i;
            }
        }
        return index;
    }

    private void setUpView() {
        spImageType = (Spinner)findViewById(R.id.spImageType);
        spImageSize = (Spinner)findViewById(R.id.spImageSize);
        spColorFilter = (Spinner)findViewById(R.id.spColorFilter);
        etSite = (EditText)findViewById(R.id.etSite);
        btSave = (Button)findViewById(R.id.btSave);
    }

    public void addItemsOnColorFilterSpinner() {
        spColorFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setting.colorFilter = spColorFilter.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addItemsOnImageSizeSpinner() {
        spImageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setting.imageSize = spImageSize.getSelectedItem().toString();
                System.out.println(setting.imageSize);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addItemsOnImageTypeSpinner() {
        spImageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setting.imageType = spImageType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onSaveSetting(View v) {
        Intent intent = new Intent(this, SearchActivity.class);
        setting.siteFilter = etSite.getText().toString();
        intent.putExtra("setting", setting);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
