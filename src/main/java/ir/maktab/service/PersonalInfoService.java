package ir.maktab.service;


import ir.maktab.model.entity.PersonalInfo;
import ir.maktab.repository.PersonalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalInfoService {
    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    public PersonalInfo emailExist(String email) {
        Optional<PersonalInfo> found = personalInfoRepository.findByEmail(email);
        if (found.isPresent()) {
            return found.get();
        }
        return null;
    }

    public PersonalInfo save(PersonalInfo personalInfo) {
        return personalInfoRepository.save(personalInfo);
    }

    public List<PersonalInfo> findAll(){
        return personalInfoRepository.findAll();
    }

    public PersonalInfo findByNationalCode(String natinalCode){
        Optional<PersonalInfo> found = personalInfoRepository.findByNationalCode(natinalCode);
        if (found.isPresent()){
            return found.get();
        }
        return null;
    }
}
