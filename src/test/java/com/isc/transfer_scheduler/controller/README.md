
# how to Test
# mvn -Dtest=TransferControllerIntegrationTest test
# mvn -Dtest=AccountControllerIntegrationTest test
# Test Scenarios for AccountController



This document outlines the key test scenarios covered for the `AccountController` class.

---

## **Key Scenarios Covered**

### 1. **`testCreateAccount_Success`**
- **Description**: Tests successful account creation.
- **Scenario**:
    - A valid `AccountDto` is provided.
    - The account is created successfully.
    - The response includes the created account details with HTTP status `201 Created`.

---

### 2. **`testCreateAccount_InvalidInput`**
- **Description**: Tests handling of invalid input (e.g., empty account number or negative balance).
- **Scenario**:
    - An invalid `AccountDto` is provided (e.g., empty account number or negative balance).
    - The system returns a `400 Bad Request` response.

---

### 3. **`testGetAccountById_Success`**
- **Description**: Tests successful retrieval of an account by ID.
- **Scenario**:
    - A valid account ID is provided.
    - The account exists and belongs to the logged-in user.
    - The system returns the account details with HTTP status `200 OK`.

---

### 4. **`testGetAccountById_NotFound`**
- **Description**: Tests handling of a non-existent account.
- **Scenario**:
    - A non-existent account ID is provided.
    - The system returns a `404 Not Found` response.

---

### 5. **`testGetAccountById_Unauthorized`**
- **Description**: Tests handling of unauthorized access (account does not belong to the logged-in user).
- **Scenario**:
    - A valid account ID is provided, but the account belongs to another user.
    - The system returns a `404 Not Found` response (or `403 Forbidden` if you implement stricter authorization checks).

---

## **How to Run the Tests**

To run the tests, use the following Maven command:

```bash
mvn test

# ###########################################################
# ###########################################################


# TransferControllerIntegrationTest
Key Scenarios Covered
1. testCreateTransfer_Success
Description: Tests successful transfer creation.

Scenario:

A valid TransferDto is provided.

The transfer is created successfully.

The response includes the created transfer details with HTTP status 200 OK.