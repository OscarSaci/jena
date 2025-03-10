/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jena.tdb1.setup;

import org.apache.jena.tdb1.base.block.FileMode;

public class StoreParamsBuilder {
    // Immutable.
    static class Item<X> {
        final X value  ;
        final boolean isSet ;
        
        Item(X value, boolean isSet) {
            this.value = value ;
            this.isSet = isSet ;
        }
        @Override
        public int hashCode() {
            final int prime = 31 ;
            int result = 1 ;
            result = prime * result + (isSet ? 1231 : 1237) ;
            result = prime * result + ((value == null) ? 0 : value.hashCode()) ;
            return result ;
        }
        @Override
        public boolean equals(Object obj) {
            if ( this == obj )
                return true ;
            if ( obj == null )
                return false ;
            if ( getClass() != obj.getClass() )
                return false ;
            Item<?> other = (Item<?>)obj ;
            if ( isSet != other.isSet )
                return false ;
            if ( value == null ) {
                if ( other.value != null )
                    return false ;
            } else if ( !value.equals(other.value) )
                return false ;
            return true ;
        }
    }
    
    // See also StoreParamsConst.
    /** Database and query configuration */ 
    // Key names are the base name -  encode/decode may add a prefix.
    
    private Item<FileMode>           fileMode              = new Item<>(StoreParamsConst.fileMode, false) ;

    private Item<Integer>            blockReadCacheSize    = new Item<>(StoreParamsConst.blockReadCacheSize, false) ;

    private Item<Integer>            blockWriteCacheSize   = new Item<>(StoreParamsConst.blockWriteCacheSize, false) ;

    private Item<Integer>            Node2NodeIdCacheSize  = new Item<>(StoreParamsConst.Node2NodeIdCacheSize, false) ;

    private Item<Integer>            NodeId2NodeCacheSize  = new Item<>(StoreParamsConst.NodeId2NodeCacheSize, false) ;

    private Item<Integer>            NodeMissCacheSize     = new Item<>(StoreParamsConst.NodeMissCacheSize, false) ;

    private Item<Double>             NodeCacheInitialCapacityFactor = new Item<>(StoreParamsConst.NodeCacheInitialCapacityFactor, false);

    /** Database layout - ignored after a database is created */

    private Item<Integer>            blockSize             = new Item<>(StoreParamsConst.blockSize, false) ;

    private Item<String>             indexNode2Id          = new Item<>(StoreParamsConst.indexNode2Id, false) ;

    private Item<String>             indexId2Node          = new Item<>(StoreParamsConst.indexId2Node, false) ;

    private Item<String>             primaryIndexTriples   = new Item<>(StoreParamsConst.primaryIndexTriples, false) ;

    private Item<String[]>           tripleIndexes         = new Item<>(StoreParamsConst.tripleIndexes, false) ;

    private Item<String>             primaryIndexQuads     = new Item<>(StoreParamsConst.primaryIndexQuads, false) ;

    private Item<String[]>           quadIndexes           = new Item<>(StoreParamsConst.quadIndexes, false) ;

    private Item<String>             primaryIndexPrefix    = new Item<>(StoreParamsConst.primaryIndexPrefix, false) ;

    private Item<String[]>           prefixIndexes         = new Item<>(StoreParamsConst.prefixIndexes, false) ;

    private Item<String>             indexPrefix           = new Item<>(StoreParamsConst.indexPrefix, false) ;

    private Item<String>             prefixNode2Id         = new Item<>(StoreParamsConst.prefixNode2Id, false) ;

    private Item<String>             prefixId2Node         = new Item<>(StoreParamsConst.prefixId2Node, false) ;
    
    public static StoreParamsBuilder create() {
        return new StoreParamsBuilder() ;
    }

    public static StoreParamsBuilder create(StoreParams params) {
        return new StoreParamsBuilder(params) ;
    }

    /** Using a base set of {@link StoreParams}, and update with dynamic parameters.
     * 
     * @param baseParams
     * @param additionalParams
     * @return StoreParams
     */
    
