package com.etybeno.google.drive;

import com.etybeno.google.drive.model.enums.Role;
import com.etybeno.google.drive.model.enums.Type;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;

import java.io.IOException;

/**
 * Created by thangpham on 19/04/2018.
 */
public class GoogleDrivePermissionService {

    private static GoogleDrivePermissionService _instance;

    public static GoogleDrivePermissionService _load() throws Exception {
        if(null == _instance) _instance = new GoogleDrivePermissionService();
        return _instance;
    }

    private GoogleDriveService driveService;

    public GoogleDrivePermissionService() throws Exception {
        driveService = GoogleDriveService._load();
    }


    public Drive.Permissions.Create createPermissionRequest(String fileId, Type type, Role role, boolean withLink) throws IOException {
        return driveService.drive().permissions().create(fileId, createPermission(fileId, type, role, withLink));
    }

    public Permission createPermission(String fileId, Type type, Role role, boolean withLink) {
        return new Permission()
                .setType(type.getValue())
                .setRole(role.getValue())
                .setAllowFileDiscovery(withLink);
    }

}
