# SABiOx Tool (Full-Stack Application)

This repository contains the full-stack application for the SABiOx tool, consisting of a Spring Boot backend, a Vue/Quasar frontend, and a MySQL database, all orchestrated via a single Docker Compose configuration.

The frontend is served using **Nginx** after a static build, and the backend waits for the database to become healthy before starting.

---

## Prerequisites

Ensure you have the following installed on your system:

* **Docker:** Docker Desktop or Docker Engine.
* **Docker Compose:** (Usually included with Docker Desktop).

## Getting Started

The application is designed to be run entirely using a single `docker-compose.yml` file, which handles building all images, setting up the network, and starting the services.

### 1. Run the Application

Execute the following command from the root directory of the project where `docker-compose.yml` is located:

```bash
docker compose up --build -d