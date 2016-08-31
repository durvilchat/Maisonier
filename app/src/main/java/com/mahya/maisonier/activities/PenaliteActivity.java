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
import com.mahya.maisonier.adapter.model.PenaliteAdapter;
import com.mahya.maisonier.entites.Mois;
import com.mahya.maisonier.entites.Occupation;
import com.mahya.maisonier.entites.Penalite;
import com.mahya.maisonier.entites.Penalite_Table;
import com.mahya.maisonier.entites.TypeLogement_Table;
import com.mahya.maisonier.entites.TypePenalite;
import com.mahya.maisonier.interfaces.CrudActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;
import com.mahya.maisonier.utils.MyRecyclerScroll;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

import static com.mahya.maisonier.utils.Utils.currentDate;

public class PenaliteActivity extends BaseActivity implements CrudActivity, SearchView.OnQueryTextListener,
        OnItemClickListener {


    private static final String TAG = PenaliteActivity.class.getSimpleName();
    protected RecyclerView mRecyclerView;
    PenaliteAdapter mAdapter;
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
        Penalite.penalites.clear();
        Penalite.penalites = Penalite.findAll();
        setTitle(context.getString(R.string.penalite));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        fab.startAnimation(animation);

        mAdapter = new PenaliteAdapter(this, (ArrayList<Penalite>) Penalite.findAll(), this);
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
        if (Penalite.findAll().isEmpty()) {
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
        dialog.setContentView(R.layout.add_penalites);
        // Initialisation du formulaire


        final TextView operation = (TextView) dialog.findViewById(R.id.operation);
        final Spinner occupation = (Spinner) dialog.findViewById(R.id.Occupation);
        final Spinner typepenalite = (Spinner) dialog.findViewById(R.id.TypeDePenalite);
        final Spinner mois = (Spinner) dialog.findViewById(R.id.Mois);
        final EditText Montant = (EditText) dialog.findViewById(R.id.Montant);
        final EditText MontantPaye = (EditText) dialog.findViewById(R.id.MontantPaye);
        final EditText DateDeDePaiement = (EditText) dialog.findViewById(R.id.DateDeDePaiement);
        final MaterialBetterSpinner Observation = (MaterialBetterSpinner) dialog.findViewById(R.id.Observation);
        Button dateSelectEntree = (Button) dialog.findViewById(R.id.dateSelectEntree);
        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);

        final HintSpinner logementHint = new HintSpinner<>(
                occupation,
                new HintAdapter<Occupation>(context, "Occupation ", com.mahya.maisonier.entites.Occupation.findAll()),
                new HintSpinner.Callback<Occupation>() {


                    @Override
                    public void onItemSelected(int position, Occupation itemAtPosition) {


                    }
                });
        logementHint.init();


        final HintSpinner typePeHint = new HintSpinner<>(
                typepenalite,
                new HintAdapter<TypePenalite>(context, "Type de pénalité ", com.mahya.maisonier.entites.TypePenalite.findAll()),
                new HintSpinner.Callback<TypePenalite>() {


                    @Override
                    public void onItemSelected(int position, TypePenalite itemAtPosition) {


                    }
                });
        typePeHint.init();

        final HintSpinner MoisPeHint = new HintSpinner<>(
                mois,
                new HintAdapter<com.mahya.maisonier.entites.Mois>(context, "Mois ", com.mahya.maisonier.entites.Mois.findAll()),
                new HintSpinner.Callback<Mois>() {


                    @Override
                    public void onItemSelected(int position, Mois itemAtPosition) {


                    }
                });
        MoisPeHint.init();
        List<String> strings = new ArrayList<>();
        strings.add("Incomplet");
        strings.add("Complet");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, strings);
        Observation.setAdapter(stringArrayAdapter);


        dateSelectEntree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.dialog_date);
                final DatePicker datePicker = (DatePicker) dialog1.findViewById(R.id.datePicker);
                Button changeDate = (Button) dialog1.findViewById(R.id.selectDatePicker);

                DateDeDePaiement.setText(currentDate(datePicker));
                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DateDeDePaiement.setText(currentDate(datePicker));
                    }
                });
                dialog1.show();

                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DateDeDePaiement.setText(currentDate(datePicker));
                        dialog1.dismiss();
                    }
                });


            }
        });
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (occupation.getSelectedItemPosition() < 0) {
                    Toast.makeText(context, " Veillez sélectionner une occupation", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (typepenalite.getSelectedItem() == null) {
                    Toast.makeText(context, " Veillez sélectionner un type de pénalitée", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (mois.getSelectedItem() == null) {
                    Toast.makeText(context, " Veillez sélectionner un mois", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (Montant.getText().toString().trim().equals("")) {
                    Montant.setError("Velliez remplir le montant");
                    return;

                }
                if (MontantPaye.getText().toString().trim().equals("")) {
                    MontantPaye.setError("Velliez remplir le montant payé");
                    return;

                }
                if (DateDeDePaiement.getText().toString().trim().equals("")) {
                    DateDeDePaiement.setError("Velliez remplir la date de paimentr");
                    return;

                }
                if (Observation.getText().toString().trim().equals("")) {
                    Observation.setError("Velliez remplir la date de paimentr");
                    return;

                }

                Penalite penalite = new Penalite();
                try {
                    penalite.setDatePaiement(sdf.parse(DateDeDePaiement.getText().toString().trim()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                penalite.setMontant(Double.parseDouble(Montant.getText().toString()));
                penalite.setMontantPayer(Double.parseDouble(MontantPaye.getText().toString()));
                penalite.setObservation(Observation.getText().toString());
                penalite.assoOccupation((Occupation) occupation.getSelectedItem());
                penalite.assoMois((Mois) mois.getSelectedItem());
                penalite.assoTypePenalite((TypePenalite) typepenalite.getSelectedItem());
                try {
                    penalite.save();
                    if (Penalite.findAll().isEmpty()) {
                        mRecyclerView.setVisibility(View.GONE);
                        tvEmptyView.setVisibility(View.VISIBLE);

                    } else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.GONE);
                    }

                    Snackbar.make(view, "la pénalité a été correctement crée", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                   mAdapter.addItem(penalite, mAdapter.getItemCount()+1);
                } catch (Exception e) {
                    Snackbar.make(view, "echec", Snackbar.LENGTH_LONG)
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
        // Delete.tables(Penalite.class);
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

                            Penalite typeLogement = new Penalite();
                            typeLogement.setId(id);
                            typeLogement.delete();

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
        final Penalite typeLogement = SQLite.select().from(Penalite.class).where(TypeLogement_Table.id.eq(id)).querySingle();


    }

    @Override
    public void modifier(final int id) {

        final Penalite penalite = SQLite.select().from(Penalite.class).where(Penalite_Table.id.eq(id)).querySingle();
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_penalites);
        // Initialisation du formulaire


        final TextView operation = (TextView) dialog.findViewById(R.id.operation);
        final Spinner occupation = (Spinner) dialog.findViewById(R.id.Occupation);
        final Spinner typepenalite = (Spinner) dialog.findViewById(R.id.TypeDePenalite);
        final Spinner mois = (Spinner) dialog.findViewById(R.id.Mois);
        final EditText Montant = (EditText) dialog.findViewById(R.id.Montant);
        final EditText MontantPaye = (EditText) dialog.findViewById(R.id.MontantPaye);
        final EditText DateDeDePaiement = (EditText) dialog.findViewById(R.id.DateDeDePaiement);
        final MaterialBetterSpinner Observation = (MaterialBetterSpinner) dialog.findViewById(R.id.Observation);
        Button dateSelectEntree = (Button) dialog.findViewById(R.id.dateSelectEntree);
        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);


        DateDeDePaiement.setText(sdf.format(penalite.getDatePaiement()));
        MontantPaye.setText(String.valueOf(penalite.getMontantPayer()));
        Montant.setText(String.valueOf(penalite.getMontant()));

        final HintSpinner logementHint = new HintSpinner<>(
                occupation,
                new HintAdapter<Occupation>(context, "Occupation ", com.mahya.maisonier.entites.Occupation.findAll()),
                new HintSpinner.Callback<Occupation>() {


                    @Override
                    public void onItemSelected(int position, Occupation itemAtPosition) {


                    }
                });
        logementHint.init();


        final HintSpinner typePeHint = new HintSpinner<>(
                typepenalite,
                new HintAdapter<TypePenalite>(context, "Type de pénalité ", com.mahya.maisonier.entites.TypePenalite.findAll()),
                new HintSpinner.Callback<TypePenalite>() {


                    @Override
                    public void onItemSelected(int position, TypePenalite itemAtPosition) {


                    }
                });
        typePeHint.init();

        final HintSpinner MoisPeHint = new HintSpinner<>(
                mois,
                new HintAdapter<com.mahya.maisonier.entites.Mois>(context, "Mois ", com.mahya.maisonier.entites.Mois.findAll()),
                new HintSpinner.Callback<Mois>() {


                    @Override
                    public void onItemSelected(int position, Mois itemAtPosition) {


                    }
                });
        MoisPeHint.init();
        List<String> strings = new ArrayList<>();
        strings.add("Incomplet");
        strings.add("Complet");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, strings);
        Observation.setAdapter(stringArrayAdapter);


        dateSelectEntree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.dialog_date);
                final DatePicker datePicker = (DatePicker) dialog1.findViewById(R.id.datePicker);
                Button changeDate = (Button) dialog1.findViewById(R.id.selectDatePicker);

                DateDeDePaiement.setText(currentDate(datePicker));
                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DateDeDePaiement.setText(currentDate(datePicker));
                    }
                });
                dialog1.show();

                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DateDeDePaiement.setText(currentDate(datePicker));
                        dialog1.dismiss();
                    }
                });


            }
        });
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (occupation.getSelectedItemPosition() < 0) {
                    Toast.makeText(context, " Veillez sélectionner une occupation", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (typepenalite.getSelectedItem() == null) {
                    Toast.makeText(context, " Veillez sélectionner un type de pénalitée", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (mois.getSelectedItem() == null) {
                    Toast.makeText(context, " Veillez sélectionner un mois", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (Montant.getText().toString().trim().equals("")) {
                    Montant.setError("Velliez remplir le montant");
                    return;

                }
                if (MontantPaye.getText().toString().trim().equals("")) {
                    MontantPaye.setError("Velliez remplir le montant payé");
                    return;

                }
                if (DateDeDePaiement.getText().toString().trim().equals("")) {
                    DateDeDePaiement.setError("Velliez remplir la date de paimentr");
                    return;

                }
                if (Observation.getText().toString().trim().equals("")) {
                    Observation.setError("Velliez remplir la date de paimentr");
                    return;

                }

                Penalite penalite = new Penalite();
                penalite.setId(id);
                try {
                    penalite.setDatePaiement(sdf.parse(DateDeDePaiement.getText().toString().trim()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                penalite.setMontant(Double.parseDouble(Montant.getText().toString()));
                penalite.setMontantPayer(Double.parseDouble(MontantPaye.getText().toString()));
                penalite.setObservation(Observation.getText().toString());
                penalite.assoOccupation((Occupation) occupation.getSelectedItem());
                penalite.assoMois((Mois) mois.getSelectedItem());
                penalite.assoTypePenalite((TypePenalite) typepenalite.getSelectedItem());
                try {
                    penalite.save();

                    Snackbar.make(v, "la pénalité a été correctement modifié", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                    mAdapter.actualiser(Penalite.findAll());
                } catch (Exception e) {
                    Snackbar.make(v, "echec", Snackbar.LENGTH_LONG)
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
        final List<Penalite> filteredModelList = filter(Penalite.findAll(), query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<Penalite> filter(List<Penalite> models, String query) {
        query = query.toLowerCase();
        System.out.println(models);
        final List<Penalite> filteredModelList = new ArrayList<>();
        for (Penalite model : models) {
            final String text = model.getObservation().toLowerCase();
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
