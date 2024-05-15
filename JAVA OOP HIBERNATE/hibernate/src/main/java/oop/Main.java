package oop;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {


    public void save() {
        Scanner scan = new Scanner(System.in);

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Students.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        // TAMBAHKAN (PERSIST)
        session.beginTransaction();
        
        while (true) {
            try {
                // Minta nama
                System.out.print("\nStudent's Name: ");
                String name = scan.nextLine();

                // Minta umur
                System.out.print("Student's Age: ");
                int age = scan.nextInt();
                scan.nextLine();

                // Minta major
                System.out.print("Student's Major: ");
                String major = scan.nextLine();

                Students student = new Students(name, age, major);

                session.persist(student);

                session.getTransaction().commit();
        
                System.out.println("\n Data Saved!");

                break;

            } catch (InputMismatchException e) {
                System.out.println("Please enter valid data type\n");
                scan.nextLine();
            }
            
        }        
    }

    public void delete() {
        Scanner scan = new Scanner(System.in);

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Students.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        // HAPUS (DELETE)
        session.beginTransaction();
        
        while (true) {
            try {
                // Minta id
                System.out.print("\nEnter Student's Id: ");
                int id = scan.nextInt();
                scan.nextLine();

                Students student = session.get(Students.class, id);
                session.remove(student);
                
                if (student != null){
                    session.delete(student);
                    System.out.println(student.getName() + "(" + id + ")" + " have been removed from database.");
                }
                else{
                    System.out.println("\nStudent with Id " + id + " not found.");
                }

                session.getTransaction().commit();
                break;

            } catch (InputMismatchException e) {
                System.out.println("Please enter valid ID\n");
                scan.nextLine();
            }
            
        }        
    }

    public static void main(String[] args) {   
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose Action:\n1. Persist/Add data\n2. Delete data");
        System.out.print("Input Choice: ");
        String choice = scan.nextLine();

        Main action = new Main();

        switch (choice) {
            case "1":
                action.save();
                break;
            case "2":
                action.delete();
                break;
        
            default:
                break;
        }
        
    }
}