    public static StoreParams modify(StoreParams baseParams, StoreParamsDynamic additionalParams) {
        StoreParamsBuilder b = new StoreParamsBuilder(baseParams) ;
        // Merge explicitly set params 
        if ( additionalParams.isSetFileMode() )
            b.fileMode(additionalParams.getFileMode()) ;
        
        if ( additionalParams.isSetBlockReadCacheSize() )
            b.blockReadCacheSize(additionalParams.getBlockReadCacheSize()) ;

        if ( additionalParams.isSetBlockWriteCacheSize() )
            b.blockWriteCacheSize(additionalParams.getBlockWriteCacheSize()) ;
        
        if ( additionalParams.isSetNode2NodeIdCacheSize() )            
            b.node2NodeIdCacheSize(additionalParams.getNode2NodeIdCacheSize()) ;
        
        if ( additionalParams.isSetNodeId2NodeCacheSize() )            
            b.nodeId2NodeCacheSize(additionalParams.getNodeId2NodeCacheSize()) ;
        
        if ( additionalParams.isSetNodeMissCacheSize() )
            b.nodeMissCacheSize(additionalParams.getNodeMissCacheSize()) ;

        if ( additionalParams.isSetNodeCacheInitialCapacityFactor() )
            b.nodeCacheInitialCapacityFactor(additionalParams.getNodeCacheInitialCapacityFactor()) ;

        return b.build();
    }
    

    private StoreParamsBuilder() {}
    
    /** Initial with a StoreParams as default values */
    private StoreParamsBuilder(StoreParams other) {
        this.fileMode               = other.fileMode ;
        this.blockSize              = other.blockSize ;
        this.blockReadCacheSize     = other.blockReadCacheSize ; 
        this.blockWriteCacheSize    = other.blockWriteCacheSize ; 
        this.Node2NodeIdCacheSize   = other.Node2NodeIdCacheSize ; 
        this.NodeId2NodeCacheSize   = other.NodeId2NodeCacheSize ; 
        this.NodeMissCacheSize      = other.NodeMissCacheSize ; 
        this.NodeCacheInitialCapacityFactor = other.NodeCacheInitialCapacityFactor ;

        this.indexNode2Id           = other.indexNode2Id ; 
        this.indexId2Node           = other.indexId2Node ; 
        
        this.primaryIndexTriples    = other.primaryIndexTriples ; 
        this.tripleIndexes          = other.tripleIndexes ; 
        
        this.primaryIndexQuads      = other.primaryIndexQuads ; 
        this.quadIndexes            = other.quadIndexes ; 

        this.primaryIndexPrefix     = other.primaryIndexPrefix ; 
        this.prefixIndexes          = other.prefixIndexes ; 
        this.indexPrefix            = other.indexPrefix ; 

        this.prefixNode2Id          = other.prefixNode2Id ; 
        this.prefixId2Node          = other.prefixId2Node ; 
    }
    
    public StoreParams build() {
        return new StoreParams(
                 fileMode, blockSize, blockReadCacheSize, blockWriteCacheSize, 
                 Node2NodeIdCacheSize, NodeId2NodeCacheSize, NodeMissCacheSize,
                 NodeCacheInitialCapacityFactor,
                 indexNode2Id, indexId2Node, primaryIndexTriples, tripleIndexes,
                 primaryIndexQuads, quadIndexes, primaryIndexPrefix,
                 prefixIndexes, indexPrefix,
                 prefixNode2Id, prefixId2Node) ;
    }
    
    public FileMode getFileMode() {
        return fileMode.value ;
    }
    
    public StoreParamsBuilder fileMode(FileMode fileMode) {
        this.fileMode = new Item<>(fileMode, true) ;
        return this ;
    }
    
    public int getBlockSize() {
        return blockSize.value ;
    }

    public StoreParamsBuilder blockSize(int blockSize) {
        this.blockSize = new Item<>(blockSize, true) ;
        return this ;
    }

    public int getBlockReadCacheSize() {
        return blockReadCacheSize.value ;
    }

    public StoreParamsBuilder blockReadCacheSize(int blockReadCacheSize) {
        this.blockReadCacheSize = new Item<>(blockReadCacheSize, true) ;
        return this ;
    }

    public int getBlockWriteCacheSize() {
        return blockWriteCacheSize.value ;
    }

   public StoreParamsBuilder blockWriteCacheSize(int blockWriteCacheSize) {
       this.blockWriteCacheSize = new Item<>(blockWriteCacheSize, true) ;
       return this ;
   }

