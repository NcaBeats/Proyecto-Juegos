---
name: java-testing
description: >
  Use when writing or modifying unit tests for Spring Boot microservices.
  Follows the project conventions: factories own all test data, tests have
  zero private static final DTOs, package-private mocks, and faker-based
  entity creation.
---

# Java Testing

## Factory (`src/test/java/.../support/<Entidad>Factory.java`)

```java
public class <Entidad>Factory {
    public static final Faker FAKER = new Faker(Locale.of("es"));

    // --- constants ---
    public static final Long ID = 1L;
    public static final String NOMBRE = "...";

    // --- entity ---
    public static <Entidad> create<Entidad>Entity() { return <Entidad>.builder()...build(); }
    public static <Entidad> create<Entidad>EntityFaker() { return <Entidad>.builder()...build(); }

    // --- request (if applies) ---
    public static final <Entidad>Request <ENTIDAD>_REQUEST = new ...(NOMBRE);
    public static <Entidad>Request create<Entidad>RequestFaker() { return new ...(FAKER...()); }

    // --- response ---
    public static final <Entidad>Response <ENTIDAD>_RESPONSE = new ...(ID, NOMBRE, ...);

    // --- external DTOs (from Feign clients) ---
    public static final <External>Response <EXTERNAL>_RESPONSE = new ...(ID, NOMBRE, ...);
}
```

Rules:
- `FAKER` es `public static final`.
- Sin hardcoded literales en responses (usar `.getValue()`, referencias a enum, etc.).
- DTOs externos (`JuegoResponse`, `ProfileResponse`, `UserResponse`) van en el factory.

## Test (`src/test/java/.../unit/<domain>/service/<Entidad>ServiceTest.java`)

```java
@ExtendWith(MockitoExtension.class)
public class <Entidad>ServiceTest {

    @Mock
    <Entidad>Repository <entidad>Repository;
    @Mock
    <Entidad>Mapper <entidad>Mapper;
    // ... Feign clients, etc.

    @InjectMocks
    <Entidad>Service <entidad>Service;

    @Test
    void findAll_ReturnPage() {
        <Entidad> entity = create<Entidad>EntityFaker();
        ...
    }
}
```

Rules:
- Sin `private static final` para DTOs.
- Mocks package-private (sin `private`).
- Import estático del factory: `import static ...<Entidad>Factory.*;`.
- Usar `createEntityFaker()` para la mayoría de tests; `createEntity()` cuando importen valores fijos.
- Usar `var result = ...`.
- Asserts con `assertEquals(FACTORY_CONSTANT, result)`.

## Patrones comunes

### findAll (Page)
```java
when(repository.findAll(PAGEABLE)).thenReturn(page);
when(mapper.toResponse(entity)).thenReturn(RESPONSE);
var result = service.findAll(PAGEABLE);
assertNotNull(result);
assertEquals(RESPONSE, result.getContent().getFirst());
verify(repository).findAll(PAGEABLE);
```

### findById
```java
when(repository.findById(ID)).thenReturn(Optional.of(entity));
when(mapper.toResponse(entity)).thenReturn(RESPONSE);
var result = service.findById(ID);
assertNotNull(result);
assertEquals(RESPONSE, result);
```

### save (con Feign clients + notificación)
```java
when(client.method(...)).thenReturn(EXTERNAL_RESPONSE);
when(mapper.toEntity(REQUEST)).thenReturn(entity);
when(repository.save(entity)).thenReturn(entity);
when(mapper.toResponse(entity)).thenReturn(RESPONSE);
var result = service.save(REQUEST);
assertNotNull(result);
verify(notificationClient).createNotification(any());
```

### save cuando el cliente externo falla (catch)
```java
doThrow(new RuntimeException("Error")).when(notificationClient).createNotification(any());
var result = service.save(REQUEST);
assertNotNull(result);
```

### update
```java
when(repository.findById(ID)).thenReturn(Optional.of(entity));
when(repository.save(entity)).thenReturn(entity);
when(mapper.toResponse(entity)).thenReturn(RESPONSE);
var result = service.update(ID, REQUEST);
assertNotNull(result);
assertEquals(RESPONSE, result);
```

### update not found
```java
when(repository.findById(ID)).thenReturn(Optional.empty());
assertThrows(EntityNotFoundException.class, () -> service.update(ID, REQUEST));
verifyNoInteractions(mapper);
```

### delete
```java
when(repository.existsById(ID)).thenReturn(true);
service.delete(ID);
verify(repository).deleteById(ID);
```

### delete not found
```java
when(repository.existsById(ID)).thenReturn(false);
assertThrows(EntityNotFoundException.class, () -> service.delete(ID));
```
