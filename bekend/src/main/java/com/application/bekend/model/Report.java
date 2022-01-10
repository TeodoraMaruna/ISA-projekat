package com.application.bekend.model;

import javax.persistence.*;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private boolean missedReservation;
    private boolean penaltyProposal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "houseReservation_id")
    private HouseReservation houseReservation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boatReservation_id")
    private BoatReservation boatReservation;

    public Report(Long id, String comment, boolean showedUp, boolean penaltyProposal, HouseReservation houseReservation, BoatReservation boatReservation) {
        this.id = id;
        this.comment = comment;
        this.missedReservation = showedUp;
        this.penaltyProposal = penaltyProposal;
        this.houseReservation = houseReservation;
        this.boatReservation = boatReservation;
    }

    public Report(Long id, String comment, boolean showedUp, boolean penaltyProposal) {
        this.id = id;
        this.comment = comment;
        this.missedReservation = showedUp;
        this.penaltyProposal = penaltyProposal;
    }

    public Report(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isMissedReservation() {
        return missedReservation;
    }

    public void setMissedReservation(boolean missedReservation) {
        this.missedReservation = missedReservation;
    }

    public boolean isPenaltyProposal() {
        return penaltyProposal;
    }

    public void setPenaltyProposal(boolean penaltyProposal) {
        this.penaltyProposal = penaltyProposal;
    }

    public HouseReservation getHouseReservation() {
        return houseReservation;
    }

    public void setHouseReservation(HouseReservation houseReservation) {
        this.houseReservation = houseReservation;
    }

    public BoatReservation getBoatReservation() {
        return boatReservation;
    }

    public void setBoatReservation(BoatReservation boatReservation) {
        this.boatReservation = boatReservation;
    }
}
