package jp.co.futureantiques.trainingrecord.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MainFragmentStateAdapter extends FragmentStateAdapter {

    private List<Fragment> mListFragment;

    public MainFragmentStateAdapter(FragmentActivity activity, List<Fragment> list) {
        super(activity);
        this.mListFragment = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return this.mListFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return this.mListFragment.size();
    }
}
