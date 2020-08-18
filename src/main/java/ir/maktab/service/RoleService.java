package ir.maktab.service;

import ir.maktab.model.entity.Role;
import ir.maktab.model.enums.RoleTitle;
import ir.maktab.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll(){
       return roleRepository.findAll();
    }

    public Role findByTitle(RoleTitle role){
        return roleRepository.findByTitle(role);
    }
}
