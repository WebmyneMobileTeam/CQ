package com.example.computerquiz.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Android on 10-03-2015.
 */
public class Test {

    public int _id;
    public int level_id;
    public int category_id;
    public int total_questions;
    public int correct_questions;
    public boolean isPassed;
    public String generated;
    public ArrayList<Question> questions;
    public HashMap<Integer,Integer> answersMap;

}
