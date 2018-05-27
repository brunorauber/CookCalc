package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.bruno.cookcalc.Controller.ConfigController;
import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Model.ConfigModel;
import com.example.bruno.cookcalc.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ConfigView extends Activity {

    private Spinner spinner;
    private Map<String, String> configs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_view);

        ConfigController configController = new ConfigController();
        ConfigModel configModel = new ConfigModel(getBaseContext());

        spinner = (Spinner) findViewById(R.id.spinnerEstados);
        List<String> statesString = statesList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statesString);
        spinner.setAdapter(adapter);

        configs = configModel.listConfigs();
        if(configs.size() > 0){
            fillFields();
        }
    }

    public List<String> statesList(){
        List<String> statesString = new ArrayList<>();

        statesString.add("Selecione");
        statesString.add("AC");
        statesString.add("AL");
        statesString.add("AP");
        statesString.add("AM");
        statesString.add("BA");
        statesString.add("CE");
        statesString.add("DF");
        statesString.add("ES");
        statesString.add("GO");
        statesString.add("MA");
        statesString.add("MT");
        statesString.add("MS");
        statesString.add("MG");
        statesString.add("PA");
        statesString.add("PB");
        statesString.add("PR");
        statesString.add("PE");
        statesString.add("PI");
        statesString.add("RJ");
        statesString.add("RN");
        statesString.add("RS");
        statesString.add("RO");
        statesString.add("RR");
        statesString.add("SC");
        statesString.add("SP");
        statesString.add("SE");
        statesString.add("TO");
        return statesString;
    }

    public void saveConfigs(View v){
        ConfigModel configModel = new ConfigModel(getBaseContext());

        spinner = (Spinner) findViewById(R.id.spinnerEstados);
        String state = spinner.getSelectedItem().toString();
        if(configModel.configExists("state")){
            configModel.updateConfig("state", state);
        } else {
            configModel.insertConfig("state", state);
        }

        EditText text;

        text = (EditText) findViewById(R.id.editTextCity);
        if( text.getText().toString().length() >= 1 ){
        }
        String city = String.valueOf(text.getText());
        if(configModel.configExists("city")){
            configModel.updateConfig("city", city);
        } else {
            configModel.insertConfig("city", city);
        }

        text = (EditText) findViewById(R.id.editTextEmail);
        if( text.getText().toString().length() >= 1 ){
        }
        String email = String.valueOf(text.getText());
        if(configModel.configExists("email")){
            configModel.updateConfig("email", email.toLowerCase());
        } else {
            configModel.insertConfig("email", email.toLowerCase());
        }


        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupWage);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            return;
        } else{
            String wageType = null;
            if (checkedRadioButtonId == R.id.radioButtonMonth) {
                wageType = "month";
            } else if (checkedRadioButtonId == R.id.radioButtonHour) {
                wageType = "hour";
            }

            if(configModel.configExists("wageType")){
                configModel.updateConfig("wageType", wageType);
            } else {
                configModel.insertConfig("wageType", wageType);
            }

            text = (EditText) findViewById(R.id.editTextHourValue);
            if( text.getText().toString().length() >= 1 ){
            }
            Double hourValue = Double.parseDouble(String.valueOf(text.getText()));
            if(configModel.configExists("hourValue")){
                configModel.updateConfig("hourValue", hourValue.toString());
            } else {
                configModel.insertConfig("hourValue", hourValue.toString());
            }
        }
        finish();
    }
    public void returnToMain(View v){
        finish();
    }

    private void fillFields(){

        if(configs.containsKey("wageType")) {
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupWage);
            String wageType = configs.get("wageType").toString();
            switch (wageType) {
                case "month":
                    radioGroup.check(R.id.radioButtonMonth);
                    break;
                case "hour":
                    radioGroup.check(R.id.radioButtonHour);
                    break;
            }
        }
        EditText text;
        if(configs.containsKey("hourValue")){
            String hourValue = configs.get("hourValue").toString();
            text = (EditText) findViewById(R.id.editTextHourValue);
            text.setText(hourValue);
        }

        if(configs.containsKey("city")){
            String city = configs.get("city").toString();
            text = (EditText) findViewById(R.id.editTextCity);
            text.setText(city);
        }

        if(configs.containsKey("email")){
            String email = configs.get("email").toString();
            text = (EditText) findViewById(R.id.editTextEmail);
            text.setText(email);
        }

        if(configs.containsKey("state")){
            for (int i=0;i<spinner.getCount();i++){
                if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(configs.get("state"))){
                    spinner.setSelection(i);
                    break;
                }
            }


        }
    }


}