package eu.glowacki.jaxws.client.composite;

import eu.glowacki.jaxws.api.IService;
import eu.glowacki.jaxws.client.composite.proxy.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Logger;

public final class Main {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    static {
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "9999999");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dumpTreshold", "9999999");
    }

    public static void main(String... args) throws MalformedURLException {
        URL wsdl = new URL(eu.glowacki.jaxws.api.composite.IComposite.URI + IService.WSDL_SUFFIX);

        CompositeImplService service = new CompositeImplService(wsdl);
        IComposite proxy = service.getICompositePort();
        PersonModel person = new PersonModel();
        person.setFirstName("p1_name");
        person.setLastName("p2_lname");
        String string = "January 1, 2000";
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        XMLGregorianCalendar birthDate = null;
        XMLGregorianCalendar dateNow = null;
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            Date date = format.parse(string);
            calendar.setTime(date);
            birthDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            calendar.setTime(new Date());
            dateNow = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException | ParseException e) {
            e.printStackTrace();
        }
        person.setBirthDate(birthDate);
        proxy.add(person);
        RequestMessage request = new RequestMessage();
        request.setLastName("p2_lname");
        request.setBirthDate(dateNow);
        ResponseMessage response = proxy.get(request);
        for (PersonModel p : response.getResult()) {
            LOGGER.info(p.toString());
        }
    }
}