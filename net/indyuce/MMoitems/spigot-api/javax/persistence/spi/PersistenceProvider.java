// 
// Decompiled by Procyon v0.5.36
// 

package javax.persistence.spi;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

public interface PersistenceProvider
{
    EntityManagerFactory createEntityManagerFactory(final String p0, final Map p1);
    
    EntityManagerFactory createContainerEntityManagerFactory(final PersistenceUnitInfo p0, final Map p1);
}
