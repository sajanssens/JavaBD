package org.example.resources;

import org.example.App;
import org.example.domain.Contact;
import org.example.domain.ContactDaoDB;
import org.example.domain.IContactDao;
import org.example.domain.Laptop;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.net.URL;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
public class ContactsResourceIT {

    @ArquillianResource
    private URL deploymentURL;

    private String contactsResource;
    private String contactsUri = "api/contacts";

    @Before
    public void setup() {
        contactsResource = deploymentURL + contactsUri;
    }

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "ContactsResourceIT.war")
                .addClass(App.class) // dont forget!
                .addClass(JsonResource.class)
                .addClass(ContactsResource.class)
                .addClass(Contact.class)
                .addClass(Laptop.class)
                .addClass(IContactDao.class)
                .addClass(ContactDaoDB.class)
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
        System.out.println(archive.toString(true));
        return archive;
    }

    @Test
    public void get() {
        Client http = ClientBuilder.newClient();
        String message = http
                .target(contactsResource)
                .request().get(String.class);

        assertThat(message, containsString("surname"));
        assertThat(message, containsString("firstName"));
        assertThat(message, containsString("email"));
    }
}
