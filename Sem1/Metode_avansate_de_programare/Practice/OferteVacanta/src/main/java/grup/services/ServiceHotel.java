package grup.services;

import grup.repos.*;
import grup.domain.*;
import grup.utils.Observable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceHotel extends Observable {

    private RepoDBLocation repoLocation;
    private RepoDBHotel repoHotel;
    private RepoDBSpecialOffer repoOffer;
    private RepoDBClient repoClient;
    private RepoDBReservation repoRes;

    public ServiceHotel(RepoDBLocation repoLocation, RepoDBHotel repoHotel,
                        RepoDBSpecialOffer repoOffer, RepoDBClient repoClient,
                        RepoDBReservation repoRes) {
        this.repoLocation = repoLocation;
        this.repoHotel = repoHotel;
        this.repoOffer = repoOffer;
        this.repoClient = repoClient;
        this.repoRes = repoRes;
    }

    public List<Location> getAllLocations() {
        return repoLocation.getAll();
    }

    public List<Hotel> getHotelByLocation(Double locationId) {
        return repoHotel.getHotelsByLocationId(locationId);
    }

    public List<SpecialOffer> getOffersByHotelInPeriod(Double hotelId, LocalDate startDate, LocalDate endDate) {
        List<SpecialOffer> offers = repoOffer.getOffersByHotel(hotelId);

        return offers.stream()
                .filter(o -> !(o.getEndDate().isBefore(startDate) || o.getStartDate().isAfter(endDate)))
                .collect(Collectors.toList());
    }

    public Client findClient(Long id){
        return repoClient.findOne(id);
    }

    public void makeReservation(Client client, Hotel hotel, LocalDate startDate, int noNights){
        Reservation reservation = new Reservation(null, client.getClientId()
                , hotel.getHotelId(), startDate.atStartOfDay(), noNights);
        repoRes.save(reservation);
        notifyObservers(client.getHobby(), hotel.getHotelName());
    }
}
