package com.hjiee.presentation.ui.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hjiee.core.manager.FirebaseConfigManager
import com.hjiee.core.manager.UpdateRequiredStatus
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.domain.usecase.login.GetTokenUseCase
import com.hjiee.domain.usecase.login.LoginUseCase
import com.hjiee.presentation.base.BaseViewModel
import com.hjiee.presentation.ui.login.model.LoginActionEntity
import com.hjiee.presentation.ui.splash.model.SplashActionEntity
import com.hjiee.presentation.ui.splash.view.SplashStringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenUseCase: GetTokenUseCase,
    private val preference: SharedPreferenceProvider,
    private val stringProvider: SplashStringProvider,
    private val versionManager: VersionManager,
    private val firebaseConfigManager: FirebaseConfigManager
) : BaseViewModel() {

    init {
//        firebaseConfigManager.fetchRemoteConfig()
    }

    private val _updateRequiredStatus = MutableLiveData<UpdateRequiredStatus>()
    val updateRequiredStatus: LiveData<UpdateRequiredStatus> get() = _updateRequiredStatus

    fun checkForUpdate() {
        when (versionManager.updateInfo()) {
            UpdateRequiredStatus.Force -> {
                _updateRequiredStatus.value = UpdateRequiredStatus.Force
                notifyActionEvent(
                    SplashActionEntity.ForceUpdate(
                        stringProvider.getString(
                            UpdateRequiredStatus.Force
                        )
                    )
                )
            }
            UpdateRequiredStatus.Recommend -> {
                _updateRequiredStatus.value = UpdateRequiredStatus.Recommend
                notifyActionEvent(
                    SplashActionEntity.RecommendUpdate(
                        stringProvider.getString(
                            UpdateRequiredStatus.Recommend
                        )
                    )
                )
            }
            UpdateRequiredStatus.None -> {
                _updateRequiredStatus.value = UpdateRequiredStatus.None
                viewModelScope.launch {
                    delay(1500)
                    autoLogin()
                }
            }
        }
    }

    fun autoLogin() {
        tokenUseCase.execute { accessToken ->
            if (accessToken.isEmpty()) {
                notifyActionEvent(LoginActionEntity.FailLogin)
            } else {
                viewModelScope.launch {
                    runCatching {
                        loginUseCase.execute(accessToken)
                    }.onSuccess {
                        if (it.accessToken.isNotEmpty()) {
                            notifyActionEvent(LoginActionEntity.SuccessLogin)
                        } else {
                            notifyActionEvent(LoginActionEntity.FailLogin)
                        }
                    }.onFailure {
                        notifyActionEvent(LoginActionEntity.FailLogin)
                    }
                }
            }
        }
    }
}