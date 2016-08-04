package com.mahya.maisonier.adapter.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.mahya.maisonier.R;
import com.mahya.maisonier.activities.TypelogementActivity;
import com.mahya.maisonier.entites.TypeLogement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BatimentAdapter extends RecyclerSwipeAdapter<BatimentAdapter.SimpleViewHolder> {


    private static final String TAG = BatimentAdapter.class.getSimpleName();
    Context mContext;
    int idSelect;
    int selectposition;
    private List<TypeLogement> typeLogements;
    private View vue;
    private SparseBooleanArray selectedItems;
    private BatimentAdapter.SimpleViewHolder.ClickListener clickListener;

    public BatimentAdapter(Context context, List<TypeLogement> typeLogements, BatimentAdapter.SimpleViewHolder.ClickListener clickListener) {
        this.mContext = context;
        this.typeLogements = typeLogements;
        this.clickListener = clickListener;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple, parent, false);
        vue = view;
        return new SimpleViewHolder(view, clickListener);
    }


    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        try {
            viewHolder.tilte.setText(typeLogements.get(position).getCode());
            viewHolder.libele.setText(typeLogements.get(position).getLibelle());
            viewHolder.desc.setText(typeLogements.get(position).getDescription());
            viewHolder.id.setText(String.valueOf(typeLogements.get(position).getId()));
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

                if (mContext instanceof TypelogementActivity) {
                    ((TypelogementActivity) mContext).onItemClicked(position);
                }
            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ((TypelogementActivity) mContext).onItemLongClicked(position);

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

                ((TypelogementActivity) mContext).detail(idSelect);
                mItemManger.closeAllExcept(null);
            }
        });


        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TypelogementActivity) mContext).modifier(idSelect);
                mItemManger.closeAllExcept(null);


            }
        });


        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TypelogementActivity) mContext).supprimer(idSelect);
                mItemManger.closeAllExcept(null);

            }
        });


        mItemManger.bindView(viewHolder.itemView, position);


    }

    public void animateTo(List<TypeLogement> typeLogements) {
        applyAndAnimateRemovals(typeLogements);
        applyAndAnimateAdditions(typeLogements);
        applyAndAnimateMovedItems(typeLogements);
    }

    private void applyAndAnimateRemovals(List<TypeLogement> typeLogements) {
        for (int i = this.typeLogements.size() - 1; i >= 0; i--) {
            final TypeLogement model = this.typeLogements.get(i);
            if (!typeLogements.contains(model)) {
                deleteItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<TypeLogement> typeLogements) {
        for (int i = 0, count = typeLogements.size(); i < count; i++) {
            final TypeLogement typeLogement = typeLogements.get(i);
            if (!this.typeLogements.contains(typeLogement)) {
                addItem(i, typeLogement);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<TypeLogement> typeLogements) {
        for (int toPosition = typeLogements.size() - 1; toPosition >= 0; toPosition--) {
            final TypeLogement model = typeLogements.get(toPosition);
            final int fromPosition = this.typeLogements.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }


    public void addItem(int position, TypeLogement model) {
        typeLogements.add(position, model);
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
            typeLogements.remove(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public int getItemViewType(int position) {
        final TypeLogement item = typeLogements.get(position);

        return 0;
    }


    public void add(List<TypeLogement> items) {
        int previousDataSize = this.typeLogements.size();
        this.typeLogements.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    public Integer getId() {
        return idSelect;
    }

    public int getSelectposition() {
        return selectposition;
    }

    public TypeLogement getItem(int idx) {
        return typeLogements.get(idx);
    }

    public void addItem(TypeLogement typeLogement, int index) {
        typeLogements.add(typeLogement);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        typeLogements.remove(index);
        notifyItemRemoved(index);
    }

    public void actualiser(List<TypeLogement> typeLogements) {
        typeLogements.clear();
        typeLogements.addAll(typeLogements);
        notifyDataSetChanged();
    }


    public void moveItem(int fromPosition, int toPosition) {
        final TypeLogement model = typeLogements.remove(fromPosition);
        typeLogements.add(toPosition, model);
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
        return typeLogements.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();
        SwipeLayout swipeLayout;
        ImageButton tvDelete;
        ImageButton tvEdit;
        TextView tilte;
        TextView desc;
        TextView id;
        ImageButton detail;
        TextView libele;
        View selectedOverlay;
        private ClickListener listener;

        public SimpleViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tilte = (TextView) itemView.findViewById(R.id.titre);
            libele = (TextView) itemView.findViewById(R.id.libelle);
            desc = (TextView) itemView.findViewById(R.id.desc);
            id = (TextView) itemView.findViewById(R.id.idItem);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tvDelete = (ImageButton) itemView.findViewById(R.id.tvDelete);
            tvEdit = (ImageButton) itemView.findViewById(R.id.tvEdit);
            detail = (ImageButton) itemView.findViewById(R.id.btnLocation);
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

        public interface ClickListener {
            void onItemClicked(int position);

            boolean onItemLongClicked(int position);
        }

    }
}
