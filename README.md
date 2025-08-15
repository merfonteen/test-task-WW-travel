## How to run  
 
1. **Build:**
   ```bash  
   - mvn -f auth-api/pom.xml clean package -DskipTests  
   - mvn -f data-api/pom.xml clean package -DskipTests
   ````  
2. **Run:**
   ```bash  
   - docker-compose up --build
   ````
---

## Register & Login  

### Register a new user:  
**POST** `http://localhost:8080/api/v1/auth/register`  
```json
{
  "email": "test@gmail.com",
  "password": "123456"
}
```

### Authorize a user:  
**POST** `http://localhost:8080/api/v1/auth/register`  
```json
{
   "email": "test@gmail.com",
   "password": "123456"
}
````

### Save token from response

---

## Process  
**POST** `http://localhost:8080/api/v1/auth/process`  
**Headers:** `Authorization`: Bearer <your_token>  
```json
{
   "text": "TEST"
}
````

**Expected result:** `reversed string`

---

## Template for .env file:  
   ```bash
  POSTGRES_URL=jdbc:postgresql://postgres:5432/auth_db  
  POSTGRES_USERNAME=test  
  POSTGRES_PASSWORD=test  
  JWT_SECRET=aa48f49215687165e7aea66f887e0e7ae5ba4e42469eda898a63e2848da76826  
  INTERNAL_TOKEN=test-internal-token  
  DATA_API_BASE_URL=http://data-api:8081  
 ```
 
