package com.me.mseotsanyana.mande.databinding;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class SessionOrganizationFragmentBindingImpl extends SessionOrganizationFragmentBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.appBarLayout, 1);
        sViewsWithIds.put(R.id.collapsingToolbarLayout, 2);
        sViewsWithIds.put(R.id.header_text_layout, 3);
        sViewsWithIds.put(R.id.appName, 4);
        sViewsWithIds.put(R.id.toolbar, 5);
        sViewsWithIds.put(R.id.organizationTabLayout, 6);
        sViewsWithIds.put(R.id.organizationViewPager2, 7);
    }
    // views
    @NonNull
    private final androidx.coordinatorlayout.widget.CoordinatorLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public SessionOrganizationFragmentBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private SessionOrganizationFragmentBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.google.android.material.appbar.AppBarLayout) bindings[1]
            , (android.widget.TextView) bindings[4]
            , (com.google.android.material.appbar.CollapsingToolbarLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[3]
            , (com.google.android.material.tabs.TabLayout) bindings[6]
            , (androidx.viewpager2.widget.ViewPager2) bindings[7]
            , (androidx.appcompat.widget.Toolbar) bindings[5]
            );
        this.mboundView0 = (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
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