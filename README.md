# Word Stream Translator

**Lab Work for Tinkoff Education Autumn 2024 Contest**

A Spring Boot–based web application that translates a set of words from one language to another using a third-party translation API. Each word is translated in its own thread (up to 10 concurrent threads), and every request is logged in a relational database.

---

## 📋 Assignment

You need to develop a web application in Java/Kotlin to translate a set of words into another language using a third-party translation service (Yandex, Google, etc.).

**Requirements:**

* **Input:** a string of words, a source language code and a target language code.
  **Output:** the fully translated string.
* **Concurrency:** each word is translated in its own thread. No more than **10 threads** may run simultaneously.
* **Persistence:** store each request in a relational database, including:

    * User’s IP address
    * Original input string
    * Translation result
      *(You design the schema yourself.)*
* **Repository:** publish all code on GitHub with this README.

**Additional Requirements:**

* Use **Spring / Spring Boot**.
* Use **JDBC** only for database access.
* Use **RestTemplate** for calling the external translation API.

> Solutions will be manually reviewed for functionality **and** code quality (structure, style, testing, best practices).

---

## 🚀 Features

* **Multi‑threaded translation** of individual words
* **Thread pool** limiting concurrent translations to 10
* **Persistence** of each request and response in a relational database
* **Configurable** source and target languages
* **RESTful API** for submitting and retrieving translations
* **Extensible**: easily swap in different translation providers

---

## 🛠️ Tech Stack

* **Language:** Java 17
* **Framework:** Spring Boot 3.x
* **Database Access:** JDBC
* **HTTP Client:** Spring RestTemplate
* **Translation Api:** DeepL
* **Build Tool:** Maven
* **Database:** PostgreSQL
* **Threading:** `java.util.concurrent.ExecutorService`

---

## 🔧 Prerequisites

* JDK 17 or newer
* Maven 3.6+
* Running instance of PostgreSQL
* Valid API key for chosen translation service

---

## ⚙️ Configuration

Edit or add `src/main/resources/application.yml`. Example:

```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
  data:
    redis:
      host: localhost
      port: 6379
      timeout: 6000ms

translator:
  external:
    api-url: https://api-free.deepl.com/v2/translate
    api-key: ${EXTERNAL_API_KEY}

server:
  port: 8080
```

Set environment variables in your local shell or `.env` file:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/translator_db
export SPRING_DATASOURCE_USERNAME=your_db_user
export SPRING_DATASOURCE_PASSWORD=your_db_password
export EXTERNAL_API_KEY=your_translation_api_key 
```

---

## 🚀 Build & Run

**Linux/macOS (bash/zsh):**

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/translator_db
export SPRING_DATASOURCE_USERNAME=your_db_user
export SPRING_DATASOURCE_PASSWORD=your_db_password
export EXTERNAL_API_KEY=your_translation_api_key
```

**Windows PowerShell:**

```powershell
$env:SPRING_DATASOURCE_URL='jdbc:postgresql://localhost:5432/translator_db'
$env:SPRING_DATASOURCE_USERNAME='your_db_user'
$env:SPRING_DATASOURCE_PASSWORD='your_db_password'
$env:EXTERNAL_API_KEY='your_translation_api_key'
```

The service will start on **[http://localhost:8080](http://localhost:8080)** by default.

---
## 🗄️ Database Schema & Functions

All SQL files are in `src/main/resources/functions.sql` and `src/main/resources/schema.sql`.

To create and manage API keys for accessing the translation service, use the following PostgreSQL function from `src/main/resources/functions.sql`

This function generates a UUID-based key, stores it in the `api_keys` table with an optional description, and returns the new key. 
Provide the returned key in each HTTP request using the `X-API-KEY` header.

---
## 📡 Usage


### Translate endpoint

`POST /api/translate`

**Request**

```json
{
  "text": "Hello Medeu how are you? We'vent seen you for so long",
  "sourceLang": "EN",
  "targetLang": "RU"
}
```

**Response**

```json
[
  "Здравствуйте",
  "Medeu",
  "как",
  "это",
  "ты?",
  "Мы",
  "не",
  "видели",
  "вас",
  "так",
  "давно"
]
```

---

## 📝 License

This project is licensed under the **MIT License**.
See [LICENSE](LICENSE) for details.
