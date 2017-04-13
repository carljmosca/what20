package com.what20.view.sales;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.vaadin.maddon.ListContainer;

import com.what20.What20UI;
import com.what20.domain.Movie;
import com.what20.domain.MovieRevenue;
import com.vaadin.event.Action.Container;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class SalesView extends VerticalLayout implements View {

    private int colorIndex = -1;

    public SalesView() {
        setSizeFull();
        addStyleName("sales");

        addComponent(buildHeader());

        initMovieSelect();
        // Add first 4 by default
        List<Movie> subList = new ArrayList<Movie>(What20UI
                .getDataProvider().getMovies()).subList(0, 4);
        for (Movie m : subList) {
            addDataSet(m);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
    }

    private void initMovieSelect() {
        Collection<Movie> movies = What20UI.getDataProvider().getMovies();
        //Container movieContainer = new ListContainer<Movie>(Movie.class, movies);
    }

    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        Label titleLabel = new Label("Revenue by Movie");
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponents(titleLabel, buildToolbar());

        return header;
    }

    private Component buildToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("toolbar");
        toolbar.setSpacing(true);

        final Button add = new Button("Add");
        add.setEnabled(false);
        add.addStyleName(ValoTheme.BUTTON_PRIMARY);


        final Button clear = new Button("Clear");
        clear.addStyleName("clearbutton");
        clear.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                initMovieSelect();
                clear.setEnabled(false);
            }
        });
        toolbar.addComponent(clear);

        add.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                clear.setEnabled(true);
            }
        });

        return toolbar;
    }


    private void addDataSet(final Movie movie) {

        Collection<MovieRevenue> dailyRevenue = What20UI.getDataProvider()
                .getDailyRevenuesByMovie(movie.getId());

    }

    @Override
    public void enter(final ViewChangeEvent event) {
    }

}
