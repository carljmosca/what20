/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.what20;

import java.util.Locale;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import com.what20.data.DataProvider;
import com.what20.data.db.DbDataProvider;
import com.what20.data.domain.User;
import com.what20.event.What20Event.BrowserResizeEvent;
import com.what20.event.What20Event.CloseOpenWindowsEvent;
import com.what20.event.What20Event.UserLoggedOutEvent;
import com.what20.event.What20Event.UserLoginRequestedEvent;
import com.what20.event.What20EventBus;
import com.what20.view.LoginView;
import com.what20.view.MainView;

@Theme("dashboard")
@Widgetset("com.vaadin.demo.dashboard.DashboardWidgetSet")
@Title("QuickTickets Dashboard")
@SuppressWarnings("serial")
public final class What20UI extends UI {

    /*
     * This field stores an access to the dummy backend layer. In real
     * applications you most likely gain access to your beans trough lookup or
     * injection; and not in the UI but somewhere closer to where they're
     * actually accessed.
     */
    private final DataProvider dataProvider = new DbDataProvider();
    private final What20EventBus what20Eventbus = new What20EventBus();

    @Override
    protected void init(final VaadinRequest request) {
        setLocale(Locale.US);

        What20EventBus.register(this);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        updateContent();

        // Some views need to be aware of browser resize events so a
        // BrowserResizeEvent gets fired to the event bus on every occasion.
        Page.getCurrent().addBrowserWindowResizeListener((final BrowserWindowResizeEvent event) -> {
            What20EventBus.post(new BrowserResizeEvent());
        });
    }

    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    private void updateContent() {
        User user = (User) VaadinSession.getCurrent().getAttribute(
                User.class.getName());
        if (user != null && "admin".equals(user.getRole())) {
            // Authenticated user
            setContent(new MainView());
            removeStyleName("loginview");
            getNavigator().navigateTo(getNavigator().getState());
        } else {
            setContent(new LoginView());
            addStyleName("loginview");
        }
    }

    @Subscribe
    public void userLoginRequested(final UserLoginRequestedEvent event) {
        User user = getDataProvider().authenticate(event.getUserName(),
                event.getPassword());
        VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
        updateContent();
    }

    @Subscribe
    public void userLoggedOut(final UserLoggedOutEvent event) {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't
        // invalidate the current HttpSession.
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }

    @Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
        getWindows().forEach((window) -> {
            window.close();
        });
    }

    /**
     * @return An instance for accessing the (dummy) services layer.
     */
    public static DataProvider getDataProvider() {
        return ((What20UI) getCurrent()).dataProvider;
    }

    public static What20EventBus getWhat20Eventbus() {
        return ((What20UI) getCurrent()).what20Eventbus;
    }
}
