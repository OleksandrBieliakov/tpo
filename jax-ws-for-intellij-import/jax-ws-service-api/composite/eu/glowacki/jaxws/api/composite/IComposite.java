package eu.glowacki.jaxws.api.composite;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(
        name = "IComposite", //
        targetNamespace = "http://glowacki.eu/composite" //
)
public interface IComposite {

    String URI = "http://localhost:8080/composite";

    @WebMethod(action = "http://glowacki.eu/composite/get")
    Response get(Request request);

    @WebMethod(action = "http://glowacki.eu/composite/add")
    void add(Person person);
}