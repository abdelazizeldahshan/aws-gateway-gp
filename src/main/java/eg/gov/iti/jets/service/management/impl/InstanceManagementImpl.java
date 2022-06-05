package eg.gov.iti.jets.service.management.impl;

import eg.gov.iti.jets.persistence.dao.TemplateConfigurationDao;
import eg.gov.iti.jets.persistence.entity.aws.*;
import eg.gov.iti.jets.service.gateway.aws.ec2.AwsGateway;
import eg.gov.iti.jets.service.management.InstanceManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstanceManagementImpl implements InstanceManagement {

    public final AwsGateway awsGateway;
    public final TemplateConfigurationDao templateConfigurationDao;

    public InstanceManagementImpl( AwsGateway awsGateway, TemplateConfigurationDao templateConfigurationDao ) {
        this.awsGateway = awsGateway;
        this.templateConfigurationDao = templateConfigurationDao;
    }


    @Override
    public List<SecurityGroup> describeSecurityGroupsForVpc(String vpcId){
        return awsGateway.describeSecurityGroupsForVpc(vpcId);
    }
    @Override
    public List<String> getInstanceTypes(){
       return awsGateway.getInstanceTypes();
    }

    @Override
    public List<Subnet> getAllSubnet(){
        return awsGateway.describeAllSubnets();
    }

    @Override
    public Optional<Ami> describeAmi(String amiId){
       return awsGateway.describeAmi(amiId);
    }


    private Instance createInstanceAws(TemplateConfiguration templateConfiguration, String instanceName , KeyPair keyPair) {
        return awsGateway.createInstance( templateConfiguration , instanceName, keyPair );
    }

    @Override
    public Instance createInstance( int templateConfigurationId, String instanceName , String keyPair){
        Optional<TemplateConfiguration> byId = templateConfigurationDao.findById( templateConfigurationId );
        KeyPair keyPair1 = awsGateway.createKeyPair( keyPair );
        return byId.map( templateConfiguration -> createInstanceAws( templateConfiguration, instanceName, keyPair1 ) ).orElse( null );

    }


}
