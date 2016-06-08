package org.kapps.test.dao;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Antoine Kapps
 */
@Entity
@Table(name = "ANIMALS")
public class Pet {

    @Id
    private Integer id;

    private String name;

    private int age;

    @ManyToOne
    @JoinColumn(name = "personId")
    private Person owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    private Integer getOwnerId() {
        return (owner == null) ? null : owner.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;

        return age == pet.age &&
                Objects.equals(id, pet.id) &&
                Objects.equals(name, pet.name) &&
                Objects.equals(getOwnerId(), pet.getOwnerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, getOwnerId());
    }
}