    public int getNode2NodeIdCacheSize() {
        return Node2NodeIdCacheSize.value ;
    }

   public StoreParamsBuilder node2NodeIdCacheSize(int node2NodeIdCacheSize) {
       Node2NodeIdCacheSize = new Item<>(node2NodeIdCacheSize, true) ;
       return this ;
   }

    public int getNodeId2NodeCacheSize() {
        return NodeId2NodeCacheSize.value ;
    }

   public StoreParamsBuilder nodeId2NodeCacheSize(int nodeId2NodeCacheSize) {
       NodeId2NodeCacheSize = new Item<>(nodeId2NodeCacheSize, true) ;
       return this ;
   }

    public int getNodeMissCacheSize() {
        return NodeMissCacheSize.value ;
    }

   public StoreParamsBuilder nodeMissCacheSize(int nodeMissCacheSize) {
       NodeMissCacheSize = new Item<>(nodeMissCacheSize, true) ;
       return this ;
   }

   public double getNodeCacheInitialCapacityFactor() {
       return NodeCacheInitialCapacityFactor.value ;
   }

   public StoreParamsBuilder nodeCacheInitialCapacityFactor(double nodeCacheInitialCapacityFactor) {
       NodeCacheInitialCapacityFactor = new Item<>(nodeCacheInitialCapacityFactor, true) ;
       return this ;
   }

    public String getIndexNode2Id() {
        return indexNode2Id.value ;
    }

   public StoreParamsBuilder indexNode2Id(String indexNode2Id) {
       this.indexNode2Id = new Item<>(indexNode2Id, true) ;
       return this ;
   }

    public String getIndexId2Node() {
        return indexId2Node.value ;
    }

   public StoreParamsBuilder indexId2Node(String indexId2Node) {
       this.indexId2Node = new Item<>(indexId2Node, true) ;
       return this ;
   }

    public String getPrimaryIndexTriples() {
        return primaryIndexTriples.value ;
    }

   public StoreParamsBuilder primaryIndexTriples(String primaryIndexTriples) {
       this.primaryIndexTriples = new Item<>(primaryIndexTriples, true) ;
       return this ;
   }

    public String[] getTripleIndexes() {
        return tripleIndexes.value ;
    }

   public StoreParamsBuilder tripleIndexes(String[] tripleIndexes) {
       this.tripleIndexes = new Item<>(tripleIndexes, true) ;
       return this ;
   }

   public String getPrimaryIndexQuads() {
       return primaryIndexQuads.value ;
   }

   public StoreParamsBuilder primaryIndexQuads(String primaryIndexQuads) {
       this.primaryIndexQuads = new Item<>(primaryIndexQuads, true) ;
       return this ;
   }

    public String[] getQuadIndexes() {
        return quadIndexes.value ;
    }

   public StoreParamsBuilder quadIndexes(String[] quadIndexes) {
       this.quadIndexes = new Item<>(quadIndexes, true) ;
       return this ;
   }

    public String getPrimaryIndexPrefix() {
        return primaryIndexPrefix.value ;
    }

   public StoreParamsBuilder primaryIndexPrefix(String primaryIndexPrefix) {
       this.primaryIndexPrefix = new Item<>(primaryIndexPrefix, true) ;
       return this ;
   }

    public String[] getPrefixIndexes() {
        return prefixIndexes.value ;
    }

   public StoreParamsBuilder prefixIndexes(String[] prefixIndexes) {
       this.prefixIndexes = new Item<>(prefixIndexes, true) ;
       return this ;
   }

    public String getIndexPrefix() {
        return indexPrefix.value ;
    }

   public StoreParamsBuilder indexPrefix(String indexPrefix) {
       this.indexPrefix = new Item<>(indexPrefix, true) ;
       return this ;
   }

    public String getPrefixNode2Id() {
        return prefixNode2Id.value ;
    }

   public StoreParamsBuilder prefixNode2Id(String prefixNode2Id) {
       this.prefixNode2Id = new Item<>(prefixNode2Id, true) ;
       return this ;
   }

    public String getPrefixId2Node() {
        return prefixId2Node.value ;
    }

   public StoreParamsBuilder prefixId2Node(String prefixId2Node) {
       this.prefixId2Node = new Item<>(prefixId2Node, true) ;
       return this ;
   }
}

