package cn.app.c.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cn.app.c.myapplication.R;
import cn.app.c.myapplication.activity.DisplayActivity;
import cn.app.c.myapplication.activity.SoundActivity;

public class MenuFragment extends Fragment implements AdapterView.OnItemClickListener {
    /**
     * 菜单界面中只包含一个ListView。
     */
    private ListView menuListView;

    /**
     * ListView的适配器。
     */
    private ArrayAdapter<String> adapter;

    /**
     * 用于填充ListView的数据，这里就简单只用了两条数据。
     */
    private String[] menuItems = {"Sound", "Display"};

    /**
     * 是否是双页模式。如果一个Activity中包含了两个Fragment，就是双页模式。
     */
    private boolean isTwoPane;

    /**
     * 当Activity和Fragment建立关联时，初始化适配器中的数据。
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, menuItems);
    }

    /**
     * 加载menu_fragment布局文件，为ListView绑定了适配器，并设置了监听事件。
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);
        menuListView = view.findViewById(R.id.menu_list_view);
        menuListView.setAdapter(adapter);
        menuListView.setOnItemClickListener(this);
        return view;
    }

    /**
     * 当Activity创建完毕后，尝试获取一下布局文件中是否有details_layout这个元素，如果有说明当前
     * 是双页模式，如果没有说明当前是单页模式。
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.details_layout) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }
    }

    /**
     * 处理ListView的点击事件，会根据当前是否是双页模式进行判断。如果是双页模式，则会动态添加Fragment。
     * 如果不是双页模式，则会打开新的Activity。
     */
    @Override
    public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
        if (isTwoPane) {
            Fragment fragment = null;
            switch (i) {
                case 0:
                    fragment = new SoundFragment();
                    break;
                case 1:
                    fragment = new DisplayFragment();
                    break;
            }
            getFragmentManager().beginTransaction().replace(R.id.details_layout, fragment).commit();
        } else {
            Intent intent = null;
            switch (i) {
                case 0:
                    intent = new Intent(getActivity(), SoundActivity.class);
                    break;
                case 1:
                    intent = new Intent(getActivity(), DisplayActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
