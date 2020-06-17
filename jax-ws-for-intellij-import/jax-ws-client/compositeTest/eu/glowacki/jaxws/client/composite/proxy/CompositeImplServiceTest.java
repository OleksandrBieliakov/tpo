package eu.glowacki.jaxws.client.composite.proxy;

import eu.glowacki.jaxws.api.IService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

import static org.junit.jupiter.api.Assertions.*;

class CompositeImplServiceTest {

    private static PersonModel person1 = new PersonModel();
    private static PersonModel person2 = new PersonModel();
    private static PersonModel person3 = new PersonModel();
    private static PersonModel person4 = new PersonModel();
    private static PersonModel person5 = new PersonModel();
    private static PersonModel person6 = new PersonModel();

    private static final String name1 = "name1";
    private static final String name2 = "name2";
    private static final String name3 = "name3";
    private static final String name4 = "name4";
    private static final String name5 = "name5";
    private static final String name6 = "name6";

    private static final String l_name1 = "l_name1";
    private static final String l_name2 = "l_name2";
    private static final String l_name3 = "l_name3";
    private static final String l_name_lacking = "lacking";

    private static final String dateString1 = "January 1, 2001";
    private static final String dateString2 = "January 1, 2002";
    private static final String dateString3 = "January 1, 2003";
    private static final String dateString_lacking = "January 1, 2000";

    private static final XMLGregorianCalendar birthDate1 = getDate(dateString1);
    private static final XMLGregorianCalendar birthDate2 = getDate(dateString2);
    private static final XMLGregorianCalendar birthDate3 = getDate(dateString3);
    private static final XMLGregorianCalendar birthDate_lacking = getDate(dateString_lacking);

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private static IComposite proxy;

    static {
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "9999999");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dumpTreshold", "9999999");
    }

    private static XMLGregorianCalendar getDate(String string) {
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        XMLGregorianCalendar birthDate = null;
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            Date date = format.parse(string);
            calendar.setTime(date);
            birthDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException | ParseException e) {
            e.printStackTrace();
        }
        return birthDate;
    }

    @BeforeAll
    static void addPeople() {
        URL wsdl = null;
        try {
            wsdl = new URL(eu.glowacki.jaxws.api.composite.IComposite.URI + IService.WSDL_SUFFIX);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail();
        }
        CompositeImplService service = new CompositeImplService(wsdl);
        proxy = service.getICompositePort();

        person1.setFirstName(name1);
        person2.setFirstName(name2);
        person3.setFirstName(name3);
        person4.setFirstName(name4);
        person5.setFirstName(name5);
        person6.setFirstName(name6);

        person1.setLastName(l_name1);
        person2.setLastName(l_name1);
        person3.setLastName(l_name2);
        person4.setLastName(l_name2);
        person5.setLastName(l_name3);
        person6.setLastName(l_name3);

        person1.setBirthDate(birthDate1);
        person2.setBirthDate(birthDate2);
        person3.setBirthDate(birthDate1);
        person4.setBirthDate(birthDate2);
        person5.setBirthDate(birthDate3);
        person6.setBirthDate(birthDate3);

        proxy.add(person1);
        proxy.add(person2);
        proxy.add(person3);
        proxy.add(person4);
        proxy.add(person5);
        proxy.add(person6);
    }

    @Test
    void get_by_none() {
        RequestMessage request = new RequestMessage();
        ResponseMessage response = proxy.get(request);
        Assertions.assertEquals(6, response.result.size());
    }

    @Test
    void get_by_date() {
        RequestMessage request = new RequestMessage();
        request.setBirthDate(birthDate_lacking);
        assertNotNull(proxy);
        ResponseMessage response = proxy.get(request);
        assertNull(response.result);

        request = new RequestMessage();
        request.setBirthDate(birthDate1);
        response = proxy.get(request);
        Assertions.assertEquals(2, response.result.size());
        Assertions.assertTrue(response.result.contains(person1));
        Assertions.assertTrue(response.result.contains(person3));
    }

    @Test
    void get_by_lastName() {
        RequestMessage request = new RequestMessage();
        request.setLastName(l_name_lacking);
        ResponseMessage response = proxy.get(request);
        assertNull(response.result);

        request = new RequestMessage();
        request.setLastName(l_name1);
        response = proxy.get(request);
        Assertions.assertEquals(2, response.result.size());
        Assertions.assertTrue(response.result.contains(person1));
        Assertions.assertTrue(response.result.contains(person2));
    }

    @Test
    void get_by_date_and_lastName() {
        RequestMessage request = new RequestMessage();
        request.setLastName(l_name_lacking);
        request.setBirthDate(birthDate_lacking);
        ResponseMessage response = proxy.get(request);
        assertNull(response.result);

        request = new RequestMessage();
        request.setLastName(l_name1);
        request.setBirthDate(birthDate_lacking);
        response = proxy.get(request);
        assertNull(response.result);

        request = new RequestMessage();
        request.setLastName(l_name_lacking);
        request.setBirthDate(birthDate1);
        response = proxy.get(request);
        assertNull(response.result);

        request = new RequestMessage();
        request.setLastName(l_name1);
        request.setBirthDate(birthDate1);
        response = proxy.get(request);
        Assertions.assertEquals(1, response.result.size());
        Assertions.assertTrue(response.result.contains(person1));

        request = new RequestMessage();
        request.setLastName(l_name3);
        request.setBirthDate(birthDate3);
        response = proxy.get(request);
        Assertions.assertEquals(2, response.result.size());
        Assertions.assertTrue(response.result.contains(person5));
        Assertions.assertTrue(response.result.contains(person6));
    }
}