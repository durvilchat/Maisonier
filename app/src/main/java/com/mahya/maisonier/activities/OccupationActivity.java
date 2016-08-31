package com.mahya.maisonier.activities;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.DividerItemDecoration;
import com.mahya.maisonier.adapter.model.OccupationAdapter;
import com.mahya.maisonier.entites.Habitant;
import com.mahya.maisonier.entites.Logement;
import com.mahya.maisonier.entites.Occupation;
import com.mahya.maisonier.entites.Occupation_Table;
import com.mahya.maisonier.interfaces.CrudActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;
import com.mahya.maisonier.utils.Constants;
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


public class OccupationActivity extends BaseActivity implements CrudActivity, SearchView.OnQueryTextListener,
        OnItemClickListener {

    //keep track of camera capture intent
    static final int CAMERA_CAPTURE = 1;
    private static final String TAG = OccupationActivity.class.getSimpleName();
    //keep track of cropping intent
    final int PIC_CROP = 3;
    //keep track of gallery intent
    final int PICK_IMAGE_REQUEST = 2;
    protected RecyclerView mRecyclerView;
    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    OccupationAdapter mAdapter;
    FrameLayout fab;
    FloatingActionButton myfab_main_btn;
    Animation animation;
    ImageView photo;
    Button changeDate;
    Bitmap thePic = null;
    Habitant h = null;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private android.support.v7.view.ActionMode actionMode;
    private android.content.Context context = this;
    private TextView tvEmptyView;
    private boolean loading = false;
    private int page = 0;
    private Uri picUri;
    private int month;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setAllowReturnTransitionOverlap(true);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementExitTransition(new ChangeTransform());
        animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        super.setContentView(R.layout.activity_model1);

        setTitle(context.getString(R.string.Occupations));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        fab.startAnimation(animation);
        mAdapter = new OccupationAdapter(this, (ArrayList<Occupation>) Occupation.findAll(), this);
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
        if (Occupation.findAll().isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }

    }

    public void add(final View view) {
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
        dialog.setContentView(R.layout.add_occupation);
        // Initialisation du formulaire
        final Spinner habitant = (Spinner) dialog.findViewById(R.id.habitant);
        final Spinner logement = (Spinner) dialog.findViewById(R.id.logement);
        final EditText desc = (EditText) dialog.findViewById(R.id.desc);
        final EditText loyerBase = (EditText) dialog.findViewById(R.id.loyerBase);
        final EditText dateEntree = (EditText) dialog.findViewById(R.id.dateEntree);
        Button dateSelectEntree = (Button) dialog.findViewById(R.id.dateSelectEntree);
        final EditText dateSortie = (EditText) dialog.findViewById(R.id.DateSortie);
        Button dateSelectsort = (Button) dialog.findViewById(R.id.dateSelectsort);


        dateSelectEntree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.dialog_date);
                final DatePicker datePicker = (DatePicker) dialog1.findViewById(R.id.datePicker);
                changeDate = (Button) dialog1.findViewById(R.id.selectDatePicker);

                dateEntree.setText(currentDate(datePicker));
                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateEntree.setText(currentDate(datePicker));
                    }
                });
                dialog1.show();

                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateEntree.setText(currentDate(datePicker));
                        dialog1.dismiss();
                    }
                });


            }
        });
        dateSelectsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.dialog_date);
                final DatePicker datePicker = (DatePicker) dialog1.findViewById(R.id.datePicker);
                changeDate = (Button) dialog1.findViewById(R.id.selectDatePicker);

                dateSortie.setText(currentDate(datePicker));
                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateSortie.setText(currentDate(datePicker));
                    }
                });
                dialog1.show();

                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateSortie.setText(currentDate(datePicker));
                        dialog1.dismiss();
                    }
                });
            }
        });
        CheckBox paieEaucheckBox = (CheckBox) dialog.findViewById(R.id.paieEaucheckBox);
        final EditText indexEau = (EditText) dialog.findViewById(R.id.indexEau);
        paieEaucheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                indexEau.setEnabled(b);
            }
        });
        final CheckBox paieElectcheckBox = (CheckBox) dialog.findViewById(R.id.paieElectcheckBox);

        final EditText indexElect = (EditText) dialog.findViewById(R.id.indexElect);
        paieElectcheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                indexElect.setEnabled(b);
            }
        });
        final CheckBox paieCablecheckBox = (CheckBox) dialog.findViewById(R.id.paieCablecheckBox);

        final EditText indexCable = (EditText) dialog.findViewById(R.id.indexCable);
        paieCablecheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                indexCable.setEnabled(b);
            }
        });
        final ImageView imgBailleur = (ImageView) dialog.findViewById(R.id.imgBailleur);
        final TextView Nom = (TextView) dialog.findViewById(R.id.Nom);
        final TextView Prenom = (TextView) dialog.findViewById(R.id.Prenom);
        final TextView Titre = (TextView) dialog.findViewById(R.id.Titre);
        indexCable.setEnabled(false);
        indexEau.setEnabled(false);
        indexElect.setEnabled(false);
        indexCable.setText("0");
        indexElect.setText("0");
        indexEau.setText("0");
        final RadioGroup modereglement = (RadioGroup) dialog.findViewById(R.id.modereglement);
        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        final HintSpinner logementHint = new HintSpinner<>(
                logement,
                new HintAdapter<Logement>(context, "Logement ", Logement.findLogementDispo()),
                new HintSpinner.Callback<Logement>() {


                    @Override
                    public void onItemSelected(int position, Logement itemAtPosition) {


                    }
                });
        logementHint.init();
        final HintSpinner habitantHint = new HintSpinner<>(
                habitant,
                new HintAdapter<Habitant>(context, "Habitant ", Habitant.findAll()),
                new HintSpinner.Callback<Habitant>() {


                    @Override
                    public void onItemSelected(int position, Habitant itemAtPosition) {

                        h = itemAtPosition;
                        Nom.setText(h.getNom());
                        Prenom.setText(h.getPrenom());
                        Titre.setText(h.getTitre());
                        if (h.getPhoto() != null) {

                            imgBailleur.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + "" + h.getPhoto()));
                        }
                    }
                });
        habitantHint.init();
        // Click cancel to dismiss android custom dialog box
        final EditText finalDateSortie = dateSortie;
        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                int selectedId = modereglement.getCheckedRadioButtonId();
                RadioButton moderegre = (RadioButton) dialog.findViewById(selectedId);

                if (h == null) {
                    Toast.makeText(context, "Verifier selectionner un habitant", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (loyerBase.getText().toString().trim().isEmpty() || Integer.parseInt(loyerBase.getText().toString().trim()) < 1) {
                    loyerBase.setError("Verifier la valeur du loyer de base");
                    return;
                }
                if (dateEntree.getText().toString().trim().isEmpty() || finalDateSortie.getText().toString().isEmpty()) {
                    dateEntree.setError("Verifier les dates entrées");
                    finalDateSortie.setError("Verifier les dates entrées");
                    return;
                }

                Occupation occupation = new Occupation();
                occupation.assoLogement((Logement) logement.getSelectedItem());
                occupation.assoHabitant(h);
                occupation.setModePaiement(moderegre.getText().toString());
                occupation.setLoyerBase(Double.parseDouble(loyerBase.getText().toString().trim()));
                occupation.setForfaitEau(paieElectcheckBox.isChecked());
                occupation.setForfaitElectricte(paieElectcheckBox.isChecked());
                occupation.setPaieCable(paieCablecheckBox.isChecked());
                occupation.setDescription(desc.getText().toString().trim());
                try {
                    occupation.setDateEntree(sdf.parse(dateEntree.getText().toString()));
                    occupation.setDateSortie(sdf.parse(finalDateSortie.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(context, "Verifier la date d'entrée et la date de sortie", Toast.LENGTH_SHORT).show();
                }
                try {

                    occupation.save();
                    Snackbar.make(view, "l'occupation a été correctement crée", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                    mAdapter.addItem(0, occupation);
                } catch (android.database.sqlite.SQLiteConstraintException e) {


                    Snackbar.make(v, "Occupation déja existante", Snackbar.LENGTH_LONG)
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
        // Delete.tables(Occupation.class);
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

                            Occupation occupation = new Occupation();
                            occupation.setId(id);
                            occupation.delete();

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
        final Occupation occupation = SQLite.select().from(Occupation.class).where(Occupation_Table.id.eq(id)).querySingle();
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_occupation);
        // Initialisation du formulaire
        final Spinner habitant = (Spinner) dialog.findViewById(R.id.habitant);
        final Spinner logement = (Spinner) dialog.findViewById(R.id.logement);
        final EditText desc = (EditText) dialog.findViewById(R.id.desc);
        final EditText loyerBase = (EditText) dialog.findViewById(R.id.loyerBase);
        final EditText dateEntree = (EditText) dialog.findViewById(R.id.dateEntree);
        Button dateSelectEntree = (Button) dialog.findViewById(R.id.dateSelectEntree);
        final EditText dateSortie = (EditText) dialog.findViewById(R.id.DateSortie);
        Button dateSelectsort = (Button) dialog.findViewById(R.id.dateSelectsort);


        dateSelectEntree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.dialog_date);
                final DatePicker datePicker = (DatePicker) dialog1.findViewById(R.id.datePicker);
                changeDate = (Button) dialog1.findViewById(R.id.selectDatePicker);

                dateEntree.setText(currentDate(datePicker));
                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateEntree.setText(currentDate(datePicker));
                    }
                });
                dialog1.show();

                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateEntree.setText(currentDate(datePicker));
                        dialog1.dismiss();
                    }
                });


            }
        });
        dateSelectsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.dialog_date);
                final DatePicker datePicker = (DatePicker) dialog1.findViewById(R.id.datePicker);
                changeDate = (Button) dialog1.findViewById(R.id.selectDatePicker);

                dateSortie.setText(currentDate(datePicker));
                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateEntree.setText(currentDate(datePicker));
                    }
                });
                dialog1.show();

                changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateSortie.setText(currentDate(datePicker));
                        dialog1.dismiss();
                    }
                });
            }
        });
        CheckBox paieEaucheckBox = (CheckBox) dialog.findViewById(R.id.paieEaucheckBox);
        final EditText indexEau = (EditText) dialog.findViewById(R.id.indexEau);
        paieEaucheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                indexEau.setEnabled(b);
            }
        });
        final CheckBox paieElectcheckBox = (CheckBox) dialog.findViewById(R.id.paieElectcheckBox);

        final EditText indexElect = (EditText) dialog.findViewById(R.id.indexElect);
        paieElectcheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                indexElect.setEnabled(b);
            }
        });
        final CheckBox paieCablecheckBox = (CheckBox) dialog.findViewById(R.id.paieCablecheckBox);

        final EditText indexCable = (EditText) dialog.findViewById(R.id.indexCable);
        paieCablecheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                indexCable.setEnabled(b);
            }
        });
        final ImageView imgBailleur = (ImageView) dialog.findViewById(R.id.imgBailleur);
        final TextView Nom = (TextView) dialog.findViewById(R.id.Nom);
        final TextView Prenom = (TextView) dialog.findViewById(R.id.Prenom);
        final TextView Titre = (TextView) dialog.findViewById(R.id.Titre);
        indexCable.setEnabled(false);
        indexEau.setEnabled(false);
        indexElect.setEnabled(false);
        indexCable.setText("0");
        indexElect.setText("0");
        indexEau.setText("0");

        loyerBase.setText(String.valueOf(occupation.getLoyerBase()));
        dateEntree.setText(sdf.format(occupation.getDateEntree()));
        dateSortie.setText(sdf.format(occupation.getDateSortie()));
        paieCablecheckBox.setChecked(occupation.isPaieCable());
        paieElectcheckBox.setChecked(occupation.isPaieEau());
        paieEaucheckBox.setChecked(occupation.isPaieEau());

        final RadioGroup modereglement = (RadioGroup) dialog.findViewById(R.id.modereglement);
        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        final HintSpinner logementHint = new HintSpinner<>(
                logement,
                new HintAdapter<Logement>(context, "Logement ", Logement.findLogementDispo()),
                new HintSpinner.Callback<Logement>() {


                    @Override
                    public void onItemSelected(int position, Logement itemAtPosition) {


                    }
                });
        logementHint.init();
        final HintSpinner habitantHint = new HintSpinner<>(
                habitant,
                new HintAdapter<Habitant>(context, "Habitant ", Habitant.findAll()),
                new HintSpinner.Callback<Habitant>() {


                    @Override
                    public void onItemSelected(int position, Habitant itemAtPosition) {

                        h = itemAtPosition;
                        Nom.setText(h.getNom());
                        Prenom.setText(h.getPrenom());
                        Titre.setText(h.getTitre());
                        if (h.getPhoto() != null) {

                            imgBailleur.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + "" + h.getPhoto()));
                        }
                    }
                });
        habitantHint.init();
        // Click cancel to dismiss android custom dialog box
        final EditText finalDateSortie = dateSortie;
        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                int selectedId = modereglement.getCheckedRadioButtonId();
                RadioButton moderegre = (RadioButton) dialog.findViewById(selectedId);

                if (h == null) {
                    Toast.makeText(context, "Verifier selectionner un habitant", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (((Logement) logement.getSelectedItem()) == null) {
                    Toast.makeText(context, "Verifier selectionner un logement", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (loyerBase.getText().toString().trim().isEmpty() || Integer.parseInt(loyerBase.getText().toString().trim()) < 1) {
                    loyerBase.setError("Verifier la valeur du loyer de base");
                    return;
                }
                if (dateEntree.getText().toString().trim().isEmpty() || finalDateSortie.getText().toString().isEmpty()) {
                    dateEntree.setError("Verifier les dates entrées");
                    finalDateSortie.setError("Verifier les dates entrées");
                    return;
                }

                Occupation occupation = new Occupation();
                occupation.setId(id);
                occupation.assoLogement((Logement) logement.getSelectedItem());
                occupation.assoHabitant(h);
                occupation.setModePaiement(moderegre.getText().toString());
                occupation.setLoyerBase(Double.parseDouble(loyerBase.getText().toString().trim()));
                occupation.setForfaitEau(paieElectcheckBox.isChecked());
                occupation.setForfaitElectricte(paieElectcheckBox.isChecked());
                occupation.setPaieCable(paieCablecheckBox.isChecked());
                occupation.setDescription(desc.getText().toString().trim());
                try {
                    occupation.setDateEntree(sdf.parse(dateEntree.getText().toString()));
                    occupation.setDateSortie(sdf.parse(finalDateSortie.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(context, "Verifier la date d'entrée et la date de sortie", Toast.LENGTH_SHORT).show();
                }
                try {

                    occupation.save();
                    Snackbar.make(v, "l'occupation a été correctement modifié", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                    mAdapter.actualiser(Occupation.findAll());
                } catch (android.database.sqlite.SQLiteConstraintException e) {


                    Snackbar.make(v, "Occupation  déjà existante", Snackbar.LENGTH_LONG)
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
        final List<Occupation> filteredModelList = filter(Occupation.findAll(), query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Occupation> filter(List<Occupation> models, String query) {
        query = query.toLowerCase();
        System.out.println(models);
        final List<Occupation> filteredModelList = new ArrayList<>();
        for (Occupation model : models) {
            final String text = model.getHabitant().load().getNom().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //user is returning from capturing an image using the camera
            if (requestCode == CAMERA_CAPTURE) {
                //get the Uri for the captured image
                Uri uri = picUri;
                //carry out the crop operation
                performCrop();
                Log.d("picUri", uri.toString());

            } else if (requestCode == PICK_IMAGE_REQUEST) {
                picUri = data.getData();
                Log.d("uriGallery", picUri.toString());
                performCrop();
            }

            //user is returning from cropping the image
            else if (requestCode == PIC_CROP) {
                //get the returned data
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                thePic = (Bitmap) extras.get("data");
                //display the returned cropped image
                photo.setImageBitmap(thePic);

            }

        }
    }

    private void performCrop() {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "erreur";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
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
