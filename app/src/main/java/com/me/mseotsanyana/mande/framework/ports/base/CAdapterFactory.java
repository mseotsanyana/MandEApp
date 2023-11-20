package com.me.mseotsanyana.mande.framework.ports.base;

import android.content.Context;

import com.me.mseotsanyana.mande.application.structures.CIndexedLinkedHashMap;
import com.me.mseotsanyana.mande.framework.ui.adapters.session.COrganizationWorkspaceAdapter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;
import com.me.mseotsanyana.mande.infrastructure.ports.session.IOrganizationWorkspaceController;
import com.me.mseotsanyana.mande.infrastructure.utils.responsemodel.CTreeModel;

public class CAdapterFactory implements AGUIFactory<IBaseAdapter> {

    private final Context context;
    private final IBaseView iBaseView;
    private final EAdapterType adapterType;

    public CAdapterFactory(Context context, IBaseView iBaseView,
                           EAdapterType adapterType) {
        this.context = context;
        this.iBaseView = iBaseView;
        this.adapterType = adapterType;
    }

    public IBaseView getIBaseView() {
        return iBaseView;
    }

    @Override
    public IBaseAdapter create() {
        return new COrganizationWorkspaceAdapter(context,
                (IOrganizationWorkspaceController.IViewModel) getIBaseView());
    }

}
