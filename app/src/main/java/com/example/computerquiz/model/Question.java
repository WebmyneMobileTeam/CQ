package com.example.computerquiz.model;

import java.util.ArrayList;

/**
 * Created by Android on 10-03-2015.
 */
public class Question {

    public int _id;
    public int category_id;
    public int level_id;
    public int correct_answer;
    public String description;
    public String option1;
    public String option2;
    public String option3;
    public String option4;
    public String option5;


    public ArrayList<String> getOptions() {

        ArrayList<String> options = new ArrayList<>();

        if(option1 != null && !option1.isEmpty()){
            options.add(option1);
        }
        if(option2 != null && !option2.isEmpty()){
            options.add(option2);
        }

        if(option3 != null && !option3.isEmpty()){
            options.add(option3);
        }

        if(option4 != null && !option4.isEmpty()){
            options.add(option4);
        }

        if(option5 != null && !option5.isEmpty()){
            options.add(option5);
        }

        return options;
    }

}
