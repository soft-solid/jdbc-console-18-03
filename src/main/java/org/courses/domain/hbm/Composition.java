package org.courses.domain.hbm;

import javax.persistence.*;

@Entity
@Table(name = "SocksMaterial")
public class Composition {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "percentage")
    private int percentage;

    @ManyToOne
    @JoinColumn(name = "material")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "socks")
    private Socks socks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Socks getSocks() {
        return socks;
    }

    public void setSocks(Socks socks) {
        this.socks = socks;
    }
}
