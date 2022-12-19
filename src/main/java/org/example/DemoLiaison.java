package org.example;

import org.example.model.Department;
import org.example.model.Employe;
import org.example.model.ParkingSpace;
import org.example.model.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Collection;

public class DemoLiaison {

    public static void main(String[] args) {

        // Demo One To One Bidirectionnelle

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DemoJpa");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transac = em.getTransaction();

        // Création et enregistrement d'un employe (avec id = 5)
        transac.begin();

        Employe employe = new Employe();

        employe.setId(5);

        em.persist(employe);

        transac.commit();

        // Création d'un parkingSpace

        transac.begin();;
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId(6);
        parkingSpace.setLocation("emplacement 6");
        parkingSpace.setEmp(employe);
        employe.setSpace(parkingSpace);
        em.persist(parkingSpace);
        transac.commit();

        // Récupération des informations

        Employe employe1 = em.find(Employe.class, 5);
        System.out.println("Employé avec l'ID : "+employe1.getId()+" et à la place de parking située à "+employe1.getSpace().getLocation());

        ParkingSpace parkingSpace1 = em.find(ParkingSpace.class, 6);
        System.out.println("Emplacement parking avec l'ID : "+parkingSpace1.getId()+" attribué à l'employé avec l'ID : "+parkingSpace1.getEmp()
                .getId());

        // Démonstration Many To One et One To Many

            // Création d'un employé supplémentaire

        transac.begin();

        Employe employe2 = new Employe();
        employe2.setId(6);
        em.persist(employe2);
        transac.commit();

            // Création d'un département

        transac.begin();

        Department department = new Department();
        department.setId(1);
        department.setName("Science");

            // Création d'une collection d'employés
        Collection<Employe> list = new ArrayList<>();
        list.add(employe);
        list.add(employe2);

            // Ajout de cette collection d'employés à son département
        department.setEmps(list);

            // Attribution des départements aux employés
        employe.setD(department);
        employe2.setD(department);

            // Persistence
        em.persist(department);
        em.persist(employe);
        em.persist(employe2);

        transac.commit();

        Employe employe3 = em.find(Employe.class, 5);
        Employe employe4 = em.find(Employe.class,6);

        System.out.println("Employé avec l'ID : "+employe3.getId()+" travaille au département "+employe3.getD().getName());
        System.out.println("Employé avec l'ID : "+employe4.getId()+" travaille au département "+employe4.getD().getName());

        Department department1 = em.find(Department.class, 1);
        Collection<Employe> emps = department1.getEmps();
        System.out.println("Liste des employés du département "+department1.getName()+" : ");
        for(Employe emp : emps) {
            System.out.println(emp.getId());
        }

        // Many To Many

        transac.begin();
            // Création de 2 projets
        Project project = new Project();
        project.setId(1);
        project.setPname("ProjetA");
        em.persist(project);

        Project project1 = new Project();
        project1.setId(2);
        project1.setPname("ProjetB");
        em.persist(project1);

            // Création d'une collection de projets
        Collection<Project> listProjet = new ArrayList<>();
        listProjet.add(project1);
        listProjet.add(project);

            // Récupération de plusieurs employés
        Employe employe5 = em.find(Employe.class, 5);
        Employe employe6 = em.find(Employe.class, 6);

            // que je mets dans une liste
        Collection<Employe> mesemps = new ArrayList<>();
        mesemps.add(employe5);
        mesemps.add(employe6);

            // J'attribue des projets à mes employés
        employe5.setP(listProjet);
        employe6.setP(listProjet);

        em.persist(employe5);
        em.persist(employe6);

        project.setE(mesemps);
        project1.setE(mesemps);

        transac.commit();

        transac.begin();
        Employe employe7 = em.find(Employe.class, 5);
        Employe employe8 = em.find(Employe.class, 6);
        Project monprojet1 = em.find(Project.class, 1);
        Project monprojet2 = em.find(Project.class, 2);

        System.out.println("Liste des projets de l'employé avec l'ID : "+employe7.getId());
        for(Project p : employe7.getP()) {
            System.out.println(p.getPname());
        }
        System.out.println("Liste des projets de l'employé avec l'ID : "+employe8.getId());
        for(Project p : employe8.getP()) {
            System.out.println(p.getPname());
        }
        System.out.println("Liste des employés sur le Projet : "+monprojet1.getPname());
        for(Employe e : monprojet1.getE()) {
            System.out.println(e.getId());
        }

        System.out.println("Liste des employés sur le Projet : "+monprojet2.getPname());
        for(Employe e : monprojet2.getE()) {
            System.out.println(e.getId());
        }
        transac.commit();
        em.close();
        emf.close();



    }
}
