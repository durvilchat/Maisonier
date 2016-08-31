package com.mahya.maisonier.activities.detail;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mahya.maisonier.R;
import com.mahya.maisonier.entites.Habitant;
import com.mahya.maisonier.entites.Habitant_Table;
import com.mahya.maisonier.fragments.AffHabitantFragment;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Aff_HabitantActivity extends AppCompatActivity {


    int id;
    private TextView mOperation;
    private EditText mEmail1;
    private EditText mEmail2;
    private EditText mTel2;
    private EditText mTel3;
    private EditText mTel4;
    private Button mValider;
    private Button mAnnuler;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.activity_user_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt("id",
                    getIntent().getIntExtra("id", 0));
            AffHabitantFragment fragment = new AffHabitantFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



    }

    public void addContact(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_contact);
        // Initialisation du formulaire


        mOperation = (TextView) dialog.findViewById(R.id.operation);
        mEmail1 = (EditText) dialog.findViewById(R.id.Email1);
        mEmail2 = (EditText) dialog.findViewById(R.id.Email2);
        mTel2 = (EditText) dialog.findViewById(R.id.tel2);
        mTel3 = (EditText) dialog.findViewById(R.id.tel3);
        mTel4 = (EditText) dialog.findViewById(R.id.tel4);
        mValider = (Button) dialog.findViewById(R.id.valider);
        mAnnuler = (Button) dialog.findViewById(R.id.annuler);

        // Click cancel to dismiss android custom dialog box
        mValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Habitant habitant = SQLite.select().from(Habitant.class).where(Habitant_Table.id.eq(id)).querySingle();
                habitant.setId(id);
                habitant.setEmail1(mEmail1.getText().toString().trim());
                habitant.setEmail2(mEmail2.getText().toString().trim());
                habitant.setTel2(mTel2.getText().toString().trim());
                habitant.setTel3(mTel3.getText().toString().trim());
                habitant.setTel4(mTel4.getText().toString().trim());
                habitant.update();
                dialog.dismiss();
            }
        });
        mAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
