package grup.domain;
import java.time.LocalDate;

public class SpecialOffer {
    private Double specialOfferId;
    private Double hotelId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int percents;

    public SpecialOffer(Double specialOfferId, Double hotelId, LocalDate startDate, LocalDate endDate, int percents) {
        this.specialOfferId = specialOfferId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percents = percents;
    }

    public Double getHotelId() { return hotelId; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public int getPercents() { return percents; }
}