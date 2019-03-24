package org.courses.domain.hbm;

import org.courses.domain.hbm.sqliteconvertion.ColorConverter;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Socks")
public class Socks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "size")
    private double size;

    @Column(name = "colour")
    @Convert(converter = ColorConverter.class)
    private Color colour;

    @ManyToOne
    @JoinColumn(name = "manufacture")
    private Manufacture manufacture;

    @ManyToOne
    @JoinColumn(name = "type")
    private Type type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "socks")
    private List<Composition> composition = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public Manufacture getManufacture() {
        return manufacture;
    }

    public void setManufacture(Manufacture manufacture) {
        this.manufacture = manufacture;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Composition> getComposition() {
        return composition;
    }

    public void setComposition(List<Composition> composition) {
        this.composition = composition;
    }

    public void add(Composition c) {
        composition.add(c);
        c.setSocks(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Socks{ ");
        sb.append("id: ")
                .append(id)
                .append(", size = ")
                .append(size)
                .append(", colour = ")
                .append(colour)
                .append(", manufacture = ")
                .append(manufacture.getName())
                .append(", type = ")
                .append(type.getName())
                .append("\n")
                .append("\tComposition :");
        for (Composition c : composition) {
            sb.append("\n\t")
                    .append("[ id: ")
                    .append(c.getId())
                    .append(", material = ")
                    .append(c.getMaterial().getName())
                    .append(" ")
                    .append(c.getPercentage())
                    .append("% ]");
        }
        sb.append("}");

        return sb.toString();
    }
}
