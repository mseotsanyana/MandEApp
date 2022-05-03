package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class cMELViewPagerAdapter extends FragmentStateAdapter {
    private final List<Fragment>  fragmentList = new ArrayList<>();
    private final List<String> titleList= new ArrayList<>();

    public cMELViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void addFrag(int index, Fragment fragment, String title) {
        fragmentList.add(index, fragment);
        titleList.add(index, title);
        notifyItemChanged(index);
    }
    public void replaceFragment(int index, Fragment fragment) {
        String title = titleList.get(index);
        remove(index);
        fragmentList.set(index, fragment);
        titleList.add(index, title);
        notifyItemChanged(index);
    }

    public void remove(int index) {
        fragmentList.remove(index);
        titleList.remove(index);
        notifyItemChanged(index);
    }

    public void clearFragments  (){
        fragmentList.clear();
        titleList.clear();
    }

    public Fragment getPageFragment(int index){
        return fragmentList.get(index);
    }

    public CharSequence getPageTitle(int index) {
        return titleList.get(index);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
