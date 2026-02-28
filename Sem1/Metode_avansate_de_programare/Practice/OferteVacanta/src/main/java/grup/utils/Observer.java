package grup.utils;

import grup.domain.Hobby;

public interface Observer {
    void update(Hobby hobbyClientRezervare, String hotelName);
}
