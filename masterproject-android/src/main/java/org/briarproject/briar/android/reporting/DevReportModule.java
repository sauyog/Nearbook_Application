package org.briarproject.masterproject.android.reporting;

import androidx.lifecycle.ViewModel;

import org.briarproject.masterproject.android.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class DevReportModule {

    @Binds
    @IntoMap
    @ViewModelKey(ReportViewModel.class)
    abstract ViewModel bindReportViewModel(ReportViewModel reportViewModel);

    @Binds
    abstract Thread.UncaughtExceptionHandler bindUncaughtExceptionHandler(
            BriarExceptionHandler handler);

}
