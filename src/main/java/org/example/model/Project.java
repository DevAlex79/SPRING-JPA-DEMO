package org.example.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
public class Project {

    @Id
    private int id;

    private String Pname;

    @ManyToMany(mappedBy = "p")
    private Collection<Employe> e;

    // Getter and Setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public Collection<Employe> getE() {
        return e;
    }

    public void setE(Collection<Employe> e) {
        this.e = e;
    }
}
