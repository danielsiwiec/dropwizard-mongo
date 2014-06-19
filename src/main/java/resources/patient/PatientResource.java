package resources.patient;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import model.Patient;
import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static utils.ResourceHelper.notFoundIfNull;

@Path("/patient")
public class PatientResource {

    private JacksonDBCollection<Patient, String> patients;

    public PatientResource(JacksonDBCollection<Patient, String> patients) {
        this.patients = patients;
    }

    @GET
    @Path("/{id}")
    public Patient getPatient(@PathParam("id") String id) {
        DBCursor<Patient> cursor = patients.find().is("data.patientId", id);
        notFoundIfNull(cursor);

        return cursor.next();
    }

    @PUT
    public Response createPatient(@Valid Patient patient) {
        DBCursor<Patient> cursor = patients.find().is("data.patientId", patient.data.patientId);
        if (cursor.hasNext()) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        patients.save(patient);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response removePatient(@PathParam("id") String id) {
        DBObject query = new BasicDBObject("data.patientId", id);
        WriteResult<Patient, String> result = patients.remove(query);
        int affectedObjects = result.getWriteResult().getN();

        if (affectedObjects != 1) {
            return Response.serverError().build();
        }
        return Response.noContent().build();
    }
}