package com.amir.contact.datamodel;


import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class ContactData{

    private static List<Contact> contacts = new ArrayList<>();
    private static String fileName = "Contacts.txt";
    private static ContactData instance = new ContactData();
    private static Scanner scanner = new Scanner(System.in);

    public static ContactData getInstance(){
        return instance;
    }

    private ContactData(){

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

            }
             finally {
                if (br != null) {
                    br.close();
                }

            }
    }

    public  List<Contact> getContacts() throws IOException{
        contacts = loadContact();
        return contacts;
    }

    public void addContact() throws IOException, InputMismatchException {

        try {

            System.out.println("================================================");
            System.out.println("Name:");
            String name = scanner.nextLine();
            System.out.println("Last Name:");
            String lastName = scanner.nextLine();
            System.out.println("Phone:");
            String phone = scanner.nextLine();

            contacts = loadContact();
            Contact newContact = new Contact(name, lastName, phone);
            contacts.add(newContact);
            storeToFile();

        } catch (InputMismatchException e){
            System.out.println("Wrong Input. You need to add a string of character.");
            return;
        }
    }

    public List<Contact> loadContact() throws IOException, ArrayIndexOutOfBoundsException, NullPointerException{

        List<Contact> tempContacts = new ArrayList<>();
        Path path = Paths.get(fileName);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {

            while ((input = br.readLine()) != null){
                String[] pieceItem = input.split("\t");

                String name = pieceItem[0];
                String lastName = pieceItem[1];
                String phone = pieceItem[2];


                Contact newContact = new Contact(name,lastName,phone);
                tempContacts.add(newContact);

            }
            return tempContacts;

        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("There is no data yet.... You need to add a contact First.");
            return contacts;

        } finally{
            if (br !=null){
                br.close();
            }
        }
    }
    public void removeContact(String name) throws IOException{
        System.out.println("================================================");


        Contact contact = searchByName(name ,false);
        if (contact == null) {
            System.out.println("Not such a contact....");
            return;
        }
        System.out.println(contact.getName() + " has been removed now.");
        contacts.remove(contact);
        storeToFile();
        showContact();
    }

    public Contact searchByName(String name , boolean searchByNameFlag) throws IOException{
        contacts = loadContact();

        for (int i =0 ; i<contacts.size(); i++){
            if (contacts.get(i).getName().equals(name)){

                if (!searchByNameFlag ) return contacts.get(i);
                else {
                    System.out.println(contacts.get(i).toString());
                }
            }
        }
        return null;

    }

    public void showContact() throws IOException, NullPointerException{
        try {

            System.out.println("================================================");

            List<Contact> contacts = loadContact();

            for (Contact contact : contacts) {
                System.out.println(contact.getName() + " " + contact.getLastName() + " ==> " + contact.getPhone());
            }
        } catch (IOException | NullPointerException e){
            System.out.println("You need to add at leas one contact.");
        }
    }

    public void editContact(String name) throws IOException,InputMismatchException{
        contacts = loadContact();

        try {
            Iterator<Contact> it = contacts.iterator();
            while (it.hasNext()) {
                Contact editContact = it.next();
                if (editContact.getName().equals(name)) {
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
                            editContact.setName(newName);
                            break;
                        case 2:
                            System.out.println("New Last Name:");
                            String newLastName = scanner.nextLine();
                            editContact.setLastName(newLastName);
                            break;
                        case 3:
                            System.out.println("New Phone:");
                            String newPhone = scanner.nextLine();
                            editContact.setPhone(newPhone);
                            break;
                    }
                }
                else {
                    System.out.println("Not such a contact.");
                    return;
                }
            }
        } catch (InputMismatchException e){
            System.out.println("Wrong Input");
            return;
        }
        storeToFile();
    }

    public static Comparator<Contact> nameComparator = (contact1, contact2) -> contact1.getName().compareTo(contact2.getName());

    public void sortByName() throws IOException{

        contacts = loadContact();

        Collections.sort(contacts,nameComparator);
        storeToFile();
        showContact();
    }

    public static Comparator<Contact> lastNameComparator = new Comparator<Contact>() {
        @Override
        public int compare(Contact contact1, Contact contact2) {
            return contact1.getLastName().compareTo(contact2.getLastName());
        }
    };

    public void sortByLastName() throws IOException {
        contacts = loadContact();
        Collections.sort(contacts,lastNameComparator);
        storeToFile();
        showContact();
    }

}
