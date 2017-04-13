package com.what20.view.transactions;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.google.common.eventbus.Subscribe;
import com.what20.event.DashboardEvent.BrowserResizeEvent;
import com.what20.event.DashboardEventBus;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings({ "serial", "unchecked" })
public final class TransactionsView extends VerticalLayout implements View {

    private final Grid table;
    private Button createReport;
    private static final DateFormat DATEFORMAT = new SimpleDateFormat(
            "MM/dd/yyyy hh:mm:ss a");
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");
    private static final String[] DEFAULT_COLLAPSIBLE = { "country", "city",
            "theater", "room", "title", "seats" };

    public TransactionsView() {
        setSizeFull();
        addStyleName("transactions");
        DashboardEventBus.register(this);

        addComponent(buildToolbar());

        table = buildGrid();
        addComponent(table);
        setExpandRatio(table, 1);
    }

    @Override
    public void detach() {
        super.detach();
        // A new instance of TransactionsView is created every time it's
        // navigated to so we'll need to clean up references to it on detach.
        DashboardEventBus.unregister(this);
    }

    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        Label title = new Label("Latest Transactions");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        createReport = buildCreateReport();
        HorizontalLayout tools = new HorizontalLayout(
                createReport);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);

        return header;
    }

    private Button buildCreateReport() {
        final Button createReport = new Button("Create Report");
        createReport
                .setDescription("Create a new report from the selected transactions");
        createReport.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {

            }
        });
        createReport.setEnabled(false);
        return createReport;
    }


    private Grid buildGrid() {
        final Grid grid = new Grid();

        grid.setSizeFull();
        grid.addStyleName(ValoTheme.TABLE_BORDERLESS);
        grid.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
        grid.addStyleName(ValoTheme.TABLE_COMPACT);


        grid.setColumnReorderingAllowed(true);


        return grid;
    }

    @Subscribe
    public void browserResized(final BrowserResizeEvent event) {
        // Some columns are collapsed when browser window width gets small
        // enough to make the table fit better.

    }


    @Override
    public void enter(final ViewChangeEvent event) {
    }

    private class TransactionsActionHandler implements Handler {
        private final Action report = new Action("Create Report");

        private final Action discard = new Action("Discard");

        private final Action details = new Action("Movie details");

        @Override
        public void handleAction(final Action action, final Object sender,
                final Object target) {
            if (action == discard) {
                Notification.show("Not implemented in this demo");
            } else if (action == details) {
            }
        }

        @Override
        public Action[] getActions(final Object target, final Object sender) {
            return new Action[] { details, report, discard };
        }
    }

}
