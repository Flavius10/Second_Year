package grup.domain;

import java.time.LocalDateTime;

public class Reservation {
    private Double reservationId;
    private Long clientId;
    private Double hotelId;
    private LocalDateTime startDate;
    private int noNights;

    public Reservation(Double reservationId, Long clientId, Double hotelId, LocalDateTime startDate, int noNights) {
        this.reservationId = reservationId;
        this.clientId = clientId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.noNights = noNights;
    }

    public Double getReservationId() {
        return reservationId;
    }

    public Long getClientId() {
        return clientId;
    }

    public Double getHotelId() {
        return hotelId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public int getNoNights() {
        return noNights;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", clientId=" + clientId +
                ", hotelId=" + hotelId +
                ", startDate=" + startDate +
                ", noNights=" + noNights +
                '}';
    }
}