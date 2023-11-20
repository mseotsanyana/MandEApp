package com.me.mseotsanyana.mande.application.structures;

import com.me.mseotsanyana.mande.application.structures.enums.EAction;

public interface IResponseDTO <T> {
    EAction getAction();
    T getData();
}
