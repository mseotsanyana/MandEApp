package com.me.mseotsanyana.mande.databinding;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class SessionOrganizationCardviewBindingImpl extends SessionOrganizationCardviewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.cardView, 1);
        sViewsWithIds.put(R.id.linearLayoutName, 2);
        sViewsWithIds.put(R.id.textViewOrganizationIcon, 3);
        sViewsWithIds.put(R.id.textViewName, 4);
        sViewsWithIds.put(R.id.linearLayoutEmail, 5);
        sViewsWithIds.put(R.id.textViewEmailIcon, 6);
        sViewsWithIds.put(R.id.textViewEmail, 7);
        sViewsWithIds.put(R.id.linearLayoutWebsite, 8);
        sViewsWithIds.put(R.id.textViewWebsiteIcon, 9);
        sViewsWithIds.put(R.id.textViewWebsite, 10);
        sViewsWithIds.put(R.id.textViewDeleteIcon, 11);
        sViewsWithIds.put(R.id.textViewUpdateIcon, 12);
        sViewsWithIds.put(R.id.textViewCreateIcon, 13);
        sViewsWithIds.put(R.id.textViewJoinIcon, 14);
        sViewsWithIds.put(R.id.textViewDetailIcon, 15);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public SessionOrganizationCardviewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private SessionOrganizationCardviewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.cardview.widget.CardView) bindings[1]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[8]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[11]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[15]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[7]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[6]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[4]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[3]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[12]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[9]
            );
        this.linearLayout.setTag(null);
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