package student.laurens.novibackend.entities;

import org.springframework.http.HttpMethod;

/**
 * Base class for entities to implement, which are owned by a user, required for typing purposes.
 *
 * Used by {@link student.laurens.novibackend.controllers.BaseRestController} to provide a generic way of protecting
 * resources that can only be modified by their owner.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class AbstractOwnedEntity extends AbstractEntity {

    abstract public Integer getOwnerUid();

    public static boolean isMethodProtected(HttpMethod method){
        if(method.equals(HttpMethod.GET)){
            return isGetProtected();
        }
        if(method.equals(HttpMethod.PUT)){
            return isPutProtected();
        }
        if(method.equals(HttpMethod.DELETE)){
            return isDeleteProtected();
        }
        return false;
    }

    protected static boolean isGetProtected(){
        return false;
    }

    protected static boolean isPutProtected(){
        return true;
    }

    protected static boolean isDeleteProtected(){
        return true;
    }
}
