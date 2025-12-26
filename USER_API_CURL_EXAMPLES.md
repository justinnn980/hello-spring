# User API curl 예시

## 1. 회원가입 (POST /api/users)

### 성공 케이스
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123",
    "nickname": "홍길동"
  }'
```

**응답 (201 Created):**
```json
{
  "meta": {
    "result": "SUCCESS",
    "error_code": null,
    "message": null
  },
  "data": {
    "id": 1,
    "email": "user@example.com",
    "nickname": "홍길동",
    "created_at": "2025-12-26T12:34:56Z",
    "updated_at": "2025-12-26T12:34:56Z"
  }
}
```

### 닉네임 없이 회원가입
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user2@example.com",
    "password": "password123"
  }'
```

### 실패 케이스 - 이메일 중복 (409 Conflict)
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123",
    "nickname": "김철수"
  }'
```

**응답 (409 Conflict):**
```json
{
  "meta": {
    "result": "FAIL",
    "error_code": "Conflict",
    "message": "이미 존재하는 이메일입니다."
  },
  "data": null
}
```

### 실패 케이스 - 유효성 검사 실패 (400 Bad Request)
```bash
# 비밀번호가 8자 미만
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user3@example.com",
    "password": "short"
  }'
```

**응답 (400 Bad Request):**
```json
{
  "meta": {
    "result": "FAIL",
    "error_code": "Bad Request",
    "message": "password: 비밀번호는 최소 8자 이상이어야 합니다."
  },
  "data": null
}
```

```bash
# 이메일 형식 오류
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "invalid-email",
    "password": "password123"
  }'
```

**응답 (400 Bad Request):**
```json
{
  "meta": {
    "result": "FAIL",
    "error_code": "Bad Request",
    "message": "email: 유효한 이메일 형식이 아닙니다."
  },
  "data": null
}
```

## 2. 사용자 단건 조회 (GET /api/users/{id})

### 성공 케이스
```bash
curl -X GET http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json"
```

**응답 (200 OK):**
```json
{
  "meta": {
    "result": "SUCCESS",
    "error_code": null,
    "message": null
  },
  "data": {
    "id": 1,
    "email": "user@example.com",
    "nickname": "홍길동",
    "created_at": "2025-12-26T12:34:56Z",
    "updated_at": "2025-12-26T12:34:56Z"
  }
}
```

### 실패 케이스 - 존재하지 않는 사용자 (404 Not Found)
```bash
curl -X GET http://localhost:8080/api/users/999 \
  -H "Content-Type: application/json"
```

**응답 (404 Not Found):**
```json
{
  "meta": {
    "result": "FAIL",
    "error_code": "Not Found",
    "message": "사용자를 찾을 수 없습니다."
  },
  "data": null
}
```

## 3. 사용자 정보 수정 (PATCH /api/users/{id})

### 성공 케이스 - 닉네임 변경
```bash
curl -X PATCH http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nickname": "새로운닉네임"
  }'
```

**응답 (200 OK):**
```json
{
  "meta": {
    "result": "SUCCESS",
    "error_code": null,
    "message": null
  },
  "data": {
    "id": 1,
    "email": "user@example.com",
    "nickname": "새로운닉네임",
    "created_at": "2025-12-26T12:34:56Z",
    "updated_at": "2025-12-26T13:45:00Z"
  }
}
```

### 닉네임 null로 변경
```bash
curl -X PATCH http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nickname": null
  }'
```

### 실패 케이스 - 존재하지 않는 사용자 (404 Not Found)
```bash
curl -X PATCH http://localhost:8080/api/users/999 \
  -H "Content-Type: application/json" \
  -d '{
    "nickname": "새닉네임"
  }'
```

**응답 (404 Not Found):**
```json
{
  "meta": {
    "result": "FAIL",
    "error_code": "Not Found",
    "message": "사용자를 찾을 수 없습니다."
  },
  "data": null
}
```

### 실패 케이스 - 유효성 검사 실패 (400 Bad Request)
```bash
# 닉네임이 100자 초과
curl -X PATCH http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nickname": "이것은100자를초과하는매우긴닉네임입니다이것은100자를초과하는매우긴닉네임입니다이것은100자를초과하는매우긴닉네임입니다이것은100자를초과하는매우긴닉네임입니다"
  }'
```

**응답 (400 Bad Request):**
```json
{
  "meta": {
    "result": "FAIL",
    "error_code": "Bad Request",
    "message": "nickname: 닉네임은 100자를 초과할 수 없습니다."
  },
  "data": null
}
```

## 주요 특징

1. **비밀번호 보안**: password_hash는 어떤 응답에서도 노출되지 않음
2. **자동 타임스탬프**: created_at은 생성 시 자동 설정, updated_at은 수정 시 자동 갱신 (@PreUpdate 사용)
3. **Snake Case 응답**: 모든 JSON 필드는 snake_case로 변환됨 (JacksonConfig의 PropertyNamingStrategies.SNAKE_CASE 적용)
4. **입력 검증**: Spring Validation과 도메인 로직 검증을 함께 사용
5. **일관된 에러 응답**: ApiResponse 포맷으로 통일된 에러 응답 제공