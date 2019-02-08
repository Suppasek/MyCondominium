package com.suppasek.mycondo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.suppasek.mycondo.repository.AuthenRepository
import com.suppasek.mycondo.repository.DataRepository
import com.suppasek.mycondo.viewmodel.LoginViewModel
import io.reactivex.Single
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import java.lang.Exception


class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dataRepositoryStub: DataRepository
    private lateinit var authenRepositoryStub: AuthenRepository

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        dataRepositoryStub = mock(DataRepository::class.java)
        authenRepositoryStub = mock(AuthenRepository::class.java)

        loginViewModel = Mockito.spy(LoginViewModel(dataRepositoryStub, authenRepositoryStub))
    }

//    @Test
//    fun getRoom_success() {
//        `when`(dataRepositoryStub.getRoomNumber())
//                .thenReturn(Single.just("1"))
//
//        loginViewModel.getRoomNumber()
//        assertEquals(loginViewModel.observeRoomNumber().value, "1")
//    }

//    @Test
//    fun authen_success() {
//        `when`(authenRepositoryStub.authentication("test", "1234"))
//                .thenReturn(Single.just("success"))
//        `when`(dataRepositoryStub.getRoomNumber())
//                .thenReturn(Single.just("1"))
//
//        loginViewModel.authentication("test", "1234")
//        assertEquals(loginViewModel.getResult(), "success")
//    }

}