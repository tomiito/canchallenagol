package ar.com.ironsoft.marroccl.web.guice;

import java.util.LinkedList;

import com.google.common.base.Preconditions;

/**
 * @author Tomas de Priede
 */
public class GuiceClassesHolder {

    protected LinkedList<Class<?>> classes = new LinkedList<>();

    public GuiceClassesHolder(LinkedList<Class<?>> allClasses) {
        this.classes = allClasses;
    }

    public <T> LinkedList<Class<T>> findClassesByType(Class<T> type) {
        Preconditions.checkNotNull(type);
        //
        LinkedList<Class<T>> result = new LinkedList<>();
        for (Class<?> clazz : classes) {
            if (type.isAssignableFrom(clazz)) {
                result.add((Class<T>) clazz);
            }
        }
        return result;
    }
}
