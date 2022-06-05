package eg.gov.iti.jets.api.resource.instance;


import eg.gov.iti.jets.persistence.entity.aws.Ami;
import eg.gov.iti.jets.persistence.entity.aws.Subnet;
import eg.gov.iti.jets.service.management.impl.InstanceManagementImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/instances")
public class InstanceController {

    private final InstanceManagementImpl instanceManagement;

    public InstanceController( InstanceManagementImpl instanceManagement) {
        this.instanceManagement = instanceManagement;
    }

    @PostMapping("create")
    void createInstances(){
        instanceManagement.createInstances();
    }

    @DeleteMapping("delete")
    void deleteInstances(){
        instanceManagement.deleteInstances();
    }

    @GetMapping
    void getAllInstances(){
        instanceManagement.getAllInstances();
    }

    @GetMapping("{branchName}")
    void getAllBranchInstances(@PathVariable String branchName){
        instanceManagement.getAllBranchInstances(branchName);
    }

    @GetMapping ("{trackName}")
    void getAllTrackInstances(@PathVariable String trackName){
        instanceManagement.getAllTrackInstances(trackName);
    }


    @PostMapping("instnace/assign")
    void assignInstance(){
        instanceManagement.assignInstance();
    }

    @PostMapping("customInstance/create")
    void createInstanceUsingTemplate(){
        instanceManagement.createInstanceUsingTemplate();
    }

    @GetMapping("instance/{id}")
    void getInstance(@PathVariable String id){
        instanceManagement.getInstance(id);
    }

    @GetMapping("instance/start/{id}")
    void startInstance(@PathVariable String id){
        instanceManagement.startInstance(id);
    }

    @GetMapping("instance/stop/{id}")
    void stopInstance(@PathVariable String id){
        instanceManagement.stopInstance(id);
    }


    @GetMapping("types")
    List<String> getInstanceTypes(){
        return  instanceManagement.getInstanceTypes();
    }

    @GetMapping("subnet")
    List<Subnet> getAllSubnet(){
        return  instanceManagement.getAllSubnet();
    }


    @GetMapping("ami/{id}")
    Optional<Ami> describeAmi (@PathVariable String id){
        return  instanceManagement.describeAmi(id);
    }

}