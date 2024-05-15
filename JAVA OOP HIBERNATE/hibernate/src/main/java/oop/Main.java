package oop;

import java.util.List;
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

    public void update() {
        Scanner scan = new Scanner(System.in);

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Students.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        // UPDATE
        session.beginTransaction();
        
        while (true) {
            try {
                // Minta id
                System.out.print("\nEnter Student's Id: ");
                int id = scan.nextInt();
                scan.nextLine();

                Students student = session.get(Students.class, id);
                
                if (student != null){
                    System.out.println("\nChoose Field to Change:\n1. Name\n2. Age\n3. Major\n4. Any key to exit.");
                    System.out.print("Input Choice: ");
                    String pilihan = scan.nextLine();

                    switch (pilihan) {
                        case "1":
                        // nama barunya
                        System.out.print("\nStudent's new name: ");
                        String name = scan.nextLine();
                        student.setName(name);
                            break;
                        case "2":
                        // umur barunya
                        System.out.print("\nStudent's new age: ");
                        int age = scan.nextInt();
                        scan.nextLine();
                        student.setAge(age);
                            break;
                        case "3":
                        // major barunya
                        System.out.print("\nStudent's new major: ");
                        String major = scan.nextLine();
                        student.setMajor(major);
                            break;
                    
                        default:
                        System.out.println("Bye");
                            break;
                    }
                    session.saveOrUpdate(student);
                    System.out.println("Student data updated.");
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

    public void select() {
        Scanner scan = new Scanner(System.in);

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Students.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        // TAMPILKAN (SELECT)
        session.beginTransaction();
        
            try {
                List<Students> students = session.createQuery("from Students", Students.class).list();

                if (!students.isEmpty()){
                    for (Students student : students) {
                        System.out.println("\n--------------------");
                        System.out.println("ID: " + student.getId());
                        System.out.println("Name: " + student.getName());
                        System.out.println("Age: " + student.getAge());
                        System.out.println("Major: " + student.getMajor());
                        System.out.println("--------------------");
                    }
                }
                else{
                    System.out.println("\nNothing to see here...");
                }
            } catch (InputMismatchException e) {
            }
            
        }        



    public static void main(String[] args) {   
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose Action:\n1. Persist/Add data\n2. Update data\n3. Delete data\n4. Show all data");
        System.out.print("Input Choice: ");
        String choice = scan.nextLine();

        Main action = new Main();

        switch (choice) {
            case "1":
                action.save();
                break;
            case "2":
                action.update();
                break;
            case "3":
                action.delete();
                break;
            case "4":
                action.select();
                break;
        
            default:
                break;
        }
        scan.close();
    }
}
