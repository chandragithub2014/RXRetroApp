package com.rx.rxretroapp.di

import com.rx.rxretroapp.retrorx.repository.RetroRXRepository
import com.rx.rxretroapp.retrorx.viewmodel.RetroRXViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetroRxModule::class])
interface RetroRxComponent  {

 fun inject(retroRXViewModel: RetroRXViewModel)

}