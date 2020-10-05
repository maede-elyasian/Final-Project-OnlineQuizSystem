package ir.maktab.service;

import ir.maktab.model.entity.Role;
import ir.maktab.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll(){
       return roleRepository.findAll();
    }

    public Role findByTitle(String role){
        Optional<Role> found = roleRepository.findByTitle(role);
        if (found.isPresent()){
            return found.get();
        }
        return null;
    }
}
