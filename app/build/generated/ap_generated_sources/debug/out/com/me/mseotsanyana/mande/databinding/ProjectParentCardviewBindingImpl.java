package com.me.mseotsanyana.mande.databinding;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ProjectParentCardviewBindingImpl extends ProjectParentCardviewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(20);
        sIncludes.setIncludes(1, 
            new String[] {"me_tablayout_viewpager2"},
            new int[] {2},
            new int[] {com.me.mseotsanyana.mande.R.layout.me_tablayout_viewpager2});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.cardView, 3);
        sViewsWithIds.put(R.id.expandableLayout, 4);
        sViewsWithIds.put(R.id.show, 5);
        sViewsWithIds.put(R.id.textViewExpandIcon, 6);
        sViewsWithIds.put(R.id.textViewName, 7);
        sViewsWithIds.put(R.id.textViewDescription, 8);
        sViewsWithIds.put(R.id.textViewStartDateCaption, 9);
        sViewsWithIds.put(R.id.textViewStartDate, 10);
        sViewsWithIds.put(R.id.textViewEndDateCaption, 11);
        sViewsWithIds.put(R.id.textViewEndDate, 12);
        sViewsWithIds.put(R.id.textViewLocationCaption, 13);
        sViewsWithIds.put(R.id.textViewLocation, 14);
        sViewsWithIds.put(R.id.textViewLogframeIcon, 15);
        sViewsWithIds.put(R.id.textViewDeleteIcon, 16);
        sViewsWithIds.put(R.id.textViewUpdateIcon, 17);
        sViewsWithIds.put(R.id.textViewCreateIcon, 18);
        sViewsWithIds.put(R.id.textViewDetailIcon, 19);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ProjectParentCardviewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds));
    }
    private ProjectParentCardviewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (androidx.cardview.widget.CardView) bindings[3]
            , (com.me.mseotsanyana.expandablelayoutlibrary.CExpandableLayout) bindings[4]
            , (android.widget.LinearLayout) bindings[0]
            , (com.me.mseotsanyana.mande.databinding.MeTablayoutViewpager2Binding) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[18]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[8]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[19]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[12]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[11]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[6]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[15]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[7]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[17]
            );
        this.linearLayout.setTag(null);
        this.mboundView1 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[1];
        this.mboundView1.setTag(null);
        setContainedBinding(this.projectViewPager2);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        projectViewPager2.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (projectViewPager2.hasPendingBindings()) {
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
        projectViewPager2.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeProjectViewPager2((com.me.mseotsanyana.mande.databinding.MeTablayoutViewpager2Binding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeProjectViewPager2(com.me.mseotsanyana.mande.databinding.MeTablayoutViewpager2Binding ProjectViewPager2, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
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
        executeBindingsOn(projectViewPager2);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): projectViewPager2
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}