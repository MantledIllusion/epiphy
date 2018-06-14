package com.mantledillusion.data.epiphy.io;

import com.mantledillusion.data.epiphy.context.Context;

public interface IndexedSetter<P, T> {

	void set(P target, T value, Context context);
}