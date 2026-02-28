package grup.domain;

public class Location {

    private Double locationId;
    private String locationName;

    public Location(Double locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public Double getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationId(Double locationId) {
        this.locationId = locationId;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public String toString() {
        return "Location [locationId=" + locationId + ", locationName=" + locationName + "]";
    }

    @Override
    public boolean equals(Object obj) {
       if (this == obj)
           return true;
         if (obj == null || getClass() != obj.getClass())
              return false;

         Location other = (Location) obj;

            return locationId != null ? locationId.equals(other.locationId) : other.locationId == null;
    }

    @Override
    public int hashCode() {
        return locationId != null ? locationId.hashCode() : 0;
    }

}
