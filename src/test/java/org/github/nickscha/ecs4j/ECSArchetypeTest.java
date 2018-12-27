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

import org.junit.Assert;
import org.junit.Test;

public class ECSArchetypeTest {

    @Test
    public void testAny() {
        ECSArchetype archetype = ECSArchetype.builder()
                .any(Position.class, AnyComponent.class)
                .build();

        Assert.assertFalse(archetype.valid(Velocity.class));
        Assert.assertFalse(archetype.valid(DoNotUse.class));

        Assert.assertTrue(archetype.valid(Position.class));
        Assert.assertTrue(archetype.valid(AnyComponent.class));
        Assert.assertTrue(archetype.valid(Position.class, AnyComponent.class));
    }

    @Test
    public void testAll() {
        ECSArchetype archetype = ECSArchetype.builder()
                .all(Position.class, Velocity.class)
                .build();

        Assert.assertFalse(archetype.valid(Position.class));
        Assert.assertFalse(archetype.valid(Velocity.class));
        Assert.assertFalse(archetype.valid(DoNotUse.class));
        Assert.assertFalse(archetype.valid(Position.class, DoNotUse.class));
        Assert.assertFalse(archetype.valid(Velocity.class, DoNotUse.class));

        Assert.assertTrue(archetype.valid(Position.class, Velocity.class));
        Assert.assertTrue(archetype.valid(Velocity.class, Position.class, AnyComponent.class));
        Assert.assertTrue(archetype.valid(AnyComponent.class, Velocity.class, Position.class));
    }

    @Test
    public void testAllNone() {
        ECSArchetype archetype = ECSArchetype.builder()
                .all(Position.class, Velocity.class)
                .none(DoNotUse.class)
                .build();

        Assert.assertFalse(archetype.valid(Position.class));
        Assert.assertFalse(archetype.valid(Velocity.class));
        Assert.assertFalse(archetype.valid(DoNotUse.class));
        Assert.assertFalse(archetype.valid(Position.class, DoNotUse.class));
        Assert.assertFalse(archetype.valid(Velocity.class, DoNotUse.class));
        Assert.assertFalse(archetype.valid(Position.class, Velocity.class, DoNotUse.class));

        Assert.assertTrue(archetype.valid(Position.class, Velocity.class));
        Assert.assertTrue(archetype.valid(Velocity.class, Position.class));
        Assert.assertTrue(archetype.valid(Velocity.class, Position.class, AnyComponent.class));
    }

    class DoNotUse implements ECSComponent {
    }

    class AnyComponent implements ECSComponent {
    }

    class Position implements ECSComponent {
    }

    class Velocity implements ECSComponent {
    }
}
