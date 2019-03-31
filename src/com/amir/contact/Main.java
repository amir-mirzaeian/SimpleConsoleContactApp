package com.amir.contact;


import com.amir.contact.datamodel.ContactData;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
	// write your code here



        System.out.println("Welcome to Contact App:");
        showMenu();

    }

    public static void showMenu() throws IOException{
        System.out.println("================================================");
        System.out.println("Press \n1)To show Contacts\n" +
        "2.To Add a contact\n" +
        "3.To Remove a contact\n" +
        "4.To Edit a contact\n" +
        "5.To Search A Contact By Either Name,Last Name or Phone\n" +
                "6.To Sort Contacts By Name\n" +
                "7,To Sort Contacts By LastName\n" +
                "8.To Exit\n" +
        "================================================");
        getInputFromUser();
    }
    public static void getInputFromUser() throws IOException, InputMismatchException, StackOverflowError {
        try {

            System.out.println("Option:");
            int opt = scanner.nextInt();
            ContactData contactData = ContactData.getInstance();
            switch (opt) {
                case 1:
                    contactData.showContact();
                    ifContinue();
                    break;
                case 2:
                    contactData.addContact();
                    ifContinue();
                    break;
                case 3:
                    System.out.println("Contact Name, lastName, or phone number:");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    contactData.removeContact(name);
                    ifContinue();
                    break;
                case 4:
                    System.out.println("Name:");
                    scanner.nextLine();
                    String newName = scanner.nextLine();
                    contactData.editContact(newName);
                    ifContinue();
                    break;
                case 5:
                    System.out.println("Name, Last name or phone of the Contact you want to search:");
                    scanner.nextLine();
                    String searchName = scanner.nextLine();
                    if (contactData.searchContact(searchName) == null)
                        System.out.println("Not such a contact...");
                    else
                    System.out.println(contactData.searchContact(searchName).toString());
                    ifContinue();
                    break;
                case 6:
                    contactData.sortByName();
                    ifContinue();
                    break;
                case 7:
                    contactData.sortByLastName();
                    ifContinue();
                    break;
                case 8:
                    System.out.printf("Thank's for using the app... GOODBYE!");
                    System.exit(-1);
                default:
                    System.err.printf("Wrong option. Please Try Again...\n");
                    showMenu();
            }
        } catch (IOException| InputMismatchException |StackOverflowError e) {
            System.out.printf("A problem has happened... The program will be terminated. Please try later.");

        }
    }
    public static void ifContinue() throws IOException, InputMismatchException {
        try {

        System.out.println("================================================");
        System.out.println("Want to continue:\n" +
                "1.Yes\n" +
                "2.No");
        int opt = scanner.nextInt();
        if (opt == 1) {
            showMenu();
        } else {
            System.out.println("That's for using the app....");
            System.exit(-1);
        }
    } catch (InputMismatchException e){
            System.out.println("Wrong input. The program will be terminated automatically.");
            return;
        }

    }
}
