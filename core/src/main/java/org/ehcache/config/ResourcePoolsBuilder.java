/*
 * Copyright Terracotta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ehcache.config;

import org.ehcache.config.units.MemoryUnit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ludovic Orban
 */
public class ResourcePoolsBuilder {

  private final Map<ResourceType, ResourcePool> resourcePools = new HashMap<ResourceType, ResourcePool>();

  private ResourcePoolsBuilder() {
  }

  private ResourcePoolsBuilder(ResourcePoolsBuilder resourcePoolsBuilder) {
    resourcePools.putAll(resourcePoolsBuilder.resourcePools);
  }

  public static ResourcePoolsBuilder newResourcePoolsBuilder() {
    return new ResourcePoolsBuilder();
  }

  public ResourcePoolsBuilder with(ResourceType type, long size, ResourceUnit unit, boolean persistent) {
    ResourcePoolsBuilder otherBuilder = new ResourcePoolsBuilder(this);
    otherBuilder.resourcePools.put(type, new ResourcePoolImpl(type, size, unit, persistent));
    return otherBuilder;
  }

  public ResourcePoolsBuilder heap(long size, ResourceUnit unit) {
    ResourcePoolsBuilder otherBuilder = new ResourcePoolsBuilder(this);
    otherBuilder.resourcePools.put(ResourceType.Core.HEAP, new ResourcePoolImpl(ResourceType.Core.HEAP, size, unit, false));
    return otherBuilder;
  }

  public ResourcePoolsBuilder offheap(long size, MemoryUnit unit) {
    ResourcePoolsBuilder otherBuilder = new ResourcePoolsBuilder(this);
    otherBuilder.resourcePools.put(ResourceType.Core.OFFHEAP, new ResourcePoolImpl(ResourceType.Core.OFFHEAP, size, unit, false));
    return otherBuilder;
  }

  public ResourcePoolsBuilder disk(long size, ResourceUnit unit) {
    return disk(size, unit, false);
  }

  public ResourcePoolsBuilder disk(long size, ResourceUnit unit, boolean persistent) {
    ResourcePoolsBuilder otherBuilder = new ResourcePoolsBuilder(this);
    otherBuilder.resourcePools.put(ResourceType.Core.DISK, new ResourcePoolImpl(ResourceType.Core.DISK, size, unit, persistent));
    return otherBuilder;
  }

  public ResourcePools build() {
    return new ResourcePoolsImpl(resourcePools);
  }

}
