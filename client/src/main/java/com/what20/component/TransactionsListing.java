package com.what20.component;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

import com.what20.domain.Transaction;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class TransactionsListing extends CssLayout {

    private final DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

    public TransactionsListing(final Collection<Transaction> transactions) {
        addComponent(new Label("<strong>Selected transactions</strong>"));

        if (transactions != null) {
            for (Transaction transaction : transactions) {
                CssLayout transationLayout = new CssLayout();
                transationLayout.addStyleName("transaction");

                Label content = new Label(df.format((transaction.getTime()))
                        + "<br>" + transaction.getCity() + ", "
                        + transaction.getCountry());
                content.setSizeUndefined();
                content.addStyleName("time");
                transationLayout.addComponent(content);

                content = new Label(transaction.getTitle());
                content.setSizeUndefined();
                content.addStyleName("movie-title");
                transationLayout.addComponent(content);

                content = new Label("Seats: "
                        + transaction.getSeats()
                        + "<br>"
                        + "Revenue: $"
                        + new DecimalFormat("#.##").format(transaction
                                .getPrice()));
                content.setSizeUndefined();
                content.addStyleName("seats");
                transationLayout.addComponent(content);

                addComponent(transationLayout);
            }

        }
    }

}
