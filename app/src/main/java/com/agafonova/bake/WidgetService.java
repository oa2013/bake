package com.agafonova.bake;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Olga Agafonova on 8/26/18.
 *
 *  This class is based on the tutorial here:
 * https://github.com/tashariko/widget_sample/
 */

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakeRemoteViewFactory(this.getApplicationContext(), intent);
    }
}
