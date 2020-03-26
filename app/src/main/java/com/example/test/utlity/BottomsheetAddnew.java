package com.example.test.utlity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomsheetAddnew extends BottomSheetDialogFragment {
View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_addnew_repeat,container,false);
        return  view;
    }
}
