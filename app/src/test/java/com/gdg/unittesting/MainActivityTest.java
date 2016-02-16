package com.gdg.unittesting;

import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class MainActivityTest {

    MainActivity mainActivity;
    @Mock
    RepositoryInteractor mockRepositoryInteractor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mainActivity = new MainActivity();
        mainActivity.interactor = mockRepositoryInteractor;
    }

    @Test
    public void testGetRepositoriesIsCalledInOnCreate() throws Exception {
        when(mockRepositoryInteractor.getRepositories()).thenReturn(Observable.<List<Repository>>empty());

        mainActivity.onCreate(null);

        verify(mockRepositoryInteractor).getRepositories();
    }

    @Test
    public void testRepositoryAdapterReceivesReposOnSuccess() throws Exception {
        RepositoryListAdapter listAdapter = new RepositoryListAdapter(null);
        mainActivity.adapter = listAdapter;

        List<Repository> repos = new ArrayList<>();
        RepositoryOwner owner = new RepositoryOwner("Bob");
        repos.add(new Repository("Test", "Ruby", owner));
        repos.add(new Repository("More tests", "Java", owner));

        when(mockRepositoryInteractor.getRepositories())
                .thenReturn(Observable.just(repos));


        mainActivity.getRepos();

        Thread.sleep(2000);

        verify(mockRepositoryInteractor).getRepositories();
        Truth.assertThat(listAdapter.getCount()).isEqualTo(2);
    }
}