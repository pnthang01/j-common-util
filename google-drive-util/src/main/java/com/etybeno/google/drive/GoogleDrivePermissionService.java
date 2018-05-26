package com.etybeno.google.drive;

import com.etybeno.google.drive.model.GoogleDriveConnection;
import com.etybeno.google.drive.model.enums.Role;
import com.etybeno.google.drive.model.enums.Type;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;

import java.io.IOException;

/**
 * Created by thangpham on 19/04/2018.
 */
public class GoogleDrivePermissionService {

    public static Drive.Permissions.Create createPermissionRequest(GoogleDriveConnection con, String fileId, Type type,
                                                            Role role, boolean withLink) throws IOException {
        return con.drive().permissions().create(fileId, createPermission(fileId, type, role, withLink));
    }

    public static Permission createPermission(String fileId, Type type, Role role, boolean withLink) {
        return new Permission()
                .setType(type.getValue())
                .setRole(role.getValue())
                .setAllowFileDiscovery(withLink);
    }

}
