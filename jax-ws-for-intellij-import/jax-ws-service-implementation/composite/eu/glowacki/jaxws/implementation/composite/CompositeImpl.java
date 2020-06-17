package eu.glowacki.jaxws.implementation.composite;

import eu.glowacki.jaxws.api.composite.IComposite;
import eu.glowacki.jaxws.api.composite.Person;
import eu.glowacki.jaxws.api.composite.Request;
import eu.glowacki.jaxws.api.composite.Response;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebService( //
        name = "IComposite", //
        targetNamespace = "http://glowacki.eu/composite" //
)
public final class CompositeImpl implements IComposite {

    private Set<Person> allPeople = new HashSet<>();
    private Map<String, Collection<Person>> byLastName = new HashMap<>();
    private Map<Date, Collection<Person>> byBirthDate = new HashMap<>();

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public static void main(String... args) {
        Endpoint.publish(IComposite.URI, new CompositeImpl());
        LOGGER.info("SERVICE STARTED");
    }

    private CompositeImpl() {

    }

    public Response get(Request request) {
        Collection<Person> result = null;
        Collection<Person> byLastNameResult =  byLastName.get(request.lastName);
        Collection<Person> byBirthDateResult =  byBirthDate.get(request.birthDate);
        if (request.birthDate == null && request.lastName == null) {
            result = allPeople;
        }
        else if (request.birthDate == null) {
            result = byLastNameResult;
        }
        else if (request.lastName == null) {
            result = byBirthDateResult;
        } else if (byLastNameResult != null && byBirthDateResult != null){
            result = byLastNameResult.stream()
                    .filter(byBirthDateResult::contains)
                    .collect(Collectors.toSet());
        }
        if(result == null) {
            result = new HashSet<>();
        }
        LOGGER.info("size " + result.size() + " " + result.toString());
        return new Response(result);
    }

    @Override
    public void add(Person person) {
        if (person != null) {
            if (!byLastName.containsKey(person.lastName)) {
                Collection<Person> people = new HashSet<>();
                people.add(person);
                byLastName.put(person.lastName, people);
            } else {
                byLastName.get(person.lastName).add(person);
            }
            if (!byBirthDate.containsKey(person.birthDate)) {
                Collection<Person> people = new HashSet<>();
                people.add(person);
                byBirthDate.put(person.birthDate, people);
            } else {
                byBirthDate.get(person.birthDate).add(person);
            }
            allPeople.add(person);
        }
    }
}