package bloomd;

import bloomd.args.CreateFilterArgs;
import bloomd.replies.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("SameParameterValue")
public interface BloomdClient {
    CompletableFuture<List<BloomdFilter>> list();

    CompletableFuture<List<BloomdFilter>> list(String prefix);

    CompletableFuture<CreateResult> create(String filterName);

    CompletableFuture<CreateResult> create(CreateFilterArgs args);

    CompletableFuture<Boolean> drop(String filterName);

    CompletableFuture<Boolean> close(String filterName);

    CompletableFuture<ClearResult> clear(String filterName);

    CompletableFuture<StateResult> check(String filterName, String key);

    CompletableFuture<StateResult> set(String filterName, String key);

    CompletableFuture<List<StateResult>> multi(String filterName, String... keys);

    CompletableFuture<List<StateResult>> bulk(String filterName, String... keys);

    CompletableFuture<BloomdInfo> info(String filterName);

    CompletableFuture<Boolean> flush(String filterName);

    /**
     * @return a future that will resolve to a {@link BloomdClient} implementation
     */
    static CompletableFuture<BloomdClient> newInstance(String host, int port) {
        return newInstance(host, port, 1);
    }

    /**
     * @return a future that will resolve to a {@link BloomdClient} implementation
     */
    static CompletableFuture<BloomdClient> newInstance(String host, int port, int maxConnections) {
        return newInstance(host, port, maxConnections, 2_000, 2_000);
    }

    /**
     * @return a future that will resolve to a {@link BloomdClient} implementation
     */
    static CompletableFuture<BloomdClient> newInstance(String host, int port, int maxConnections, int connectionTimeoutMillis, int acquireTimeoutMillis) {
        return new BloomdClientPool(host, port, maxConnections, connectionTimeoutMillis, acquireTimeoutMillis).acquire();
    }
}
