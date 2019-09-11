package com.android.quizappdemo.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.quizappdemo.R;

public class ResultFragment extends Fragment {

    private View mView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.result_layout, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews(){
        TextView totalnumberOfQuestions,correctAnswered,wrongAnswered;
        totalnumberOfQuestions = mView.findViewById(R.id.numberofQuestions);
        correctAnswered = mView.findViewById(R.id.txt_correct_answer_count);
        wrongAnswered = mView.findViewById(R.id.txt_wrong_answer_count);

        if(null!=getArguments()) {
            int correctCount = getArguments().getInt("correct");
            int wrongCount = getArguments().getInt("wrong");
            int numberOfQuestions = getArguments().getInt("numberOfQuestions");
            totalnumberOfQuestions.setText(getString(R.string.txt_number_of_questions).concat(" "+numberOfQuestions));
            correctAnswered.setText(getString(R.string.txt_correct_answered).concat(" " + correctCount));
            wrongAnswered.setText(getString(R.string.txt_wrong_answered).concat(" "+ wrongCount));
        }

    }
}
