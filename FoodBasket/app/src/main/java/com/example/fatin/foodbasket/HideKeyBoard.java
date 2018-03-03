package com.example.fatin.foodbasket;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class HideKeyBoard {

   private View view =null;
   Object activity;
   public HideKeyBoard(View view,Object activity){
       this.view=view;
       this.activity=activity;

   }
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =(InputMethodManager)activity;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
