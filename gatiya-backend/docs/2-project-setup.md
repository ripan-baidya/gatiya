# Complete Spring Boot Monorepo setup

## Project Structure

```markdown
gatiya-backend/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── gatiya/
│   │   │           ├── RideshareApplication.java
│   │   │           │
│   │   │           ├── config/              # Global configurations
│   │   │           ├── security/            # Security & JWT
│   │   │           ├── common/              # Shared components
│   │   │           ├── exception/           # Exception handling
│   │   │           ├── util/                # Utility classes
│   │   │           ├── properties/          # Configuration properties
│   │   │           │
│   │   │           ├── user/                # User module
│   │   │           ├── rider/               # Rider module
│   │   │           ├── driver/              # Driver module
│   │   │           ├── ride/                # Ride module
│   │   │           ├── payment/             # Payment module
│   │   │           ├── location/            # Location tracking module
│   │   │           ├── notification/        # Notification module
│   │   │           └── rating/              # Rating module
│   │   │
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       ├── firebase-service-account.json
│   │       ├── db/
│   │       │   └── migration/               # Flyway/Liquibase migrations
│   │       │       ├── V1__create_users_table.sql
│   │       │       ├── V2__create_riders_table.sql
│   │       │       └── ...
│   │       └── static/
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── rideshare/
│                   ├── user/
│                   ├── rider/
│                   ├── driver/
│                   └── ...
│
├── .env                           # Environment variables (gitignored)
├── .gitignore
├── pom.xml (or build.gradle)      # Maven/Gradle build file
├── Dockerfile                     # Docker container definition
├── docker-compose.yml             # Local development with Docker
├── .github/
│   └── workflows/
│       └── ci-cd.yml              # GitHub Actions CI/CD
├── docs/                          # Documentation
│   ├── API.md
│   ├── SETUP.md
│   └── ARCHITECTURE.md
└── README.md
```

## Config files

```markdown
config/
├── SecurityConfig.java              # Spring Security configuration
├── JwtConfig.java                   # JWT configuration
├── FirebaseConfig.java              # Firebase initialization
├── WebMvcConfig.java                # CORS, Interceptors
├── AsyncConfig.java                 # Async task executor
├── OpenApiConfig.java               # Swagger/OpenAPI docs
└── RedisConfig.java                 # Redis cache (optional)
```

## Module Structure (Feature Based)

```markdown
Each Module will follow the same structure.

{module_name}/
├── entity/                          # JPA entities
│   └── {Entity}.java
├── repository/                      # Spring Data JPA repositories
│   └── {Entity}Repository.java
├── service/                         # Business logic
│   ├── {Service}.java
│   └── impl/
│       └── {Service}Impl.java
├── controller/                      # REST controllers
│   └── {Controller}.java
├── dto/                             # Data Transfer Objects
│   ├── request/
│   │   └── {Request}DTO.java
│   └── response/
│       └── {Response}DTO.java
├── mapper/                          # Entity <-> DTO mappers
│   └── {Mapper}.java
└── enums/                           # Module-specific enums
└── {Enum}.java
```

## External API Setup

### Firebase

```markdown
1. Go to Firebase Console
2. Click Add project → Create a project (e.g., gatiya-rideshare)
3. Once created, open Project Settings → Service accounts
4. Click “Generate new private key”, which downloads a JSON file (e.g., serviceAccountKey.json)

👉 This key file gives your backend permission to use Firebase Admin SDK.
Keep it private — don’t commit it to Git.
```

### Razorpay

```markdown

```

## Entities wrt each module

1. common
    - BaseEntity

2. user
    - User (C)
    - UserRole (E)
    - AccountStatus (E)

3. rider
    - Rider (C)
    - SavedAddress(C)

4. driver
    - Driver(C)
    - DriverDocument(C)
    - Vehicle(C)
    - DocumentType(E)
    - DocumentVerificationStatus(E)
    - DriverAvailabilityStatus(E)
    - DriverVerificationStatus(E)
    - VehicleType(E)

5. ride
    - Ride(C)
    - RideOffer(C) ❌
    - RideStatus(E)
    - PricingMode(E)
    - CancellationBy(E)
    - OfferStatus(E) ❌

6. payment
    - Payment(C)
    - PaymentMethod(E)
    - PaymentStatus(E)

7. rating
    - Rating(C)

8. notification
    - Notification(C)
    - NotificationType(E)

---


## Entity Relationship Diagram

```markdown
                                ┌─────────────┐
                                │ BaseEntity  │ (Base)
                                └──────┬──────┘
                                       |
                                       |
                                ┌──────|──────┐
                                │    User     │ 
                                └──────┬──────┘
                                       │
                    ├──────────────────┬──────────────────┐
                    │                  │                  │
             ┌──────▼──────┐    ┌──────▼──────┐   ┌──────▼──────┐
             │    Rider    │    │   Driver    │   │    Admin    │
             └──────┬──────┘    └──────┬──────┘   └─────────────┘
                    │                  │
                    │                  │
                    │           ┌──────┴──────┐
                    │           │             │
                    │      ┌────▼────┐   ┌────▼────────────┐
                    │      │ Vehicle │   │ DriverDocument  │
                    │      └─────────┘   └─────────────────┘
                    │
                    │
                    ├──────────┬──────────┬──────────┐
                    │          │          │          │
             ┌──────▼──────┐   │    ┌─────▼──────┐   │
             │SavedAddress │   │    │   Ride     │◄──┤
             └─────────────┘   │    └─────┬──────┘   │
                               │          │          │
                        ┌──────▼──────┐   ├──────────┤
                        │  Payment    │   │          │
                        └─────────────┘   │    ┌─────▼──────┐
                                          |    │ RideOffer  │
                                          |    ┴-───────────┘
                                    ┌─────▼───-----|
                                    │     Rating   |
                                    └──────────────┘
```