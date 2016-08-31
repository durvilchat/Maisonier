package com.mahya.maisonier.activities;


import android.annotation.TargetApi;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.DividerItemDecoration;
import com.mahya.maisonier.adapter.model.ContraBailAdapter;
import com.mahya.maisonier.entites.Bailleur;
import com.mahya.maisonier.entites.ContratBail;
import com.mahya.maisonier.entites.ContratBail_Table;
import com.mahya.maisonier.entites.Occupation;
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

@RequiresApi(api = Build.VERSION_CODES.N)
public class ContraBailActivity extends BaseActivity implements CrudActivity, SearchView.OnQueryTextListener,
        OnItemClickListener {


    private static final String TAG = ContraBailActivity.class.getSimpleName();
    protected RecyclerView mRecyclerView;
    ContraBailAdapter mAdapter;
    FrameLayout fab;
    FloatingActionButton myfab_main_btn;
    Animation animation;
    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Bailleur bailleur1;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private android.support.v7.view.ActionMode actionMode;
    private android.content.Context context = this;
    private TextView tvEmptyView;

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setAllowReturnTransitionOverlap(true);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementExitTransition(new ChangeTransform());
        animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        super.setContentView(R.layout.activity_model1);
        ContratBail.contratBails.clear();
        ContratBail.contratBails = ContratBail.findAll();

        setTitle(context.getString(R.string.Contratdebail));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        fab.startAnimation(animation);
        mAdapter = new ContraBailAdapter(this, (ArrayList<ContratBail>) ContratBail.findAll(), this);
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
        if (ContratBail.findAll().isEmpty()) {
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
        dialog.setContentView(R.layout.add_contrat_de_bail);
        // Initialisation du formulaire

        TextView operation = (TextView) dialog.findViewById(R.id.operation);
        final EditText dateEtablissement = (EditText) dialog.findViewById(R.id.DateEtablissement);
        Button dateSelect = (Button) dialog.findViewById(R.id.dateSelect);
        final Spinner bailleur = (Spinner) dialog.findViewById(R.id.Bailleur);
        Spinner occupation = (Spinner) dialog.findViewById(R.id.Occupation);


        operation.setText(context.getText(R.string.ajouterContrat));

        dateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.dialog_date);
                final DatePicker datePicker = (DatePicker) dialog1.findViewById(R.id.datePicker);
                Button changeDate = (Button) dialog1.findViewById(R.id.selectDatePicker);

                dateEtablissement.setText(currentDate(datePicker));
                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateEtablissement.setText(currentDate(datePicker));
                    }
                });
                dialog1.show();

                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateEtablissement.setText(currentDate(datePicker));
                        dialog1.dismiss();
                    }
                });
            }
        });

        final HintSpinner occupationHint = new HintSpinner<>(
                occupation,
                new HintAdapter<Occupation>(this, "Occupation ", Occupation.findAll()),
                new HintSpinner.Callback<Occupation>() {


                    @Override
                    public void onItemSelected(int position, Occupation itemAtPosition) {

                    }
                });
        occupationHint.init();


        final HintSpinner bailleurHint = new HintSpinner<>(
                bailleur,
                new HintAdapter<Bailleur>(this, "Bailleur ", Bailleur.findAll()),
                new HintSpinner.Callback<Bailleur>() {


                    @Override
                    public void onItemSelected(int position, Bailleur itemAtPosition) {
                    }
                });
        bailleurHint.init();

        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (dateEtablissement.getText().toString().trim().equals("")) {
                    dateEtablissement.setError("Velliez remplir selectionner une date");
                    return;

                }

                ContratBail contratBail = new ContratBail();
                //contratBail.assoOccupation();
                contratBail.assoBailleur((Bailleur) bailleur.getSelectedItem());
                try {
                    contratBail.setDateEtablissement(sdf.parse(dateEtablissement.getText().toString().trim()));
                } catch (ParseException e) {
                    Snackbar.make(view, "Veillez selectionner une date valide", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                }
                try {
                    contratBail.save();
                    if (ContratBail.findAll().isEmpty()) {
                        mRecyclerView.setVisibility(View.GONE);
                        tvEmptyView.setVisibility(View.VISIBLE);

                    } else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.GONE);
                    }

                    Snackbar.make(view, "la ContratBail a été correctement crée", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                    mAdapter.addItem(0, contratBail);
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

                dateEtablissement.setText("");

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
        // Delete.tables(ContratBail.class);
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

                            ContratBail ContratBail = new ContratBail();
                            ContratBail.setId(id);
                            ContratBail.delete();

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


    }

    @Override
    public void modifier(final int id) {

        final ContratBail bail = SQLite.select().from(ContratBail.class).where(ContratBail_Table.id.eq(id)).querySingle();
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_contrat_de_bail);
        // Initialisation du formulaire

        TextView operation = (TextView) dialog.findViewById(R.id.operation);
        final EditText dateEtablissement = (EditText) dialog.findViewById(R.id.DateEtablissement);
        Button dateSelect = (Button) dialog.findViewById(R.id.dateSelect);
        final Spinner bailleur = (Spinner) dialog.findViewById(R.id.Bailleur);
        Spinner occupation = (Spinner) dialog.findViewById(R.id.Occupation);


        operation.setText(context.getText(R.string.ajouterContrat));

        dateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.dialog_date);
                final DatePicker datePicker = (DatePicker) dialog1.findViewById(R.id.datePicker);
                Button changeDate = (Button) dialog1.findViewById(R.id.selectDatePicker);

                dateEtablissement.setText(currentDate(datePicker));
                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateEtablissement.setText(currentDate(datePicker));
                    }
                });
                dialog1.show();

                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateEtablissement.setText(currentDate(datePicker));
                        dialog1.dismiss();
                    }
                });
            }
        });

        final HintSpinner occupationHint = new HintSpinner<>(
                occupation,
                // Default layout - You don't need to pass in any layout id, just your hint text and
                // your list data
                new HintAdapter<Occupation>(this, "Occupation ", Occupation.findAll()),
                new HintSpinner.Callback<Occupation>() {


                    @Override
                    public void onItemSelected(int position, Occupation itemAtPosition) {
                    }
                });
        occupationHint.init();

        final Bailleur[] bailleur1 = new Bailleur[1];
        final HintSpinner bailleurHint = new HintSpinner<>(
                bailleur,
                // Default layout - You don't need to pass in any layout id, just your hint text and
                // your list data
                new HintAdapter<Bailleur>(this, "Bailleur ", Bailleur.findAll()),
                new HintSpinner.Callback<Bailleur>() {


                    @Override
                    public void onItemSelected(int position, Bailleur itemAtPosition) {
                        bailleur1[0] = itemAtPosition;
                    }
                });
        bailleurHint.init();

        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (dateEtablissement.getText().toString().trim().equals("")) {
                    dateEtablissement.setError("Velliez remplir selectionner une date");
                    return;

                }

                ContratBail contratBail = new ContratBail();
                contratBail.setId(id);
                contratBail.assoBailleur(bailleur1[0]);
                try {
                    contratBail.setDateEtablissement(sdf.parse(dateEtablissement.getText().toString().trim()));
                } catch (ParseException e) {
                    Snackbar.make(v, "Veillez selectionner une date valide", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                }
                try {
                    contratBail.save();
                    if (ContratBail.findAll().isEmpty()) {
                        mRecyclerView.setVisibility(View.GONE);
                        tvEmptyView.setVisibility(View.VISIBLE);

                    } else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.GONE);
                    }

                    Snackbar.make(v, "la ContratBail a été correctement modifié", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                    mAdapter.addItem(0, contratBail);
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

                dateEtablissement.setText("");

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
        final List<ContratBail> filteredModelList = filter(ContratBail.findAll(), query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<ContratBail> filter(List<ContratBail> models, String query) {
        query = query.toLowerCase();
        System.out.println(models);
        final List<ContratBail> filteredModelList = new ArrayList<>();
        for (ContratBail model : models) {
            final String text = model.getDateEtablissement().toString().toLowerCase();
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
