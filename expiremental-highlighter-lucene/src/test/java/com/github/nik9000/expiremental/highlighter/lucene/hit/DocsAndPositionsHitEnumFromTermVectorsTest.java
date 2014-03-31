package expiremental.highlighter.lucene.hit;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.automaton.CompiledAutomaton;

import expiremental.highlighter.HitEnum;
import expiremental.highlighter.hit.weight.ConstantTermWeigher;
import expiremental.highlighter.lucene.WrappedExceptionFromLucene;

/**
 * Tests DocsAndPositionsHitEnum using term vectors.
 */
public class DocsAndPositionsHitEnumFromTermVectorsTest extends AbstractDocsAndPositionsHitEnumTestBase {
    protected HitEnum buildEnum(String source, Analyzer analyzer, CompiledAutomaton acceptable) {
        try {
            return DocsAndPositionsHitEnum.fromTermVectors(buildIndexReader(source, analyzer), 0, "field", acceptable, new ConstantTermWeigher<BytesRef>());
        } catch (IOException e) {
            throw new WrappedExceptionFromLucene(e);
        }
    }
}