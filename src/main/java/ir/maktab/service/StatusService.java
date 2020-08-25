package ir.maktab.service;

import ir.maktab.model.entity.Status;
import ir.maktab.model.enums.StatusTitle;
import ir.maktab.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status findByTitle(StatusTitle statusTitle){
        return statusRepository.findByTitle(statusTitle);
    }

    public List<Status> findAll(){
        return statusRepository.findAll();
    }
}
