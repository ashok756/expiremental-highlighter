package com.github.nik9000.expiremental.highlighter.hit;

import com.github.nik9000.expiremental.highlighter.HitEnum;

/**
 * A HitEnum that is always empty.
 */
public final class EmptyHitEnum implements HitEnum {
    public static final EmptyHitEnum INSTANCE = new EmptyHitEnum();

    private EmptyHitEnum() {
    }

    @Override
    public boolean next() {
        return false;
    }

    @Override
    public int position() {
        return 0;
    }

    @Override
    public int startOffset() {
        return 0;
    }

    @Override
    public int endOffset() {
        return 0;
    }

    @Override
    public float weight() {
        return 0;
    }
}
