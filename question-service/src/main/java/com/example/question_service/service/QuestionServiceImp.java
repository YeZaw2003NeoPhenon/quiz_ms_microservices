package com.example.question_service.service;

import com.example.question_service.dao.QuestionDao;
import com.example.question_service.dto.QuestionRequest;
import com.example.question_service.dto.QuestionResponse;
import com.example.question_service.exception.QuestionNotFoundException;
import com.example.question_service.model.Question;
import com.example.question_service.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {

    private final QuestionDao questionDao;

    @Override
    public QuestionResponse createQuestion(QuestionRequest request) {
        Question question = new Question();
        question.setQuestionTitle(request.getQuestionTitle());
        question.setOption1(request.getOption1());
        question.setOption2(request.getOption2());
        question.setOption3(request.getOption3());
        question.setOption4(request.getOption4());
        question.setRightAnswer(request.getRightAnswer());
        question.setCategory(request.getCategory());

        questionDao.save(question);

        return new QuestionResponse(question.getId(),
                question.getQuestionTitle(),
                question.getOption1(),
                question.getOption2(),
                question.getOption3(),
                question.getOption4(),
               question.getCategory());
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    @Override
    public List<Question> getQuestionByCategory(String category) {
       return questionDao.findQuestionByCategory(category);
//         String lowerCategory = category.toLowerCase();
//        return getAllQuestions().stream()
//                .filter(q -> q.getCategory().toLowerCase().contains(lowerCategory))
//                .toList();
    }

    @Override
    public List<Integer> getQuestionsForQuiz(String category, int numberOfQuestions) {
        return questionDao.findRandomQuestionsByCategory(category, numberOfQuestions);
    }

    @Override
    public List<QuestionResponse> getQuestionsFromIds(List<Integer> questionIds) {

        List<Question> questions = new ArrayList<>();
        List<QuestionResponse> wrappers = new ArrayList<>();

        for(Integer id : questionIds){
            Question question = questionDao.findById(id).orElseThrow(() -> new QuestionNotFoundException("Question with id " + id + " not found"));
            questions.add(question);
        }

        for(Question q : questions){
            QuestionResponse wrapper = new QuestionResponse();
            wrapper.setId(q.getId());
            wrapper.setQuestionTitle(q.getQuestionTitle());
            wrapper.setOption1(q.getOption1());
            wrapper.setOption2(q.getOption2());
            wrapper.setOption3(q.getOption3());
            wrapper.setOption4(q.getOption4());
            wrapper.setCategory(q.getCategory());
            wrappers.add(wrapper);
        }
        return wrappers;
    }

    @Override
    public Integer getScore(List<Response> responses) {
        int right = 0;

        for(Response r : responses){
           Question question = questionDao.findById(r.getId()).orElseThrow(() -> new QuestionNotFoundException("Question not found"));
           if(question.getRightAnswer().equals(r.getResponse())){
                right++;
           }
        }
        return right;
    }

}
