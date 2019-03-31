package com.amir.contact.datamodel;


import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class ContactData {

    private static List<Contact> contacts = new ArrayList<>();
    private static String fileName = "Contacts.txt";
    private static ContactData instance = new ContactData();
    private static Scanner scanner = new Scanner(System.in);

    public static ContactData getInstance() {
        return instance;
    }

    private ContactData() {

    }

    public void storeToFile() throws IOException {


        Path path = Paths.get(fileName);
        BufferedWriter br = Files.newBufferedWriter(path);

        try {

            Iterator<Contact> iterator = contacts.iterator();
            while (iterator.hasNext()) {
                Contact myContact = iterator.next();
                br.write(String.format(("%s\t%s\t%s"), myContact.getName(), myContact.getLastName(), myContact.getPhone()));
                br.newLine();
            }

        } finally {
            if (br != null) {
                br.close();
            }

        }
    }

    public void addContact() throws IOException, InputMismatchException {

        try {
            loadContact();

            System.out.println("Name:");
            String name = scanner.nextLine();
            System.out.println("Last Name:");
            String lastName = scanner.nextLine();
            System.out.println("Phone:");
            String phone = scanner.nextLine();

            Contact newContact = new Contact(name, lastName, phone);
            contacts.add(newContact);
            storeToFile();

        } catch (InputMismatchException e) {
            System.out.println("Wrong Input. You need to add a string of character.");
            return;
        }
    }

    public void loadContact() throws IOException, ArrayIndexOutOfBoundsException, NullPointerException {

        contacts = new ArrayList<>();
        Path path = Paths.get(fileName);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {

            while ((input = br.readLine()) != null) {
                String[] pieceItem = input.split("\t");

                String name = pieceItem[0];
                String lastName = pieceItem[1];
                String phone = pieceItem[2];

                Contact newContact = new Contact(name, lastName, phone);
                contacts.add(newContact);

            }

        } catch (ArrayIndexOutOfBoundsException | IOException e) {
            System.err.println(e.getMessage());

            return;

        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void removeContact(String name) throws IOException {
        System.out.println("================================================");


        Contact contact = searchContact(name);
        if (contact == null) {
            System.out.println("Not such a contact....");
            return;
        }
        System.out.println(contact.getName() + " has been removed now. The new Contact list:");
        contacts.remove(contact);
        storeToFile();
        showContact();
    }

    public Contact searchContact(String name) throws IOException, NullPointerException {

        loadContact();

        for (int i = 0; i < contacts.size(); i++)
            if ((contacts.get(i).getName().toLowerCase().equals(name.toLowerCase())) ||
                    (contacts.get(i).getLastName().toLowerCase().equals(name.toLowerCase()) ||
                            (contacts.get(i).getPhone().equals(name))))
                return contacts.get(i);
        return null;

    }


    public void showContact() throws IOException, NullPointerException {
        try {
            loadContact();
            if (contacts.size() == 0) {
                System.out.println("There is no contact in the file.You need to add a contact first.");
                addContact();
            } else {
                for (Contact contact : contacts) {
                    System.out.println(contact.getName() + " " + contact.getLastName() + " ==> " + contact.getPhone());
                }
            }
        } catch (IOException | NullPointerException e) {
            return;
        }
    }

    public void editContact(String name) throws IOException, InputMismatchException {


        loadContact();

        try {

            Contact contactToEdit = searchContact(name);
            if (contactToEdit != null) {

                System.out.println("Want to change:\n" +
                        "1.Name\n" +
                        "2.Last Name\n" +
                        "3.Phone\n");
                System.out.println("Option:");
                int opt = scanner.nextInt();
                scanner.nextLine();
                switch (opt) {
                    case 1:
                        System.out.println("New Name:");
                        String newName = scanner.nextLine();
                        contactToEdit.setName(newName);
                        break;
                    case 2:
                        System.out.println("New Last Name:");
                        String newLastName = scanner.nextLine();
                        contactToEdit.setLastName(newLastName);
                        break;
                    case 3:
                        System.out.println("New Phone:");
                        String newPhone = scanner.nextLine();
                        contactToEdit.setPhone(newPhone);
                        break;
                }
                System.out.println("The contact has been updated to:" + contactToEdit.toString());
                storeToFile();
            }
            else {
                System.out.println("Not such a contact.");
            }


        } catch (InputMismatchException e) {
            System.out.println("Wrong Input");
            return;
        }

    }

    private static Comparator<Contact> nameComparator = (contact1, contact2) -> contact1.getName().compareTo(contact2.getName());

    public void sortByName() throws IOException{

        loadContact();

        Collections.sort(contacts,nameComparator);
        storeToFile();
        showContact();
    }

    private static Comparator<Contact> lastNameComparator = new Comparator<Contact>() {
        @Override
        public int compare(Contact contact1, Contact contact2) {
            return contact1.getLastName().compareTo(contact2.getLastName());
        }
    };

    public void sortByLastName() throws IOException {
        loadContact();
        Collections.sort(contacts,lastNameComparator);
        storeToFile();
        showContact();
    }

}
