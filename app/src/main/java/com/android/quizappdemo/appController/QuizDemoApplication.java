package com.android.quizappdemo.appController;

import android.app.Application;
import android.content.Context;

public class QuizDemoApplication extends Application {

    private static QuizDemoApplication quizDemoApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        quizDemoApplication=this;
    }

    public static QuizDemoApplication getInstance() {
        return quizDemoApplication;
    }
}
