package DAL;

import DTOs.Requests.ListResourcesReq;
import DTOs.Requests.LoginReq;
import DTOs.Requests.ResourceDetailsReq;
import DTOs.Responses.ListResourcesRes;
import DTOs.Responses.LoginRes;
import DTOs.Responses.ResourceDetailsRes;

public interface IRepository {

    LoginRes login(LoginReq request) throws Exception;

    ListResourcesRes listResources(ListResourcesReq request) throws Exception;

    ResourceDetailsRes getResourceDetails(ResourceDetailsReq request) throws Exception;

}
