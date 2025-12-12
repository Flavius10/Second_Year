package org.example.domain.friendship;

public class Request {

    private Long id;
    private String senderUsername;
    private String receiverUsername;
    private String status;

    public Request(Long id, String senderUsername, String receiverUsername, String status) {
        this.id = id;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSenderUsername(String username) {
        this.senderUsername = username;
    }

    public void setReceiverUsername(String username) {
        this.receiverUsername = username;
    }

    @Override
    public String toString() {
        return "Request{" + "id=" + id + ", senderUsername='" + senderUsername + '\'' +
                ", receiverUsername='" + receiverUsername + '\'' + ", status='" + status + '\'' + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Request)) return false;
        Request request = (Request) obj;
        return request.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }




}
