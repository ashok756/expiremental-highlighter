package com.github.nik9000.expiremental.highlighter.hit.weight;

import com.github.nik9000.expiremental.highlighter.hit.TermWeigher;

/**
 * Weighs terms by multiplying the weight of two delegates. Lazy so if the
 * first TermWeigher returns 0 the second one isn't called.
 */
public class MultiplyingTermWeigher<T> implements TermWeigher<T> {
    private final TermWeigher<T> first;
    private final TermWeigher<T> second;

    public MultiplyingTermWeigher(TermWeigher<T> first, TermWeigher<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public float weigh(T term) {
        float weight = first.weigh(term);
        if (weight == 0) {
            return 0;
        }
        return weight * second.weigh(term);
    }

}
