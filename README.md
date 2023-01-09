# Zadanie Docker
*Wszystkie podpunkty* 

Link do dockera:
https://hub.docker.com/repository/docker/dzejkejem/first_docker

---

# Zadanie KTOR
*Wszystkie podpunkty* 

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

# Zadanie Kalkulator
*Wszystkie podpunkty* 

 ***Oznacznie niektórych wyrażeń***:
 
> ***\~*** - podana liczba z przeciwnym znakiem - **pomnożenie przez -1**

>***POWER*** - podana liczba do potęgi drugiej

>***LOG*** - podana liczba do logarytmu o podstawie dwa

**ZadanieKalkulator/Kalkulator.apk** - gotowe apk aplikacji 

Aplikacja była testowana na **Android 11 i 10**

---

# Zadanie Fragmenty
*Wszystkie podpunkty* 

Nie wiedziałem do końca w jaki sposób mam użyć fragemntów. W końcu każdy element listy jest w osobnym fragmencie

**ZadanieFragmenty/Fragmenty.apk** - gotowe apk aplikacji 

Aplikacja była testowana na **Android 11 i 10**

---
---

# Zadanie Intencje
*Wszystkie podpunkty* 

Zadanie jak każde inne od zadania 5 znajduje się w folderze **Zadania5-11**

---

# Zadanie Bazy Danych
*Wszystkie podpunkty* 

Zadanie jak każde inne od zadania 5 znajduje się w folderze **Zadania5-11**

---

# Zadanie Sieci
*Wszystkie podpunkty* 

Zadanie jak każde inne od zadania 5 znajduje się w folderze **Zadania5-11**
Aby zmienić adres serwera z którym się łączy aplikacji należy edytować zmienną
**baseUrl** w pakiecie **zadaniebazydanych\NetworkAdapter.kt**

---

# Zadanie Outh
*Wszystkie podpunkty* 

Zadanie jak każde inne od zadania 5 znajduje się w folderze **Zadania5-11**
Dodałem outh do googla i gita. Aby aplikacja działa poprawnie należy dodać pliki konfiguracyjne

> **outh.properties** w \assets - dane do łączenia z gitem

> **google-services.json** w folderze głównym \app

---

# Zadanie Płatności
*Wszystkie podpunkty* 

Zadanie jak każde inne od zadania 5 znajduje się w folderze **Zadania5-11**
Dodałem przykładowe konfigi do poprzednich zadań. Dodałem nowego konfiga do ktora **application.conf**