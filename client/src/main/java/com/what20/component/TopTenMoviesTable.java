package com.what20.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.what20.What20UI;
import com.what20.domain.MovieRevenue;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public final class TopTenMoviesTable extends Grid {


    public TopTenMoviesTable() {
        setCaption("Top 10 Titles by Revenue");

        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSizeFull();

        List<MovieRevenue> movieRevenues = new ArrayList<MovieRevenue>(
                What20UI.getDataProvider().getTotalMovieRevenues());
        Collections.sort(movieRevenues, new Comparator<MovieRevenue>() {
            @Override
            public int compare(final MovieRevenue o1, final MovieRevenue o2) {
                return o2.getRevenue().compareTo(o1.getRevenue());
            }
        });

    }

}
