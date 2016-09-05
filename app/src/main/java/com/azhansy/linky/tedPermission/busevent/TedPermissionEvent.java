package com.azhansy.linky.tedPermission.busevent;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


/**
 * Created by TedPark on 16. 2. 17..
 */
public class TedPermissionEvent {

    public boolean permission;
    public ArrayList<String> deniedPermissions;


    public TedPermissionEvent(boolean permission, ArrayList<String> deniedPermissions
    ) {
        this.permission = permission;
        this.deniedPermissions = deniedPermissions;
    }



    public boolean hasPermission() {
        return permission;
    }


    public ArrayList<String> getDeniedPermissions() {
        return deniedPermissions;
    }

    public static void post(boolean permission, ArrayList<String> deniedPermissions) {
        EventBus.getDefault().post(new TedPermissionEvent(permission, deniedPermissions));
    }
}
