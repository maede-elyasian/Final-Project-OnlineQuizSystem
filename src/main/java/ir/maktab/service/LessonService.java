package ir.maktab.service;

import ir.maktab.model.entity.Lesson;
import ir.maktab.model.enums.LessonTitle;
import ir.maktab.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson findByTitle(LessonTitle lessonTitle) {
        return lessonRepository.findByTitle(lessonTitle);
    }

    public List<Lesson> findAll(){
        return lessonRepository.findAll();
    }

}
