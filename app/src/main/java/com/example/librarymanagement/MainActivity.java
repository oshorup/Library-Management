package com.example.librarymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.computer_science)
    Button computerScience;
    @BindView(R.id.electronics)
    Button electronics;
    @BindView(R.id.information_technology)
    Button informationTechnology;
    @BindView(R.id.software_engineering)
    Button softwareEngineering;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.computer_science, R.id.electronics, R.id.information_technology, R.id.software_engineering})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.computer_science:
                Intent ItBACOE = new Intent(MainActivity.this,BooksAvailable_COE.class);
                startActivity(ItBACOE);
                break;
            case R.id.electronics:
                break;
            case R.id.information_technology:
                break;
            case R.id.software_engineering:
                break;
        }
    }
}
