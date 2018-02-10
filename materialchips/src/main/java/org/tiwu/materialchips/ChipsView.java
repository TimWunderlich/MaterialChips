package org.tiwu.materialchips;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("unused")
public class ChipsView extends RecyclerView {

    ChipsViewAdapter adapter;
    boolean firstInit = true;
    private List<Object> items;
    private boolean deletable;
    private OnDeleteListener onDeleteListener;
    private OnClickListener onClickListener;

    public ChipsView(Context context) {
        super(context);
        init();
    }

    public ChipsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.items = new ArrayList<>();
        this.deletable = false;
        this.onDeleteListener = null;
        this.onClickListener = null;
        ChipsLayoutManager layoutManager = ChipsLayoutManager.newBuilder(getContext())
                .setChildGravity(Gravity.TOP)
                .setScrollingEnabled(true)
                .setMaxViewsInRow(4)
                .setGravityResolver(new IChildGravityResolver() {
                    @Override
                    public int getItemGravity(int i) {
                        return Gravity.CENTER;
                    }
                })
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();
        setLayoutManager(layoutManager);
        this.adapter = new ChipsViewAdapter(this, this.items);
        setAdapter(this.adapter);
        if (firstInit) {
            addItemDecoration(new SpacingItemDecoration(
                    getResources().getDimensionPixelOffset(R.dimen.chip_space),
                    getResources().getDimensionPixelOffset(R.dimen.chip_space))
            );
            this.firstInit = false;
        }
    }

    public void addItem(@Nullable Object item) {
        if (item != null) {
            this.items.add(item);
            this.adapter.notifyItemInserted(this.items.size() - 1);
        }
    }

    public boolean isDeletable() {
        return this.deletable;
    }

    /**
     * Sets the "deletable" status of the chips within the view.
     *
     * @param deletable The "deletable" status to be set, so either true or false.
     */
    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    /**
     * Returns the item at a given position within the internal list.
     * Returns null if no item can be found at the given position.
     *
     * @param position The position of the requested item.
     * @return The item at the given position.
     */
    @Nullable
    public Object getItem(int position) {
        if (this.items.size() > position) {
            return this.items.get(position);
        }
        return null;
    }

    /**
     * Wrapper around <code>List.indexOf(Object)</code>.
     *
     * @param item The item to be found.
     * @return The index of the Chip.
     */
    public int indexOf(@NonNull Object item) {
        return items.indexOf(item);
    }

    public void removeItem(int position) {
        this.items.remove(position);
        this.adapter.notifyItemRemoved(position);
    }

    public List<String> getStrings() {
        List<String> result = new ArrayList<>();
        for (Object item : this.items) {
            result.add(item.toString());
        }
        return result;
    }

    @NonNull
    public List<Object> getItems() {
        return this.items;
    }

    @Nullable
    public OnClickListener getOnClickListener() {
        return this.onClickListener;
    }

    public void setOnClickListener(@Nullable OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public
    @Nullable
    OnDeleteListener getOnDeleteListener() {
        return this.onDeleteListener;
    }

    public void setOnDeleteListener(@Nullable OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    /**
     * Resets the ChipsView, i.e. deletes all items and listeners.
     */
    public void clear() {
        init();
    }

    public static abstract class OnDeleteListener {
        protected abstract boolean onDelete(Object item, int position);
    }

    public static abstract class OnClickListener {
        protected abstract void onClick(Object item, int position);
    }

    public class ChipsViewAdapter extends RecyclerView.Adapter<ChipsViewAdapter.ChipsViewHolder> {

        List<?> items;
        ChipsView parent;

        ChipsViewAdapter(ChipsView parent, List<?> items) {
            this.items = items;
            this.parent = parent;
        }

        @Override
        public ChipsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chip, parent, false);
            return new ChipsViewHolder(layoutView);
        }

        @Override
        public void onBindViewHolder(final ChipsView.ChipsViewAdapter.ChipsViewHolder holder, int position) {

            final Object item = items.get(position);

            Resources res = getContext().getResources();
            int paddingSmall = res.getDimensionPixelSize(R.dimen.chip_padding_bottom);
            int paddingLarge = res.getDimensionPixelSize(R.dimen.chip_padding_left);

            int paddingLeft;
            int paddingRight;

            if (item instanceof ImageProvider) {
                Bitmap bitmap = ((ImageProvider) item).getBitmap();
                if (bitmap == null) {
                    holder.thumbnail.setVisibility(View.GONE);
                    paddingLeft = paddingLarge;
                } else {
                    holder.thumbnail.setImageBitmap(((ImageProvider) item).getBitmap());
                    holder.thumbnail.setVisibility(View.VISIBLE);
                    paddingLeft = paddingSmall;
                }
            } else {
                holder.thumbnail.setVisibility(View.GONE);
                paddingLeft = paddingLarge;
            }

            holder.label.setText(item.toString());

            if (isDeletable()) {
                paddingRight = paddingSmall;
                holder.deleteButton.setVisibility(View.VISIBLE);
                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OnDeleteListener onDeleteListener = getOnDeleteListener();
                        if (onDeleteListener != null) {
                            boolean result = onDeleteListener.onDelete(item, holder.getAdapterPosition());
                            if (result) {
                                removeItem(holder.getAdapterPosition());
                            }
                        }
                    }
                });
            } else {
                holder.deleteButton.setVisibility(View.GONE);
                paddingRight = paddingLarge;
            }
            holder.label.setPadding(paddingLeft, paddingSmall, paddingRight, paddingSmall);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnClickListener onClickListener = getOnClickListener();
                    if (onClickListener != null) {
                        onClickListener.onClick(item, holder.getAdapterPosition());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }

        class ChipsViewHolder extends RecyclerView.ViewHolder {

            TextView label;
            ImageView deleteButton;
            CircleImageView thumbnail;

            ChipsViewHolder(View itemView) {
                super(itemView);
                this.thumbnail = itemView.findViewById(R.id.thumbnail);
                this.label = itemView.findViewById(R.id.label);
                this.deleteButton = itemView.findViewById(R.id.delete);
            }
        }
    }
}
