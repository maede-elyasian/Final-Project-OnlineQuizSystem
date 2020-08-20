package ir.maktab.service;

import ir.maktab.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {

    private ConfirmationTokenRepository tokenRepository;

    @Autowired
    public ConfirmationTokenService(ConfirmationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void deleteTokenById(Long id){
        tokenRepository.deleteById(id);
    }
}
