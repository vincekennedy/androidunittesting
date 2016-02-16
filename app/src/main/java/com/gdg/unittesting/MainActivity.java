
package com.gdg.unittesting;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import java.util.List;

public class MainActivity extends ListActivity {

    RepositoryInteractor interactor = new RepositoryInteractor();
    RepositoryListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new RepositoryListAdapter(this);
        setListAdapter(adapter);

        getRepos();
    }

    void getRepos(){
        interactor.getRepositories()
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Action1<List<Repository>>() {
                                 @Override
                                 public void call(final List<Repository> repositories) {
                                     repositoriesReceived(repositories);
                                 }
                             },
                             new Action1<Throwable>() {
                                 @Override
                                 public void call(final Throwable throwable) {
                                     Timber.e("Error : %s", throwable.getLocalizedMessage());
                                     displayErrorMessage();
                                 }
                             });
    }

    void repositoriesReceived(final List<Repository> repositories) {
        if (repositories.isEmpty()) {
            displaySadMessage();
        } else {
            adapter.setRepositories(repositories);
        }
    }

    void displaySadMessage() {
        Toast.makeText(MainActivity.this, "No repos :(",
                       Toast.LENGTH_LONG).show();
    }

    void displayErrorMessage() {
        Toast.makeText(MainActivity.this, "Failed to retrieve the user's repos.",
                       Toast.LENGTH_LONG).show();
    }
}