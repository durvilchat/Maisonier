package com.mahya.maisonier.activities;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.text.Html;
import android.transition.ChangeTransform;
import android.util.Log;
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
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.DividerItemDecoration;
import com.mahya.maisonier.adapter.model.LogementAdapter;
import com.mahya.maisonier.entites.Batiment;
import com.mahya.maisonier.entites.Logement;
import com.mahya.maisonier.entites.TypeLogement;
import com.mahya.maisonier.entites.TypeLogement_Table;
import com.mahya.maisonier.interfaces.CrudActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;
import com.mahya.maisonier.utils.Constants;
import com.mahya.maisonier.utils.MyRecyclerScroll;
import com.mahya.maisonier.utils.PermissionUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

import static com.mahya.maisonier.utils.Utils.currentDate;

public class LogementActivity extends BaseActivity implements CrudActivity, SearchView.OnQueryTextListener,
        OnItemClickListener {

    private static final String TAG = LogementActivity.class.getSimpleName();
    protected RecyclerView mRecyclerView;
    Button changeDate;
    LogementAdapter mAdapter;
    FrameLayout fab;
    FloatingActionButton myfab_main_btn;
    Animation animation;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private android.support.v7.view.ActionMode actionMode;
    private android.content.Context context = this;
    private TextView tvEmptyView;

    private static void addContent(Document document) throws DocumentException {
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                Font.BOLD);
        Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                Font.BOLD);


    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);
        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Reference"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Batiment"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Description"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        for (Logement logement : Logement.findAll()) {
            table.addCell(logement.getReference());
            table.addCell(logement.getBatiment().load().getNom());
            table.addCell(logement.getDescription());
        }


        subCatPart.add(table);

    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private static void createList(Section subCatPart) {
        com.itextpdf.text.List list = new com.itextpdf.text.List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
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

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (Logement.findAll().isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    tvEmptyView.setVisibility(View.VISIBLE);

                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    tvEmptyView.setVisibility(View.GONE);
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
            createPDF();
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

    private void createPDF() {


        DecimalFormat df = new DecimalFormat("0.00");

        try {
            getFile();
            //Create time stamp
            Date date = new Date();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(date);
            File myFile = new File(file.getAbsolutePath() + File.separator + timeStamp + ".pdf");
            myFile.createNewFile();

            OutputStream output = new FileOutputStream(myFile);

            Document doc = new Document();
            PdfWriter writer = PdfWriter.getInstance(doc, output);
            writer.setLinearPageMode();
            writer.setFullCompression();
            // document header attributes
            doc.addAuthor(Constants.pdfAuteur);
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("DURVIL");
            doc.addTitle("etat");
            doc.setPageSize(PageSize.A4);
            // left,right,top,bottom
            doc.setMargins(36, 36, 36, 36);
            doc.setMarginMirroring(true);
            // open document
            doc.open();

            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);


            //open document
            doc.open();

            //create a paragraph
            Paragraph paragraph = new Paragraph("iText ® is a library that allows you to create and " +
                    "manipulate PDF documents. It enables developers looking to enhance web and other " +
                    "applications with dynamic PDF document generation and/or manipulation.");


            //specify column widths
            float[] columnWidths = {1.5f, 2f, 5f, 2f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(90f);

            //insert column headings
            insertCell(table, "Order No", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Account No", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Account Name", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Order Total", Element.ALIGN_RIGHT, 1, bfBold12);
            table.setHeaderRows(1);

            //insert an empty row
            insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
            //create section heading by cell merging
            insertCell(table, "New York Orders ...", Element.ALIGN_LEFT, 4, bfBold12);
            double orderTotal, total = 0;

            //just some random data to fill
            for (int x = 1; x < 5; x++) {

                insertCell(table, "10010" + x, Element.ALIGN_RIGHT, 1, bf12);
                insertCell(table, "ABC00" + x, Element.ALIGN_LEFT, 1, bf12);
                insertCell(table, "This is Customer Number ABC00" + x, Element.ALIGN_LEFT, 1, bf12);

                orderTotal = Double.valueOf(df.format(Math.random() * 1000));
                total = total + orderTotal;
                insertCell(table, df.format(orderTotal), Element.ALIGN_RIGHT, 1, bf12);

            }
            //merge the cells to create a footer for that section
            insertCell(table, "New York Total...", Element.ALIGN_RIGHT, 3, bfBold12);
            insertCell(table, df.format(total), Element.ALIGN_RIGHT, 1, bfBold12);

            //repeat the same as above to display another location
            insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
            insertCell(table, "California Orders ...", Element.ALIGN_LEFT, 4, bfBold12);
            orderTotal = 0;

            for (int x = 1; x < 7; x++) {

                insertCell(table, "20020" + x, Element.ALIGN_RIGHT, 1, bf12);
                insertCell(table, "XYZ00" + x, Element.ALIGN_LEFT, 1, bf12);
                insertCell(table, "This is Customer Number XYZ00" + x, Element.ALIGN_LEFT, 1, bf12);

                orderTotal = Double.valueOf(df.format(Math.random() * 1000));
                total = total + orderTotal;
                insertCell(table, df.format(orderTotal), Element.ALIGN_RIGHT, 1, bf12);

            }
            insertCell(table, "California Total...", Element.ALIGN_RIGHT, 3, bfBold12);
            insertCell(table, df.format(total), Element.ALIGN_RIGHT, 1, bfBold12);

            //add the PDF table to the paragraph
            paragraph.add(table);
            // add the paragraph to the document
            doc.add(paragraph);

            if (doc != null) {
                //close the document
                doc.close();
            }
            if (writer != null) {
                //close the writer
                writer.close();
            }
        } catch (DocumentException dex) {
            dex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }

    private void createPdf() {
        try {
            getFile();
            //Create time stamp
            Date date = new Date();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(date);
            File myFile = new File(file.getAbsolutePath() + File.separator + timeStamp + ".pdf");
            myFile.createNewFile();

            OutputStream output = new FileOutputStream(myFile);

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, output);
            writer.setLinearPageMode();
            writer.setFullCompression();
            // document header attributes
            document.addAuthor(Constants.pdfAuteur);
            document.addCreationDate();
            document.addProducer();
            document.addCreator("DURVIL");
            document.addTitle("etat");
            document.setPageSize(PageSize.A4);
            // left,right,top,bottom
            document.setMargins(36, 36, 36, 36);
            document.setMarginMirroring(true);
            // open document
            document.open();

            //Add content
            if (!isPDFFromHTML) {


                /* Inserting Image in PDF */
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.ic_launcher);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                Image myImg = Image.getInstance(stream.toByteArray());
                myImg.setAlignment(Image.MIDDLE);

                //add image to document
                document.add(myImg);

                Chapter catPart = new Chapter(0);


                // add a table
                createTable(catPart);
                //document.add(createTable(catPart))
                addContent(document);


                //set footer
               /* Phrase footerText = new Phrase("This is an example of a footer");
                HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
                document.(pdfFooter);*/
            }

            //Close the document
            document.close();
            viewPdf(myFile);

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            Log.e("PDF--->", "exception", e);
        }
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
        final Logement logement = SQLite.select().from(Logement.class).where(TypeLogement_Table.id.eq(id)).querySingle();

        AlertDialog detail = new AlertDialog.Builder(this)
                .setMessage(Html.fromHtml("<b>" + "Code: " + "</b> ") + logement.getDescription() + "\n" + "\n " + Html.fromHtml("<b>" + "Description: " + "</b> ") + logement.getDescription())
                .setIcon(R.drawable.ic_info_indigo_900_18dp)
                .setTitle("Detail " + logement.getDescription())
                .setNeutralButton("OK", null)
                .setCancelable(false)
                .create();
        detail.show();

    }

    @Override
    public void modifier(final int id) {

        final Logement logemen = SQLite.select().from(Logement.class).where(TypeLogement_Table.id.eq(id)).querySingle();
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
