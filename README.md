# Gremio API
[![UrbanRoland](https://circleci.com/gh/UrbanRoland/gremio-backend.svg?style=svg)](<LINK>)

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)

## Overview

Gremio is a single-page project management web application inspired by Asana built with JavaScript, Angular, PostgreSQL,
GraphQL and Spring Boot.

## Features

- Modern Spring Boot application
- Gradle for build automation
- GraphQL for efficient API querying
- Zipkin for distributed tracing

## Prerequisites

Make sure you have the following installed before starting:

- Java 21 or higher
- Gradle (you can use the wrapper provided)
- Docker

## Getting Started

1. Zipkin setup

   ```bash
   docker run -d -p 9411:9411 openzipkin/zipkin
   ```
2. Clone the repository:

   ```bash
   git clone https://github.com/UrbanRoland/gremio-backend.git
   
3. Run docker-compose:

   ```bash
   docker-compose up
   ```
   
4. Run the application:

   ```bash
   ./gradlew bootRun
   ```