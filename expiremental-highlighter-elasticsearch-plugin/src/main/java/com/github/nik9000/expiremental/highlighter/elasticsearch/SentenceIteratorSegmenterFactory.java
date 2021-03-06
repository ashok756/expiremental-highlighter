package com.github.nik9000.expiremental.highlighter.elasticsearch;

import java.text.BreakIterator;
import java.util.Locale;

import com.github.nik9000.expiremental.highlighter.Segmenter;
import com.github.nik9000.expiremental.highlighter.snippet.BreakIteratorSegmenter;

public class SentenceIteratorSegmenterFactory implements SegmenterFactory {
    private final Locale locale;
    private final int boundaryMaxScan;

    public SentenceIteratorSegmenterFactory(Locale locale, int boundaryMaxScan) {
        this.locale = locale;
        this.boundaryMaxScan = boundaryMaxScan;
    }

    @Override
    public Segmenter build(String value) {
        BreakIterator breakIterator = BreakIterator.getSentenceInstance(locale);
        breakIterator.setText(value);
        return new BreakIteratorSegmenter(breakIterator);
    }

    @Override
    public String extractNoMatchFragment(String value, int size) {
        // Just find the next sentence break after the size which is in the
        // spirit of the Segmenter, even if it doesn't use it.
        BreakIterator breakIterator = BreakIterator.getSentenceInstance(locale);
        breakIterator.setText(value);
        int end = breakIterator.preceding(size + boundaryMaxScan);
        if (end > 0) {
            return value.substring(0, end);
        }
        // If the sentence is too far away, try a word
        breakIterator = BreakIterator.getWordInstance(locale);
        breakIterator.setText(value);
        end = breakIterator.preceding(size + boundaryMaxScan);
        if (end > 0) {
            return value.substring(0, end);
        }
        // If the word is too far away, just snap it at the size.
        return value.substring(0, size);
    }
}
