package kr.sofac.jangsisters.models;

import java.util.ArrayList;
import java.util.List;

import kr.sofac.jangsisters.R;

/**
 * Created by Sasha on 20.12.2017.
 */

public class Constants {

    public static List<String> tabNames(){
        List<String> names = new ArrayList<>();
        names.add("");
        names.add("");
        names.add("");
        names.add("");
        names.add("");
        return names;
    }

    public static List<Integer> toolbarMenus(){
        List<Integer> menus = new ArrayList<>();
        menus.add(R.menu.menu_toolbar_shop);
        menus.add(R.menu.menu_toolbar_search);
        menus.add(R.menu.menu_toolbar_home);
        menus.add(R.menu.menu_toolbar_home);
        menus.add(R.menu.menu_toolbar_profile);
        return menus;
    }

    public static final String SHOP_URL = "https://m.rozetka.com.ua/";
}
