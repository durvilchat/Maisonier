package com.mahya.maisonier.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mahya.maisonier.R;
import com.mahya.maisonier.activities.AnneeActivity;
import com.mahya.maisonier.activities.ArticleActivity;
import com.mahya.maisonier.activities.BailleurActivity;
import com.mahya.maisonier.activities.BatimentActivity;
import com.mahya.maisonier.activities.CaracteristiqueActivity;
import com.mahya.maisonier.activities.CautionActivity;
import com.mahya.maisonier.activities.CiteActivity;
import com.mahya.maisonier.activities.CompteActivity;
import com.mahya.maisonier.activities.ContraBailActivity;
import com.mahya.maisonier.activities.DepenseActivity;
import com.mahya.maisonier.activities.DepotActivity;
import com.mahya.maisonier.activities.DossierHablitant;
import com.mahya.maisonier.activities.HabitantActivity;
import com.mahya.maisonier.activities.HabitantListActivity;
import com.mahya.maisonier.activities.LogementActivity;
import com.mahya.maisonier.activities.LogementDispoActivity;
import com.mahya.maisonier.activities.LoyerActivity;
import com.mahya.maisonier.activities.MoisActivity;
import com.mahya.maisonier.activities.OccupationActivity;
import com.mahya.maisonier.activities.ParametrageActivity;
import com.mahya.maisonier.activities.RubriqueActivity;
import com.mahya.maisonier.activities.RubriqueContratActivity;
import com.mahya.maisonier.activities.TypeCautionActivity;
import com.mahya.maisonier.activities.TypeCompteActivity;
import com.mahya.maisonier.activities.TypePenaliteActivity;
import com.mahya.maisonier.activities.TypedeChargeActivity;
import com.mahya.maisonier.activities.TypelogementActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MenuApp extends ExpandableRecyclerAdapter<MenuApp.ItemMenu> implements OnItemClickListener {
    public static final int sub_menu = 1001;
    Context context;

    public MenuApp(Context context) {
        super(context);
        this.context = context;
        setItems(getSampleItems());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case menu:
                return new MenuHolder(inflate(R.layout.item_menu, parent));
            case sub_menu:
            default:
                return new SubmenuHolder(inflate(R.layout.item_submenu, parent), this);
        }
    }

    @Override
    public void onBindViewHolder(final ExpandableRecyclerAdapter.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case menu:
                ((MenuHolder) holder).bind(position);
                if (((MenuHolder) holder).name.getText().equals(context.getString(R.string.accueilmaisonier)) && position == 0) {
                    ((MenuHolder) holder).arrow.setVisibility(View.GONE);
                } else {
                    ((MenuHolder) holder).arrow.setVisibility(View.VISIBLE);
                }


                break;
            case sub_menu:
            default:
                ((SubmenuHolder) holder).bind(position);

                ((SubmenuHolder) holder).sous_titre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView name = (TextView) view.findViewById(R.id.item_name);
                        if (name.getText().toString().trim().equals(context.getString(R.string.typelogement))) {

                            //   ((MainActivity) mContext).onClick();
                            context.startActivity(new Intent(context, TypelogementActivity.class));

                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Caracteristiques))) {
                            context.startActivity(new Intent(context, CaracteristiqueActivity.class));

                        } else if (name.getText().toString().trim().equals(context.getString(R.string.typedecharge))) {
                            context.startActivity(new Intent(context, TypedeChargeActivity.class));

                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Cite))) {
                            context.startActivity(new Intent(context, CiteActivity.class));

                        } else if (name.getText().toString().trim().equals(context.getString(R.string.batiment))) {
                            context.startActivity(new Intent(context, BatimentActivity.class));

                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Logements))) {
                            context.startActivity(new Intent(context, LogementActivity.class));

                        } else if (name.getText().toString().trim().equals(context.getString(R.string.typepenalite).trim())) {
                            context.startActivity(new Intent(context, TypePenaliteActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.article).trim())) {
                            context.startActivity(new Intent(context, ArticleActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Bailleur).trim())) {
                            context.startActivity(new Intent(context, BailleurActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Enregistrement).trim())) {
                            context.startActivity(new Intent(context, HabitantActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Bailleur).trim())) {
                            context.startActivity(new Intent(context, BailleurActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Occupations).trim())) {
                            context.startActivity(new Intent(context, OccupationActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Année).trim())) {
                            context.startActivity(new Intent(context, AnneeActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Mois).trim())) {
                            context.startActivity(new Intent(context, MoisActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Typedecompte).trim())) {
                            context.startActivity(new Intent(context, TypeCompteActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Dépot).trim())) {
                            context.startActivity(new Intent(context, DepotActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Rubrique).trim())) {
                            context.startActivity(new Intent(context, RubriqueActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Rubriquedecontrat).trim())) {
                            context.startActivity(new Intent(context, RubriqueContratActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Contratdebail).trim())) {
                            context.startActivity(new Intent(context, ContraBailActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Loyers).trim())) {
                            context.startActivity(new Intent(context, LoyerActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.TypedeCaution).trim())) {
                            context.startActivity(new Intent(context, TypeCautionActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Caution).trim())) {
                            context.startActivity(new Intent(context, CautionActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Depenses).trim())) {
                            context.startActivity(new Intent(context, DepenseActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Compte).trim())) {
                            context.startActivity(new Intent(context, CompteActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.disponibles).trim())) {
                            context.startActivity(new Intent(context, LogementDispoActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Listedeshablitants).trim())) {
                            context.startActivity(new Intent(context, HabitantListActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.parametrage).trim())) {
                            context.startActivity(new Intent(context, ParametrageActivity.class));


                        }else if (name.getText().toString().trim().equals(context.getString(R.string.Dossierdunhabitant).trim())) {
                            context.startActivity(new Intent(context, DossierHablitant.class));


                        }


                    }
                });

                break;
        }


    }


    List<ItemMenu> getSampleItems() {
        List<ItemMenu> items = new ArrayList<>();

        items.add(new ItemMenu(R.drawable.ic_home, context.getText(R.string.accueilmaisonier).toString()));
        items.add(new ItemMenu(R.drawable.ic_logement, context.getText(R.string.gererlogement).toString()));
        items.add(new ItemMenu(context.getText(R.string.typelogement).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.batiment).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Logements).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Caracteristiques).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.disponibles).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Cite).toString(), " "));
        items.add(new ItemMenu(R.drawable.ic_loyer, context.getText(R.string.GererdesLoyers).toString()));
        items.add(new ItemMenu(context.getText(R.string.Occupations).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Loyers).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Caution).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Correspondance).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Depenses).toString(), " "));
        items.add(new ItemMenu(R.drawable.ic_habitant, context.getText(R.string.Gérerleshabitants).toString()));
        items.add(new ItemMenu(context.getText(R.string.Enregistrement).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Listedeshablitants).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Dossierdunhabitant).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Etatdesoccupations).toString(), " "));
        items.add(new ItemMenu(R.drawable.ic_contrat, context.getText(R.string.Gérerlescontrats).toString()));
        items.add(new ItemMenu(context.getText(R.string.Bailleur).toString(), " "));
        items.add(new ItemMenu(context.getString(R.string.article), " "));
        items.add(new ItemMenu(context.getText(R.string.Contratdebail).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Rubriquedecontrat).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Rubrique).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.ChargerRibriqueduncontrat).toString(), " "));
        items.add(new ItemMenu(R.drawable.ic_compte, context.getText(R.string.Gérerlescomptes).toString()));
        items.add(new ItemMenu(context.getText(R.string.Typedecompte).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Compte).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Dépot).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.ChargerDépot).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Transfertintercompte).toString(), " "));
        items.add(new ItemMenu(R.drawable.ic_penalite, context.getText(R.string.Gérerlespénalités).toString()));
        items.add(new ItemMenu(context.getString(R.string.typepenalite).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Pénalités).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Charges).toString(), " "));
        items.add(new ItemMenu(context.getString(R.string.typedecharge).toString(), " "));
        items.add(new ItemMenu(R.drawable.ic_index, context.getText(R.string.Gestiondesindex).toString()));
        items.add(new ItemMenu(context.getText(R.string.Consommationeneau).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Consommationenélectricité).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Indexeneau).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Indexenéléctricité).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Gérerlecable).toString(), " "));
        items.add(new ItemMenu(R.drawable.ic_etat, context.getText(R.string.Etats).toString()));
        items.add(new ItemMenu(context.getText(R.string.Depenses).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Mois).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Année).toString(), " "));
        items.add(new ItemMenu(R.drawable.ic_settings_applications_white_18dp, context.getText(R.string.Parametres).toString()));
        items.add(new ItemMenu(context.getText(R.string.parametrage).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Mois).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Année).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.TypedeCaution).toString(), " "));
        items.add(new ItemMenu(context.getText(R.string.Historiquedeparametrage).toString(), " "));
        return items;
    }


    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public boolean onItemLongClicked(int position) {
        return false;
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    public static class ItemMenu extends ExpandableRecyclerAdapter.ListItem {
        public String Text;
        int res;

        public ItemMenu(int res, String group) {
            super(menu);
            Text = group;
            this.res = res;
        }

        public ItemMenu(String first, String last) {
            super(sub_menu);

            Text = first + " " + last;
        }
    }

    public class MenuHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {
        TextView name;
        LinearLayout tittre;
        ImageView icon;

        public MenuHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.item_arrow));
            tittre = (LinearLayout) view.findViewById(R.id.titremenu);
            name = (TextView) view.findViewById(R.id.item_header_name);
            icon = (ImageView) view.findViewById(R.id.item_header_image);

        }


        public void bind(int position) {
            super.bind(position);
            name.setText(visibleItems.get(position).Text);
            icon.setImageResource(visibleItems.get(position).res);
        }
    }

    public class SubmenuHolder extends ExpandableRecyclerAdapter.ViewHolder {
        TextView name;
        LinearLayout sous_titre;

        public SubmenuHolder(View view, final OnItemClickListener onItemClickListener) {
            super(view);

            name = (TextView) view.findViewById(R.id.item_name);
            sous_titre = (LinearLayout) view.findViewById(R.id.sous_titre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onLongClick(view, getAdapterPosition());
                    }
                    Toast.makeText(context, "koool", Toast.LENGTH_SHORT).show();
                }

            });

        }

        public void bind(int position) {
            name.setText(visibleItems.get(position).Text);
        }
    }


}
