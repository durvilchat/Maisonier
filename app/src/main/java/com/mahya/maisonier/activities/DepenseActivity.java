package com.mahya.maisonier.activities;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.transition.ChangeTransform;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.DividerItemDecoration;
import com.mahya.maisonier.adapter.model.DepenseAdapter;
import com.mahya.maisonier.entites.Annee;
import com.mahya.maisonier.entites.Bailleur;
import com.mahya.maisonier.entites.Batiment;
import com.mahya.maisonier.entites.Cite;
import com.mahya.maisonier.entites.Depense;
import com.mahya.maisonier.entites.Depense_Table;
import com.mahya.maisonier.entites.Habitant;
import com.mahya.maisonier.entites.Logement;
import com.mahya.maisonier.entites.Mois;
import com.mahya.maisonier.interfaces.CrudActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;
import com.mahya.maisonier.utils.MyRecyclerScroll;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

import static com.mahya.maisonier.utils.Utils.currentDate;

public class DepenseActivity extends BaseActivity implements CrudActivity, SearchView.OnQueryTextListener,
        OnItemClickListener {


    private static final String TAG = DepenseActivity.class.getSimpleName();
    protected RecyclerView mRecyclerView;
    DepenseAdapter mAdapter;
    FrameLayout fab;
    FloatingActionButton myfab_main_btn;
    Animation animation;
    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private android.support.v7.view.ActionMode actionMode;
    private android.content.Context context = this;
    private TextView tvEmptyView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setAllowReturnTransitionOverlap(true);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementExitTransition(new ChangeTransform());
        animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        super.setContentView(R.layout.activity_model1);
        setTitle(context.getString(R.string.Depenses));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        fab.startAnimation(animation);
        mAdapter = new DepenseAdapter(this, (ArrayList<Depense>) Depense.findAll(), this);
        myfab_main_btn.hide(false);
        mRecyclerView.setAdapter(mAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myfab_main_btn.show(true);
                myfab_main_btn.setShowAnimation(AnimationUtils.loadAnimation(context, R.anim.show_from_bottom));
                myfab_main_btn.setHideAnimation(AnimationUtils.loadAnimation(context, R.anim.hide_to_bottom));
            }
        }, 300);
        mRecyclerView.addOnScrollListener(new MyRecyclerScroll() {
            @Override
            public void show() {
                myfab_main_btn.show(true);
            }

            @Override
            public void hide() {
                myfab_main_btn.hide(true);
            }
        });

    }

    private void initView() {

        fab = (FrameLayout) findViewById(R.id.myfab_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_item);
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView.setFilterTouchesWhenObscured(true);
        myfab_main_btn = (FloatingActionButton) findViewById(R.id.myfab_main_btn);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        if (Depense.findAll().isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }

    }

    public void action(final View view) {
        switch (view.getId()) {
            case R.id.myfab_main_btn:
                ajouter(view);
                break;
        }
    }

    @Override
    public void ajouter(final View view) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_depenses);
        // Initialisation du formulaire

        TextView mOperation = (TextView) dialog.findViewById(R.id.operation);
        final Spinner mAnnee = (Spinner) dialog.findViewById(R.id.Annee);
        final Spinner mMois = (Spinner) dialog.findViewById(R.id.Mois);
        final Spinner mCite = (Spinner) dialog.findViewById(R.id.Cite);
        final Spinner mBatiment = (Spinner) dialog.findViewById(R.id.Batiment);
        final Spinner mBailleur = (Spinner) dialog.findViewById(R.id.Bailleur);
        final EditText mMontant = (EditText) dialog.findViewById(R.id.Montant);
        final EditText mDescription = (EditText) dialog.findViewById(R.id.Description);
        final EditText mDesignation = (EditText) dialog.findViewById(R.id.Designation);
        final EditText mDateEnregistrement = (EditText) dialog.findViewById(R.id.DateEnregistrement);
        Button mDateSelect = (Button) dialog.findViewById(R.id.dateSelect);
        mMontant.setText(String.valueOf("0.0"));

        mBailleur.setAdapter(new ArrayAdapter<Bailleur>(this, R.layout.spinner_item, Bailleur.findAll()));
        mBatiment.setAdapter(new ArrayAdapter<Logement>(this, R.layout.spinner_item, Logement.findAll()));
        mMois.setAdapter(new ArrayAdapter<Mois>(this, R.layout.spinner_item, Mois.findAll()));
        mAnnee.setAdapter(new ArrayAdapter<Annee>(this, R.layout.spinner_item, Annee.findAll()));
        mCite.setAdapter(new ArrayAdapter<Cite>(this, R.layout.spinner_item, Cite.findAll()));

        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);

        mDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.dialog_date);
                final DatePicker datePicker = (DatePicker) dialog1.findViewById(R.id.datePicker);
                Button changeDate = (Button) dialog1.findViewById(R.id.selectDatePicker);

                mDateEnregistrement.setText(currentDate(datePicker));
                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDateEnregistrement.setText(currentDate(datePicker));
                    }
                });
                dialog1.show();

                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDateEnregistrement.setText(currentDate(datePicker));
                        dialog1.dismiss();
                    }
                });
            }
        });
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (mAnnee.getSelectedItem().equals(null)) {
                    Toast.makeText(context, "Veillez selectionner une Année", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (mMois.getSelectedItem().equals(null)) {
                    Toast.makeText(context, "Veillez selectionner un mois", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (mCite.getSelectedItem().equals(null)) {
                    Toast.makeText(context, "Veillez selectionner un cite", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (mBatiment.getSelectedItem().equals(null)) {
                    Toast.makeText(context, "Veillez selectionner un batiment", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (mBailleur.getSelectedItem().equals(null)) {
                    Toast.makeText(context, "Veillez selectionner un Bailleur", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (mDateEnregistrement.getText().toString().trim().isEmpty()) {
                    mDateEnregistrement.setError("Veillez selectionner une date");
                    return;

                }


                Depense depense = new Depense();
                depense.assoBatiment((Batiment) mBatiment.getSelectedItem());
                depense.assoBailleur((Bailleur) mBailleur.getSelectedItem());
                depense.setDesignation(mDesignation.getText().toString().trim());
                depense.assoMois((Mois) mMois.getSelectedItem());
                try {
                    depense.setDateEnregistrement(sdf.parse(mDateEnregistrement.getText().toString().trim()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                depense.setDescription(mDescription.getText().toString().trim());
                depense.setMontant(Double.parseDouble(mMontant.getText().toString().trim()));
                try {
                    depense.save();
                    if (Depense.findAll().isEmpty()) {
                        mRecyclerView.setVisibility(View.GONE);
                        tvEmptyView.setVisibility(View.VISIBLE);

                    } else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.GONE);
                    }

                    Snackbar.make(view, "la Depense a été correctement crée", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                    mAdapter.addItem(0, depense);
                } catch (android.database.sqlite.SQLiteConstraintException e) {


                    Snackbar.make(v, "Dépense déja existante", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } catch (Exception e) {
                    Snackbar.make(view, "echec d'enregistremment", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


                dialog.dismiss();
            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMontant.setText("");
                mDescription.setText("");

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        //FlowManager.destroy();
        // Delete.tables(Depense.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void supprimer(final int id) {

        new AlertDialog.Builder(this)
                .setTitle("Avertissement")
                .setMessage("Voulez vous vraimment supprimer ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {

                            Depense Depense = new Depense();
                            Depense.setId(id);
                            Depense.delete();

                        } catch (Exception e) {

                        }

                        mAdapter.deleteItem(mAdapter.getSelectposition());

                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
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

    @Override
    public void onLongClick(View view, int position) {

    }


    @Override
    public void detail(final int id) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.aff_depense);
        TextView Cite;
        TextView Batiment;
        TextView Mois;
        TextView Bailleur;
        TextView Montant;
        TextView Description;
        TextView Designation;
        TextView DateEnregistrement;

        Cite = (TextView) dialog.findViewById(R.id.Cite);
        Batiment = (TextView) dialog.findViewById(R.id.Batiment);
        Bailleur = (TextView) dialog.findViewById(R.id.Bailleur);
        Montant = (TextView) dialog.findViewById(R.id.Montant);
        Description = (TextView) dialog.findViewById(R.id.Description);
        Designation = (TextView) dialog.findViewById(R.id.Designation);
        DateEnregistrement = (TextView) dialog.findViewById(R.id.DateEnregistrement);
        final Depense depense = SQLite.select().from(Depense.class).where(Depense_Table.id.eq(id)).querySingle();

        // Initialisation du formulaire
        Button fermer = (Button) dialog.findViewById(R.id.fermer);
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Cite.setText(depense.getBatiment().load().getCite().load().getNomCite());
        Batiment.setText(depense.getBatiment().load().getNom());
        //  Mois.setText(depense.getMois().load().getMois());
        Bailleur.setText(depense.getBailleur().load().getNom() +" "+depense.getBailleur().load().getPrenom());
        Montant.setText(String.valueOf(depense.getMontant()));
        Description.setText(depense.getDescription());
        Designation.setText(depense.getDesignation());
        DateEnregistrement.setText(sdf.format(depense.getDateEnregistrement()));
        fermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

}

    @Override
    public void modifier(final int id) {

        final Depense depot = SQLite.select().from(Depense.class).where(Depense_Table.id.eq(id)).querySingle();
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_depots);
        // Initialisation du formulaire

        TextView operation = (TextView) dialog.findViewById(R.id.operation);
        final EditText montant = (EditText) dialog.findViewById(R.id.Montant);
        final EditText observation = (EditText) dialog.findViewById(R.id.Observation);
        Button dateSelect = (Button) dialog.findViewById(R.id.dateSelect);
        final Spinner habitant = (Spinner) dialog.findViewById(R.id.Habitant);
        final Spinner logement = (Spinner) dialog.findViewById(R.id.Logement);
        final Spinner mois = (Spinner) dialog.findViewById(R.id.Mois);
        final Spinner annee = (Spinner) dialog.findViewById(R.id.Annee);

        final HintSpinner habHint = new HintSpinner<>(
                habitant,
                new HintAdapter<Habitant>(this, "Habitant ", Habitant.findAll()),
                new HintSpinner.Callback<Habitant>() {


                    @Override
                    public void onItemSelected(int position, Habitant itemAtPosition) {


                    }
                });
        habHint.init();
        logement.setAdapter(new ArrayAdapter<Logement>(this, R.layout.spinner_item, Logement.findAll()));
        mois.setAdapter(new ArrayAdapter<Mois>(this, R.layout.spinner_item, Mois.findAll()));
        annee.setAdapter(new ArrayAdapter<Annee>(this, R.layout.spinner_item, Annee.findAll()));


        montant.setText(String.valueOf(depot.getMontant()));


        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (habitant.getSelectedItem().toString().trim().equals("")) {
                    Toast.makeText(context, "Veillez selectionner un habitant", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (logement.getSelectedItem().toString().trim().equals("")) {
                    Toast.makeText(context, "Veillez selectionner un logement", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (mois.getSelectedItem().toString().trim().equals("")) {
                    Toast.makeText(context, "Veillez selectionner un mois", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (annee.getSelectedItem().toString().trim().equals("")) {
                    Toast.makeText(context, "Veillez selectionner une année", Toast.LENGTH_SHORT).show();
                    return;

                }

                Depense depot = new Depense();

                try {
                    depot.save();


                    Snackbar.make(v, "la Depense a été correctement modifié", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                    mAdapter.addItem(0, depot);
                } catch (android.database.sqlite.SQLiteConstraintException e) {


                    Snackbar.make(v, "Dépense  déjà existante", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                catch (Exception e) {
                    Snackbar.make(v, "echec de la modification", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


                dialog.dismiss();
            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                montant.setText("");
                observation.setText("");

                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.model, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextChange(String query) {
        final List<Depense> filteredModelList = filter(Depense.findAll(), query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<Depense> filter(List<Depense> models, String query) {
        query = query.toLowerCase();
        System.out.println(models);
        final List<Depense> filteredModelList = new ArrayList<>();
        for (Depense model : models) {
            final String text = model.getBailleur().toString().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
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
