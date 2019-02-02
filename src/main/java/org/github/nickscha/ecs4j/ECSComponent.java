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

/**
 * ECS Component Specification
 * <p>
 * An ECS component is a <b>container for data and nothing more.</b> It
 * constains no logic and behaviour but data.
 * </p>
 * <b>Example:</b>
 *
 * <pre>
 * public class Position implements {@link ECSComponent} { x,y }
 * public class Velocity implements {@link ECSComponent} { velX, velY }
 * </pre>
 *
 * <p>
 * In ECS <b>one entity can only contain one component of the same class</b>.
 * Given the example above one entity may have a Position and Velocity component
 * but not two positions components. Having multiple instances of the same
 * component attached to a single entity can cause unexpected results and no
 * possibility to debug system processing.
 * </p>
 *
 * <p>
 * The {@link ECSEntityManager} takes care of efficiently organizing the
 * component data for entites in memory.
 * </p>
 *
 * @author nickscha
 * @since 0.0.1
 */
public interface ECSComponent {

}
