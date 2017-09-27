package me.lam.maidong.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 2017/6/15.
 */
public class NewsList {

    public List<newsEntity.NewsListEntity> newsListEntities=  new ArrayList<>();

    private static NewsList newsList;
    private NewsList() {
    }
    public static NewsList getInstance() {

        if (newsList == null) {
            synchronized (NewsList.class) {
                if (newsList == null) {
                    newsList = new NewsList();
                }
            }
        }
        return newsList;
    }
}
