# Zadanie Docker

Link do dockera:
https://hub.docker.com/repository/docker/dzejkejem/first_docker

---

# Zadanie KTOR

CORS został tak skonfigurowany, że pozwala na połączenia hostom: **www.google.com** i **localhost:3400**

Przykładowe polecenie testujące CORS

```bash
fetch('http://127.0.0.1:8080/categories',
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            method: "GET",
        })
        .then(response => response.text())
        .then(data => {
            console.log('Success:', data);
            alert(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
```
---