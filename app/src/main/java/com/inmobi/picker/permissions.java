package com.inmobi.picker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class permissions extends Activity implements View.OnClickListener  {
    private final static int COARSE_LOCATION_RESULT = 100;
    private final static int FINE_LOCATION_RESULT = 101;
    private final static int ALL_PERMISSIONS_RESULT = 103;


    private SharedPreferences sharedPreferences;
    private Button btnLocationFine, btnLocationCoarse, btnRequestAll;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected;
    private View coordinatorLayoutView;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        //for determining if we have asked the questions..
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        btnLocationFine = (Button)findViewById(R.id.getFinePermission);
        btnLocationCoarse = (Button)findViewById(R.id.getCoarsePermission);

        btnLocationFine.setOnClickListener(this);
        btnLocationCoarse.setOnClickListener(this);

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        ArrayList<String> permissions = new ArrayList<>();
        int resultCode = 0;
        switch(v.getId()){
            case R.id.getFinePermission:
                permissions.add(ACCESS_FINE_LOCATION);
                resultCode = FINE_LOCATION_RESULT;
                break;
            case R.id.getCoarsePermission:
                permissions.add(ACCESS_COARSE_LOCATION);
                resultCode = COARSE_LOCATION_RESULT;
                break;
//            case R.id.btnRequestAll:
//                permissions.add(ACCESS_FINE_LOCATION);
//                permissions.add(ACCESS_COARSE_LOCATION);
//                permissions.add(CAMERA);
//                permissions.add(READ_CONTACTS);
//                permissions.add(RECORD_AUDIO);
//                permissions.add(CALL_PHONE);
//                permissions.add(WRITE_EXTERNAL_STORAGE);
//                resultCode = ALL_PERMISSIONS_RESULT;
//                break;
        }

        //filter out the permissions we have already accepted
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.
        permissionsRejected = findRejectedPermissions(permissions);

        if(permissionsToRequest.size()>0){//we need to ask for permissions
            //but have we already asked for them?
            requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), resultCode);
            //mark all these as asked..
            for(String perm : permissionsToRequest){
                markAsAsked(perm);
            }
        }else{
            //show the success banner
            if(permissionsRejected.size()<permissions.size()){
                //this means we can show success because some were already accepted.
            }

            if(permissionsRejected.size()>0){
                //we have none to request but some previously rejected..tell the user.
                //It may be better to show a dialog here in a prod application

            }
        }

    }


    /**
     * This is the method that is hit after the user accepts/declines the
     * permission you requested. For the purpose of this example I am showing a "success" header
     * when the user accepts the permission and a snackbar when the user declines it.  In your application
     * you will want to handle the accept/decline in a way that makes sense.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case FINE_LOCATION_RESULT:
                if (hasPermission(ACCESS_FINE_LOCATION)) {
                } else {
                    permissionsRejected.add(ACCESS_FINE_LOCATION);
                }
                break;
            case COARSE_LOCATION_RESULT:
                if (hasPermission(ACCESS_COARSE_LOCATION)) {
                } else {
                    permissionsRejected.add(ACCESS_COARSE_LOCATION);
                }
                break;
            case ALL_PERMISSIONS_RESULT:
                boolean someAccepted = false;
                boolean someRejected = false;
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {
                        someAccepted = true;
                    } else {
                        someRejected = true;
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    someRejected = true;
                }

                if (someAccepted) {
                }
                if (someRejected) {
                }
                break;
        }

    }

    /**
     * method that will return whether the permission is accepted. By default it is true if the user is using a device below
     * version 23
     *
     * @param permission
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    /**
     * method to determine whether we have asked
     * for this permission before.. if we have, we do not want to ask again.
     * They either rejected us or later removed the permission.
     *
     * @param permission
     * @return
     */
    private boolean shouldWeAsk(String permission) {
        return (sharedPreferences.getBoolean(permission, true));
    }

    /**
     * we will save that we have already asked the user
     *
     * @param permission
     */
    private void markAsAsked(String permission) {
        sharedPreferences.edit().putBoolean(permission, false).apply();
    }

    /**
     * We may want to ask the user again at their request.. Let's clear the
     * marked as seen preference for that permission.
     *
     * @param permission
     */
    private void clearMarkAsAsked(String permission) {
        sharedPreferences.edit().putBoolean(permission, true).apply();
    }


    /**
     * This method is used to determine the permissions we do not have accepted yet and ones that we have not already
     * bugged the user about.  This comes in handle when you are asking for multiple permissions at once.
     *
     * @param wanted
     * @return
     */
    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm) && shouldWeAsk(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    /**
     * this will return us all the permissions we have previously asked for but
     * currently do not have permission to use. This may be because they declined us
     * or later revoked our permission. This becomes useful when you want to tell the user
     * what permissions they declined and why they cannot use a feature.
     *
     * @param wanted
     * @return
     */
    private ArrayList<String> findRejectedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm) && !shouldWeAsk(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    /**
     * Just a check to see if we have marshmallows (version 23)
     *
     * @return
     */
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

}