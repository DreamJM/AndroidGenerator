package com.dream.android.sample.lib.eventbus;

import android.support.annotation.NonNull;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dream on 15/10/28.
 */
public class RxBus {

    private static RxBus instance;

    private RxBus() {

    }

    public static synchronized RxBus get() {
        if(null == instance) {
            instance = new RxBus();
        }
        return instance;
    }

    private ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    public <T> Observable<T> register(@NonNull Object tag, @NonNull Class<T> clazz) {
        List<Subject> subjects = subjectMapper.get(tag);
        if(null == subjects) {
            subjects = new ArrayList<>();
            subjectMapper.put(tag, subjects);
        }
        Subject<T, T> subject;
        subjects.add(subject = PublishSubject.create());
        return subject;
    }

    public <T> Observable<T> register(@NonNull Class<T> clazz) {
        return register(clazz.getName(), clazz);
    }

    public void unregister(@NonNull Object tag, @NonNull Observable observable) {
        List<Subject> subjects = subjectMapper.get(tag);
        if(null != subjects) {
            subjects.remove((Subject)observable);
            if(subjects.size() == 0) {
                subjectMapper.remove(tag);
            }
        }
    }

    public void unregister(@NonNull Class clazz, @NonNull Observable observable) {
        unregister(clazz.getName(), observable);
    }

    public void post(@NonNull Object content) {
        post(content.getClass().getName(), content);
    }

    @SuppressWarnings("unchecked")
    public void post(@NonNull Object tag, @NonNull Object content) {
        List<Subject> subjects = subjectMapper.get(tag);
        if(null != subjects && subjects.size() > 0) {
            for(Subject subject : subjects) {
                subject.onNext(content);
            }
        }
    }
}
