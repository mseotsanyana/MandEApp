package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import com.me.mseotsanyana.expandablelayoutlibrary.CExpandableLayout;
import com.me.mseotsanyana.mande.databinding.ProjectParentCardviewBinding;

public interface iViewProjectListener {
    void onClickReadLogFrame(int position);
    void onRegisterProjectViewPager2(ProjectParentCardviewBinding parentBinding, int position);
    void onUnRegisterProjectViewPager2(ProjectParentCardviewBinding parentBinding);
}
