package Logic;

import DAL.IRepository;
import DAL.PostgresRepository;
import DTOs.Requests.ListResourcesReq;
import DTOs.Requests.LoginReq;
import DTOs.Requests.ResourceDetailsReq;
import DTOs.Responses.ListResourcesRes;
import DTOs.Responses.LoginRes;
import DTOs.Responses.ResourceDetailsRes;

public class Logic {

    private final IRepository _repository = new PostgresRepository();

    public LoginRes login(LoginReq request) {
        return _repository.login(request);
    }

    public ListResourcesRes listResources(ListResourcesReq request) {
        return _repository.listResources(request);
    }

    public ResourceDetailsRes getResourceDetails(ResourceDetailsReq request) {
        return _repository.getResourceDetails(request);
    }

}
