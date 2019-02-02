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

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ECSEntityManagerTest {

    @Test
    public void test() {
        // Get the ECS Entity Manager an add the system
        ECSEntityManager em = ECSEntityManager.getOrCreate();
        em.createSystem(new MovementSystem());

        // Check if system exists
        Assert.assertTrue(em.hasSystem(MovementSystem.class));

        Position pos = new Position();
        pos.x = 5;
        pos.y = 2;

        Velocity vel = new Velocity();
        vel.velX = 0.5f;
        vel.velY = 0.5f;

        // Create the ECS Entity
        int carId = em.createEntity(pos, vel);

        // Run the ECS System
        em.update();

        // Check if entityId is available
        Assert.assertTrue(em.hasEntity(carId));

        // Remove entity
        Assert.assertTrue(em.removeEntity(carId));

        // Check if entityId is available
        Assert.assertFalse(em.hasEntity(carId));
    }

    class Position implements ECSComponent {

        public float x, y;
    }

    class Velocity implements ECSComponent {

        public float velX, velY;
    }

    class MovementSystem implements ECSSystem {

        @Override
        public void update(List<ECSComponent> components) {
            Position pos = (Position) components.get(0);
            Velocity vel = (Velocity) components.get(1);

            System.out.println("result= " + (pos.x * vel.velX) + ":" + (pos.y * vel.velY));
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder()
                    .all(Position.class, Velocity.class)
                    .build();
        }

    }

}
