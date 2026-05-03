package com.example.msreview.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Rating {
    UNA_ESTRELLA(1),
    DOS_ESTRELLAS(2),
    TRES_ESTRELLAS(3),
    CUATRO_ESTRELLAS(4),
    CINCO_ESTRELLAS(5);

    private final int value;

}
