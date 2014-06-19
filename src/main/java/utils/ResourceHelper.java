package utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import net.vz.mongodb.jackson.DBCursor;

public class ResourceHelper {

    public static void notFoundIfNull(DBCursor<?> cursor) {
        if (!cursor.hasNext()) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }
    }

}