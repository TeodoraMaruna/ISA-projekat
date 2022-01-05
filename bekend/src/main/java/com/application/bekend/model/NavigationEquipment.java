package com.application.bekend.model;

import javax.persistence.*;

@Entity
public class NavigationEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean gps;
    private boolean radar;
    private boolean vhfradio;
    private boolean fishFinder;

    @OneToOne(mappedBy = "navigationEquipment")
    private Boat boat;

    public NavigationEquipment(Long id, boolean gps, boolean radar, boolean VHFRadio, boolean fishFinder) {
        this.id = id;
        this.gps = gps;
        this.radar = radar;
        this.vhfradio = VHFRadio;
        this.fishFinder = fishFinder;
    }

    public NavigationEquipment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isGps() {
        return gps;
    }

    public void setGps(boolean gps) {
        this.gps = gps;
    }

    public boolean isRadar() {
        return radar;
    }

    public void setRadar(boolean radar) {
        this.radar = radar;
    }

    public boolean isFishFinder() {
        return fishFinder;
    }

    public void setFishFinder(boolean fishFinder) {
        this.fishFinder = fishFinder;
    }

    public boolean isVhfradio() {
        return vhfradio;
    }

    public void setVhfradio(boolean vhfradio) {
        this.vhfradio = vhfradio;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }
}
