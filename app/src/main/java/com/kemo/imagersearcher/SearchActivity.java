package com.kemo.imagersearcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends Activity {

    private EditText etQuery;
    private GridView gvResult;
    private ArrayList<ImageResult> imageResults;
    private ImageResultAdapter aImageResultAdapter;
    private final int REQUEST_CODE = 20;
    private Setting mainSetting;
    private String targetUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
    private String resultSetParam = "&rsz=8";
    private String imgColorFilterParam = "&imgcolor=";
    private String imgTypeFilterParam = "&imgtype=";
    private String imgSizeFilterParam = "&imgsz=";
    private String asSiteSearchParam = "&as_sitesearch=";
    private String startParam="&start=";
    private String searchUrl;
    private int increment;
    private int maxPage = 8;

    private EndlessScrollListener endlessScrollListener = new EndlessScrollListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {
            Log.i("INFO", "Loading more items");
            loadMoreDataFromApi(page);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageResults = new ArrayList<ImageResult>();
        aImageResultAdapter = new ImageResultAdapter(this, imageResults);
        gvResult.setAdapter(aImageResultAdapter);
        gvResult.setOnScrollListener(endlessScrollListener);
    }

    public void loadMoreDataFromApi(int offset) {
        // increment the starting image number
        increment += 8;
        String incrementPage = startParam + increment;
        if (!searchUrl.contains(startParam)) {
            searchUrl = searchUrl + incrementPage;
        } else {
            int lastStartIndex = searchUrl.lastIndexOf(startParam);
            searchUrl = searchUrl.substring(0,lastStartIndex);
            searchUrl = searchUrl + incrementPage;
        }
        makeImageSearchRequest(searchUrl, offset);
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResult = (GridView) findViewById(R.id.gvResult);
        gvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                ImageResult result = imageResults.get(position);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        });
    }

    public void onImageSearch(View v) {
        increment = 0;
        String query = etQuery.getText().toString();
        if (!StringUtils.isEmpty(query)) {
            Toast.makeText(this, "Search for: " + query, Toast.LENGTH_SHORT).show();

            String settingParams = null;
            //String searchUrl = null;

            if (mainSetting != null) {
                settingParams = imgColorFilterParam + mainSetting.colorFilter
                        + imgSizeFilterParam + mainSetting.imageSize
                        + imgTypeFilterParam + mainSetting.imageType;
                if (!StringUtils.isEmpty(mainSetting.siteFilter)) {
                    settingParams = settingParams + asSiteSearchParam + mainSetting.siteFilter;
                }
                searchUrl = targetUrl + query + settingParams + resultSetParam;
            } else {
                searchUrl = targetUrl + query + resultSetParam;
            }
            makeImageSearchRequest(searchUrl,0);
        } else {
            Toast.makeText(this, "Please enter search query", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeImageSearchRequest(String searchUrl, final int page) {

        if (maxPage > page) {

            if (page == 0) {
                imageResults.clear();
            }

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(searchUrl, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("DEBUG", response.toString());
                    JSONArray imageResultJson = null;

                    try {
                        imageResultJson = response.getJSONObject("responseData").getJSONArray("results");
                        aImageResultAdapter.addAll(ImageResult.fromJSONArray(imageResultJson));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("INFO", imageResults.toString());
                }
            });
        } else {
            Toast.makeText(this, "End of the maximum page (8) ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.miSetting:
                Intent intent = new Intent(this, SettingActivity.class);
                if (mainSetting == null) {
                    mainSetting = new Setting();
                }
                intent.putExtra("setting", mainSetting);
                startActivityForResult(intent, 20);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            Setting newSetting = (Setting) data.getSerializableExtra("setting");
            mainSetting.colorFilter = newSetting.colorFilter;
            mainSetting.imageType = newSetting.imageType;
            mainSetting.imageSize = newSetting.imageSize;
            mainSetting.siteFilter = newSetting.siteFilter;

            Toast.makeText(this, "New Setting is: " + mainSetting, Toast.LENGTH_SHORT).show();
        }
    }
}
