// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory.meta;

import java.util.List;

public interface BookMeta extends ItemMeta
{
    boolean hasTitle();
    
    String getTitle();
    
    boolean setTitle(final String p0);
    
    boolean hasAuthor();
    
    String getAuthor();
    
    void setAuthor(final String p0);
    
    boolean hasGeneration();
    
    Generation getGeneration();
    
    void setGeneration(final Generation p0);
    
    boolean hasPages();
    
    String getPage(final int p0);
    
    void setPage(final int p0, final String p1);
    
    List<String> getPages();
    
    void setPages(final List<String> p0);
    
    void setPages(final String... p0);
    
    void addPage(final String... p0);
    
    int getPageCount();
    
    BookMeta clone();
    
    public enum Generation
    {
        ORIGINAL("ORIGINAL", 0), 
        COPY_OF_ORIGINAL("COPY_OF_ORIGINAL", 1), 
        COPY_OF_COPY("COPY_OF_COPY", 2), 
        TATTERED("TATTERED", 3);
        
        private Generation(final String name, final int ordinal) {
        }
    }
}
