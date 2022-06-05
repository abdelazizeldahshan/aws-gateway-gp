package eg.gov.iti.jets.persistence.dao;

import eg.gov.iti.jets.persistence.entity.aws.InstanceLogs;
import eg.gov.iti.jets.persistence.entity.enums.UserAction;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InstanceLogsDao extends GenericCrudDao<InstanceLogs, Long> {

    Optional<InstanceLogs> findLastLogByActionAndInstanceId(UserAction action,int instanceId);
    boolean deleteInstanceLogByDate(LocalDateTime localDateTime);
    List<InstanceLogs> findAllByInstanceId(Long instanceId, int pageNumber, int pageSize);
    List<InstanceLogs> findAllByActionMakerAndAction(int id, UserAction userAction, int pageNumber, int pageSize);


    List<InstanceLogs> findAllByAction_TerminateInstance(String terminateInstance);
    InstanceLogs findLatestTerminateInstanceById(Long instanceId);


}
