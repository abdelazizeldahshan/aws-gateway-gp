package eg.gov.iti.jets.service.management;

import eg.gov.iti.jets.api.util.Crud;
import eg.gov.iti.jets.service.model.Intake;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntakeManagement implements Crud<Intake> {

    @Override
    public Boolean create(Intake type) {
        return null;
    }

    @Override
    public Intake update(Intake type) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }

    @Override
    public List<Intake> getAll() {
        return null;
    }

    @Override
    public Intake getById(int id) {
        return null;
    }
}
