package model;

import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.ObjectId;

import javax.validation.Valid;

public class Patient {

    @Id
    @ObjectId
    public String id;

    @Valid
    public PatientData data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PatientData getData() {
        return data;
    }

    public void setData(PatientData data) {
        this.data = data;
    }

}
