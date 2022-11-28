package org.briarproject.masterproject.android.util;

public interface ItemReturningAdapter<I> {

	I getItemAt(int position);

	int getItemCount();

}
