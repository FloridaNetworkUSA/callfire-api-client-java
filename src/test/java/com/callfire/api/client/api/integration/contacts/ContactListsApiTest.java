package com.callfire.api.client.api.integration.contacts;

import com.callfire.api.client.CallfireClient;
import com.callfire.api.client.JsonConverter;
import com.callfire.api.client.api.common.model.Page;
import com.callfire.api.client.api.common.model.ResourceId;
import com.callfire.api.client.api.contacts.ContactListsApi;
import com.callfire.api.client.api.contacts.model.Contact;
import com.callfire.api.client.api.contacts.model.ContactList;
import com.callfire.api.client.api.contacts.model.request.*;
import com.callfire.api.client.api.integration.AbstractIntegrationTest;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * integration tests for /contacts/lists api endpoint
 */
public class ContactListsApiTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException ex = ExpectedException.none();

    @Test
    public void testFindContactLists() throws Exception {
        CallfireClient client = getCallfireClient();

        FindContactListsRequest request = FindContactListsRequest.create().build();
        Page<ContactList> contactLists = client.getContactsApi().getContactListsApi().findContactLists(request);
        System.out.println(contactLists);
    }

    @Test
    public void testDynamicPropertiesSerializationStringNumbers() throws Exception {
        // contactNumbers
        CreateContactListRequest requestString = CreateContactListRequest.<String>create()
            .name("listFromNumbers")
            .contacts(asList("12345678881", "12345678882"))
            .build();
        JsonConverter jsonConverter = new JsonConverter();
        String serialized = jsonConverter.serialize(requestString);
        System.out.println("contactNumbers: " + serialized);
        assertThat(serialized, containsString("\"contactNumbers\":"));
    }

    @Test
    public void testDynamicPropertiesSerializationContactIds() throws Exception {
        JsonConverter jsonConverter = new JsonConverter();
        // contactIds
        CreateContactListRequest requestLong = CreateContactListRequest.<Long>create()
            .name("listFromIds")
            .contacts(asList(123L, 456L))
            .build();

        String serialized = jsonConverter.serialize(requestLong);
        System.out.println("contactIds: " + serialized);
        assertThat(serialized, containsString("\"contactIds\":"));
        assertThat(serialized, containsString("\"listFromIds\""));
    }

    @Test
    public void testDynamicPropertiesSerializationContactPojos() throws Exception {
        JsonConverter jsonConverter = new JsonConverter();
        Contact c1 = new Contact();
        c1.setFirstName("name1");
        Contact c2 = new Contact();
        c2.setFirstName("name2");
        // contacts
        CreateContactListRequest requestObjects = CreateContactListRequest.<Contact>create()
            .name("listFromContacts")
            .contacts(asList(c1, c2))
            .build();
        String serialized = jsonConverter.serialize(requestObjects);
        System.out.println("contacts: " + serialized);
        assertThat(serialized, containsString("\"contacts\":"));
        assertThat(serialized, containsString("\"listFromContacts\""));
    }

    @Test
    public void testDynamicPropertiesSerializationWithOtherProps() throws Exception {
        AddContactListItemsRequest requestObjects = AddContactListItemsRequest.<Long>create()
            .contactNumbersField("field")
            .contactListId(5L)
            .contacts(asList(1L, 2L))
            .build();
        JsonConverter jsonConverter = new JsonConverter();
        String serialized = jsonConverter.serialize(requestObjects);
        System.out.println("contactIds: " + serialized);
        assertThat(serialized, containsString("\"contactIds\":"));
        assertThat(serialized, containsString("\"contactNumbersField\":\"field\""));
        assertThat(serialized, not(containsString("\"contactListId\":")));
    }

    @Test
    @Ignore("not implemented on server")
    // TODO vmikhailov uncomment when logic will be ready
    public void testCreateContactListFromFile() throws Exception {
        CallfireClient client = getCallfireClient();
        File file = new File(getClass().getClassLoader().getResource("file-examples/contacts1.csv").toURI());
        ContactListsApi api = client.getContactsApi().getContactListsApi();
        ResourceId id = api.createContactListFromFile("fileList", file);

        ContactList contactList = api.getContactList(id.getId());
        System.out.println(contactList);
        assertEquals(Integer.valueOf(3), contactList.getSize());
        assertEquals("fileList", contactList.getName());
    }

    @Test
    public void testContactListCRUD() throws Exception {
        CallfireClient client = getCallfireClient();
        ContactListsApi api = client.getContactsApi().getContactListsApi();

        // create from numbers
        CreateContactListRequest request = CreateContactListRequest.<String>create()
            .name("listFromNumbers")
            .contacts(asList("12135678881", "12135678882"))
            .build();
        ResourceId numbersListId = api.createContactList(request);
        ContactList contactList = api.getContactList(numbersListId.getId());
        assertEquals("listFromNumbers", contactList.getName());
        assertEquals(Integer.valueOf(2), contactList.getSize());

        // get items
        GetContactListItemsRequest getItemsRequest = GetContactListItemsRequest.create()
            .id(numbersListId.getId())
            .build();
        Page<Contact> contactListItems = api.getContactListItems(getItemsRequest);
        List<Contact> items = contactListItems.getItems();
        assertEquals(2, items.size());

        // create from ids
        request = CreateContactListRequest.<Long>create()
            .name("listFromExistingContacts")
            .contacts(asList(items.get(0).getId(), items.get(1).getId()))
            .build();
        ResourceId contactsListId = api.createContactList(request);

        FindContactListsRequest findRequest = FindContactListsRequest.create()
            .name("listFrom")
            .build();
        Page<ContactList> contactLists = api.findContactLists(findRequest);
        assertThat(contactLists.getTotalCount(), greaterThan(1L));
        System.out.println(contactLists);

        // update
        UpdateContactListRequest updateListRequest = UpdateContactListRequest.create()
            .id(contactsListId.getId())
            .name("new_name")
            .build();
        api.updateContactList(updateListRequest);
        ContactList updatedList = api.getContactList(contactsListId.getId());
        assertEquals("new_name", updatedList.getName());

        // delete
        api.deleteContactList(numbersListId.getId());
        api.deleteContactList(contactsListId.getId());
    }

    @Test
    public void testContactListItemsCRUD() throws Exception {
        CallfireClient client = getCallfireClient();
        ContactListsApi api = client.getContactsApi().getContactListsApi();
        // create from dto
        Contact c1 = new Contact();
        c1.setFirstName("name1");
        c1.setHomePhone("12345678901");
        Contact c2 = new Contact();
        c2.setFirstName("name2");
        c2.setHomePhone("12345678902");
        CreateContactListRequest request = CreateContactListRequest.<Contact>create()
            .name("listFromContacts")
            .contacts(asList(c1, c2))
            .build();
        ResourceId id = api.createContactList(request);

        AddContactListItemsRequest addItemsRequest = AddContactListItemsRequest.<String>create()
            .contactListId(id.getId())
            .contacts(asList("12345543211"))
            .build();
        api.addContactListItems(addItemsRequest);

        GetContactListItemsRequest getItemsRequest = GetContactListItemsRequest.create().id(id.getId()).build();
        Page<Contact> contactListItems = api.getContactListItems(getItemsRequest);
        List<Contact> items = contactListItems.getItems();
        assertEquals(3, items.size());

        api.removeContactListItem(id.getId(), items.get(0).getId());
        contactListItems = api.getContactListItems(getItemsRequest);
        items = contactListItems.getItems();
        assertEquals(2, items.size());

        api.removeContactListItems(id.getId(), asList(items.get(0).getId(), items.get(1).getId()));
        contactListItems = api.getContactListItems(getItemsRequest);
        assertEquals(0, contactListItems.getItems().size());

        api.deleteContactList(id.getId());
    }
}
