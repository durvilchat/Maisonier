package com.mahya.maisonier.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.DividerItemDecoration;
import com.mahya.maisonier.adapter.model.HabitantAdapter;
import com.mahya.maisonier.adapter.model.LoyerAdapter;
import com.mahya.maisonier.entites.Habitant;
import com.mahya.maisonier.entites.Loyer;
import com.mahya.maisonier.interfaces.OnItemClickListener;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class DossierHablitant extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {


    protected Button loyer;
    protected Button compte;
    protected Button depot;
    protected Button cable;
    protected Button prixUElect;
    protected Button prixUEau;
    protected Button facture;
    protected Button caution;
    protected Button Occupation;
    protected Button charge;
    protected RecyclerView habitant;
    protected RecyclerView dossier;
    Context context;

    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private android.support.v7.view.ActionMode actionMode;
    private HabitantAdapter mAdapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        super.setContentView(R.layout.dossierhabitant);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        dossier.setFilterTouchesWhenObscured(true);
        dossier.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        dossier.setLayoutManager(mLayoutManager);
        dossier.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));

        habitant.setHasFixedSize(true);
        LinearLayoutManager mLayoutManag = new LinearLayoutManager(this);
        habitant.setLayoutManager(mLayoutManag);
        habitant.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));

        mAdapter = new HabitantAdapter(this, Habitant.findAll(), this);
        habitant.setAdapter(mAdapter);

        dossier.setAdapter(new LoyerAdapter(this, Loyer.findAll(), this));

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

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loyer) {
            dossier.removeAllViews();
            dossier.setAdapter(new LoyerAdapter(this, Loyer.findAll(), this));
            System.out.println("alllooo");

        } else if (view.getId() == R.id.compte) {

        } else if (view.getId() == R.id.depot) {

        } else if (view.getId() == R.id.cable) {

        } else if (view.getId() == R.id.prixUElect) {

        } else if (view.getId() == R.id.prixUEau) {

        } else if (view.getId() == R.id.facture) {

        } else if (view.getId() == R.id.caution) {

        } else if (view.getId() == R.id.Occupation) {

        } else if (view.getId() == R.id.charge) {

        } else if (view.getId() == R.id.habitant) {

        } else if (view.getId() == R.id.dossier) {

        }
    }

    private void initView() {
        loyer = (Button) findViewById(R.id.loyer);
        loyer.setOnClickListener(DossierHablitant.this);
        compte = (Button) findViewById(R.id.compte);
        compte.setOnClickListener(DossierHablitant.this);
        depot = (Button) findViewById(R.id.depot);
        depot.setOnClickListener(DossierHablitant.this);
        cable = (Button) findViewById(R.id.cable);
        cable.setOnClickListener(DossierHablitant.this);
        prixUElect = (Button) findViewById(R.id.prixUElect);
        prixUElect.setOnClickListener(DossierHablitant.this);
        prixUEau = (Button) findViewById(R.id.prixUEau);
        prixUEau.setOnClickListener(DossierHablitant.this);
        facture = (Button) findViewById(R.id.facture);
        facture.setOnClickListener(DossierHablitant.this);
        caution = (Button) findViewById(R.id.caution);
        caution.setOnClickListener(DossierHablitant.this);
        Occupation = (Button) findViewById(R.id.Occupation);
        Occupation.setOnClickListener(DossierHablitant.this);
        charge = (Button) findViewById(R.id.charge);
        charge.setOnClickListener(DossierHablitant.this);
        habitant = (RecyclerView) findViewById(R.id.habitant);
        dossier = (RecyclerView) findViewById(R.id.dossier);
    }


    @Override
    public void onLongClick(View view, int position) {

    }


    @Override
    public void onItemClicked(int position) {
        if (actionMode != null) {
            toggleSelection(position);
        } else {


        }
    }

    @Override
    public boolean onItemLongClicked(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }

        toggleSelection(position);

        return true;
    }


    private class ActionModeCallback implements android.support.v7.view.ActionMode.Callback {
        @SuppressWarnings("unused")
        private final String TAG = ActionModeCallback.class.getSimpleName();

        @Override
        public boolean onCreateActionMode(android.support.v7.view.ActionMode mode, Menu menu) {

            mode.getMenuInflater().inflate(R.menu.menu_supp, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
            return false;
        }


        @Override
        public boolean onActionItemClicked(final android.support.v7.view.ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_supp:
                    new AlertDialog.Builder(context)
                            .setTitle("Avertissement")
                            .setMessage("Voulez vous vraimment supprimer ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    try {

                                        mAdapter.removeItems(mAdapter.getSelectedItems());
                                        mode.finish();

                                    } catch (Exception e) {

                                    }


                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();

                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {
            mAdapter.clearSelection();
            actionMode = null;
        }
    }
}