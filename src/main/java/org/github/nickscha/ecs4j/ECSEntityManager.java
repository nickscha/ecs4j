/*
 * Copyright (C) 2019 nickscha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.github.nickscha.ecs4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * ECS4J EntityManager
 *
 * @author nickscha
 * @since 0.0.1
 *
 */
public final class ECSEntityManager {

    private static final ECSEntityManager INSTANCE = new ECSEntityManager();

    // Entity Store
    private final Map<Integer, List<ECSComponent>> eId2data = new HashMap<>();
    private final AtomicInteger eIdSeq = new AtomicInteger(0);

    // System Store
    private final Map<Integer, ECSSystem> sId2data = new HashMap<>();
    private final Map<Integer, ECSArchetype> sId2archetype = new HashMap<>();
    private final AtomicInteger sIdSeq = new AtomicInteger(0);
    private final Map<Class<? extends ECSSystem>, Integer> sClass2sId = new HashMap<>();

    // Archetype Store
    private final Map<ECSArchetype, List<Integer>> archetype2eids = new HashMap<>();

    private ECSEntityManager() {
    }

    public static ECSEntityManager getOrCreate() {
        return INSTANCE;
    }

    public int createEntity(ECSComponent... components) {
        return createEntity(Arrays.asList(components));
    }

    public int createEntity(List<ECSComponent> components) {
        final int entityId = createEntityId();
        eId2data.put(entityId, components);

        // assign to archetpye
        final List<Class<? extends ECSComponent>> tmp = components.stream().map(e -> e.getClass()).collect(Collectors.toList());
        for (Entry<ECSArchetype, List<Integer>> entry : archetype2eids.entrySet()) {
            if (entry.getKey().valid(tmp)) {
                entry.getValue().add(entityId);
            }
        }

        return entityId;
    }

    public ECSEntityManager addComponent(int entityId, ECSComponent component) {
        if (hasEntity(entityId)) {
            eId2data.get(entityId).add(component);
            final List<Class<? extends ECSComponent>> tmp = eId2data.get(entityId).stream().map(e -> e.getClass()).collect(Collectors.toList());
            // Check if the archetype for the entityId is still valid
            for (Entry<ECSArchetype, List<Integer>> entry : archetype2eids.entrySet()) {
                if (entry.getValue().contains(entityId) && !entry.getKey().valid(tmp)) {
                    // Remove entity for archetype
                    entry.getValue().remove((Integer) entityId);
                }
            }
        }
        return this;
    }

    public boolean hasComponent(int entityId, ECSComponent component) {
        if (hasEntity(entityId)) {
            return eId2data.get(entityId).contains(component);
        }
        return false;
    }

    public boolean hasEntity(int entityId) {
        return eId2data.containsKey(entityId);
    }

    public boolean removeEntity(int entityId) {
        if (hasEntity(entityId)) {
            for (List<Integer> entityIds : archetype2eids.values()) {
                entityIds.removeIf(e -> e == entityId);
            }
            eId2data.remove(entityId);
            return true;
        }
        return false;
    }

    public ECSEntityManager createSystem(ECSSystem system) {
        final int systemId = getOrCreateSystemId(system.getClass());
        final ECSArchetype archetype = system.archetype();
        sId2data.put(systemId, system);
        sId2archetype.put(systemId, archetype);

        // If the system is added after entities have been created all entities have to be checked
        if (!archetype2eids.containsKey(archetype) && eId2data.size() > 0) {
            archetype2eids.computeIfAbsent(archetype, e -> new ArrayList());
            assignEntities2Archetype(archetype, eId2data);
        } else {
            archetype2eids.computeIfAbsent(archetype, e -> new ArrayList());
        }

        return this;
    }

    private void assignEntities2Archetype(ECSArchetype archetype, Map<Integer, List<ECSComponent>> eId2data) {
        for (Entry<Integer, List<ECSComponent>> entry : eId2data.entrySet()) {
            final List<Class<? extends ECSComponent>> tmp = entry.getValue().stream().map(e -> e.getClass()).collect(Collectors.toList());
            if (archetype.valid(tmp)) {
                archetype2eids.get(archetype).add(entry.getKey());
            }
        }
    }

    public boolean hasSystem(Class<? extends ECSSystem> system) {
        return sClass2sId.containsKey(system);
    }

    private int getOrCreateSystemId(Class<? extends ECSSystem> system) {
        return sClass2sId.computeIfAbsent(system, e -> sIdSeq.getAndIncrement());
    }

    private int createEntityId() {
        return eIdSeq.getAndIncrement();
    }

    public void update() {
        CompletableFuture[] futures = new CompletableFuture[sId2archetype.size()];
        int index = 0;
        for (Entry<Integer, ECSArchetype> entry : sId2archetype.entrySet()) {
            futures[index] = CompletableFuture.runAsync(()
                    -> runSystem(
                            sId2data.get(entry.getKey()),
                            archetype2eids.get(entry.getValue())
                    )
            );
            index++;
        }
        try {
            CompletableFuture.allOf(futures).get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ECSEntityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void runSystem(ECSSystem system, List<Integer> entities) {
        entities.stream().map(eId2data::get).forEach(system::update);
    }

}
