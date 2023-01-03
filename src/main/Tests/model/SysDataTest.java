package model;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SysDataTest {

    @Test
    void testSysDataImportQuestionsNotNull() {
        SysData sysData = SysData.getInstance();
        ArrayList<Question> ques = sysData.getQuestions();
        Assertions.assertTrue(ques.size()>0);
        //if the json size is zero it is handled in the code
    }
    @Test
    void testAddAndRemoveQuestionFromJson() {
        //initializing sysdata
        SysData sysData = SysData.getInstance();
        HashMap <Integer,String> testAnswers = new HashMap<>();
        testAnswers.put(1,"test1");
        testAnswers.put(2,"test2");
        testAnswers.put(3,"test3");
        testAnswers.put(4,"test4");
        //creating new question to add to json
        Question question = new Question("Does this question appear in the Json?",testAnswers,1,1,"test");
        ArrayList<Question> testQuestions;
        sysData.addQuestion(question);
        testQuestions = sysData.getQuestions();
        System.out.println(testQuestions);
       Assertions.assertTrue(testQuestions.contains(question));
       sysData.removeQuestion("Does this question appear in the Json?");
       Assertions.assertFalse(sysData.getQuestions().contains(question));
    }

}