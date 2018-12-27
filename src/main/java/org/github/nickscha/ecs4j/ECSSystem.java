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
 * ECS System Specification
 * <p>
 * An ECS system contains the actual <b>behaviour/logic</b> for the supplied
 * {@link ECSComponent} data from the {@link ECSEntityManagerOld}.
 * </p>
 * <b>Example:</b>
 *
 * <pre>
 * public class MovementSystem implements {@link ECSSystem} {
 *
 *      &#64;Override
 *      public void update(ECSEntityManager em) {
 *         List<ECSComponent> components = em.getEntities(Position.ID | Velocity.ID);
 *         ((Position) components.get(0)).x *= ((Velocity) components.get(1)).velX;
 *      }
 * }
 *
 * // Using Lambda
 * ECSSystem movementSystem = em -> {};
 * </pre>
 *
 * @author nickscha
 * @since 0.0.1
 */
public interface ECSSystem {

    void update(List<ECSComponent> components);

    ECSArchetype archetype();

}
