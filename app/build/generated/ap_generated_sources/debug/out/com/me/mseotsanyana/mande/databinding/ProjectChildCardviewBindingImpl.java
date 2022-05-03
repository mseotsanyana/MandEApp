package com.me.mseotsanyana.mande.databinding;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ProjectChildCardviewBindingImpl extends ProjectChildCardviewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(17);
        sIncludes.setIncludes(1, 
            new String[] {"me_tablayout_viewpager2"},
            new int[] {2},
            new int[] {com.me.mseotsanyana.mande.R.layout.me_tablayout_viewpager2});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.cardView, 3);
        sViewsWithIds.put(R.id.expandableLayout, 4);
        sViewsWithIds.put(R.id.show, 5);
        sViewsWithIds.put(R.id.textViewName, 6);
        sViewsWithIds.put(R.id.textViewDescription, 7);
        sViewsWithIds.put(R.id.textViewStartDateCaption, 8);
        sViewsWithIds.put(R.id.textViewStartDate, 9);
        sViewsWithIds.put(R.id.textViewEndDateCaption, 10);
        sViewsWithIds.put(R.id.textViewEndDate, 11);
        sViewsWithIds.put(R.id.textViewLocationCaption, 12);
        sViewsWithIds.put(R.id.textViewLocation, 13);
        sViewsWithIds.put(R.id.textViewDeleteIcon, 14);
        sViewsWithIds.put(R.id.textViewUpdateIcon, 15);
        sViewsWithIds.put(R.id.textViewDetailIcon, 16);
    }
    // views
    @Nullable
    private final com.me.mseotsanyana.mande.databinding.MeTablayoutViewpager2Binding mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ProjectChildCardviewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private ProjectChildCardviewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.cardview.widget.CardView) bindings[3]
            , (com.me.mseotsanyana.expandablelayoutlibrary.CExpandableLayout) bindings[4]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[0]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[7]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[11]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[12]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[6]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[8]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[15]
            );
        this.hide.setTag(null);
        this.linearLayout.setTag(null);
        this.mboundView1 = (com.me.mseotsanyana.mande.databinding.MeTablayoutViewpager2Binding) bindings[2];
        setContainedBinding(this.mboundView1);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        mboundView1.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (mboundView1.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        mboundView1.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
        executeBindingsOn(mboundView1);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}