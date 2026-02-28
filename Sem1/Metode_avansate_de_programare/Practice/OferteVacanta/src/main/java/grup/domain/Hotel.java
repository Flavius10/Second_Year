package grup.domain;

public class Hotel {
    private Double hotelId;
    private Double locationId;
    private String hotelName;
    private int noRooms;
    private Double pricePerNight;
    private HotelType type;

    public Hotel(Double hotelId, Double locationId, String hotelName, int noRooms, Double pricePerNight, HotelType type) {
        this.hotelId = hotelId;
        this.locationId = locationId;
        this.hotelName = hotelName;
        this.noRooms = noRooms;
        this.pricePerNight = pricePerNight;
        this.type = type;
    }

    public Double getHotelId() { return hotelId; }
    public Double getLocationId() { return locationId; }
    public String getHotelName() { return hotelName; }
    public int getNoRooms() { return noRooms; }
    public Double getPricePerNight() { return pricePerNight; }
    public HotelType getType() { return type; }
}