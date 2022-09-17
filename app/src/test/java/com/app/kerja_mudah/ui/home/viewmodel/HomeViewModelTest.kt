package com.app.kerja_mudah.ui.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.entity.auth.AuthEntity
import com.app.kerja_mudah.data.entity.core.CoreEntity
import com.app.kerja_mudah.data.entity.freelancer.FreelancerEntity
import com.app.kerja_mudah.data.entity.job.JobEntity
import com.app.kerja_mudah.data.repository.auth.AuthRepository
import com.app.kerja_mudah.data.response.core.BaseResponse
import com.app.kerja_mudah.data.response.freelancer.FreelancerResponse
import com.app.kerja_mudah.data.room.job.JobRoomRepository
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest{

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel:HomeViewModel

    @Mock
    lateinit var jobEntity:JobEntity

    @Mock
    lateinit var jobRoomRepository: JobRoomRepository

    @Mock
    lateinit var freelancerEntity: FreelancerEntity

    @Mock
    lateinit var coreEntity: CoreEntity

    @Mock
    lateinit var authEntity: AuthEntity

    @Mock
    lateinit var authRepository: AuthRepository

    @Before
    fun before(){
        MockitoAnnotations.openMocks(this)

        viewModel = HomeViewModel(jobEntity, jobRoomRepository, freelancerEntity, coreEntity, authEntity, authRepository)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `list_freelancer_is_not_empty`(){
        var listFreelancer:ArrayList<FreelancerResponse> = arrayListOf()
        listFreelancer.add(FreelancerResponse())
        val baseResponse = BaseResponse<List<FreelancerResponse>>(
            code = 200,
            message = "success",
            data = listFreelancer
        )

        Mockito.`when`(freelancerEntity.getAllFreelancer()).thenReturn(
            Observable.just(baseResponse)
        )

        viewModel.getAllFreelancer()

        Mockito.verify(freelancerEntity, Mockito.times(1)).getAllFreelancer()
        Assert.assertEquals(true, viewModel.homeState.value?.dataAllFreelancer?.isNullOrEmpty() == false)
        Assert.assertEquals(BaseState.SUCCESS, viewModel.homeState.value?.allFreelancerState)
    }

    @Test
    fun `freelancer_state_failed_when_code_is_not_200`(){
        var listFreelancer:ArrayList<FreelancerResponse> = arrayListOf()
        listFreelancer.add(FreelancerResponse())
        val baseResponse = BaseResponse<List<FreelancerResponse>>(
            code = 401,
            message = "success",
            data = listFreelancer
        )

        Mockito.`when`(freelancerEntity.getAllFreelancer()).thenReturn(
            Observable.just(baseResponse)
        )

        viewModel.getAllFreelancer()

        Mockito.verify(freelancerEntity, Mockito.times(1)).getAllFreelancer()
        Assert.assertEquals(BaseState.FAILED, viewModel.homeState.value?.allFreelancerState)
    }

    @Test
    fun `freelancer_state_failed_when_message_is_not_success`(){
        var listFreelancer:ArrayList<FreelancerResponse> = arrayListOf()
        listFreelancer.add(FreelancerResponse())
        val baseResponse = BaseResponse<List<FreelancerResponse>>(
            code = 200,
            message = "Unauthorized",
            data = listFreelancer
        )

        Mockito.`when`(freelancerEntity.getAllFreelancer()).thenReturn(
            Observable.just(baseResponse)
        )

        viewModel.getAllFreelancer()

        Mockito.verify(freelancerEntity, Mockito.times(1)).getAllFreelancer()
        Assert.assertEquals(BaseState.FAILED, viewModel.homeState.value?.allFreelancerState)
    }
}