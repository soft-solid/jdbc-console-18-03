package org.courses.domain.hbm;

import javax.persistence.*;

@Entity
@Table(name = "Manufacture")
public class Manufacture {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format("Manufacture { id: %d, name: %s }", id, name);
    }
}
