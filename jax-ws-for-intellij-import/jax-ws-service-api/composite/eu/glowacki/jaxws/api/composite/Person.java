package eu.glowacki.jaxws.api.composite;

import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.Objects;

@XmlType( //
        name = "PersonModel", // name of the XmlType should be different from the name of the class
        namespace = "http://glowacki.eu/composite" //
)
public class Person {

    public String firstName;
    public String lastName;
    public Date birthDate;

    public Person() {
    }

    public Person(String firstName, String lastName, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(birthDate, person.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
