# ECS4J - in development

[![Maven](https://maven-badges.herokuapp.com/maven-central/com.github.nickscha/ecs4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.nickscha/ecs4j)
![Build Status](https://travis-ci.org/nickscha/ecs4j.svg?branch=master)
![codecov.io](https://codecov.io/github/nickscha/ecs4j/coverage.svg?branch=master)
![License](https://img.shields.io/hexpm/l/plug.svg)

ECS4J provides a lightweight Entity Component System (ECS) implementation which is easy to use as well as a transparent and fluent API.

## Maven
```xml
<dependency>
  <groupId>com.github.nickscha</groupId>
  <artifactId>ecs4j</artifactId>
  <version>NOT-RELEASED</version>
</dependency>
```

The artifact will be published under https://oss.sonatype.org

## Requirements
* Java 8 or later

## Introduction
The Entity Component System (in short ECS) is an architectural pattern following the composition over inheritance pattern and is highly compatible with data oriented design techniques. 
ECS is commonly used in applications were inheritance would lead to inflexible code
and confusing/unclear code structures in order to manage the data and
behaviours of entities. Due to the strict separation of data and behaviour it
allows easy multithreading and serialization of data.

ECS has a very strict terminology:
* Entity - A unique identifier for a set of components.
* Component - raw data and nothing more. No logic/behaviour.
* System - Contains the logic/behaviour for a list of entity components as required by the system. (Usually a system runs contuniously in private threads)
