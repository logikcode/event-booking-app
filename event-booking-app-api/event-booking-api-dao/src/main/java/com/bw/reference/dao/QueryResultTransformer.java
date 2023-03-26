package com.bw.reference.dao;

public interface QueryResultTransformer<E, T> {

    T transaform(E e);
}
