/*
 * Copyright (C) 2018 nickscha
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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * ECS4J Archetype
 * @author nickscha
 * @since 0.0.1
 */
public final class ECSArchetype {

    private final String id;
    private final int[] all;
    private final int[] any;
    private final int[] none;

    private ECSArchetype(final String id, final int[] all, final int[] any, final int[] none) {
        this.id = id;
        this.all = all;
        this.any = any;
        this.none = none;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean valid(Class<? extends ECSComponent>... components) {
        return valid(Arrays.asList(components));
    }

    public boolean valid(List<Class<? extends ECSComponent>> components) {
        final List<Integer> componentIds = components.stream().map(Builder::getOrCreateComponentId).sorted().collect(Collectors.toList());

        // check none        
        for (int cid : none) {
            if (componentIds.contains(cid)) {
                return false;
            }
        }

        // check all
        for (int cid : all) {
            if (!componentIds.contains(cid)) {
                return false;
            }
        }

        // check any
        boolean check = true;
        for (int cid : any) {
            if (componentIds.contains(cid)) {
                return true;
            } else {
                check = false;
            }
        }
        if (!check && any.length > 0) {
            return false;
        }

        return true;
    }

    public String getId() {
        return id;
    }

    public static class Builder {

        private static final Map<Class<? extends ECSComponent>, Integer> CLASS_2_CID = new HashMap<>(256);
        private static final AtomicInteger CID_SEQ = new AtomicInteger(0);

        private static final String DIM_ALL = "&";
        private static final String DIM_ANY = ",";
        private static final String DIM_NONE = "!";

        private List<Integer> all = new ArrayList<>(4);
        private List<Integer> any = new ArrayList<>(4);
        private List<Integer> none = new ArrayList<>(4);

        public Builder all(Class<? extends ECSComponent>... components) {
            all = Arrays.asList(components).stream().map(Builder::getOrCreateComponentId).sorted().collect(Collectors.toList());
            return this;
        }

        public Builder any(Class<? extends ECSComponent>... components) {
            any = Arrays.asList(components).stream().map(Builder::getOrCreateComponentId).sorted().collect(Collectors.toList());
            return this;
        }

        public Builder none(Class<? extends ECSComponent>... components) {
            none = Arrays.asList(components).stream().map(Builder::getOrCreateComponentId).sorted().collect(Collectors.toList());
            return this;
        }

        private static int getOrCreateComponentId(Class<? extends ECSComponent> component) {
            return CLASS_2_CID.computeIfAbsent(component, e -> CID_SEQ.getAndIncrement());
        }

        private String buildComponentsId(List<Integer> all, List<Integer> any, List<Integer> none) {
            String id = "";
            for (int cid : all) {
                id = id.concat(cid + DIM_ALL);
            }
            for (int cid : any) {
                id = id.concat(cid + DIM_ANY);
            }
            for (int cid : none) {
                id = id.concat(cid + DIM_NONE);
            }
            return id;
        }

        public ECSArchetype build() {
            return new ECSArchetype(buildComponentsId(all, any, none), all.stream().mapToInt(i -> i).toArray(), any.stream().mapToInt(i -> i).toArray(), none.stream().mapToInt(i -> i).toArray());
        }

    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ECSArchetype other = (ECSArchetype) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ECSArchetype{" + "id=" + id + '}';
    }

}
