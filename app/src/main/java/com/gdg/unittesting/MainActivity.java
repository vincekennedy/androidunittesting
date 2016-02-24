
package com.gdg.unittesting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.w3c.dom.Text;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import java.util.List;

public class MainActivity extends Activity
        implements MainPresenter.MainView {

    RepositoryInteractor interactor = new RepositoryInteractor();
    RepositoryListAdapter adapter;
    ListView repos;
    EditText userName;
    Button findRepos;

    MainPresenter presenter = new MainPresenter();
    ConnectivityManager connectivityManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new RepositoryListAdapter(this);

        presenter.registerView(this);
        connectivityManager = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        userName = (EditText) findViewById(R.id.edit_username);
        findRepos = (Button) findViewById(R.id.btn_find);

        repos = (ListView) findViewById(R.id.list_repos);
        repos.setAdapter(adapter);

        findRepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                findRepos(userName.getText().toString());
            }
        });
    }


    void findRepos(String name) {

        if(connectivityManager.hasConnection()) {
            interactor.getRepositories(name)
//                      .subscribeOn(Schedulers.io())
//                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Action1<List<Repository>>() {
                          @Override
                          public void call(final List<Repository> repositories) {
                              repositoriesReceived(repositories);
                          }
                      });
        }

    }

    void getRepos() {
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