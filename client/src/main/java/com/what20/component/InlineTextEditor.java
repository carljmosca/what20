package com.what20.component;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class InlineTextEditor extends CustomComponent {

    /*
     * This Property contains the String type value to be edited. The same
     * object is passed as content source for the label in read-only mode as
     * well as the data source for the RichTextArea in edit mode. From there on
     * synchronization between the two is automatic.
     */
    private final Component editor;
    private final Component readOnly;

    public InlineTextEditor(final String initialValue) {
        setWidth(100.0f, Unit.PERCENTAGE);
        addStyleName("inline-text-editor");

        editor = buildEditor();
        readOnly = buildReadOnly();

        setCompositionRoot(editor);
    }

    private Component buildReadOnly() {

        Button editButton = new Button(VaadinIcons.EDIT);
        editButton.addStyleName(ValoTheme.BUTTON_SMALL);
        editButton.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        editButton.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                setCompositionRoot(editor);
            }
        });

        CssLayout result = new CssLayout(editButton);
        result.addStyleName("text-editor");
        result.setSizeFull();
        result.addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(final LayoutClickEvent event) {
            }
        });
        return result;
    }

    private Component buildEditor() {
        final RichTextArea rta = new RichTextArea();
        rta.setWidth(100.0f, Unit.PERCENTAGE);
        rta.addAttachListener(new AttachListener() {
            @Override
            public void attach(final AttachEvent event) {
                rta.focus();
                rta.selectAll();
            }
        });

        Button save = new Button("Save");
        save.setDescription("Edit");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addStyleName(ValoTheme.BUTTON_SMALL);
        save.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                setCompositionRoot(readOnly);
            }
        });

        CssLayout result = new CssLayout(rta, save);
        result.addStyleName("edit");
        result.setSizeFull();
        return result;
    }

}
