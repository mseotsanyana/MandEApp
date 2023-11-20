package com.me.mseotsanyana.mande.application.repository.synchronization;

import java.util.ArrayList;

public interface iSyncSetRepository<T, K> {
    void synchronizeEntitySet(ArrayList<T> entitySet);
}
