package org.briarproject.masterproject.android.mailbox;

import static android.Manifest.permission.CAMERA;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static org.briarproject.masterproject.android.util.PermissionUtils.showDenialDialog;
import static org.briarproject.masterproject.android.util.PermissionUtils.showRationale;

import android.content.Context;

import androidx.core.util.Consumer;
import androidx.fragment.app.FragmentActivity;

import org.briarproject.briar.R;
import org.briarproject.masterproject.android.util.Permission;

import java.util.Map;

class CameraPermissionManager {

    private final FragmentActivity ctx;
    private final Consumer<String[]> requestPermissions;
    private Permission cameraPermission = Permission.UNKNOWN;

    CameraPermissionManager(FragmentActivity ctx,
                            Consumer<String[]> requestPermissions) {
        this.ctx = ctx;
        this.requestPermissions = requestPermissions;
    }

    private static boolean areEssentialPermissionsGranted(Context ctx) {
        return checkSelfPermission(ctx, CAMERA) == PERMISSION_GRANTED;
    }

    void resetPermissions() {
        cameraPermission = Permission.UNKNOWN;
    }

    private boolean areEssentialPermissionsGranted() {
        return cameraPermission == Permission.GRANTED;
    }

    boolean checkPermissions() {
        if (areEssentialPermissionsGranted()) return true;
        // If an essential permission has been permanently denied, ask the
        // user to change the setting
        if (cameraPermission == Permission.PERMANENTLY_DENIED) {
            showDenialDialog(ctx, R.string.permission_camera_title,
                    R.string.permission_camera_qr_denied_body);
        } else if (cameraPermission == Permission.SHOW_RATIONALE) {
            showRationale(ctx, R.string.permission_camera_title,
                    R.string.permission_camera_request_body,
                    this::requestPermissions);
        } else {
            requestPermissions();
        }
        return false;
    }

    private void requestPermissions() {
        String[] permissions = new String[]{CAMERA};
        requestPermissions.accept(permissions);
    }

    void onRequestPermissionResult(Map<String, Boolean> result) {
        if (gotPermission(result)) {
            cameraPermission = Permission.GRANTED;
        } else if (shouldShowRequestPermissionRationale(ctx, CAMERA)) {
            cameraPermission = Permission.SHOW_RATIONALE;
        } else {
            cameraPermission = Permission.PERMANENTLY_DENIED;
        }
    }

    private boolean gotPermission(Map<String, Boolean> result) {
        Boolean permissionResult = result.get(CAMERA);
        return permissionResult == null ? areEssentialPermissionsGranted(ctx) :
                permissionResult;
    }

}
