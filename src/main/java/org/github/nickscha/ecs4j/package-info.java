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
/**
 * ECS4J - Pure Entity Component System for Java
 *
 * <p>
 * ECS4J provides a lightweight Entity Component System implementation which is
 * easy to understand and provides a transparent and fluent API.
 * </p>
 *
 * <b>Goals:</b>
 * <ul>
 * <li>Pure ECS implementation (composition over inheritance)</li>
 * <li>Memory efficient and fast access/querying of entites and their component
 * data in systems</li>
 * <li>Easy API usage</li>
 * </ul>
 * <b>What is ECS ?</b>
 * <p>
 * The <u>E</u>ntity <u>C</u>omponent <u>S</u>ystem (in short ECS) is an
 * architectural pattern following the <b>composition over inheritance</b>
 * pattern and is highly compatible with data oriented design techniques. ECS is
 * commonly used in applications were inheritance would lead to inflexible code
 * and confusing/unclear code structures in order to manage the data and
 * behaviours of entities. Due to the strict separation of data and behaviour it
 * allows easy multithreading and serialization of data.
 * </p>
 * ECS has a very strict terminology:
 * <ul>
 * <li><b>E</b>ntity - A unique identifier for a set of components.</li>
 * <li><b>C</b>omponent - raw data and nothing more. No logic/behaviour.</li>
 * <li><b>S</b>ystem - Contains the logic/behaviour for a list of entity
 * components as required by the system. (Usually a system runs contuniously in
 * private threads)</li>
 * </ul>
 * In ECS4J systems can access and query component data required for the update
 * method from the {@link ECSEntityManager}.
 * <b>ECS Example</b>
 * <p>
 * In this example we will create a MovementSystem which will modify a cars
 * position by velocity.
 * </p>
 *
 * <pre>
 * public class Position implements ECSComponent {
 *     public float x, y;
 * }
 *
 * public class Velocity implements ECSComponent {
 *     public float velX, velY;
 * }
 *
 * public class MovementSystem implements ECSSystem {
 *     &#64;Override
 *     public void update(List&lt;ECSComponent&gt; components) {
 *         Position pos = (Position) components.get(0);
 *         Velocity vel = (Velocity) components.get(1);
 *         System.out.println("result= " + (pos.x * vel.velX) + ":" + (pos.y * vel.velY));
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
 * We have now defied our logic in the MovementSystem class and seperated
 * required data in ECS components. But how can we create the actual car entity
 * and run the code ?
 *
 * <pre>
 * ECSEntityManager em = ECSEntityManager.getOrCreate();
 * </pre>
 *
 * @author nickscha
 * @since 0.0.1
 */
package org.github.nickscha.ecs4j;
