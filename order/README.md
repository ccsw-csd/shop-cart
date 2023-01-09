# Shop Order

## Invocación

Se debe realizar una llamada POST a:

* URL: http://localhost:8081/order/purchase

Como Body se adjunta el mensaje:

```json
{
    "customer":"customer1",
    "email":"email@email.com",
    "address":"address1",
    "credit":"12345",
    "product":"product1",
    "quantity":2
}
```