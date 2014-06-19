package service;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import configuration.PatientConfiguration;
import health.MongoHealthCheck;
import model.Patient;
import net.vz.mongodb.jackson.JacksonDBCollection;
import resources.patient.PatientResource;

public class PatientService extends Service<PatientConfiguration> {

    public static void main(String[] args) throws Exception {
        new PatientService().run(new String[]{"server", "src/main/resources/config.yaml"});
    }

    private PatientService() {
        super("app");
    }

    @Override
    protected void initialize(PatientConfiguration configuration, Environment environment) throws Exception {
        Mongo mongo = new Mongo(configuration.mongohost, configuration.mongoport);
        DB db = mongo.getDB(configuration.mongodb);
        JacksonDBCollection<Patient, String> patients =
                JacksonDBCollection.wrap(db.getCollection("patients"), Patient.class, String.class);

        MongoManaged mongoManaged = new MongoManaged(mongo);
        environment.manage(mongoManaged);

        environment.addHealthCheck(new MongoHealthCheck(mongo));

        environment.addResource(new PatientResource(patients));
    }

}
