package com.mahya.maisonier.activities;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
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
import com.github.clans.fab.FloatingActionMenu;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.mahya.maisonier.PdfRegion;
import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.DividerItemDecoration;
import com.mahya.maisonier.adapter.model.LogementAdapter;
import com.mahya.maisonier.entites.Batiment;
import com.mahya.maisonier.entites.Logement;
import com.mahya.maisonier.entites.Logement_Table;
import com.mahya.maisonier.entites.TypeLogement;
import com.mahya.maisonier.interfaces.CrudActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;
import com.mahya.maisonier.utils.MyRecyclerScroll;
import com.mahya.maisonier.utils.PermissionUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

import static com.mahya.maisonier.utils.Utils.currentDate;

public class LogementActivity extends BaseActivity implements CrudActivity, SearchView.OnQueryTextListener,
        OnItemClickListener  {

    private static final String TAG = LogementActivity.class.getSimpleName();
    protected RecyclerView mRecyclerView;
    Button changeDate;
    LogementAdapter mAdapter;
    FrameLayout fab;
    FloatingActionButton myfab_main_btn;
    Animation animation;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;
    private Context context = this;
    private TextView tvEmptyView;
    private FloatingActionButton print;
    private FloatingActionButton sms;
    private FloatingActionButton email;
    private FloatingActionMenu menuAction;

    private static void addContent(Document document) throws DocumentException {
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                Font.BOLD);
        Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                Font.BOLD);


    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setAllowReturnTransitionOverlap(true);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementExitTransition(new ChangeTransform());
        animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        super.setContentView(R.layout.activity_model1);
        setTitle(context.getString(R.string.Logement));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        fab.startAnimation(animation);
        mAdapter = new LogementAdapter(this, (ArrayList<Logement>) Logement.findAll(), this);
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
        if (Logement.findAll().isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }

     /*   print = (FloatingActionButton) findViewById(R.id.print);
        print*//**//*.setOnClickListener(this);
        sms = (FloatingActionButton) findViewById(R.id.sms);
        sms.setOnClickListener(this);
        email = (FloatingActionButton) findViewById(R.id.email);
        email.setOnClickListener(this);
        menuAction = (FloatingActionMenu) findViewById(R.id.menuAction);
        menuAction.setOnClickListener(this);*/
    }

    public void action(final View view) {
        switch (view.getId()) {
            case R.id.myfab_main_btn:
                ajouter(view);
                break;
            case R.id.print:
                viewPdf(new PdfRegion(this).etatsRegion(Logement.findAll()));
                break;
        }
    }

    @Override
    public void ajouter(final View view) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_logement);
        // Initialisation du formulaire

        final Spinner type = (Spinner) dialog.findViewById(R.id.TypeDeLogement);
        final Spinner batiment = (Spinner) dialog.findViewById(R.id.Batiment);
        final EditText ref = (EditText) dialog.findViewById(R.id.Reference);
        final EditText prixMin = (EditText) dialog.findViewById(R.id.PrixMin);
        final EditText priwMax = (EditText) dialog.findViewById(R.id.PrixMax);
        final EditText desc = (EditText) dialog.findViewById(R.id.Description);
        final EditText date = (EditText) dialog.findViewById(R.id.date);
        final Button selectDate = (Button) dialog.findViewById(R.id.dateSelect);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.dialog_date);
                final DatePicker datePicker = (DatePicker) dialog1.findViewById(R.id.datePicker);
                changeDate = (Button) dialog1.findViewById(R.id.selectDatePicker);

                date.setText(currentDate(datePicker));
                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        date.setText(currentDate(datePicker));
                    }
                });
                dialog1.show();

                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        date.setText(currentDate(datePicker));
                        dialog1.dismiss();
                    }
                });
            }
        });

        final HintSpinner batimentHint = new HintSpinner<>(
                batiment,
                new HintAdapter<Batiment>(this, "Batiment", Batiment.findAll()),
                new HintSpinner.Callback<Batiment>() {


                    @Override
                    public void onItemSelected(int position, Batiment itemAtPosition) {
                    }
                });
        batimentHint.init();

        final HintSpinner typehint = new HintSpinner<>(
                type,
                new HintAdapter<TypeLogement>(this, "Type de logement", TypeLogement.findAll()),
                new HintSpinner.Callback<TypeLogement>() {


                    @Override
                    public void onItemSelected(int position, TypeLogement itemAtPosition) {
                    }
                });
        typehint.init();


        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ref.getText().toString().trim().equals("")) {
                    ref.setError("Velliez remplir le nom");
                    return;

                }
                if (prixMin.getText().toString().trim().equals("")) {
                    prixMin.setError("Velliez remplir le prix min");
                    return;

                }
                if (priwMax.getText().toString().trim().equals("")) {
                    priwMax.setError("Velliez remplir le prix max");
                    return;

                }
                if (desc.getText().toString().trim().equals("")) {
                    desc.setError("Velliez remplir la description");
                    return;

                }
                if (batiment.getSelectedItem().toString().trim().equals("")) {
                    // bailleur.setEr("Velliez remplir le code");
                    return;

                }

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    Logement logement = new Logement();
                    logement.setReference(ref.getText().toString().trim());
                    logement.setPrixMax(Double.parseDouble(priwMax.getText().toString().trim()));
                    logement.setPrixMin(Double.parseDouble(prixMin.getText().toString().trim()));
                    logement.setDescription(desc.getText().toString().trim());
                    logement.setDatecreation(formatter.parse(date.getText().toString()));

                    logement.assoBatiment((Batiment) batiment.getSelectedItem());
                    logement.assoTypeLogement((TypeLogement) type.getSelectedItem());

                    logement.save();
                    Snackbar.make(view, "la logement a été correctement crée", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                    mAdapter.addItem(0, logement);
                    if (Logement.findAll().isEmpty()) {
                        mRecyclerView.setVisibility(View.GONE);
                        tvEmptyView.setVisibility(View.VISIBLE);

                    } else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.GONE);
                    }

                }catch (android.database.sqlite.SQLiteConstraintException e) {


                    Snackbar.make(v, "Logement déja existant", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } catch (Exception e) {
                    Snackbar.make(view, "echec d'enregistremment", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();;
                }



                dialog.dismiss();
            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.setText("");
                desc.setText("");
                priwMax.setText("");
                prixMin.setText("");

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
        // Delete.tables(Logement.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.action_settings) {

            if (Build.VERSION.SDK_INT >= 23)
                if (!PermissionUtils.checkAndRequestPermission(LogementActivity.this, REQUEST_CODE_ASK_PERMISSIONS, "You need to grant access to Write Storage", permission[0]))

                    isPDFFromHTML = false;
            viewPdf(new PdfRegion(this).etatsRegion(Logement.findAll()));
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

                            Logement logement = new Logement();
                            logement.setId(id);
                            logement.delete();

                        } catch (Exception e) {

                        }

                        mAdapter.deleteItem(mAdapter.getSelectposition());


                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }




    private void emailNote(File myFile) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_SUBJECT, "rostapig@gmail.com");
        email.putExtra(Intent.EXTRA_TEXT, "rostapig@gmail.com");
        Uri uri = Uri.parse(myFile.getAbsolutePath());
        email.putExtra(Intent.EXTRA_STREAM, uri);
        email.setType("message/rfc822");
        startActivity(email);
    }

    private void viewPdf(File myFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(myFile), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
/*

    private void promptForNextAction()
    {
        final String[] options = { getString(R.string.label_email), getString(R.string.label_preview),
                getString(R.string.label_cancel) };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Note Saved, What Next?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals(getString(R.string.label_email))){
                    emailNote();
                }else if (options[which].equals(getString(R.string.label_preview))){
                    viewPdf();
                }else if (options[which].equals(getString(R.string.label_cancel))){
                    dialog.dismiss();
                }
            }
        });

        builder.show();

    }*/

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();

        } else {
            if (count == 1) {

                actionMode.getMenu().findItem(R.id.caract).setVisible(true);

            } else {

                actionMode.getMenu().findItem(R.id.caract).setVisible(false);
            }
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
        final Logement logement = SQLite.select().from(Logement.class).where(Logement_Table.id.eq(id)).querySingle();
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.aff_logement);


    }

    @Override
    public void modifier(final int id) {

        final Logement logemen = SQLite.select().from(Logement.class).where(Logement_Table.id.eq(id)).querySingle();
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_logement);
        // Initialisation du formulaire

        final Spinner type = (Spinner) dialog.findViewById(R.id.TypeDeLogement);
        final Spinner batiment = (Spinner) dialog.findViewById(R.id.Batiment);
        final EditText ref = (EditText) dialog.findViewById(R.id.Reference);
        final EditText prixMin = (EditText) dialog.findViewById(R.id.PrixMin);
        final EditText priwMax = (EditText) dialog.findViewById(R.id.PrixMax);
        final EditText desc = (EditText) dialog.findViewById(R.id.Description);
        ref.setText(logemen.getReference());
        priwMax.setText(String.valueOf(logemen.getPrixMax()));
        prixMin.setText(String.valueOf(logemen.getPrixMin()));

        final HintSpinner batimentHint = new HintSpinner<>(
                batiment,
                new HintAdapter<Batiment>(this, "Batiment", Batiment.findAll()),
                new HintSpinner.Callback<Batiment>() {


                    @Override
                    public void onItemSelected(int position, Batiment itemAtPosition) {
                    }
                });
        batimentHint.init();

        final HintSpinner typehint = new HintSpinner<>(
                type,
                new HintAdapter<TypeLogement>(this, "Type de logement", TypeLogement.findAll()),
                new HintSpinner.Callback<TypeLogement>() {


                    @Override
                    public void onItemSelected(int position, TypeLogement itemAtPosition) {
                    }
                });
        typehint.init();
        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ref.getText().toString().trim().equals("")) {
                    ref.setError("Velliez remplir le nom");
                    return;

                }
                if (prixMin.getText().toString().trim().equals("")) {
                    prixMin.setError("Velliez remplir le prix min");
                    return;

                }
                if (priwMax.getText().toString().trim().equals("")) {
                    priwMax.setError("Velliez remplir le prix max");
                    return;

                }
                if (desc.getText().toString().trim().equals("")) {
                    desc.setError("Velliez remplir la description");
                    return;

                }
                if (batiment.getSelectedItem().toString().trim().equals("")) {
                    // bailleur.setEr("Velliez remplir le code");
                    return;

                }

                Logement logement = new Logement();
                logement.setId(logemen.getId());
                logement.setReference(ref.getText().toString().trim());
                logement.setPrixMax(Double.parseDouble(priwMax.getText().toString().trim()));
                logement.setPrixMin(Double.parseDouble(prixMin.getText().toString().trim()));
                logement.setDescription(desc.getText().toString().trim());
                logement.assoBatiment((Batiment) batiment.getSelectedItem());
                logement.assoTypeLogement((TypeLogement) type.getSelectedItem());

                try {
                    logement.save();

                    Snackbar.make(v, "le logement a été correctement modifié", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mAdapter.actualiser(Logement.findAll());
                }  catch (android.database.sqlite.SQLiteConstraintException e) {


                    Snackbar.make(v, "Logement  déjà existant", Snackbar.LENGTH_LONG)
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

                ref.setText("");
                desc.setText("");
                priwMax.setText("");
                prixMin.setText("");

                dialog.dismiss();

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
        final List<Logement> filteredModelList = filter(Logement.findAll(), query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<Logement> filter(List<Logement> models, String query) {
        query = query.toLowerCase();
        System.out.println(models);
        final List<Logement> filteredModelList = new ArrayList<>();
        for (Logement model : models) {
            final String text = model.getReference().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    public void ok(View v) {
        switch (v.getId()) {
            case R.id.print:
                viewPdf(new PdfRegion(this).etatsRegion(Logement.findAll()));
                break;
            case R.id.sms:

                break;
            case R.id.email:
                emailNote(new PdfRegion(this).etatsRegion(Logement.findAll()));
                break;
        }
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @SuppressWarnings("unused")
        private final String TAG = ActionModeCallback.class.getSimpleName();

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            mode.getMenuInflater().inflate(R.menu.menu_supp, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }


        @Override
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
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
                case R.id.caract:
                    Intent intent = new Intent(context, CaracterisationActivity.class);
                    intent.putExtra("id", mAdapter.getId());
                    startActivity(intent);
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelection();
            actionMode = null;
        }
    }
}
