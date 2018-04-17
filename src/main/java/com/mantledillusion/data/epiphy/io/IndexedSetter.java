package com.mantledillusion.data.epiphy.io;

import com.mantledillusion.data.epiphy.index.IndexContext;

public interface IndexedSetter<P, T> {

	void set(P target, T value, IndexContext context);
}