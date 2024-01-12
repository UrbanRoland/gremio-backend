# Gremio API
[![Build Status](https://img.shields.io/circleci/project/github/UrbanRoland/gremio-backend/master.svg)](https://circleci.com/gh/UrbanRoland/gremio-backend)

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [GraphQL](#graphql)
- [Zipkin](#zipkin)
- [Contributing](#contributing)
- [License](#license)

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