package org.briarproject.masterproject.android.mailbox;

import androidx.lifecycle.ViewModel;

import org.briarproject.masterproject.android.viewmodel.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface MailboxModule {

    @Binds
    @IntoMap
    @ViewModelKey(MailboxViewModel.class)
    ViewModel bindMailboxViewModel(MailboxViewModel mailboxViewModel);

}
