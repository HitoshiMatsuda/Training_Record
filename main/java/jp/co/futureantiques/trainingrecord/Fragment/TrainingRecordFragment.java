package jp.co.futureantiques.trainingrecord.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.futureantiques.trainingrecord.R;

public class TrainingRecordFragment extends Fragment {

    //ListViewの内容
    String[] SampleDate = {"大胸筋","背中","下半身","肩・腕","有酸素"};
    String[] SampleBookWriterDate = {"2月17日","2月18日","2月19日","2月20日","2月21日"};

    public TrainingRecordFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training_record, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ListViewに表示するリスト項目をArrayListで準備する
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (int i = 0; i < SampleDate.length; i++) {
            Map<String, String> item = new HashMap<String, String>();
            item.put("Subject", SampleDate[i]);
            item.put("Comment", SampleBookWriterDate[i]);
            data.add(item);
        }

        // リスト項目とListViewを対応付けるArrayAdapterを用意する
        SimpleAdapter adapter = new SimpleAdapter(getContext(), data,
                R.layout.list_layout_main,
                new String[]{"Subject", "Comment"},
                new int[]{R.id.menu_name, R.id.date_text});

        // ListViewにArrayAdapterを設定する
        ListView listView = view.findViewById(R.id.training_list);
        listView.setAdapter(adapter);

    }
}