package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SysDataTest {

    @Test
    void testSysDataImportQuestionsNotNull() {
        SysData sysData = SysData.getInstance();
        ArrayList<Question> ques = sysData.getQuestions();
        Assertions.assertTrue(ques.size()>0);
    }
    @Test
    void testImportQuestionEqualsToJson() {
        ArrayList<String> questions =  new ArrayList();
        questions.add("Which Design Pattern should you use when a class wants its subclasses to specify the objects it creates?");
        questions.add("Which of the following are examples of traditional process models?");
        questions.add("is an entity that has a state and a defined set of operations, which operate on that state.");
        questions.add("White-Box testing sometimes called _____ testing.");
        questions.add("In object-oriented design of software,objects have");
        SysData sysData = SysData.getInstance();
        for (int i=0; i<sysData.getQuestions().size(); i++){
            Assertions.assertEquals(sysData.getQuestions().get(i).getQuestionID(),questions.get(i));
        }



    }

}