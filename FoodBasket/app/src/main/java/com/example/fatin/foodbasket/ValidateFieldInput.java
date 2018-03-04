package com.example.fatin.foodbasket;

import android.text.TextUtils;

public class ValidateFieldInput {


    protected static boolean fieldsNotEmpty(String... fieldsInputs) {
        for (int i=0;i<fieldsInputs.length;i++){
            if (TextUtils.isEmpty(fieldsInputs[i])) {
                return false;
            }
        }
        return true;
    }
}
