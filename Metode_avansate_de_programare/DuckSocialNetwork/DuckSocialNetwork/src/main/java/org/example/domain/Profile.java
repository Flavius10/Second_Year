package org.example.domain;

public class Profile {

    private String profilePicture;
    private String username;
    private String email;
    private String profileType;

    public Profile(String profilePicture, String username, String email, String profileType) {
        this.profilePicture = profilePicture;
        this.username = username;
        this.email = email;
        this.profileType = profileType;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    @Override
    public String toString() {
        return "Profile{" + "profilePicture='" + profilePicture +
                '\'' + ", username='" + username + '\'' +
                ", email='" + email + '\'' + ", profileType='" + profileType + '\'' + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Profile)) return false;
        Profile profile = (Profile) obj;
        return profile.getUsername().equals(this.getUsername());
    }

    @Override
    public int hashCode() {
        return this.getUsername().hashCode();
    }



}
