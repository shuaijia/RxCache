package com.cv4j.rxcache.cachestrategy.impl;

import com.cv4j.rxcache.RxCache;
import com.cv4j.rxcache.domain.Record;
import com.cv4j.rxcache.domain.Source;
import com.cv4j.rxcache.cachestrategy.*;
import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;

import java.lang.reflect.Type;

/**
 * Created by tony on 2018/9/30.
 */
public class CacheFirstStrategy implements ObservableStrategy,
        FlowableStrategy,
        MaybeStrategy  {

    @Override
    public <T> Publisher<Record<T>> execute(RxCache rxCache, String key, Flowable<T> source, Type type) {

        Flowable<Record<T>> cache = rxCache.<T>load2Flowable(key, type);

        Flowable<Record<T>> remote = source
                .map(new Function<T, Record<T>>() {
                    @Override
                    public Record<T> apply(@NonNull T t) throws Exception {

                        rxCache.save(key, t);

                        return new Record<>(Source.CLOUD, key, t);
                    }
                });

        return cache.switchIfEmpty(remote);
    }

    @Override
    public <T> Maybe<Record<T>> execute(RxCache rxCache, String key, Maybe<T> source, Type type) {

        Maybe<Record<T>> cache = rxCache.<T>load2Maybe(key, type);

        Maybe<Record<T>> remote = source
                .map(new Function<T, Record<T>>() {
                    @Override
                    public Record<T> apply(@NonNull T t) throws Exception {

                        rxCache.save(key, t);

                        return new Record<>(Source.CLOUD, key, t);
                    }
                });

        return cache.switchIfEmpty(remote);
    }

    @Override
    public <T> Observable<Record<T>> execute(RxCache rxCache, String key, Observable<T> source, Type type) {

        Observable<Record<T>> cache = rxCache.<T>load2Observable(key, type);

        Observable<Record<T>> remote = source
                .map(new Function<T, Record<T>>() {
                    @Override
                    public Record<T> apply(@NonNull T t) throws Exception {

                        rxCache.save(key, t);

                        return new Record<>(Source.CLOUD, key, t);
                    }
                });

        return cache.switchIfEmpty(remote);
    }
}