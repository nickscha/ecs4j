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

import java.util.List;

/**
 * ECS4J System Specification
 * <p>
 * An ECS <b>S</b>ystem contains the actual <b>behaviour/logic</b> for the
 * supplied {@link ECSComponent} data from the {@link ECSEntityManager}.
 * </p>
 * <b>Example:</b>
 *
 * <pre>
 * public class MovementSystem implements {@link ECSSystem} {
 *
 *     &#64;Override
 *     public void update(List&lt;ECSComponent&gt; components) {
 *        Position pos = (Position) components.get(0);
 *        Velocity vel = (Velocity) components.get(1);
 *        System.out.println("result= " + (pos.x * vel.velX) + ":" + (pos.y * vel.velY));
 *     }
 *
 *     &#64;Override
 *     public ECSArchetype archetype() {
 *         return ECSArchetype.builder()
 *                 .all(Position.class, Velocity.class)
 *                 .build();
 *     }
 * }
 * </pre>
 *
 * @author nickscha
 * @since 0.0.1
 */
public interface ECSSystem {

    /**
     * This method will be invoked from the {@link ECSEntityManager} for each
     * entity id individually by this system.
     * <b>Example for system logic:</b>
     *
     * <pre>
     *     &#64;Override
     *     public void update(List&lt;ECSComponent&gt; components) {
     *         Position pos = (Position) components.get(0);
     *         Velocity vel = (Velocity) components.get(1);
     *         System.out.println("result= " + (pos.x * vel.velX) + ":" + (pos.y * vel.velY));
     *     }
     * </pre>
     *
     * @param components the set of component per entity supplied from the
     * {@link ECSEntityManager}
     */
    void update(List<ECSComponent> components);

    /**
     * <p>
     * Each system has to define which set of components it can process. In
     * ECS4J the required components are defined by using {@link ECSArchetype}.
     * </p>
     * <b>Example (the system required the Position and Velocity component):</b>
     *
     * <pre>
     *     &#64;Override
     *     public ECSArchetype archetype() {
     *         return ECSArchetype.builder()
     *                 .all(Position.class, Velocity.class)
     *                 .build();
     *     }
     * </pre>
     * <b>Note:</b> This method will be only called once the system has been
     * created in the entity manager
     *
     * @return the archetype (set of components required by this system)
     */
    ECSArchetype archetype();

}
