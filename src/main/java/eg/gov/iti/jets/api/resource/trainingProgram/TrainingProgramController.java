package eg.gov.iti.jets.api.resource.trainingProgram;

import eg.gov.iti.jets.api.resource.intake.IntakeResponse;
import eg.gov.iti.jets.api.resource.intake.IntakeResponseList;
import eg.gov.iti.jets.api.util.Mapper;
import eg.gov.iti.jets.persistence.dao.BranchDao;
import eg.gov.iti.jets.persistence.entity.Intake;
import eg.gov.iti.jets.persistence.entity.TrainingProgram;
import eg.gov.iti.jets.service.management.IntakeManagement;
import eg.gov.iti.jets.service.management.TrainingProgramManagement;
import eg.gov.iti.jets.service.management.impl.TrainingProgramManagementImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "/api/trainingPrograms" )
public class TrainingProgramController {
    final
    TrainingProgramManagement trainingProgramManagement;
    final
    Mapper mapper;

    public TrainingProgramController( TrainingProgramManagement trainingProgramManagement, Mapper mapper ) {
        this.trainingProgramManagement = trainingProgramManagement;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasAuthority(T(eg.gov.iti.jets.persistence.entity.enums.PrivilegeName).ADD_EDIT_DELETE_TRAINING_PROGRAMS.name())")
    public ResponseEntity<?> createTrainingProgram(@Valid @RequestBody TrainingProgramRequest trainingProgramRequest ) {

        TrainingProgram trainingProgram = mapper.mapFromTrainingProgramRequestToTrainingProgram( trainingProgramRequest );
        Boolean program = trainingProgramManagement.createTrainingProgram( trainingProgram );
        if ( program ) {
            return new ResponseEntity<>( HttpStatus.CREATED );
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT );
        }
    }

    @PutMapping( "/{id}" )
    @PreAuthorize("hasAuthority(T(eg.gov.iti.jets.persistence.entity.enums.PrivilegeName).ADD_EDIT_DELETE_TRAINING_PROGRAMS.name())")
    public ResponseEntity<?> updateTrainingProgram(@Valid @RequestBody TrainingProgramPutRequest trainingProgramPutRequest, @PathVariable int id ) {
        TrainingProgram trainingProgram = trainingProgramManagement.updateTrainingProgram( mapper.mapFromTrainingProgramPutRequestToTrainingProgram( trainingProgramPutRequest, id ) );
        TrainingProgramResponse trainingProgramResponse = mapper.mapFromTrainingProgramToTrainingProgramResponse( trainingProgram );
        return new ResponseEntity<>( trainingProgramResponse, HttpStatus.OK );
    }

//    @
    @GetMapping( "/{id}" )
    @PreAuthorize("hasAuthority(T(eg.gov.iti.jets.persistence.entity.enums.PrivilegeName).VIEW_TRAINING_PROGRAMS.name())")
    public ResponseEntity<?> getTrainingProgramById( @PathVariable int id ) {
        TrainingProgram trainingProgram = trainingProgramManagement.getTrainingProgramById( id );
        TrainingProgramResponse trainingProgramResponse = mapper.mapFromTrainingProgramToTrainingProgramResponse( trainingProgram );
        return new ResponseEntity<>( trainingProgramResponse, HttpStatus.OK );
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(eg.gov.iti.jets.persistence.entity.enums.PrivilegeName).VIEW_TRAINING_PROGRAMS.name())")
    public ResponseEntity<?> getTrainingPrograms() {
        GetTrainingProgramsResponse getTrainingProgramsResponse = new GetTrainingProgramsResponse();
        getTrainingProgramsResponse.setTrainingPrograms( trainingProgramManagement.getAllTrainingPrograms()
                .stream().map( mapper::mapFromTrainingProgramToTrainingProgramResponse )
                .collect( Collectors.toList() ) );
        return new ResponseEntity<>( getTrainingProgramsResponse, HttpStatus.OK );

    }

    @GetMapping( "{trainingProgramId}/intakes" )
    @PreAuthorize("hasAuthority(T(eg.gov.iti.jets.persistence.entity.enums.PrivilegeName).VIEW_TRAINING_PROGRAMS.name())")
    public ResponseEntity<?> getIntakesByTrainingProgramId( @PathVariable int trainingProgramId ) {
        List<Intake> intakes = trainingProgramManagement.getIntakeByProgramId( trainingProgramId );
        List<IntakeResponse> intakeResponse = new ArrayList<>();
        intakes.forEach( intake -> intakeResponse.add( mapper.mapFromIntakeToIntakeResponse( intake ) ) );
        IntakeResponseList intakeResponseList = new IntakeResponseList( intakeResponse );
        return new ResponseEntity<>( intakeResponseList, HttpStatus.OK );
    }


}
