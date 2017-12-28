package kr.sofac.jangsisters.models;

import java.util.ArrayList;
import java.util.List;

import kr.sofac.jangsisters.R;

public class TabManager {

    private static TabManager tabManager;

    private List<Integer> menus;
    private List<Integer> drawables;
    private List<String> names;

    public static TabManager getTabManager(){
        if (tabManager == null){
            tabManager = new TabManager();
        }
        return tabManager;
    }

    private TabManager() {
        fillMenus();
        fillDrawables();
        fillNames();
    }

    private void fillNames() {
        names = new ArrayList<>();
        names.add("");
        names.add("");
        names.add("");
        names.add("");
        names.add("");
    }

    private void fillDrawables() {
        drawables = new ArrayList<>();
        drawables.add(R.drawable.selector_shop);
        drawables.add(R.drawable.selector_search);
        drawables.add(R.drawable.selector_home);
        drawables.add(R.drawable.selector_chat);
        drawables.add(R.drawable.selector_profile);
    }

    private void fillMenus() {
        menus = new ArrayList<>();
        menus.add(R.menu.menu_toolbar_shop);
        menus.add(R.menu.menu_toolbar_search);
        menus.add(R.menu.menu_toolbar_home);
        menus.add(R.menu.menu_toolbar_profile);
        menus.add(R.menu.menu_toolbar_profile);
    }

    public int getMenuByPosition(int position){
        return menus.get(position);
    }

    public int getDrawableByPosition(int position){
        return drawables.get(position);
    }

    public String getNameByPosition(int position){
        return names.get(position);
    }
}
