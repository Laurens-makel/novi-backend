package student.laurens.novibackend.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for entities to implement, required for typing purposes.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class AbstractEntity {
    protected Logger log = LoggerFactory.getLogger(AbstractEntity.class);

    abstract public Integer getId();

}
