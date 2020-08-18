package ir.maktab.service;


import ir.maktab.model.entity.PersonalInfo;
import ir.maktab.repository.PersonalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalInfoService {
    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    public boolean emailExist(String email) {
        if (personalInfoRepository.findByEmail(email).isPresent()) {
            return true;
        }
        return false;
    }

    public PersonalInfo save(PersonalInfo personalInfo){
       return personalInfoRepository.save(personalInfo);
    }
}
