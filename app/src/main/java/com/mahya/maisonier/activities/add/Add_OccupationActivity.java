package com.mahya.maisonier.activities.add;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mahya.maisonier.R;
import com.mahya.maisonier.entites.Habitant;
import com.mahya.maisonier.entites.Logement;
import com.mahya.maisonier.entites.Occupation;
import com.mahya.maisonier.utils.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

import static com.mahya.maisonier.utils.Utils.currentDate;

/**
 * Created by LARUMEUR on 12/08/2016.
 */

public class Add_OccupationActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    protected CheckBox paieEaucheckBox;
    protected EditText indexEau;
    protected CheckBox paieElectcheckBox;
    protected EditText indexElect;
    protected CheckBox paieCablecheckBox;
    protected EditText indexCable;
    protected RadioGroup modereglement;
    RadioButton modePrepaye;
    Button changeDate;
    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Spinner habitant;
    private ImageView imgBailleur;
    private TextView Nom;
    private TextView Prenom;
    private TextView Titre;
    private Spinner logement;
    private EditText desc;
    private RadioButton modePostpaye;
    private EditText loyerBase;
    private EditText dateEntree;
    private Button dateSelectEntree;
    private EditText DateSortie;
    private Button dateSelectsort;
    private Button valider;
    private Button annuler;
    private Logement mlogement = null;
    private Habitant h = null;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        super.setContentView(R.layout.add_occupation);

        initView();


        final HintSpinner logementHint = new HintSpinner<>(
                logement,
                new HintAdapter<Logement>(this, "Logement ", Logement.findAll()),
                new HintSpinner.Callback<Logement>() {


                    @Override
                    public void onItemSelected(int position, Logement itemAtPosition) {

                        mlogement = itemAtPosition;

                    }
                });
        logementHint.init();

        final HintSpinner habitantHint = new HintSpinner<>(
                habitant,
                new HintAdapter<Habitant>(this, "Habitant ", Habitant.findAll()),
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


    }


    private void initView() {
        habitant = (Spinner) findViewById(R.id.habitant);
        logement = (Spinner) findViewById(R.id.logement);
        desc = (EditText) findViewById(R.id.desc);
        modePostpaye = (RadioButton) findViewById(R.id.modePostpaye);
        modePrepaye = (RadioButton) findViewById(R.id.modePrepaye);
        loyerBase = (EditText) findViewById(R.id.loyerBase);
        dateEntree = (EditText) findViewById(R.id.dateEntree);
        dateSelectEntree = (Button) findViewById(R.id.dateSelectEntree);
        dateSelectEntree.setOnClickListener(Add_OccupationActivity.this);
        DateSortie = (EditText) findViewById(R.id.DateSortie);
        dateSelectsort = (Button) findViewById(R.id.dateSelectsort);
        dateSelectsort.setOnClickListener(Add_OccupationActivity.this);
        paieEaucheckBox = (CheckBox) findViewById(R.id.paieEaucheckBox);
        paieEaucheckBox.setOnCheckedChangeListener(Add_OccupationActivity.this);
        indexEau = (EditText) findViewById(R.id.indexEau);
        paieElectcheckBox = (CheckBox) findViewById(R.id.paieElectcheckBox);
        paieElectcheckBox.setOnCheckedChangeListener(Add_OccupationActivity.this);
        indexElect = (EditText) findViewById(R.id.indexElect);
        paieCablecheckBox = (CheckBox) findViewById(R.id.paieCablecheckBox);
        paieCablecheckBox.setOnCheckedChangeListener(Add_OccupationActivity.this);
        indexCable = (EditText) findViewById(R.id.indexCable);
        valider = (Button) findViewById(R.id.valider);
        valider.setOnClickListener(Add_OccupationActivity.this);
        annuler = (Button) findViewById(R.id.annuler);
        annuler.setOnClickListener(Add_OccupationActivity.this);
        imgBailleur = (ImageView) findViewById(R.id.imgBailleur);
        Nom = (TextView) findViewById(R.id.Nom);
        Prenom = (TextView) findViewById(R.id.Prenom);
        Titre = (TextView) findViewById(R.id.Titre);
        indexCable.setEnabled(false);
        indexEau.setEnabled(false);
        indexElect.setEnabled(false);
        indexCable.setText("0");
        indexElect.setText("0");
        indexEau.setText("0");
        modereglement = (RadioGroup) findViewById(R.id.modereglement);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.valider) {
            int selectedId = modereglement.getCheckedRadioButtonId();
            RadioButton moderegre = (RadioButton) findViewById(selectedId);

            if (h == null) {
                Toast.makeText(context, "Verifier selectionner un habitant", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mlogement == null) {
                Toast.makeText(context, "Verifier selectionner un logement", Toast.LENGTH_SHORT).show();
                return;
            }
            if (loyerBase.getText().toString().trim().isEmpty() || Integer.parseInt(loyerBase.getText().toString().trim()) < 1) {
                loyerBase.setError("Verifier la valeur du loyer de base");
                return;
            }
            if (dateEntree.getText().toString().trim().isEmpty() || DateSortie.getText().toString().isEmpty()) {
                dateEntree.setError("Verifier les dates entrées");
                DateSortie.setError("Verifier les dates entrées");
                return;
            }

            Occupation occupation = new Occupation();
            occupation.assoLogement(mlogement);
            occupation.assoHabitant(h);
            occupation.setModePaiement(moderegre.getText().toString());
            occupation.setLoyerBase(Double.parseDouble(loyerBase.getText().toString().trim()));
            occupation.setForfaitEau(paieElectcheckBox.isChecked());
            occupation.setForfaitElectricte(paieElectcheckBox.isChecked());
            occupation.setPaieCable(paieCablecheckBox.isChecked());
            occupation.setDescription(desc.getText().toString().trim());
            try {
                occupation.setDateEntree(sdf.parse(dateEntree.getText().toString()));
                occupation.setDateSortie(sdf.parse(DateSortie.getText().toString()));
            } catch (ParseException e) {
                Toast.makeText(context, "Verifier la date d'entrée et la date de sortie", Toast.LENGTH_SHORT).show();
            }
            occupation.save();

        } else if (view.getId() == R.id.annuler) {

        } else if (view.getId() == R.id.dateSelectEntree) {
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


        } else if (view.getId() == R.id.dateSelectsort) {

            final Dialog dialog1 = new Dialog(context);
            dialog1.setContentView(R.layout.dialog_date);
            final DatePicker datePicker = (DatePicker) dialog1.findViewById(R.id.datePicker);
            changeDate = (Button) dialog1.findViewById(R.id.selectDatePicker);

            DateSortie.setText(currentDate(datePicker));
            changeDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DateSortie.setText(currentDate(datePicker));
                }
            });
            dialog1.show();

            changeDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DateSortie.setText(currentDate(datePicker));
                    dialog1.dismiss();
                }
            });
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.paieEaucheckBox) {
            indexEau.setEnabled(b);
        } else if (compoundButton.getId() == R.id.paieElectcheckBox) {
            indexElect.setEnabled(b);
        } else if (compoundButton.getId() == R.id.paieCablecheckBox) {
            indexCable.setEnabled(b);

        }
    }
}
