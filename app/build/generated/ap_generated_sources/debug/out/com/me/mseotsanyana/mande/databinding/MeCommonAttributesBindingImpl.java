package com.me.mseotsanyana.mande.databinding;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class MeCommonAttributesBindingImpl extends MeCommonAttributesBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.includeProgressBar, 2);
        sViewsWithIds.put(R.id.cardView, 3);
        sViewsWithIds.put(R.id.linearLayoutOwner, 4);
        sViewsWithIds.put(R.id.appCompatTextViewOwner, 5);
        sViewsWithIds.put(R.id.appCompatSpinnerOwner, 6);
        sViewsWithIds.put(R.id.linearLayoutTeam, 7);
        sViewsWithIds.put(R.id.appCompatTextViewTeam, 8);
        sViewsWithIds.put(R.id.appCompatSpinnerTeam, 9);
        sViewsWithIds.put(R.id.linearLayoutOrganization, 10);
        sViewsWithIds.put(R.id.appCompatTextViewOrganization, 11);
        sViewsWithIds.put(R.id.appCompatSpinnerOrganization, 12);
        sViewsWithIds.put(R.id.textViewCreatedDateCaption, 13);
        sViewsWithIds.put(R.id.textViewCreatedDate, 14);
        sViewsWithIds.put(R.id.textViewModifiedDateCaption, 15);
        sViewsWithIds.put(R.id.textViewModifiedDate, 16);
        sViewsWithIds.put(R.id.tableLayoutStatus, 17);
        sViewsWithIds.put(R.id.tableRowStatusOperations, 18);
        sViewsWithIds.put(R.id.textViewDeleted, 19);
        sViewsWithIds.put(R.id.textViewBlocked, 20);
        sViewsWithIds.put(R.id.textViewActivated, 21);
        sViewsWithIds.put(R.id.textViewCancelled, 22);
        sViewsWithIds.put(R.id.textViewPending, 23);
        sViewsWithIds.put(R.id.tableRowStatusOwner, 24);
        sViewsWithIds.put(R.id.switchDeleted, 25);
        sViewsWithIds.put(R.id.switchBlocked, 26);
        sViewsWithIds.put(R.id.switchActivated, 27);
        sViewsWithIds.put(R.id.switchCancelled, 28);
        sViewsWithIds.put(R.id.switchPending, 29);
        sViewsWithIds.put(R.id.separatorView, 30);
        sViewsWithIds.put(R.id.tableLayoutPermission, 31);
        sViewsWithIds.put(R.id.tableRowOperations, 32);
        sViewsWithIds.put(R.id.textViewEmpty, 33);
        sViewsWithIds.put(R.id.textViewRead, 34);
        sViewsWithIds.put(R.id.textViewUpdate, 35);
        sViewsWithIds.put(R.id.textViewDelete, 36);
        sViewsWithIds.put(R.id.tableRowOwner, 37);
        sViewsWithIds.put(R.id.textViewOwner, 38);
        sViewsWithIds.put(R.id.checkBoxOwnerRead, 39);
        sViewsWithIds.put(R.id.checkBoxOwnerUpdate, 40);
        sViewsWithIds.put(R.id.checkBoxOwnerDelete, 41);
        sViewsWithIds.put(R.id.tableRowPrimary, 42);
        sViewsWithIds.put(R.id.textViewPrimary, 43);
        sViewsWithIds.put(R.id.checkBoxPrimaryRead, 44);
        sViewsWithIds.put(R.id.checkBoxPrimaryUpdate, 45);
        sViewsWithIds.put(R.id.checkBoxPrimaryDelete, 46);
        sViewsWithIds.put(R.id.tableRowSecondary, 47);
        sViewsWithIds.put(R.id.textViewSecondary, 48);
        sViewsWithIds.put(R.id.checkBoxSecondaryRead, 49);
        sViewsWithIds.put(R.id.checkBoxSecondaryUpdate, 50);
        sViewsWithIds.put(R.id.checkBoxSecondaryDelete, 51);
        sViewsWithIds.put(R.id.tableRowOrganization, 52);
        sViewsWithIds.put(R.id.textViewOrganization, 53);
        sViewsWithIds.put(R.id.checkBoxOrganizationRead, 54);
        sViewsWithIds.put(R.id.checkBoxOrganizationUpdate, 55);
        sViewsWithIds.put(R.id.checkBoxOrganizationDelete, 56);
        sViewsWithIds.put(R.id.linearLayoutModify, 57);
        sViewsWithIds.put(R.id.buttonReset, 58);
        sViewsWithIds.put(R.id.buttonUpdate, 59);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public MeCommonAttributesBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 60, sIncludes, sViewsWithIds));
    }
    private MeCommonAttributesBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.me.mseotsanyana.multiselectspinnerlibrary.CSingleSpinnerSearch) bindings[12]
            , (com.me.mseotsanyana.multiselectspinnerlibrary.CSingleSpinnerSearch) bindings[6]
            , (com.me.mseotsanyana.multiselectspinnerlibrary.CSingleSpinnerSearch) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[11]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[8]
            , (android.widget.Button) bindings[58]
            , (android.widget.Button) bindings[59]
            , (androidx.cardview.widget.CardView) bindings[3]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[56]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[54]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[55]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[41]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[39]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[40]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[46]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[44]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[45]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[51]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[49]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[50]
            , (android.view.View) bindings[2]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.LinearLayout) bindings[57]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.LinearLayout) bindings[7]
            , (android.view.View) bindings[30]
            , (com.google.android.material.switchmaterial.SwitchMaterial) bindings[27]
            , (com.google.android.material.switchmaterial.SwitchMaterial) bindings[26]
            , (com.google.android.material.switchmaterial.SwitchMaterial) bindings[28]
            , (com.google.android.material.switchmaterial.SwitchMaterial) bindings[25]
            , (com.google.android.material.switchmaterial.SwitchMaterial) bindings[29]
            , (android.widget.TableLayout) bindings[31]
            , (android.widget.TableLayout) bindings[17]
            , (android.widget.TableRow) bindings[32]
            , (android.widget.TableRow) bindings[52]
            , (android.widget.TableRow) bindings[37]
            , (android.widget.TableRow) bindings[42]
            , (android.widget.TableRow) bindings[47]
            , (android.widget.TableRow) bindings[18]
            , (android.widget.TableRow) bindings[24]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[22]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (android.widget.TextView) bindings[36]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[33]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[15]
            , (android.widget.TextView) bindings[53]
            , (android.widget.TextView) bindings[38]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[43]
            , (android.widget.TextView) bindings[34]
            , (android.widget.TextView) bindings[48]
            , (android.widget.TextView) bindings[35]
            );
        this.linearLayout.setTag(null);
        this.mboundView1 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[1];
        this.mboundView1.setTag(null);
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