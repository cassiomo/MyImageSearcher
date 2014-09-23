package com.kemo.imagersearcher;

/**
 * Created by kemo on 9/23/14.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
// ...

public class EditSettingDialog extends DialogFragment {

    private Spinner spImageType;
    private Spinner spColorFilter;
    private Spinner spImageSize;
    private EditText etSite;
    private Button btSave;
    private Setting setting;
    private EditSettingDialogListener editSettingDialogListener;

    public EditSettingDialog() {
        // Empty constructor required for DialogFragment
    }

    View.OnClickListener onClickListener=
            new View.OnClickListener(){
                @Override public void onClick(View view){
                    setting.siteFilter = etSite.getText().toString();
                    editSettingDialogListener.onFinishEditDialog(setting);
                    dismiss();
                }
            };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            editSettingDialogListener = (EditSettingDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement EditSettingDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        editSettingDialogListener = null;
    }

    public interface EditSettingDialogListener {
        void onFinishEditDialog(Setting newSetting);
    }

    public static EditSettingDialog newInstance(String title) {
        EditSettingDialog frag = new EditSettingDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container);

        setUpView(view);
        addItemsOnImageTypeSpinner();
        addItemsOnColorFilterSpinner();
        addItemsOnImageSizeSpinner();


        //etSite.setOnEditorActionListener(this);

        setting = new Setting();

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

        String title = getArguments().getString("title");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
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

    private void setUpView(View view) {
        spImageType = (Spinner)view.findViewById(R.id.spImageType);
        spImageSize = (Spinner)view.findViewById(R.id.spImageSize);
        spColorFilter = (Spinner)view.findViewById(R.id.spColorFilter);
        etSite = (EditText)view.findViewById(R.id.etSite);
        btSave = (Button)view.findViewById(R.id.btSave);
        btSave.setOnClickListener(onClickListener);
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

//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (EditorInfo.IME_ACTION_DONE == actionId) {
//
//            // Return input text to activity
////            EditSettingDialogListener listener = (EditSettingDialogListener) getActivity();
////            listener.onFinishEditDialog(etSite.getText().toString());
//            dismiss();
//            return true;
//        }
//        return false;
//    }

//    public void onSaveSetting() {
//        setting.siteFilter = etSite.getText().toString();
//        EditSettingDialogListener listener = (EditSettingDialogListener) getActivity();
//        listener.onFinishEditDialog(setting);
//        dismiss();
//    }

//    public void onSaveSetting(View v) {
//        Intent intent = new Intent(this, SearchActivity.class);
//        setting.siteFilter = etSite.getText().toString();
//        intent.putExtra("setting", setting);
//        setResult(RESULT_OK, intent);
//        finish();
//    }
}