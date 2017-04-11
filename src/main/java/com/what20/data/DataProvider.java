/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.what20.data;

import com.what20.data.domain.Widget;
import java.util.Collection;

public interface DataProvider {

    Collection<Widget> getWidgets();

    Widget getMovie(long widgetId);

    
}
