package org.briarproject.briar.headless

import dagger.Component
import org.briarproject.bramble.BrambleCoreEagerSingletons
import org.briarproject.bramble.BrambleCoreModule
import org.briarproject.bramble.BrambleJavaModule
import org.briarproject.bramble.api.crypto.CryptoComponent
import org.briarproject.briar.BriarCoreEagerSingletons
import org.briarproject.briar.BriarCoreModule
import org.briarproject.masterproject.api.test.TestDataCreator
import javax.inject.Singleton

@Component(
    modules = [
        BrambleCoreModule::class,
        BrambleJavaModule::class,
        BriarCoreModule::class,
        HeadlessTestModule::class
    ]
)
@Singleton
internal interface BriarHeadlessTestApp : BrambleCoreEagerSingletons, BriarCoreEagerSingletons,
    HeadlessEagerSingletons {

    fun getRouter(): Router

    fun getCryptoComponent(): CryptoComponent

    fun getTestDataCreator(): TestDataCreator
}
