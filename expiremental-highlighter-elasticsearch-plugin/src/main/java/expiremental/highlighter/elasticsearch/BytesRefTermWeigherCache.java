package expiremental.highlighter.elasticsearch;

import org.apache.lucene.util.BytesRef;
import org.elasticsearch.common.util.BigArrays;
import org.elasticsearch.common.util.BytesRefHash;
import org.elasticsearch.common.util.FloatArray;

import expiremental.highlighter.hit.weight.CachingTermWeigher;

/**
 * Implementation of CachingTermWeighter.Cache using Elasticsearch's BytesRef
 * hashing infrastructure.
 */
public class BytesRefTermWeigherCache implements CachingTermWeigher.Cache<BytesRef> {
    private static final long INITIAL_CAPACITY = 8;

    private final BytesRefHash bytes;
    private final FloatArray weights;
    
    public BytesRefTermWeigherCache(BigArrays bigArrays) {
        bytes = new BytesRefHash(INITIAL_CAPACITY, bigArrays);
        weights = bigArrays.newFloatArray(INITIAL_CAPACITY);
    }
    
    @Override
    public float get(BytesRef term) {
        long id = bytes.find(term);
        if (id < 0) {
            return -1;
        }
        return weights.get(id);
    }

    @Override
    public void put(BytesRef term, float weight) {
        long id = bytes.add(term);
        if (id < 0) {
            // Already seen it.  Odd.
            id = -1 - id;
        }
        weights.set(id, weight);
    }
}
