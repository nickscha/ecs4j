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
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.LongStream;

import org.junit.Test;

public class ECSTest {

    private static final int ITERATIONS = 100;
    private static final int ENTITIES = 10_000;
    private static final AtomicLong counter = new AtomicLong(0);

    @Test
    public void runECS() {
        Random rand = new Random(123456789);

        ECSEntityManager em = ECSEntityManager.getOrCreate();

        em.createSystem(new MovementSystem());
        em.createSystem(new MovementSystem2());
        em.createSystem(new MovementSystem3());
        em.createSystem(new MovementSystem4());
        em.createSystem(new MovementSystem5());
        em.createSystem(new MovementSystem6());
        em.createSystem(new MovementSystem7());
        em.createSystem(new MovementSystem8());
        em.createSystem(new MovementSystem9());
        em.createSystem(new MovementSystem10());
        em.createSystem(new PositionOnlySystem());

        long start = System.currentTimeMillis();
        for (int i = 0; i < ENTITIES; i++) {
            em.createEntity(
                    new Position(rand.nextFloat(), rand.nextFloat()),
                    new Velocity(rand.nextFloat(), rand.nextFloat())
            );
        }
        System.out.println("creation: " + (System.currentTimeMillis() - start) + "ms");

        runECS("uc", e -> em.update());

        System.out.println("systems calls: " + counter.get());
    }

    private void runECS(String prefix, Consumer<String> run) {
        long[] times = new long[ITERATIONS];
        System.out.print(prefix + ";");
        for (int i = 0; i < ITERATIONS; i++) {
            long start = System.currentTimeMillis();
            run.accept(prefix);
            final long time = (System.currentTimeMillis() - start);
            times[i] = time;
            System.out.print(time + ";");
        }
        System.out.println("");
        System.out.println(prefix + ";min(" + LongStream.of(times).min().getAsLong() + ") avg(" + LongStream.of(times).average().getAsDouble() + ") max(" + LongStream.of(times).max().getAsLong() + ")");
    }

    class Position implements ECSComponent {

        private final float x;
        private final float y;

        public Position(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

    }

    class Velocity implements ECSComponent {

        private final float velX;
        private final float velY;

        public Velocity(float velX, float velY) {
            this.velX = velX;
            this.velY = velY;
        }

        public float getVelX() {
            return velX;
        }

        public float getVelY() {
            return velY;
        }

    }

    class MovementSystem implements ECSSystem {

        @Override
        @SuppressWarnings("unused")
        public void update(List<ECSComponent> archetype) {
            Position pos = (Position) archetype.get(0);
            Velocity vel = (Velocity) archetype.get(1);
            float res = (pos.getX() * vel.getVelX()) + (pos.getY() * vel.getVelY());
            counter.incrementAndGet();
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder().all(Position.class, Velocity.class).build();
        }

    }

    class MovementSystem2 implements ECSSystem {

        @Override
        @SuppressWarnings("unused")
        public void update(List<ECSComponent> archetype) {
            Position pos = (Position) archetype.get(0);
            Velocity vel = (Velocity) archetype.get(1);
            float res = (pos.getX() * vel.getVelX()) + (pos.getY() * vel.getVelY());
            counter.incrementAndGet();
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder().all(Position.class, Velocity.class).build();
        }

    }

    class MovementSystem3 implements ECSSystem {

        @Override
        @SuppressWarnings("unused")
        public void update(List<ECSComponent> archetype) {
            Position pos = (Position) archetype.get(0);
            Velocity vel = (Velocity) archetype.get(1);
            float res = (pos.getX() * vel.getVelX()) + (pos.getY() * vel.getVelY());
            counter.incrementAndGet();
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder().all(Position.class, Velocity.class).build();
        }
    }

    class MovementSystem4 implements ECSSystem {

        @Override
        @SuppressWarnings("unused")
        public void update(List<ECSComponent> archetype) {
            Position pos = (Position) archetype.get(0);
            Velocity vel = (Velocity) archetype.get(1);
            float res = (pos.getX() * vel.getVelX()) + (pos.getY() * vel.getVelY());
            counter.incrementAndGet();
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder().all(Position.class, Velocity.class).build();
        }

    }

    class MovementSystem5 implements ECSSystem {

        @Override
        @SuppressWarnings("unused")
        public void update(List<ECSComponent> archetype) {
            Position pos = (Position) archetype.get(0);
            Velocity vel = (Velocity) archetype.get(1);
            float res = (pos.getX() * vel.getVelX()) + (pos.getY() * vel.getVelY());
            counter.incrementAndGet();
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder().all(Position.class, Velocity.class).build();
        }

    }

    class MovementSystem6 implements ECSSystem {

        @Override
        @SuppressWarnings("unused")
        public void update(List<ECSComponent> archetype) {
            Position pos = (Position) archetype.get(0);
            Velocity vel = (Velocity) archetype.get(1);
            float res = (pos.getX() * vel.getVelX()) + (pos.getY() * vel.getVelY());
            counter.incrementAndGet();
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder().all(Position.class, Velocity.class).build();
        }

    }

    class MovementSystem7 implements ECSSystem {

        @Override
        @SuppressWarnings("unused")
        public void update(List<ECSComponent> archetype) {
            Position pos = (Position) archetype.get(0);
            Velocity vel = (Velocity) archetype.get(1);
            float res = (pos.getX() * vel.getVelX()) + (pos.getY() * vel.getVelY());
            counter.incrementAndGet();
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder().all(Position.class, Velocity.class).build();
        }
    }

    class MovementSystem8 implements ECSSystem {

        @Override
        @SuppressWarnings("unused")
        public void update(List<ECSComponent> archetype) {
            Position pos = (Position) archetype.get(0);
            Velocity vel = (Velocity) archetype.get(1);
            float res = (pos.getX() * vel.getVelX()) + (pos.getY() * vel.getVelY());
            counter.incrementAndGet();
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder().all(Position.class, Velocity.class).build();
        }
    }

    class MovementSystem9 implements ECSSystem {

        @Override
        @SuppressWarnings("unused")
        public void update(List<ECSComponent> archetype) {
            Position pos = (Position) archetype.get(0);
            Velocity vel = (Velocity) archetype.get(1);
            float res = (pos.getX() * vel.getVelX()) + (pos.getY() * vel.getVelY());
            counter.incrementAndGet();
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder().all(Position.class, Velocity.class).build();
        }

    }

    class MovementSystem10 implements ECSSystem {

        @Override
        @SuppressWarnings("unused")
        public void update(List<ECSComponent> archetype) {
            Position pos = (Position) archetype.get(0);
            Velocity vel = (Velocity) archetype.get(1);
            float res = (pos.getX() * vel.getVelX()) + (pos.getY() * vel.getVelY());
            counter.incrementAndGet();
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder().all(Position.class, Velocity.class).build();
        }
    }

    class MovementSystemSpecial implements ECSSystem {

        @Override
        public void update(List<ECSComponent> archetype) {
            System.out.println("MovementSystemSpecial::updated");
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder().all(Position.class, Velocity.class).build();
        }
    }

    class PositionOnlySystem implements ECSSystem {

        @Override
        public void update(List<ECSComponent> archetype) {
            System.out.println("position system called!");
        }

        @Override
        public ECSArchetype archetype() {
            return ECSArchetype.builder()
                    .all(Position.class)
                    .none(Velocity.class)
                    .build();
        }

    }

}
