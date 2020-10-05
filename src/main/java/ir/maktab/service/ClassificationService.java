package ir.maktab.service;

import ir.maktab.model.entity.Classification;
import ir.maktab.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificationService {

    private LessonRepository lessonRepository;

    @Autowired
    public ClassificationService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Classification findByTitle(String lessonTitle) {
        return lessonRepository.findByTitle(lessonTitle);
    }

    public List<Classification> findAll(){
        return lessonRepository.findAll();
    }

    public Classification save(Classification classification){
        return lessonRepository.save(classification);
    }

}
