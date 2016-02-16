package com.gdg.unittesting;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

public class RepositoryInteractorTest {

    RepositoryInteractor repositoryInteractor;
    @Mock GitHub gitHubApi;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        repositoryInteractor = new RepositoryInteractor(gitHubApi);
    }

    @Test
    public void testGetRepositoriesCallsApi() throws Exception {
        repositoryInteractor.getRepositories("swanson");

        Mockito.verify(gitHubApi).repositories(anyString());
    }

    @Test
    public void testGetRepositoriesUsesDefaultUser() throws Exception {
        repositoryInteractor.getRepositories();

        Mockito.verify(gitHubApi).repositories("swanson");
    }

}