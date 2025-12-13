package org.example.services;

import org.example.domain.friendship.Request;
import org.example.repositories.repo_db.RepoDBRequest;

import java.util.Optional;

public class RequestService {

    private RepoDBRequest repoDBRequest;

    public RequestService(RepoDBRequest repoDBRequest) {
        this.repoDBRequest = repoDBRequest;
    }

    public void saveRequest(Request request){
        Optional<Request> existing = findById(request.getId());

        if (existing.isPresent()) {
            throw new RuntimeException("Request already exists!");
        }
        repoDBRequest.save(request);
    }

    public void deleteRequest(Request request){
        Optional<Request> deleted = this.repoDBRequest.delete(request.getId());
        if (deleted.isEmpty()) {
            throw new RuntimeException("Request not found!");
        }

    }

    public Iterable<Request> findAll(){
        return repoDBRequest.findAll();
    }

    public Optional<Request> findById(Long id){
        return repoDBRequest.findOne(id);
    }

}
