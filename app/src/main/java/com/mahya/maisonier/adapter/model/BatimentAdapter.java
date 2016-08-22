package com.mahya.maisonier.adapter.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.mahya.maisonier.R;
import com.mahya.maisonier.activities.BatimentActivity;
import com.mahya.maisonier.activities.detail.Aff_BatimentActivity;
import com.mahya.maisonier.entites.Batiment;
import com.mahya.maisonier.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BatimentAdapter extends RecyclerSwipeAdapter<BatimentAdapter.SimpleViewHolder> {


    private static final String TAG = BatimentAdapter.class.getSimpleName();
    Context mContext;
    int idSelect;
    int selectposition;
    private List<Batiment> batiments;
    private SparseBooleanArray selectedItems;
    private OnItemClickListener clickListener;

    public BatimentAdapter(Context context, List<Batiment> batiments, OnItemClickListener clickListener) {
        this.mContext = context;
        this.batiments = batiments;
        this.clickListener = clickListener;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bati, parent, false);
        return new SimpleViewHolder(view, clickListener);
    }


    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        try {
            viewHolder.nom.setText(batiments.get(position).getCode());
            viewHolder.code.setTextColor(mContext.getResources().getColor(R.color.red));
            viewHolder.citecode.setTextColor(mContext.getResources().getColor(R.color.red));
            viewHolder.citecode.setText(batiments.get(position).getCite().load().getBailleur().load().getNom());
            viewHolder.code.setText(batiments.get(position).getNom());
            viewHolder.cite.setText(batiments.get(position).getCite().load().getNomCite() + " " + batiments.get(position).getCite().load().getSiege());
            viewHolder.id.setText(String.valueOf(batiments.get(position).getId()));
        } catch (Exception e) {

            return;
        }

// Span the item if active
        final ViewGroup.LayoutParams lp = viewHolder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;

            viewHolder.itemView.setLayoutParams(sglp);
        }

        // Highlight the item if it's selected
        viewHolder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

        // Drag From Left
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));
        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mContext instanceof BatimentActivity) {
                    TextView id = (TextView) v.findViewById(R.id.idItem);
                    idSelect = Integer.parseInt(id.getText().toString());
                    if (mContext instanceof BatimentActivity) {
                        ((BatimentActivity) mContext).onItemClicked(position);
                        Intent intent = new Intent(mContext, Aff_BatimentActivity.class);
                        intent.putExtra("id", idSelect);
                        mContext.startActivity(intent);
                    }
                }
            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ((BatimentActivity) mContext).onItemLongClicked(position);

                return true;
            }


        });

        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

                mItemManger.closeAllExcept(layout);

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                selectposition = position;
                TextView id = (TextView) layout.findViewById(R.id.idItem);
                idSelect = Integer.parseInt(id.getText().toString());
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });


        viewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((BatimentActivity) mContext).detail(idSelect);
                mItemManger.closeAllExcept(null);
            }
        });


        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BatimentActivity) mContext).modifier(idSelect);
                mItemManger.closeAllExcept(null);


            }
        });


        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BatimentActivity) mContext).supprimer(idSelect);
                mItemManger.closeAllExcept(null);

            }
        });

        viewHolder.imgBat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, "bonjour", Toast.LENGTH_SHORT).show();
            }


        });


        mItemManger.bindView(viewHolder.itemView, position);


    }

    public void animateTo(List<Batiment> batiments) {
        applyAndAnimateRemovals(batiments);
        applyAndAnimateAdditions(batiments);
        applyAndAnimateMovedItems(batiments);
    }

    private void applyAndAnimateRemovals(List<Batiment> batiments) {
        for (int i = this.batiments.size() - 1; i >= 0; i--) {
            final Batiment model = this.batiments.get(i);
            if (!batiments.contains(model)) {
                deleteItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Batiment> batiments) {
        for (int i = 0, count = batiments.size(); i < count; i++) {
            final Batiment Batiment = batiments.get(i);
            if (!this.batiments.contains(Batiment)) {
                addItem(i, Batiment);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Batiment> batiments) {
        for (int toPosition = batiments.size() - 1; toPosition >= 0; toPosition--) {
            final Batiment model = batiments.get(toPosition);
            final int fromPosition = this.batiments.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }


    public void addItem(int position, Batiment model) {
        batiments.add(position, model);
        notifyItemInserted(position);
    }


    public void removeItems(List<Integer> positions) {
        // Reverse-sort the list
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        // Split the list in ranges
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                deleteItem(positions.get(0));
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }

                if (count == 1) {
                    deleteItem(positions.get(0));
                } else {
                    removeRange(positions.get(count - 1), count);
                }

                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }
    }

    private void removeRange(int positionStart, int itemCount) {
        for (int i = 0; i < itemCount; ++i) {
            batiments.remove(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public int getItemViewType(int position) {
        final Batiment item = batiments.get(position);

        return 0;
    }


    public void add(List<Batiment> batiments) {
        int previousDataSize = this.batiments.size();
        this.batiments.addAll(batiments);
        notifyItemRangeInserted(previousDataSize, batiments.size());
    }

    public Integer getId() {
        return idSelect;
    }

    public int getSelectposition() {
        return selectposition;
    }

    public Batiment getItem(int idx) {
        return batiments.get(idx);
    }

    public void addItem(Batiment Batiment, int index) {
        batiments.add(index, Batiment);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        batiments.remove(index);
        notifyItemRemoved(index);
    }

    public void actualiser(List<Batiment> batiments) {
        this.batiments.clear();
        this.batiments.addAll(batiments);
        notifyDataSetChanged();
    }


    public void moveItem(int fromPosition, int toPosition) {
        final Batiment model = batiments.remove(fromPosition);
        batiments.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }


    /**
     * Indicates if the item at position position is selected
     *
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    /**
     * Toggle the selection status of the item at a given position
     *
     * @param position Position of the item to toggle the selection status for
     */
    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }


    /**
     * Clear the selection status for all items
     */
    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    /**
     * Count the selected items
     *
     * @return Selected items count
     */
    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    /**
     * Indicates the list of selected items
     *
     * @return List of selected items ids
     */
    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }


    @Override
    public int getItemCount() {
        return batiments.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();
        private final ImageView imgBat;
        SwipeLayout swipeLayout;
        ImageButton tvDelete;
        ImageButton tvEdit;
        TextView code;
        TextView nom;
        TextView id;
        ImageButton detail;
        TextView cite;
        TextView citecode;

        View selectedOverlay;
        OnItemClickListener listener;

        public SimpleViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            code = (TextView) itemView.findViewById(R.id.titre);
            nom = (TextView) itemView.findViewById(R.id.libelle);
            cite = (TextView) itemView.findViewById(R.id.libelle1);
            imgBat = (ImageView) itemView.findViewById(R.id.imgbat);
            citecode = (TextView) itemView.findViewById(R.id.titre1);
            id = (TextView) itemView.findViewById(R.id.idItem);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tvDelete = (ImageButton) itemView.findViewById(R.id.tvDelete);
            tvEdit = (ImageButton) itemView.findViewById(R.id.tvEdit);
            detail = (ImageButton) itemView.findViewById(R.id.detail);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            this.listener = listener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setLongClickable(true);

        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                return listener.onItemLongClicked(getPosition());
            }

            return false;
        }


    }
}
