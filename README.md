Applikáció dev módban való elindításához szükséges dokumentáció
Előfeltételek

Java 17 (vagy újabb),
Maven 3.8+,
PostgreSQL,
jq

Adatbázis létrehozása és feltöltés (PostgreSQL)

Hozd létre a DB-t és a felhasználót:
Start menü → PostgreSQL → SQL Shell (psql)
```
Server [localhost]:
Database [postgres]:
Port [5432]:
Username [postgres]:
Password: ******
```
Ha beléptél, ott vagy a postgres=# promptban.
```
CREATE DATABASE turtle_db;
CREATE USER turtle_user WITH ENCRYPTED PASSWORD 'secretpassword';
GRANT ALL PRIVILEGES ON DATABASE turtle_db TO turtle_user;
```
Kapcsold be a sémához való jogosultságot
```
\c turtle_db

GRANT USAGE ON SCHEMA public TO turtle_user;
GRANT CREATE ON SCHEMA public TO turtle_user;```
```
Futtasd a create_schema.sql és a fill_data.sql fájlokat az adatbázison:
```
psql -h localhost -U turtle_user -d turtle_db -f create_schema.sql
psql -h localhost -U turtle_user -d turtle_db -f fill_data.sql
```
Alkalmazás indítása (dev mód)


Futtatás Maven-nel:
```
mvn clean package
mvn spring-boot:run
```

Alapértelmezett port: 8080.

API példák (curl)

A dátumok ISO-8601 zonális formátumban legyenek: pl. 2025-11-20T15:30:00+01:00.

GET all turtles (nem törölt)
```
curl -s http://localhost:8080/api/turtles/all | jq
```
GET one turtle
```
curl -s http://localhost:8080/api/turtles?id=1 | jq
```
GET all by species
```
curl -s http://localhost:8080/api/turtles/by-species?speciesId=2 | jq
```
POST create new turtle (súly lehet kg vagy lb stb.)
```
curl -X POST http://localhost:8080/api/turtles \
-H "Content-Type: application/json" \
-d '{
"speciesId": 2,
"name": "Cili",
"intakeDate": "2025-11-01T09:00:00+01:00",
"weightValue": 1.2,
"weightUnit": "kg",
"ageYears": 2
}'
```

Megjegyzés: id megadása tilos POST-nál. Minden mező kötelező.

PUT update turtle (részleges módosítás — null = nem módosít)

Példa: csak név és súly módosítása:
```
curl -X PUT http://localhost:8080/api/turtles \
-H "Content-Type: application/json" \
-d '{
"id": 1,
"name": "Béla Új",
"weightValue": 2.6,
"weightUnit": "lb"
}'
```


Ha csak egy mezőt akarsz módosítani, küldd a többit nélkül (null ≠ mező hiánya — DTO-ban a hiányzó mező semmilyen változtatást nem indít el).

DELETE soft delete
```
curl -X DELETE http://localhost:8080/api/turtles?id=1
```

Ha a törlendő entitás már deleted = true, a rendszer 404 Not Found-dal válaszol a specifikációnak megfelelően.

