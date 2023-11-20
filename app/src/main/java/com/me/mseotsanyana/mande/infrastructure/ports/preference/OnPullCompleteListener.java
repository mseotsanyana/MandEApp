package com.me.mseotsanyana.mande.infrastructure.ports.preference;

import com.me.mseotsanyana.mande.infrastructure.services.CSessionManagerImpl;

public interface OnPullCompleteListener {
    void OnPullSucceeded(CSessionManagerImpl preferences);
    void OnPullFailed(Exception e);
}
