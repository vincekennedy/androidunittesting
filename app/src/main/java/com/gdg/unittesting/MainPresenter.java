package com.gdg.unittesting;

public class MainPresenter implements ConnectivityManager{

    @Override
    public boolean hasConnection() {
        return false;
    }

    interface MainView{
    }

    MainView view;

    public void registerView(MainView view){
        this.view = view;
    }


    public void searchRepos(){

    }

}
