package com.github.jvanheesch.wicket;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.resource.PackageResourceReference;

public class HomePage extends WebPage {
    private static final long serialVersionUID = -2661623348955324736L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new Label("styledLabel", "styledLabel"));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(CssHeaderItem.forReference(new PackageResourceReference(HomePage.class, "stylesheet.css")));
    }
}
