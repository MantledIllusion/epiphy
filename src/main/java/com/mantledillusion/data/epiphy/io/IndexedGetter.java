package com.mantledillusion.data.epiphy.io;

import com.mantledillusion.data.epiphy.context.Context;

public interface IndexedGetter<P, T> {

	T get(P source, Context context, boolean allowNull);
}