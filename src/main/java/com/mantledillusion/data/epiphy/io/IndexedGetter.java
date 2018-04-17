package com.mantledillusion.data.epiphy.io;

import com.mantledillusion.data.epiphy.index.IndexContext;

public interface IndexedGetter<P, T> {

	T get(P source, IndexContext context, boolean allowNull);
}