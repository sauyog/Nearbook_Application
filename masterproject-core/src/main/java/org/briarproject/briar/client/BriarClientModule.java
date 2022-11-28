package org.briarproject.briar.client;

import org.briarproject.masterproject.api.client.MessageTracker;

import dagger.Module;
import dagger.Provides;

@Module
public class BriarClientModule {

	@Provides
	MessageTracker provideMessageTracker(MessageTrackerImpl messageTracker) {
		return messageTracker;
	}
}
